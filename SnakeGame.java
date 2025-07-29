import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGame extends Application {
    
    // Game constants
    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 600;
    private static final int UNIT_SIZE = 20;
    private static final int GAME_UNITS = (BOARD_WIDTH * BOARD_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final double GAME_SPEED = 150_000_000; // nanoseconds (comfortable pace)
    
    // Game variables
    private Canvas canvas;
    private GraphicsContext gc;
    private List<BodyPart> snake;
    private Food food;
    private Direction direction = Direction.RIGHT;
    private boolean running = false;
    private int score = 0;
    private Random random;
    private AnimationTimer gameTimer;
    private long lastUpdate = 0;
    private Label scoreLabel;
    private Label gameOverLabel;
    
    // Direction enum
    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    
    // Snake body part class
    static class BodyPart {
        int x, y;
        
        BodyPart(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    // Food class
    static class Food {
        int x, y;
        
        Food(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        initializeGame();
        setupUI(primaryStage);
        startGame();
    }
    
    private void initializeGame() {
        random = new Random();
        snake = new ArrayList<>();
        
        // Initialize snake with 3 body parts
        snake.add(new BodyPart(0, 0));
        snake.add(new BodyPart(UNIT_SIZE, 0));
        snake.add(new BodyPart(UNIT_SIZE * 2, 0));
        
        generateFood();
        running = true;
    }
    
    private void setupUI(Stage primaryStage) {
        // Create main layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #1a1a1a;");
        
        // Create header with score
        VBox header = new VBox();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #2d2d2d;");
        
        Label titleLabel = new Label("🐍 SNAKE GAME");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.LIGHTGREEN);
        
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        scoreLabel.setTextFill(Color.WHITE);
        
        gameOverLabel = new Label("");
        gameOverLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gameOverLabel.setTextFill(Color.RED);
        
        header.getChildren().addAll(titleLabel, scoreLabel, gameOverLabel);
        
        // Create game canvas
        canvas = new Canvas(BOARD_WIDTH, BOARD_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        
        // Create instructions panel
        VBox footer = new VBox();
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(10));
        footer.setStyle("-fx-background-color: #2d2d2d;");
        
        Label instructionsLabel = new Label("Use WASD to control the snake • Space to restart");
        instructionsLabel.setFont(Font.font("Arial", 14));
        instructionsLabel.setTextFill(Color.LIGHTGRAY);
        
        footer.getChildren().add(instructionsLabel);
        
        // Add components to root
        root.setTop(header);
        root.setCenter(canvas);
        root.setBottom(footer);
        
        // Create scene
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT + 140);
        scene.setFill(Color.BLACK);
        
        // Set up key controls
        scene.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            
            if (running) {
                switch (key) {
                    case W:
                        if (direction != Direction.DOWN) direction = Direction.UP;
                        break;
                    case S:
                        if (direction != Direction.UP) direction = Direction.DOWN;
                        break;
                    case A:
                        if (direction != Direction.RIGHT) direction = Direction.LEFT;
                        break;
                    case D:
                        if (direction != Direction.LEFT) direction = Direction.RIGHT;
                        break;
                }
            } else if (key == KeyCode.SPACE) {
                restartGame();
            }
        });
        
        // Configure stage
        primaryStage.setTitle("Snake Game - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
        
        // Request focus for key events
        canvas.setFocusTraversable(true);
        canvas.requestFocus();
    }
    
    private void startGame() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= GAME_SPEED) {
                    if (running) {
                        move();
                        checkFood();
                        checkCollisions();
                    }
                    draw();
                    lastUpdate = now;
                }
            }
        };
        gameTimer.start();
    }
    
    private void move() {
        // Create new head
        BodyPart newHead = new BodyPart(snake.get(snake.size() - 1).x, snake.get(snake.size() - 1).y);
        
        // Move head based on direction
        switch (direction) {
            case UP:
                newHead.y -= UNIT_SIZE;
                break;
            case DOWN:
                newHead.y += UNIT_SIZE;
                break;
            case LEFT:
                newHead.x -= UNIT_SIZE;
                break;
            case RIGHT:
                newHead.x += UNIT_SIZE;
                break;
        }
        
        // Add new head
        snake.add(newHead);
        
        // Check if food eaten
        if (newHead.x == food.x && newHead.y == food.y) {
            score++;
            scoreLabel.setText("Score: " + score);
            generateFood();
        } else {
            // Remove tail if no food eaten
            snake.remove(0);
        }
    }
    
    private void generateFood() {
        int x, y;
        boolean validPosition;
        
        do {
            validPosition = true;
            x = random.nextInt(BOARD_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            y = random.nextInt(BOARD_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            
            // Check if food spawns on snake
            for (BodyPart bodyPart : snake) {
                if (bodyPart.x == x && bodyPart.y == y) {
                    validPosition = false;
                    break;
                }
            }
        } while (!validPosition);
        
        food = new Food(x, y);
    }
    
    private void checkFood() {
        BodyPart head = snake.get(snake.size() - 1);
        if (head.x == food.x && head.y == food.y) {
            score++;
            scoreLabel.setText("Score: " + score);
            generateFood();
        }
    }
    
    private void checkCollisions() {
        BodyPart head = snake.get(snake.size() - 1);
        
        // Check if head collides with body
        for (int i = 0; i < snake.size() - 1; i++) {
            if (head.x == snake.get(i).x && head.y == snake.get(i).y) {
                running = false;
            }
        }
        
        // Check if head touches borders
        if (head.x < 0 || head.x >= BOARD_WIDTH || head.y < 0 || head.y >= BOARD_HEIGHT) {
            running = false;
        }
        
        if (!running) {
            gameOverLabel.setText("GAME OVER! Press SPACE to restart");
        }
    }
    
    private void draw() {
        // Clear canvas with dark background
        gc.setFill(Color.rgb(20, 20, 20));
        gc.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        
        // Draw grid lines (subtle)
        gc.setStroke(Color.rgb(40, 40, 40));
        gc.setLineWidth(0.5);
        for (int i = 0; i < BOARD_HEIGHT / UNIT_SIZE; i++) {
            gc.strokeLine(0, i * UNIT_SIZE, BOARD_WIDTH, i * UNIT_SIZE);
        }
        for (int i = 0; i < BOARD_WIDTH / UNIT_SIZE; i++) {
            gc.strokeLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, BOARD_HEIGHT);
        }
        
        // Draw food with glowing effect
        gc.setFill(Color.RED);
        gc.fillRoundRect(food.x + 2, food.y + 2, UNIT_SIZE - 4, UNIT_SIZE - 4, 6, 6);
        
        // Add food glow effect
        gc.setFill(Color.rgb(255, 100, 100, 0.3));
        gc.fillRoundRect(food.x, food.y, UNIT_SIZE, UNIT_SIZE, 8, 8);
        
        // Draw snake with gradient effect
        for (int i = 0; i < snake.size(); i++) {
            BodyPart bodyPart = snake.get(i);
            
            if (i == snake.size() - 1) {
                // Head - brighter green
                gc.setFill(Color.LIMEGREEN);
                gc.fillRoundRect(bodyPart.x + 1, bodyPart.y + 1, UNIT_SIZE - 2, UNIT_SIZE - 2, 8, 8);
                
                // Add head details (eyes)
                gc.setFill(Color.BLACK);
                gc.fillOval(bodyPart.x + 5, bodyPart.y + 5, 3, 3);
                gc.fillOval(bodyPart.x + UNIT_SIZE - 8, bodyPart.y + 5, 3, 3);
            } else {
                // Body - gradient from dark to light green
                double intensity = 0.4 + (0.4 * i / snake.size());
                gc.setFill(Color.rgb(0, (int)(155 * intensity), 0));
                gc.fillRoundRect(bodyPart.x + 1, bodyPart.y + 1, UNIT_SIZE - 2, UNIT_SIZE - 2, 6, 6);
                
                // Add subtle highlight
                gc.setFill(Color.rgb(100, 255, 100, 0.2));
                gc.fillRoundRect(bodyPart.x + 2, bodyPart.y + 2, UNIT_SIZE - 4, UNIT_SIZE - 4, 4, 4);
            }
        }
    }
    
    private void restartGame() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        
        score = 0;
        scoreLabel.setText("Score: 0");
        gameOverLabel.setText("");
        direction = Direction.RIGHT;
        
        initializeGame();
        startGame();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}