#!/bin/bash

# Snake Game Compilation Script
# This script compiles the JavaFX Snake Game

echo "🐍 Compiling Snake Game..."
echo "=========================="

# Check if JavaFX is available
if [ ! -d "/usr/share/java" ]; then
    echo "Error: JavaFX not found. Please install it first:"
    echo "sudo apt update && sudo apt install -y openjfx libopenjfx-java"
    exit 1
fi

# Compile the game
echo "Compiling SnakeGame.java..."
javac -cp "/usr/share/java/*" SnakeGame.java

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo "   Generated files:"
    ls -la *.class | sed 's/^/   /'
    echo ""
    echo "To run the game:"
    echo "   ./run_snake.sh"
    echo "   OR"
    echo "   java -cp \"/usr/share/java/*:.\" SnakeGame"
else
    echo "❌ Compilation failed!"
    exit 1
fi