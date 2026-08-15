# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

Plain Java + Ant, no dependencies, no test suite, no lint config. Targets **Java 21** (`release="21"` in `build.xml`); the jar runs on any JDK 21 or newer.

```bash
ant release          # javac --release 21 src/ -> build/, then jar -> jsloccount.jar (manifest: src/Manifest)
ant clean            # remove jsloccount.jar and build/
java -jar jsloccount.jar <directory>
```

Ant is **not installed on this machine**. To compile and run without it:

```bash
javac --release 21 -encoding UTF-8 -Xlint:all -d /tmp/out $(find src -name '*.java')
java -cp /tmp/out org.jsloc.Main <directory>
```

The build must stay warning-clean under `-Xlint:all`.

Verifying a change means running the tool on a directory and inspecting the two generated CSVs — there are no unit tests. Dogfooding on `src/` is the usual smoke test; for a change to the counting logic, build a small corpus with known comment shapes (block comments, trailing comments, blank lines, files without a trailing newline) and diff the output against the previous build.

Outputs are written to the **current working directory**, named `<dirname>-filestats.csv` and `<dirname>-sizestats.csv`, where `<dirname>` is the basename of the scanned directory. `build/`, `jsloccount.jar` and `*.csv` are gitignored.

## Architecture

Single pass, four stages, driven by `Main.main`:

