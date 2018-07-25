import javax.swing.*;

public class UpgradeMenuItem extends JMenuItem{
    private long cost;
    private int multiplier;
    private String description;
    private MenuBar menuBar;

    public UpgradeMenuItem(String text, long c, int multiplier){
        super(text);
        cost = c;
        this.multiplier = multiplier;
        this.description = description;
    }

    public UpgradeMenuItem(MenuBar menu){
        super("");
        menuBar = menu;
    }

    public MenuBar getMenuBar(){
        return menuBar;
    }

    public long getCost(){
        return cost;
    }

    public void setCost(long c){
        cost = c;
    }

    public double getMultiplier(){
        return multiplier;
    }

    public void setMultiplier(int m){
        multiplier = m;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String s){
        description = s;
    }

    public void setDefaultDescription(String name){
        setDescription("This upgrade gives a permanent " + multiplier + "x boost to the value of " + name + ".");
    }

    public void setText(String t){
        super.setText(t);
    }

}