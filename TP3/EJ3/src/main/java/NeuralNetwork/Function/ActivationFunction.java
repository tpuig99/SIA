package NeuralNetwork.Function;

public interface ActivationFunction {
    double calculate(double h);
    double derivative(double h);
}
