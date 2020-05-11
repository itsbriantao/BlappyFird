package blappy_fird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

public class LeaderBoard extends JPanel {
	
	private static final String LEADERBOARD_IMAGE_LOCATION = "src/sprites/leaderboardBackground.png";
    //private static final String DEFAULT_SAVE_LOCATION = "src/scores.txt";
    private static final String fileName = "src/scores.txt";
       
    private ArrayList<Record> records;
    BufferedImage leaderboardBackground = null;
    //private Image leaderboardBackground;
    private JButton restartButton;
    private GameRunner game;
    //private JPanel leaderBoardPanel;
    
    public LeaderBoard(GameRunner game) {
        
        setLayout(null);
        setFocusable(true);
        this.game = game;
        
        copyRecords();		// copies prev records from file

        try {
        	leaderboardBackground = ImageIO.read(new File("src/sprites/leaderboardBackground.png"));
        } catch (IOException e) {
        	 System.err.println("Can't find reference: " + LEADERBOARD_IMAGE_LOCATION);
             System.exit(0);
        }
        
        // add reset button
        restartButton = new JButton("Return to Main Menu");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.gameReset();
            }
        });
        add(restartButton);
    }
    
    // Copies records from file
    private void copyRecords() {
        records = new ArrayList<Record>(10);
        try {
            File file = new File(fileName);
            Scanner scan = new Scanner(file);
            for (int i = 0; i < 10; i++) {
                String recordString = scan.nextLine();
                String inititals = recordString.substring(0, 3);
                int score = Integer.parseInt(recordString.substring(4));
                records.add(new Record(inititals, score, new Date()));
            }
            scan.close();
            
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } 
    }
    
    // Saves records to file
    private boolean saveToFile() {
        boolean saveSuccessful = true;
        FileOutputStream fileOutput = null;
        ObjectOutputStream objectOutput = null;
        try {
            fileOutput = new FileOutputStream(fileName);
            objectOutput = new ObjectOutputStream(fileOutput);
            objectOutput.writeObject(records);
  
        } catch (IOException e) {
            saveSuccessful = false;
        }
        return saveSuccessful;
    }
    
    // Add scores on top of background leaderboard image
    public void paintLeaders(Graphics g){
        // set up font
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        
        int startY = (int)(game.getHeight() * .325);
        int verticalSpacing = (int)(game.getHeight() * .0652);
        for (int i = 0; i < 10; i ++){
            int startX = (int)(game.getWidth() * .13);
            // print rank
            g.drawString(Integer.toString(i + 1), startX, startY);
            // print name
            startX += (int)(game.getWidth() * .13);
            g.drawString(records.get(i).getName(), startX, startY);
            // print score
            startX += (int)(game.getWidth() * .225);
            g.drawString(Integer.toString(records.get(i).getScore()), startX, startY);
            // print date
            startX += (int)(game.getWidth() * .225);
            g.drawString(DateFormat.getDateInstance(DateFormat.SHORT).format(records.get(i).getDate()), startX, startY);
            // move Y location
            startY += verticalSpacing;
        }
    }
    
//    // Load scores data from file
//    private boolean loadFromFile() {
//        boolean loadSuccessful = true;
//        File saveFile = new File(fileName);
//        if (saveFile.exists()) {
//            try {
//
//                // Read from disk using FileInputStream.
//                FileInputStream fileStream = new FileInputStream(saveFile);
//
//                // Read object using ObjectInputStream.
//                ObjectInputStream objectStream = new ObjectInputStream(fileStream);
//                Object obj = objectStream.readObject();
//
//                // Perform appropriate casting.
//                if (obj instanceof ArrayList) {
//                    records = (ArrayList<Record>) obj;
//                }
//
//                objectStream.close();
//                
//            } catch (IOException e) {
//                loadSuccessful = false;
//            } catch (ClassNotFoundException e) {
//                loadSuccessful = false;
//            }
//
//        } else {
//            loadSuccessful = false;
//        }
//        return loadSuccessful;
//    }
    
    
    
    // Player adds new score
    public boolean isNewRecord(int playerScore){
        // only need to check last element; records should always be full and sorted
        if (playerScore > records.get(records.size() - 1).getScore()){
            return true;
        }
        return false;
    }
    
    // Add the record onto leaderboard
    public void addRecord(Record newRecord){
        records.remove(9); // know it will kick #10 out
        int newRecordScore = newRecord.getScore();
        int oneBefore;
        for (oneBefore = 8; oneBefore >= 0; oneBefore--) { 
            if (records.get(oneBefore).getScore() >= newRecordScore){
                break; // quite looping; found place
            }
        }
        
        records.add(oneBefore+1, newRecord);
        saveToFile();
        
        for (int i = 0; i < 10; i++) {
        	System.out.println(records.get(i).getName());
        }
    }
    
    // Get records from file 
    public ArrayList<Record> getRecords() {
    	return records;
    }
    
    // Paint component to draw the image and players records over it
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(leaderboardBackground, 0, 0, game.getWidth(), game.getHeight(), null);
        paintLeaders(g);
    }
}

