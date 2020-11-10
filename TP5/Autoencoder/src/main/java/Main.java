import NeuralNetwork.Functions.ActivationFunction;
import NeuralNetwork.Functions.SigmoidActivationFunction;
import NeuralNetwork.Functions.TanhActivationFunction;
import NeuralNetwork.MLP;
import models.Font;
import models.NoisyFont;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Ej1a();
        Ej1b();
        Ej2();
    }

    public static void Ej2 () {
        Font[] fonts = new Font[Constants.font1.length];

        double[][] trainingSet = new double[32][35];

        for(int i = 0; i < fonts.length ; i++) {
            fonts[i] = new Font(Constants.font1[i], 7, 5);
        }

        int from=0;
        int to=32;
        for (int k = from; k < to ; k++) {
            for (int j = 0; j < fonts[k].getAsArray().length ; j++) {
                trainingSet[k][j] = fonts[k].getAsArray()[j];
            }
        }


        ActivationFunction tn = new TanhActivationFunction(2.3);

        MLP nn = new MLP(35, new int[] {30,20,2,20,30}, 35, 0.0012,tn);

        nn.train(trainingSet, trainingSet, 10000,true,0.75);

        System.out.printf("Error despues de entrenar %.3f\n\n",nn.getError(trainingSet, trainingSet));

        List<Double> nine = nn.calculateMiddleLayer(trainingSet[16]);
        List<Double> questionMark = nn.calculateMiddleLayer(trainingSet[25]);

        double x1 = nine.get(0), y1 = nine.get(1), x2 = questionMark.get(0), y2 = questionMark.get(1);

        double midX = (x1+x2)/2;
        double midY = (y1+y2)/2;

        double leftX, leftY, rightX, rightY;

        System.out.println("Posiciones cartesianas:");

        if (x1 < x2) {
            leftX = (x1 + midX)/2;
            leftY = (y1 + midY)/2;
            rightX = (x2 + midX)/2;
            rightY = (y2 + midY)/2;

            System.out.println("0:  (" + x1 + "; " + y1 + ")");
            System.out.println("Segundo punto (" + leftX + "; " + leftY + ")");
            System.out.println("Punto medio (" + midX + "; " + midY + ")");
            System.out.println("Cuarto punto (" + rightX + "; " + rightY + ")");
            System.out.println("9:  (" + x2 + "; " + y2 + ")");
        } else {
            leftX = (x2 + midX)/2;
            leftY = (y2 + midY)/2;
            rightX = (x1 + midX)/2;
            rightY = (y1 + midY)/2;

            System.out.println("9:  (" + x2 + "; " + y2 + ")");
            System.out.println("Segundo punto (" + leftX + "; " + leftY + ")");
            System.out.println("Punto medio (" + midX + "; " + midY + ")");
            System.out.println("Cuarto punto (" + rightX + "; " + rightY + ")");
            System.out.println("0:  (" + x1 + "; " + y1 + ")");
        }

        if (x1 < x2) {
            List<Double> f_letter = nn.calculate(new double[]{x1, y1}, 3, 6);
            System.out.println("First Letter:");
            double [] array = new double[35];
            for (int i = 0; i < 35 ; i++) {
                array[i] = f_letter.get(i) > 0.5 ? 1 : 0;
            }
            PrintLetterMethod.printLetter(array, 5);

        } else {
            List<Double> f_letter = nn.calculate(new double[]{x2, y2}, 3, 6);
            System.out.println("First Letter:");

            double [] array = new double[35];
            for (int i = 0; i < 35 ; i++) {
                array[i] = f_letter.get(i) > 0.5 ? 1 : 0;
            }
            PrintLetterMethod.printLetter(array, 5);
        }


        List<Double> left = nn.calculate(new double[]{leftX, leftY}, 3, 6);
        System.out.println("Left hibrid:");

        double [] array_left = new double[35];
        for (int i = 0; i < 35 ; i++) {
            array_left[i] = left.get(i) > 0.5 ? 1 : 0;
        }
        PrintLetterMethod.printLetter(array_left, 5);

        List<Double> mid = nn.calculate(new double[]{midX, midY}, 3, 6);
        System.out.println("Middle hibrid:");

        double [] array_mid = new double[35];
        for (int i = 0; i < 35 ; i++) {
            array_mid[i] = mid.get(i) > 0.5 ? 1 : 0;
        }
        PrintLetterMethod.printLetter(array_mid, 5);

        List<Double> right = nn.calculate(new double[]{rightX, rightY}, 3, 6);
        System.out.println("Right hibrid:");

        double [] array_right = new double[35];
        for (int i = 0; i < 35 ; i++) {
            array_right[i] = right.get(i) > 0.5 ? 1 : 0;
        }
        PrintLetterMethod.printLetter(array_right, 5);

        if (x1 < x2) {
            List<Double> f_letter = nn.calculate(new double[]{x2, y2}, 3, 6);
            System.out.println("Last Letter:");

            double [] array_last = new double[35];
            for (int i = 0; i < 35 ; i++) {
                array_last[i] = f_letter.get(i) > 0.5 ? 1 : 0;
            }
            PrintLetterMethod.printLetter(array_last, 5);

        } else {
            List<Double> f_letter = nn.calculate(new double[]{x1, y1}, 3, 6);
            System.out.println("Last Letter:");
            double [] array_last = new double[35];
            for (int i = 0; i < 35 ; i++) {
                array_last[i] = f_letter.get(i) > 0.5 ? 1 : 0;
            }
            PrintLetterMethod.printLetter(array_last, 5);

        }

    }

    public static void Ej1b () {
        Font[] fonts = new Font[Constants.font1.length];
        NoisyFont[] noisyFonts = new NoisyFont[Constants.font1.length];

        double[][] trainingSet = new double[32][35];
        double[][] expectedResultSet = new double[32][35];

        for(int i = 0; i < fonts.length ; i++) {
            fonts[i] = new Font(Constants.font1[i], 7, 5);
            noisyFonts[i] = new NoisyFont(Constants.font1[i], 7, 5, 1);
            System.out.println("Clean font:");
            System.out.println(fonts[i].toString());
            System.out.println("Noisy font:");
            System.out.println(noisyFonts[i].toString());
            System.out.println("/////////////////\n\n");
        }

        int from=0;
        int to=32;
        for (int k = from; k < to ; k++) {
            for (int j = 0; j < fonts[k].getAsArray().length ; j++) {
                trainingSet[k][j] = noisyFonts[k].getAsArray()[j];
                expectedResultSet[k][j] = fonts[k].getAsArray()[j];
            }
        }

        ActivationFunction tn = new TanhActivationFunction(2.3);

        MLP nn = new MLP(35, new int[] {30,20,2,20,30}, 35, 0.0012,tn);

        nn.train(trainingSet, expectedResultSet, 10000,true,0.75);

        System.out.printf("Error despues de entrenar %.3f\n\n",nn.getError(trainingSet, expectedResultSet));

    }

    public static void Ej1a () throws IOException {
        Font[] fonts = new Font[Constants.font1.length];

        double[][] trainingSet = new double[32][35];

        for(int i = 0; i < fonts.length ; i++) {
            fonts[i] = new Font(Constants.font1[i], 7, 5);
        }

        int from=0;
        int to=32;
        for (int k = from; k < to ; k++) {
            for (int j = 0; j < fonts[k].getAsArray().length ; j++) {
                trainingSet[k][j] = fonts[k].getAsArray()[j];
            }
        }

        ActivationFunction tn = new TanhActivationFunction(2.3);
        ActivationFunction sig = new SigmoidActivationFunction();

        int[][] layers = {
                {30,20,2,20,30},
                {30,10,2,10,30},
                {20,10,5,10,20},
        };

        MLP nn = new MLP(35, layers[0], 35, 0.0012,tn);

        for (int i = 0; i < 1; i++) {
            System.out.println("Corrida: " +i);
            for (int[] layerConfig : layers) {
                System.out.println("Layer :" + Arrays.toString(layerConfig));

                nn = new MLP(35, layerConfig, 35, 0.0012,tn);

                nn.train(trainingSet, trainingSet, 10000,true,0.75);

                System.out.printf("Error despues de entrenar %.3f\n\n",nn.getError(trainingSet,trainingSet));

                System.out.println("///////////////////////////////////////////////////////////\n\n");
            }
        }


        BufferedWriter writer = Files.newBufferedWriter(Paths.get("./data/middle_layer.csv"), StandardCharsets.UTF_8);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("x", "y", "value"));

        String values = " !\"#$%&'()*+,-./0123456789:;<=>?";

        for (int i = 0; i < trainingSet.length; i++) {
            List<Double> l = nn.calculateMiddleLayer(trainingSet[i]);
            csvPrinter.printRecord(l.get(0), l.get(1), values.charAt(i));
        }

        csvPrinter.close();

    }
}
