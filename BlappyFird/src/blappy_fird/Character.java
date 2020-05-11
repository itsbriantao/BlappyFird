package blappy_fird;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// Models a Character.
public abstract class Character {
	
	public static final float START_X = GameBoard.WIDTH/4, START_Y = GameBoard.HEIGHT*3/8;
	private float dy, w, h, x, y;
	private Image fallingImg, risingImg, img;

	// Overloaded constructor to accept an Image of the character sprite
	public Character(Image rising, Image falling)
    {	
		setSprites(rising, falling);
		initialize();
		w = img.getWidth(null);
		h = img.getHeight(null);
    }
	
	// Overloaded constructor to accept a File of the character sprite
	public Character(File rising, File falling)
    {	
		try {
			setSprites(ImageIO.read(rising), ImageIO.read(falling));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initialize();
		w = img.getWidth(null);
		h = img.getHeight(null);
    }
	
	// Overloaded constructor to accept a String of the path of the character sprite
	public Character(String rising, String falling)
    {	
		try {
			setSprites(ImageIO.read(new File(rising)), ImageIO.read(new File(falling)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initialize();
		w = img.getWidth(null);
		h = img.getHeight(null);
		
    }
	
	// Initializes values. Also used for resetting after game ends.
	public void initialize() {
		dy = 0;
		x = START_X;
		y = START_Y;
		img = fallingImg;
	}
	
	// A Character has two sprites:
	// one for rising, and one for falling.
	public void setSprites(Image rising, Image falling) {
		risingImg = rising;
		img = fallingImg = falling;
	}
	
	// Use floats for greater precision in movement
	// Called continuously by actionPerformed() in GameRunner
	public void move() {
		float before = y;
		y += dy; // Move the character in y-axis
		if (before <= y) // Used to check if a character is falling
			img = fallingImg;
		dy += 0.5f; // Increase the falling speed to mimic gravity
	}
	
	// Character moves upward, is in rising status
	public void jump() {
		dy = -7;
		img = risingImg;
	}
	
	// Check if the character is off-screen (top or bottom)
	public boolean offscreen() {
		return y <= -(h*2/3) || y >= GameBoard.HEIGHT;
	}

	public float getWidth() {
		return w;
	}

	public float getHeight() {
		return h;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public Image getImage() {
		return img;
	}
	
	// Get the bounds of the Character as a Rectangle
	// Use 2/3 of the size to be more lenient to the player
	public Rectangle getBounds() {
		return new Rectangle((int)(x+w/3), (int)(y+h/3), (int)w*2/3, (int)h*2/3);
	}
}
