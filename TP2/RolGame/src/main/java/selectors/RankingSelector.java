package selectors;

import subjectModels.Subject;

import java.util.Comparator;
import java.util.List;

public class RankingSelector extends RouletteSelector{
    int x;
    @Override
    public List<Subject> select(List<Subject> subjects, int N) {
        x=0;
        for (int i = 1; i < (subjects.size()+1);x+=i, i++ );
        subjects.sort(new Comparator<Subject>() {
            @Override
            public int compare(Subject o1, Subject o2) {
                return Double.compare(o1.getFitness(),o2.getFitness());
            }
        });
        return super.select(subjects, N);
    }

    @Override
    double getFitnessR(int index) {
        return ((double) (index+1)/(double)x);
    }
}
