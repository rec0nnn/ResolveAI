import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Harry Yang 100% Recon Chen 0%
 * last update 2024, 06, 11
 * Ms. Krasteva
 * The leaderboard class displays players and their usernames. Only the top 6 are shown
 */
public class LeaderBoard extends JPanel implements MouseListener, MouseMotionListener {
   private Image bg; // Background image
   private String[] name = new String[6]; // Player names
   private int[] score = new int[6]; // Player scores
   private boolean hovered = false; // Hover state for mouse interactions

   /**
    * Constructs a new LeaderBoard panel, initializes the background image,
    * and sets up mouse listeners.
    */
   public LeaderBoard() {
      bg = ResolveAI.readImg("assets/LeaderboardBackground.png");
      bg = bg.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
      
      addMouseMotionListener(this);
      addMouseListener(this);
      
      // Repaint the panel to update the graphics
      repaint();
   }

   /**
    * Paints the leaderboard panel, including the background image,
    * player names, and scores.
    *
    * @param g the Graphics object used for painting
    */
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;
   
      // Draw the background
      g2d.drawImage(bg, 0, 0, this);
   
      // Set font for text
      Font font = new Font("Sans Serif", Font.BOLD, 20);
      g2d.setFont(font);
      g2d.setColor(Color.BLACK);
   
      // Read file and fill ArrayLists with leaderboard information
      int length = 0; // Length of the amount of people on the leaderboard
      ArrayList<String> names = new ArrayList<>();
      ArrayList<Integer> scores = new ArrayList<>();
   
      try {
         Scanner input = new Scanner(new File("Leaderboard.txt"));
      
         // Read the file line by line
         while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (!line.isEmpty()) {
               String playerName = line;
               if (input.hasNextLine()) {
                  line = input.nextLine().trim();
                  try {
                     int playerScore = Integer.parseInt(line);
                     names.add(playerName);
                     scores.add(playerScore);
                  } catch (NumberFormatException e) {
                     // If the score is not a valid integer, skip this entry
                  }
               }
            }
         }
         input.close();
      
         // Sort the scores and names together based on scores
         for (int i = 0; i < scores.size(); i++) {
            for (int j = i + 1; j < scores.size(); j++) {
               if (scores.get(j) > scores.get(i)) {
                  // Swap scores
                  int tempScore = scores.get(i);
                  scores.set(i, scores.get(j));
                  scores.set(j, tempScore);
               
                  // Swap names
                  String tempName = names.get(i);
                  names.set(i, names.get(j));
                  names.set(j, tempName);
               }
            }
         }
      
         length = Math.min(names.size(), 5); // Maximum of 5 entries
      } catch (FileNotFoundException e) {
         System.err.println("File not found: Leaderboard.txt");
      }
   
      // Draw rounded rectangles with a calm blue color
      Color backgroundBrown = new Color(255, 255, 255);
      g2d.setColor(backgroundBrown);
      g2d.fillRoundRect(210, 150 + 60, 390, 60 * length, 30, 30);
   
      g2d.setFont(font);
      g2d.setColor(Color.GRAY); // Dark coffee brown
   
      for (int i = 0; i < length; i++) {
         int yPosition = 190 + (i * 60);
         g2d.drawString(names.get(i), 250, yPosition + 60); // Draw name
         g2d.drawString(String.valueOf(scores.get(i)), 530, yPosition + 60); // Draw score
      }
   }
   
   /**
    * Checks the position of the mouse to update the hover state.
    *
    * @param p the current mouse position
    */
   private void checkMousePosition(Point p) {
      hovered = false; // Reset hovered button
      if (p.x > 0) {
         hovered = true;
      }
      repaint();
   }
   
   /**
    * Checks if a button was clicked based on the mouse position.
    *
    * @param p the current mouse position
    */
   private void checkButtonClick(Point p) {
      if (p.x > 20 && p.x < 150 && p.y > 20 && p.y < 100) {
         ResolveAI.screen = 1;
         ResolveAI.game();
      }
   }
   
   // Implementing MouseListener methods as it is an abstract class
   /**
    * Invoked when the mouse has been clicked (pressed and released) on a component.
    *
    * @param e the event to be processed
    */
   @Override
   public void mouseClicked(MouseEvent e) {
      checkButtonClick(e.getPoint());
   }

   @Override
   public void mouseEntered(MouseEvent e) {}

   @Override
   public void mouseExited(MouseEvent e) {}

   @Override
   public void mousePressed(MouseEvent e) {}

   @Override
   public void mouseReleased(MouseEvent e) {}

   // Implementing MouseMotionListener methods
   @Override
   public void mouseDragged(MouseEvent e) {}

   /**
    * Invoked when the mouse cursor has been moved onto a component but no buttons have been pushed.
    *
    * @param e the event to be processed
    */
   @Override
   public void mouseMoved(MouseEvent e) {
      checkMousePosition(e.getPoint());
   }
}
