package neuralNetwork.Function;

public class SigmoidFunction implements ActivationFunction{
    final static double  BETA = 2;
    @Override
    public double derivative(double argument) {
        double value = calculate(argument);
        return value * (1-value);
    }

    @Override
    public double calculate(double argument) {
        return (1.0 /( 1.0 + Math.exp(-BETA * argument)));
    }
}
