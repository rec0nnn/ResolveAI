import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;

/**
 * Harry Yang 100% Recon Chen 0%
 * last update 2024, 06, 12
 * Ms. Krasteva
 * The main menu connects all other classes with mouse input that allows the user to enter the other screens i.e. leaderboard, level1, level2
 */
 
public class MainMenu extends JPanel implements MouseListener, MouseMotionListener {
 /**
 * The background image.
 */
private Image bg;

/**
 * The zigzag image.
 */
private Image zigzag;

/**
 * The small background image.
 */
private Image smallBg;

/**
 * The first selection arrow image.
 */
private Image selectionArrow1;

/**
 * The second selection arrow image.
 */
private Image selectionArrow2;

/**
 * The mouse instructions image.
 */
private Image mouseInstructions;

/**
 * The help button image.
 */
private Image helpButton;

/**
 * The pause button image.
 */
private Image pauseButton;

/**
 * The WASD instructions image.
 */
private Image wasdInstructions;

/**
 * Array to hold character images.
 */
private Image[] characterImages;

/**
 * The y-coordinate of animations.
 */
private int y = 0;

/**
 * Indicates if the zigzag image should move.
 */
private boolean zigzagMove = true;

/**
 * Indicates if the button animation is active.
 */
private boolean buttonAnimation;

/**
 * Indicates if this is the first run from the splash screen.
 */
public static boolean fromSplashScreen;

/**
 * Array of rectangles representing the main menu buttons.
 */
private Rectangle[] buttons;

/**
 * Index of the currently hovered button. -1 means no button is hovered.
 */
private int hoveredButton = -1;

/**
 * The current stage of user customization.
 */
private int customizationStage = 0;

/**
 * The index of the currently selected character.
 */
private int currentCharacterIndex = 1;

/**
 * Used to set the scene variable in level 1.
 */
public static int holder = 0;

/**
 * The initial instruction or tutorial stage.
 */
private int instructionStage = 0;

/**
 * The stage of the later instructions or help.
 */
private int help = 0;

/**
 * Timer for tracking how long it takes to close the program.
 */
private int time = 0;

   
   /**
    * Constructor for MainMenu class.
    */
   public MainMenu() {
      // Loading images
      bg = ResolveAI.readImg("assets/MenuBackground.png");
      bg = bg.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
      smallBg = ResolveAI.readImg("assets/smallBackground.png");
      smallBg = smallBg.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
      zigzag = ResolveAI.readImg("assets/zigzag.png");
      zigzag = zigzag.getScaledInstance(100, 460, Image.SCALE_SMOOTH);
      selectionArrow1 = ResolveAI.readImg("assets/selectionArrow1.png");
      selectionArrow1 = selectionArrow1.getScaledInstance(200,100, Image.SCALE_SMOOTH);
      selectionArrow2 = ResolveAI.readImg("assets/selectionArrow2.png");
      selectionArrow2 = selectionArrow2.getScaledInstance(200,100, Image.SCALE_SMOOTH);
      mouseInstructions = ResolveAI.readImg("assets/instructionsMouse.png");
      mouseInstructions = mouseInstructions.getScaledInstance(300,225, Image.SCALE_SMOOTH);
      helpButton = ResolveAI.readImg("assets/instructionsIcon.png");
      helpButton = helpButton.getScaledInstance(50,50, Image.SCALE_SMOOTH);
      pauseButton = ResolveAI.readImg("assets/pauseIcon.png");
      pauseButton = pauseButton.getScaledInstance(50,50, Image.SCALE_SMOOTH);
      wasdInstructions = ResolveAI.readImg("assets/instructionsWASD.png");
      wasdInstructions = wasdInstructions.getScaledInstance(300,240, Image.SCALE_SMOOTH);
      
      if(!fromSplashScreen)
         instructionStage=5;
      // Initialize the character images array
      characterImages = new Image[3];
      characterImages[0] = ResolveAI.readImg("assets/john.png").getScaledInstance(90,120, Image.SCALE_SMOOTH);
      characterImages[1] = ResolveAI.readImg("assets/bob.png").getScaledInstance(90,120, Image.SCALE_SMOOTH);
      characterImages[2] = ResolveAI.readImg("assets/emily.png").getScaledInstance(90,120, Image.SCALE_SMOOTH);
   
      // Initialize buttons
      buttons = new Rectangle[4];
      for (int i = 0; i < buttons.length; i++) {
         buttons[i] = new Rectangle(300, 230 + i * 80, 200, 70);
      }
   
      // Add mouse listeners
      addMouseMotionListener(this);
      addMouseListener(this);
   
      repaint(); // Repaint the panel
   }

