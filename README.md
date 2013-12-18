JSLoCCount - Java Source Line of Code Counter Tool
==================================================

Calculate physical LoC (Lines of Code) and other code-related size metrics for several languages. In addition, JSLoCCount provides standard file statistics according to file type.

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

Supported Languages
===================

* ANT Build File ( *build.xml*, )
* make ( *Makefile*, *.inc*, )
* Java ( *.java*, )
* J% ( *.jmod*, )
* C ( *.c*, )
* C++ ( *.C*, *.cpp*, *.cxx*, *.cc*, )
* C/C++/Obj-C Headers ( *.h*, *.hxx*, *.H*, )
* Pascal ( *.p*, *.pas*, )
* Bourne Shell ( *.sh*, )
* C# ( *.cs*, )
* XML ( *.xml*, )
* HTML ( *.htm*, *.html*, )
* BiBTeX ( *.bib*, )
* TeX/LaTeX ( *.tex*, )
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
* Javascript ( *.js*, )
* C-Shell ( *.csh*, )
* RTF ( *.rtf*, )
* Python ( *.py*, )
* JavaCC Grammar Files ( *.jj*, )
* Ruby ( *.rb*, )
* TCL ( *.tcl*, )
* TCL/Tk ( *.tk*, )
* D ( *.d*, )
* CMake ( *.cmake*, )
* Scala ( *.scala*, )
* MS Word Documents ( *.doc*, *.docx*, )
* JPEG Images ( *.jpeg*, *.jpg*, )
* GIF Images ( *.gif*, )
* PNG Images ( *.png*, )
* JAR Archive ( *.jar*, )
* TIFF Images ( *.tiff*, *.tif*, )
* PSD Photoshop Images ( *.psd*, )
* ZIP Archives ( *.zip*, )
* PDF Documents ( *.pdf*, )
* GZIP Archives ( *.gz*, *.gzip*, )
* BZIP Archives ( *.bz2*, *.bz*, *.bzip2*, *.bzip*, )
* Windows HELP Files ( *.hlp*, *.chm*, )
* RAR Archives ( *.rar*, )
* Mac OS X Installation Files ( *.dmg*, *.pkg*, )
* Excel Files ( *.xls*, )
* Powerpoint Files ( *.ppt*, *.pps*, )
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
* Python Compiled Unit ( *.pyc*, )
* MP3 Audio File ( *.mp3*, )
* Audio-Video File ( *.avi*, )
* Device Independent File Format ( *.dvi*, )
* Postscript/Encapsulated Postscript File ( *.ps*, *.eps*, )
* Java Compiled Class File ( *.class*, )
* Object File ( *.o*, )
* Bitmap File ( *.bmp*, )
* MP4 Multimedia File ( *.mp4*, )

License
=======
Copyright (c) 2013, Vassilios Karakoidas (vassilios.karakoidas@gmail.com) All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright
* Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
* The names of its contributors may not be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Vassilios Karakoidas BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

