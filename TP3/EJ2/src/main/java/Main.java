import javafx.scene.shape.Path;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        final int MAX_LENGTH = 200;
        double[][] input = new double[MAX_LENGTH][4];
        double[] expected_output = new double[MAX_LENGTH];

        Scanner inputScanner = new Scanner(Paths.get("src/main/resources/TP3-ej2-Conjunto-entrenamiento.txt"));
        Scanner outputScanner = new Scanner(Paths.get("src/main/resources/TP3-ej2-Salida-deseada.txt"));

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
//
//        Perceptron linearPerceptron = new LinearPerceptron(4);
//        linearPerceptron.train(input,normalized_output,0.000001,100000, 160);
//
//        for (int k = 160; k < 200; k++) {
//            double compare = linearPerceptron.activationFunction(input[k]) - normalized_output[k];
//            System.out.printf("Perceptron returned: %f \t Expected was: %f \t Comparison is: %f \t\n",
//                    linearPerceptron.activationFunction(input[k]), normalized_output[k], compare);
//        }
//
//        System.out.println("W: " + Arrays.toString(linearPerceptron.getW()));

        Perceptron nonLinearPerceptron = new NonLinearPerceptron(4);
        nonLinearPerceptron.train(input,normalized_output,0.001,100000, 160);

        for (int k = 160; k < 200; k++) {
            double compare = nonLinearPerceptron.activationFunction(input[k]) - normalized_output[k];
            System.out.printf("Perceptron returned: %f \t Expected was: %f \t Comparison is: %f \t\n",
                    nonLinearPerceptron.activationFunction(input[k]), normalized_output[k], compare);
        }

        System.out.println("W: " + Arrays.toString(nonLinearPerceptron.getW()));

    }
}