   /**
    * Method to perform entry animation for the main menu.
    */
   public void entryAnimation() {
      Timer timer = new Timer();
   
      TimerTask buttonMove = 
         new TimerTask() {
            public void run() {
               if(instructionStage>=5)
               {
                  y += 7; // Move characters by 7 pixels per tick
               }
               if (y >= 0 && buttonAnimation) {
                  y = 0; // Stop the movement when y reaches the limit
                  this.cancel();
               }
               repaint();
            }
         };
   
      if (fromSplashScreen) {
         TimerTask zmove = 
            new TimerTask() {
               public void run() {
                  y += 5; // Move characters by 5 pixels per tick
                  if (y >= 550) {
                     y = -470; // Stop the movement when y reaches the limit
                     zigzagMove = false;
                     buttonAnimation = true;
                     fromSplashScreen = false;
                     instructionStage++;
                     timer.scheduleAtFixedRate(buttonMove, 0, 15); // Calls the buttons down Adjust the delay as needed
                     this.cancel();
                  }
                  repaint();
               }
            };
         timer.scheduleAtFixedRate(zmove, 0, 20); // Adjust the delay as needed
      } else {
         zigzagMove = false;
         buttonAnimation = true;
         y = -470;
         timer.scheduleAtFixedRate(buttonMove, 0, 15); // Calls the buttons down Adjust the delay as needed
      }
   }

   /**
    * Method to check the position of the mouse.
    * @param p The Point object representing the mouse position.
    */
   private void checkMousePosition(Point p) {
      if(help==0){
         if(customizationStage==0 && instructionStage>=5){
            hoveredButton = -1; // Reset hovered button
            for (int i = 0; i < buttons.length; i++) {
               Rectangle buttonBounds = new Rectangle(buttons[i].x, buttons[i].y + y, buttons[i].width, buttons[i].height);
               if (buttonBounds.contains(p)) {
                  hoveredButton = i;
                  break;
               }
            }
         }
         else if(customizationStage==1)
         {
         // Check mouse position for stage 1 customization
            hoveredButton =-1; // Reset hovered button
            Rectangle buttonBounds1 = new Rectangle(200, 190, 400, 50);
            if (buttonBounds1.contains(p)) {
               hoveredButton = 0;
            }
            Rectangle buttonBounds2 = new Rectangle(300, 420, 200, 50);
            if (buttonBounds2.contains(p)) {
               hoveredButton = 1;
            }
         }
         else if(customizationStage==2)
         {
         // Check mouse position for stage 2 customization
            hoveredButton=-1;
            Rectangle buttonBounds2 = new Rectangle(300, 420, 200, 50);
            if (buttonBounds2.contains(p)) {
               hoveredButton = 1;
            }
         }
      }
      repaint();
   }

