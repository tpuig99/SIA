import crossovers.AnularCrossover;
import crossovers.Crossover;
import crossovers.UniformCrossover;
import finishCriteria.FinishCriteria;
import finishCriteria.GenerationsCriteria;
import finishCriteria.SolutionCriteria;
import finishCriteria.StructureCriteria;
import implementations.FillAllImplementation;
import implementations.Implementation;
import mutations.LimitedMultigeneMutation;
import mutations.Mutation;
import mutations.UniformMultigeneMutation;
import selectors.RankingSelector;
import selectors.RouletteSelector;
import selectors.Selector;
import selectors.UniversalSelector;
import subjectModels.Constants.Role;
import subjectModels.RandomSubject;
import subjectModels.Subject;
import subjectModels.roles.RandomCharacter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestMain {
    Selector sParent1;
    Selector sParent2;
    double sParentPer;

    Selector sReplace1;
    Selector sReplace2;
    float sReplacePer;
    int parentSelectSize;

    Mutation mutation;
    int mutationSize;

    Crossover crossover;
    int crossoverSize;

    Implementation fillImplementation;

    FinishCriteria finishCriteria;

    Role role;

    String path;

    int populationSize;
    int initialPopulationSize;


    public void run(){
        getConfiguration();
        List<Subject> population = new ArrayList<>();
        RandomSubject rdm = new RandomCharacter();
        for (int i = 0; i < initialPopulationSize; i++) {
            population.add(rdm.randomCharacter(role));
        }
        List<Subject> parentSelection;
        List<Subject> newGeneration;
        while (!finishCriteria.shouldFinish(population)){
                newGeneration = new ArrayList<>();
                parentSelection = new ArrayList<>();

                parentSelection.addAll(sParent1.select(population, (int) Math.ceil(parentSelectSize*sParentPer)));
                parentSelection.addAll(sParent2.select(population, (int) Math.floor(parentSelectSize*(1-sParentPer))));

                while (newGeneration.size()<crossoverSize) {
                    if (newGeneration.size() % parentSelectSize == 0) {
                        Collections.shuffle(parentSelection);
                    }
                    Subject s1 = parentSelection.remove(0);
                    Subject s2 = parentSelection.remove(0);
                    parentSelection.add(s1);
                    parentSelection.add(s2);
                    newGeneration.addAll(crossover.cross(s1, s2));
                }
            for (int i = 0; i <newGeneration.size() ; i++) {
                Subject subject = mutation.mutate(newGeneration.remove(i));
                newGeneration.add(i,subject);
            }
            population = new ArrayList<>();
            population.addAll(fillImplementation.evolve(parentSelection,newGeneration,populationSize));
        }
    }

    private void getConfiguration() {
        sParent1 = new UniversalSelector();
        sParent2 = new RouletteSelector();
        sParentPer = 0.5;
        parentSelectSize = 50;

        sReplace1 = new RankingSelector();
        sReplace2 = new UniversalSelector();
        sReplacePer = 0.5f;

        mutation = new UniformMultigeneMutation(0.2f);
        mutationSize = 20;

        crossover = new UniformCrossover();
        crossoverSize = 40;

        fillImplementation = new FillAllImplementation(sReplace1,sReplace2,sReplacePer);
        initialPopulationSize = 100;
        populationSize = 150;

        finishCriteria = new StructureCriteria((int) Math.ceil(0.3*populationSize),3);

        role = Role.ARCHER;

        //path;



    }
}
