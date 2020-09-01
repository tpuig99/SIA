
import subjectModels.Constants.ItemType;
import subjectModels.Constants.Role;
import subjectModels.Subject;
import subjectModels.roles.Character;
import subjectModels.equipment.Items;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static subjectModels.Constants.ItemType.ARMOR;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        Items boots = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\botas.tsv", ItemType.BOOTS);
        Items weapon = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\armas.tsv", ItemType.WEAPON);
        Items helmets = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\cascos.tsv", ItemType.HELMET);
        Items armors = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\pecheras.tsv", ARMOR);
        Items gloves = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\guantes.tsv", ItemType.GLOVES);
        
        List<Subject> population = new ArrayList<>();
        //saldría de config, voy a elegir 100
        int subjCant = 100;
        double auxHeight;
        int index;
        Role role = Role.ARCHER;
        for (int i = 0; i < subjCant; i++) {
            Character aux = new Character(role);
            index = (int) (Math.random() * boots.itemSize());
            aux.setProperty(boots.getItem(index),ItemType.BOOTS.ordinal());
            index = (int) (Math.random() * gloves.itemSize());
            aux.setProperty(gloves.getItem(index),ItemType.GLOVES.ordinal());
            index = (int) (Math.random() * armors.itemSize());
            aux.setProperty(armors.getItem(index),ItemType.ARMOR.ordinal());
            index = (int) (Math.random() * helmets.itemSize());
            aux.setProperty(helmets.getItem(index),ItemType.HELMET.ordinal());
            index = (int) (Math.random() * weapon.itemSize());
            aux.setProperty(weapon.getItem(index),ItemType.WEAPON.ordinal());
            aux.setReady(true);
            auxHeight = (Math.random() * (2-1.3))+1.3;
            aux.setProperty(auxHeight,ItemType.values().length);
            population.add(aux);
        }
    }


    static Items Parser(String sourcePath, ItemType type){
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
