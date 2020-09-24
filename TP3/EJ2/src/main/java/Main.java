import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final int MAX_LENGTH = 200;
        double[][] input = new double[MAX_LENGTH][4];
        double[] expected_output = new double[MAX_LENGTH];

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

        int trainingSize;
        double eta;
        int steps;
        double beta;

        if (configObj != null) {
            trainingSize = (int) ((long) configObj.get("trainingSize"));
            eta = (double) configObj.get("eta");
            steps = (int) ((long) configObj.get("steps"));
             beta = (double) configObj.get("beta");
        }
        else {
            trainingSize = 160;
            eta = 0.01;
            steps = 500;
            beta = 2;
        }

        System.out.println("Configuration info: ");
        System.out.println("Training Size = " + trainingSize);
        System.out.println("Eta = " + eta);
        System.out.println("Steps = " + steps);
        System.out.println("Beta = " + beta);

        System.out.println("////////////////////////////////////////");
        Scanner inputScanner = new Scanner(new InputStreamReader(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("TP3-ej2-Conjunto-entrenamiento.txt"))));
        Scanner outputScanner = new Scanner(new InputStreamReader(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("TP3-ej2-Salida-deseada.txt"))));

        for (int i = 0; i < MAX_LENGTH; i++) {
            if (inputScanner.hasNextLine()) {
                String[] inputStr = inputScanner.nextLine().trim().split(" +");
                input[i][0] = 1;
                for (int j = 0; j < 3; j++) {
                    input[i][j+1] = Double.parseDouble(inputStr[j]);
                }
            }

            if (outputScanner.hasNextLine()) {
                String[] str = outputScanner.nextLine().trim().split(" +");
                expected_output[i] = Double.parseDouble(str[0]);
            }
        }

        OptionalDouble X_MAX = Arrays.stream(expected_output).max();
        OptionalDouble X_MIN = Arrays.stream(expected_output).min();

        double[] normalized_output = new double[MAX_LENGTH];

        for (int i = 0; i < 200; i++) {
            normalized_output[i] = (expected_output[i] - X_MIN.getAsDouble())/(X_MAX.getAsDouble() - X_MIN.getAsDouble());
        }

        Perceptron lp = new LinearPerceptron(4);
        lp.train(input,normalized_output,eta,steps, trainingSize);

        System.out.println("Max squared error was: " + lp.calculateError(Arrays.copyOfRange(input, trainingSize, MAX_LENGTH),
                Arrays.copyOfRange(normalized_output, trainingSize, MAX_LENGTH)));

        System.out.println("////////////////////////////////////////");

        Perceptron nlp = new NonLinearPerceptron(4, beta);
        nlp.train(input,normalized_output,eta,steps, trainingSize);

        System.out.println("Max squared error was: " + nlp.calculateError(Arrays.copyOfRange(input, trainingSize, MAX_LENGTH),
                Arrays.copyOfRange(normalized_output, trainingSize, MAX_LENGTH)));

    }
}
