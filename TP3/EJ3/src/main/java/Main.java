
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

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
        int[] neuronsByLayer;
        int steps;


        if (configObj != null) {
            iterations = (int) ((long) configObj.get("iterations"));
            eta = (double) configObj.get("eta");
            JSONArray array = ((JSONArray) configObj.get("neuronsByLayer"));
            neuronsByLayer = new int[array.size()];

            for (int i = 0; i < array.size(); ++i) {
                neuronsByLayer[i] = (int) ((long) array.get(i));
            }

            steps = (int) ((long) configObj.get("steps"));
        }
        else {
            iterations = 1000;
            eta = 0.01;
            neuronsByLayer = new int[]{3};
            steps = 100;
        }


        System.out.println("Configuration info: ");
        System.out.println("Iterations = " + iterations);
        System.out.println("Eta = " + eta);
        System.out.println("Steps = " + steps);
        System.out.println("Neurons Configuration = " + Arrays.toString(neuronsByLayer));

        System.out.println("////////////////////////////////////////");


        double[][] training_set = {
                {0, 1},
                {1, 0},
                {0, 0},
                {1, 1}
        };


        MultilayerPerceptron nnej1 = new MultilayerPerceptron(2, neuronsByLayer, 1, eta);

        double[][] result_set = {{1}, {1}, {0}, {0}};

        for (int i = 0; i < iterations; i++) {
            System.out.println("Error "+i+": " + nnej1.getError(training_set, result_set));
            nnej1.train(training_set, result_set, steps);
        }

        for(int i = 0; i < training_set.length; i++) {
            List<Double> resultSet = nnej1.calculate(training_set[i]);
            double result = resultSet.get(0);

            System.out.println(Math.round(training_set[i][0]) + " XOR " + Math.round(training_set[i][1]) + " = " + Math.abs(Math.round(result)) + " (" + Math.abs(result) + ")");
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int iterations2;
        double eta2;
        int[] neuronsByLayer2;
        List<Integer> trainingArray;
        int steps2;


        if (configObj != null) {
            iterations2 = (int) ((long) configObj.get("iterations2"));
            eta2 = (double) configObj.get("eta2");
            JSONArray array = ((JSONArray) configObj.get("neuronsByLayer2"));
            neuronsByLayer2 = new int[array.size()];
            for (int i = 0; i < array.size(); ++i) {
                neuronsByLayer2[i] = (int) ((long) array.get(i));
            }

            trainingArray = new ArrayList<>();

            JSONArray array2 = ((JSONArray) configObj.get("trainingArray"));

            for (int i = 0; i < array2.size(); ++i) {
                trainingArray.add((int) ((long) array2.get(i)));
            }

            steps2 = (int) ((long) configObj.get("steps2"));
        }
        else {
            iterations2 = 1000;
            eta2 = 0.01;
            neuronsByLayer2 = new int[]{3};
            steps2 = 100;
            trainingArray = Arrays.stream(new int[]{0, 1, 2, 3, 4}).boxed().collect(Collectors.toList());
        }

        System.out.println("////////////////////////////////////////");

        System.out.println("Configuration info: ");
        System.out.println("Iterations = " + iterations2);
        System.out.println("Eta = " + eta2);
        System.out.println("Steps = " + steps2);
        System.out.println("Neurons Configuration = " + Arrays.toString(neuronsByLayer2));
        System.out.println("Training Array Chosen: " + trainingArray.toString());

        System.out.println("////////////////////////////////////////");

        double [][] set2 = new double[10][35];
        double [][] resultSet2 = {{1},{0},{1},{0},{1},{0},{1},{0},{1},{0}};
        double [][] training_result_set2 = new double[trainingArray.size()][1];
        double [][] training_set2 = new double[trainingArray.size()][35];
        double [][] testing_set2 = new double[10-trainingArray.size()][35];
        double [][] testing_result_set2 = new double[10-trainingArray.size()][1];

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("numeros.txt"))));

            String line;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 7; j++) {
                    line = reader.readLine();
                    String[] strPixel = line.split(" ");
                    for (int k = 0; k < 5; k++) {
                        set2[i][j*5+k] = Double.parseDouble(strPixel[k]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int test = 0;
        int train = 0;
        for (int i = 0; i < 10; i++) {
            if(trainingArray.contains(i)){
                for (int j = 0; j < 35; j++) {
                    training_set2[train][j]=set2[i][j];
                    training_result_set2[train]=resultSet2[i];
                }
                train++;
            }else {
                for (int j = 0; j < 35; j++) {
                    testing_set2[test][j]=set2[i][j];
                    testing_result_set2[test]=resultSet2[i];
                }
                test++;
            }
        }

        MultilayerPerceptron nn = new MultilayerPerceptron(35, neuronsByLayer2, 1, eta2);


        for (int i = 0; i < iterations2; i++) {
            System.out.println("Error: " + nn.getError(training_set2, training_result_set2));
            nn.train(training_set2, training_result_set2, steps2);
        }
        for(int i = 0; i < training_set2.length; i++) {
            double result = nn.calculate(training_set2[i]).get(0);

            System.out.println(i + " = " + Math.abs(Math.round(result)) + " (" + Math.abs(result) + ")");
        }

        for(int i = 0; i < testing_set2.length; i++) {
            double result = nn.calculate(testing_set2[i]).get(0);

            System.out.println(i+5 + " = " + Math.abs(Math.round(result)) + " (" + Math.abs(result) + ")");
        }
    }
}
