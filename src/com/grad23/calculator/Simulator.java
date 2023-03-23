package com.grad23.calculator;

import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

class Simulator {
    // Have higher scope because they're accessed in calcButtonListener
    JFrame frame;
    JTextField emptyScreen1;
    JTextField emptyScreen2;
    JTextField screen;
    JTextField mScreen;
    JTextField negScreen;
    JTextField eScreen;
    Calculator calculator;

    static final private boolean debug = false;
    static final private int howManyCalculatorsDoWeWant___QuestionMark = 1; // Please

    public static void main(String args[]) {
        for (int i = 0; i < howManyCalculatorsDoWeWant___QuestionMark; i++) {
            new Simulator();
        }
    }

    private Simulator() {
        try {
            // This allows for changes to the UI of elements added to the frame
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Create the calculator
        calculator = new Calculator();

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

        // Empty space to force the text to be at the bottom right

        emptyScreen1 = new JTextField(27);
        emptyScreen1.setHorizontalAlignment(JTextField.RIGHT);
        emptyScreen1.setEditable(false);

        // Empty space to force the text to be at the bottom right
        emptyScreen2 = new JTextField(27);
        emptyScreen2.setHorizontalAlignment(JTextField.RIGHT);
        emptyScreen2.setEditable(false);

        // Adding all the screen elements for it to be larger
        screen.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        emptyScreen1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        emptyScreen2.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        // Make the M screen
        mScreen = new JTextField(3);
        mScreen.setEditable(false);
        mScreen.setHorizontalAlignment(JTextField.CENTER);
        mScreen.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        // Make the - screen
        negScreen = new JTextField(3);
        negScreen.setEditable(false);
        negScreen.setHorizontalAlignment(JTextField.CENTER);
        negScreen.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        // Make the E screen
        eScreen = new JTextField(3);
        eScreen.setEditable(false);
        eScreen.setHorizontalAlignment(JTextField.CENTER);
        eScreen.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        // This is adding a font to the screens from digital-7 (mono).ttf
        try {
            String filename = "src/digital-7 (mono).ttf";
            Font font;
            font = Font.createFont(Font.TRUETYPE_FONT, new File(filename));
            font = font.deriveFont(Font.BOLD, 28);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            screen.setFont(font);
            mScreen.setFont(font);
            negScreen.setFont(font);
            eScreen.setFont(font);
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

        // GridBagLayout for changing putting the specific buttons on certain grids
        constraints.gridx = 0;
        constraints.gridy = 0;
        titleAndScreenPanel.add(mScreen, constraints);
        constraints.gridy = 1;
        titleAndScreenPanel.add(negScreen, constraints);
        constraints.gridy = 2;
        titleAndScreenPanel.add(eScreen, constraints);
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
        List<String> operations = Arrays.asList("OFF", "MRC", "M-", "M+", "/", "*", "-", "+", "=", "%", "√");
        List<String> clears = Arrays.asList("C", "AC");

        // Create each button and add it to the panel.
        for (int i = 0; i < buttonArray.length; i++) {
            String button = buttonArray[i];
            JButton calcButton = new JButton(button);

            int col = i % 5;
            int row = i / 5;

            // Adding a listener to each button to see when it is pressed
            calcButton.addActionListener(calcButtonListener);

            if (operations.contains(button)) {
                calcButton.setBackground(Color.BLACK);
                calcButton.setOpaque(true);
                calcButton.setForeground(Color.WHITE);
            } else if (clears.contains(button)) {
                calcButton.setBackground(Color.RED);
                calcButton.setOpaque(true);
                calcButton.setForeground(Color.WHITE);
            } else {
                calcButton.setOpaque(false);
                calcButton.setForeground(Color.BLACK);
            }

            buttonConstraints.gridx = col;
            buttonConstraints.gridy = row;
            buttonConstraints.gridheight = (button == "+") ? 2 : 1;

            calcButton.setPreferredSize(new Dimension(85, 85));
            calcButton.setFocusPainted(false);
            panel.add(calcButton, buttonConstraints);
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

            boolean success = calculator.performAction(actionCommand);
            if (!success) {
                // Can't seem to throw in here
                System.exit(1);
            }
            if (calculator.isDone()) {
                frame.dispose();
            }

            Display();
        }

        private void Display() {
            String display = calculator.getDisplay();
            String eDisplay = calculator.getEDisplay();
            String negDisplay = calculator.getNegDisplay();
            String mDisplay = calculator.getMDisplay();

            screen.setText(display);
            eScreen.setText(eDisplay);
            negScreen.setText(negDisplay);
            mScreen.setText(mDisplay);

            if (debug) {
                double memory = calculator.getMemory();
                double accumulator = calculator.getAccumulator();

                emptyScreen1.setText(calculator.formatNumber(accumulator));
                emptyScreen2.setText(calculator.formatNumber(memory));
            }
        }
    };
}
