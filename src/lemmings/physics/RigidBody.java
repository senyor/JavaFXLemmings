package lemmings.physics;
import lemmings.math.Vector2D;

public class RigidBody {

    protected Vector2D position;
    protected Vector2D lastPosition;
    protected Vector2D velocity;

    public RigidBody(Vector2D position, Vector2D velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getLastPosition() {
        return lastPosition;
    }

    public Vector2D getVelocity() {
        return velocity;
    }
    
    public void addVelocity(Vector2D v) {
        velocity.add(v);
    }
    
    public void move(double timeTick) {
        if (lastPosition == null) {
            lastPosition = new Vector2D(position);
        } else {
            lastPosition.setX(position.getX());
            lastPosition.setY(position.getY());
        }
        position.add(Vector2D.multiply(velocity, timeTick));
    }
    
}