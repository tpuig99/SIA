package finishCriteria;

import subjectModels.Subject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class GenerationsCriteria implements FinishCriteria {
    int generations;
    int currentGen = 0;

    public GenerationsCriteria(int generations) {
        if(generations<0)
            throw new ExceptionInInitializerError();
        this.generations = generations;
    }

    @Override
    public boolean shouldFinish(List<Subject> subjects) {
        currentGen++;
        return generations==currentGen;
    }

    @Override
    public String toString() {
        return "Generations Criteria with limit: "+generations;
    }
}
