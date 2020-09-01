package selectors;

import subjectModels.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProbabilisticTournamentsSelector extends Selector {

    private final double threshold;
    private final Random randomGenerator;

    public ProbabilisticTournamentsSelector(double threshold) {
        if (threshold < 0.5 || threshold > 1) {
            throw new IllegalArgumentException();
        }
        this.threshold = threshold;
        randomGenerator = new Random();
    }

    @Override
    public List<Subject> select(List<Subject> subjects, int K) {
        List<Subject> selectedList = new ArrayList<>();

        if (K < 0) {
            return selectedList;
        }

        while (selectedList.size() < K) {
            Subject bestSubject = subjects.get(randomGenerator.nextInt(subjects.size()));
            Subject worstSubject = subjects.get(randomGenerator.nextInt(subjects.size()));

            if (bestSubject.getFitness() < worstSubject.getFitness()) {
                Subject aux = bestSubject;
                bestSubject = worstSubject;
                worstSubject = aux;
            }

            if (Math.random() < threshold) {
                selectedList.add(bestSubject);
            }
            else {
                selectedList.add(worstSubject);
            }

        }

        return selectedList;
    }
}