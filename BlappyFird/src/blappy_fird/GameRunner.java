package blappy_fird;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
// A class for running the game itself.
public class GameRunner extends JFrame implements KeyListener, ActionListener {

	private static final int DELAY = 15;
	private static Timer timer;
	private BirdCharacter c;
	private boolean started;
	private ArrayList<Obstacle> obs;
	private static GameBoard gb;
	private static JPanel charButtons;
	private static JPanel leaderboardView;
	private int time, highScore, driftSpeed, frequency;
	private LeaderBoard leaderboard;
	
	public GameRunner() {
		initGame();	// Initialize the game
		
		// Add the GameBoard and character buttons to this JFrame
		add(gb);
		add(charButtons, BorderLayout.SOUTH);
	
		// Complete other setup
		addKeyListener(this);
		setFocusable(true);
		setVisible(true);
		setTitle(" Blappy Fird");
		setSize(GameBoard.WIDTH, GameBoard.HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// Helper method to initialize values for instance variables
	private void initGame() {
		frequency = 100;
		driftSpeed = 2;
		started = false;
		time = 0;
		highScore = 0;
		obs = new ArrayList<>();
		c = new BirdCharacter();
		gb = new GameBoard(c, this);
		timer = new Timer(DELAY, this);
		timer.start();
		charButtons = gb.addCharacterButtons();
	}
	
	// Marks the game as started
	public boolean started() {
		return started;
	}
	
	// Gets the score as a function of time
	public int getScore() {
		return time/10;
	}
	
	// Gets the list of obstacles
	public ArrayList<Obstacle> getObstacles() {
		return obs;
	}
	
	// Get Height of gameboard
	public int getHeight() { return gb.getHeight(); }
	public int getWidth() { return gb.getWidth(); }
	
	// Get Width of gameboard
	
	// Utility method to end the game
	private void endGame() {
		int score = getScore();
		leaderboard = new LeaderBoard(this);
		String name = JOptionPane.showInputDialog("Good run!\n"+"Score: " + score + "\nEnter you name: ");
		
		if (leaderboard.isNewRecord(score)){
            String player_name = name;
            leaderboard.addRecord(new Record(player_name, score, new Date()));    
        } 
		
		//leaderboardView.add("Leaderboard", leaderboard);
		//add(leaderboardView);
		setLocationRelativeTo(this);
		leaderboard.setVisible(true);
		
		//if (highScore < score) highScore = score; // Set the new high score
		
		//System.out.println(name);
		//JOptionPane.showMessageDialog(this, "High score: "+ highScore);
		gameReset();
	}
	
	// Helper method to reset the game
	public void gameReset() {
		started = false;
		c.initialize();
		time = 0;
		frequency = 100;
		obs.clear();
		driftSpeed = 2;
		charButtons.setVisible(true);
	}
	
	// Helper method to calculate Cloud height
	private int calcCloudHeight() {
		return (int)(Math.random()*(GameBoard.HEIGHT-Cloud.HEIGHT));
	}
	
	// Adds new Clouds just off-screen to the right
	private void addClouds() {
		// Use time to regularly add in clouds
		// Frequency is a variable so that it can be increased as time goes on
		if (time % frequency == 0) {
			int h1 = calcCloudHeight();
			Obstacle o1 = new Cloud(GameBoard.WIDTH, h1, driftSpeed);
			int h2 = calcCloudHeight();
			// Don't want overlapping clouds, so make sure the height is different
			while (!(h2 >= (h1+Cloud.HEIGHT) || h1 >= (h2+Cloud.HEIGHT)))
				h2 = calcCloudHeight();
			Obstacle o2 = new Cloud(GameBoard.WIDTH, h2, driftSpeed);
			obs.add(o1);
			obs.add(o2);
		}
	}
	
	// Determines whether the character made contact with an obstacle
	private boolean charContact(Obstacle obs) {
		return (c.getBounds().intersects(obs.getBounds()) || c.offscreen());
	}
		
	// Moves the obstacles across the screen, creates new ones, removes old ones
	private void moveObstacles() {
		ArrayList<Obstacle> removeObstacles = new ArrayList<>(); // A list of obstacles to removed
		
		// Make each of the Clouds drift across the screen
		for (int i = 0; i < obs.size(); ++i) {
			Cloud cloud = (Cloud)obs.get(i);
			cloud.drift();
			
			// Clouds that go off-screen are added to the removal list
			if(c.offscreen()) removeObstacles.add(cloud);
			
			if (charContact(cloud)) // Check if the Character touched a Cloud or went off-screen
				endGame();
		}
		obs.removeAll(removeObstacles); // Remove the off-screen obstacles
	}
	
	@Override
	// This method is called every DELAY ms by the timer.
    public void actionPerformed(ActionEvent e) {
		gb.repaint(); // Calls the paintComponent method of gb to update visual changes
		if (started) {	// If game hasn't started, don't do anything yet
			addClouds();
			c.move();
			moveObstacles();
			
			// Increase the drifting speed and spawn frequency 
			// of obstacles every 30 seconds
			// Math: time variable increments every 15 ms
			// -> 15 ms * 2000 = 30000 ms = 30 seconds
			if (++time % 2000 == 0) {
				driftSpeed++;
				frequency -= 10;
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// If the up arrow is pressed
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			c.jump();						// Makes the character jump
			started = true;					// Starts the game
			charButtons.setVisible(false); 	// Hides the character buttons
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Do nothing
		// Just here to satisfy the implemented KeyListener
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Do nothing
		// Just here to satisfy the implemented KeyListener
	} 
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			GameRunner gr = new GameRunner();
			gr.setVisible(true);
		});
	}
}
