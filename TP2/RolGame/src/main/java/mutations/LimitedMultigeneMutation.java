package mutations;

import subjectModels.Subject;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LimitedMultigeneMutation extends Mutation{

    public LimitedMultigeneMutation(float probability) {
        super(probability);
    }

    @Override
    public Subject mutate(Subject subject) {
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

    @Override
    public String toString() {
        return "Limited Multigene Mutation";
    }
}
