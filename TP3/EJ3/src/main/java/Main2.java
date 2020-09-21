import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main2 {
    public static void main(String[] args) {
        double [][] training_set = new double[10][35];
        double [][] result_set = {{1}, {0}, {1}, {0}, {1}, {0}, {1}, {0}, {1}, {0}};

        try {
            File sourceFile = new File("data/numeros.txt");
            BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
            String line;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 7; j++) {
                    line = reader.readLine();
                    String[] strPixel = line.split(" ");
                    for (int k = 0; k < 5; k++) {
                        training_set[i][j*5+k] = Double.valueOf(strPixel[k]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
