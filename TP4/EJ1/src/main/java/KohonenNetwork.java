import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KohonenNetwork {
    private static final double MIN_WEIGHT = -1;
    private static final double MAX_WEIGHT = 1;
    private final Neuron[][] neuronMap;

    public KohonenNetwork(int inputDimension, int[] outputDimension) {
        neuronMap = new Neuron[outputDimension[0]][outputDimension[1]];

        for (int i = 0; i < neuronMap.length; i++) {
            for (int j = 0; j < neuronMap[0].length; j++) {
                neuronMap[i][j] = new Neuron(inputDimension);
            }
        }
    }

    public void train(double[][] inputSet, double initialLearningRate, int epochs, double initialNeighbourhoodSize) {
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < inputSet.length; i++) {
            order.add(i);
        }
        double sigma;
        int t = 1;

        for (int epoch = 0; epoch < epochs; epoch++) {
            Collections.shuffle(order);

            for (Integer n : order) {
                sigma = Math.exp((-1) * t / initialNeighbourhoodSize) + 1;
                List<Integer[]> neighbours = getNeighbours(getWinningNeuronCoordinates(inputSet[n]), sigma);

                for (Integer[] neuron : neighbours) {
                    int i = neuron[0];
                    int j = neuron[1];
                    for (int k = 0; k < neuronMap[i][j].getWeights().length; k++) {
                        double delta = (initialLearningRate / t) * (inputSet[n][k] - neuronMap[i][j].getWeight(k));
                        neuronMap[i][j].setWeight(k, neuronMap[i][j].getWeight(k) + delta);
                    }
                }
                t++;
            }
        }
    }

    public Integer[] getWinningNeuronCoordinates(double[] input) {
        Integer[] winningNeuron = new Integer[2];
        double minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < neuronMap.length; i++) {
            for (int j = 0; j < neuronMap[i].length; j++){
                double currentDistance = eulerDistance(input, neuronMap[i][j].getWeights());
                if (currentDistance < minDistance) {
                    winningNeuron[0] = i;
                    winningNeuron[1] = j;
                    minDistance = currentDistance;
                }
            }
        }
        return winningNeuron;
    }

    public int[][] getRegistersMatrix(double[][] input) {
        int[][] registersMatrix = new int[neuronMap.length][neuronMap[0].length];
        for (double[] register : input) {
            Integer[] winningNeuron = getWinningNeuronCoordinates(register);
            registersMatrix[winningNeuron[0]][winningNeuron[1]]++;
        }
        return registersMatrix;
    }

    private double eulerDistance(double[] w1, double[] w2) {
        double sum = 0;
        for (int i = 0; i < w1.length; i++) {
            sum += Math.pow(w1[i] - w2[i], 2); // always >= 0
        }
        return sum;
    }

    private List<Integer[]> getNeighbours(Integer[] winningNeuron, double sigma) {
        List<Integer[]> neighborhood = new ArrayList<>();
        for (int i = 0; i < neuronMap.length; i++) {
            for (int j = 0; j < neuronMap[0].length; j++) {
                Integer[] neuron = new Integer[]{i, j};
                if (schemeDistance(neuron, winningNeuron) <= sigma)
                    neighborhood.add(neuron);
            }
        }

        return neighborhood;
    }

    private double schemeDistance(Integer[] n1, Integer[] n2) {
        return eulerDistance(new double[]{n1[0], n1[1]}, new double[]{n2[0], n2[1]});
    }

    public double[][] getDistanceMatrix() {
        double[][] toReturn = new double[neuronMap.length][neuronMap[0].length];

        for (int i = 0; i < toReturn.length; i++) {
            for (int j = 0; j < toReturn[i].length; j++) {
                Integer[] currentNeuron = new Integer[]{i, j};
                List<Integer[]> neighbours = getNeighbours(currentNeuron, 1);
                double sum = 0.0;
                for (Integer[] neighbour : neighbours) {
                    sum += eulerDistance(neuronMap[currentNeuron[0]][currentNeuron[1]].getWeights(),
                            neuronMap[neighbour[0]][neighbour[1]].getWeights());
                }

                if (neighbours.size() > 1) {
                    sum /= neighbours.size() - 1;
                }

                toReturn[i][j] = sum;
            }
        }

        return toReturn;
    }

    private static class Neuron {
        private final double [] weights;

        public Neuron(int dim) {
            weights = new double[dim];
            for (int i  = 0; i < dim; i++) {
                weights[i] = MIN_WEIGHT + Math.random() / (MAX_WEIGHT - MIN_WEIGHT);
            }
        }

        public double[] getWeights() {
            return weights;
        }

        public double getWeight(int index) {
            return weights[index];
        }

        public void setWeight(int index, double value) {
            this.weights[index] = value;
        }
    }
}
