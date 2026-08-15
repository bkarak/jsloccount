JSLoCCount - Java Source Line of Code Counter Tool
==================================================

Calculate physical LoC (Lines of Code) and other code-related size metrics for several languages. In addition, JSLoCCount provides standard file statistics according to file type.

Requirements
============

Java 21 or newer. Building from source additionally needs Apache Ant:

<pre>
ant release
</pre>

Usage
=====

<pre>
java -jar jsloccount.jar [options] &lt;directory&gt;
</pre>

Options:

<pre>
  -o, --output &lt;dir&gt;    write the reports into &lt;dir&gt; (default: the working directory)
  -n, --name &lt;name&gt;     base name for the report files (default: the scanned directory)
      --stdout          write one combined report to standard output instead of files
  -x, --exclude &lt;name&gt;  skip files and directories called &lt;name&gt;; repeatable
      --include-hidden  scan hidden files and directories too
  -q, --quiet           suppress progress messages
      --list-languages  list every recognized file type and exit
  -h, --help            show this help and exit
  -V, --version         show the version and exit
</pre>

Two reports are written, <i>&lt;name&gt;-filestats.csv</i> and <i>&lt;name&gt;-sizestats.csv</i>, both
RFC 4180 CSV. Progress messages go to standard error, so <i>--stdout</i> can be piped:

<pre>
java -jar jsloccount.jar --stdout --exclude node_modules ~/src/project | column -s, -t
</pre>

The exit status is 0 on success, 1 when the directory cannot be scanned, and 2 for a
usage error.

Eat Your Own dogfood
====================

For example, a quick run of jsloccount on its own source directory will produce the following results:

<pre>
Number of Files:

Java Compiled Class File, 14 / 30
Java, 11 / 30
JAR, 1 / 30
ANT Build File, 1 / 30
Other, 3 / 30

Number of Lines (comments):

Java, 544 (89)
ANT Build File, 24 (2)
</pre>

and two output files, one with the file statistics:

<pre>
Resource Type,File Count,Total File Count
Java Compiled Class File,14,30
Java,11,30
JAR,1,30
ANT Build File,1,30
</pre>

and one with the size metrics:

<pre>
Resource Type,Source Lines of Code,Comments Lines of Code
Java,544,89
ANT Build File,24,2
</pre>

Supported Languages & Files types
=================================

192 file types in ten groups. The first four groups hold the text types, counted for
source and comment lines and reported in both CSVs. The remaining groups are binary and
counted by file, so they appear in the file statistics only — the one exception being
SVG, which is XML and therefore line-counted despite sitting with the images.

Run <i>--list-languages</i> to print this same list from the tool itself.

Programming Languages
---------------------

* Haskell ( *.hs*, )
* Java ( *.java*, )
* C ( *.c*, )
* C++ ( *.C*, *.cpp*, *.cxx*, *.cc*, )
* C/C++/SWIFT/Objective-c Headers ( *.h*, *.hxx*, *.H*, *.hpp*, *.hh*, )
* Pascal ( *.p*, *.pas*, )
* Bourne Shell ( *.sh*, *.bash*, *.ksh*, )
* C# ( *.cs*, )
* Perl ( *.pl*, *.pm*, )
* awk ( *.awk*, )
* Objective-C ( *.m*, )
* PHP ( *.php*, *.php3*, *.php4*, )
* MS-Dos/Windows Batch Files ( *.bat*, *.cmd*, )
* SQL ( *.sql*, )
* Gnuplot ( *.plot*, *.gnuplot*, )
* SED ( *.sed*, )
* JSP ( *.jsp*, )
* Javascript ( *.js*, *.mjs*, *.cjs*, *.jsx*, )
* C-Shell ( *.csh*, )
* Python ( *.py*, )
* JavaCC Grammar Files ( *.jj*, )
* Ruby ( *.rb*, )
* TCL ( *.tcl*, )
* TCL/Tk ( *.tk*, )
* D ( *.d*, )
* Scala ( *.scala*, )
* Fortran (fixed-form) ( *.f*, *.for*, *.f77*, )
* Go ( *.go*, )
* Swift ( *.swift*, )
* TypeScript ( *.ts*, *.tsx*, *.mts*, *.cts*, )
* Rust ( *.rs*, )
* Kotlin ( *.kt*, *.kts*, )
* Lua ( *.lua*, )
* Dart ( *.dart*, )
* Elixir ( *.ex*, *.exs*, )
* Clojure ( *.clj*, *.cljs*, *.cljc*, *.edn*, )
* Scheme ( *.scm*, )
* R ( *.r*, *.R*, )
* PowerShell ( *.ps1*, *.psm1*, *.psd1*, )
* Z Shell ( *.zsh*, )
* Fish Shell ( *.fish*, )
* Groovy ( *.groovy*, *.gradle*, )
* Julia ( *.jl*, )
* Zig ( *.zig*, )
* Vue Single-File Component ( *.vue*, )
* Svelte Component ( *.svelte*, )
* Erlang ( *.erl*, *.hrl*, )
* OCaml ( *.ml*, *.mli*, )
* F# ( *.fs*, *.fsx*, *.fsi*, )
* Visual Basic ( *.vb*, *.bas*, )
* GraphQL ( *.graphql*, *.gql*, )
* Fortran (free-form) ( *.f90*, *.f95*, *.f03*, *.f08*, )
* Vim Script ( *.vim*, )
* QML ( *.qml*, )

