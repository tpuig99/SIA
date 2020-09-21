package NeuralNetwork.Neurons;

import NeuralNetwork.Connections.Connection;
import NeuralNetwork.Connections.InnerConnection;
import NeuralNetwork.Function.ActivationFunction;

import java.util.ArrayList;
import java.util.List;

public class HiddenNeuron extends Neuron{
    private List<Connection> output;

    public HiddenNeuron(ActivationFunction function, double learnRate) {
        super(function,learnRate);
        output = new ArrayList<>();

    }
    public void calculateDelta(Double r) {
        double aux = 0;
        for (Connection n:output) {
            aux+=n.getW()*n.getOutput().getDelta();
        }
        delta= super.g.derivative(h)*aux;
    }
    public void connect(Neuron n){
        Connection innerConnection = new InnerConnection(this,n);
        this.output.add(innerConnection);
        n.input.add(innerConnection);
    }
}
