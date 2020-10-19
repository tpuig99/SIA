import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        OjaRuleExercise();
    }

    public static void OjaRuleExercise() throws IOException {
        double[][] parsedData = new double[28][7];
        int i = 0, j;

        File csvData = new File("src/main/resources/normalized_data.csv");
        CSVParser parser = CSVParser.parse(csvData, StandardCharsets.UTF_8, CSVFormat.newFormat(';'));

        for (CSVRecord csvRecord : parser) {
            Iterator<String> recordIterator = csvRecord.iterator();
            j = 0;
            recordIterator.next();
            while(recordIterator.hasNext()) {
                parsedData[i][j++] = Double.parseDouble(recordIterator.next());
            }
            i++;
        }

        OjaRulePerceptron ojaRulePerceptron = new OjaRulePerceptron(parsedData[0].length);

        ojaRulePerceptron.train(parsedData, 0.4, 100000);

        for (int country = 0; country < parsedData.length; country++) {
            System.out.printf("Country %d: %f\n", country ,ojaRulePerceptron.calculate(parsedData[country]));
        }
    }
}
