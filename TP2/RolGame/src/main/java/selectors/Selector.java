package selectors;

import subjectModels.Subject;

import java.util.ArrayList;
import java.util.List;

public abstract class Selector {
    private List<Subject> subjects;
    int N;
    double totalF;

    public List<Subject> select (List<Subject> subjects, int N){
        this.subjects = subjects;
        setTotalF();
        this.N = N;
        return new ArrayList<>();
    };
    double getFitnessR(int index){
        return subjects.get(index).getFitness()/totalF;
    }

    private void setTotalF(){
        totalF = 0;
        for (Subject character: subjects) {
            totalF += character.getFitness();
        }
    }
    int getSubjectsSize(){
        return subjects.size();
    }
}
