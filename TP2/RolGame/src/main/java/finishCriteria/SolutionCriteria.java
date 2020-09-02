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
        subjects.sort(new Comparator<Subject>() {
            @Override
            public int compare(Subject o1, Subject o2) {
                return Double.compare(o1.getFitness(),o2.getFitness());
            }
        });
        return subjects.get(0).getFitness()>=fitnessCriteria;
    }
}
