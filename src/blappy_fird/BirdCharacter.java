package blappy_fird;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

// A Bird that's a Character. Three flavors.
public class BirdCharacter extends Character {
	
	public static final ImageIcon blueBird = new ImageIcon("src/sprites/blue_falling.png"),
			pinkBird = new ImageIcon("src/sprites/pink_falling.png"),
			brownBird = new ImageIcon("src/sprites/brown_falling.png");
	
	public BirdCharacter() {
		super("src/sprites/blue_rising.png", "src/sprites/blue_falling.png");
	}
	
	// Used for switching between different BirdCharacter sprites.
	public void switchBird(BirdNames bn) {
		String rising = "", falling = "";
		if (bn == BirdNames.BLUE) {
			rising = "src/sprites/blue_rising.png";
			falling = "src/sprites/blue_falling.png";
		}
		else if (bn == BirdNames.PINK) {
			rising = "src/sprites/pink_rising.png";
			falling = "src/sprites/pink_falling.png";
		}
		else if (bn == BirdNames.BROWN) {
			rising = "src/sprites/brown_rising.png";
			falling = "src/sprites/brown_falling.png";
		}
		try {
			super.setSprites(ImageIO.read(new File(rising)), ImageIO.read(new File(falling)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
