package finishCriteria;

import subjectModels.Subject;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SolutionCriteria implements FinishCriteria{
    double fitnessCriteria;

    public SolutionCriteria(double fitnessCriteria) {
        this.fitnessCriteria = fitnessCriteria;
    }

    @Override
    public boolean shouldFinish(List<Subject> subjects) {
        return Collections.min(subjects).getFitness()>=fitnessCriteria;
    }

    @Override
    public String toString() {
        return "Acceptable Solution Criteria with min fitness greater than "+fitnessCriteria;
    }
}
