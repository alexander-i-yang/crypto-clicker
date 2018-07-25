import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuBar {
    //private ArrayList<JMenuItem> menuItems;
    private ArrayList<JMenu> menus;
    private ArrayList<ActionListener> actionListeners;

    private JMenuBar menuBar;

    public MenuBar(){
        menus = new ArrayList<>();
        actionListeners = new ArrayList<>();
        menuBar = new JMenuBar();
    }

    public int search(JMenu menu){
        for(int i = 0; i < menus.size(); ++i){
            if(menu == menus.get(i)) return i;
        }
        return -1;
    }

    public void addMe(JFrame frame){
        frame.add(menuBar);
        frame.setJMenuBar(menuBar);
    }

    public void add(JMenu menu){
        menus.add(menu);
        menuBar.add(menu);
    }

    public JMenuItem add(int menuIndex, JMenuItem item){
        //menuItems.add(item);
        menus.get(menuIndex).add(item);
        return item;
    }

    public UpgradeMenuItem add(int menuIndex, UpgradeMenuItem item){
        menus.get(menuIndex).add(item);
        return item;
    }

    public UpgradeMenuItem add(JMenu subMenu, UpgradeMenuItem item){
        subMenu.add(item);
        return item;
    }

    public void resetActionListeners(JMenu subMenu){
        for(int i = 0; i < subMenu.getItemCount(); ++i){
            //System.out.println(subMenu.getItem(i).getText() + ", Menubar resetActionListeners");
            if(subMenu.getItem(i).getActionListeners().length == 0){
                subMenu.getItem(i).addActionListener(subMenu.getItem(0).getActionListeners()[0]);
                System.out.println(subMenu.getItem(i).getText() + " has gotten a new action " + subMenu.getItem(i).getActionListeners());
                //System.out.println(actionListeners.get(menuIndex));
            }
        }
        //System.out.println(menu.getItemCount());
    }

    public int getNumItems(int menuIndex){
        return getMenu(menuIndex).getItemCount();
    }

    public JMenu getMenu(int index){
        return menus.get(index);
    }

    public JMenuItem getItem(int menuIndex, int itemIndex){
        return getMenu(menuIndex).getItem(itemIndex);
    }

    public void addAction(int menuIndex, ActionListener e){
        JMenu menu = menus.get(menuIndex);
        actionListeners.add(menuIndex, e);
        for(int i = 0; i < menu.getItemCount(); ++i){
            menu.getItem(i).addActionListener(e);
        }
    }

    public void addAction(JMenu submenu, ActionListener e){
        for(int i = 0; i<submenu.getItemCount(); ++i){
            submenu.getItem(i).addActionListener(e);
        }
    }

}