1. **`project/Resource`** (enum) — the single source of truth for every supported file type. Each constant carries comment markers, optionally a `Quotes.*` string-literal family (shared presets rather than per-constant delimiters — a separate class, since an enum constant may not reference its own enum's static fields), extensions/filenames, and a display name. The 3-arg constructor (`List<Marker>, List<String>, String`) declares a *text* resource that gets line-counted; the 2-arg one (`List<String>, String`) declares a *binary* resource that only gets file-counted. `Resource.detect()` matches by `String.endsWith` over the **filename**, **in declaration order** — so entries whose "extension" is really a full filename (`build.xml`, `Makefile`) must stay at the top of the enum, before the suffix-based ones, or they get shadowed. Adding a language = one enum constant; nothing else needs touching.

2. **`project/ProjectStatistics`** — walks the directory with `Files.walkFileTree` (following symlinks, skipping hidden files and dirs, logging and continuing past unreadable entries and symlink loops), calls `Resource.detect` per file, and accumulates a `LanguageStatistics` per `Resource` in an `EnumMap`. Binary and `OTHER` resources only bump the file count. The `EnumMap` is what makes report ordering deterministic — ties break by enum declaration order.

3. **`resources/statistics/Statistics`** — the counter, a record produced by the static factory `Statistics.count(Path, Resource)`. It scans each trimmed line segment by segment, carrying an `openBlock` marker across lines, and decides at the end of the line whether it held code, a comment, or both. Single-line markers are those where start equals end (`Marker.isSingleLine()`). Files are read as UTF-8 with malformed bytes replaced, so an oddly-encoded file cannot abort a scan. See "Counting rules" below.

4. **`output/`** — `OutputFactory.getFileOutput()` is the only entry point (`AbstractOutput` exists for alternative outputs that don't exist yet). `AbstractOutput` builds the two descending-sorted lists of `ResourceValue` (a record; sort with `ResourceValue.BY_VALUE_DESCENDING`): `byFiles` covers every resource and feeds `-filestats.csv`, `byLines` is text-only and feeds `-sizestats.csv`. `FileOutput.produce()` renders them, skipping `OTHER` in the file report.

`Configuration` is a static logger (`[INFO]`/`[WARN]`/`[ERRO]` to stdout) and nothing else.

### Counting rules

The scanner in `Statistics.count` walks each line segment by segment rather than testing it once, which is what makes the following hold. Preserve these when touching it:

- A line is counted **at most once** per bucket, but can land in both: `int c; /* inline */ int d;` is one source line and one comment line.
- A block comment stays open across lines until its closing marker, including across blank lines. Interior lines are comments, not code.
- Code after a block closes on the same line counts as source: `still open */ int e;` is both.
- Where two markers start at the same index, the longer one wins, so Java's `/**` beats `/*` and Lua's `--[[` beats `--`.
- Blank lines count toward `totalLines` only.
- `Marker.inColumnOne(...)` markers match only at index 0 of the **untrimmed** line, which is what fixed-form Fortran needs: a column-one `C` opens a comment, but the `C` of an indented `CALL FOO` does not. Only Fortran uses this; everything else matches anywhere on the line.
- **Nesting is per marker, not per language**, because D has both forms: its `/* */` ends at the first `*/`, while its `/+ +/` nests. `Marker.nesting(...)` opts a marker in, and the scanner tracks a depth carried across lines alongside `openBlock`. Nesting languages: Rust, Swift, Scala, Kotlin, Dart, Haskell, OCaml, F#, Julia, and D's `/+ +/`. C, C++, C#, Java, JavaScript and Go deliberately do **not** nest — a wrong `nesting(...)` there would swallow everything after `/* a /* b */`.
- **String literals are skipped, not searched.** Whichever opens first at the current position wins: a marker inside a literal opens no comment (`char *url = "http://example.com";` is code alone), and a quote inside a comment opens no literal. Escapable literals step over `\"`, and spanning ones (Java text blocks, Python docstrings, JS templates) carry across lines exactly as block comments do. Literal content always counts as code.

There are no unit tests, so changes here are verified by counting a corpus with hand-computed expectations (see the "Build & Run" note above).

### Deciding what to support next

There is a check worth re-running after any batch of additions — it walks every
declared extension and asserts `Resource.detect` returns the declaring constant,
which catches one type shadowing another:

```java
for (Resource r : Resource.values())
    for (String ext : r.extensions())
        assert Resource.detect(ext.startsWith(".") ? "sample" + ext : ext) == r;
```

`Main` prints a line naming the most common suffixes that fell into `OTHER`
(`ProjectStatistics.unknownSuffixes()`), so the enum grows from evidence rather than
guesswork — run the tool over a real project and read what it reports. Adding a type
is one enum constant; longest-suffix matching means placement cannot shadow an
existing entry. What needs deliberate thought is a **collision** (`.ts` TypeScript vs
MPEG transport stream, `.m` Objective-C vs MATLAB, `.d` D vs make-deps, `.pl` Perl vs
Prolog): equal-length matches fall to declaration order, so pick a winner on purpose.

**Binary types matter as much as text ones.** They carry the `-filestats.csv` report,
which is half of what the tool exists to produce — "Compiled Gettext Catalogs, 9214"
is a project statistic, while the same files sitting in `OTHER` are noise. Adding a
binary type is the same one-line change; it just uses the 2-argument constructor.

Deliberately **not** supported, and worth not "fixing" by accident:

- **Assembly (`.s`/`.asm`).** The comment character is per-architecture (`;`, `#`, `@`,
  `//`), and `.S` files are preprocessed, so a `#` marker would count every `#include`
  as a comment. Better absent than wrong.
- **reStructuredText.** Its `..` comment prefix is indistinguishable from ordinary
  directive and link syntax without a real parser.
- **Numeric man-page sections (`.1` … `.9`).** `.3` would also swallow versioned shared
  objects such as `libfoo.so.3`, which are far more common in a scanned tree.

### Remaining known issues

- `README.md`'s "Eat Your Own dogfood" section opens with a `Number of Files: … Number of Lines (comments): …` console block. The tool has no such console report — it only writes the two CSVs, which the rest of that section shows correctly. Either the feature was dropped or the docs were aspirational.
- `Resource.detect` matches whole-filename entries by suffix, so `mybuild.xml` is detected as an ANT build file. Exact-filename matching for entries without a leading dot would tighten this.
- Lua's long-bracket comments only match `--[[ ]]`; the levelled forms (`--[==[ ]==]`) are not recognized.
- Vim Script uses `"` for comments *and* for string literals, so `.vim` counts are optimistic. It is listed anyway because `"` genuinely is the comment character.
- No unit tests at all.
