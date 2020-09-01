package finishCriteria;

import subjectModels.Subject;

import java.util.List;

public interface FinishCriteria {
    boolean shouldFinish(List<Subject> subjects);
}
