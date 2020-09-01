package selectors;

import subjectModels.roles.Character;

import java.util.ArrayList;
import java.util.List;

public class UniversalSelector extends Selector{
    @Override
    public List<Character> select(List<Character> subjects, int N) {
        List<Character> ans = super.select(subjects, N);
        double[] accumulated = new double[subjects.size()];
        double[] ramdoms = new double[N];

        double aux = 0;
        for(int i =0;i<subjects.size();i++){
            aux+=getFitnessR(i);
            accumulated[i]=aux;
        }
        double r = Math.random();
        for (int i = 0; i <N; i++) {
            ramdoms[i] = (r+i)/N;
        }
        for(int i = 0; i<N;i++){
            int j;
            for(j = 0; j<accumulated.length && accumulated[j]<ramdoms[i];j++);
            ans.add(subjects.get(j));
        }
        return ans;
    }

}
