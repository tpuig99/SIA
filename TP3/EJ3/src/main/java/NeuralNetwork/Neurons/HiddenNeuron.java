package NeuralNetwork.Neurons;

import NeuralNetwork.Connections.Connection;
import NeuralNetwork.Function.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class HiddenNeuron extends Neuron{
    private List<Connection> output;

    public HiddenNeuron(ActivationFunction function, double learnRate) {
        super(function,learnRate);
        output = new ArrayList<>();

    }
    public double calculateDelta(Double r) {
        double aux = 0;
        for (Connection n:output) {
            aux+=n.getW()*n.getOutput().getDelta();
        }
        return super.g.derivative(h)*aux;
    }
    public void connect(Neuron n){
        Connection connection = new Connection(this,n);
        this.output.add(connection);
        n.input.add(connection);
    }
}
