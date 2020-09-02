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
    public List<Subject> evolve(List<Subject> oldSubjects, List<Subject> newSubjects, int N) {
        List<Subject> newGenerationList = new ArrayList<>();

        int K = newSubjects.size();

        if (N < 0) {
            throw new IllegalArgumentException();
        }

        int firstSelection = (int)Math.floor(N*b);
        int secondSelection = N - firstSelection;

        if (K > N) {
            newGenerationList.addAll(s1.select(newSubjects, firstSelection));
            newGenerationList.addAll(s2.select(newSubjects, secondSelection));
        }
        else {
            int oldSize = oldSubjects.size();
            int i = 0;
            List<Subject> fillParentList = new ArrayList<>(newSubjects);
            while (fillParentList.size() < N) {
                fillParentList.add(oldSubjects.get((i++)%oldSize));
            }
            newGenerationList.addAll(s1.select(fillParentList, firstSelection));
            newGenerationList.addAll(s2.select(fillParentList, secondSelection));
        }

        return newGenerationList;
    }

    @Override
    public String toString() {
        return "FillParent Implementation";
    }
}
