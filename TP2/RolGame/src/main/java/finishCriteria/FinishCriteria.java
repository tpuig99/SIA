package finishCriteria;

import subjectModels.roles.Character;

import java.util.List;

public interface FinishCriteria {
    boolean shouldFinish(List<Character> subjects);
}
