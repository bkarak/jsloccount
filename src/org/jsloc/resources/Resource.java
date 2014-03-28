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
package org.jsloc.resources;

import org.jsloc.resources.statistics.Marker;

/**
 * 
 * 
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 *
 */
public enum Resource {
    // text
    // first of all, put the files with full filenames
    ANT(new Marker[] { new Marker("<!--", "-->") }, new String[] { "build.xml" }, "ANT Build File"),
    MAKE(new Marker[] { new Marker("#") }, new String[] { "Makefile", ".inc" }, "make"),
    // then the rest
    JMOD(new Marker[] {}, new String[] { ".jmod" }, "J%"),
    JAVA(new Marker[] { new Marker("//"), new Marker("/*", "*/"),  new Marker("/**", "*/") }, new String[] { ".java" }, "Java" ),
    C(new Marker[] { new Marker("//"), new Marker("/*", "*/") }, new String[] { ".c" }, "C" ),
    CPLUSPLUS(new Marker[] { new Marker("//"), new Marker("/*", "*/") }, new String[] { ".C", ".cpp", ".cxx", ".cc" }, "C++"),
    HEADER(new Marker[] { new Marker("//"), new Marker("/*", "*/") }, new String[] { ".h", ".hxx", ".H" }, "C/C++ Headers" ),
    PASCAL(new Marker[] { new Marker("//"), new Marker("{", "}") }, new String[] { ".p", ".pas" }, "Pascal" ),
    BOURNESHELL(new Marker[] { new Marker("#") }, new String[] { ".sh" }, "Bourne Shell" ),
    CSHARP(new Marker[] { new Marker("//"), new Marker("/*", "*/") }, new String[] { ".cs" }, "C#" ),
    XML(new Marker[] { new Marker("<!--", "-->") }, new String[] { ".xml" }, "XML" ),
    HTML(new Marker[] { new Marker("<!--", "-->") }, new String[] { ".htm", ".html" }, "HTML" ),
    BIBTEX(new Marker[] { new Marker("%") }, new String[] { ".bib" }, "BiBTeX" ),
    TEX(new Marker[] { new Marker("%") }, new String[] { ".tex" }, "TeX/LaTeX" ),
    PERL(new Marker[] { new Marker("#") }, new String[] { ".pl", ".pm" }, "Perl"),
    AWK(new Marker[] { new Marker("#") }, new String[] { ".awk" }, "awk" ),
    OBJECTIVEC(new Marker[] { new Marker("//"), new Marker("/*", "*/") }, new String[] { ".m" }, "Objective-C" ),
    PHP(new Marker[] { new Marker("<!--", "-->"), new Marker("//"), new Marker("#"), new Marker("/*", "*/")}, new String[] { ".php", ".php3", ".php4" }, "PHP"),
    XSL(new Marker[] { new Marker("<!--", "-->") }, new String[] { ".xsl", ".xslt" }, "XSL/XSLT"),
    BAT(new Marker[] { new Marker("rem ") }, new String[] { ".bat", ".cmd" }, "MS-Dos/Windows Batch Files" ),
    XSD(new Marker[] { new Marker("<!--", "-->")}, new String[] { ".xsd", ".xs" }, "X-Schema Files"),
    DTD(new Marker[] { new Marker("<!--", "-->")}, new String[] { ".dtd", ".mod" }, "Document Type Definition Files" ),
    SQL(new Marker[] { new Marker("--"), new Marker("/*", "*/") }, new String[] { ".sql" }, "SQL" ),
    GNUPLOT(new Marker[] { new Marker("#"), }, new String[] { ".plot", ".gnuplot" }, "Gnuplot" ),
    SED(new Marker[] { new Marker("#") }, new String[] { ".sed" }, "SED" ),
    TEXT(new Marker[] {}, new String[] { ".txt", ".text"}, "ASCII Text Files"),
    VISUALSTUDIOPROJECT(new Marker[] { new Marker("<!--","-->") }, new String[] { ".vcproj" }, "Visual Studio Project File"),
    CSHARPPROJECT(new Marker[] { new Marker("<!--", "-->") }, new String[] { ".csproj" }, "Visual Studio C# Project File" ),
    RDF(new Marker[] { new Marker("<!--", "-->") }, new String[] { ".rdf" }, "RDF"),
    WSDL(new Marker[] { new Marker("<!--", "-->") }, new String[] { ".wsdl" }, "WSDL" ),
    POM(new Marker[] { new Marker("<!--", "-->") }, new String[] { ".pom" }, "Maven POM File"),
    JSP(new Marker[] { new Marker("<!--", "-->"), new Marker("//"), new Marker("/*", "*/"),  new Marker("/**", "*/") }, new String[] { ".jsp" }, "JSP" ),
    JAVASCRIPT(new Marker[] { new Marker("//"), new Marker("/*", "*/")}, new String[] { ".js" }, "Javascript" ),
    CSHELL(new Marker[] { new Marker("#") }, new String[] { ".csh" }, "C-Shell" ),
    RTF(new Marker[] {}, new String[] { ".rtf" }, "RTF"),
    PYTHON(new Marker[] { new Marker("#") }, new String[] { ".py" }, "Python"),
    JAVACC(new Marker[] { new Marker("//"), new Marker("/*", "*/"), new Marker("/**", "*/") }, new String[] { ".jj" }, "JavaCC Grammar Files"),
    RUBY(new Marker[] { new Marker("#"), new Marker("=begin", "=end") }, new String[] { ".rb" }, "Ruby"),
    TCL(new Marker[] { new Marker("#") }, new String[] { ".tcl" }, "TCL"),
    TCLTK(new Marker[] { new Marker("#") }, new String[] { ".tk" }, "TCL/Tk"),
    D(new Marker[] { new Marker("//"), new Marker("/*", "*/") }, new String[] { ".d" }, "D" ),
    CMAKE(new Marker[] { new Marker("#"), new Marker("//") }, new String[] { ".cmake" }, "CMake"),
    SCALA(new Marker[] { new Marker("//"), new Marker("/*", "*/") }, new String[] { ".scala" }, "Scala"),
    FORTRAN(new Marker[] { new Marker("!*") }, new String[] { ".f" }, "Fortran"),
    // binary
    WORD(new String[] { ".doc", ".docx" }, "MS Word Documents"),
    JPEG(new String[] { ".jpeg", ".jpg" }, "JPEG Images"),
    GIF(new String[] { ".gif" }, "GIF Images"),
    PNG(new String[] { ".png" }, "PNG Images"),
    JAR(new String[] { ".jar" }, "JAR"),
    TIFF(new String[] { ".tiff", ".tif" }, "TIFF Images"),
    PSD(new String[] { ".psd" }, "PSD Photoshop Images"),
    ZIP(new String[] { ".zip" }, "ZIP Archives"),
    PDF(new String[] { ".pdf" }, "PDF Documents"),
    GZIP(new String[] { ".gz", ".gzip" }, "GZIP Archives"),
    BZIP(new String[] { ".bz2", ".bz", ".bzip2", ".bzip"}, "BZIP Archives"),
    WINHELP(new String[] { ".hlp", ".chm" }, "Windows HELP Files"),
    RAR(new String[] { ".rar" }, "RAR Archives"),
    DMG(new String[] { ".dmg", ".pkg" }, "Mac OS X Installation Files"),
    EXCEL(new String[] { ".xls" }, "Excel Files"),
    POWERPOINT(new String[] { ".ppt", ".pps" }, "Powerpoint Files"),
    TAR(new String[] { ".tar" }, "TAR Archives"),
    TARGZ(new String[] { ".tgz", ".tar.gz" }, "GZIPed TAR Archives"),
    TARBZ(new String[] { ".tar.bz2" }, "BZIPed TAR Archives" ),
    RPM(new String[] { ".rpm" }, "RPM Linux Archives"),
    DEB(new String[] { ".deb" }, "DEB Linux Archives"),
    ICO(new String[] { ".ico" }, "ICO Images"),
    DLL(new String[] { ".dll" }, "Win32 Dynamic Linked Library"),
    UNIXSO(new String[] { ".so" }, "Unix Shared Object"),
    EXE(new String[] { ".exe"}, "Win32 Executable"),
    WEBARCHIVE(new String[] { ".war"}, "Java Web Application Archive"),
    WMF(new String[] { ".wmf" }, "Windows Metafile"),
    PYC(new String[] { ".pyc" }, "Python Compiled Unit"),
    MP3(new String[] { ".mp3" }, "MP3 Audio File"),
    AVI(new String[] { ".avi" }, "Audio-Video File"),
    DVI(new String[] { ".dvi" }, "Device Independent File Format"),
    PS(new String[] { ".ps", ".eps" }, "Postscript/Encapsulated Postscript File"),
    CLASS(new String[] { ".class" }, "Java Compiled Class File"),
    OBJECTFILE(new String[] { ".o" }, "Object File"),
    BITMAP(new String[] { ".bmp" }, "Bitmap File"),
    MP4(new String[] { ".mp4" }, "MP4 Multimedia File"),
    // Other
    OTHER(new String[] {}, "Other");
    