Markup, Data & Configuration
----------------------------

* XML ( *.xml*, )
* HTML ( *.htm*, *.html*, )
* XSL/XSLT ( *.xsl*, *.xslt*, )
* X-Schema Files ( *.xsd*, *.xs*, )
* Document Type Definition Files ( *.dtd*, *.mod*, )
* RDF ( *.rdf*, )
* WSDL ( *.wsdl*, )
* Protocol Buffers ( *.proto*, )
* YAML ( *.yml*, *.yaml*, )
* TOML ( *.toml*, )
* JSON ( *.json*, )
* JSON with Comments ( *.jsonc*, *.json5*, )
* CSS ( *.css*, )
* Sass/SCSS/Less ( *.scss*, *.sass*, *.less*, )
* Terraform/HCL ( *.tf*, *.tfvars*, *.hcl*, )
* INI/Config Files ( *.ini*, *.cfg*, *.conf*, *.properties*, *.desktop*, )

Build & Project Files
---------------------

* ANT Build File ( *build.xml*, )
* make ( *Makefile*, *makefile*, *GNUmakefile*, *.inc*, *.mk*, )
* Visual Studio Project File ( *.vcproj*, )
* Visual Studio C# Project File ( *.csproj*, )
* Maven POM File ( *.pom*, )
* CMake ( *.cmake*, *CMakeLists.txt*, )
* Dockerfile ( *Dockerfile*, *.dockerfile*, )
* Nix ( *.nix*, )
* M4 Macro Files ( *.m4*, )

Documentation & Text
--------------------

* BiBTeX ( *.bib*, )
* TeX/LaTeX ( *.tex*, *.cls*, )
* ASCII Text Files ( *.txt*, *.text*, )
* RTF ( *.rtf*, )
* Markdown ( *.md*, *.markdown*, )
* AsciiDoc ( *.adoc*, *.asciidoc*, )
* DocBook Documents ( *.docbook*, )
* Mallard Help Pages ( *.page*, )
* Perl POD Documentation ( *.pod*, )

Images, Fonts & Design Assets
-----------------------------

* SVG Images ( *.svg*, )
* JPEG Images ( *.jpeg*, *.jpg*, )
* GIF Images ( *.gif*, )
* PNG Images ( *.png*, )
* TIFF Images ( *.tiff*, *.tif*, )
* PSD Photoshop Images ( *.psd*, )
* ICO Images ( *.ico*, )
* Windows Metafile ( *.wmf*, )
* Bitmap File ( *.bmp*, )
* WebP Images ( *.webp*, )
* AVIF Images ( *.avif*, )
* HEIF/HEIC Images ( *.heic*, *.heif*, )
* JPEG XL Images ( *.jxl*, )
* Targa Images ( *.tga*, )
* OpenEXR Images ( *.exr*, )
* DirectDraw Surface Images ( *.dds*, )
* RAW Camera Images ( *.raw*, *.cr2*, *.nef*, *.arw*, *.dng*, )
* GIMP Images ( *.xcf*, )
* Adobe Illustrator Documents ( *.ai*, )
* Design Tool Documents ( *.sketch*, *.fig*, )
* TrueType Fonts ( *.ttf*, *.ttc*, )
* OpenType Fonts ( *.otf*, )
* Web Fonts ( *.woff*, *.woff2*, *.eot*, )

Audio & Video
-------------

* MP3 Audio File ( *.mp3*, )
* Audio-Video File ( *.avi*, )
* MP4 Multimedia File ( *.mp4*, )
* WAVE Audio ( *.wav*, )
* FLAC Audio ( *.flac*, )
* Ogg Audio ( *.ogg*, *.oga*, *.opus*, )
* AAC Audio ( *.aac*, *.m4a*, )
* Windows Media Audio ( *.wma*, )
* AIFF Audio ( *.aiff*, *.aif*, )
* MIDI Files ( *.mid*, *.midi*, )
* Matroska/WebM Video ( *.mkv*, *.webm*, )
* QuickTime Video ( *.mov*, *.m4v*, )
* MPEG Video ( *.mpg*, *.mpeg*, *.3gp*, )
* Flash Video ( *.flv*, )
* Windows Media Video ( *.wmv*, )

