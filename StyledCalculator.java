import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

public class StyledCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private String operator = "";
    private double num1 = 0;

    public StyledCalculator() {
        setTitle("Curvy Java Calculator");
        setSize(350, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Display field with curved border and large font
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Segoe UI", Font.BOLD, 36));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setBackground(new Color(245, 245, 245));
        display.setBorder((Border) new RoundedBorder(20)); // Rounded border
        display.setMargin(new Insets(20, 20, 20, 20));
        add(display, BorderLayout.NORTH);

        // Button panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(30, 30, 30)); // Dark background for contrast

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 22));
            btn.setFocusPainted(false);
            btn.setBorder(new RoundedBorder(15)); // Rounded corners

            // Color coding
            if (text.matches("[0-9]")) {
                btn.setBackground(new Color(200, 230, 201));
            } else if (text.equals("C")) {
                btn.setBackground(new Color(255, 138, 128));
            } else if (text.equals("=")) {
                btn.setBackground(new Color(129, 212, 250));
            } else {
                btn.setBackground(new Color(255, 224, 178));
            }

            btn.setForeground(Color.BLACK);
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();

        if (input.matches("[0-9]")) {
            display.setText(display.getText() + input);
        } else if (input.matches("[+\\-*/]")) {
            operator = input;
            try {
                num1 = Double.parseDouble(display.getText());
            } catch (NumberFormatException ex) {
                display.setText("Error");
                return;
            }
            display.setText("");
        } else if (input.equals("=")) {
            double num2;
            try {
                num2 = Double.parseDouble(display.getText());
            } catch (NumberFormatException ex) {
                display.setText("Error");
                return;
            }

            double result = 0;
            switch (operator) {
                case "+": result = num1 + num2; break;
                case "-": result = num1 - num2; break;
                case "*": result = num1 * num2; break;
                case "/":
                    if (num2 == 0) {
                        display.setText("Divide by 0");
                        return;
                    }
                    result = num1 / num2;
                    break;
            }
            display.setText(String.valueOf(result));
        } else if (input.equals("C")) {
            display.setText("");
            operator = "";
            num1 = 0;
        }
    }

    // Custom border class for rounded corners
    class RoundedBorder implements Border {
        private int radius;
        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 5, radius + 5, radius + 5, radius + 5);
        }

        public boolean isBorderOpaque() {
            return false;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.GRAY);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    public static void main(String[] args) {
        new StyledCalculator();
    }
}
