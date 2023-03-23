import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

class CalculatorSimulator {
    // Have higher scope because they're accessed in calcButtonListener
    JFrame frame;
    JTextField emptyScreen1;
    JTextField emptyScreen2;
    JTextField screen;
    JTextField MScreen;
    JTextField minusScreen;
    JTextField EScreen;

    private double accumulator = 0.0;
    private double memory = 0.0;

    private String currentOperator = null;

    public static void main(String args[]) {
        new CalculatorSimulator();
    }

    private CalculatorSimulator() {
        try 
        {
            //This allows for changes to the UI of elements added to the frame
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Creating the frame of the calculator which has a title and a size
        frame = new JFrame();
        frame.setTitle("Casio Calculator");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 600);

        // Initializing the screen where the inputs and result will be showed. It cannot
        // be edited by just clicking on the text field
        screen = new JTextField(27);
        screen.setHorizontalAlignment(JTextField.RIGHT);
        screen.setEditable(false);
        
        //Empty space to force the text to be at the bottom right

        emptyScreen1 = new JTextField(27);
        emptyScreen1.setEditable(false);

        //Empty space to force the text to be at the bottom right
        emptyScreen2 = new JTextField(27);
        emptyScreen2.setEditable(false);

        //Adding all the screen elements for it to be larger
        screen.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        emptyScreen1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        emptyScreen2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        //Adding the different text areas for M, - and E
        MScreen = new JTextField(3);
        minusScreen = new JTextField(3);
        EScreen = new JTextField(3);

        //Making them uneditable
        MScreen.setEditable(false);
        minusScreen.setEditable(false);
        EScreen.setEditable(false);

        //Making the values align to the centre
        MScreen.setHorizontalAlignment(JTextField.CENTER);
        minusScreen.setHorizontalAlignment(JTextField.CENTER);
        EScreen.setHorizontalAlignment(JTextField.CENTER);

        //Removing the borders on the text fields so that they look like one screen
        MScreen.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        minusScreen.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        EScreen.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        //This is adding a font to the screens from digital-7 (mono).ttf
        try 
        {
            String filename="src/digital-7 (mono).ttf";
            Font font;
            font = Font.createFont(Font.TRUETYPE_FONT, new File(filename));
            font = font.deriveFont(Font.BOLD, 28);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            screen.setFont(font);
            MScreen.setFont(font);
            minusScreen.setFont(font);
            EScreen.setFont(font);
            emptyScreen1.setFont(font);
            emptyScreen2.setFont(font);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Creating the first panel that houses the titles and the screen itself so that
        // its layout can differ from the buttons themselves
        JPanel titleAndScreenPanel = new JPanel(new GridBagLayout());
        titleAndScreenPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;
        titleAndScreenPanel.setBackground(Color.gray);

        //GridBagLayout for changing putting the specific buttons on certain grids
        constraints.gridx = 0;
        constraints.gridy = 0;
        titleAndScreenPanel.add(MScreen, constraints);
        constraints.gridy = 1;
        titleAndScreenPanel.add(minusScreen, constraints);
        constraints.gridy = 2;
        titleAndScreenPanel.add(EScreen, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        titleAndScreenPanel.add(emptyScreen1, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        titleAndScreenPanel.add(emptyScreen2, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        titleAndScreenPanel.add(screen, constraints);

        // Creating the second panel that will house all the buttons for the calculator
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.fill = GridBagConstraints.VERTICAL;
        panel.setBorder(new EmptyBorder(0, 20, 20, 20));
        panel.setBackground(Color.gray);

        // An array of buttons so that each creation and addistion does not need to be
        // individually typed out. The order is important here
        String[] buttonArray = {
                "OFF", "MRC", "M-", "M+", "/",
                "%", "7", "8", "9", "*",
                "√", "4", "5", "6", "-",
                "C", "1", "2", "3", "+",
                "AC", "0", ".", "="
        };

        // For loop to go through the buttonArray, create each button and add them to
        // the second panel
        int rows = 0;
        int cols = 0;
        for (String i : buttonArray) {
            JButton calcButton = new JButton(i);

            if(cols == 5)
            {
                rows++;
                cols = 0;
            }

            // Adding a listener to each button to see when it is pressed
            calcButton.addActionListener(calcButtonListener);
            List<String> operations = Arrays.asList("OFF", "MRC", "M-", "M+", "/", "*", "-", "+", "=", "%", "√");
            List<String> clears = Arrays.asList("C", "AC");

            if (operations.contains(i)) {
                calcButton.setBackground(Color.BLACK);
                calcButton.setOpaque(true);
                calcButton.setForeground(Color.WHITE);
            } else if (clears.contains(i)) {
                calcButton.setBackground(Color.RED);
                calcButton.setOpaque(true);
                calcButton.setForeground(Color.WHITE);
            } else {
                calcButton.setOpaque(false);
                calcButton.setForeground(Color.BLACK);
            }

            buttonConstraints.gridx = cols;
            buttonConstraints.gridy = rows;
            if(i == "+")
            {   
                buttonConstraints.gridheight = 2;
            }
            else
            {
                buttonConstraints.gridheight = 1;
            }
            calcButton.setPreferredSize(new Dimension(85, 85));
            calcButton.setFocusPainted(false);
            panel.add(calcButton, buttonConstraints);
            cols++;       
        }

        // Layout configuration
        frame.add(panel, BorderLayout.CENTER);
        frame.add(titleAndScreenPanel, BorderLayout.NORTH);

        // Setting the frame to visible, essentially creating it to be seen
        frame.setVisible(true);
    }

    // This is the main method that will activate whenever a button that has a
    // listener on it is pressed on the calculator
    ActionListener calcButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // The e.getActionCommand() method gets exactly which button is pressed
            String actionCommand = e.getActionCommand();
            switch (actionCommand) {
                case "AC":
                    accumulator = 0.0;
                    memory = 0.0;
                    currentOperator = null;
                case "C":
                    // Clear the screen
                    screen.setText("");
                    break;
                case "OFF":
                    // If the user presses OFF, terminate the program
                    frame.dispose();
                    break;
                case ".":
                    // TODO: Logic so the user can only put in one '.'
                case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9":
                    // TODO: The calculator can only show one number at a time
                    // This gets the current text on the screen and appends the newest value pressed
                    String s = screen.getText() + actionCommand;

                    // This sets the screen to the total string of values pressed
                    screen.setText(s);
                    break;
                case "MRC":
                    // TODO: Implement memory recall
                    break;
                case "M-":
                    // TODO: Implement memory -
                    break;
                case "M+":
                    // TODO: Implement memory +
                    break;
                case "/", "*", "-", "+":
                    // TODO: Implement operators
                    currentOperator = actionCommand;
                    break;
                case "%":
                    // TODO: Implement percentage
                    // Needs to take into account the current operator
                    break;
                case "√":
                    // TODO: Implement square root
                    break;
                default:
                    // Unhandled case
                    // Can't seem to throw in here
                    System.exit(1);
                    break;
            }
        }
    };
}