Archives & Packages
-------------------

* JAR ( *.jar*, )
* ZIP Archives ( *.zip*, )
* GZIP Archives ( *.gz*, *.gzip*, )
* BZIP Archives ( *.bz2*, *.bz*, *.bzip2*, *.bzip*, )
* RAR Archives ( *.rar*, )
* Mac OS X Installation Files ( *.dmg*, *.pkg*, )
* TAR Archives ( *.tar*, )
* GZIPed TAR Archives ( *.tgz*, *.tar.gz*, )
* BZIPed TAR Archives ( *.tar.bz2*, )
* RPM Linux Archives ( *.rpm*, )
* DEB Linux Archives ( *.deb*, )
* Java Web Application Archive ( *.war*, )
* 7-Zip Archives ( *.7z*, )
* XZ Archives ( *.xz*, *.lzma*, )
* Zstandard Archives ( *.zst*, )
* XZ-compressed TAR Archives ( *.tar.xz*, )
* Zstandard-compressed TAR Archives ( *.tar.zst*, )
* Android Packages ( *.apk*, *.aab*, )
* iOS Application Archives ( *.ipa*, )
* Python Distribution Packages ( *.whl*, *.egg*, )
* NuGet Packages ( *.nupkg*, )
* Linux Application Bundles ( *.snap*, *.flatpak*, *.appimage*, )
* Windows Installer Packages ( *.msi*, *.cab*, )
* Disk Images ( *.iso*, *.img*, *.vhd*, *.qcow2*, )
* Browser Extensions ( *.crx*, *.xpi*, )
* Java Module File ( *.jmod*, )

Office Documents
----------------

* MS Word Documents ( *.doc*, *.docx*, )
* PDF Documents ( *.pdf*, )
* Windows HELP Files ( *.hlp*, *.chm*, )
* Excel Files ( *.xls*, *.xlsx*, )
* Powerpoint Files ( *.ppt*, *.pps*, *.pptx*, )
* Device Independent File Format ( *.dvi*, )
* Postscript/Encapsulated Postscript File ( *.ps*, *.eps*, )
* OpenDocument Files ( *.odt*, *.ods*, *.odp*, )
* E-Book Files ( *.epub*, *.mobi*, *.azw3*, )
* DjVu Documents ( *.djvu*, )
* Apple iWork Documents ( *.pages*, *.numbers*, )

Databases, Datasets & Models
----------------------------

* SQLite Databases ( *.sqlite*, *.sqlite3*, )
* Database Files ( *.db*, *.mdb*, *.accdb*, )
* Apache Parquet Files ( *.parquet*, )
* Apache Avro Files ( *.avro*, )
* HDF5 Data Files ( *.h5*, *.hdf5*, )
* NumPy Arrays ( *.npy*, *.npz*, )
* Python Pickle Files ( *.pkl*, )
* Safetensors Model Files ( *.safetensors*, )
* GGUF Model Files ( *.gguf*, )
* ONNX Models ( *.onnx*, )
* PyTorch Checkpoints ( *.pt*, *.pth*, *.ckpt*, )
* TensorFlow Models ( *.tflite*, *.pb*, )
* Binary Keystores and Certificates ( *.p12*, *.pfx*, *.jks*, *.keystore*, *.der*, )
* Binary Data Files ( *.bin*, *.dat*, *.pak*, )

Compiled Artifacts
------------------

* Win32 Dynamic Linked Library ( *.dll*, )
* Unix Shared Object ( *.so*, )
* Win32 Executable ( *.exe*, )
* Python Compiled Unit ( *.pyc*, *.pyo*, *.pyd*, )
* Java Compiled Class File ( *.class*, )
* Object File ( *.o*, *.obj*, )
* Static Libraries ( *.a*, *.lib*, )
* Debug Symbol Files ( *.pdb*, )
* Mach-O Dynamic Libraries ( *.dylib*, )
* Linux Kernel Modules ( *.ko*, )
* WebAssembly Binaries ( *.wasm*, )
* Erlang BEAM Files ( *.beam*, )
* Rust Libraries ( *.rlib*, )
* Compiled Gettext Catalogs ( *.mo*, )
* Compiled Qt Translations ( *.qm*, )

License
=======
Copyright (c) 2013, Vassilios Karakoidas (vassilios.karakoidas@gmail.com) All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright
* Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
* The names of its contributors may not be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Vassilios Karakoidas BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

