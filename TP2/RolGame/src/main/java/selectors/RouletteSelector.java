package selectors;

import subjectModels.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouletteSelector extends Selector{
    @Override
    public List<Subject> select(List<Subject> subjects, int N) {
        List<Subject> ans = super.select(subjects, N);
        double[] accumulated = new double[subjects.size()];
        double[] ramdoms = new double[N];

        double aux = 0;
        for(int i =0;i<subjects.size();i++){
            aux+=getFitnessR(i);
            accumulated[i]=aux;
        }
        Random random = new Random();
        for (int i = 0; i <N; i++) {
            ramdoms[i] = random.nextDouble();
        }
        for(int i = 0; i<N;i++){
            int j;
            for(j = 0; j<accumulated.length && accumulated[j]<ramdoms[i];j++);
            ans.add(subjects.get(j));
        }
        return ans;
    }

}
