import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame f = new JFrame("Test");

        JButton button = new JButton("Test");
        button.setBounds(20, 20, 60, 30);
        f.add(button);


        JButton overlap = new JButton("Over");
        overlap.setBounds(20, 20, 60, 30);
        overlap.addActionListener(e -> {
            overlap.setVisible(false);
            System.out.println("overlap");
            button.setVisible(true);
        });
        button.addActionListener(e -> {
            overlap.setVisible(true);
            System.out.println("button");
            button.setVisible(false);
        });

        f.add(overlap);

        overlap.setVisible(false);

        f.setSize(200, 200);
        f.setLayout(null);
        f.setVisible(true);
    }
}
