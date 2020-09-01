import selectors.RankingSelector;
import selectors.RouletteSelector;
import selectors.Selector;
import subjectModels.Constants.ItemType;
import subjectModels.Constants.Role;
import subjectModels.roles.Character;
import subjectModels.weapons.Item;
import subjectModels.weapons.Items;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        ClassLoader classLoader = Main.class.getClassLoader();
        //Items boots = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\botas.tsv", ItemType.boots);
        //Items subjectModels.weapons = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\armas.tsv", ItemType.weapon);
        //Items helmets = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\cascos.tsv", ItemType.helmet);
        //Items armors = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\pecheras.tsv", ItemType.armor);
        //Items gloves = Parser("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\dataset\\prueba\\guantes.tsv", ItemType.glove);
        testSelector();
    }

    private static void testSelector() {
        Character c1 = new Character(Role.ARCHER);
        Character c2 = new Character(Role.ARCHER);
        Character c3 = new Character(Role.ARCHER);
        Character c4 = new Character(Role.ARCHER);
        Character c5 = new Character(Role.ARCHER);
        c1.desempeño = 3;
        c2.desempeño = 6;
        c3.desempeño = 11;
        c4.desempeño = 14;
        c5.desempeño = 1;
        List<Character> list = new ArrayList<>();
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
        list.add(c5);
        Selector selector = new RankingSelector();
        selector.select(list,3);
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
