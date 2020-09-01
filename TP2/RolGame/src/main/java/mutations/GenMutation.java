package mutations;

import subjectModels.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenMutation extends Mutation {
    GenMutation(float probability) {
        super(probability);
    }

    @Override
    public Subject mutate(Subject subject) {
        Subject mutant = subject.cloneSubject();
        Random random = new Random();
        int selectedGen = random.nextInt(subject.getPropertyCount()); // generates random integer from 0 to 5
        mutant.mutateProperty(selectedGen);
        return mutant;
    }
}
