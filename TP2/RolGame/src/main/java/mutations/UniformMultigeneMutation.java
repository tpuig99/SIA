package mutations;

import subjectModels.Subject;

import java.util.HashSet;
import java.util.Set;

public class UniformMultigeneMutation extends Mutation{
    public UniformMultigeneMutation(float probability) {
        super(probability);
    }

    @Override
    public Subject mutate(Subject subject) {
        Subject mutatedSubj = subject.cloneSubject();
        for (int i = 0; i < mutatedSubj.getPropertyCount(); i++) {
            if(probability>=random.nextFloat()) {
                mutatedSubj.mutateProperty(i);
            }
        }
        return mutatedSubj;
    }
}
