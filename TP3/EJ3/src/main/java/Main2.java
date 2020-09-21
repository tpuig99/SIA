import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        double [][] set = new double[10][35];
        double [][] training_result_set = {{1}, {0}, {1}, {0}, {1}};
        double [][] training_set = new double[5][35];
        double [][] testing_set = new double[5][35];
        double [][] testing_result_set = {{0}, {1}, {0}, {1}, {0}};
        try {
            File sourceFile = new File("C:\\Users\\tamyp\\OneDrive\\Desktop\\ITBA\\3ero\\SIA\\TP3\\EJ3\\src\\main\\resources\\numeros.txt");
            BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
            String line;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 7; j++) {
                    line = reader.readLine();
                    String[] strPixel = line.split(" ");
                    for (int k = 0; k < 5; k++) {
                        set[i][j*5+k] = Double.valueOf(strPixel[k]);
                        if(i<5){
                            training_set[i][j*5+k]=set[i][j*5+k];
                        }else{
                            testing_set[i%5][j*5+k]=set[i][j*5+k];
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            for (int j = 0; j < 35; j++) {
                if(j%5==0){
                    System.out.print('\n');
                }
                System.out.print(set[i][j]);
            }
            System.out.println();
            System.out.println();
        }

        MultilayerPerceptron nn = new MultilayerPerceptron(35, new int[]{40}, 1, 0.01);



        for (int i = 0; i < 1000; i++) {
            System.out.println("Error: " + nn.getError(training_set, training_result_set));
            nn.train(training_set, training_result_set, 100);
        }
        for(int i = 0; i < training_set.length; i++) {
            List<Double> resultSet = nn.calculate(training_set[i]);
            double result = resultSet.get(0);

            System.out.println(i + " = " + Math.round(result) + " (" + result + ")");
        }

        for(int i = 0; i < testing_set.length; i++) {
            List<Double> resultSet = nn.calculate(testing_set[i]);
            double result = resultSet.get(0);

            System.out.println(i+5 + " = " + Math.round(result) + " (" + result + ")");
        }

    }
}
