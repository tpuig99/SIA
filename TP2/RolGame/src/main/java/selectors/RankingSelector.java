package selectors;

import subjectModels.roles.Character;

import java.util.Comparator;
import java.util.List;

public class RankingSelector extends RouletteSelector{
    int x;
    @Override
    public List<Character> select(List<Character> subjects, int N) {
        x=0;
        for (int i = 1; i < (subjects.size()+1);x+=i, i++ );
        subjects.sort(new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return Double.compare(o1.getDesempeño(),o2.getDesempeño());
            }
        });
        return super.select(subjects, N);
    }

    @Override
    double getFitnessR(int index) {
        return ((double) (index+1)/(double)x);
    }
}
