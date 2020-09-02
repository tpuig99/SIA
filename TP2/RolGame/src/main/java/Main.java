
import crossovers.Crossover;
import finishCriteria.FinishCriteria;
import mutations.Mutation;
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
        Selector sParent1;
        Selector sParent2;
        double sParentPer;
        int parentSize;

        Selector sReplace1;
        Selector sReplace2;
        double sReplacePer;

        Mutation mutation;
        int mutationSize;

        Crossover crossover;
        int crossoverSize;

        FinishCriteria finishCriteria;
        List<Object> finishCriteriaParams;

        Role role;
        
        String path;

        int populationSize;

    public static void main(String[] args){


        List<Subject> population = new ArrayList<>();
        //saldría de config, voy a elegir 100
        int subjCant = 100;
        Role role = Role.ARCHER;
        RandomSubject rdm = new RandomCharacter();
        for (int i = 0; i < subjCant; i++) {
            population.add(rdm.randomCharacter(role));
        }
    }



}
