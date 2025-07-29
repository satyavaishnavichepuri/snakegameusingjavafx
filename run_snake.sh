#!/bin/bash

# Snake Game Launcher Script
# This script runs the JavaFX Snake Game

echo "🐍 Starting Snake Game..."
echo "Controls: WASD to move, SPACE to restart"
echo "========================================="

# Run the game with JavaFX in the classpath
java -cp "/usr/share/java/*:." SnakeGame

echo "Thanks for playing Snake Game! 🐍"