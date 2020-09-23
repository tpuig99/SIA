public interface Perceptron {
    double activationFunction(double[] input);
    void train(double[][] input, double[] expected_output, double learning_rate, int steps, int epochSize);
    double calculateError(double[][] input, double[] expected_output);
    double calculate(double[] input);
    double[] getOutput(double[][] input);
    double[] getW();
}