    private Marker[] markers;
    private String[] extensions;
    private String name;
    private boolean isText;
    
    private Resource(String[] extensions, String name) {
        this.markers = new Marker[] {};
        this.extensions = extensions;
        this.name = name;
        this.isText = false;
    }
    
    private Resource(Marker[] markers, String[] extensions, String name) {
        this.markers = markers;
        this.extensions = extensions;
        this.name = name;
        this.isText = true;
    }
    
    // simple getters
    
    public Marker[] getCommentMarkers() { return markers; }
    
    public String[] getExtensions() { return extensions; }
    
    public String getName() { return name; }
    
    public String toString() {
        StringBuilder r = new StringBuilder();
        
        r.append(name);
        r.append(" ( ");
        for ( String s : extensions ) {
            r.append(s);
            r.append(", ");
        }
        r.append(")");
        
        return r.toString(); 
    }
    
    public boolean isText() {
        return isText;
    }
    
    public boolean isBinary() {
        return !isText;
    }
    
    // more complex methods
    
    public boolean hasExtension(String fname) {
        for ( String s : extensions ) {
            if( fname.endsWith(s) ) { return true; }
        }
        
        return false;
    }
    
    public static Resource detect(String fname) {
        for ( Resource l : values() ) {
            if ( l == OTHER) { continue; }
            if ( l.hasExtension(fname) ) { return l; }
        }
        
        return OTHER;
    }
    
}
