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

Typical usage involves the execution of the following command:

java -jar jsloccount.jar <directory>

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
===================

* ANT Build File ( *build.xml*, )
* make ( *Makefile*, *makefile*, *GNUmakefile*, *.inc*, *.mk*, )
* Haskell ( *.hs*, )
* Java ( *.java*, )
* C ( *.c*, )
* C++ ( *.C*, *.cpp*, *.cxx*, *.cc*, )
* C/C++/SWIFT/Objective-c Headers ( *.h*, *.hxx*, *.H*, *.hpp*, *.hh*, )
* Pascal ( *.p*, *.pas*, )
* Bourne Shell ( *.sh*, *.bash*, *.ksh*, )
* C# ( *.cs*, )
* XML ( *.xml*, )
* HTML ( *.htm*, *.html*, )
* BiBTeX ( *.bib*, )
* TeX/LaTeX ( *.tex*, *.cls*, )
* Perl ( *.pl*, *.pm*, )
* awk ( *.awk*, )
* Objective-C ( *.m*, )
* PHP ( *.php*, *.php3*, *.php4*, )
* XSL/XSLT ( *.xsl*, *.xslt*, )
* MS-Dos/Windows Batch Files ( *.bat*, *.cmd*, )
* X-Schema Files ( *.xsd*, *.xs*, )
* Document Type Definition Files ( *.dtd*, *.mod*, )
* SQL ( *.sql*, )
* Gnuplot ( *.plot*, *.gnuplot*, )
* SED ( *.sed*, )
* ASCII Text Files ( *.txt*, *.text*, )
* Visual Studio Project File ( *.vcproj*, )
* Visual Studio C# Project File ( *.csproj*, )
* RDF ( *.rdf*, )
* WSDL ( *.wsdl*, )
* Maven POM File ( *.pom*, )
* JSP ( *.jsp*, )
* Javascript ( *.js*, *.mjs*, *.cjs*, *.jsx*, )
* C-Shell ( *.csh*, )
* RTF ( *.rtf*, )
* Python ( *.py*, )
* JavaCC Grammar Files ( *.jj*, )
* Ruby ( *.rb*, )
* TCL ( *.tcl*, )
* TCL/Tk ( *.tk*, )
* D ( *.d*, )
* CMake ( *.cmake*, *CMakeLists.txt*, )
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
* Protocol Buffers ( *.proto*, )
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
* YAML ( *.yml*, *.yaml*, )
* TOML ( *.toml*, )
* JSON ( *.json*, )
* JSON with Comments ( *.jsonc*, *.json5*, )
* Markdown ( *.md*, *.markdown*, )
* CSS ( *.css*, )
* Sass/SCSS/Less ( *.scss*, *.sass*, *.less*, )
* Dockerfile ( *Dockerfile*, *.dockerfile*, )
* Terraform/HCL ( *.tf*, *.tfvars*, *.hcl*, )
* Nix ( *.nix*, )
* INI/Config Files ( *.ini*, *.cfg*, *.conf*, *.properties*, *.desktop*, )
* SVG Images ( *.svg*, )
* AsciiDoc ( *.adoc*, *.asciidoc*, )
* QML ( *.qml*, )
* M4 Macro Files ( *.m4*, )
* DocBook Documents ( *.docbook*, )
* Mallard Help Pages ( *.page*, )
* Perl POD Documentation ( *.pod*, )
* MS Word Documents ( *.doc*, *.docx*, )
* JPEG Images ( *.jpeg*, *.jpg*, )
* GIF Images ( *.gif*, )
* PNG Images ( *.png*, )
* JAR ( *.jar*, )
* TIFF Images ( *.tiff*, *.tif*, )
* PSD Photoshop Images ( *.psd*, )
* ZIP Archives ( *.zip*, )
* PDF Documents ( *.pdf*, )
* GZIP Archives ( *.gz*, *.gzip*, )
* BZIP Archives ( *.bz2*, *.bz*, *.bzip2*, *.bzip*, )
* Windows HELP Files ( *.hlp*, *.chm*, )
* RAR Archives ( *.rar*, )
* Mac OS X Installation Files ( *.dmg*, *.pkg*, )
* Excel Files ( *.xls*, *.xlsx*, )
* Powerpoint Files ( *.ppt*, *.pps*, *.pptx*, )
* TAR Archives ( *.tar*, )
* GZIPed TAR Archives ( *.tgz*, *.tar.gz*, )
* BZIPed TAR Archives ( *.tar.bz2*, )
* RPM Linux Archives ( *.rpm*, )
* DEB Linux Archives ( *.deb*, )
* ICO Images ( *.ico*, )
* Win32 Dynamic Linked Library ( *.dll*, )
* Unix Shared Object ( *.so*, )
* Win32 Executable ( *.exe*, )
* Java Web Application Archive ( *.war*, )
* Windows Metafile ( *.wmf*, )
* Python Compiled Unit ( *.pyc*, *.pyo*, *.pyd*, )
* MP3 Audio File ( *.mp3*, )
* Audio-Video File ( *.avi*, )
* Device Independent File Format ( *.dvi*, )
* Postscript/Encapsulated Postscript File ( *.ps*, *.eps*, )
* Java Compiled Class File ( *.class*, )
* Object File ( *.o*, *.obj*, )
* Bitmap File ( *.bmp*, )
* MP4 Multimedia File ( *.mp4*, )
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
* Static Libraries ( *.a*, *.lib*, )
* Debug Symbol Files ( *.pdb*, )
* Mach-O Dynamic Libraries ( *.dylib*, )
* Linux Kernel Modules ( *.ko*, )
* WebAssembly Binaries ( *.wasm*, )
* Erlang BEAM Files ( *.beam*, )
* Rust Libraries ( *.rlib*, )
* Compiled Gettext Catalogs ( *.mo*, )
* Compiled Qt Translations ( *.qm*, )
* OpenDocument Files ( *.odt*, *.ods*, *.odp*, )
* E-Book Files ( *.epub*, *.mobi*, *.azw3*, )
* DjVu Documents ( *.djvu*, )
* Apple iWork Documents ( *.pages*, *.numbers*, )
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

License
=======
Copyright (c) 2013, Vassilios Karakoidas (vassilios.karakoidas@gmail.com) All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright
* Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
* The names of its contributors may not be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Vassilios Karakoidas BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

