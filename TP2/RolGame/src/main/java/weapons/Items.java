package weapons;


import Constants.ItemType;
import weapons.Item;

import java.util.ArrayList;

public class Items {
    ItemType type;
    ArrayList<Item> items;

    public Items(ItemType type) {
        this.type = type;
        items = new ArrayList<Item>();
    }
    public void addItem(double strength, double agility, double expertise, double resistance, double life, int id){
        Item item = new Item(strength,agility,expertise,resistance,life,id,type);
        items.add(id,item);
    }
    public Item getItem(int index){
        return items.get(index);
    }
    public int itemSize(){
        return items.size();
    }
}
