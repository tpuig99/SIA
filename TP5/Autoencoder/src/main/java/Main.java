import models.Font;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Font[] fonts = new Font[Constants.font1.length];

        double[][] trainingSet = new double[32][35];

        for(int i = 0; i < fonts.length ; i++) {
            fonts[i] = new Font(Constants.font1[i], 7, 5);
        }

        for (int k = 0; k < fonts.length ; k++) {
            for (int j = 0; j < fonts[k].getAsArray().length ; j++) {
                trainingSet[k][j] = fonts[k].getAsArray()[j];
            }
        }

        MultilayerPerceptron nn = new MultilayerPerceptron(35, new int[]{40, 12, 2, 12, 40}, 35, 0.0012);

        nn.train(trainingSet, trainingSet, 10000);

        for (double[] item : trainingSet) {
            System.out.print("[");
            for (double d : nn.calculate(item)) {
                System.out.printf("%.2f ,", d);
            }
            System.out.println("]");
        }
    }
}
