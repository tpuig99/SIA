package NeuralNetwork.Functions;

public interface ActivationFunction {
    double evaluateDerivative(double argument);

    double evaluate(double argument);
}
