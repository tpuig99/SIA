package NeuralNetwork.Function;

public class SigmoidFunction implements ActivationFunction{
    @Override
    public double derivative(double argument) {
        double value = calculate(argument);
        return value * (1-value);
    }

    @Override
    public double calculate(double argument) {
        return (1.0 /( 1.0 + Math.exp(-argument)));
    }
}
