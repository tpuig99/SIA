import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final int ROW_SIZE = 5;
    public static void main(String[] args) {
        JSONObject configObj = getJsonConfig();

        char[] letters = ("HVCK").toCharArray();
        char testingLetter = 'H';
        int testingLevel = 1;

        if (configObj != null) {
            String aux = (String) configObj.get("letters");
            letters = aux.toCharArray();
            String aux2 = (String) configObj.get("testingLetter");
            testingLetter = aux2.charAt(0);
            testingLevel = (int) ((long) configObj.get("testingLevel"));
        }

        System.out.println("############################################################");
        System.out.println("############################################################");
        System.out.println("Executing Hopfield Exercise with the following parameters:");
        System.out.println("Letters: " + Arrays.toString(letters));
        System.out.println("Testing Letter: " + testingLetter);
        System.out.println("Testing Level: " + testingLevel);
        System.out.println("############################################################");
        System.out.println("############################################################\n");

        //new IncompatibleSets().test();
        List<Constants> constants = new ArrayList<>();
        constants.add(Constants.getByLetter(letters[0]));
        constants.add(Constants.getByLetter(letters[1]));
        constants.add(Constants.getByLetter(letters[2]));
        constants.add(Constants.getByLetter(letters[3]));
        Hopfield h = new Hopfield(constants);
        h.resolvePatron(Constants.getByLetterAndLevel(testingLetter, testingLevel).getRepresentation(),ROW_SIZE);

    }

    private static JSONObject getJsonConfig() {
        JSONParser jsonParser = new JSONParser();
        JSONObject configObj = null;
        try {
            configObj = (JSONObject) jsonParser.parse(new FileReader("./config_ej2.json"));
        } catch (Exception e) {
            try {
                configObj = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/config_ej2.json"));
            } catch (Exception e2) {
                e.printStackTrace();
            }
        }
        return configObj;
    }

}