   /**
    * Method to handle mouse click events.
    * @param p The Point object representing the mouse click position.
    */
   private void checkButtonClick(Point p) {
      if(help==0){
         if(instructionStage>=5){
            Rectangle buttonHelp = new Rectangle(720, 10, 50, 50);
            if (buttonHelp.contains(p)) {
               help=1;
               repaint();
            }
         }
         if(instructionStage>=1)
         {
            instructionStage++;
            repaint();
         }
         if(customizationStage==0 && instructionStage>=5){
         // Check button click for stage 0 customization
            for (int i = 0; i < buttons.length; i++) {
               Rectangle buttonBounds = new Rectangle(buttons[i].x, buttons[i].y + y, buttons[i].width, buttons[i].height);
               if (buttonBounds.contains(p)) {
                  handleButtonClick(i);
                  break;
               }
            }
         }
         else if(customizationStage==1)
         {
         // Check button click for stage 1 customization
            hoveredButton =-1; // Reset hovered button
            Rectangle buttonBounds1 = new Rectangle(200, 190, 400, 50);
            if (buttonBounds1.contains(p)) {
               handleButtonClick(0);   
            }
            Rectangle buttonBounds2 = new Rectangle(300, 420, 200, 50);
            if (buttonBounds2.contains(p)) {
               handleButtonClick(1);
            }
         }
         else if(customizationStage==2)
         {
         // Check button click for stage 2 customization
            Rectangle buttonBounds1 = new Rectangle(130, 260, 200, 100);
            if (buttonBounds1.contains(p)) {
               handleButtonClick(0);
            }
         
            Rectangle buttonBounds2 = new Rectangle(460, 260, 200, 100);
            if (buttonBounds2.contains(p)) {
               handleButtonClick(1);
            }
         
            Rectangle buttonBounds3 = new Rectangle(300, 420, 200, 50);
            if (buttonBounds3.contains(p)) {
               handleButtonClick(2);
            }
         }
      }
      else
      {
         help++;
         if(help>=4){
            help=0;
         }
         repaint();
      }
   }

