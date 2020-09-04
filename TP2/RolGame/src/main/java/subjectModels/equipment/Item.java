package subjectModels.equipment;


import subjectModels.Constants.ItemType;

import java.util.Objects;

public class Item {
    double strength, agility, expertise, resistance, life;
    int id;

    public ItemType getType() {
        return type;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                type == item.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "Item: " +
                "id=" + id +
                ", type=" + type;
    }
}
