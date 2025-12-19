#!/bin/bash

# Script to generate Javadoc for the complete project (Mac/Linux compatible)

# Use Java 22 if available (Windows path)
if [ -f "/c/Program Files/Java/jdk-22/bin/javadoc" ]; then
    JAVADOC_CMD="/c/Program Files/Java/jdk-22/bin/javadoc"
else
    JAVADOC_CMD="javadoc"
fi

echo "Generating Javadoc for complete project (including driver with JavaFX)..."
echo "Using: $JAVADOC_CMD"

# Remove old docs and create new directory
rm -rf docs 2>/dev/null
mkdir -p docs

# Maven repository path
M2_REPO="${HOME}/.m2/repository"

# ANTLR and Commons IO
ANTLR_JAR="${M2_REPO}/org/antlr/antlr4-runtime/4.13.2/antlr4-runtime-4.13.2.jar"
COMMONS_IO_JAR="${M2_REPO}/commons-io/commons-io/2.19.0/commons-io-2.19.0.jar"

# JavaFX dependencies - detect OS for platform-specific jars
if [[ "$OSTYPE" == "darwin"* ]]; then
    PLATFORM="mac"
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
    PLATFORM="linux"
else
    PLATFORM="win"
fi

JAVAFX_BASE="${M2_REPO}/org/openjfx"
JAVAFX_CONTROLS="${JAVAFX_BASE}/javafx-controls/22.0.2/javafx-controls-22.0.2.jar"
JAVAFX_CONTROLS_PLATFORM="${JAVAFX_BASE}/javafx-controls/22.0.2/javafx-controls-22.0.2-${PLATFORM}.jar"
JAVAFX_FXML="${JAVAFX_BASE}/javafx-fxml/22.0.2/javafx-fxml-22.0.2.jar"
JAVAFX_FXML_PLATFORM="${JAVAFX_BASE}/javafx-fxml/22.0.2/javafx-fxml-22.0.2-${PLATFORM}.jar"
JAVAFX_GRAPHICS="${JAVAFX_BASE}/javafx-graphics/22.0.2/javafx-graphics-22.0.2.jar"
JAVAFX_GRAPHICS_PLATFORM="${JAVAFX_BASE}/javafx-graphics/22.0.2/javafx-graphics-22.0.2-${PLATFORM}.jar"
JAVAFX_BASE_JAR="${JAVAFX_BASE}/javafx-base/22.0.2/javafx-base-22.0.2.jar"
JAVAFX_BASE_PLATFORM="${JAVAFX_BASE}/javafx-base/22.0.2/javafx-base-22.0.2-${PLATFORM}.jar"

# Ikonli dependencies
IKONLI_CORE="${M2_REPO}/org/kordamp/ikonli/ikonli-core/12.2.0/ikonli-core-12.2.0.jar"
IKONLI_JAVAFX="${M2_REPO}/org/kordamp/ikonli/ikonli-javafx/12.2.0/ikonli-javafx-12.2.0.jar"
IKONLI_FA5="${M2_REPO}/org/kordamp/ikonli/ikonli-fontawesome5-pack/12.2.0/ikonli-fontawesome5-pack-12.2.0.jar"

# RichTextFX
RICHTEXTFX="${M2_REPO}/org/fxmisc/richtext/richtextfx/0.11.2/richtextfx-0.11.2.jar"
FLOWLESS="${M2_REPO}/org/fxmisc/flowless/flowless/0.7.0/flowless-0.7.0.jar"
UNDOFX="${M2_REPO}/org/fxmisc/undo/undofx/2.1.1/undofx-2.1.1.jar"
WELLBEHAVEDFX="${M2_REPO}/org/fxmisc/wellbehaved/wellbehavedfx/0.3.3/wellbehavedfx-0.3.3.jar"
REACTFX="${M2_REPO}/org/reactfx/reactfx/2.0-M5/reactfx-2.0-M5.jar"

# Complete classpath
CLASSPATH="${ANTLR_JAR}:${COMMONS_IO_JAR}:${JAVAFX_CONTROLS}:${JAVAFX_CONTROLS_PLATFORM}:${JAVAFX_FXML}:${JAVAFX_FXML_PLATFORM}:${JAVAFX_GRAPHICS}:${JAVAFX_GRAPHICS_PLATFORM}:${JAVAFX_BASE_JAR}:${JAVAFX_BASE_PLATFORM}:${IKONLI_CORE}:${IKONLI_JAVAFX}:${IKONLI_FA5}:${RICHTEXTFX}:${FLOWLESS}:${UNDOFX}:${WELLBEHAVEDFX}:${REACTFX}"

# Find all Java files (excluding module-info.java)
find memory/src/main/java ast/src/main/java ast/target/generated-sources/antlr4 compiler/src/main/java interpreter/src/main/java driver/src/main/java -name "*.java" 2>/dev/null | grep -v "module-info.java" > javadoc_files.txt

# Generate Javadoc
"$JAVADOC_CMD" \
    -d docs \
    -classpath "${CLASSPATH}" \
    -encoding UTF-8 \
    -charset UTF-8 \
    -Xdoclint:none \
    -windowtitle "Groupe 5 - Projet Compilation" \
    -doctitle "Documentation du Projet de Compilation - Groupe 5" \
    @javadoc_files.txt

# Cleanup
rm -f javadoc_files.txt

# Check result
if [ -f "docs/index.html" ]; then
    echo ""
    echo "Javadoc generated successfully!"
    echo "Open docs/index.html in your browser."

    # Auto-open on Mac
    if [[ "$OSTYPE" == "darwin"* ]]; then
        open docs/index.html
    fi
else
    echo ""
    echo "Failed to generate Javadoc"
    exit 1
fi
