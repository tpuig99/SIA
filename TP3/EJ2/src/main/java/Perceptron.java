public interface Perceptron {
    double activationFunction(double[] input);
    void train(double[][] input, double[] expected_output, double learning_rate, int steps);
    double calculateError(double[][] input, double[] expected_output);
    double[] getOutput(double[][] input);
    double[] getW();
}
