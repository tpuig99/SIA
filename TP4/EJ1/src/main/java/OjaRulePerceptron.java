import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OjaRulePerceptron {
    private static final double MIN_WEIGHT = -0.5;
    private static final double MAX_WEIGHT = 0.5;

    double[] w;

    public OjaRulePerceptron(int dim) {
        w = new double[dim];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < dim; i++)
            w[i] = MIN_WEIGHT + random.nextDouble() * (MAX_WEIGHT - MIN_WEIGHT);
    }

    public double calculate(double[] input) {
        double toReturn = 0;
        for (int i = 0; i < w.length; i++)
            toReturn += w[i] * input[i];
        return toReturn;
    }

    public void train(double[][] inputSet, double learningRate, int epochs) {
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < inputSet.length; i++) {
            order.add(i);
        }

        int t = 1;

        for (int epoch = 0; epoch < epochs; epoch++) {
            Collections.shuffle(order);

            for (Integer n : order) {
                double y = calculate(inputSet[n]);
                for (int i = 0; i < w.length; i++) {
                    w[i] += (learningRate / t) * (inputSet[n][i] * y - Math.pow(y, 2) * w[i]);
                }
                t++;
            }
        }
    }
}
