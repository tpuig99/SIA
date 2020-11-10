package NeuralNetwork.Functions;

public class SigmoidActivationFunction implements ActivationFunction {
    @Override
    public double evaluateDerivative(double argument) {
        double value = evaluate(argument);
        return value * (1-value);
    }

    @Override
    public double evaluate(double argument) {
        double denominator = 1 + Math.exp(-argument);

        return (1d / denominator);
    }
}
