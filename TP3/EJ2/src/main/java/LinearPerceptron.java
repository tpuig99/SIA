import java.util.Random;

public class LinearPerceptron implements Perceptron {
    private final int connections;
    private double threshold;
    private double[] w;

    public LinearPerceptron(int connections) {
        this.connections = connections;
        this.threshold = 0.0;
        this.w = new double[connections];
        for (int i = 0; i < connections; i++) {
            w[i] = Math.random() * 2 - 1;
            // w[i] = 0;
        }
    }

    @Override
    public double activationFunction(double[] input) {
        double output = 0;
        for (int i = 0; i < w.length; i++) {
            output += w[i]*input[i];
        }
        return output - threshold;
    }

    @Override
    public void train (double[][] input, double[] expected_output, double learning_rate, int steps) {
        Random rnd = new Random();
        double error = 1;
        double error_min = expected_output.length*2;
        double[] w_min = w.clone();
        int i = 0;

        while (error > 0 && i < steps) {
            if ( i < 0.1*steps) {
                    for (int j = 0; j < connections; j++) {
                        w[j] = Math.random()  * 2 - 1;
                    }
            }

            int curr = rnd.nextInt(160);

            threshold += learning_rate * (expected_output[curr] - activationFunction(input[curr])) * (-1);

            for (int j = 0; j < this.connections; j++) {
                w[j] += learning_rate * (expected_output[curr] - activationFunction(input[curr])) * input[curr][j];
            }

            error = calculateError(input, expected_output);
            if (error < error_min) {
                error_min = error;
                w_min = w.clone();
            }
            else {
                w = w_min;
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
