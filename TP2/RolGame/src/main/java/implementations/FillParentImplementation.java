package implementations;

import java.util.ArrayList;
import java.util.List;

import selectors.Selector;
import subjectModels.Subject;
import subjectModels.roles.Character;


public class FillParentImplementation extends Implementation {
    public FillParentImplementation(Selector s1, Selector s2, float b) {
        super(s1, s2, b);
    }

    @Override
    public List<Subject> evolve(List<Subject> oldSubjects, List<Subject> newSubjects, int K) {
        List<Subject> newGenerationList = new ArrayList<>();

        int n = oldSubjects.size();

        if (K < 0) {
            throw new IllegalArgumentException();
        }

        int i = 0;

        if (K > n) {
            while (newGenerationList.size() < n) {
                newGenerationList.add(newSubjects.get(i%n));
                i++;
            }
        }
        else {
            while (newGenerationList.size() < n) {
                if (i < K) {
                    newGenerationList.add(newSubjects.get(i));
                }
                else {
                    newGenerationList.add(oldSubjects.get(i));
                }
                i++;
            }
        }

        return newGenerationList;
    }
}
