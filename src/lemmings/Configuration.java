package lemmings;

import lemmings.math.Vector2D;

public interface Configuration {
    
    public static final double CANVAS_WIDTH = 800;
    public static final double CANVAS_HEIGHT = 600;
    public static final String BACKGROUND_COLOR_HEX = "#000033";
    public static final double LEMMING_HEIGHT = 20;
    public static final double LEMMING_WIDTH = 10;
    public static final double BOTTOM_HEIGHT = 400;
    public static final String IMAGE_FILENAME_LEMMING = "/lemming_%02d%s.png";
    public static final Vector2D GRAVITY = new Vector2D(0, 200);
    
}