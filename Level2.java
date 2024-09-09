import java.awt.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Harry Yang 80% Recon Chen 20%
 * last update 2024, 06, 11
 * Ms. Krasteva
 * Level 2 is a demonstration of the AI that plays like how you played in level 1
 */
 
public class Level2 extends JPanel implements MouseListener {

   private Image bg, byeBg; // Background image
   private Image robot, robot1, robot2, robot3; //robot directions
   private Image joe;   //joe
   private Image arguement;   //the two characters arguing
   private Image ava, avaBig; //ava and ava used in text boxes
   private Image adam, adamBig;  //adam and adam used in text boxes
   private Image neville, nevilleBig;  //neville and neville used in text boxes
   private Image koji;  //image for text Ayanokoji
   private MessageBox box; // Message box to display messages
   private int scene; // Index to track the current message
   private int x, y; //x and y variables for animation
   private int xDirection, yDirection; //to dictate which way the robot faces
   private static boolean[] buttons = {false, false, false, false};  // up down left right

   /**
    * Constructor for Level1 class.
    * Initializes the background image, sets up the mouse listener, and repaints the panel.
    */
   public Level2() {
      scene = 0;
      x = 350;
      y = 350;
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
      arguement = ResolveAI.readImg("assets/arguement.png");
      arguement = arguement.getScaledInstance(176, 110, Image.SCALE_SMOOTH);
      ava = ResolveAI.readImg("assets/ava1.png");
      ava = ava.getScaledInstance(60, 80, Image.SCALE_SMOOTH);
      avaBig = ResolveAI.readImg("assets/ava1.png");
      avaBig = avaBig.getScaledInstance(240, 320, Image.SCALE_SMOOTH);
      adam = ResolveAI.readImg("assets/adam.png");
      adam = adam.getScaledInstance(60, 80, Image.SCALE_SMOOTH);
      adamBig = ResolveAI.readImg("assets/adam.png");
      adamBig = adamBig.getScaledInstance(240, 320, Image.SCALE_SMOOTH);
      neville = ResolveAI.readImg("assets/neville.png");
      neville = neville.getScaledInstance(60, 80, Image.SCALE_SMOOTH);
      nevilleBig = ResolveAI.readImg("assets/neville.png");
      nevilleBig = nevilleBig.getScaledInstance(240, 320, Image.SCALE_SMOOTH);
      koji = ResolveAI.readImg("assets/ayanokoji1M.png");
      koji = koji.getScaledInstance(240, 320, Image.SCALE_SMOOTH);
      bg = ResolveAI.readImg("assets/lvl2Background.png");
      bg = bg.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
      byeBg = ResolveAI.readImg("assets/MenuBackground.png");
      byeBg = byeBg.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
      addMouseListener(this);
      play();
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
      
      if(scene==2)
      {
         yMove(350, 300, -5);
      }
      else if(scene == 5)
      {
         yMove(350, 440, 5);
      }
      else if(scene==6)
      {
         xMove(350,120,-5);
      }
      else if(scene==10)
      {
         yMove(400, 70, -5);
      }
      else if(scene==11)
      {
         xMove(120, 115, -5);
      }
      else if(scene==15)
      {
         xMove(120, 500, 5);
      }
      else if(scene==19)
      {
         xMove(500, 350, -5);
      }
      else if(scene==20)
      {
         yMove(70, 160, 5);
      }
   }
   

