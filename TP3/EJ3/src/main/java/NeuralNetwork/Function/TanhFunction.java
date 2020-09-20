package NeuralNetwork.Function;

public class TanhFunction implements ActivationFunction{
    private double b = 2;

    @Override
    public double calculate(double value) {
        return 0.5 * Math.tanh(b * value) + 0.5;
    }

    @Override
    public double derivative(double value) {
        double th = Math.tanh(value);
        return 0.5 * b * (1 - th * th);
    }
}
