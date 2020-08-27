package subjectModels.roles;

import subjectModels.Constants.Role;
import subjectModels.weapons.Item;

public abstract class  Character {
    double height;
    Item[] weapon;
    Role role;

    public Character(Role role) {
        this.role = role;
        weapon = new Item[5];
    }
    public double getDesempeño(){
        return role.getAttackId()*getAttack()+role.getDefenseId()*getDefense();
    }

    private double getDefense() {
        return (getResistance()+getExpertise())*getLife()*getDEM();
    }
    private double getAttack() {
        return (getAgility()+getExpertise())* getStrength()*getATM();
    }

    private double getDEM() {
        return 1.9-Math.pow((2.5*height-2.16),4) + Math.pow((2.5*height-4.16),2) + (3*height)/10;
    }
    private double getATM() {
        return 0.7-Math.pow((3*height-5),4) + Math.pow((3*height-5),2) + height/4;
    }

    private double getLife() {
        double life=0;
        for(int i=0;i<5;i++){
            life+=weapon[i].getLife();
        }
        return 100 * Math.tanh(0.01*life);
    }

    private double getResistance() {
        double resistance=0;
        for(int i=0;i<5;i++){
            resistance+=weapon[i].getResistance();
        }
        return Math.tanh(0.01*resistance);
    }

    private double getStrength() {
        double strength=0;
        for(int i=0;i<5;i++){
            strength+=weapon[i].getStrength();
        }
        return 100 * Math.tanh(0.01*strength);

    }

    private double getExpertise() {
        double expertise=0;
        for(int i=0;i<5;i++){
            expertise+=weapon[i].getExpertise();
        }
        return 0.6* Math.tanh(0.01*expertise);
    }

    private double getAgility() {
        double agility=0;
        for(int i=0;i<5;i++){
            agility+=weapon[i].getAgility();
        }
        return Math.tanh(0.01*agility);
    }
}