   /**
    * Handles mouse click events. Increments the text index and repaints the panel.
    *
    * @param e the mouse event
    */
   @Override
   public void mouseClicked(MouseEvent e) {
      if(scene!=23){
         scene++;
         play();
         repaint();
      }
      else 
      {
         ResolveAI.screen=1;
         ResolveAI.game();
      }
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
      removeMouseListener(this);
   
      // Display the appropriate message based on the text index
      if (scene==0) {
         addMouseListener(this);
         g2d.drawImage(joe, 540, 180, this);
         box = new MessageBox("Mr. Joe: Hey it's me Joe again. Ready to see the fruits of your teachings?");
         box.draw(g);
      } else if (scene==1) {
         addMouseListener(this);
      
         g2d.drawImage(joe, 540, 180, this);
         box = new MessageBox("Mr. Joe: We trained our AI based on your actions and movements and are ready to show you the new and improved AI!");
         box.draw(g);
      } else if (scene>=2) {
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
         g2d.drawImage(arguement, 280, 200, this);
         g2d.drawImage(adam, 45, 70, this);
         g2d.drawImage(ava, 570, 70, this);
         g2d.drawImage(neville, 50, 440, this);
         if(scene==3 || scene==4)
         {
            int temp=1;
            if(Level1.correct>=1)
            {
               temp=2;
            }
            addMouseListener(this);
            g2d.drawImage(koji, 540, 180, this);
            String options[] = {"1) Why are you guys arguing?", "2) Hey, let's calm down here."};
            box = new MessageBox("Ayanokoji: HEY GET OUT OF HERE!!", options, true, temp);
            box.draw(g);
            if(Level1.correct>=1 && scene==4)
            {
               g2d.drawImage(koji, 540, 180, this);
               box = new MessageBox("Ayanokoji: Oh, sorry. I got so angry that I started yelling. You won't listen to my story anyways. If you still want to know more go talk to Neville, he's the dude with the weird red hat.");
               box.draw(g);
            } 
            else if(Level1.correct<1 && scene==4)
            {
               g2d.drawImage(koji, 540, 180, this);
               box = new MessageBox("Ayanokoji: LEAVE, GO ASK SOMEONE ELSE");
               box.draw(g);
            }
         }
         else if(scene >= 7 && scene <= 9) {
            int temp=2;
            if(Level1.correct>=2)
            {
               temp=1;
            }
            addMouseListener(this);
            g2d.drawImage(nevilleBig, 540, 180, this);
            String options[] = {"1) Hi Neville! I'm AI.", "2) Why are those two fighting?"};
            box = new MessageBox("Neville: Hi, I'm Neville. Who are you?", options, true, temp);
            box.draw(g);
            if(Level1.correct>=2 && scene>=8)
            {
               g2d.drawImage(nevilleBig, 540, 180, this);
               if(scene==8)
               {
                  options[0] = "1) Do you know why they are fighting?";
                  options[1] = "2) Nah, nothing.";
                  box = new MessageBox("Neville: Hi, AI. Do you need anything?.", options, true, 1);
                  box.draw(g);
               }
               if(scene==9)
               {
                  box = new MessageBox("Neville: I know they are always fighting, Ayanokoji is normally in the right. Sorry I dont know that much go ask Adam, he knows more than me.");
                  box.draw(g);
               }
            } 
            else if(Level1.correct<2 && scene>=8)
            {
               g2d.drawImage(nevilleBig, 540, 180, this);
               options[0] = "1) AI.";
               options[1] = "2) Ur mom.";
               box = new MessageBox("Neville: Uhhh I dont know? Who even are you?", options, true, 1);
               box.draw(g);
               if(scene==9)
               {
                  box = new MessageBox("Neville: ...Ok. Like I said I dont really know much try asking Adam, he's the emo looking one.");
                  box.draw(g);
               }
            }
         }
         else if(scene>=12 && scene<=14)
         {
            int temp=2;
            if(Level1.correct>=3)
            {
               temp=1;
            }
            addMouseListener(this);
            g2d.drawImage(adamBig, 540, 180, this);
            String options[] = {"1) Whats up Adam.", "2) What do you know."};
            box = new MessageBox("Adam: Hey what's up lil bro.", options, true, temp);
            box.draw(g);
            if(Level1.correct>=3 && scene>=13)
            {
               g2d.drawImage(adamBig, 540, 180, this);
               if(scene==13)
               {
                  options[0] = "1) What do you know about the fight?";
                  options[1] = "2) Nothing much.";
                  box = new MessageBox("Adam: Yo what do wanna know.", options, true, 1);
                  box.draw(g);
               }
               if(scene==14)
               {
                  box = new MessageBox("Adam: I think Rebbeca stole something from Ayanokoji again. Ugh I hate that girl she's always taking things. I dont know everything though go talk Ava she's Rebecca's best friend.");
                  box.draw(g);
               }
            } 
            else if(Level1.correct<3 && scene>=13)
            {
               g2d.drawImage(adamBig, 540, 180, this);
               options[0] = "1) I'm gonna resolve the conflict.";
               options[1] = "2) Ur mom.";
               box = new MessageBox("Neville: Uhhh what do i know? What's it to you", options, true, 1);
               box.draw(g);
               if(scene==14)
               {
                  box = new MessageBox("Neville: ...Ok. I don't know go try asking Ava also you migtht wanna try to be nicer.");
                  box.draw(g);
               }
            }
         }
         else if(scene>=16 && scene<=18)
         {
            int temp=2;
            if(Level1.correct>=4)
            {
               temp=1;
            }
            addMouseListener(this);
            g2d.drawImage(avaBig, 540, 180, this);
            String options[] = {"1) My name is AI. ", "2) You don't need to know that."};
            box = new MessageBox("Ava: Hello? Whats your name?.", options, true, temp);
            box.draw(g);
            if(Level1.correct>=4 && scene>=17)
            {
               g2d.drawImage(avaBig, 540, 180, this);
               if(scene==17)
               {
                  options[0] = "1) Yea thats right I hear you're Rebbecas best friend.";
                  options[1] = "2) Yes.";
                  box = new MessageBox("Ava: I assume you want to know about the argument.", options, true, 1);
                  box.draw(g);
               }
               if(scene==18)
               {
                  box = new MessageBox("Ava: You're right about that! I know Rebecca gets into alot of fights with Ayanokoji but this time Rebbeca actually didn't take anything Ayanokoji probably lost it.");
                  box.draw(g);
               }
            } 
            else if(Level1.correct<4 && scene>=17)
            {
               g2d.drawImage(avaBig, 540, 180, this);
               options[0] = "1) Tell me about the fight.";
               options[1] = "2) What do you know..";
               box = new MessageBox("Ava: Uhhh ok, what do you want then? ", options, true, 2);
               box.draw(g);
               if(scene==18)
               {
                  box = new MessageBox("Ava: Uhhh I don't think you'll believe me but Rebecca didn't do it.");
                  box.draw(g);
               }
            }
            
         }
         else if(scene>=21 && scene<=22)
         {
            int temp=1;
            if(Level1.correct>=4)
            {
               temp=2;
            }
            addMouseListener(this);
            g2d.drawImage(koji, 540, 180, this);
            String options[] = {"1) Rebbeca stole your toy. ", "2) Check your pockets."};
            box = new MessageBox("Ayanokoji: Oh, it's you again AI. What do you have to say?.", options, true, temp);
            box.draw(g);
            if(Level1.correct>=4 && scene==22)
            {
               g2d.drawImage(koji, 540, 180, this);
               box = new MessageBox("Ayanokoji: Oh I can't believe I did that. I'm sorry Rebecca, the toys were in my pocket the entire time");
               box.draw(g);
            } 
            else if(Level1.correct<4 && scene==22)
            {
               g2d.drawImage(koji, 540, 180, this);
               box = new MessageBox("Ayanokoji: I knew it! You've stolen things from me for too long Rebbeca");
               box.draw(g);
            }
         }
         else if(scene==23)
         {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Leaderboard.txt", true))) {
               writer.write(ResolveAI.username);
               writer.newLine();            
               writer.write(Level1.correct*200+"");
               writer.newLine(); // Add a new line
            } catch (IOException e) {
               e.printStackTrace();
            }
            addMouseListener(this);
            g2d.drawImage(byeBg, 0, 0, this);
            
            Font titleFont = new Font("SansSerif", Font.BOLD, 50);
            Font textFont = new Font("SansSerif", Font.BOLD, 30);
         
            g2d.setFont(titleFont);
            String title = "The End!";
            int titleX = (500 - g2d.getFontMetrics().stringWidth(title)) / 2 + 150; // Centered horizontally within the panel
            int titleY = 300;
            g2d.drawString(title, titleX, titleY);
            
            title = "Thank you for playing";
            titleX = (500 - g2d.getFontMetrics().stringWidth(title)) / 2 + 150; // Centered horizontally within the panel
            titleY = 400;
            g2d.drawString(title, titleX, titleY);
            
            g2d.setFont(textFont);
            title = "In your run you made the AI "+Level1.correct*20+"% more human";
            titleX = (500 - g2d.getFontMetrics().stringWidth(title)) / 2 + 150; // Centered horizontally within the panel
            titleY = 450;
            g2d.drawString(title, titleX, titleY);
         }      
      }        
   }

}