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
        w[0] = 0;
    }


    private double g_prime(double input) {
        double tanh = Math.tanh(beta*input);
        return beta*(1 - tanh * tanh);
    }

    private double g(double input) {
        return Math.tanh(beta*input);
    }

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

        while (error > 0 && i < steps) {
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
                    w[0] = 0;
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
        }
    }

    @Override
    public double calculateError (double[][] input, double[] expected_output) {
        double error = 0;
        for (int i = 0; i < input.length; i++) {
            error += Math.abs(expected_output[i] - activationFunction(input[i]));
        }
        return error;
    }

    @Override
    public double[] getOutput(double[][] input) {
        double [] toReturn = new double [input.length];

        for (int k = 0; k < input.length; k++) {
            toReturn[k] = activationFunction(input[k]);
        }
        return toReturn;
    }

    public double[] getW() {
        return w;
    }
}
