package selectors;

import subjectModels.Subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EliteSelector extends Selector {

    @Override
    public List<Subject> select(List<Subject> subjects, int K) {
        List<Subject> selectionList = new ArrayList<>();

        if (K <= 0) {
            return selectionList;
        }

        List<Subject> auxiliarySortedList = new ArrayList<>(subjects);
        Collections.sort(auxiliarySortedList);

        for (int i = 0; selectionList.size() <= K && i < auxiliarySortedList.size(); i++) {
            int n = (int) Math.ceil((float) (K - i) / auxiliarySortedList.size());
            for (int j = 0; selectionList.size() <= K && j < n; j++)
                selectionList.add(auxiliarySortedList.get(i));
        }
        return selectionList;
    }
}