   /**
    * Method to handle button click events.
    * @param buttonIndex The index of the clicked button.
    */
   private void handleButtonClick(int buttonIndex) {
      if(customizationStage==0 && instructionStage>=5)
      {
         // Handle button click for stage 0 customization
         switch (buttonIndex) {
            case 0:
               // Play button clicked
               ResolveAI.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
               customizationStage++;
               repaint();
               break;
            case 1:
               // LeaderBoard button clicked
               ResolveAI.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
               ResolveAI.screen = -1;
               ResolveAI.game();
               break;
            case 2:
               // Load Save button clicked
               ResolveAI.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
               try
               {
                  String filePath = JOptionPane.showInputDialog(ResolveAI.frame, "Please enter the name of the file you want to load:");
                  BufferedReader input = new BufferedReader(new FileReader(filePath));
                  String line=" ";
               
                  line=input.readLine();
                  ResolveAI.username=line;
                  line=input.readLine();
                  ResolveAI.characterType=line;
                  line=input.readLine();
                  try
                  {
                     int scene = Integer.parseInt(line);
                     holder=scene;
                     ResolveAI.character[0] = ResolveAI.readImg("assets/"+ResolveAI.characterType+".png");
                     ResolveAI.character[0] = ResolveAI.character[0].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
                     ResolveAI.character[1] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"1.png");
                     ResolveAI.character[1] = ResolveAI.character[1].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
                     ResolveAI.character[2] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"2.png");
                     ResolveAI.character[2] = ResolveAI.character[2].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
                     ResolveAI.character[3] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"3.png");
                     ResolveAI.character[3] = ResolveAI.character[3].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
                     ResolveAI.character[4] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"L.png");
                     ResolveAI.character[4] = ResolveAI.character[4].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
                     ResolveAI.character[5] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"R.png");
                     ResolveAI.character[5] = ResolveAI.character[5].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
                     ResolveAI.character[6] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"1L.png");
                     ResolveAI.character[6] = ResolveAI.character[6].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
                     ResolveAI.character[7] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"1R.png");
                     ResolveAI.character[7] = ResolveAI.character[7].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
                     ResolveAI.character[8] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"2L.png");
                     ResolveAI.character[8] = ResolveAI.character[8].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
                     ResolveAI.character[9] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"2R.png");
                     ResolveAI.character[9] = ResolveAI.character[9].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
                     ResolveAI.character[10] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"3L.png");
                     ResolveAI.character[10] = ResolveAI.character[10].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
                     ResolveAI.character[11] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"3R.png");
                     ResolveAI.character[11] = ResolveAI.character[11].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
                     ResolveAI.screen++;
                     ResolveAI.game();
                  }
                  catch(Exception e)
                  {
                  
                  }
               } catch (IOException e) {
                  System.out.println("Could not find file");
               }
               break;
            case 3:
               // Exit button clicked
               ResolveAI.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
               timer();
               break;
         }
      }
      else if(customizationStage==1)
      {
         // Handle button click for stage 1 customization
         switch (buttonIndex) {
            case 0:
               // Enter name button clicked
               ResolveAI.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
               String input = JOptionPane.showInputDialog(ResolveAI.frame, "Please enter your name (max 12 characters):");
               ResolveAI.username=input;
               input+=" ";
               while(ResolveAI.username==null || ResolveAI.username.equals("") || ResolveAI.username.length()>12)
               {
                  input = JOptionPane.showInputDialog(ResolveAI.frame, "Error! invalid name (1-12 characters)");
                  ResolveAI.username=input;
               }
               break;
            case 1:
               // Confirm button clicked
               ResolveAI.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
               if(ResolveAI.username.equals("Enter Your Name"))
               {
                  JOptionPane.showMessageDialog(ResolveAI.frame, "You didn't select a name so your username will be set as Bobby");
                  ResolveAI.username="Bobby"; 
               }  
               customizationStage++;
               repaint();
               break;
         }
      }
      else if(customizationStage==2)
      {
         // Handle button click for stage 2 customization
         switch (buttonIndex) {
            case 0:
               // Enter name button clicked
               ResolveAI.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
               currentCharacterIndex--;
               if(currentCharacterIndex==-1)
                  currentCharacterIndex=0;
               repaint();
               break;
            case 1:
               // Confirm button clicked
               ResolveAI.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
               currentCharacterIndex++;
               if(currentCharacterIndex==3)
                  currentCharacterIndex=2;
               repaint();
               break;
            case 2:
               // Confirm character selection button clicked
               ResolveAI.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
               if(currentCharacterIndex==0)
                  ResolveAI.characterType="john";
               else if(currentCharacterIndex==1)
                  ResolveAI.characterType="bob";
               else if(currentCharacterIndex==2)
                  ResolveAI.characterType="emily";
                  
               ResolveAI.character[0] = ResolveAI.readImg("assets/"+ResolveAI.characterType+".png");
               ResolveAI.character[0] = ResolveAI.character[0].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
               ResolveAI.character[1] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"1.png");
               ResolveAI.character[1] = ResolveAI.character[1].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
               ResolveAI.character[2] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"2.png");
               ResolveAI.character[2] = ResolveAI.character[2].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
               ResolveAI.character[3] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"3.png");
               ResolveAI.character[3] = ResolveAI.character[3].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
               ResolveAI.character[4] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"L.png");
               ResolveAI.character[4] = ResolveAI.character[4].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
               ResolveAI.character[5] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"R.png");
               ResolveAI.character[5] = ResolveAI.character[5].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
               ResolveAI.character[6] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"1L.png");
               ResolveAI.character[6] = ResolveAI.character[6].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
               ResolveAI.character[7] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"1R.png");
               ResolveAI.character[7] = ResolveAI.character[7].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
               ResolveAI.character[8] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"2L.png");
               ResolveAI.character[8] = ResolveAI.character[8].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
               ResolveAI.character[9] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"2R.png");
               ResolveAI.character[9] = ResolveAI.character[9].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
               ResolveAI.character[10] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"3L.png");
               ResolveAI.character[10] = ResolveAI.character[10].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
               ResolveAI.character[11] = ResolveAI.readImg("assets/"+ResolveAI.characterType+"3R.png");
               ResolveAI.character[11] = ResolveAI.character[11].getScaledInstance(72, 96, Image.SCALE_SMOOTH);
               ResolveAI.screen=2;
               ResolveAI.game();
               break;
         }
      
      }
   }
   
   public void timer()
   {
      Timer timer = new Timer();
      TimerTask exit = 
            new TimerTask() {
               public void run() {
                  time++;; // Move characters by 5 pixels per tick
                  if (time>=20) {
                     ResolveAI.screen = -3;
                     ResolveAI.game();
                     this.cancel();
                  }
               }
            };
      timer.scheduleAtFixedRate(exit, 0, 100); // Adjust the delay as needed   
   }
   /**
    * Method to paint the components of the panel.
    * @param g The Graphics object.
    */
   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;
      g2d.drawImage(bg, 0, 0, this);
      if(time>=1){
         Font titleFont = new Font("SansSerif", Font.BOLD, 50);            
         g2d.setFont(titleFont);
         String title = "The End!";
         int titleX = (500 - g2d.getFontMetrics().stringWidth(title)) / 2 + 150; // Centered horizontally within the panel
         int titleY = 300;
         g2d.drawString(title, titleX, titleY);
            
         title = "Thank you for playing";
         titleX = (500 - g2d.getFontMetrics().stringWidth(title)) / 2 + 150; // Centered horizontally within the panel
         titleY = 400;
         g2d.drawString(title, titleX, titleY);
      }
      else if (zigzagMove) {
         g2d.drawImage(zigzag, 360, 200 + y, this);
      } 
      else if(instructionStage>=1 && instructionStage<=4)
      {
         g2d.drawImage(smallBg, 150, 50 , this);
         
         Font titleFont = new Font("SansSerif", Font.BOLD, 50);
         Font instructionsFont = new Font("SansSerif", Font.PLAIN, 20);
         Font instructionsFontBold = new Font("SansSerif", Font.BOLD, 20);
         Font smallFont = new Font("SansSerif", Font.BOLD, 15);
         
         g2d.setFont(titleFont);
         String title = "Instructions";
         int titleX = (500 - g2d.getFontMetrics().stringWidth(title)) / 2 + 150; // Centered horizontally within the panel
         int titleY = 150;
         g2d.drawString(title, titleX, titleY);
         
         if(instructionStage==1)
         {
            g2d.setFont(instructionsFontBold);
            String text = "Here are some instructions to get you started";
            int textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            int textY = 200;
            g2d.drawString(text, textX, textY);
         
            text = "A little bit about our game:";
            textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            textY = 260;
            g2d.drawString(text, textX, textY);
         
            g2d.setFont(instructionsFont);
            text = "Our game is all about resolveing conflicts!";
            textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            textY = 300;
            g2d.drawString(text, textX, textY);
         
            text = "You will first see our self made AI solve it.";
            textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            textY = 340;
            g2d.drawString(text, textX, textY);
         
            text = "Then you will solve the problem yourself,";
            textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            textY = 380;
            g2d.drawString(text, textX, textY);
         
            text = "and the AI will learn from your teachings.";
            textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            textY = 420;
            g2d.drawString(text, textX, textY);
         
            g2d.setFont(smallFont);
            text = "Click anywhere to continue.";
            textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            textY = 480;
            g2d.drawString(text, textX, textY);
         }
         else if(instructionStage==2)
         {
            g2d.setFont(instructionsFontBold);
            String text = "Most of our game can be operated";
            int textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            int textY = 200;
            g2d.drawString(text, textX, textY);
         
            text = "Using mouse clicks";
            textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            textY = 240;
            g2d.drawString(text, textX, textY);
         
            g2d.drawImage(mouseInstructions, 240, 240, this);
         
            g2d.setFont(smallFont);
            text = "Click anywhere to continue.";
            textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            textY = 480;
            g2d.drawString(text, textX, textY);
         }
         else if(instructionStage==3)
         {
            g2d.setFont(instructionsFontBold);
            String text = "In level 1 there will be character movement.";
            int textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            int textY = 200;
            g2d.drawString(text, textX, textY);
         
            text = "This will be controlled using WASD";
            textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            textY = 240;
            g2d.drawString(text, textX, textY);
         
            g2d.drawImage(wasdInstructions, 240, 240, this);
         
            g2d.setFont(smallFont);
            text = "Click anywhere to continue.";
            textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            textY = 480;
            g2d.drawString(text, textX, textY);
            
         }
         else if(instructionStage==4)
         {
            g2d.setFont(instructionsFontBold);
            String text = "If you forget these instructions,";
            int textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            int textY = 200;
            g2d.drawString(text, textX, textY);
            
            text = "You can click this Icon:";
            textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            textY = 240;
            g2d.drawString(text, textX, textY);
            
            g2d.drawImage(helpButton, 375, 260, this);
            
            text = "During levels to pause and leave";
            textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            textY = 340;
            g2d.drawString(text, textX, textY);
            
            text = "You can click this Icon:";
            textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            textY = 380;
            g2d.drawString(text, textX, textY);
            
            g2d.drawImage(pauseButton, 375, 400, this);
            
            g2d.setFont(instructionsFontBold);
            text = "Click anywhere to get on with the game!";
            textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
            textY = 490;
            g2d.drawString(text, textX, textY);
         }
      }
      else if (buttonAnimation) {
        // Buttons
         Color calmBlue = new Color(155, 227, 255);
         Color hoverColor = new Color(100, 200, 255);
         g2d.setColor(calmBlue);
         int handCursor = 0;
         for (int i = 0; i < buttons.length; i++) {
            if (i == hoveredButton) {
               g2d.setColor(hoverColor);
               ResolveAI.frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
               g2d.setColor(calmBlue);
               handCursor++;
            }
            g2d.fillRoundRect(buttons[i].x, buttons[i].y + y, buttons[i].width, buttons[i].height, 30, 30);
         }
         if (handCursor == 4) {
            ResolveAI.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }
      
        // Set font for text
         Font font = new Font("Sans Serif", Font.BOLD, 20);
         g2d.setFont(font);
         g2d.setColor(Color.BLACK);
      
        // Draw text inside the rectangles
         g2d.drawString("Play", 380, 270 + y);
         g2d.drawString("LeaderBoard", 340, 350 + y);
         g2d.drawString("Load Save", 350, 430 + y);
         g2d.drawString("Exit", 380, 510 + y);
         
         g2d.drawImage(helpButton, 720, 10, this); //help button
            
         // Once play is selected
         if (customizationStage >= 1) {
            g2d.drawImage(bg, 0, 0, this);
            g2d.drawImage(smallBg, 150, 50, this);
            g2d.drawImage(helpButton, 720, 10, this); //help button
            Font titleFont = new Font("SansSerif", Font.BOLD, 40);
            Font customizationFont = new Font("SansSerif", Font.BOLD, 30);
            Font customizationFontSm = new Font("SansSerif", Font.BOLD, 15);
            Color lightGray = Color.decode("#CCCCCC");
            if (customizationStage == 1) {         
               g2d.setFont(titleFont);
               g2d.setColor(Color.BLACK);
               String title = "Username Selection";
               int titleX = (500 - g2d.getFontMetrics().stringWidth(title)) / 2 + 150; // Centered horizontally within the panel
               int titleY = 150;
               g2d.drawString(title, titleX, titleY);
            
               g2d.setFont(customizationFont);
                // Draw "Enter your name" box
               Color boxColor = (hoveredButton == 0) ? lightGray : Color.WHITE;
               g2d.setColor(boxColor);
               g2d.fillRoundRect(200, 190, 400, 50, 20, 20);
               g2d.setColor(Color.BLACK);
               g2d.drawRoundRect(200, 190, 400, 50, 20, 20);
                // Draw text "Enter your name"
               String text = ResolveAI.username+" ";
               FontMetrics metrics = g2d.getFontMetrics(customizationFont);
               int x = (600 - metrics.stringWidth(text)) / 2; // Centered horizontally within the box
               int y = 225;
               g2d.drawString(text, x + 100, y); // Adjust the x position to center it in the box
            
            
                // Draw "Confirm" box
               Color confirmBoxColor = (hoveredButton == 1) ? lightGray : Color.WHITE;
               g2d.setColor(confirmBoxColor);
               g2d.fillRoundRect(300, 420, 200, 50, 20, 20);
               g2d.setColor(Color.BLACK);
               g2d.drawRoundRect(300, 420, 200, 50, 20, 20);
                // Draw text "Confirm"
               text = "Confirm";
               x = (600 - metrics.stringWidth(text)) / 2; // Centered horizontally within the box
               y = 455; // Adjust the y position as needed
               g2d.drawString(text, x + 100, y); // Adjust the x position to center it in the box
            
               g2d.setFont(customizationFontSm);
            
                // First line of text
               String instructions1 = "Click on the 'enter your name' box";
               FontMetrics metrics1 = g2d.getFontMetrics(customizationFontSm);
               int x1 = (500 - metrics1.stringWidth(instructions1)) / 2 + 150; // Centered horizontally within the panel
               int y1 = 300; // Adjust the y position as needed
               g2d.drawString(instructions1, x1, y1);
            
                // Second line of text
               String instructions2 = "then type in your desired user name.";
               FontMetrics metrics2 = g2d.getFontMetrics(customizationFontSm);
               int x2 = (500 - metrics2.stringWidth(instructions2)) / 2 + 150; // Centered horizontally within the panel
               int y2 = 330; // Adjust the y position as needed
               g2d.drawString(instructions2, x2, y2);
            
                // Third line of text
               String instructions3 = "Once you have decided on your name, click confirm.";
               FontMetrics metrics3 = g2d.getFontMetrics(customizationFontSm);
               int x3 = (500 - metrics3.stringWidth(instructions3)) / 2 + 150; // Centered horizontally within the panel
               int y3 = 360; // Adjust the y position as needed
               g2d.drawString(instructions3, x3, y3);
            }
            else {
               g2d.setFont(titleFont);
               g2d.setColor(Color.BLACK);
               String title = "Character Selection";
               int titleX = (500 - g2d.getFontMetrics().stringWidth(title)) / 2 + 150; // Centered horizontally within the panel
               int titleY = 150;
               g2d.drawString(title, titleX, titleY);
            
            // Draw the first character image
               int charX1 = 200;
               int charY1 = 250;
               if(currentCharacterIndex>=1){
                  g2d.drawImage(characterImages[currentCharacterIndex-1], charX1, charY1, this);
               
               // Draw the selection arrow 1 over the first character image
                  int arrowX1 = charX1 - 70; // Adjusted to center the arrow
                  int arrowY1 = charY1 + 10;
                  g2d.drawImage(selectionArrow1, arrowX1, arrowY1, this);
               }
            
            // Draw the second character image
               int charX2 = 350;
               int charY2 = 250;
               g2d.drawImage(characterImages[currentCharacterIndex], charX2, charY2, this);
            
            // Draw the third character image
               int charX3 = 500;
               int charY3 = 250;
               if(currentCharacterIndex<=1){
                  g2d.drawImage(characterImages[currentCharacterIndex+1], charX3, charY3, this);
               
               // Draw the selection arrow 2 over the third character image
                  int arrowX2 = charX3 - 40; // Adjusted to center the arrow
                  int arrowY2 = charY3 + 10;
                  g2d.drawImage(selectionArrow2, arrowX2, arrowY2, this);
               }
               
            // Draw "Confirm" box
               Color confirmBoxColor = (hoveredButton == 1) ? lightGray : Color.WHITE;
               g2d.setColor(confirmBoxColor);
               g2d.fillRoundRect(300, 420, 200, 50, 20, 20);
               g2d.setColor(Color.BLACK);
               g2d.drawRoundRect(300, 420, 200, 50, 20, 20);
            // Draw text "Confirm"
               String text = "Confirm";
               FontMetrics metrics = g2d.getFontMetrics(titleFont);
               int x = (600 - metrics.stringWidth(text)) / 2; // Centered horizontally within the box
               int y = 455; // Adjust the y position as needed
               g2d.drawString(text, x + 100, y); // Adjust the x position to center it in the box
            }
         }
         if(help>=1){
            g2d.drawImage(smallBg, 150, 50 , this);
         
            Font titleFont = new Font("SansSerif", Font.BOLD, 50);
            Font instructionsFont = new Font("SansSerif", Font.PLAIN, 20);
            Font instructionsFontBold = new Font("SansSerif", Font.BOLD, 20);
            Font smallFont = new Font("SansSerif", Font.BOLD, 15);
         
            g2d.setFont(titleFont);
            String title = "Instructions";
            int titleX = (500 - g2d.getFontMetrics().stringWidth(title)) / 2 + 150; // Centered horizontally within the panel
            int titleY = 150;
            g2d.drawString(title, titleX, titleY);
         
            if(help==1)
            {
               g2d.setFont(instructionsFontBold);
               String text = "Most of our game can be operated";
               int textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
               int textY = 200;
               g2d.drawString(text, textX, textY);
            
               text = "Using mouse clicks";
               textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
               textY = 240;
               g2d.drawString(text, textX, textY);
            
               g2d.drawImage(mouseInstructions, 240, 240, this);
            
               g2d.setFont(smallFont);
               text = "Click anywhere to continue.";
               textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
               textY = 480;
               g2d.drawString(text, textX, textY);
            }
            else if(help==2)
            {
               g2d.setFont(instructionsFontBold);
               String text = "In level 1 there will be character movement.";
               int textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
               int textY = 200;
               g2d.drawString(text, textX, textY);
            
               text = "This will be controlled using WASD";
               textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
               textY = 240;
               g2d.drawString(text, textX, textY);
            
               g2d.drawImage(wasdInstructions, 240, 240, this);
            
               g2d.setFont(smallFont);
               text = "Click anywhere to continue.";
               textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
               textY = 480;
               g2d.drawString(text, textX, textY);
            
            }
            else if(help==3)
            {
               g2d.setFont(instructionsFontBold);
               String text = "If you forget these instructions,";
               int textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
               int textY = 200;
               g2d.drawString(text, textX, textY);
            
               text = "You can click this Icon:";
               textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
               textY = 240;
               g2d.drawString(text, textX, textY);
            
               g2d.drawImage(helpButton, 375, 260, this);
            
               text = "During levels to pause and leave";
               textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
               textY = 340;
               g2d.drawString(text, textX, textY);
            
               text = "You can click this Icon:";
               textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
               textY = 380;
               g2d.drawString(text, textX, textY);
            
               g2d.drawImage(pauseButton, 375, 400, this);
            
               g2d.setFont(instructionsFontBold);
               text = "Click anywhere to get on with the game!";
               textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
               textY = 490;
               g2d.drawString(text, textX, textY);
            }
         }
      }
   }

   // Overridden methods from MouseListener interface
   @Override
   public void mouseClicked(MouseEvent e) {
      checkButtonClick(e.getPoint());
   }

   @Override
   public void mousePressed(MouseEvent e) {
   }

   @Override
   public void mouseReleased(MouseEvent e) {
   }

   @Override
   public void mouseEntered(MouseEvent e) {
   }

   @Override
   public void mouseExited(MouseEvent e) {
   }

   // Overridden methods from MouseMotionListener interface
   @Override
   public void mouseDragged(MouseEvent e) {
   }

   @Override
   public void mouseMoved(MouseEvent e) {
      checkMousePosition(e.getPoint());
   }
}
