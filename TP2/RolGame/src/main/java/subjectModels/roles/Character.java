package subjectModels.roles;

import subjectModels.Constants.ItemType;
import subjectModels.Constants.Role;
import subjectModels.Subject;
import subjectModels.weapons.Item;

public class  Character implements Subject {
    double height;
    Item[] weapon;
    Role role;
    double desempeño;
    double strength, agility, expertise, resistance, life;
    double ATM,DEM;

    public Character(Role role) {
        this.role = role;
        weapon = new Item[ItemType.values().length];
    }
    public void setDesempeño(){
        desempeño = role.getAttackId()*getAttack()+role.getDefenseId()*getDefense();
    }
    public double getDesempeño(){
        return desempeño;
    }
    private double getDefense() {
        return (resistance+expertise)*life*DEM;
    }
    private double getAttack() {
        return (agility+expertise)* strength*ATM;
    }

    private void setDEM() {
        DEM = 1.9-Math.pow((2.5*height-2.16),4) + Math.pow((2.5*height-4.16),2) + (3*height)/10;
    }
    private void setATM() {
        ATM = 0.7-Math.pow((3*height-5),4) + Math.pow((3*height-5),2) + height/4;
    }

    private void setLife() {
        double life=0;
        for(int i=0;i<5;i++){
            life+=weapon[i].getLife();
        }
        this.life = 100 * Math.tanh(0.01*life);
    }

    private void setResistance() {
        double resistance=0;
        for(int i=0;i<5;i++){
            resistance+=weapon[i].getResistance();
        }
        this.resistance = Math.tanh(0.01*resistance);
    }

    private void setStrength() {
        double strength=0;
        for(int i=0;i<5;i++){
            strength+=weapon[i].getStrength();
        }
        this.strength = 100 * Math.tanh(0.01*strength);

    }

    private void setExpertise() {
        double expertise=0;
        for(int i=0;i<5;i++){
            expertise+=weapon[i].getExpertise();
        }
        this.expertise = 0.6* Math.tanh(0.01*expertise);
    }

    private void setAgility() {
        double agility=0;
        for(int i=0;i<5;i++){
            agility+=weapon[i].getAgility();
        }
        this.agility = Math.tanh(0.01*agility);
    }

    public void setWeapon(Item weapon) {
        this.weapon[weapon.getType().ordinal()] = weapon;
        setAgility();
        setExpertise();
        setLife();
        setResistance();
        setStrength();
        setDesempeño();
    }
    public void setHeight(double height){
        this.height = height;
        setATM();
        setDEM();
        setDesempeño();
    }

    @Override
    public Subject getRandom() {
        return null;
    }

    @Override
    public Subject cloneSubject() {
        Character subject = new Character(role);
        for (Item item:weapon) {
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
            return weapon[propertyIndex];
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
        return Double.compare(character.getDesempeño(),desempeño);
    }
}
