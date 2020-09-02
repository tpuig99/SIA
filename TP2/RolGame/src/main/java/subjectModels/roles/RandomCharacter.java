package subjectModels.roles;

import subjectModels.Constants.ItemType;
import subjectModels.Constants.Role;
import subjectModels.RandomSubject;
import subjectModels.Subject;
import subjectModels.equipment.Item;
import subjectModels.equipment.Items;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import static subjectModels.Constants.ItemType.ARMOR;

public class RandomCharacter implements RandomSubject {
    Items boots,weapon,helmets,armors,gloves;

    public RandomCharacter() {
        boots = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\botas.tsv", ItemType.BOOTS);
        weapon = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\armas.tsv", ItemType.WEAPON);
        helmets = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\cascos.tsv", ItemType.HELMET);
        armors = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\pecheras.tsv", ARMOR);
        gloves = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\guantes.tsv", ItemType.GLOVES);

    }

    @Override
    public Item randomProperty(int property) {
        int index;
        Random random = new Random();
        switch (property){
            case 0:{
                index = random.nextInt(boots.itemSize());
                return boots.getItem(index);
            }
            case 1:{
                index = random.nextInt(armors.itemSize());
                return armors.getItem(index);
            }
            case 2:{
                index = random.nextInt(weapon.itemSize());
                return weapon.getItem(index);
            }
            case 3:{
                index = random.nextInt(helmets.itemSize());
                return helmets.getItem(index);
            }
            case 4:{
                index = random.nextInt(gloves.itemSize());
                return gloves.getItem(index);
            }
            }
        return null;
    }

    @Override
    public Character randomCharacter(Role role) {
        Character aux = new Character(role,this);
        double auxHeight;
        int index;
        Random random = new Random();
        index = random.nextInt(boots.itemSize());
        aux.setProperty(boots.getItem(index),ItemType.BOOTS.ordinal());
        index = random.nextInt(gloves.itemSize());;
        aux.setProperty(gloves.getItem(index),ItemType.GLOVES.ordinal());
        index = random.nextInt(armors.itemSize());;
        aux.setProperty(armors.getItem(index),ItemType.ARMOR.ordinal());
        index = random.nextInt(helmets.itemSize());;
        aux.setProperty(helmets.getItem(index),ItemType.HELMET.ordinal());
        index = random.nextInt(weapon.itemSize());;
        aux.setProperty(weapon.getItem(index),ItemType.WEAPON.ordinal());
        aux.setReady(true);
        auxHeight = (random.nextDouble() * (2-1.3))+1.3;
        aux.setProperty(auxHeight,ItemType.values().length);
        return aux;
    }

    Items Parser(String sourcePath, ItemType type){
        File sourceFile = new File(sourcePath);
        Items weapons = new Items(type);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(sourceFile));
            reader.readLine(); //Titulos columnas

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parameters = line.split("\t", -1);
                int id = Integer.parseInt(parameters[0]);
                double strength = Double.parseDouble(parameters[1]);
                double agility = Double.parseDouble(parameters[2]);
                double expertise = Double.parseDouble(parameters[3]);
                double resistance = Double.parseDouble(parameters[4]);
                double life = Double.parseDouble(parameters[5]);
                weapons.addItem(strength, agility, expertise, resistance, life,id);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return weapons;
    }
}
