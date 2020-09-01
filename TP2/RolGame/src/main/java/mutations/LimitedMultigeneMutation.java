package mutations;

import subjectModels.Subject;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LimitedMultigeneMutation extends Mutation{

    LimitedMultigeneMutation(float probability) {
        super(probability);
    }

    @Override
    Subject mutate(Subject subject) {
        Subject mutatedSubj = subject.cloneSubject();
        int gensToMutate = random.nextInt(mutatedSubj.getPropertyCount());
        Set<Integer> mutateGens = new HashSet<>();
        int currGen;
        while (mutateGens.size()<gensToMutate){
            currGen = random.nextInt(mutatedSubj.getPropertyCount()-1);
            if(!mutateGens.contains(currGen)){
                if(probability>=random.nextFloat()) {
                    mutatedSubj.mutateProperty(currGen);
                }
                mutateGens.add(currGen);
            }
        }
        return mutatedSubj;
    }
}
