package crossovers;


import subjectModels.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DoublePointCrossover implements Crossover {
    @Override
    public List<Subject> cross(Subject subject1, Subject subject2) {
        if (subject1 == null || subject2 == null) {
            throw new IllegalArgumentException();
        }

        List<Subject> crossoverList = new ArrayList<>();
        Subject s1 = subject1.cloneSubject();
        Subject s2 = subject2.cloneSubject();

        Random random = new Random();
        int secondPoint = random.nextInt(s1.getPropertyCount()-1)+1; // (generates random integer from 0 to 4) + 1
        int firstPoint =  random.nextInt(secondPoint);

        for (int i = firstPoint; i <= secondPoint ; i++) {
                s1.setProperty(s2.getProperty(i), i);
                s2.setProperty(s1.getProperty(i), i);
        }

        crossoverList.add(s1);
        crossoverList.add(s2);

        return crossoverList;
    }

    @Override
    public String toString() {
        return "Double Point Crossover";
    }
}
