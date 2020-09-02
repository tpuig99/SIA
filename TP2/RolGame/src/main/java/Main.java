
import crossovers.Crossover;
import finishCriteria.FinishCriteria;
import mutations.Mutation;
import selectors.RankingSelector;
import selectors.Selector;
import subjectModels.Constants.ItemType;
import subjectModels.Constants.Role;
import subjectModels.RandomSubject;
import subjectModels.Subject;
import subjectModels.roles.Character;
import subjectModels.equipment.Items;
import subjectModels.roles.RandomCharacter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static subjectModels.Constants.ItemType.ARMOR;

public class Main {
    public static void main(String[] args){
        new TestMain().run();

    }



}
