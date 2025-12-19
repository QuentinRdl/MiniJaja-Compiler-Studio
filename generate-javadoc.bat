@echo off
setlocal enabledelayedexpansion

set JAVA_HOME=C:\Program Files\Java\jdk-22
set PATH=%JAVA_HOME%\bin;%PATH%

echo Generating Javadoc for complete project (including driver with JavaFX)...

rmdir /s /q docs 2>nul
mkdir docs

:: Build classpath from Maven repository
set M2_REPO=%USERPROFILE%\.m2\repository
set ANTLR_JAR=%M2_REPO%\org\antlr\antlr4-runtime\4.13.2\antlr4-runtime-4.13.2.jar
set COMMONS_IO_JAR=%M2_REPO%\commons-io\commons-io\2.19.0\commons-io-2.19.0.jar

:: JavaFX dependencies
set JAVAFX_BASE=%M2_REPO%\org\openjfx
set JAVAFX_CONTROLS=%JAVAFX_BASE%\javafx-controls\22.0.2\javafx-controls-22.0.2.jar
set JAVAFX_CONTROLS_WIN=%JAVAFX_BASE%\javafx-controls\22.0.2\javafx-controls-22.0.2-win.jar
set JAVAFX_FXML=%JAVAFX_BASE%\javafx-fxml\22.0.2\javafx-fxml-22.0.2.jar
set JAVAFX_FXML_WIN=%JAVAFX_BASE%\javafx-fxml\22.0.2\javafx-fxml-22.0.2-win.jar
set JAVAFX_GRAPHICS=%JAVAFX_BASE%\javafx-graphics\22.0.2\javafx-graphics-22.0.2.jar
set JAVAFX_GRAPHICS_WIN=%JAVAFX_BASE%\javafx-graphics\22.0.2\javafx-graphics-22.0.2-win.jar
set JAVAFX_BASE_JAR=%JAVAFX_BASE%\javafx-base\22.0.2\javafx-base-22.0.2.jar
set JAVAFX_BASE_WIN=%JAVAFX_BASE%\javafx-base\22.0.2\javafx-base-22.0.2-win.jar

:: Ikonli dependencies
set IKONLI_CORE=%M2_REPO%\org\kordamp\ikonli\ikonli-core\12.2.0\ikonli-core-12.2.0.jar
set IKONLI_JAVAFX=%M2_REPO%\org\kordamp\ikonli\ikonli-javafx\12.2.0\ikonli-javafx-12.2.0.jar
set IKONLI_FA5=%M2_REPO%\org\kordamp\ikonli\ikonli-fontawesome5-pack\12.2.0\ikonli-fontawesome5-pack-12.2.0.jar

:: RichTextFX
set RICHTEXTFX=%M2_REPO%\org\fxmisc\richtext\richtextfx\0.11.2\richtextfx-0.11.2.jar
set FLOWLESS=%M2_REPO%\org\fxmisc\flowless\flowless\0.7.0\flowless-0.7.0.jar
set UNDOFX=%M2_REPO%\org\fxmisc\undo\undofx\2.1.1\undofx-2.1.1.jar
set WELLBEHAVEDFX=%M2_REPO%\org\fxmisc\wellbehaved\wellbehavedfx\0.3.3\wellbehavedfx-0.3.3.jar
set REACTFX=%M2_REPO%\org\reactfx\reactfx\2.0-M5\reactfx-2.0-M5.jar

:: Complete classpath
set CLASSPATH=%ANTLR_JAR%;%COMMONS_IO_JAR%;%JAVAFX_CONTROLS%;%JAVAFX_CONTROLS_WIN%;%JAVAFX_FXML%;%JAVAFX_FXML_WIN%;%JAVAFX_GRAPHICS%;%JAVAFX_GRAPHICS_WIN%;%JAVAFX_BASE_JAR%;%JAVAFX_BASE_WIN%;%IKONLI_CORE%;%IKONLI_JAVAFX%;%IKONLI_FA5%;%RICHTEXTFX%;%FLOWLESS%;%UNDOFX%;%WELLBEHAVEDFX%;%REACTFX%

:: Get all Java files (excluding module-info.java) - now including driver
dir /s /b memory\src\main\java\*.java ast\src\main\java\*.java ast\target\generated-sources\antlr4\*.java compiler\src\main\java\*.java interpreter\src\main\java\*.java driver\src\main\java\*.java 2>nul | findstr /v "module-info.java" > javadoc_files.txt

"%JAVA_HOME%\bin\javadoc.exe" ^
    -d docs ^
    -classpath "%CLASSPATH%" ^
    -encoding UTF-8 ^
    -charset UTF-8 ^
    -Xdoclint:none ^
    --ignore-source-errors ^
    -windowtitle "Groupe 5 - Projet Compilation" ^
    -doctitle "Documentation du Projet de Compilation - Groupe 5" ^
    @javadoc_files.txt

del javadoc_files.txt 2>nul

if exist docs\index.html (
    echo.
    echo Javadoc generated successfully!
    echo Open docs\index.html in your browser.
) else (
    echo.
    echo Failed to generate Javadoc
)

endlocal

