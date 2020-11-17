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

    public static void Ej2 () throws IOException {

        System.out.println("///////////////////////////////////////////////////////////\n");
        System.out.println(("Ejecutando Ejercicio 2"));
        System.out.println("///////////////////////////////////////////////////////////\n\n");

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

        printCSVData(nn, trainingSet, "./data/ej2.csv");

        System.out.printf("Error despues de entrenar %.3f\n\n",nn.getError(trainingSet, trainingSet));

        List<Double> zero = nn.calculateMiddleLayer(trainingSet[16]);
        List<Double> nine = nn.calculateMiddleLayer(trainingSet[25]);
        List<Double> eight = nn.calculateMiddleLayer(trainingSet[24]);
        List<Double> three = nn.calculateMiddleLayer(trainingSet[19]);

        double x1 = zero.get(0), y1 = zero.get(1), x2 = nine.get(0), y2 = nine.get(1);

        double midX = (x1+x2)/2;
        double midY = (y1+y2)/2;

        double leftX, leftY, rightX, rightY;

        System.out.println("Posiciones cartesianas:");

        if (x1 < x2) {
            leftX = (x1 + midX)/2;
            leftY = (y1 + midY)/2;
            rightX = (x2 + midX)/2;
            rightY = (y2 + midY)/2;

            System.out.println("0:\t(" + x1 + "\t;\t " + y1 + ")");
            System.out.println("Segundo punto (" + leftX + "\t;\t " + leftY + ")");
            System.out.println("Punto medio (" + midX + "\t;\t " + midY + ")");
            System.out.println("Cuarto punto (" + rightX + "\t; " + rightY + ")");
            System.out.println("9:\t(" + x2 + "\t;\t " + y2 + ")");
        } else {
            leftX = (x2 + midX)/2;
            leftY = (y2 + midY)/2;
            rightX = (x1 + midX)/2;
            rightY = (y1 + midY)/2;

            System.out.println("9:\t(" + x2 + "\t;\t " + y2 + ")");
            System.out.println("Segundo punto (" + leftX + "\t;\t " + leftY + ")");
            System.out.println("Punto medio (" + midX + "\t;\t " + midY + ")");
            System.out.println("Cuarto punto (" + rightX + "\t;\t " + rightY + ")");
            System.out.println("0:\t(" + x1 + "\t;\t " + y1 + ")");
        }

        System.out.println("8:\t(" + eight.get(0) + "\t;\t " + eight.get(1) + ")");
        System.out.println("3:\t(" + three.get(0) + "\t;\t " + three.get(1) + ")");

        System.out.println("First Letter:");
        if (x1 < x2) {
            printLetterFromList(nn.calculate(new double[]{x1, y1},3,6));
        } else {
            printLetterFromList(nn.calculate(new double[]{x2, y2},3,6));
        }

        System.out.println("Left hibrid:");
        printLetterFromList(nn.calculate(new double[]{leftX, leftY},3,6));

        System.out.println("Middle hibrid:");
        printLetterFromList(nn.calculate(new double[]{midX, midY},3,6));

        System.out.println("Right hibrid:");
        printLetterFromList(nn.calculate(new double[]{rightX, rightY},3,6));

        System.out.println("Last Letter:");
        if (x1 < x2) {
            printLetterFromList(nn.calculate(new double[]{x2, y2},3,6));
        } else {
            printLetterFromList(nn.calculate(new double[]{x1, y1},3,6));
        }

        System.out.println("Letter: 3");
        printLetterFromList(nn.calculate(new double[]{three.get(0), three.get(1)},3,6));

        System.out.println("Letter: 8");
        printLetterFromList(nn.calculate(new double[]{eight.get(0), eight.get(1)},3,6));

    }

    public static void printLetterFromList(List<Double> list) {
        double [] array_list = new double[35];
        for (int i = 0; i < 35 ; i++) {
            array_list[i] = list.get(i) > 0.5 ? 1 : 0;
        }
        PrintLetterMethod.printLetter(array_list, 5);
    }

    public static void Ej1b () {

        System.out.println("///////////////////////////////////////////////////////////\n");
        System.out.println(("Ejecutando Ejercicio 1B"));
        System.out.println("///////////////////////////////////////////////////////////\n\n");

        Font[] fonts = new Font[Constants.font1.length];
        NoisyFont[] noisyFonts = new NoisyFont[Constants.font1.length];

        double[][] trainingSet = new double[32][35];
        double[][] expectedResultSet = new double[32][35];

        for(int i = 0; i < fonts.length ; i++) {
            fonts[i] = new Font(Constants.font1[i], 7, 5);
            noisyFonts[i] = new NoisyFont(Constants.font1[i], 7, 5, 2);
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
        System.out.println("///////////////////////////////////////////////////////////\n");
        System.out.println(("Ejecutando Ejercicio 1A"));
        System.out.println("///////////////////////////////////////////////////////////\n\n");


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

        for (int[] layerConfig : layers) {
            System.out.println("Layer :" + Arrays.toString(layerConfig));

            nn = new MLP(35, layerConfig, 35, 0.0012, tn);

            nn.train(trainingSet, trainingSet, 10000, true, 0.75);

            System.out.printf("Error despues de entrenar %.3f\n\n", nn.getError(trainingSet, trainingSet));

            System.out.println("///////////////////////////////////////////////////////////\n\n");
        }

        printCSVData(nn, trainingSet, "./data/ej1a.csv");

    }

    public static void printCSVData(MLP nn, double[][] trainingSet, String fileName) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName), StandardCharsets.UTF_8);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("x", "y", "value"));

        String values = " !\"#$%&'()*+,-./0123456789:;<=>?";

        for (int i = 0; i < trainingSet.length; i++) {
            List<Double> l = nn.calculateMiddleLayer(trainingSet[i]);
            csvPrinter.printRecord(l.get(0), l.get(1), values.charAt(i));
        }

        csvPrinter.close();
    }
}
