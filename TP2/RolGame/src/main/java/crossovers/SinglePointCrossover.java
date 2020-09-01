package crossovers;

import subjectModels.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SinglePointCrossover implements Crossover {
    @Override
    public List<Subject> cross(Subject subject1, Subject subject2) {
        if (subject1 == null || subject2 == null) {
            throw new IllegalArgumentException();
        }

        List<Subject> crossoverList = new ArrayList<>();
        Subject s1 = subject1.cloneSubject();
        Subject s2 = subject2.cloneSubject();

        Random random = new Random();
        int point = random.nextInt(s1.getPropertyCount()); // generates random integer from 0 to 5

        for (int i = point; i < s1.getPropertyCount() ; i++) {
                s1.setProperty(s2.getProperty(i), i);
                s2.setProperty(s1.getProperty(i), i);
        }

        crossoverList.add(s1);
        crossoverList.add(s2);

        return crossoverList;
    }
}
