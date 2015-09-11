package lemmings;

import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import lemmings.math.Vector2D;
import lemmings.physics.RigidBody;

public class Lemming extends RigidBody implements Configuration {
    
    private static final AnimatedImage staticImageRightDirection;
    private static final AnimatedImage staticImageLeftDirection;
    
    static {
        Image[] rightDirectionImageArray = new Image[4];
        Image[] leftDirectionImageArray = new Image[4];
        for (int i = 1; i <= rightDirectionImageArray.length; i++) {
            rightDirectionImageArray[i - 1] = new Image(String.format(IMAGE_FILENAME_LEMMING, i, ""));
            leftDirectionImageArray[i - 1] = new Image(String.format(IMAGE_FILENAME_LEMMING, i, "_i"));
        }
        staticImageRightDirection = new AnimatedImage(rightDirectionImageArray, 0.100);
        staticImageLeftDirection = new AnimatedImage(leftDirectionImageArray, 0.100);
    }
    private double width;
    private double height;
    
    private AnimatedImage image;

    public Lemming(double x, double y, double width, double height) {
        super(new Vector2D(x, y), new Vector2D(0, 0));
        this.width = width;
        this.height = height;
        image = staticImageRightDirection;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    
    public void render(GraphicsContext gc, double elapsedTime) {
        gc.drawImage(image.getFrame(elapsedTime), position.getX(), position.getY());
    }
    
    @Override
    public void move(double timeTick) {
        super.move(timeTick);
        // TODO
    }
    
    public void resolveCollisions(LinkedList<Shape> obstacles) {
        for (Shape s: obstacles) {
            if (s instanceof Rectangle) {
                Rectangle r = (Rectangle)s;
                if (position.getX() < r.getX() + r.getWidth()
                        && position.getX() + width > r.getX()
                        && position.getY() < r.getY() + r.getHeight()
                        && position.getY() + height > r.getY()) {
                    position.setY(r.getY() - height);
                    break;
                }
            }
        }
    }
    
}