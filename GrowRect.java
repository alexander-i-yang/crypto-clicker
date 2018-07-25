import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GrowRect extends Block{

    private long cycleTime;

    private int fixedWidth;

    public static final double SYS_ERR = 1.2;

    private boolean stop;

    //Delete later
    ArrayList<Long> temp;

    public GrowRect(){
        super();
    }

    public GrowRect(int x, int y, int w, int h, Color col){
        super(x, y, w, h, col);
        temp = new ArrayList<>();
    }

    public GrowRect(int x, int y, int w, int h, Color col, long cycleTime){
        this(x, y, w, h, col);
        this.cycleTime = cycleTime;
        fixedWidth = w;
        this.setWidth(0);
    }

    public void addTo(JFrame frame){
        frame.add(this);
        frame.setVisible(true);
    }

    public void setStop(boolean b){
        stop = b;
    }

    public void cycle(int delay){
        if(!stop) setRect(delay);
    }

    public void setRect(int delay){
        float flIncr = (float)fixedWidth*(float)delay/(float)cycleTime*(float)SYS_ERR;
        int incr = (int)(flIncr);
        //System.out.println(cycleTime);
        setWidth(getWidth()+incr);
        if(isFilled()){
            //setColor(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
            setWidth(0);
            //System.out.println("Width: " + getWidth());
            temp.add(System.currentTimeMillis());
            if(temp.size() > 1){
                System.out.println(temp.get(temp.size()-1)-temp.get(temp.size()-2) + " filled");
            }
        }
    }

    public boolean isFilled(){
        boolean ret = getWidth() >= fixedWidth;
        if(ret) stop = true;
        //System.out.println(ret + " " + stop);
        return ret;
    }

    public boolean getStop(){
        return stop;
    }

    public void addTime(long time){
        temp.add(time);
    }

}