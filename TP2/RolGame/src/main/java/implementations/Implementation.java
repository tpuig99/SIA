package implementations;

import java.util.List;

import selectors.Selector;
import subjectModels.Subject;
import subjectModels.roles.Character;

public abstract class Implementation {
    Selector s1;
    Selector s2;
    float b;

    public Implementation(Selector s1, Selector s2, float b) {
        this.s1 = s1;
        this.s2 = s2;
        this.b = b;
    }

    public abstract List<Subject> evolve (List<Subject> oldSubjects, List<Subject> newSubjects, int K);
}
