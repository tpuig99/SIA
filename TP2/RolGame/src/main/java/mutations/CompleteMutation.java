package mutations;

import subjectModels.Subject;

import java.util.Random;

public class CompleteMutation extends Mutation {
    @Override
    public String toString() {
        return "Complete Mutation";
    }

    CompleteMutation(float probability) {
        super(probability);
    }

    @Override
    public Subject mutate(Subject subject) {
        Subject mutant = subject.cloneSubject();

        for (int i = 0; i < subject.getPropertyCount(); i++) {
            mutant.mutateProperty(i);
        }

        return mutant;
    }
}
