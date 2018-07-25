import javax.swing.*;
import java.util.ArrayList;

enum BuyNum{
    ONE, TEN, HUNDRED, MAX
}

public class ItemHolder {
    private ArrayList<Item> list;

    public static BuyNum buynum;

    public ItemHolder(){
        list = new ArrayList<>();
    }

    public void add(Item i){
        list.add(i);
    }

    public Item get(int i){
        return list.get(i);
    }

    public void addTo(JFrame f){
        for(Item i: list){
            f.add(i);
        }
    }

    public void managerClicked(JMenuItem menuItem){
        for(Item i: list){
            //int upgradeIndex = i.containsUpgrade(menuItem);
            if(i.getManagerItem() == menuItem){
                i.managerClicked();
            }/* else if(upgradeIndex >= 0){
                i.upgradeClicked(upgradeIndex, menuItem, frame);
            }*/
        }
    }

    public void upgradeClicked(UpgradeMenuItem menuItem){
        for(Item i: list){
            if(i.containsUpgrade(menuItem)){
                //System.out.println(menuItem.getText());
                i.upgradeClicked(menuItem);
            }
        }
    }

}