import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ej1();
    }

    public static void ej1 () {

        JSONParser jsonParser = new JSONParser();
        JSONObject configObj = null;
        try {
            configObj = (JSONObject) jsonParser.parse(new FileReader("./config.json"));
        } catch (Exception e) {
            try {
                System.err.println("Error while reading config file. Attempting to read from auxiliar file");
                configObj = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/config.json"));
            } catch (Exception e2) {
                e.printStackTrace();
            }
        }

        int iterations;
        double eta;
        int steps;

        if (configObj != null) {
            iterations = (int) ((long) configObj.get("iterations"));
            eta = (double) configObj.get("eta");
            steps = (int) ((long) configObj.get("steps"));
        }
        else {
            iterations = 100;
            eta = 0.2;
            steps = 20;
        }

        System.out.println("Configuration info: ");
        System.out.println("Iterations = " + iterations);
        System.out.println("Eta = " + eta);
        System.out.println("Steps = " + steps);

        System.out.println("////////////////////////////////////////////////");
        // AND
        SimplePerceptron simplePerceptron = new SimplePerceptron(3);
        double[][] input = {{-1, -1, 1}, {-1, 1, -1}, {-1, -1, -1}, {-1, 1, 1}};
        double[] expected_output_AND = {-1, -1, -1, 1};
        int i = 0;
        int fails = 0;
        while (i++ < iterations) {
            if (!Arrays.equals(simplePerceptron.train(input, expected_output_AND, eta, steps), expected_output_AND)) {
             fails ++;
            }
        }
        System.out.printf("In the AND problem, there were %d fails\n", fails);

        System.out.println("////////////////////////////////////////////////");
        // XOR
        SimplePerceptron simplePerceptron2 = new SimplePerceptron(3);
        double[] expectedOutputXOR = {1, 1, -1, -1};
        int XORi = 0;
        int XORfails = 0;
        while (XORi++ < iterations) {
            if (!Arrays.equals(simplePerceptron2.train(input, expectedOutputXOR, eta, steps), expectedOutputXOR)) {
                XORfails ++;
            }
        }
        System.out.printf("In the XOR problem, there were %d fails\n", XORfails);


    }
}
