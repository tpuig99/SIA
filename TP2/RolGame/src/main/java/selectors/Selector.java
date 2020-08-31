package selectors;

import subjectModels.roles.Character;

import java.util.ArrayList;
import java.util.List;

public abstract class Selector {
    private List<Character> subjects;
    int N;
    double totalF;

    public List<Character> select (List<Character> subjects, int N){
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
        for (Character character: subjects) {
            totalF += character.getFitness();
        }
    }
    int getSubjectsSize(){
        return subjects.size();
    }
}
