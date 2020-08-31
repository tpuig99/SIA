package selectors;

import subjectModels.roles.Character;

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
    public List<Character> select(List<Character> subjects, int K) {
        List<Character> selectedList = new ArrayList<>();

        if (K < 0) {
            return selectedList;
        }

        while (selectedList.size() < K) {
            Double bestFitness = null;
            Character randomSubject = null;

            for (int i = 0; i < threshold; i++) {
                randomSubject = subjects.get(randomGenerator.nextInt(subjects.size()));
                if (bestFitness == null || randomSubject.getFitness() < bestFitness) {
                    bestFitness = randomSubject.getFitness();
                }
            }
            if (randomSubject != null) {
                selectedList.add(randomSubject);
            }
        }

        return selectedList;
    }
}
