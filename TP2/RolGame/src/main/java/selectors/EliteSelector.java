package selectors;

import subjectModels.roles.Character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EliteSelector extends Selector {

    @Override
    public List<Character> select(List<Character> subjects, int K) {
        List<Character> selectionList = new ArrayList<>();

        if (K <= 0) {
            return selectionList;
        }

        List<Character> auxiliarySortedList = new ArrayList<>(subjects);
        Collections.sort(auxiliarySortedList);

        for (int i = 0; selectionList.size() <= K && i < auxiliarySortedList.size(); i++) {
            int n = (int) Math.ceil((float) (K - i) / auxiliarySortedList.size());
            for (int j = 0; selectionList.size() <= K && j < n; j++)
                selectionList.add(auxiliarySortedList.get(i));
        }
        return selectionList;
    }
}
