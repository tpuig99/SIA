package NeuralNetwork.Functions;

public class TanhActivationFunction implements ActivationFunction {
    private final double beta;

    public TanhActivationFunction(double beta) {
        this.beta = beta;
    }

    @Override
    public double evaluate(double value) {
        return 0.5 * Math.tanh(beta * value) + 0.5;
    }

    @Override
    public double derivative(double value) {
        double tanh = Math.tanh(value);
        return 0.5 * beta * (1 - tanh * tanh);
    }
}
