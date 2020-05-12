package blappy_fird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
// A GameBoard that handles all the drawing and GUI stuff
public class GameBoard extends JPanel {
	
	public static final int WIDTH = 550, HEIGHT = 400;
	private final Color SKY_BLUE = new Color(0, 171, 255);
	private final Font scoreFont = new Font("Comic Sans MS", Font.BOLD, 18);
	private BirdCharacter c;
	private GameRunner gr;

	public GameBoard(BirdCharacter theChar, GameRunner theGame) {
		c = theChar;
		gr = theGame;
	}
	
	public int getHeight() { return HEIGHT; }
	public int getWidth() { return WIDTH; }
	
	// Draws the character
	private void drawCharacter(Graphics2D g2d) {
		g2d.drawImage(c.getImage(), Math.round(c.getX()), Math.round(c.getY()), this);
	}
	
	// Gets a list of obstacles from the GameRunner and draws each of them
	private void drawObstacles(Graphics2D g2d) {
		ArrayList<Obstacle> obs = gr.getObstacles(); 
		for (Obstacle o : obs)
			g2d.drawImage(o.getImage(), o.getX(), o.getY(), this);
	}
	
	// Draws the game's score and obstacles
	private void drawGame(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(scoreFont);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Score: "+gr.getScore(), 5, 20);    
		
		drawObstacles(g2d);
	}
	
	// Draws the initial pause screen 
	private void drawPauseScreen(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.BLACK);
		Font pauseFont = new Font("Candara", Font.BOLD, 48);
		g2d.setFont(pauseFont);
		g2d.drawString("BLAPPY FIRD", 110, 50);
		g2d.drawString("PAUSED", 170, 130);
        g2d.drawString("PRESS UP TO BEGIN", 50, 250);
	}
	
	@Override
	// Called every time repaint() is called on this GameBoard
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Draws the background
		g.setColor(SKY_BLUE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
		drawCharacter((Graphics2D)g);
		if (gr.started()) {
			drawGame(g);
		}
		else {
			drawPauseScreen(g);
		}
		Toolkit.getDefaultToolkit().sync();
	}
	
	// Adds buttons for changing characters
	public JPanel addCharacterButtons() {
		JButton blueBird = new JButton(BirdCharacter.blueBird);
		JButton pinkBird = new JButton(BirdCharacter.pinkBird);
		JButton brownBird = new JButton(BirdCharacter.brownBird);
		
		// Add button functionality: when a button is clicked, switch to that bird
		blueBird.addActionListener(event -> c.switchBird(BirdNames.BLUE));	
		pinkBird.addActionListener(event -> c.switchBird(BirdNames.PINK));		
		brownBird.addActionListener(event -> c.switchBird(BirdNames.BROWN));
		
		// Necessary so that the buttons don't draw focus away
		// from the GameRunner or else the KeyListener won't work
		blueBird.setRequestFocusEnabled(false);
		pinkBird.setRequestFocusEnabled(false);
		brownBird.setRequestFocusEnabled(false);
		
		// Add the buttons to a JPanel
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(blueBird);
		buttonPanel.add(pinkBird);
		buttonPanel.add(brownBird);
		buttonPanel.setRequestFocusEnabled(false);
		return buttonPanel;
	}
}