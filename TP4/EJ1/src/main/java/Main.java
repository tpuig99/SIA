import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.ArrayUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws IOException {
         KohonenNetworkExercise();
         OjaRuleExercise();
    }

    public static void KohonenNetworkExercise() throws IOException {
        JSONObject configObj = getJsonConfig();

        double eta = 0.1;
        int epochs = 100;
        double initialNeighbourhoodSize = 3;
        int dimX = 3;
        int dimY = 3;

        if (configObj != null) {
            eta = (double) configObj.get("eta_a");
            epochs = (int) ((long) configObj.get("epochs_a"));
            initialNeighbourhoodSize = (double) configObj.get("initialNeighbourhoodValue");
            dimX = (int) ((long) configObj.get("dim_x"));
            dimY = (int) ((long) configObj.get("dim_y"));
        }

        int[] grid = new int[] {dimX, dimY};

        System.out.println("############################################################");
        System.out.println("############################################################");
        System.out.println("Executing Kohonen Exercise with the following parameters:");
        System.out.println("Eta: " + eta);
        System.out.println("Epochs: " + epochs);
        System.out.println("Initial Neighbourhood Value " + initialNeighbourhoodSize);
        System.out.println("Matrix Ranges: " + Arrays.toString(grid));
        System.out.println("############################################################");
        System.out.println("############################################################\n");

        double[][] parsedData = getNormalizedValues();
        String[] parsedCountries = getCountriesValues();

        KohonenNetwork kohonenNetwork = new KohonenNetwork(parsedData[0].length, grid);

        kohonenNetwork.train(parsedData, eta, epochs, initialNeighbourhoodSize);

        BufferedWriter writer1 = Files.newBufferedWriter(Paths.get("./data/distance_matrix.csv"), StandardCharsets.UTF_8);
        CSVPrinter csvPrinter1 = new CSVPrinter(writer1, CSVFormat.DEFAULT);
        System.out.println("Final distance matrix:");
        for (double[] row : kohonenNetwork.getDistanceMatrix()) {
            Double[] doubleArray = ArrayUtils.toObject(row);
            System.out.println(Arrays.toString(row));
            csvPrinter1.printRecord(Arrays.asList(doubleArray));
        }
        System.out.println();
        csvPrinter1.close();

        BufferedWriter writer2 = Files.newBufferedWriter(Paths.get("./data/countries_and_neurons.csv"), StandardCharsets.UTF_8);
        CSVPrinter csvPrinter2 = new CSVPrinter(writer2, CSVFormat.DEFAULT.withHeader("country", "neuron"));
        System.out.println("Countries and Associated Neurons:");
        for (int country = 0; country < parsedData.length; country++) {
            Integer[] associatedNeuron = kohonenNetwork.getWinningNeuronCoordinates(parsedData[country]);
            System.out.printf("Country %s: Associated Neuron: (%d, %d)\n", parsedCountries[country] , associatedNeuron[0], associatedNeuron[1]);
            String neuron = String.format("(%d;%d)", associatedNeuron[0], associatedNeuron[1]);
            csvPrinter2.printRecord(parsedCountries[country], neuron);
        }
        System.out.println();
        csvPrinter2.close();

        BufferedWriter writer3 = Files.newBufferedWriter(Paths.get("./data/registers_matrix.csv"), StandardCharsets.UTF_8);
        CSVPrinter csvPrinter3 = new CSVPrinter(writer3, CSVFormat.DEFAULT);
        System.out.println("Registers by Neuron:");
        for (int[] row : kohonenNetwork.getRegistersMatrix(parsedData)) {
            Integer[] integerArray = ArrayUtils.toObject(row);
            System.out.println(Arrays.toString(row));
            csvPrinter3.printRecord(Arrays.asList(integerArray));
        }
        csvPrinter3.close();

        System.out.println("\n\n");
    }

    public static void OjaRuleExercise() throws IOException {
        JSONObject configObj = getJsonConfig();

        double eta = 0.1;
        int epochs = 100;

        if (configObj != null) {
            eta = (double) configObj.get("eta_b");
            epochs = (int) ((long) configObj.get("epochs_b"));
        }

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

        ojaRulePerceptron.train(parsedData, eta, epochs);

        BufferedWriter writer = Files.newBufferedWriter(Paths.get("./data/oja_rule.csv"), StandardCharsets.UTF_8);
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("country", "index"));

        double value = ojaRulePerceptron.calculate(parsedData[0]) > 0 ? -1 : 1;
        for (int i = 0; i < parsedData.length; i++) {
            System.out.println(parsedCountries[i] + "\t" + ojaRulePerceptron.calculate(parsedData[i])*value);
            csvPrinter.printRecord(parsedCountries[i], ojaRulePerceptron.calculate(parsedData[i])*value);
        }
        csvPrinter.close();
    }

    private static double[][] getNormalizedValues() {
        double[][] parsedData = new double[28][7];
        int i = 0, j;

        File csvData = new File("./normalized_data.csv");

        if (!csvData.exists()) {
            csvData = new File("src/main/resources/normalized_data.csv");
            if (!csvData.exists()) {
                try {
                    throw new FileNotFoundException();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        CSVParser parser = null;

        try {
            parser = CSVParser.parse(csvData, StandardCharsets.UTF_8, CSVFormat.newFormat(';'));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (parser != null) {
            for (CSVRecord csvRecord : parser) {
                Iterator<String> recordIterator = csvRecord.iterator();
                j = 0;
                recordIterator.next();
                while (recordIterator.hasNext()) {
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

        File csvData = new File("./europe.csv");

        if (!csvData.exists()) {
            csvData = new File("src/main/resources/europe.csv");
            if (!csvData.exists()) {
                try {
                    throw new FileNotFoundException();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        CSVParser parser = null;

        try {
            parser = CSVParser.parse(csvData, StandardCharsets.UTF_8, CSVFormat.EXCEL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (parser != null) {
            Iterator<CSVRecord> csvRecordIterator = parser.iterator();
            csvRecordIterator.next();
            while (csvRecordIterator.hasNext()) {
                parsedCountries[i++] = csvRecordIterator.next().get(0);
            }
        }

        return parsedCountries;
    }

    private static JSONObject getJsonConfig() {
        JSONParser jsonParser = new JSONParser();
        JSONObject configObj = null;
        try {
            configObj = (JSONObject) jsonParser.parse(new FileReader("./config_ej1.json"));
        } catch (Exception e) {
            try {
                configObj = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/config_ej1.json"));
            } catch (Exception e2) {
                e.printStackTrace();
            }
        }
        return configObj;
    }

}
