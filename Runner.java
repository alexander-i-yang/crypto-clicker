import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Runner implements ActionListener{

    public Runner(JFrame frame){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);

        Block rect1 = new Block(10, 10, 10, 10, Color.GREEN);
        Block rect2 = new Block(10, 20, 10, 10, Color.MAGENTA);

        frame.getContentPane().add(rect1);
        frame.getContentPane().add(rect2);

        frame.setVisible(true);

        GrowRect rect = new GrowRect(50, 50, 10, 10, Color.GREEN);
        rect.addTo(frame);

        /*
        while(c < 10) {
            frame.getContentPane().add(new Block(c*50, c*50, 50, 50, Color.RED));
            frame.getContentPane().add(new Block(200, 10, 5, 5, Color.BLUE));
            frame.setVisible(true);
            c++;
            System.out.println(c);
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void actionPerformed(ActionEvent e) {}
}
