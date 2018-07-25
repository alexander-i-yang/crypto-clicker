import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.*;

public class Board extends JPanel
        implements Runnable {

    private final int B_WIDTH = 350;
    private final int B_HEIGHT = 350;
    private final int DELAY = 25;

    private Thread animator;

    private GrowRect grow1;
    private GrowRect grow2;

    private ThreadAnimationEx frame;

    public Board(){ }

    public Board(ThreadAnimationEx f) {
        frame = f;
        initBoard();

    }

    private void initBoard() {
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);
        grow1 = new GrowRect(20, 20, 310, 20, Color.BLUE, 1000);
        grow2 = new GrowRect(20, 60, 310, 20, Color.GREEN, 2000);
        JButton button = new JButton("Test");
        button.setBounds(50, 200, 200, 20);
        frame.getContentPane().add(button);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRect(g);
        frame.setVisible(true);
    }

    private void drawRect(Graphics g) {

        //setRect();
        //rect1.paintComponent(g);
        grow1.paintComponent(g);
        grow2.paintComponent(g);
        //g.fillRect(0, 0, width, 50);
        Toolkit.getDefaultToolkit().sync();
    }

    /*
    private void setRect(){
        rect1.setWidth(width);
    }*/

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {
            grow1.cycle(1);
            grow2.cycle(1);
            repaint();

            /*
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;



            if (sleep < 0) {
                sleep = 1;
            }*/


            try {
                //Thread.sleep(sleep);
                Thread.sleep(1);
            } catch (InterruptedException e) {

                String msg = String.format("Thread interrupted: %s", e.getMessage());

                JOptionPane.showMessageDialog(this, msg, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            beforeTime = System.currentTimeMillis();
        }
    }
}