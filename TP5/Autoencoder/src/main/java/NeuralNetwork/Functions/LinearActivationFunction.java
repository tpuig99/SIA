package NeuralNetwork.Functions;

public class LinearActivationFunction implements ActivationFunction{
    @Override
    public double evaluate(double value) {
        return value;
    }

    @Override
    public double evaluateDerivative(double value) {
        return 1;
    }
}
