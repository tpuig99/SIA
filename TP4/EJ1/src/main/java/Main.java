import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws IOException {
        // KohonenNetworkExercise();
         OjaRuleExercise();
    }

    public static void KohonenNetworkExercise() {
        double[][] parsedData = getNormalizedValues();

        KohonenNetwork kohonenNetwork = new KohonenNetwork(parsedData[0].length, new int[]{3, 3});

        kohonenNetwork.train(parsedData, 0.1, 100, 3);
        int i = 0;
        for (double[] row : kohonenNetwork.getDistanceMatrix()) {
            System.out.println("Row "+(i++)+": "+ Arrays.toString(row));
        }

        for (int country = 0; country < parsedData.length; country++) {
            Integer[] associatedNeuron = kohonenNetwork.getWinningNeuronCoordinates(parsedData[country]);
            System.out.printf("Country %d: Associated Neuron: (%d, %d)\n", country+1 , associatedNeuron[0], associatedNeuron[1]);
        }

        i = 0;
        for (int[] row : kohonenNetwork.getRegistersMatrix(parsedData)) {
            System.out.println("Row "+(i++)+": "+ Arrays.toString(row));
        }

    }

    public static void OjaRuleExercise() throws IOException {
        double eta = 0.4;
        int epochs = 100000;
        System.out.println("############################################################");
        System.out.println("############################################################");
        System.out.println("Executing Oja's Rule Exercise with the following parameters:");
        System.out.println("Eta: " + eta);
        System.out.println("Epochs: " + epochs);
        System.out.println("############################################################");
        System.out.println("############################################################\n");

        double[][] parsedData = getNormalizedValues();
        String[] parsedCountries = getCountriesValues();

        OjaRulePerceptron ojaRulePerceptron = new OjaRulePerceptron(parsedData[0].length);

        ojaRulePerceptron.train(parsedData, 0.4, 100000);

        BufferedWriter writer = Files.newBufferedWriter(Paths.get("./oja_rule.csv"), StandardCharsets.UTF_8);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("country", "index"));

        for (int i = 0; i < parsedData.length; i++) {
            System.out.println(parsedCountries[i] + "\t" + ojaRulePerceptron.calculate(parsedData[i]));
            csvPrinter.printRecord(parsedCountries[i], ojaRulePerceptron.calculate(parsedData[i]));
        }
        csvPrinter.close();
    }

    private static double[][] getNormalizedValues() {
        double[][] parsedData = new double[28][7];
        int i = 0, j;

        File csvData = new File("src/main/resources/normalized_data.csv");
        CSVParser parser = null;

        try {
            parser = CSVParser.parse(csvData, StandardCharsets.UTF_8, CSVFormat.newFormat(';'));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (parser !=  null) {
            for (CSVRecord csvRecord : parser) {
                Iterator<String> recordIterator = csvRecord.iterator();
                j = 0;
                recordIterator.next();
                while(recordIterator.hasNext()) {
                    parsedData[i][j++] = Double.parseDouble(recordIterator.next());
                }
                i++;
            }
        }

        return parsedData;
    }


    private static String[] getCountriesValues() {
        String[] parsedCountries = new String[28];
        int i = 0;

        File csvData = new File("src/main/resources/europe.csv");
        CSVParser parser = null;

        try {
            parser = CSVParser.parse(csvData, StandardCharsets.UTF_8, CSVFormat.EXCEL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (parser !=  null) {
            Iterator<CSVRecord> csvRecordIterator = parser.iterator();
            csvRecordIterator.next();
            while(csvRecordIterator.hasNext()) {
                parsedCountries[i++] = csvRecordIterator.next().get(0);
            }
        }

        return parsedCountries;
    }

}
