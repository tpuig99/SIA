package implementations;

import selectors.Selector;
import subjectModels.Subject;
import subjectModels.roles.Character;

import java.util.ArrayList;
import java.util.List;

public class FillAllImplementation extends Implementation{
    public FillAllImplementation(Selector s1, Selector s2, float b) {
        super(s1, s2, b);
    }

    @Override
    public List<Subject> evolve(List<Subject> oldSubjects, List<Subject> newSubjects, int K) {
        oldSubjects.addAll(newSubjects);
        List<Subject> newGeneration = new ArrayList<>();
        int percentage = (int)Math.ceil(K*b);
        newGeneration.addAll(s1.select(oldSubjects,percentage));
        newGeneration.addAll(s2.select(oldSubjects,K-percentage));
        return newGeneration;
    }

    @Override
    public String toString() {
        return "FillAll Implementation";
    }
}
