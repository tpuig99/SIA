package finishCriteria;

import java.util.List;
import java.util.concurrent.TimeUnit;
import subjectModels.Subject;

public class TimeCriteria implements FinishCriteria {
    Long startTimeMillis = null;
    Long durationMillis;

    public TimeCriteria(long seconds) {
        durationMillis = TimeUnit.SECONDS.toMillis(seconds);
    }

    @Override
    public boolean shouldFinish(List<Subject> population) {
        if(startTimeMillis == null) {
            startTimeMillis = System.currentTimeMillis();
        }

        return (System.currentTimeMillis() - startTimeMillis) >= durationMillis;
    }
}