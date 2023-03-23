import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

class CalculatorSimulator {
    // Have higher scope because they're accessed in calcButtonListener
    JFrame frame;
    JTextField emptyScreen1;
    JTextField emptyScreen2;
    JTextField screen;
    JTextField MScreen;
    JTextField minusScreen;
    JTextField EScreen;

    public static void main(String args[]) {
        new CalculatorSimulator();
    }

    private CalculatorSimulator() {
        try 
        {
            // set look and feel
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        }
        catch (Exception e) 
        {
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
        

        emptyScreen1 = new JTextField(27);
        emptyScreen1.setEditable(false);

        emptyScreen2 = new JTextField(27);
        emptyScreen2.setEditable(false);

        screen.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        emptyScreen1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        emptyScreen2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        
        MScreen = new JTextField(3);
        minusScreen = new JTextField(3);
        EScreen = new JTextField(3);

        MScreen.setEditable(false);
        minusScreen.setEditable(false);
        EScreen.setEditable(false);

        MScreen.setHorizontalAlignment(JTextField.CENTER);
        minusScreen.setHorizontalAlignment(JTextField.CENTER);
        EScreen.setHorizontalAlignment(JTextField.CENTER);

        MScreen.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        minusScreen.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        EScreen.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        try 
        {
            String filename="src/digital-7 (mono).ttf";
            Font font;
            font = Font.createFont(Font.TRUETYPE_FONT, new File(filename));
            font = font.deriveFont(Font.BOLD,28);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            screen.setFont(font);
            MScreen.setFont(font);
            minusScreen.setFont(font);
            EScreen.setFont(font);
            emptyScreen1.setFont(font);
            emptyScreen2.setFont(font);
        } 
        catch (FontFormatException e) 
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Creating the first panel that houses the titles and the screen itself so that
        // its layout can differ from the buttons themselves
        JPanel titleAndScreenPanel = new JPanel(new GridBagLayout());
        titleAndScreenPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;
        titleAndScreenPanel.setBackground(Color.gray);

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
        JPanel panel = new JPanel(new GridLayout(5, 5, 10, 10));
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
        for (String i : buttonArray) {
            JButton calcButton = new JButton(i);

            // Adding a listener to each button to see when it is pressed
            calcButton.addActionListener(calcButtonListener);
            if(i == "OFF" || i == "MRC" || i == "M-" || i == "M+" || i == "/" || i == "*" || i == "-" || i == "+" || i == "=" || i == "%" || i == "√")
            {
                calcButton.setBackground(Color.BLACK);
                calcButton.setOpaque(true);
                calcButton.setForeground(Color.WHITE);
            }
            else if(i == "C" || i == "AC")
            {
                calcButton.setBackground(Color.RED);
                calcButton.setOpaque(true);
                calcButton.setForeground(Color.WHITE);
            }
            else
            {
                calcButton.setOpaque(false);
                calcButton.setForeground(Color.BLACK);
            }

            panel.add(calcButton);       
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
                    // TODO: AC also clear memory
                case "C":
                    // If it is AC or C, clear the screen
                    // Here you set the screen to be an empty string
                    screen.setText("");
                    break;
                case "OFF":
                    // If the user presses OFF, terminate the program
                    frame.dispose();
                    break;
                default:
                    // This gets the current text on the screen and appends the newest value pressed
                    String s = screen.getText() + actionCommand;

                    // This sets the screen to the total string of values pressed
                    screen.setText(s);
                    break;
            }
        }
    };
}
