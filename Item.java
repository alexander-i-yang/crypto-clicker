import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Item extends JPanel implements Runnable{
    private GrowRect bar;
    private JButton incrButton;
    private JButton buyButton;
    private JButton unlockButton;
    private JLabel valLable;
    private JLabel numBoughtLabel;

    private long orijValue;

    private int numBought;

    private String name;

    private String itemName;

    private Thread animator;

    private boolean buyClicked;

    private BigDecimal incrVal;

    public static final int DELAY = 10;

    private long value;

    public static BigDecimal total = new BigDecimal("1000000000.0");

    private static boolean changed;

    private ArrayList<UpgradeMenuItem> upgradeMenuItems;

    private JMenuItem managerItem;
    private int managerCost;

    private boolean managerBought;

    private boolean visible;

    private JFrame frame;

    private int unlockCost;

    private int benchmark;

    private long[] upgradeCosts;
    private int upgradeCostIndex;
    private int numUpgrades;

    private JMenu subMenu;

    NumberFormat currencyFormatter;

    public Item(){
    }

    public Item(JFrame frame, int x, int y, int w, int h, String name, long cycleTime, long val, float incrVal, JMenuItem menuItem, ArrayList<UpgradeMenuItem> upgradeItems, int manCost){
        benchmark = 25;
        this.frame = frame;

        managerItem = menuItem;
        managerCost = manCost;

        this.name = name;
        buyClicked = false;
        changed = false;
        value = val;
        orijValue = val;

        upgradeCosts = new long[] {25000, 50000, 100000};
        upgradeCostIndex = 0;
        setUpgradeMenuItems(upgradeItems);
        for(UpgradeMenuItem item: upgradeMenuItems){
            /*System.out.println(item.getPercent());
            System.out.println(item.getPercent()*10);*/
            item.setDefaultDescription(name);
        }
        numBought = 1;

        this.incrVal = BigDecimal.valueOf(incrVal);

        //this.setBackground(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
        buyButton = new JButton(name);
        itemName = name;
        int valHeight = 15;
        buyButton.setBounds(0, 0, h*2, h-valHeight);

        int buyWidth = buyButton.getWidth();
        int buyHeight = buyButton.getHeight();

        int barHeight = h/2-valHeight/2;
        bar = new GrowRect(buyWidth, 0, w-buyWidth, barHeight, Color.GREEN, cycleTime);

        incrButton = new JButton("Buy! ($" + incrVal + "0)");
        incrButton.setBounds(buyWidth, barHeight, w-buyWidth, barHeight);

        valLable = new JLabel("$" + value + ".00");
        valLable.setBounds(0, buyHeight, w, h-buyHeight);

        numBoughtLabel = new JLabel("1");
        numBoughtLabel.setBounds(buyWidth, buyHeight, w, 10);

        buyButton.addActionListener(e -> {
            buyClicked = true;
            bar.addTime(System.currentTimeMillis());
            //System.out.println("buyClicked " + buyClicked);
        });

        incrButton.addActionListener(e -> incrClicked());

        currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

        setDoubleBuffered(true);
        this.add(buyButton);
        this.add(incrButton);
        this.add(valLable);
        this.add(numBoughtLabel);
        this.setLayout(null);
        this.setBounds(x, y, w, h);
    }

    public void setSubMenu(JMenu sub){
        subMenu = sub;
    }

    private void setUpgradeMenuItems(ArrayList<UpgradeMenuItem> upgradeItems){
        upgradeMenuItems = upgradeItems;
        numUpgrades = upgradeItems.size();
        for(int i = 0; i < upgradeItems.size(); ++i){
            upgradeCostIndex++;
            UpgradeMenuItem curItem = upgradeMenuItems.get(i);
            curItem.setText(name + " " + (i+1));
            curItem.setCost(upgradeCosts[i]);
            curItem.setMultiplier(3);
        }
    }

    private void addNewUpgrade(){
        MenuBar menuBar = upgradeMenuItems.get(0).getMenuBar();
        numUpgrades++;
        UpgradeMenuItem newItem = new UpgradeMenuItem(menuBar);
        newItem.setText(name + " " + numUpgrades);
        newItem.setCost(upgradeCosts[upgradeCostIndex]);
        upgradeCostIndex++;
        if(upgradeCostIndex >= upgradeCosts.length){
            upgradeCostIndex = 0;
            for(int i = 0; i<upgradeCosts.length; ++i){
                upgradeCosts[i] *= 10;
            }
        }
        //System.out.println("Upgrade cost index: " + upgradeCostIndex + " array: " + upgradeCosts[upgradeCostIndex]);
        newItem.setMultiplier(3);
        newItem.setDefaultDescription(name);
        menuBar.add(subMenu, newItem);
        menuBar.resetActionListeners(subMenu);
        System.out.println(newItem.getText() + newItem.getActionListeners());
        upgradeMenuItems.add(newItem);
        //System.out.println("Item 158");
    }

    public void setFrame(JFrame f){
        frame = f;
    }

    public void unlockButton(int x, int y, int w, int h){
        unlockButton = new JButton("Unlock " + this.name);
        unlockButton.setBounds(x, y, w, h);
        unlockButton.addActionListener(e -> unlockButtonClicked());
        frame.add(unlockButton);
    }

    public void setUnlockCost(int a){
        unlockCost = a;
    }

    public void unlockButtonClicked(){
        String unlockString = currencyFormatter.format(unlockCost);
        int a = JOptionPane.showConfirmDialog(frame, "Are you sure you want to unlock " + name + " for " + unlockString + "?");
        if(a == JOptionPane.YES_OPTION){
            if(total.compareTo(BigDecimal.valueOf(unlockCost)) < 0){
                noMoney();
            } else {
                total = total.subtract(BigDecimal.valueOf(unlockCost));
                changed = true;
                setVisible(true);
                unlockButton.setVisible(false);
                JOptionPane.showMessageDialog(frame, name + " bought successfully.");
                //System.out.println(value);
            }
        }
    }

    public void setVisible(boolean b){
        visible = b;
        super.setVisible(b);
        managerItem.setVisible(b);
        subMenu.setVisible(b);
        for(UpgradeMenuItem i: upgradeMenuItems) i.setVisible(b);
        if(!visible){
            unlockButton(super.getX(), super.getY(), super.getWidth(), super.getHeight());
        }
    }

    public boolean containsUpgrade(UpgradeMenuItem menuItem){
        for(int i = 0; i<upgradeMenuItems.size(); ++i){
            if(menuItem == upgradeMenuItems.get(i)){
                return true;
            }
        }
        return false;
    }

    public void upgradeClicked(UpgradeMenuItem menuItem){
        long cost = menuItem.getCost();
        BigDecimal bigCost = BigDecimal.valueOf(cost);
        String costStr = currencyFormatter.format(cost);
        int a = JOptionPane.showConfirmDialog(frame, "Are you sure you want to buy " + menuItem.getText() + " for " + costStr + "?\n" + "(" + menuItem.getDescription() + ")");
        if(a == JOptionPane.YES_OPTION){
            if(total.compareTo(bigCost) < 0){
                noMoney();
            } else {
                total = total.subtract(bigCost);
                //managerBought = true;
                value *= menuItem.getMultiplier();
                orijValue *= menuItem.getMultiplier();
                resetValLable();
                changed = true;
                JOptionPane.showMessageDialog(frame, menuItem.getText() + " bought successfully.");
                menuItem.setVisible(false);
                addNewUpgrade();
                //System.out.println(value);
            }
        }
    }

    public void resetValLable(){
        valLable.setText(currencyFormatter.format(value));
    }

    public void incrClicked(){
        if(total.compareTo(incrVal) >= 0){
            total = total.subtract(incrVal);

            if(total.compareTo(incrVal) < 0){
                //System.out.println("105");
                incrButton.setEnabled(false);
            }

            numBought++;
            numBoughtLabel.setText("" + numBought);

            value += orijValue;
            if(numBought == benchmark){
                if(benchmark < 100){
                    benchmark += 25;
                } else {
                    benchmark += 100;
                }
                value *= 2;
                resetValLable();
                benchmarkReached();
            } else {
                resetValLable();
            }

            //buyButton.setText();
            /*double dubIncr = (double)(incrVal)*1.5;
            incrVal = (int)(dubIncr);*/

            /*String message = "Your total is: " + currencyFormatter.format(total);*/
            incrVal = incrVal.multiply(BigDecimal.valueOf(1.07));
            String incrString = currencyFormatter.format(incrVal);
            incrButton.setText("Buy! (" + incrString + ")");
            changed = true;
        }
    }

    public void benchmarkReached(){
        /*managerBought = false; //I need to stop everything until the person clicks ok
        managerBought = true;*/
        JOptionPane.showMessageDialog(frame,
                "You have bought " + numBought + " " + name + "s.\nReward: 2x profit",
                "Achievement unlocked",
                JOptionPane.PLAIN_MESSAGE);

    }

    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawRect(g);
    }

    public void drawRect(Graphics g){
        bar.paintComponent(g);
        Toolkit.getDefaultToolkit().sync();
    }

    public static boolean isChanged(){/*No idea why I need this*/ System.out.print(""); return changed;}

    public static void setChanged(boolean b){changed = false;}

    public JMenuItem getManagerItem() {
        return managerItem;
    }

    public void noMoney(){
        JOptionPane.showMessageDialog(frame, "You don't have enough money.", "Not enough money", JOptionPane.WARNING_MESSAGE);
    }

    public void managerClicked(){
        BigDecimal bigCost = BigDecimal.valueOf(managerCost);
        String costStr = currencyFormatter.format(bigCost);
        int a = JOptionPane.showConfirmDialog(frame, "Are you sure you want to buy " + getManagerItem().getText() + " for " + costStr + "?");
        if (a == JOptionPane.YES_OPTION) {
            if (total.compareTo(bigCost) < 0) {
                noMoney();
            } else {
                total = total.subtract(bigCost);
                managerBought = true;
                JOptionPane.showMessageDialog(frame, "Manager bought successfully.");
                changed = true;
                managerItem.setEnabled(false);
                //ystem.out.println(total);
            }
        }
    }

    public void run(){
        while(true) {
            /*No idea why I need this*/System.out.print("");

            if(total.compareTo(incrVal) < 0){
                //System.out.println("105");
                incrButton.setEnabled(false);
            } else {
                //System.out.println("108");
                incrButton.setEnabled(true);
            }

            if (buyClicked || managerBought) {
                //bar.setStop(false);
                //System.out.println(73);
                //System.out.println(bar.isFilled());
                buyButton.setEnabled(false);
                while (!bar.getStop()) {

                    bar.cycle(DELAY);
                    repaint();

                    try {
                        Thread.sleep(DELAY);
                    } catch (InterruptedException e) {
                        System.err.println(e);
                    }
                }
                bar.setStop(false);
                //System.out.println(87);
                buyClicked = false;
                buyButton.setEnabled(true);
                total = total.add(BigDecimal.valueOf(value));
                //total = total.multiply(BigInteger.valueOf(value));
                //System.out.println(total);
                changed = true;
                //System.out.println(changed);
            }
        }
    }
}