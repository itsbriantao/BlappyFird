package blappy_fird;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// An abstract class representing a game obstacle.
public abstract class Obstacle {

	private Image img;
	private int x, y, w, h, scrollSpeed;
	
	public Obstacle(int x, int y, int w, int h, String imagePath, int scrollSpeed) {
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.scrollSpeed = scrollSpeed;
		
		try {
			img = ImageIO.read(new File(imagePath));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// Obstacles drift from right to left of the screen
	// This method is repeatedly called by the GameRunner's actionPerformed() method
	public void drift() {
		x -= scrollSpeed;
	}
	
	public boolean offscreen() {
		return x+w < 0;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Image getImage() {
		return img;
	}
	
	// Get the bounds of the obstacle represented by a Rectangle
	// Use 2/3s of its size to be more lenient to the player
	public Rectangle getBounds() {
		return new Rectangle(x+w/3, y+h/3, w*2/3, h*2/3);
	}
}
