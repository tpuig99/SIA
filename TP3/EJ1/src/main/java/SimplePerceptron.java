import java.util.Random;

public class SimplePerceptron {
    private int connections;
    private double threshold;
    private double[] w;
    private final int ACTIVATE = 1, DEACTIVATE = -1;

    public SimplePerceptron(int connections) {
        this.connections = connections;
        this.threshold = 0.0;
        this.w = new double[connections];
        for (int i = 0; i < connections; i++) {
            w[i] = Math.random() * 2 - 1;
        }
    }

    public int sign (double [] excitations) {
        double stimuli = 0;
        for (int i = 0; i < w.length ; i++) {
            stimuli += w[i]*excitations[i];
        }
        return stimuli > w[0] ? ACTIVATE : DEACTIVATE;
    }

    public double[] train (double[][] input, double[] expected_output, double learning_rate, int steps ) {
        Random rnd = new Random();
        double error = 1;
        double error_min = 2*expected_output.length;
        double[] w_min = new double[w.length];
        int i = 0;
        while (error > 0 && i < steps) {

            if ( i < steps * 0.1) {
                for (int j = 0; j < connections; j++) {
                    w[j] = Math.random() * 2 - 1;
                }
            }
            int curr = rnd.nextInt(expected_output.length);
            double output = sign(input[curr]);

            for (int j = 0; j < this.connections; j++) {
                w[j] += learning_rate * (expected_output[curr] - output) * input[curr][j];
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
        double [] toReturn = new double [input.length];
        for (int k = 0; k < input.length; k++) {
            toReturn[k] = sign(input[k]);
        }

        return toReturn;
    }

    private double calculateError (double[][] input, double[] expected_output) {
        double error = 0;
        for (int i = 0; i < input.length; i++) {
            if (sign(input[i]) != (int) expected_output[i]) {
                error++;
            }
        }
        return error/input.length;
    }
}
