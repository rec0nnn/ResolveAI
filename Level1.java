import java.awt.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.*;
import java.io.*;

/**
 * Harry Yang 30% Recon Chen 70%
 * last update 2024, 06, 12
 * Ms. Krasteva
 * Level 1 contains the original demo of the flawed AI and lets the user control a character and play through a similar scenario. Movement is achieved user WASD keyboard input and mouse input is also incorporated.
 **/

public class Level1 extends JPanel implements MouseListener, KeyListener {

   private Image bg, bg2, smallBg, pauseIcon; // Background image
   private Image robot, robot1, robot2, robot3; //robot images
   private Image joe, joeAnimation; //joe images
   private Image arguement;   //argument people 
   private Image ava, avaBig; //ava images
   private Image adam, adamBig;  //adam images
   private Image neville, nevilleBig;  //neville images
   private Image rebeccaBig;  //rebecca images
   private Image koji;  //ayanokoji images
   private Image player, playerBig; //player images
   private Image wasdInstructions;  //instructions image
   private MessageBox box; // Message box to display messages
   private int scene; // Index to track the current message
   private int x, y, x2, y2;  //x and y of background and x2 and y2 of the character
   private int xDirection, yDirection; //to find which way the robot needs to face
   private int chosen; //variable to track the option chosen by the user
   public static int correct; //variable to track number of correct answers;
   private int interaction; //variable to track which interaction you are on
   private int temp; //temperary variable used throughout the level
   private final boolean[][] black= new boolean[30][40]; // Array to store colors of pixels
   private String options[] = new String[2]; //options for the MessageBox class
   private int characterState, delay, reset; //various timers
   private boolean initiateRun=false, idle;  //booleans that dictate when animations run
   private Timer resetTimer = new Timer();   //global timer variable for animations
   private int instructionStage; //which stage of instructions
   private boolean[] corrects = new boolean[4]; //so that correct doesnt increase infinatly
   private boolean pauseMenu = false;  //boolean for when the game is paused
   private static final Rectangle PAUSE_ICON_BOUNDS = new Rectangle(700, 10, 50, 50); // Position and size of the pause icon
   private static final Rectangle resume = new Rectangle(300, 100, 200, 70); // Position and size of the resume button
   private static final Rectangle mainMenu = new Rectangle(300, 240, 200, 70); // Position and size of the main menu button
   private static final Rectangle save = new Rectangle(300, 380, 200, 70); // Position and size of the save button
   
   /**
    * Constructor for Level1 class.
    * Initializes the background image, sets up the mouse listener, and repaints the panel.
    */
    
