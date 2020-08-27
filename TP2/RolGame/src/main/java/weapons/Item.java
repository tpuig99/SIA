package weapons;


import Constants.ItemType;

public class Item {
    double strength, agility, expertise, resistance, life;
    int id;
    ItemType type;

    public Item(double strength, double agility, double expertise, double resistance, double life, int id, ItemType type) {
        this.strength = strength;
        this.agility = agility;
        this.expertise = expertise;
        this.resistance = resistance;
        this.life = life;
        this.id = id;
        this.type = type;
    }

    public double getStrength() {
        return strength;
    }

    public double getAgility() {
        return agility;
    }

    public double getExpertise() {
        return expertise;
    }

    public double getResistance() {
        return resistance;
    }

    public double getLife() {
        return life;
    }
}
