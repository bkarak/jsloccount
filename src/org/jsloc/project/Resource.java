/*
# Copyright (c) 2013, Vassilios Karakoidas (vassilios.karakoidas@gmail.com)
 All rights reserved.
 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright

    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * The names of its contributors may not be used to endorse or promote products
      derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL Vassilios Karakoidas BE LIABLE FOR ANY
 DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.jsloc.project;

import java.util.List;

import org.jsloc.resources.statistics.Marker;

import static java.util.stream.Collectors.joining;

/**
 * Every file type JSLoCCount knows about.
 *
 * <p>A constant declared with comment markers is a <em>text</em> resource and gets
 * its lines counted; a constant declared with extensions alone is a <em>binary</em>
 * resource and only gets its files counted.
 *
 * <p>{@link #detect(String)} resolves a filename to the constant owning the longest
 * matching suffix, so entries whose "extension" is really a whole filename
 * ({@code build.xml}, {@code Makefile}) win over the plain suffixes they end with,
 * wherever they are declared. Declaration order only breaks ties between suffixes
 * of equal length.
 *
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 */
public enum Resource {
    // text
    // first of all, put the files with full filenames
    ANT(List.of(new Marker("<!--", "-->")), List.of("build.xml"), "ANT Build File"),
    MAKE(List.of(new Marker("#")), List.of("Makefile", "makefile", "GNUmakefile", ".inc", ".mk"), "make"),
    // then the rest
    HASKELL(List.of(new Marker("--"), new Marker("{-", "-}")), List.of(".hs"), "Haskell"),
    JAVA(List.of(new Marker("//"), new Marker("/*", "*/"), new Marker("/**", "*/")), List.of(".java"), "Java"),
    C(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".c"), "C"),
    CPLUSPLUS(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".C", ".cpp", ".cxx", ".cc"), "C++"),
    HEADER(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".h", ".hxx", ".H", ".hpp", ".hh"), "C/C++/SWIFT/Objective-c Headers"),
    PASCAL(List.of(new Marker("//"), new Marker("{", "}"), new Marker("(*", "*)")), List.of(".p", ".pas"), "Pascal"),
    BOURNESHELL(List.of(new Marker("#")), List.of(".sh", ".bash", ".ksh"), "Bourne Shell"),
    CSHARP(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".cs"), "C#"),
    XML(List.of(new Marker("<!--", "-->")), List.of(".xml"), "XML"),
    HTML(List.of(new Marker("<!--", "-->")), List.of(".htm", ".html"), "HTML"),
    BIBTEX(List.of(new Marker("%")), List.of(".bib"), "BiBTeX"),
    TEX(List.of(new Marker("%")), List.of(".tex", ".cls"), "TeX/LaTeX"),
    PERL(List.of(new Marker("#")), List.of(".pl", ".pm"), "Perl"),
    AWK(List.of(new Marker("#")), List.of(".awk"), "awk"),
    OBJECTIVEC(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".m"), "Objective-C"),
    PHP(List.of(new Marker("<!--", "-->"), new Marker("//"), new Marker("#"), new Marker("/*", "*/")), List.of(".php", ".php3", ".php4"), "PHP"),
    XSL(List.of(new Marker("<!--", "-->")), List.of(".xsl", ".xslt"), "XSL/XSLT"),
    BAT(List.of(new Marker("rem "), new Marker("REM "), new Marker("::")), List.of(".bat", ".cmd"), "MS-Dos/Windows Batch Files"),
    XSD(List.of(new Marker("<!--", "-->")), List.of(".xsd", ".xs"), "X-Schema Files"),
    DTD(List.of(new Marker("<!--", "-->")), List.of(".dtd", ".mod"), "Document Type Definition Files"),
    SQL(List.of(new Marker("--"), new Marker("/*", "*/")), List.of(".sql"), "SQL"),
    GNUPLOT(List.of(new Marker("#")), List.of(".plot", ".gnuplot"), "Gnuplot"),
    SED(List.of(new Marker("#")), List.of(".sed"), "SED"),
    TEXT(List.of(), List.of(".txt", ".text"), "ASCII Text Files"),
    VISUALSTUDIOPROJECT(List.of(new Marker("<!--", "-->")), List.of(".vcproj"), "Visual Studio Project File"),
    CSHARPPROJECT(List.of(new Marker("<!--", "-->")), List.of(".csproj"), "Visual Studio C# Project File"),
    RDF(List.of(new Marker("<!--", "-->")), List.of(".rdf"), "RDF"),
    WSDL(List.of(new Marker("<!--", "-->")), List.of(".wsdl"), "WSDL"),
    POM(List.of(new Marker("<!--", "-->")), List.of(".pom"), "Maven POM File"),
    JSP(List.of(new Marker("<!--", "-->"), new Marker("//"), new Marker("/*", "*/"), new Marker("/**", "*/")), List.of(".jsp"), "JSP"),
    JAVASCRIPT(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".js", ".mjs", ".cjs", ".jsx"), "Javascript"),
    CSHELL(List.of(new Marker("#")), List.of(".csh"), "C-Shell"),
    RTF(List.of(), List.of(".rtf"), "RTF"),
    PYTHON(List.of(new Marker("#")), List.of(".py"), "Python"),
    JAVACC(List.of(new Marker("//"), new Marker("/*", "*/"), new Marker("/**", "*/")), List.of(".jj"), "JavaCC Grammar Files"),
    RUBY(List.of(new Marker("#"), new Marker("=begin", "=end")), List.of(".rb"), "Ruby"),
    TCL(List.of(new Marker("#")), List.of(".tcl"), "TCL"),
    TCLTK(List.of(new Marker("#")), List.of(".tk"), "TCL/Tk"),
    D(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".d"), "D"),
    CMAKE(List.of(new Marker("#"), new Marker("//")), List.of(".cmake", "CMakeLists.txt"), "CMake"),
    SCALA(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".scala"), "Scala"),
    FORTRAN(List.of(Marker.inColumnOne("C"), Marker.inColumnOne("c"), Marker.inColumnOne("*"), new Marker("!")), List.of(".f", ".for", ".f77"), "Fortran (fixed-form)"),
    // 2017 additions
    GO(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".go"), "Go"),
    SWIFT(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".swift"), "Swift"),
    // 2026 additions: languages
    TYPESCRIPT(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".ts", ".tsx", ".mts", ".cts"), "TypeScript"),
    RUST(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".rs"), "Rust"),
    KOTLIN(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".kt", ".kts"), "Kotlin"),
    LUA(List.of(new Marker("--"), new Marker("--[[", "]]")), List.of(".lua"), "Lua"),
    DART(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".dart"), "Dart"),
    ELIXIR(List.of(new Marker("#")), List.of(".ex", ".exs"), "Elixir"),
    CLOJURE(List.of(new Marker(";")), List.of(".clj", ".cljs", ".cljc", ".edn"), "Clojure"),
    SCHEME(List.of(new Marker(";")), List.of(".scm"), "Scheme"),
    R(List.of(new Marker("#")), List.of(".r", ".R"), "R"),
    POWERSHELL(List.of(new Marker("#"), new Marker("<#", "#>")), List.of(".ps1", ".psm1", ".psd1"), "PowerShell"),
    ZSH(List.of(new Marker("#")), List.of(".zsh"), "Z Shell"),
    FISH(List.of(new Marker("#")), List.of(".fish"), "Fish Shell"),
    PROTOBUF(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".proto"), "Protocol Buffers"),
    GROOVY(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".groovy", ".gradle"), "Groovy"),
    JULIA(List.of(new Marker("#"), new Marker("#=", "=#")), List.of(".jl"), "Julia"),
    ZIG(List.of(new Marker("//")), List.of(".zig"), "Zig"),
    VUE(List.of(new Marker("<!--", "-->"), new Marker("//"), new Marker("/*", "*/")), List.of(".vue"), "Vue Single-File Component"),
    SVELTE(List.of(new Marker("<!--", "-->"), new Marker("//"), new Marker("/*", "*/")), List.of(".svelte"), "Svelte Component"),
    ERLANG(List.of(new Marker("%")), List.of(".erl", ".hrl"), "Erlang"),
    OCAML(List.of(new Marker("(*", "*)")), List.of(".ml", ".mli"), "OCaml"),
    FSHARP(List.of(new Marker("//"), new Marker("(*", "*)")), List.of(".fs", ".fsx", ".fsi"), "F#"),
    VISUALBASIC(List.of(new Marker("'")), List.of(".vb", ".bas"), "Visual Basic"),
    GRAPHQL(List.of(new Marker("#")), List.of(".graphql", ".gql"), "GraphQL"),
    FORTRANFREE(List.of(new Marker("!")), List.of(".f90", ".f95", ".f03", ".f08"), "Fortran (free-form)"),
    VIMSCRIPT(List.of(new Marker("\"")), List.of(".vim"), "Vim Script"),
    // 2026 additions: configuration and markup
    YAML(List.of(new Marker("#")), List.of(".yml", ".yaml"), "YAML"),
    TOML(List.of(new Marker("#")), List.of(".toml"), "TOML"),
    JSON(List.of(), List.of(".json"), "JSON"),
    JSONC(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".jsonc", ".json5"), "JSON with Comments"),
    MARKDOWN(List.of(new Marker("<!--", "-->")), List.of(".md", ".markdown"), "Markdown"),
    CSS(List.of(new Marker("/*", "*/")), List.of(".css"), "CSS"),
    SASS(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".scss", ".sass", ".less"), "Sass/SCSS/Less"),
    DOCKERFILE(List.of(new Marker("#")), List.of("Dockerfile", ".dockerfile"), "Dockerfile"),
    TERRAFORM(List.of(new Marker("#"), new Marker("//"), new Marker("/*", "*/")), List.of(".tf", ".tfvars", ".hcl"), "Terraform/HCL"),
    NIX(List.of(new Marker("#"), new Marker("/*", "*/")), List.of(".nix"), "Nix"),
    INI(List.of(new Marker("#"), new Marker(";")), List.of(".ini", ".cfg", ".conf", ".properties", ".desktop"), "INI/Config Files"),
    SVG(List.of(new Marker("<!--", "-->")), List.of(".svg"), "SVG Images"),
    ASCIIDOC(List.of(new Marker("//")), List.of(".adoc", ".asciidoc"), "AsciiDoc"),
    QML(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".qml"), "QML"),
    M4(List.of(new Marker("dnl"), new Marker("#")), List.of(".m4"), "M4 Macro Files"),
    DOCBOOK(List.of(new Marker("<!--", "-->")), List.of(".docbook"), "DocBook Documents"),
    MALLARD(List.of(new Marker("<!--", "-->")), List.of(".page"), "Mallard Help Pages"),
    POD(List.of(), List.of(".pod"), "Perl POD Documentation"),
    // binary
    WORD(List.of(".doc", ".docx"), "MS Word Documents"),
    JPEG(List.of(".jpeg", ".jpg"), "JPEG Images"),
    GIF(List.of(".gif"), "GIF Images"),
    PNG(List.of(".png"), "PNG Images"),
    JAR(List.of(".jar"), "JAR"),
    TIFF(List.of(".tiff", ".tif"), "TIFF Images"),
    PSD(List.of(".psd"), "PSD Photoshop Images"),
    ZIP(List.of(".zip"), "ZIP Archives"),
    PDF(List.of(".pdf"), "PDF Documents"),
    GZIP(List.of(".gz", ".gzip"), "GZIP Archives"),
    BZIP(List.of(".bz2", ".bz", ".bzip2", ".bzip"), "BZIP Archives"),
    WINHELP(List.of(".hlp", ".chm"), "Windows HELP Files"),
    RAR(List.of(".rar"), "RAR Archives"),
    DMG(List.of(".dmg", ".pkg"), "Mac OS X Installation Files"),
    EXCEL(List.of(".xls", ".xlsx"), "Excel Files"),
    POWERPOINT(List.of(".ppt", ".pps", ".pptx"), "Powerpoint Files"),
    TAR(List.of(".tar"), "TAR Archives"),
    TARGZ(List.of(".tgz", ".tar.gz"), "GZIPed TAR Archives"),
    TARBZ(List.of(".tar.bz2"), "BZIPed TAR Archives"),
    RPM(List.of(".rpm"), "RPM Linux Archives"),
    DEB(List.of(".deb"), "DEB Linux Archives"),
    ICO(List.of(".ico"), "ICO Images"),
    DLL(List.of(".dll"), "Win32 Dynamic Linked Library"),
    UNIXSO(List.of(".so"), "Unix Shared Object"),
    EXE(List.of(".exe"), "Win32 Executable"),
    WEBARCHIVE(List.of(".war"), "Java Web Application Archive"),
    WMF(List.of(".wmf"), "Windows Metafile"),
    PYC(List.of(".pyc", ".pyo", ".pyd"), "Python Compiled Unit"),
    MP3(List.of(".mp3"), "MP3 Audio File"),
    AVI(List.of(".avi"), "Audio-Video File"),
    DVI(List.of(".dvi"), "Device Independent File Format"),
    PS(List.of(".ps", ".eps"), "Postscript/Encapsulated Postscript File"),
    CLASS(List.of(".class"), "Java Compiled Class File"),
    OBJECTFILE(List.of(".o", ".obj"), "Object File"),
    BITMAP(List.of(".bmp"), "Bitmap File"),
    MP4(List.of(".mp4"), "MP4 Multimedia File"),
    // 2026 additions: images and design assets
    WEBP(List.of(".webp"), "WebP Images"),
    AVIF(List.of(".avif"), "AVIF Images"),
    HEIF(List.of(".heic", ".heif"), "HEIF/HEIC Images"),
    JPEGXL(List.of(".jxl"), "JPEG XL Images"),
    TARGA(List.of(".tga"), "Targa Images"),
    OPENEXR(List.of(".exr"), "OpenEXR Images"),
    DIRECTDRAW(List.of(".dds"), "DirectDraw Surface Images"),
    RAWPHOTO(List.of(".raw", ".cr2", ".nef", ".arw", ".dng"), "RAW Camera Images"),
    GIMP(List.of(".xcf"), "GIMP Images"),
    ILLUSTRATOR(List.of(".ai"), "Adobe Illustrator Documents"),
    DESIGNDOCUMENT(List.of(".sketch", ".fig"), "Design Tool Documents"),
    // 2026 additions: fonts
    TRUETYPE(List.of(".ttf", ".ttc"), "TrueType Fonts"),
    OPENTYPE(List.of(".otf"), "OpenType Fonts"),
    WEBFONT(List.of(".woff", ".woff2", ".eot"), "Web Fonts"),
    // 2026 additions: audio and video
    WAVE(List.of(".wav"), "WAVE Audio"),
    FLAC(List.of(".flac"), "FLAC Audio"),
    OGG(List.of(".ogg", ".oga", ".opus"), "Ogg Audio"),
    AAC(List.of(".aac", ".m4a"), "AAC Audio"),
    WMA(List.of(".wma"), "Windows Media Audio"),
    AIFF(List.of(".aiff", ".aif"), "AIFF Audio"),
    MIDI(List.of(".mid", ".midi"), "MIDI Files"),
    MATROSKA(List.of(".mkv", ".webm"), "Matroska/WebM Video"),
    QUICKTIME(List.of(".mov", ".m4v"), "QuickTime Video"),
    MPEGVIDEO(List.of(".mpg", ".mpeg", ".3gp"), "MPEG Video"),
    FLASHVIDEO(List.of(".flv"), "Flash Video"),
    WMV(List.of(".wmv"), "Windows Media Video"),
    // 2026 additions: archives and packages
    SEVENZIP(List.of(".7z"), "7-Zip Archives"),
    XZ(List.of(".xz", ".lzma"), "XZ Archives"),
    ZSTANDARD(List.of(".zst"), "Zstandard Archives"),
    TARXZ(List.of(".tar.xz"), "XZ-compressed TAR Archives"),
    TARZSTD(List.of(".tar.zst"), "Zstandard-compressed TAR Archives"),
    ANDROIDPACKAGE(List.of(".apk", ".aab"), "Android Packages"),
    IOSPACKAGE(List.of(".ipa"), "iOS Application Archives"),
    PYTHONPACKAGE(List.of(".whl", ".egg"), "Python Distribution Packages"),
    NUGET(List.of(".nupkg"), "NuGet Packages"),
    LINUXBUNDLE(List.of(".snap", ".flatpak", ".appimage"), "Linux Application Bundles"),
    WINDOWSINSTALLER(List.of(".msi", ".cab"), "Windows Installer Packages"),
    DISKIMAGE(List.of(".iso", ".img", ".vhd", ".qcow2"), "Disk Images"),
    BROWSEREXTENSION(List.of(".crx", ".xpi"), "Browser Extensions"),
    // 2026 additions: compiled artifacts
    JMOD(List.of(".jmod"), "Java Module File"),
    STATICLIBRARY(List.of(".a", ".lib"), "Static Libraries"),
    DEBUGSYMBOLS(List.of(".pdb"), "Debug Symbol Files"),
    MACHOLIBRARY(List.of(".dylib"), "Mach-O Dynamic Libraries"),
    KERNELMODULE(List.of(".ko"), "Linux Kernel Modules"),
    WEBASSEMBLY(List.of(".wasm"), "WebAssembly Binaries"),
    BEAM(List.of(".beam"), "Erlang BEAM Files"),
    RUSTLIBRARY(List.of(".rlib"), "Rust Libraries"),
    GETTEXT(List.of(".mo"), "Compiled Gettext Catalogs"),
    QTTRANSLATION(List.of(".qm"), "Compiled Qt Translations"),
    // 2026 additions: documents, data and models
    OPENDOCUMENT(List.of(".odt", ".ods", ".odp"), "OpenDocument Files"),
    EBOOK(List.of(".epub", ".mobi", ".azw3"), "E-Book Files"),
    DJVU(List.of(".djvu"), "DjVu Documents"),
    IWORK(List.of(".pages", ".numbers"), "Apple iWork Documents"),
    SQLITE(List.of(".sqlite", ".sqlite3"), "SQLite Databases"),
    DATABASE(List.of(".db", ".mdb", ".accdb"), "Database Files"),
    PARQUET(List.of(".parquet"), "Apache Parquet Files"),
    AVRO(List.of(".avro"), "Apache Avro Files"),
    HDF5(List.of(".h5", ".hdf5"), "HDF5 Data Files"),
    NUMPY(List.of(".npy", ".npz"), "NumPy Arrays"),
    PICKLE(List.of(".pkl"), "Python Pickle Files"),
    SAFETENSORS(List.of(".safetensors"), "Safetensors Model Files"),
    GGUF(List.of(".gguf"), "GGUF Model Files"),
    ONNX(List.of(".onnx"), "ONNX Models"),
    PYTORCH(List.of(".pt", ".pth", ".ckpt"), "PyTorch Checkpoints"),
    TENSORFLOW(List.of(".tflite", ".pb"), "TensorFlow Models"),
    BINARYKEYSTORE(List.of(".p12", ".pfx", ".jks", ".keystore", ".der"), "Binary Keystores and Certificates"),
    BINARYDATA(List.of(".bin", ".dat", ".pak"), "Binary Data Files"),
    // Other
    OTHER(List.of(), "Other");

    private final List<Marker> markers;
    private final List<String> extensions;
    private final String name;
    private final boolean text;

    /** Declares a binary resource: files are counted, lines are not. */
    Resource(List<String> extensions, String name) {
        this(List.of(), extensions, name, false);
    }

    /** Declares a text resource, counted with the given comment markers. */
    Resource(List<Marker> markers, List<String> extensions, String name) {
        this(markers, extensions, name, true);
    }

    Resource(List<Marker> markers, List<String> extensions, String name, boolean text) {
        this.markers = List.copyOf(markers);
        this.extensions = List.copyOf(extensions);
        this.name = name;
        this.text = text;
    }

    public List<Marker> commentMarkers() {
        return markers;
    }

    public List<String> extensions() {
        return extensions;
    }

    public String displayName() {
        return name;
    }

    public boolean isText() {
        return text;
    }

    public boolean isBinary() {
        return !text;
    }

    @Override
    public String toString() {
        return extensions.stream()
                         .map(extension -> extension + ", ")
                         .collect(joining("", name + " ( ", ")"));
    }

    /** Whether {@code fileName} carries one of this resource's suffixes. Case sensitive: {@code .C} is C++, {@code .c} is C. */
    public boolean matches(String fileName) {
        return extensions.stream().anyMatch(fileName::endsWith);
    }

    /**
     * The resource owning the longest suffix of {@code fileName}, or {@link #OTHER}
     * when nothing matches. Longest wins so that a specific suffix beats a shorter
     * one it ends with: {@code .tar.gz} is a GZIPed TAR archive rather than a plain
     * GZIP archive, and {@code build.xml} is an ANT build file rather than XML.
     * Equal-length matches fall to whichever constant is declared first.
     */
    public static Resource detect(String fileName) {
        Resource detected = OTHER;
        int matched = 0;

        for (Resource resource : values()) {
            if (resource == OTHER) { continue; }

            for (String extension : resource.extensions) {
                if (extension.length() > matched && fileName.endsWith(extension)) {
                    detected = resource;
                    matched = extension.length();
                }
            }
        }

        return detected;
    }
}
