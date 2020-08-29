package crossovers;

import subjectModels.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniformCrossover implements Crossover{
    @Override
    public List<Subject> cross(Subject subject1, Subject subject2) {
        Subject c1 = subject1.cloneSubject();
        Subject c2 = subject2.cloneSubject();
        Random rm = new Random();
        for (int i = 0; i < subject1.getPropertyCount(); i++) {
            if(rm.nextBoolean()){
                c1.setProperty(subject2.getProperty(i),i);
                c2.setProperty(subject1.getProperty(i),i);
            }
        }
        List<Subject> ans = new ArrayList<>();
        ans.add(c1);
        ans.add(c2);
        return ans;
    }
}
