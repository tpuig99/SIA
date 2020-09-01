package crossovers;

import subjectModels.Subject;

import java.util.List;

public interface Crossover {
    List<Subject> cross(Subject subject1, Subject subject2);
}
