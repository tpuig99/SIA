package finishCriteria;

import subjectModels.roles.Character;

import java.util.Collections;
import java.util.List;

public class ContentCriteria implements FinishCriteria {
    private final int generationsLimit;
    private int generationsAcum = 0;
    private final double epsilon;
    private Character bestSubject = null;

    public ContentCriteria(int generationsLimit, double epsilon) {
        this.generationsLimit = generationsLimit;
        this.epsilon = epsilon;
    }

    @Override
    public boolean shouldFinish(List<Character> population) {
        Character bestCurrent = Collections.min(population);

        if (bestSubject == null || bestCurrent.getFitness() -
                bestSubject.getFitness() > epsilon ) { // if new is significantly better
            generationsAcum = 0;
            bestSubject = bestCurrent;
        }
        else if (bestCurrent.getFitness() -
                bestSubject.getFitness() < epsilon) {  // if new did not improve
            generationsAcum++;
        }

        return generationsAcum == generationsLimit;
    }
}