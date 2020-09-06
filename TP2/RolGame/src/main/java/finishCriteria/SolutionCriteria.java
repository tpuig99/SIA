package finishCriteria;

import subjectModels.Subject;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SolutionCriteria implements FinishCriteria{
    double fitnessCriteria;
    int genLimit = 5000;
    int currGen = 0;

    public SolutionCriteria(double fitnessCriteria) {
        this.fitnessCriteria = fitnessCriteria;
    }

    @Override
    public boolean shouldFinish(List<Subject> subjects) {
        return Collections.max(subjects).getFitness()>=fitnessCriteria||currGen++>=genLimit;
    }

    public double getFitnessCriteria() {
        return fitnessCriteria;
    }

    @Override
    public String toString() {
        return "Acceptable Solution Criteria with min fitness greater than "+fitnessCriteria;
    }
}
