import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class Block extends JPanel {

    private int x;
    private int y;
    private int width;
    private int height;

    private Color color;

    public Block(){
        this(0, 0, 0, 0, Color.BLACK);
    }

    public Block(int x, int y, int width, int height, Color col) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        color = col;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public void setWidth(int w){
        width = w;
    }

    public int getWidth(){
        return width;
    }

    public void setColor(Color col){
        color = col;
    }
}