import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

class Calculator extends JFrame implements ActionListener {

    // Static screen because it needs to be accessed in the actionPerformed method
    static JTextField screen;

    public static void main(String args[]) {
        Calculator calculator = new Calculator();

        // Creating the frame of the calculator which has a title and a size
        JFrame frame = new JFrame();
        frame.setTitle("Casio Calculator");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 600);

        // Creating the different titles that are displayed at the top of a calculator
        JLabel casioTitle = new JLabel("CASIO", SwingConstants.CENTER);
        JLabel descriptionTitle = new JLabel("ELECTRONIC CALCULATOR", SwingConstants.CENTER);
        JLabel modelTitle = new JLabel("HL-815L", SwingConstants.CENTER);

        // Initializing the screen where the inputs and result will be showed. It cannot
        // be edited by just clicking on the text field
        screen = new JTextField(10);
        screen.setHorizontalAlignment(JTextField.RIGHT);
        screen.setEditable(false);

        // Creating the first panel that houses the titles and the screen itself so that
        // its layout can differ from the buttons themselves
        JPanel titleAndScreenPanel = new JPanel(new GridLayout(4, 1));
        titleAndScreenPanel.setBackground(Color.gray);

        // Adding each title to the first panel
        titleAndScreenPanel.add(casioTitle);
        titleAndScreenPanel.add(descriptionTitle);
        titleAndScreenPanel.add(modelTitle);
        titleAndScreenPanel.add(screen);

        // Creating the second panel that will house all the buttons for the calculator
        JPanel panel = new JPanel(new GridLayout(5, 5, 10, 10));
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
            calcButton.addActionListener(calculator);
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
    public void actionPerformed(ActionEvent e) {
        // The e.getActionCommand() method listens to see exactly which button is
        // pressed
        // If it is AC or C, clear the screen
        if (e.getActionCommand() == "AC" || e.getActionCommand() == "C") {
            // Here you set the screen to be an empty string
            screen.setText("");
        } else if (e.getActionCommand() == "OFF") {
            // If the user presses OFF, terminate the program
            System.exit(0);
        } else {
            // This gets the current text on the screen and appends the newest value pressed
            String s = screen.getText() + e.getActionCommand();

            // This sets the screen to the total string of values pressed
            screen.setText(s);
        }
    }
}
