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
    MAKE(List.of(new Marker("#")), List.of("Makefile", ".inc"), "make"),
    // then the rest
    HASKELL(List.of(new Marker("--")), List.of(".hs"), "Haskell"),
    JMOD(List.of(), List.of(".jmod"), "J%"),
    JAVA(List.of(new Marker("//"), new Marker("/*", "*/"), new Marker("/**", "*/")), List.of(".java"), "Java"),
    C(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".c"), "C"),
    CPLUSPLUS(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".C", ".cpp", ".cxx", ".cc"), "C++"),
    HEADER(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".h", ".hxx", ".H"), "C/C++/SWIFT/Objective-c Headers"),
    PASCAL(List.of(new Marker("//"), new Marker("{", "}")), List.of(".p", ".pas"), "Pascal"),
    BOURNESHELL(List.of(new Marker("#")), List.of(".sh"), "Bourne Shell"),
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
    BAT(List.of(new Marker("rem ")), List.of(".bat", ".cmd"), "MS-Dos/Windows Batch Files"),
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
    JAVASCRIPT(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".js"), "Javascript"),
    CSHELL(List.of(new Marker("#")), List.of(".csh"), "C-Shell"),
    RTF(List.of(), List.of(".rtf"), "RTF"),
    PYTHON(List.of(new Marker("#")), List.of(".py"), "Python"),
    JAVACC(List.of(new Marker("//"), new Marker("/*", "*/"), new Marker("/**", "*/")), List.of(".jj"), "JavaCC Grammar Files"),
    RUBY(List.of(new Marker("#"), new Marker("=begin", "=end")), List.of(".rb"), "Ruby"),
    TCL(List.of(new Marker("#")), List.of(".tcl"), "TCL"),
    TCLTK(List.of(new Marker("#")), List.of(".tk"), "TCL/Tk"),
    D(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".d"), "D"),
    CMAKE(List.of(new Marker("#"), new Marker("//")), List.of(".cmake"), "CMake"),
    SCALA(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".scala"), "Scala"),
    FORTRAN(List.of(new Marker("!*")), List.of(".f"), "Fortran"),
    // 2017 additions
    GO(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".go"), "Go"),
    SWIFT(List.of(new Marker("//"), new Marker("/*", "*/")), List.of(".swift"), "Swift"),
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
    EXCEL(List.of(".xls"), "Excel Files"),
    POWERPOINT(List.of(".ppt", ".pps"), "Powerpoint Files"),
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
    PYC(List.of(".pyc"), "Python Compiled Unit"),
    MP3(List.of(".mp3"), "MP3 Audio File"),
    AVI(List.of(".avi"), "Audio-Video File"),
    DVI(List.of(".dvi"), "Device Independent File Format"),
    PS(List.of(".ps", ".eps"), "Postscript/Encapsulated Postscript File"),
    CLASS(List.of(".class"), "Java Compiled Class File"),
    OBJECTFILE(List.of(".o"), "Object File"),
    BITMAP(List.of(".bmp"), "Bitmap File"),
    MP4(List.of(".mp4"), "MP4 Multimedia File"),
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
