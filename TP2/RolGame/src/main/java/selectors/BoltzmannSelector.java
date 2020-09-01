package selectors;

import subjectModels.Subject;

import java.util.*;

public class BoltzmannSelector extends Selector {

    private final double T0;
    private final double Tc;
    private int generationTime = 0;

    public BoltzmannSelector(double T0, double Tc) {
        this.T0 = T0;
        this.Tc = Tc;
    }

    @Override
    public List<Subject> select(List<Subject> subjects, int K) {
        List<Subject> selectionList = new ArrayList<>();

        if (K < 0) {
            return selectionList;
        }

        double currentT = getT();
        double sum = getSum(subjects, currentT);
        double[] roulette = new double[subjects.size()];
        double[] expectedValues = new double[subjects.size()];

        for (int i = 0; i < subjects.size(); i++) {
            roulette[i] = Math.random();
            expectedValues[i] = Math.exp(subjects.get(i).getFitness()/currentT) / sum;
        }

        Arrays.sort(roulette);

        double currQ = 0;
        for(int i = 0; selectionList.size() < K && i < subjects.size(); i++) {
            currQ += expectedValues[i];
            while(selectionList.size() < K && currQ > roulette[selectionList.size()]) {
                selectionList.add(subjects.get(i));
            }
        }

        return selectionList;
    }

    private double getSum(List<Subject> subjects, double currentTemperature) {
        double sum = 0;
        for (Subject subject : subjects) {
            sum += Math.exp(subject.getFitness()/currentTemperature);
        }
        return sum;
    }

    private double getT() {
        double T = Tc + (T0 - Tc) * Math.exp(-0.1 * generationTime);
        generationTime++;
        return T;
    }
}
