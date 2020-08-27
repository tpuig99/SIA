package ui;
import Constants.ItemType;
import weapons.Item;
import weapons.Items;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        ClassLoader classLoader = Main.class.getClassLoader();
        //Items boots = Parser(classLoader.getResource("botas.tsv").toURI(), ItemType.boots);
        //Items weapons = Parser(classLoader.getResource("armas.tsv").toURI(), ItemType.weapon);
        //Items helmets = Parser(classLoader.getResource("cascos.tsv").toURI(), ItemType.helmet);
        //Items armors = Parser(classLoader.getResource("pecheras.tsv").toURI(), ItemType.armor);
        //Items gloves = Parser(classLoader.getResource("guantes.tsv").toURI(), ItemType.glove);
    }

    static Items Parser(URI sourcePath, ItemType type){
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
        System.out.println(weapons.itemSize());
        Item aux = weapons.getItem(0);
        System.out.println(aux.getStrength());
        System.out.println(aux.getAgility());
        System.out.println(aux.getExpertise());
        System.out.println(aux.getResistance());
        System.out.println(aux.getLife());
        return weapons;
    }
}
