package NeuralNetwork.Functions;

public class TanhActivationFunction implements ActivationFunction {
    private double BETA = 2;

    @Override
    public double evaluate(double value) {
        return 0.5 * Math.tanh(BETA * value) + 0.5;
    }

    @Override
    public double evaluateDerivative(double value) {
        double tanh = Math.tanh(value);
        return 0.5 * BETA * (1 - tanh * tanh);
    }
}
