package crossovers;


import subjectModels.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnularCrossover implements Crossover{
    Random random = new Random();
    @Override
    public List<Subject> cross(Subject subject1, Subject subject2) {
        int point = random.nextInt(subject1.getPropertyCount()-1);
        int length = random.nextInt(subject1.getPropertyCount()/2);
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

    @Override
    public String toString() {
        return "Anular Crossover";
    }
}
