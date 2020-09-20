import javafx.scene.shape.Path;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        final int MAX_LENGTH = 200;
        double[][] input = new double[MAX_LENGTH][3];
        double[] expected_output = new double[MAX_LENGTH];

        Scanner inputScanner = new Scanner(Paths.get("src/main/resources/TP3-ej2-Conjunto-entrenamiento.txt"));
        Scanner outputScanner = new Scanner(Paths.get("src/main/resources/TP3-ej2-Salida-deseada.txt"));

        for (int i = 0; i < MAX_LENGTH; i++) {
            if (inputScanner.hasNextLine()) {
                String[] inputStr = inputScanner.nextLine().trim().split(" +");
                for (int j = 0; j < 3; j++) {
                    input[i][j] = Double.parseDouble(inputStr[j]);
                }
            }

            if (outputScanner.hasNextLine()) {
                String[] str = outputScanner.nextLine().trim().split(" +");
                expected_output[i] = Double.parseDouble(str[0]);
            }
        }

//        Perceptron linearPerceptron = new LinearPerceptron(3);
//        linearPerceptron.train(input,expected_output,0.001,10000);
//
//        for (int k = 10; k < 200; k++) {
//            double compare = linearPerceptron.activationFunction(input[k]) - expected_output[k];
//            System.out.printf("Perceptron returned: %f \t Expected was: %f \t Comparison is: %f \t\n",
//                    linearPerceptron.activationFunction(input[k]), expected_output[k], compare);
//        }
//
//        System.out.println("W: " + Arrays.toString(linearPerceptron.getW()));

        Perceptron nonLinearPerceptron = new NonLinearPerceptron(3);
        nonLinearPerceptron.train(input,expected_output,0.01,200000);

        for (int k = 0; k < 10; k++) {
            double compare = nonLinearPerceptron.activationFunction(input[k]) - expected_output[k];
            System.out.printf("Perceptron returned: %f \t Expected was: %f \t Comparison is: %f \t\n",
                    nonLinearPerceptron.activationFunction(input[k]), expected_output[k], compare);
        }

        System.out.println("W: " + Arrays.toString(nonLinearPerceptron.getW()));

    }
}
