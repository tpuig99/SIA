package mutations;

import subjectModels.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenMutation extends Mutation {
    public GenMutation(float probability) {
        super(probability);
    }

    @Override
    public Subject mutate(Subject subject) {
        Subject mutant = subject.cloneSubject();
        Random random = new Random();
        int selectedGen = random.nextInt(subject.getPropertyCount()); // generates random integer from 0 to 5
        if(probability>=random.nextFloat()) {
            mutant.mutateProperty(selectedGen);
        }
        return mutant;
    }
    @Override
    public String toString() {
        return "Gen Mutation";
    }

}
