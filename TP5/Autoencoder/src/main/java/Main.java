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
        //Ej1a();
       Ej1b();
    }

    public static void Ej1b () {
        Font[] fonts = new Font[Constants.font1.length];
        NoisyFont[] noisyFonts = new NoisyFont[Constants.font1.length];

        double[][] trainingSet = new double[32][35];
        double[][] expectedResultSet = new double[32][35];

        for(int i = 0; i < fonts.length ; i++) {
            fonts[i] = new Font(Constants.font1[i], 7, 5);
            noisyFonts[i] = new NoisyFont(Constants.font1[i], 7, 5, 5);
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
        };
        PrintLetterMethod printLetterMethod = new PrintLetterMethod();
        MLP nn = new MLP(35, layers[0], 35, 0.0012,tn);
        nn.train(trainingSet, trainingSet, 10000,true,0.75);
//        int count = 0;
//        for (double[] item:trainingSet) {
//            List<Double> result = nn.calculate(item);
//            double array_result[] = new double[item.length];
//            boolean error = false;
//            for (int i = 0; i < item.length; i++) {
//                array_result[i]=result.get(i)<0.5?0:1;
//                if(array_result[i]!=item[i]){
//                    error=true;
//                }
//            }
//            if(error) {
//                System.out.println("original");
//                System.out.println();
//                printLetterMethod.printLetter(item, 5);
//                System.out.println("error:");
//                System.out.println();
//                printLetterMethod.printLetter(array_result, 5);
//                count++;
//            }
//        }
//        System.out.println(count);


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
