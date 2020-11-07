package neuralNetwork.Connections;

import neuralNetwork.Neurons.Neuron;

public class FixerInnerConnection extends Connection {
    double fixValue;
    public FixerInnerConnection(Neuron output, double fixValue) {
        super(output);
        this.fixValue=fixValue;

    }

    @Override
    double getValue() {
        return fixValue;
    }

    @Override
    public double getForHValue() {
        return w*getValue();
    }

    ;
}
