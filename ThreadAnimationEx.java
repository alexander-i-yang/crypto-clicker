import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.*;

public class ThreadAnimationEx extends JFrame {

    public static JMenuBar menuBar;
    public static JMenu managerTab;
    public static JMenu upgradeTab;
    public static JMenuItem manager1, manager2, manager3;

    //private Board b;

    /*public ThreadAnimationEx() {
        initUI();
    }

    private void initUI() {
        b = new Board(this);
        add(b);

        setResizable(false);
        //pack();

        this.setSize(350, 350);
        this.setLayout(null);
        setTitle("CryptoClicker");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("Yee");
        button.setBounds(50, 50, 30, 30);
        this.add(button);
    }*/

    /*public void paint(Graphics g){
        b.paintComponent(g);
    }*/

    public static void main(String[] args) {

        JFrame testFrame = new JFrame("Test");
        testFrame.setSize(420, 420);
        testFrame.setLayout(null);


        //holder.add(new Item(20, 20, 360, 50, "Test", 1000, 1, 1));

        /*Item yee = new Item(20, 20, 360, 50, "Test", 1000, 1, 1);
        Item yee2 = new Item(20, 80, 360, 40, "Test", 1500, 2);
        Item yee3 = new Item(20, 140, 360, 40, "Test", 2000, 3);*/

        /*
        manager1 = new JMenuItem("Manager 1");
        manager2 = new JMenuItem("Manager 2");
        manager3 = new JMenuItem("Manager 3");

        managerTab = new JMenu("Managers");

        menuBar = new JMenuBar();

        managerTab.add(manager1);
        managerTab.add(manager2);
        managerTab.add(manager3);
        menuBar.add(managerTab);*/

        managerTab = new JMenu("Managers");
        upgradeTab = new JMenu("Upgrades");

        MenuBar menuBar = new MenuBar();
        menuBar.add(managerTab);
        menuBar.add(upgradeTab);

        /*for(int i = 1; i < 4; ++i){
            menuBar.add(0, new JMenuItem("Manager " + i));
            menuBar.add(1, new JMenuItem("Upgrade " + i));
            menuBar.add(1, new JMenuItem("Upgrade " + i + "." + 1));
        }*/


        ItemHolder holder = new ItemHolder();

        for(int i = 0; i<3; ++i){
            ArrayList<UpgradeMenuItem> list = new ArrayList<>();
            JMenu submenu = new JMenu("Test " + i);
            menuBar.add(1, submenu);
            for(int j = 0; j<2; ++j){
                //list.add(menuBar.add(1, new UpgradeMenuItem("Upgrade " + (i+1) + "." + j, (i+1)*300+j*300, 1.1+0.1*j)));
                list.add(menuBar.add(submenu, new UpgradeMenuItem(menuBar)));
            }

            menuBar.addAction(submenu,
                    e -> upgradeClicked((UpgradeMenuItem) e.getSource(), holder)
            );

            Item it = new Item(testFrame, 20, 20+i*80, 360, 55, "Test " + i, 1000+i*500, i+1, (i+4), menuBar.add(0, new JMenuItem("Manager " + (i+1))), list, (i+1)*500);
            it.setUnlockCost(i*100);
            it.setSubMenu(submenu);
            it.setVisible(i == 0);
            holder.add(it);
        }

        //holder.get(1).setVisible(false);

        JMenuItem buy1 = new JMenuItem("Buy 1x");
        JMenuItem buy10 = new JMenuItem("Buy 10x");
        //JMenuItem buy100 = new JMenuItem("Buy 100x");
        JMenuItem buyMax = new JMenuItem("Buy Max");

        //Look at https://docs.google.com/spreadsheets/d/1hcTM6gqAUP_pxKvWTkshNc3Ud3ZGA1NqNGy_qvb_RAw/edit#gid=1316159954

        //buyMax.setVisible(false);
        JMenu buyManager = new JMenu("Buy");
        buyManager.add(buy1);
        buyManager.add(buy10);
        buyManager.add(buyMax);
        menuBar.add(buyManager);
        //System.out.println(menuBar.search(buyManager));
        menuBar.addAction(buyManager,
                e -> buyClicked((JMenuItem)e.getSource(), holder)
        );

        menuBar.addAction(0,
                e -> managerClicked((JMenuItem) e.getSource(), holder)
        );

        holder.addTo(testFrame);
        menuBar.addMe(testFrame);
        /*testFrame.add(menuBar);
        testFrame.setJMenuBar(menuBar);*/
        testFrame.setVisible(true);
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel total = new JLabel("Total: $0");
        total.setBounds(20, 0, 1000, 20);

        testFrame.add(total);

        while(true){
            if(Item.isChanged()) {
                Locale locale = new Locale("en", "US");
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                /*String message = "Your total is: " + currencyFormatter.format(total);*/
                total.setText("Total: " + currencyFormatter.format(Item.total));
                Item.setChanged(false);
                //System.out.println("Yee");
            }
            //System.out.println("Yee");
        }

        /*JPanel panel = new JPanel();
        panel.setBounds(40, 200, 30, 30);
        panel.setBackground(Color.BLUE);
        testFrame.add(panel);*/

        /*EventQueue.invokeLater(() -> {
            JFrame ex = new ThreadAnimationEx();
            ex.setVisible(true);
        });*/
    }

    public static void managerClicked(JMenuItem menuItem, ItemHolder holder){
        holder.managerClicked(menuItem);
        /*int a = JOptionPane.showConfirmDialog(frame,"Are you sure you want to buy " + menuItem.getText() + " for $____ ?" );
        if(a == JOptionPane.YES_OPTION){
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }*/
    }

    public static void upgradeClicked(UpgradeMenuItem menuItem, ItemHolder holder){
        //System.out.println(menuItem.getText());
        holder.upgradeClicked(menuItem);
    }

    public static void buyClicked(JMenuItem menuItem, ItemHolder holder){
        System.out.println(menuItem.getText());
    }

}