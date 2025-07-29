# JavaFX Snake Game

A modern Snake game built with JavaFX featuring smooth gameplay, elegant graphics, and intuitive controls.

## Features

- **Smooth Gameplay**: Comfortable movement speed with responsive controls
- **Beautiful Graphics**: Green gradient snake with glowing red food
- **Modern UI**: Dark theme with clean, professional interface
- **WASD Controls**: Use W, A, S, D keys to control the snake
- **Score Tracking**: Real-time score display
- **Game Over/Restart**: Press SPACE to restart after game over
- **Collision Detection**: Smart boundary and self-collision detection
- **Visual Effects**: Subtle grid, glow effects, and gradient colors

## Controls

- **W**: Move Up
- **A**: Move Left  
- **S**: Move Down
- **D**: Move Right
- **SPACE**: Restart game (when game over)

## Requirements

### System Requirements
- Java 11 or higher (recommended: Java 17+)
- JavaFX SDK 17+
- Operating System: Windows, macOS, or Linux

### Installation Instructions

#### Ubuntu/Debian:
```bash
sudo apt update
sudo apt install openjdk-17-jdk openjfx
```

#### CentOS/RHEL/Fedora:
```bash
sudo dnf install java-17-openjdk-devel openjfx
```

#### Arch Linux:
```bash
sudo pacman -S jdk17-openjdk java-openjfx
```

#### macOS (using Homebrew):
```bash
brew install openjdk@17
brew install openjfx
```

#### Windows:
1. Download and install OpenJDK 17+ from [Adoptium](https://adoptium.net/)
2. Download JavaFX SDK from [OpenJFX](https://openjfx.io/)

## How to Compile and Run

### Quick Start (Recommended)

```bash
# Make scripts executable
chmod +x compile_snake.sh run_snake.sh

# Compile the game
./compile_snake.sh

# Run the game
./run_snake.sh
```

### Method 1: Using System JavaFX (Ubuntu/Debian)

```bash
# Compile the game
javac -cp "/usr/share/java/*" SnakeGame.java

# Run the game
java -cp "/usr/share/java/*:." SnakeGame
```

### Method 2: Simple Compilation (if JavaFX is in system path)

```bash
# Compile the game
javac SnakeGame.java

# Run the game
java SnakeGame
```

### Method 3: With JavaFX Module Path (if JavaFX SDK downloaded separately)

```bash
# Compile with module path
javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml SnakeGame.java

# Run with module path
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml SnakeGame
```


##  Game Design

- **Snake**: Beautiful green gradient with animated head featuring eyes
- **Food**: Bright red with subtle glow effect
- **Background**: Dark theme with subtle grid lines
- **UI**: Modern interface with score display and instructions

##  Code Structure

The game is built using clean, object-oriented Java principles:

- **Main Application**: Extends JavaFX Application class
- **Game Loop**: Uses AnimationTimer for smooth 60fps gameplay
- **Modular Design**: Separate classes for BodyPart and Food
- **Event Handling**: Keyboard input handling for WASD controls
- **Graphics**: Canvas-based rendering with GraphicsContext

##  Troubleshooting

### Issues I faced:

1. **"JavaFX runtime components are missing"**
   - Install JavaFX SDK or use the module path method
   - Ensure JavaFX is properly installed on your system

2. **"Module javafx.controls not found"**
   - Use the --module-path and --add-modules flags
   - Check that JavaFX SDK path is correct

3. **Game window doesn't appear**
   - Check that you have a display environment (not running headless)
   - Ensure JavaFX is compatible with your Java version
   - On remote servers, you may need X11 forwarding: `ssh -X username@server`

4. **"Graphics Device initialization failed"**
   - This is normal in headless environments (servers without GUI)
   - The game needs a graphical environment to run
   - Try running on a local machine with a desktop environment

##  Gameplay Tips

- Start with small movements to get used to the controls
- Plan your path to avoid trapping yourself
- The snake moves at a comfortable pace - no need to rush!
- Try to create patterns to maximize your score
- Use the edges strategically but be careful not to hit them



**Enjoy playing the Snake Game! 🐍**
