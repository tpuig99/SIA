package selectors;

import subjectModels.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeterministicTournamentsSelector extends Selector {

    private final int threshold;
    private final Random randomGenerator;

    public DeterministicTournamentsSelector(int M) {
        if (M < 0) {
            throw new IllegalArgumentException();
        }
        this.threshold = M;
        randomGenerator = new Random();
    }

    @Override
    public List<Subject> select(List<Subject> subjects, int K) {
        List<Subject> selectedList = new ArrayList<>();

        if (K < 0) {
            return selectedList;
        }

        while (selectedList.size() < K) {
            Subject bestSubject = null;
            Subject randomSubject = null;

            for (int i = 0; i < threshold; i++) {
                randomSubject = subjects.get(randomGenerator.nextInt(subjects.size()));
                if (bestSubject == null || randomSubject.getFitness() > bestSubject.getFitness()) {
                    bestSubject = randomSubject;
                }
            }
            if (bestSubject != null) {
                selectedList.add(bestSubject);
            }
        }

        return selectedList;
    }

    @Override
    public String toString() {
        return "Deterministic Tournaments Selector";
    }
}
