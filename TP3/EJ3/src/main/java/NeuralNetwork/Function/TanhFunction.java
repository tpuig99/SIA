package NeuralNetwork.Function;

public class TanhFunction implements ActivationFunction{
    private double b = 3;

    @Override
    public double calculate(double value) {
        return Math.tanh(b * value) ;
    }

    @Override
    public double derivative(double value) {
        double th = Math.tanh(value);
        return  b * (1 - th * th);
    }
}
