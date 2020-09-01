package subjectModels.roles;

import subjectModels.Constants.ItemType;
import subjectModels.Constants.Role;
import subjectModels.Subject;
import subjectModels.equipment.Item;

public class  Character implements Subject {
    private double height;
    private Item[] equipment;
    private final Role role;
    private double fitness;
    private double strength, agility, expertise, resistance, life;
    private double ATM,DEM;
    private boolean ready;

    public Character(Role role) {
        this.role = role;
        ready = false;
        equipment = new Item[ItemType.values().length];
    }
    public void setFitness(){
        fitness = role.getAttackId()*getAttack()+role.getDefenseId()*getDefense();
    }
    public double getFitness(){
        return fitness;
    }
    private double getDefense() {
        return (resistance+expertise)*life*DEM;
    }
    private double getAttack() {
        return (agility+expertise)* strength*ATM;
    }

    private void setDEM() {
        DEM = 1.9+Math.pow((2.5*height-4.16),4) - Math.pow((2.5*height-4.16),2) - (3*height)/10;
    }
    private void setATM() {
        ATM = 0.7-Math.pow((3*height-5),4) + Math.pow((3*height-5),2) + height/4;
    }

    private void setLife() {
        double life=0;
        for(int i=0;i<ItemType.values().length;i++){
            life+=equipment[i].getLife();
        }
        this.life = 100 * Math.tanh(0.01*life);
    }

    private void setResistance() {
        double resistance=0;
        for(int i=0;i<ItemType.values().length;i++){
            resistance+=equipment[i].getResistance();
        }
        this.resistance = Math.tanh(0.01*resistance);
    }

    private void setStrength() {
        double strength=0;
        for(int i=0;i<ItemType.values().length;i++){
            strength+=equipment[i].getStrength();
        }
        this.strength = 100 * Math.tanh(0.01*strength);

    }

    private void setExpertise() {
        double expertise=0;
        for(int i=0;i<ItemType.values().length;i++){
            expertise+=equipment[i].getExpertise();
        }
        this.expertise = 0.6* Math.tanh(0.01*expertise);
    }

    private void setAgility() {
        double agility=0;
        for(int i=0;i<ItemType.values().length;i++){
            agility+=equipment[i].getAgility();
        }
        this.agility = Math.tanh(0.01*agility);
    }

    public void setWeapon(Item weapon) {
        this.equipment[weapon.getType().ordinal()] = weapon;
        if(ready) {
            setAgility();
            setExpertise();
            setLife();
            setResistance();
            setStrength();
            setFitness();
        }
    }
    public void setHeight(double height){
        this.height = height;
        if(ready){
            setATM();
            setDEM();
            setFitness();
        }
    }

    public void setReady(boolean ready) {
        this.ready = ready;
        if(ready){
            setStrength();
            setResistance();
            setLife();
            setExpertise();
            setAgility();
        }
    }

    @Override
    public Subject getRandom() {
        return null;
    }

    @Override
    public Subject cloneSubject() {
        Character subject = new Character(role);
        for (Item item: equipment) {
            subject.setWeapon(item);
        }
        subject.setHeight(height);
        return subject;
    }

    @Override
    public double compareProperty(Subject gs, int propertyIndex) {
        return 0;
    }

    @Override
    public boolean isPropertySimilar(Subject gs, int propertyIndex) {
        return false;
    }

    @Override
    public int getPropertyCount() {
        return 6; //5 weapons + height
    }

    @Override
    public Object getProperty(int propertyIndex) {
        if(propertyIndex== ItemType.values().length){
            return height;
        }
            return equipment[propertyIndex];
    }

    @Override
    public void setProperty(Object property, int propertyIndex) {
        if(propertyIndex== ItemType.values().length){
            setHeight((double)property);
        }
        else{
            setWeapon((Item) property);
        }
    }

    @Override
    public int compareTo(Subject o) {
        Character character = (Character)o;
        return Double.compare(character.getFitness(), fitness);
    }
}
