import NeuralNetwork.Functions.ActivationFunction;
import NeuralNetwork.Functions.SigmoidActivationFunction;
import NeuralNetwork.Functions.TanhActivationFunction;
import NeuralNetwork.MLP;
import models.Font;
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
//                {25, 10, 2, 10, 25},
//                {20, 15, 2, 15, 20},
                {30,20,2,20,30},
                {30,10,2,10,30},
//                {10,2,10},
//                {20,2,20},
//                {17,7,2,7,17},
//                {30,3,2,3,30},
//                {15,5,2,5,15},
                {20,10,5,10,20},
//                {30,20,5,20,30}
        };

        MLP nn = new MLP(35, layers[0], 35, 0.0012,tn);

        for (int i = 0; i < 10; i++) {
            System.out.println("Corrida: " +i);
            for (int[] layerConfig : layers) {
                System.out.println("Layer :" + Arrays.toString(layerConfig));

                nn = new MLP(35, layerConfig, 35, 0.0012,tn);

                nn.train(trainingSet, trainingSet, 10000,true,0.75);

                System.out.printf("Error despues de entrenar %.3f\n\n",nn.getError(trainingSet,trainingSet));

                System.out.println("///////////////////////////////////////////////////////////\n\n");
            }
        }

//        MLP nn = new MLP(35, new int[]{40,12,2,12,40}, 35, 0.0012,tn);
//        System.out.printf("Error antes de entrenar %.3f\n",nn.getError(trainingSet,trainingSet));
//        nn.train(trainingSet, trainingSet, 10000,false,0.75);
//        System.out.printf("Error despues de entrenar %.3f\n\n",nn.getError(trainingSet,trainingSet));


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
