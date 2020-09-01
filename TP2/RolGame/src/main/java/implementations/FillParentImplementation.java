package implementations;

import java.util.ArrayList;
import java.util.List;
import subjectModels.roles.Character;


public class FillParentImplementation implements Implementation {
    @Override
    public List<Character> evolve(List<Character> oldSubjects, List<Character> newSubjects, int K) {
        List<Character> newGenerationList = new ArrayList<>();

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
