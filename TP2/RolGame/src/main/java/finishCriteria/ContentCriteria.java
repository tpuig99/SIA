package finishCriteria;

import subjectModels.Subject;

import java.util.Collections;
import java.util.List;

public class ContentCriteria implements FinishCriteria {
    private final int generationsLimit;
    private int generationsAcum = 0;
    private final double epsilon;
    private Subject bestSubject = null;

    public ContentCriteria(int generationsLimit, double epsilon) {
        this.generationsLimit = generationsLimit;
        this.epsilon = epsilon;
    }

    @Override
    public boolean shouldFinish(List<Subject> population) {
        Subject bestCurrent = Collections.min(population);

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

    @Override
    public String toString() {
        return "Content Criteria";
    }
}