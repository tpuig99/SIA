package crossovers;


import subjectModels.Subject;

import java.util.ArrayList;
import java.util.List;

public class AnularCrossover implements Crossover{

    @Override
    public List<Subject> cross(Subject subject1, Subject subject2) {
        int point = (int) (Math.random()*(subject1.getPropertyCount()-1));
        int length = (int) (Math.random()*(subject1.getPropertyCount()/2));
        List<Subject> ans = new ArrayList<>();
        Subject c1 = subject1.cloneSubject();
        Subject c2 = subject2.cloneSubject();
        for (int i = 0; i < length; i++) {
            int prop = (point + i)%subject1.getPropertyCount();
            c1.setProperty(subject2.getProperty(prop),prop);
            c2.setProperty(subject1.getProperty(prop),prop);
        }
        ans.add(c1);
        ans.add(c2);
        return ans;
    }
}
