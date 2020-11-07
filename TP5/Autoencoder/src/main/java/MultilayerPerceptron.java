import neuralNetwork.Connections.Connection;
import neuralNetwork.Function.ActivationFunction;
import neuralNetwork.Function.TanhFunction;
import neuralNetwork.Neurons.HiddenNeuron;
import neuralNetwork.Neurons.Neuron;
import neuralNetwork.Neurons.OutputNeuron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultilayerPerceptron {
    private List<Neuron>[] layers;
    private ActivationFunction g;
    private double learningRate;

    public MultilayerPerceptron(int inputLayerSize, int[] hiddenLayersSizes, int outputLayerSize, double learningRate) {

        this.learningRate = learningRate;
        layers = new ArrayList[ hiddenLayersSizes.length + 2];
        g = new TanhFunction();

        layers[0] = newLayer(inputLayerSize, null, false);
        for(int i = 0; i < hiddenLayersSizes.length; i++)
            layers[i + 1] = newLayer(hiddenLayersSizes[i], layers[i], false);
        layers[layers.length - 1] = newLayer(outputLayerSize, layers[layers.length - 2], true);
    }

    private List<Neuron> newLayer(int size, List<Neuron> previousLayer, boolean last) {
        List<Neuron> l = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            Neuron n = !last ? new HiddenNeuron(g, learningRate) : new OutputNeuron(g, learningRate);
            if(previousLayer != null)
                for(Neuron prevNeuron : previousLayer)
                    prevNeuron.connect(n);
            l.add(n);
        }

        return l;
    }
    public List<Double> calculate(double[] inputValues) {
        if(inputValues.length != layers[0].size())
            return null;

        for(int i = 0; i < layers[0].size(); i++)
            layers[0].get(i).setV(inputValues[i]);

        for(int i = 1; i < layers.length; i++)
            for(Neuron n : layers[i])
                n.process();

        List<Double> resultSet = new ArrayList<>();
        for(Neuron n : layers[layers.length - 1])
            resultSet.add(n.getV());

        return resultSet;
    }

    public double getError(double[][] inputData, double[][] resultData) {
        double e = 0;

        for (int i = 0; i < inputData.length; i++) {
            List<Double> results = calculate(inputData[i]);
            for(int j = 0; j < layers[layers.length - 1].size(); j++)
                e += Math.pow(resultData[i][j] - results.get(j), 2);
        }

        return e / inputData.length;
    }

    public void train(double[][] inputData, double[][] resultData, int epochs) {
        if(inputData.length != resultData.length || inputData[0].length != layers[0].size())
            return;

        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < inputData.length; i++)
            order.add(i);

        for(int curr = 1; curr <= epochs; curr++) {
            Collections.shuffle(order);

            for (Integer i : order) {
                double[] example = inputData[i];
                double[] exampleResult = resultData[i];

                calculate(example);

                //Backpropagate each layer, from last to second
                for(int j = layers.length - 1; j >= 1; j--) {
                    for(int k = 0; k < layers[j].size(); k++) {
                        Neuron n = layers[j].get(k);

                        if (j == layers.length - 1)
                            n.calculateDelta(exampleResult[k]);
                        else
                            n.calculateDelta(0.0);

                        for(Connection c : n.input) {
                            c.updateW();
                        }
                    }
                }
            }
        }
    }
}
