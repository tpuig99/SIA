package selectors;

import subjectModels.roles.Character;

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
    public List<Character> select(List<Character> subjects, int K) {
        List<Character> selectedList = new ArrayList<>();

        if (K < 0) {
            return selectedList;
        }

        while (selectedList.size() < K) {
            Character bestSubject = subjects.get(randomGenerator.nextInt(subjects.size()));
            Character worstSubject = subjects.get(randomGenerator.nextInt(subjects.size()));

            if (bestSubject.getFitness() < worstSubject.getFitness()) {
                Character aux = bestSubject;
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