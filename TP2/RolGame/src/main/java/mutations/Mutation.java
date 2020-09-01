package mutations;


import subjectModels.Subject;

import java.util.Random;

public abstract class Mutation {
    float probability =0.4f;
    static final Random random= new Random();

    Mutation(float probability) {
        this.probability = probability;
    }

    abstract Subject mutate (Subject subject);
}