   public Level1() {
      scene = MainMenu.holder;
      x = 170;
      y = 350;
      chosen = 0;
      interaction = 0;
      temp = 0;
      correct = 0;
      //characters initial positions
      robot = ResolveAI.readImg("assets/robot.png");
      robot = robot.getScaledInstance(60, 72, Image.SCALE_SMOOTH);
      robot1 = ResolveAI.readImg("assets/robot1.png");
      robot1 = robot1.getScaledInstance(60, 72, Image.SCALE_SMOOTH);
      robot2 = ResolveAI.readImg("assets/robot2.png");
      robot2 = robot2.getScaledInstance(60, 72, Image.SCALE_SMOOTH);
      robot3 = ResolveAI.readImg("assets/robot3.png");
      robot3 = robot3.getScaledInstance(60, 72, Image.SCALE_SMOOTH);      
      joe = ResolveAI.readImg("assets/joe1.png");
      joe = joe.getScaledInstance(240, 320, Image.SCALE_SMOOTH);
      joeAnimation = ResolveAI.readImg("assets/joe1.png");
      joeAnimation = joeAnimation.getScaledInstance(72, 96, Image.SCALE_SMOOTH);
      arguement = ResolveAI.readImg("assets/arguement.png");
      arguement = arguement.getScaledInstance(208, 130, Image.SCALE_SMOOTH);
      ava = ResolveAI.readImg("assets/ava1.png");
      ava = ava.getScaledInstance(72, 96, Image.SCALE_SMOOTH);
      avaBig = ResolveAI.readImg("assets/ava1.png");
      avaBig = avaBig.getScaledInstance(240, 320, Image.SCALE_SMOOTH);
      adam = ResolveAI.readImg("assets/adam1.png");
      adam = adam.getScaledInstance(72, 96, Image.SCALE_SMOOTH);
      adamBig = ResolveAI.readImg("assets/adam.png");
      adamBig = adamBig.getScaledInstance(240, 320, Image.SCALE_SMOOTH);
      neville = ResolveAI.readImg("assets/neville.png");
      neville = neville.getScaledInstance(72, 96, Image.SCALE_SMOOTH);
      nevilleBig = ResolveAI.readImg("assets/neville.png");
      nevilleBig = nevilleBig.getScaledInstance(240, 320, Image.SCALE_SMOOTH);
      koji = ResolveAI.readImg("assets/ayanokoji1M.png");
      koji = koji.getScaledInstance(240, 320, Image.SCALE_SMOOTH);
      bg = ResolveAI.readImg("assets/classroom.png");
      bg = bg.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
      bg2 = ResolveAI.readImg("assets/playgroundBackground.png");
      bg2 = bg2.getScaledInstance(1800, 600, Image.SCALE_SMOOTH);
      smallBg = ResolveAI.readImg("assets/smallBackground.png");
      smallBg = smallBg.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
      playerBig = ResolveAI.character[1];
      playerBig = playerBig.getScaledInstance(240, 320, Image.SCALE_SMOOTH);
      rebeccaBig = ResolveAI.readImg("assets/rebecca1.png");
      rebeccaBig = rebeccaBig.getScaledInstance(240, 320, Image.SCALE_SMOOTH);
      wasdInstructions = ResolveAI.readImg("assets/instructionsWASD.png");
      wasdInstructions = wasdInstructions.getScaledInstance(300,240, Image.SCALE_SMOOTH);
      pauseIcon = ResolveAI.readImg("assets/pauseIcon.png");
      pauseIcon = pauseIcon.getScaledInstance(50,50, Image.SCALE_SMOOTH);
      setFocusable(true);
      addKeyListener(this);
      addMouseListener(this);
      play();
   }
   private void transitionAnimation() {
      TimerTask task = 
         new TimerTask() {
            @Override
            public void run() {
               int row, col;
               for(int i=0; i<2; i++)
               {
                  do {
                     row = (int) (Math.random() * 30); // 40 rows
                     col = (int) (Math.random() * 40); // 30 columns
                  } while (black[row][col]); // Repeat if the pixel is already black
               
                  black[row][col] = true;
               }
               repaint();
            
                // Check if the animation should stop
               if (isFullyBlack()) {
                  for (int i = 0; i < 30; i++) { // 30 rows
                     for (int j = 0; j < 40; j++) { // 40 columns
                        black[i][j]=false;
                     }
                  }
                  scene++;
                  play();
                  this.cancel(); // Stop the timer task
               }
            }
         };
   
      Timer timer = new Timer();
      timer.scheduleAtFixedRate(task, 0, 1); // Schedule task to run every 50ms
   }

   private boolean isFullyBlack() {
      for (int i = 0; i < 30; i++) { // 30 rows
         for (int j = 0; j < 40; j++) { // 40 columns
            if (!black[i][j]) {
               return false;
            }
         }
      }
      return true;
   }

   private void xMove(int start, int end, int increment)
   {  
      yDirection = 0;
      x=start;
      xDirection = increment;
      TimerTask moveX = 
         new TimerTask() {
            public void run() {
               x+= increment;
               if (x==end) {
                  this.cancel();
                  scene++;
                  play();
               }
               repaint();
            }
         };
      Timer timer = new Timer();
      timer.scheduleAtFixedRate(moveX, 0, 50);
   }
   private void yMove(int start, int end, int increment)
   {  
      xDirection = 0;
      y=start;
      yDirection = increment;
      TimerTask moveY = 
         new TimerTask() {
            public void run() {
               y+= increment;
               if (y==end) {
                  this.cancel();
                  scene++;
                  play();
               }
               repaint();
            }
         };
      Timer timer = new Timer();
      timer.scheduleAtFixedRate(moveY, 0, 50);
   }
   public void play() {
      if(scene == 4)
      {
         x=280;
         y=350;
         xMove(280, 350, 5);
      }
      else if(scene==5)
      {
         x=350;
         y=350;
         yMove(350,180,-5);
      }
      else if(scene == 8) {
         x=350;
         y=180;
         yMove(180,350,5);
      }
      else if(scene==9) {
         x=350;
         y=350;
         xMove(350,490,5);
      }
      else if(scene==16) {
         x=490;
         y=350;
         xMove(490,230,-5);
      }
      else if(scene==17) {
         x=230;
         y=350;
         yMove(350,-75,-5);
      }
      else if(scene==18)
      {
         x=0;
         y=0;
         transitionAnimation();
      }
      else if(scene==19)
      {
         x=0;
         y=0;
         transitionAnimation();
      }
      else if(scene==20)
      {
         x=0;
         y=500;
         xMove(0,-1000,-50);
      }
      else if(scene==21)
      {
         x=-1000;
         xMove(-1000,0,50);
         x2 = 250;
         y=500;
      }
      else if(scene==22)
      {
         y=500;
         yMove(500,340,-5);
         x=0;
         x2=250;
         y2=340;
      }
   }
   

