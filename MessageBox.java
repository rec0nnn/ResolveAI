import java.awt.*;

/**
 * Harry Yang 0% Recon Chen 100% (ideas on how to from brian song and luka jovanovic)
 * last update 2024, 06, 08
 * Ms. Krasteva
 * The message box class creats a default message box that can be implemented all throughout our levels.
 */
 
public class MessageBox {

   /** Text to be displayed. */
   private String text;

   /** Text to be displayed formatted into an array based on length. */
   private String[] textArray;

   /** Array of options for a question text box. */
   private String[] options;
   
   /** Indicates if the AI is providing the options. */
   private boolean AI;
   
   /** The chosen option index. */
   private int chosen;

   /** Indicates if the MessageBox is asking a question. */
   private boolean question;

   /** X position of the box. */
   private static final int x = 6;

   /** Y position of the box. */
   private static final int y = 400;

   /** Width of the box. */
   private static final int w = 775;

   /** Height of the box. */
   private static final int h = 160;

   /** Border thickness of the box. */
   private static final int s = 10;

   /** Maximum characters in a line. */
   private static final int max = 60;
  
   /** Border color. */
   private static final Color border = new Color(213,153,91);
  
   /** Text box color. */
   private static final Color textbox = new Color(255,205,146);

   /** Regular font used in the box. */
   private static final Font font = new Font("Monospaced", Font.BOLD, 20);

   /** Medium font used for options. */
   private static final Font mediumFont = new Font("Monospaced", Font.BOLD, 15);

   /** Smaller font used for additional information. */
   private static final Font smallFont = new Font("Monospaced", Font.BOLD, 10);

   /**
    * Constructs a MessageBox with the specified text.
    *
    * @param text The text to be displayed.
    */
   public MessageBox(String text) {
      this.text = text;
      this.options = new String[0];
      this.question = false;
      this.AI = false;
   
      this.textArray = new String[text.length() / max + 1];  // Array with length of predicted number of lines required
      for (int i = 0; i < text.length() / max + 1; i++) {
         try {
            textArray[i] = text.substring(i * max, (i + 1) * max);  // Add each line based on the number of characters that fit
            if (textArray[i].charAt(textArray[i].length() - 1) != ' ' && textArray[i].charAt(textArray[i].length() - 1) != '!' && textArray[i].charAt(textArray[i].length() - 1) != '.' && textArray[i].charAt(textArray[i].length() - 1) != '?') {
               textArray[i] += "-";  // Hyphenate to accommodate for split words
            }
         } catch (StringIndexOutOfBoundsException e) {  // If the index is out of bounds then we take all that is left over
            textArray[i] = text.substring(i * max);
         }
      }
   }

   /**
    * Constructs a MessageBox with the specified text and options.
    *
    * @param text The text to be displayed.
    * @param options The options for the question.
    */
   public MessageBox(String text, String[] options) {
      this.text = text;
      this.options = options;
      this.question = true;
      this.AI = false;
   
      this.textArray = new String[(text.length() / (max / 2)) + 1];  // Array with length of predicted number of lines required
      for (int i = 0; i < textArray.length; i++) {  // Add each line based on the number of characters that fit
         try {
            textArray[i] = text.substring(i * max / 2, (i + 1) * max / 2);  // Add each line based on the number of characters that fit
            if (textArray[i].charAt(textArray[i].length() - 1) != ' ' && textArray[i].charAt(textArray[i].length() - 1) != '!' && textArray[i].charAt(textArray[i].length() - 1) != '.' && textArray[i].charAt(textArray[i].length() - 1) != '?') {
               textArray[i] += "-";  // Hyphenate to accommodate for split words
            }
         } catch (StringIndexOutOfBoundsException e) {
            textArray[i] = text.substring(i * max / 2);  // If the index is out of bounds then we take all that is left over
         }
      }
   }
   
   /**
    * Constructs a MessageBox with the specified text, options, AI flag, and chosen option index.
    *
    * @param text The text to be displayed.
    * @param options The options for the question.
    * @param AI Indicates if the AI is providing the options.
    * @param chosen The chosen option index.
    */
   public MessageBox(String text, String[] options, boolean AI, int chosen) {
      this.text = text;
      this.options = options;
      this.question = true;
      this.AI = true;
      this.chosen = chosen;
   
      this.textArray = new String[(text.length() / (max / 2)) + 1];  // Array with length of predicted number of lines required
      for (int i = 0; i < textArray.length; i++) {  // Add each line based on the number of characters that fit
         try {
            textArray[i] = text.substring(i * max / 2, (i + 1) * max / 2);  // Add each line based on the number of characters that fit
            if (textArray[i].charAt(textArray[i].length() - 1) != ' ' && textArray[i].charAt(textArray[i].length() - 1) != '!' && textArray[i].charAt(textArray[i].length() - 1) != '.' && textArray[i].charAt(textArray[i].length() - 1) != '?') {
               textArray[i] += "-";  // Hyphenate to accommodate for split words
            }
         } catch (StringIndexOutOfBoundsException e) {
            textArray[i] = text.substring(i * max / 2);  // If the index is out of bounds then we take all that is left over
         }
      }
   }

   /**
    * Draws the message box with the text and options.
    *
    * @param g The Graphics object used for drawing.
    */
   public void draw(Graphics g) {
   
      // Draw the box
      g.setColor(border);
      g.fillRect(x, y, w, h);
   
      g.setColor(textbox);
      g.fillRect(x + s, y + s, w - s * 2, h - s * 2);
   
      g.setColor(Color.BLACK);
      g.setFont(font);
   
      // Draw each line of text
      for (int i = 0; i < textArray.length; i++) {
         g.drawString(textArray[i], x + 20, y + (i * 30) + 40);
      }
   
      if (!question) {  // If it's not a question
         g.setFont(smallFont);
         g.drawString("click anywhere to continue", x + 305, y + 140);
      } else if (AI) {
         g.setFont(mediumFont); 
         for (int i = 0; i < options.length; i++) {  // Draw the options
            if (i == chosen - 1) {
               g.setColor(new Color(4, 204, 34));
            }
            g.drawString(options[i], x + 380, y + (i * 30) + 50);
            g.setColor(Color.BLACK);
         }
         g.setFont(smallFont);
         g.drawString("click anywhere to continue", x + 305, y + 140);
      } else {
         g.setFont(mediumFont); 
         for (int i = 0; i < options.length; i++) {  // Draw the options
            g.drawString(options[i], x + 390, y + (i * 30) + 50);
         }
         g.setFont(smallFont);
         g.drawString("press the corresponding key to answer", x + 260, y + 140);
      }
   }
}
