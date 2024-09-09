import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Harry Yang 50% Recon Chen 50%
 * last update 2024, 06, 09
 * Ms. Krasteva
 * The ResolveAI class calls the new screens in based on the userdecisions throughout the program. It manages which screens are on when.
 */
 
public class ResolveAI {

   /** This JPanel variable holds the current screen that is being displayed. */
   public static JPanel currentScreen;
   /** This JFrame variable is the main frame being used. */
   public static JFrame frame;
   /** This Graphics2D variable holds the graphics that are being drawn. */
   public static Graphics2D graphics;
   /** This integer variable represents the current option. */
   public static int option = 1;
   /** This integer variable represents the current screen being displayed. */
   public static int screen;
   /** This array holds character images. */
   public static Image[] character = new Image[12];
   /** This string stores the character type. */
   public static String characterType = "emily";
   /** This string stores the username. */
   public static String username = "Enter Your Name";

   /**
    * Reads an image file from the specified path.
    * @param path The path of the image file to read.
    * @return The image read from the file.
    */
   public static Image readImg(String path) {
      Image image = null;
      try {
         // Load the image from the file system
         image = ImageIO.read(new File(path));
      } catch (IOException e) {
         e.printStackTrace();
      }
      return image;
   }

   /**
    * Sets up character images based on the selected character type.
    */
   public static void characterChoose() {
      if (option == 1) {
         character[0] = ResolveAI.readImg("assets/" + characterType + ".png");
         character[0] = character[0].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
         character[1] = ResolveAI.readImg("assets/" + characterType + "1.png");
         character[1] = character[1].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
         character[2] = ResolveAI.readImg("assets/" + characterType + "2.png");
         character[2] = character[2].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
         character[3] = ResolveAI.readImg("assets/" + characterType + "3.png");
         character[3] = character[3].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
         character[4] = ResolveAI.readImg("assets/" + characterType + "L.png");
         character[4] = character[4].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
         character[5] = ResolveAI.readImg("assets/" + characterType + "R.png");
         character[5] = character[5].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
         character[6] = ResolveAI.readImg("assets/" + characterType + "1L.png");
         character[6] = character[6].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
         character[7] = ResolveAI.readImg("assets/" + characterType + "1R.png");
         character[7] = character[7].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
         character[8] = ResolveAI.readImg("assets/" + characterType + "2L.png");
         character[8] = character[8].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
         character[9] = ResolveAI.readImg("assets/" + characterType + "2R.png");
         character[9] = character[9].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
         character[10] = ResolveAI.readImg("assets/" + characterType + "3L.png");
         character[10] = character[10].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
         character[11] = ResolveAI.readImg("assets/" + characterType + "3R.png");
         character[11] = character[11].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
      }
   }

   /**
    * Sets up and starts the game.
    */
   public static void game() {
      if (screen == 0) {
         SplashScreen splashScreen = new SplashScreen();
         currentScreen = splashScreen;
         frame.add(splashScreen);
         frame.setVisible(true);
         splashScreen.play();
      }
      else if (screen == -3) {
         // Goodbye screen to be made
         frame.dispose(); // https://www.tutorialspoint.com/how-to-close-jframe-on-the-click-of-a-button-in-java
      }
      else if (screen == -2) {
         // Handle screen -2
      }
      else if (screen == -1) {
         frame.remove(currentScreen);
         LeaderBoard leaderBoard = new LeaderBoard();
         currentScreen = leaderBoard;
         frame.add(leaderBoard);
         frame.setVisible(true);
      }
      else if (screen == 1) {
         frame.remove(currentScreen);
         MainMenu menu = new MainMenu();
         currentScreen = menu;
         frame.add(menu);
         frame.setVisible(true);
         menu.entryAnimation();
      }
      else if (screen == 2) {
         frame.remove(currentScreen);
         Level1 lvl1 = new Level1();
         currentScreen = lvl1;
         frame.add(lvl1);
         frame.setVisible(true);
         lvl1.requestFocusInWindow();
      }
      else if (screen == 3) {
         frame.remove(currentScreen);
         Level2 lvl2 = new Level2();
         currentScreen = lvl2;
         frame.add(lvl2);
         frame.setVisible(true);
      }
   }

   /**
    * The main method of the ResolveAI application.
    * @param args The command-line arguments.
    */
   public static void main(String[] args) {
      frame = new JFrame("ResolveAI");
      characterChoose();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      frame.setResizable(false);
      frame.setSize(800, 600);
      frame.setLocationRelativeTo(null);
      game();
   }
}