   /**
    * Handles mouse click events. Increments the text index and repaints the panel.
    *
    * @param e the mouse event
    */
   @Override
   public void mouseClicked(MouseEvent e) {
      int mouseX = e.getX();
      int mouseY = e.getY();
   
      // Check if pause icon was clicked
      if (PAUSE_ICON_BOUNDS.contains(mouseX, mouseY) && pauseMenu == false) {
         pauseMenu = true;
         repaint();
      }
      else if(pauseMenu) {
         if(resume.contains(mouseX, mouseY)) {
            pauseMenu = false;
            repaint();
         }
         else if(mainMenu.contains(mouseX, mouseY)){
            ResolveAI.screen = 1;
            ResolveAI.game();
         }
         else if(save.contains(mouseX,mouseY)){
         // File name
            String fileName = "saveAs.txt";
         
         // Use try-with-resources to ensure the file is closed after writing
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            // FileWriter(fileName, false) clears the file before writing
            
            // Write the variables to the file
               writer.write(ResolveAI.username);
               writer.newLine();
               writer.write(ResolveAI.characterType);
               writer.newLine();
               writer.write(scene);
               writer.newLine();
               System.out.println("Data written to " + fileName);
            } catch (IOException x) {
            }
         }
      }
      else if(scene < 25 || scene == 29 || (scene == 30 && temp == 1) || scene>=32) {
         scene++;
         repaint();
      }
      else{
         scene = 25;
         repaint();
      }
      play();
   }


   // Unused mouse listener methods
   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }
   @Override
   public void keyPressed(KeyEvent e) {
      if(scene == 25) {
         if (e.getKeyCode() == KeyEvent.VK_W) {
            reset=0;
         //collision detections
         // if(y2<=445 && ((x2>=90 && x2<=150) && (x<=0 && x>=-50))) { //ladder
         //             y2=445;
         //          }
         //          if(y2<=195 && (x<=-55 && x>=-295)) { //seesaw
         //             y2=195;
         //          }
         //    }
            if(y2<=85 && (x >=-355 || (x<=-485 && x>=-960) || (x2>=235 && x==-1000))) {
               y2=85;
            }
            else if(y2 <= 0) {
               y2=0;
            }
            else {
               y2-=5;
            }
            //npc interactions
            if(x==0 && (x2>=145 && x2<=400) && y2==310) { //arguement interaction
               if(interaction == 4) {
                  scene = 32;
                  y2=315;
               }
               else {
                  scene =26;
                  y2=315;
               }
            }
            if((x<=-460 && x>=-570) && x2==400 && y2==285) { //adam interaction
               if(interaction >= 2){
                  scene =28;
               }
               y2+=5;
            }
            if((x<=-120 && x>=-210) && x2==400 && (y2==85) && interaction >= 1) { //ava interaction
               scene =27;
               y2=90;
            }
            if((x<=-720 && x>=-820) && x2==400 && (y2==85) && interaction >= 3) { //neville interaction
               scene =29;
               y2+=5;
            }
         //character movement animation
            delay++;
            if(characterState!=8 && characterState!=9 && characterState!=2)   //if not any of the upwards states then set to the two feet one
            {
               characterState=2;
               delay=0;
            } 
            else if(characterState==8 && (delay==3 || idle))   //next in the cycle after two feet is left foot up
            {
               characterState=9;
               delay=0;
            }
            else if(characterState==9 && (delay==3 || idle))   //next in the cycle after left foot up is right foot up
            {
               characterState=2;
               delay=0;
            }
            else if(delay==3 || idle)  //next in the cycle is two feet again then the cycle repeats
            {
               characterState=8;
               delay=0;
            }
            idle=false;
            repaint();
         } 
         if (e.getKeyCode() == KeyEvent.VK_S) {
            reset=0;
            if(y2>=469) {
               y2 = 469;
            }
            else {
               y2+=5;
            }
            //npc interactions
            if(x==0 && (x2>=145 && x2<=400) && y2==135) { //arguement interaction
               if(interaction == 4) {
                  scene = 32;
                  y2=130;
               }
               else {
                  scene =26;
                  y2=130;
               }
            }
            if((x<=-460 && x>=-570) && x2==400 && y2==105) { //adam interaction
               if(interaction >= 2){
                  scene =28;
               }
               y2=100;
            }
         
         //character movement animation
            delay++;
            if(characterState!=10 && characterState!=11 && characterState!=3)   //if no down facing character state then default to two feet
            {
               characterState=3;
               delay=0;
            } 
            else if(characterState==10 && (delay==3 || idle))   //the cycle starts with left foot up
            {
               characterState=11;
               delay=0;
            }
            else if(characterState==11 && (delay==3 || idle))   //cycle continues with right foot up
            {
               characterState=3;
               delay=0;
            }
            else if(delay==3 || idle)  //cycle repeats with two feet
            {
               characterState=10;
               delay=0;
            }
            idle=false;
            repaint();
         
         } 
         if(e.getKeyCode() == KeyEvent.VK_A) {
            reset=0;
            if(x>=0) {  //leftmost wall
               x = 0;
               if(x2 <= 95) { //fence
                  x2 = 95;
               }
               x2-=5;
            }
            else if(x2==400){
               x+=5;
            }
            else if(x<=-1000) { //againgst the rightmost wall
               x = -1000;
               if(x2 <= 400) {
                  x2 = 405;
               }
               x2-=5;
            }
         //collision detections
         // if(y2<=435 && y2>=190 && x2==150 && x>=-55) { //ladder
         //             x=-55;
         //          }
         //          if((y2>=90 &&y2 <=185) && (x2==150 && x==-295)) { //seesaw
         //             x=-300;
         //          }
         //     }
         
            if((y2>=0 && y2<=80) && (x2 == 150 && x ==-355)){ //fence
               x=-360;
            }
            if((y2>=0 && y2<=80) && (x2 == 150 && x ==-960)){ //fence
               x=-965;
            }
            //npc interactions
            if(x==0 && x2==400 && (y2<=310 && y2>=135)) { //arguement interaction
               if(interaction == 4) {
                  scene = 32;
                  x2=405;
               }
               else {
                  scene =26;
                  x2=405;
               }
            }
            if(x==-570 && x2==400 && (y2>=105 && y2<=285)) { //adam interaction
               if(interaction >= 2){
                  scene =28;
               }
               x-=5;
            }
         
         //character animation
            delay++;
            if(characterState!=6 && characterState!=7 && characterState!=1)   //if not left facing character state currently then start the cycle by going to two feet
            {
               characterState=1;
               delay=0;
            } 
            else if(characterState==6 && (delay==3 || idle))   //next in the cycle is left foot up
            {
               characterState=7;
               delay=0;
            }
            else if(characterState==7 && (delay==3 || idle))   //next in the cycle is right foot up
            {
               characterState=1;
               delay=0;
            }
            else if(delay==3 || idle)  //repeat the cycle with two feet
            {
               characterState=6;
               delay=0;
            }
         
            idle=false;
            repaint();
         } 
         if (e.getKeyCode() == KeyEvent.VK_D) {
            reset=0;
         //farthest right boundary
            if(x<=-1000) {
               x = -1000;
               if(x2 >= 640) {   //character boundary (the fence)
                  x2 = 640;
               }
               x2+=5;   //character movement when hit wall
            }
            else if(x2==400){ //when character reaches a threashould of the middle fo the screen then switch to moving the background
               x-=5; //move background
            }
            else if(x>=0) {   //when up against the furthest left wall
               x = 0;
               if(x2 >= 400) {
                  x2 = 395;
               }
               x2+=5;
            }
         //collision detection 
         //fences
            if((y2>=0 && y2<=80) && (x2==150 && x == -480)) {
               x=-475;
            }
            if((y2>=0 && y2<=80) && (x2==230 && x == -1000)) {
               x2=225;
            }
         //NPC interactions
            if(x==0 && x2==145 && (y2<=310 && y2>=135)) { //arguement interaction
               if(interaction == 4) {
                  scene = 32;
                  x2=140;
               }
               else {
                  scene =26;
                  x2=140;
               }
            }
            if(x==-460 && x2==400 && (y2>=125 && y2<=270)) { //adam interaction
               if(interaction >= 2){
                  scene =28;
               }
               x+=5;
            }
            
         //character movement animation
            delay++;
            if(characterState!=4 && characterState!=5 && characterState!=0)   //if no right facing character state then default to two feet
            {
               characterState=0;
               delay=0;
            } 
            else if(characterState==4 && (delay==3 || idle))   //the cycle starts with left foot up
            {
               characterState=5;
               delay=0;
            }
            else if(characterState==5 && (delay==3 || idle))   //cycle continues with right foot up
            {
               characterState=0;
               delay=0;
            }
            else if(delay==3 || idle)  //cycle repeats with two feet
            {
               characterState=4;
               delay=0;
            }
         }
      }
      if(scene > 25) {
         chosen = 0;
         if(e.getKeyCode() == KeyEvent.VK_1) {
            chosen = 1;
            repaint();
         }
         if(e.getKeyCode() == KeyEvent.VK_2) {
            chosen = 2;
            repaint();
         }
      }
         
      idle=false;
      repaint();
   } 
   
    
   public void keyTyped(KeyEvent e) {}
   public void keyReleased(KeyEvent e) {}
   
   /**
    * Paints the background image and the message box with the current message.
    *
    * @param g the Graphics object
    */
   @Override
   public void paintComponent(Graphics g) {
      
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;
      
      g2d.drawImage(bg, 0, 0, this);
      removeKeyListener(this);
      removeMouseListener(this);
      g2d.drawImage(pauseIcon, 700, 10, this); // Draw the pause icon
      // Display the appropriate message based on the text index
      if(!pauseMenu){
         if (scene==0) {
            addMouseListener(this);
            g2d.drawImage(joe, 540, 180, this);
            box = new MessageBox("Mr. Joe: Hello, my name is Mr. Joe.");
            box.draw(g);
         } else if (scene==1) {
            addMouseListener(this);
            g2d.drawImage(joe, 540, 180, this);
            box = new MessageBox("Mr. Joe: We have recently created a conflict resolving AI, but it seems to lack human features, such as emotion and human etiquette. As a result, it unsuccessfully resolves conflicts.");
            box.draw(g);
         } else if (scene==2) {
            addMouseListener(this);
            g2d.drawImage(joe, 540, 180, this);
            box = new MessageBox("Mr. Joe: Your job is to train the AI to become more human, so it can resolve conflicts efficiently.");
            box.draw(g);
         } else if (scene==3) {
            addMouseListener(this);
            g2d.drawImage(joe, 540, 180, this);
            box = new MessageBox("Mr. Joe: Here is a demonstration of how the AI works right now.");
            box.draw(g);
         } else if (scene>=4 && scene<18) {
            if(xDirection > 0){
               g2d.drawImage(robot, x, y, this);
            }
            else if(xDirection < 0) {
               g2d.drawImage(robot1, x, y, this);
            }
            if(yDirection > 0) {
               g2d.drawImage(robot3, x, y, this);
            }
            else if(yDirection < 0) {
               g2d.drawImage(robot2, x, y, this);
            }
            g2d.drawImage(arguement, 570, 300, this);
            g2d.drawImage(adam, 170, 460, this);
            g2d.drawImage(ava, 360, 70, this);
            g2d.drawImage(neville, 130, 120, this);
            if(scene == 6) {
               addMouseListener(this);
               g2d.drawImage(avaBig, 540, 180, this);
               options[0] = "A) Hi Ava! My name is AI.";
               options[1] = "B) Why are those two fighting?";
               box = new MessageBox("Ava: Hi, I'm Ava. What's your name?", options, true, 2);
               box.draw(g);
            }
            else if(scene == 7) {
               addMouseListener(this);
               g2d.drawImage(avaBig, 540, 180, this);
               box = new MessageBox("Ava: Uh... Who even are you? Anyway... I think they're fighting because Ayanokoji did something bad to Rebecca. I would expect nothing better from him. I hate him.");
               box.draw(g);
            }
            else if(scene == 10){
               addMouseListener(this);
               g2d.drawImage(koji, 540, 180, this);
               options[0] = "A) What did you do to Rebecca?";
               options[1] = "B) Oi oi oi! Let's calm down here.";
               box = new MessageBox("Ayanokoji: WHAT DO YOU WANT?!", options, true, 1);
               box.draw(g);
            }
            else if(scene == 11){
               addMouseListener(this);
               g2d.drawImage(koji, 540, 180, this);
               box = new MessageBox("Ayanokoji: I'M NOT IN THE MOOD RIGHT NOW. GO AWAY BEFORE I BEAT YOU UP!!!");
               box.draw(g);
            }
            else if(scene == 12) {
               addMouseListener(this);
               g2d.drawImage(joe, 540, 180, this);
               box = new MessageBox("Mr. Joe: Notice how the AI asked Ava about the conflict without even knowing her, and how the AI asked Ayanokoji when he was clearly in a bad mood? Most people like to know who they're talking to first.");
               box.draw(g);
            }
            else if(scene == 13) {
               addMouseListener(this);
               g2d.drawImage(joe, 540, 180, this);
               box = new MessageBox("Mr. Joe: In addition, the AI didn't even talk to any of the other bystanders, they might have more information or different stories.");
               box.draw(g);
            }
            else if(scene == 14) {
               addMouseListener(this);
               g2d.drawImage(joe, 540, 180, this);
               box = new MessageBox("Mr. Joe: A human would appeal more to emotions, which means they would get to know Ava first and calm Ayanokoji down. They would also gather information from more people.");
               box.draw(g);
            }
            else if(scene == 15) {
               addMouseListener(this);
               g2d.drawImage(joe, 540, 180, this);
               box = new MessageBox("Mr. Joe: We need you to train the AI to become more human by resolving a conflict yourself, so the AI learns from you. Good luck!");
               box.draw(g);
            }
         } 
         else if(scene==18)
         {
            g2d.drawImage(arguement, 570, 300, this);
            g2d.drawImage(adam, 170, 460, this);
            g2d.drawImage(ava, 360, 70, this);
            g2d.drawImage(neville, 130, 120, this);
            for (int i = 0; i < 30; i++) { // 40 rows
               for (int j = 0; j < 40; j++) { // 30 columns
                  if (black[i][j]) {
                     g2d.setColor(Color.BLACK);
                     g2d.fillRect(j * 20, i * 20, 20, 20); // Each "pixel" is 20x20 pixels
                  }
               }
            }
         }
         else if (scene==19)
         {
            g2d.drawImage(bg2,x,0,this);
            g2d.drawImage(arguement, 590, 300, this);
            g2d.drawImage(adam, 170, 460, this);
            g2d.drawImage(ava, 360, 70, this);
            g2d.drawImage(neville, 130, 120, this);
            for (int i = 0; i < 30; i++) { // 40 rows
               for (int j = 0; j < 40; j++) { // 30 columns
                  if (!black[i][j]) {
                     g2d.setColor(Color.BLACK);
                     g2d.fillRect(j * 20, i * 20, 20, 20); // Each "pixel" is 20x20 pixels
                  }
               }
            }
         }
         else if (scene==20 || scene==21){
            g2d.drawImage(bg2,x,0,this);
         }
         else if (scene==22)
         {
            g2d.drawImage(bg2,0,0,this);
            g2d.drawImage(ResolveAI.character[2],x2,y,this);
         }
         else if(scene>=23)   
         {
            g2d.drawImage(bg2, x, 0, this);
            g2d.drawImage(ResolveAI.character[characterState],x2,y2,this);
            g2d.drawImage(pauseIcon, 700, 10, this); // Draw the pause icon
            if(scene==23){
               addMouseListener(this);
               g2d.drawImage(joe, 540, 180, this);
               box = new MessageBox("Mr. Joe: Did you get a good look at your surroundings?");
               box.draw(g);
            }
            else if(scene==24 && instructionStage==0){
               instructionStage=1;
               addMouseListener(this);
               g2d.drawImage(joe, 540, 180, this);
               box = new MessageBox("Mr. Joe: This is the scenario we designed for you to solve. Do your best and the AI will learn from your tactics, good luck!");
               box.draw(g);
            }
            else if(scene==24 && instructionStage>=1)
            {
               addMouseListener(this);
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
            
               if(instructionStage==2)
               {
                  g2d.setFont(instructionsFontBold);
                  String text = "For this portion of level 1,";
                  int textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
                  int textY = 220;
                  g2d.drawString(text, textX, textY);
               
                  text = "You will control the character";
                  textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
                  textY = 260;
                  g2d.drawString(text, textX, textY);
               
                  text = "You selected in the menu";
                  textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
                  textY = 300;
                  g2d.drawString(text, textX, textY);
               
                  text = "You will need to navigate the map using WASD";
                  textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
                  textY = 340;
                  g2d.drawString(text, textX, textY);
               
                  g2d.setFont(smallFont);
                  text = "Click anywhere to continue.";
                  textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
                  textY = 480;
                  g2d.drawString(text, textX, textY);
               }
               else if(instructionStage==3)
               {
                  g2d.setFont(instructionsFontBold);
                  String text = "You can walk up to the other characters";
                  int textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
                  int textY = 200;
                  g2d.drawString(text, textX, textY);
               
                  text = "And here their stories!";
                  textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
                  textY = 240;
                  g2d.drawString(text, textX, textY);
               
                  g2d.drawImage(wasdInstructions, 250, 240, this);
               
                  g2d.setFont(smallFont);
                  text = "Click anywhere to continue with the level!";
                  textX = (500 - g2d.getFontMetrics().stringWidth(text)) / 2 + 150; // Centered horizontally within the panel
                  textY = 480;
                  g2d.drawString(text, textX, textY);
               }
            
            }
            else if(scene==25)
            {
               addKeyListener(this);
               addMouseListener(this);
               if(!initiateRun){
                  initiateRun=true;
                  idle=true;
                  stateChecker();
               }
            }
            else if(scene == 26) {
               addKeyListener(this);
               g2d.drawImage(koji, 540, 180, this);
               if(corrects[0]==false){
                  options[0] = "1) Why are you guys arguing?";
                  options[1] = "2) Hey, let's calm down here.";
                  box = new MessageBox("Ayanokoji: WHAT DO YOU WANT?!", options);
                  box.draw(g);
               }
               if(chosen == 1 && corrects[0]==false) {
                  removeKeyListener(this);
                  addMouseListener(this);
                  g2d.drawImage(koji, 540, 180, this);
                  box = new MessageBox("Ayanokoji: Who are you? GET OUT OF MY FACE!");
                  box.draw(g);
                  interaction = 1;
               }
               else if(chosen==2 || corrects[0]==true){
                  removeKeyListener(this);
                  addMouseListener(this);
                  g2d.drawImage(koji, 540, 180, this);
                  box = new MessageBox("Ayanokoji: Im sorry, I shouldn't have talked to you like that, but I don't even know you. You haven't even talked to my friends! Go talk to Ava, she is the girl with black hair.");
                  box.draw(g);
                  if(scene!=25 && corrects[0]==false) {
                     correct++;
                     corrects[0]=true;
                  }
                  interaction = 1;
               }
               System.out.println(chosen);
            }
            else if(scene == 27) {
               addKeyListener(this);
               g2d.drawImage(avaBig, 540, 180, this);
               if(corrects[1]==false)
               {
                  options[0] = "1) Why are those guys arguing?";
                  options[1] = "2) Hi, my name is " + ResolveAI.username + ".";
                  box = new MessageBox("Ava: Hey, what's up?", options);
                  box.draw(g);
               }
               if(chosen == 1 && corrects[1]==false) {
                  removeKeyListener(this);
                  addMouseListener(this);
                  g2d.drawImage(avaBig, 540, 180, this);
                  box = new MessageBox("Ava: Uh, mind your own business! Anyway... I think they are fighting because Ayanokoji. My friend Adam can tell you more, he wears all black.");
                  box.draw(g);
                  interaction = 2;
               }
               else if(chosen==2 || corrects[1]==true){
                  if(scene!=25 && corrects[1]==false) {
                     correct++;
                     corrects[1]=true;
                  }
                  removeKeyListener(this);
                  addMouseListener(this);
                  g2d.drawImage(avaBig, 540, 180, this);
                  box = new MessageBox("Ava: Nice to meet you " + ResolveAI.username + "!" + " You must be wondering why those guys are fighting. It's because of Ayanokoji. I hate people with red hair. But, my friend Adam can tell you more since he is closer with them. He wears all black.");              
                  box.draw(g);
                  interaction = 2;
               }
            }
            else if(scene == 28) {
               addKeyListener(this);
               g2d.drawImage(adamBig, 540, 180, this);
               if(corrects[2]==false){
                  options[0] = "1) Are you ok? You look a little sad.";
                  options[1] = "2) Why are those guys arguing?";
                  box = new MessageBox("Adam: Hi.", options);
                  box.draw(g);
               }
               if(chosen == 1 || corrects[2]==true) {
                  if(scene!=25 && corrects[2]==false) {
                     correct++;
                     corrects[2]=true;
                  }
                  removeKeyListener(this);
                  addMouseListener(this);
                  g2d.drawImage(adamBig, 540, 180, this);
                  box = new MessageBox("Adam: Oh this? This is just how I dress. I heard you and Ava there talking about the argument. She probably said its Ayanokoji's fault, but it's Rebecca's fault. Talk to my friend Neville, he'll tell you the full story.");
                  box.draw(g);
                  interaction = 3;
               }
               else if(chosen==2 && corrects[2]==false){
                  removeKeyListener(this);
                  addMouseListener(this);
                  g2d.drawImage(adamBig, 540, 180, this);
                  box = new MessageBox("Adam: Rebecca did something bad. My friend Neville can tell you more.");              
                  box.draw(g);
                  interaction = 3;
               }
            }
            else if(scene == 29) {
               addKeyListener(this);
               g2d.drawImage(nevilleBig, 540, 180, this);
               if(corrects[3]==false){
                  options[0] = "1) My friend Adam said you can tell me more.";
                  options[1] = "2) Tell me more about the argument.";
                  box = new MessageBox("Neville: Wassup.", options);
                  box.draw(g);
                  temp = chosen;
               }
               if(chosen == 1 || corrects[3]==true) {
                  if(scene!=25 && corrects[3]==false) {
                     correct++;
                     corrects[3]=true;
                  }
                  removeKeyListener(this);
                  addMouseListener(this);
                  g2d.drawImage(nevilleBig, 540, 180, this);
                  box = new MessageBox("Neville: Oh yes. I know the whole story.");
                  box.draw(g);
                  interaction = 4;
               }
               else if(chosen == 2 && corrects[3]==false){
                  removeKeyListener(this);
                  addMouseListener(this);
                  g2d.drawImage(nevilleBig, 540, 180, this);
                  box = new MessageBox("Neville: Oh yes. I know the whole story.");
                  box.draw(g);
                  interaction = 4;
               }
            }
            if(scene == 30) {
               addMouseListener(this);
               g2d.drawImage(nevilleBig, 540, 180, this);
               box = new MessageBox("Neville: Basically, Ayanokoji gave Rebecca a toy to borrow, but she lost it. Ayanokoji is really mad now because it was his favorite toy.");
               box.draw(g);
            }
            if(temp == 1 && scene == 31) {
               addMouseListener(this);
               g2d.drawImage(nevilleBig, 540, 180, this);
               box = new MessageBox("Neville: I think Rebecca should just apologize, but she doesn't want to do that.");
               box.draw(g);
            }
            if(scene == 32) {
               addMouseListener(this);
               g2d.drawImage(koji, 540, 180, this);
               box = new MessageBox("Ayanokoji: WHY DID YOU LOSE MY TOY??!");
               box.draw(g);
            }
            if(scene == 33) {
               addMouseListener(this);
               g2d.drawImage(rebeccaBig, 540, 180, this);
               box = new MessageBox("Rebecca: ITS NOT MY FAULT!! ITS YOUR FAULT FOR BUYING SUCH A SMALL EASY TO LOSE TOY!!!");
               box.draw(g);
            }
            if(scene == 34 && correct >= 2) {
               addMouseListener(this);
               g2d.drawImage(playerBig, 540, 180, this);
               box = new MessageBox("You: Hey guys, let's talk it out. Rebecca, apologize for losing his toy.");
               box.draw(g);
            }
            else if(scene == 34 && correct < 2) {
               addMouseListener(this);
               g2d.drawImage(playerBig, 540, 180, this);
               box = new MessageBox("You: Guys just suck it up and stop fighting.");
               box.draw(g);
            }
            if(scene == 35&& correct >=2) {
               addMouseListener(this);
               g2d.drawImage(rebeccaBig, 540, 180, this);
               box = new MessageBox("Rebecca: You're right. Ayanokoji, I'm sorry for losing your toy. I really didn't mean to.");
               box.draw(g);
            }
            else if(scene == 35&& correct <2) {
               addMouseListener(this);
               g2d.drawImage(koji, 540, 180, this);
               box = new MessageBox("Ayanokoji: YOU AGAIN?! SCRAM BEFORE I BEAT YOU UP!!!");
               box.draw(g);
            }
            if(scene == 36 && correct < 2) {
               ResolveAI.screen++;
               ResolveAI.game();
            }
            if(scene == 36&& correct >=2) {
               addMouseListener(this);
               g2d.drawImage(koji, 540, 180, this);
               box = new MessageBox("Ayanokoji: It's ok. I'm sorry for overreacting. I can buy a new one anyway.");
               box.draw(g);
            }
            if(scene == 37&& correct >=2) {
               addMouseListener(this);
               g2d.drawImage(rebeccaBig, 540, 180, this);
               box = new MessageBox("Rebecca: So... Are we friends again?");
               box.draw(g);
            }
            if(scene == 38&& correct >=2) {
               addMouseListener(this);
               g2d.drawImage(koji, 540, 180, this);
               box = new MessageBox("Ayanokoji: Yes, we can be friends again.");
               box.draw(g);
               ResolveAI.screen++;
               ResolveAI.game();
            }
         }
      }
      //pause
      else if(pauseMenu) {
      addMouseListener(this);
         g2d.setColor(Color.white);
         g2d.fillRect(0,0,800,600);
         Color calmBlue = new Color(155, 227, 255);
         g2d.setColor(calmBlue);
         g2d.fillRoundRect(300, 100, 200, 70,30,30); // Position and size of the resume button
         g2d.fillRoundRect(300, 240, 200, 70,30,30); // Position and size of the main menu button
         g2d.fillRoundRect(300, 380, 200, 70,30,30);
         // Set font for text
         Font font = new Font("Sans Serif", Font.BOLD, 20);
         g2d.setFont(font);
         g2d.setColor(Color.BLACK);
         //text
         g2d.drawString("Resume", 365, 140);
         g2d.drawString("Main Menu", 350, 280);
         g2d.drawString("Save", 380, 420);
      }   
   
   }
   public void stateChecker() //resets the state after a period of time (always runs after scene 25)
   {
      TimerTask resetState = 
               new TimerTask() {
                  public void run() {
                     reset++;
                     if (reset==20) {  //once reset hits 20 then the character returns to the direction they were facing with two feet
                        if(characterState%2==0 && characterState>=4)
                        {
                           characterState-=(characterState-4)/2+4;   //the feet character models start after 4 which is the -4 and +4 also there are two feet models for every direction hence the /2 
                        } 
                        else if(!idle && characterState>=4)
                        {
                           characterState-=(characterState-5)/2+5;   //as there are two feet models this just modifies the 4 to a 5 to account for the other foot model this is determined in the first if statement by a %2==0
                        }
                        reset=0;
                        idle=true;
                        repaint();
                     }
                     //System.out.println(idle);
                  }
               };
      resetTimer.scheduleAtFixedRate(resetState, 0, 50);
   }
}