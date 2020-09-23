import java.util.*;

public class LinearPerceptron implements Perceptron {
    private final int connections;
    private double[] w;

    public LinearPerceptron(int connections) {
        this.connections = connections;
        this.w = new double[connections];
        for (int i = 0; i < connections; i++) {
            w[i] = Math.random() * 2 - 1;
        }
    }

    @Override
    public double activationFunction(double[] input) {
        double output = 0;
        for (int i = 0; i < w.length; i++) {
            output += w[i]*input[i];
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

        while (error > 0.01 && i < steps) {
            List<Integer> integerList = new ArrayList<>();
            for (int e = 0; e < epochSize; e++) {
                integerList.add(e);
            }
            Collections.shuffle(integerList);
            for (int elem = 0; elem < integerList.size(); elem++) {
                if ( i < 0.1*steps) {
                    for (int j = 0; j < connections; j++) {
                        w[j] = Math.random()  * 2 - 1;
                    }
                }
                int curr = elem;

                for (int j = 0; j < this.connections; j++) {
                    w[j] += learning_rate * (expected_output[curr] - activationFunction(epoch_input[curr])) * epoch_input[curr][j];
                }

                error = calculateError(epoch_input, expected_output);
                if (error < error_min) {
                    error_min = error;
                    w_min = w.clone();
                }
                else {
                    w = w_min;
                }
            }
            i++;
        }
        System.out.println("El valor del error absoluto fue " + error_min);
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
    public double calculate(double[] input) {
        return activationFunction(input);
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
