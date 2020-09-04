package mutations;

import subjectModels.Subject;

import java.util.Random;

public class CompleteMutation extends Mutation {
    @Override
    public String toString() {
        return "Complete Mutation";
    }

    public CompleteMutation(float probability) {
        super(probability);
    }

    @Override
    public Subject mutate(Subject subject) {
        Subject mutant = subject.cloneSubject();
        if(probability>=random.nextFloat()) {
            for (int i = 0; i < subject.getPropertyCount(); i++) {
                mutant.mutateProperty(i);
            }
        }
        return mutant;
    }
}
