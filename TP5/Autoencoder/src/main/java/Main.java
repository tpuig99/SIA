import models.Font;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
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

        MultilayerPerceptron nn = new MultilayerPerceptron(35, new int[]{12, 2, 12}, 35, 0.0012);

        nn.train(trainingSet, trainingSet, 10000);

        BufferedWriter writer = Files.newBufferedWriter(Paths.get("./data/middle_layer.csv"), StandardCharsets.UTF_8);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("x", "y", "value"));

        String values = " !\"#$%&'()*+,-./0123456789:;<=>?";

        for (int i = 0; i < trainingSet.length; i++) {
            List<Double> l = nn.getMiddleLayerForInput(trainingSet[i]);
            csvPrinter.printRecord(l.get(0), l.get(1), values.charAt(i));
        }

        csvPrinter.close();

        for (double[] item : trainingSet) {
            List<Double> l = nn.calculate(item);
            for (Double d : l) {
                System.out.printf("%.2f ", d);
            }
            System.out.println();
        }
    }
}
