import java.util.*;

public class NonLinearPerceptron implements Perceptron {
    private final int connections;
    private double[] w;
    private final double beta = 2;

    public NonLinearPerceptron(int connections) {
        this.connections = connections;
        this.w = new double[connections];
        for (int i = 0; i < connections; i++) {
            w[i] = Math.random() * 2 - 1;
        }
    }


    private double g_prime(double input) {
        double exp = Math.exp(-beta*input);
        return (beta * exp ) / Math.pow((1 + exp) ,2);
    }

    private double g(double input) {
        return 1 / (1 + Math.exp(-beta*input));
    }


    @Override
    public double calculate(double[] input) {return g(activationFunction(input)); }

    @Override
    public double activationFunction(double[] input) {
        double output = 0;
        for (int i = 0; i < w.length; i++) {
            output += w[i] * input[i];
        }
        return output;
    }

    @Override
    public void train (double[][] input, double[] expected_output, double learning_rate, int steps, int epochSize) {
        Random rnd = new Random();
        double error = 1;
        double error_min = expected_output.length*2;
        double[] w_min = w.clone();
        int i = 0;
        double[][] epoch_input = Arrays.copyOf(input, epochSize);

        while (error > 0.0001 && i < steps) {
            List<Integer> integerList = new ArrayList<>();
            for (int e = 0; e < epochSize; e++) {
                integerList.add(e);
            }
            Collections.shuffle(integerList);

            for (int elem = 0; elem < integerList.size(); elem++) {

                if (i < steps * 0.1) {
                    for (int j = 0; j < connections; j++) {
                        w[j] = Math.random() * 2 - 1;
                    }
                }

                int curr = elem;
                double activation = activationFunction(epoch_input[curr]);

                for (int j = 0; j < this.connections; j++) {
                    w[j] += learning_rate * (expected_output[curr] - g(activation)) * g_prime(activation) * epoch_input[curr][j];
                }

                error = calculateError(epoch_input, expected_output);
                if (error < error_min) {
                    error_min = error;
                    w_min = w.clone();
                } else {
                    w = w_min;
                }
            }

            i++;
            System.out.println("Max squared error was: " + calculateError(Arrays.copyOfRange(input, epochSize, 200),
                    Arrays.copyOfRange(expected_output, epochSize, 200)));
        }
    }

    @Override
    public double calculateError (double[][] input, double[] expected_output) {
        double error = 0;
        for (int j = 0; j < input.length; j++) {
            error += Math.pow(calculate(input[j]) - expected_output[j], 2);
        }
        return error/(float) input.length;
    }

    @Override
    public double[] getOutput(double[][] input) {
        double [] toReturn = new double [input.length];

        for (int k = 0; k < input.length; k++) {
            toReturn[k] = calculate(input[k]);
        }
        return toReturn;
    }

    public double[] getW() {
        return w;
    }
}
