package NeuralNetwork.Functions;

public interface ActivationFunction {
    double derivative(double argument);

    double evaluate(double argument);
}
