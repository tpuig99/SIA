package implementations;

import java.util.List;
import subjectModels.roles.Character;

public interface Implementation {
    List<Character> evolve (List<Character> oldSubjects, List<Character> newSubjects, int K);
}
