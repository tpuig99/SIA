package mutations;


import subjectModels.Subject;

public interface Mutation {
    Subject mutate (Subject subject);
}
