import java.util.List;

public class Main1 {
    public static void main(String[] args) {

        double[][] training_set = {
                {0, 1},
                {1, 0},
                {0, 0},
                {1, 1}
        };
            MultilayerPerceptron nn = new MultilayerPerceptron(2, new int[]{3}, 1, 0.01);

            double[][] result_set = {{1}, {1}, {0}, {0}};

            for (int i = 0; i < 1000; i++) {
                System.out.println("Error "+i+": " + nn.getError(training_set, result_set));
                nn.train(training_set, result_set, 100);
            }

            for(int i = 0; i < training_set.length; i++) {
                List<Double> resultSet = nn.calculate(training_set[i]);
                double result = resultSet.get(0);

                System.out.println(Math.round(training_set[i][0]) + " XOR " + Math.round(training_set[i][1]) + " = " + Math.round(result) + " (" + result + ")");
            }

    }
}
