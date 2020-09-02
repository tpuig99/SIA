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

import javax.security.auth.login.Configuration;
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

    int generation = 0;

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
                generation++;
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
            getInformation(population);
        }
        System.out.println("Best in generation "+generation);
        System.out.println(Collections.max(population));

    }

    private void getInformation(List<Subject> population) {
        System.out.println(generation);
        System.out.println("Min fitness: "+Collections.min(population).getFitness());
        System.out.println("Max fitness: "+Collections.max(population).getFitness());
        System.out.println();
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

        finishCriteria = new SolutionCriteria(16);

        role = Role.ARCHER;

        //path;

        System.out.println("Configuration");
        System.out.println("Initial Population Size: "+initialPopulationSize);
        System.out.println("Population Size: "+populationSize);
        System.out.println("Parents selectors: "+sParent1+", "+sParent2+". Percentage "+sParentPer+". Size "+parentSelectSize);
        System.out.println("Replacement selectors: "+sReplace1+", "+sReplace2+". Percentage "+sReplacePer);
        System.out.println("CrossOver: "+crossover+". Childs Size: "+crossoverSize);
        System.out.println("Mutation: "+mutation);
        System.out.println("Fill implementation: "+fillImplementation);
        System.out.println("Finish Criteria: "+finishCriteria);
        System.out.println("-----------------------------------------------------------");
        System.out.println();

    }
}
