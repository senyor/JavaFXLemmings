package lemmings;

import java.util.LinkedList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import lemmings.math.Vector2D;

public class Lemmings extends Application implements Configuration {
    
    private final int numberOfLemmings = 10;
    private LinkedList<Lemming> lemmings;
    
    private LinkedList<Shape> obstacles;
    
    private int lemmingsCreated = 0;
    private double lastLemmingCreated;
    
    private long lastNanoTime;
    
    private double spawnX = 195;
    private double spawnY = 20;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Canvas Example");
        
        Group root = new Group();
        Scene scene = new Scene(root);
        
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);        
        
        obstacles = new LinkedList<>();
        
        obstacles.add(new Rectangle(-50, -50, CANVAS_WIDTH + 100, 50));
        obstacles.add(new Rectangle(-50, 0, 50, CANVAS_HEIGHT));
        obstacles.add(new Rectangle(CANVAS_WIDTH, 0, 50, CANVAS_HEIGHT));
        obstacles.add(new Rectangle(0, BOTTOM_HEIGHT, CANVAS_WIDTH, CANVAS_HEIGHT - BOTTOM_HEIGHT));
        obstacles.add(new Rectangle(100, BOTTOM_HEIGHT - 200, 200, 200));
        obstacles.add(new Rectangle(500, BOTTOM_HEIGHT - 100, 200, 100));
        obstacles.stream().forEach((s) -> {
            s.setFill(Color.GRAY);
        });
        
        root.getChildren().add(new Rectangle(CANVAS_WIDTH, CANVAS_HEIGHT, new Color(0, 0, 0.1, 1)));
        root.getChildren().addAll(obstacles);
        root.getChildren().add(canvas);
                
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Image sun = new Image("sun.png");
        
        lemmings = new LinkedList<>();
        
        final long startNanoTime = System.nanoTime();
        
        lastNanoTime = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                
                // Time in seconds since last frame
                double timeTick = (currentNanoTime - lastNanoTime) / 1_000_000_000.0;
                lastNanoTime = currentNanoTime;
                // Time in seconds since game start
                double elapsedTime = (currentNanoTime - startNanoTime) / 1_000_000_000.0;
                
                if (lemmingsCreated < numberOfLemmings && lastLemmingCreated < elapsedTime - 1) {
                    Lemming temp = new Lemming(spawnX - LEMMING_WIDTH / 2, spawnY, LEMMING_WIDTH, LEMMING_HEIGHT);
                    lemmings.add(temp);
                    lastLemmingCreated = elapsedTime;
                    lemmingsCreated++;
                }

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                
                gc.setFill(Color.WHITE);
                gc.fillRect(spawnX - 50, spawnY, 100, 20);
                
                for (Lemming l: lemmings) {
                    l.render(gc, elapsedTime);
                    l.addVelocity(Vector2D.multiply(GRAVITY, timeTick));
                    l.move(timeTick);
                    l.resolveCollisions(obstacles);
                }
            }
        }.start();
        
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setWidth(CANVAS_WIDTH);
        primaryStage.setHeight(CANVAS_HEIGHT);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
