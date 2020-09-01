package mutations;

import subjectModels.Subject;

import java.util.Random;

public class CompleteMutation implements Mutation {

    @Override
    public Subject mutate(Subject subject) {
        Subject mutant = subject.cloneSubject();

        for (int i = 0; i < subject.getPropertyCount(); i++) {
            mutant.mutateProperty(i);
        }

        return mutant;
    }
}
