package NeuralNetwork;

import NeuralNetwork.Connections.Connection;
import NeuralNetwork.Functions.ActivationFunction;
import NeuralNetwork.Neurons.Neuron;
import NeuralNetwork.Neurons.OutputNeuron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MLP {
    private final List<Neuron>[] layers;
    private final ActivationFunction g;
    private final double learningRate;

    public MLP(int inputLayerSize, int[] hiddenLayersSizes, int outputLayerSize, double learningRate, ActivationFunction g) {

        this.learningRate = learningRate;

        //Input layer + Hidden layers + exit layer
        int layerCount = hiddenLayersSizes.length + 2;
        layers = new ArrayList[layerCount];

        this.g = g;

        //Input layer
        layers[0] = createLayer(inputLayerSize, null, false);

        //Hidden layers
        for(int i = 0; i < hiddenLayersSizes.length; i++)
            layers[i + 1] = createLayer(hiddenLayersSizes[i], layers[i], false);

        //Exit layer
        layers[layers.length - 1] = createLayer(outputLayerSize, layers[layers.length - 2], true);
    }

    public List<Double> calculate(double[] inputValues) {
        return calculate(inputValues, 0, layers.length - 1);
    }

    public List<Double> calculate(double[] inputValues, int from, int to) {
        if(inputValues.length != layers[from].size())
            return null;

        for(int i = 0; i < layers[from].size(); i++)
            layers[from].get(i).setValue(inputValues[i]);

        for(int i = from + 1; i < to + 1; i++)
            for(Neuron n : layers[i])
                n.process();

        List<Double> resultSet = new ArrayList<>();
        for(Neuron n : layers[to])
            resultSet.add(n.getValue());

        return resultSet;
    }

    public List<Double> calculateMiddleLayer(double [] inputValues) {
        return calculate(inputValues, 0, layers.length/2);
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

    public void train(double[][] inputData, double[][] resultData, int epochs, boolean batchMode, double momentum) {
        if(inputData.length != resultData.length || inputData[0].length != layers[0].size())
            return;

        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < inputData.length; i++)
            order.add(i);

        for(int currEpoch = 1; currEpoch <= epochs; currEpoch++) {

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
                            c.updateDeltaWeight();
                            if (!batchMode)
                                c.updateWeight(momentum);
                        }
                    }
                }
            }

            for(int j = layers.length - 1; j >= 1; j--) {
                for(int k = 0; k < layers[j].size(); k++) {
                    Neuron n = layers[j].get(k);
                    for(Connection c : n.input)
                        c.updateWeight(momentum);
                }
            }
        }
    }

    public void train(double[][] inputData, double[][] resultData, int epochs, boolean batchMode) {
        train(inputData, resultData, epochs, batchMode, 0);
    }

    private List<Neuron> createLayer(int size, List<Neuron> previousLayer, boolean exitLayer) {
        List<Neuron> l = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            Neuron n = !exitLayer ? new Neuron(i, g, learningRate, 1) : new OutputNeuron(i, g, learningRate, 1);

            if(previousLayer != null)
                for(Neuron prevNeuron : previousLayer)
                    prevNeuron.connectTo(n);

            l.add(n);
        }

        return l;
    }
}
