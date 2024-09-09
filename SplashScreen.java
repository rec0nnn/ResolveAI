import java.awt.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Harry Yang 0% Recon Chen 100%
 * last update 2024, 06, 10
 * Ms. Krasteva
 * The splashscreen creates fade in and fade out animtaions along with a short movement animation introducing the logo and name of the game
 */
 
public class SplashScreen extends JPanel {
    private Image bg, bob, emily, zigzag, logo, logoSplash;
    private float alpha = 0.0f; // Start with fully transparent
    private boolean increasing = true; // To track whether the alpha is increasing or decreasing
    private boolean animate = false; // To track when to animate the characters
    private boolean logoSplashPhase = true; // To track if the logo splash phase is active
    private int x = 0;  //x value of animations

    /**
     * Constructor for the SplashScreen class. Loads the images for the splash screen.
     */
    public SplashScreen() {
        // Load the images
        bg = ResolveAI.readImg("assets/MenuBackground.png");
        bg = bg.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        zigzag = ResolveAI.readImg("assets/zigzag.png");
        zigzag = zigzag.getScaledInstance(100, 460, Image.SCALE_SMOOTH);
        bob = ResolveAI.readImg("assets/bob1.png");
        bob = bob.getScaledInstance(144, 192, Image.SCALE_SMOOTH);
        emily = ResolveAI.readImg("assets/emily.png");
        emily = emily.getScaledInstance(144, 192, Image.SCALE_SMOOTH);
        logo = ResolveAI.readImg("assets/logo.png");
        logo = logo.getScaledInstance(200, 66, Image.SCALE_SMOOTH);
        logoSplash = ResolveAI.readImg("assets/LogoSplash.png");
    }

    /**
     * Starts the splash screen animations including fading effects and character movements.
     */
    public void play() {
        // Create a Timer to update the opacity
        TimerTask fadeIn = new TimerTask() {
            public void run() {
                // Increment or decrement alpha
                if (logoSplashPhase) {
                    if (increasing) {
                        alpha += 0.025f;
                        if (alpha >= 1.0f) {
                            alpha = 1.0f;
                            increasing = false;
                        }
                    } else {
                        alpha -= 0.025f;
                        if (alpha <= 0.0f) {
                            alpha = 0.0f;
                            increasing = true;
                            logoSplashPhase = false; // End logo splash phase
                        }
                    }
                } else {
                    if (increasing) {
                        alpha += 0.025f;
                        if (alpha >= 1.0f) {
                            alpha = 1.0f;
                            increasing = false;
                            animate = true;
                            this.cancel();
                        }
                    }
                }
                repaint();
            }
        };

        // Create a Timer to schedule the TimerTask
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(fadeIn, 0, 50);

        // TimerTask for moving characters
        TimerTask move = new TimerTask() {
            public void run() {
                if (animate) {
                    x += 8; // Move characters by 8 pixels per tick
                    if (x >= 550) {
                        x = 550; // Stop the movement when x reaches the limit
                        // Switch to the new screen
                        MainMenu.fromSplashScreen = true;
                        ResolveAI.screen++;
                        ResolveAI.game();
                        this.cancel();
                    }
                    repaint();
                }
            }
        };
        // Schedule the move task to start after fade-in is complete
        timer.scheduleAtFixedRate(move, 0, 50); // Adjust the delay as needed
    }

    /**
     * Overrides the paintComponent method to draw custom graphics.
     *
     * @param g the Graphics object used for drawing
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set the composite to the current alpha value
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        if (logoSplashPhase) {
            // Draw the logo splash image with the current opacity
            g2d.drawImage(logoSplash, 0, 0, this);
        } else {
            // Draw the background image with the current opacity
            g2d.drawImage(bg, 0, 0, this);

            // Draw the characters
            if (!animate) {
                g2d.drawImage(bob, 206, 308, this);
                g2d.drawImage(emily, 450, 308, this);
                g2d.drawImage(zigzag, 360, 200, this);
            } else {
                g2d.drawImage(bob, 206 - x, 308, this);
                g2d.drawImage(emily, 450 + x, 308, this);
                g2d.drawImage(zigzag, 360, 200, this);
            }
        }
    }
}
