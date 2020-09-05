import crossovers.*;
import finishCriteria.*;
import implementations.FillAllImplementation;
import implementations.FillParentImplementation;
import implementations.Implementation;
import mutations.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import selectors.*;
import subjectModels.Constants.Role;
import subjectModels.RandomSubject;
import subjectModels.Subject;
import subjectModels.roles.RandomCharacter;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestMain {
    private static Selector sParent1;
    private static Selector sParent2;
    private static double sParentPer;
    private static Selector sReplace1;
    private static Selector sReplace2;
    private static double sReplacePer;
    private static int parentSelectSize;
    private static Mutation mutation;
    private static double p_m;
    private static Crossover crossover;
    private static int selectionSize;
    private static Implementation fillImplementation;
    private static FinishCriteria finishCriteria;
    private static Role role;
    private static String path;
    private static int populationSize;
    private static int initialPopulationSize;
    private static int generation = 0;
    private static JSONObject configObj;

    public void run(){
        readFromConfig();

        List<Subject> population = new ArrayList<>();
        RandomSubject rdm = new RandomCharacter();
        for (int i = 0; i < populationSize; i++) {
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

                while (newGeneration.size()<selectionSize) {
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
        System.out.println(Collections.min(population));

    }

    private void getInformation(List<Subject> population) {
        System.out.println(generation);
        System.out.println("Min fitness: "+Collections.max(population).getFitness());
        System.out.println("Max fitness: "+Collections.min(population).getFitness());
        System.out.println();
        Double min =Collections.max(population).getFitness();
        Double max =Collections.min(population).getFitness();
        int aux = 0;
        for (Subject subject: population) {
            aux+=subject.getFitness();
        }
        Double prom=aux/(double)population.size();

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

        crossover = new UniformCrossover();
        selectionSize = 40;

        //fillImplementation = new FillAllImplementation(sReplace1,sReplace2,sReplacePer);
        initialPopulationSize = 100;
        populationSize = 150;

        finishCriteria = new SolutionCriteria(16);

        role = Role.ARCHER;

        //path;

    }
    void readFromConfig() {
        JSONParser jsonParser = new JSONParser();
        configObj = null;
        try {
            configObj = (JSONObject) jsonParser.parse(new FileReader("../config.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (configObj != null) {
            role = parseRole((String) configObj.get("role"));
            populationSize = (int) ((long) configObj.get("population"));
            parentSelectSize = (int) ((long) configObj.get("parentSize"));
            selectionSize = (int) ((long) configObj.get("selectionSize"));
            crossover = parseCrossover((JSONObject) configObj.get("chosenCrossover"));
            mutation = parseMutation((JSONObject) configObj.get("chosenMutation"));
            parseParentSelection((JSONObject) configObj.get("chosenParentSelection"));
            parseSubjectSelection((JSONObject) configObj.get("chosenSubjectSelection"));
            finishCriteria = parseFinishCriteria((JSONObject) configObj.get("chosenFinishCriteria"));
        }
        System.out.println("Configuration");
        System.out.println("Initial Population Size: "+initialPopulationSize);
        System.out.println("Population Size: "+populationSize);
        System.out.println("Parents selectors: "+sParent1+", "+sParent2+". Percentage "+sParentPer+". Size "+parentSelectSize);
        System.out.println("Replacement selectors: "+sReplace1+", "+sReplace2+". Percentage "+sReplacePer);
        System.out.println("CrossOver: "+crossover+". Childs Size: "+selectionSize);
        System.out.println("Mutation: "+mutation);
        System.out.println("Fill implementation: "+fillImplementation);
        System.out.println("Finish Criteria: "+finishCriteria);
        System.out.println("-----------------------------------------------------------");
        System.out.println();
    }

    public static Role parseRole (String role) {
        switch (role) {
            case "archer":
                return Role.ARCHER;
            case "warrior":
                return Role.WARRIOR;
            case "infiltrate":
                return Role.INFILTRATE;
            case "defender":
                return Role.DEFENDER;
        }
        return null;
    }

    private static Crossover parseCrossover(JSONObject crossoverObj){
        String name = (String) crossoverObj.get("name");
        switch (name) {
            case "single":
                return new SinglePointCrossover();
            case "double":
                return new DoublePointCrossover();
            case "anular":
                return new AnularCrossover();
            case "uniform":
                return new UniformCrossover();
        }
        return null;
    }

    private static Mutation parseMutation(JSONObject mutationObj){
        String name = (String) mutationObj.get("name");
        p_m = (double) mutationObj.get("p_m");
        switch (name) {
            case "complete":
                return new CompleteMutation((float) p_m);
            case "gen":
                return new GenMutation((float) p_m);
            case "limited":
                return new LimitedMultigeneMutation((float) p_m);
            case "uniform":
                return new UniformMultigeneMutation((float) p_m);
        }
        return null;
    }

    private static void parseParentSelection(JSONObject parentObject){
        String selection1 = (String) parentObject.get("parent1");
        String selection2 = (String) parentObject.get("parent2");
        sParentPer = (double) parentObject.get("a");
        sParent1 = parseSelector(selection1, (JSONObject) configObj.get("parentSelection"));
        sParent2 = parseSelector(selection2, (JSONObject) configObj.get("parentSelection"));
    }

    private static FinishCriteria parseFinishCriteria(JSONObject finishObject){
        String name = (String) finishObject.get("name");
        JSONObject finishParams = (JSONObject) configObj.get("finishCriteria");

        switch (name) {
            case "time":
                JSONObject time = (JSONObject) finishParams.get("time");
                int seconds =(int) ((long) time.get("seconds"));
                return new TimeCriteria(seconds);
            case "content":
                JSONObject content = (JSONObject) finishParams.get("content");
                int cGenerationsLimit = (int) ((long) content.get("generationsLimit"));
                double epsilon = (double) content.get("epsilon");
                return new ContentCriteria(cGenerationsLimit, epsilon);
            case "structure":
                JSONObject structure = (JSONObject) finishParams.get("structure");
                int sGenerationsLimit = (int) ((long) structure.get("generationsLimit"));
                double percentage = ((double) structure.get("percentage"));
                return new StructureCriteria((int) Math.ceil(percentage*populationSize), sGenerationsLimit);
            case "generations":
                JSONObject generations = (JSONObject) finishParams.get("generations");
                int gGenerationsLimit = (int) ((long) generations.get("generationsLimit"));
                return new GenerationsCriteria(gGenerationsLimit);
            case "solution":
                JSONObject solution = (JSONObject) finishParams.get("solution");
                double fitness = (double) solution.get("fitness");
                return new SolutionCriteria(fitness);
        }
        return null;
    }

    private static void parseSubjectSelection(JSONObject subjectObject){
        String selection1 = (String) subjectObject.get("subject1");
        String selection2 = (String) subjectObject.get("subject2");
        sReplace1 = parseSelector(selection1, (JSONObject) configObj.get("subjectSelection"));
        sReplace2 = parseSelector(selection2, (JSONObject) configObj.get("subjectSelection"));
        sReplacePer = (double) subjectObject.get("b");
        fillImplementation = parseFill((String) subjectObject.get("fill"));
    }

    private static Implementation parseFill(String name) {
        if (name.equals("fillParent")) {
            return new FillParentImplementation(sReplace1, sReplace2, (float) sReplacePer);
        }
        else if (name.equals("fillAll")) {
            return new FillAllImplementation(sReplace1, sReplace2, (float) sReplacePer);
        }
        return null;
    }

    private static Selector parseSelector(String name, JSONObject selection) {
        switch (name) {
            case "boltzmann":
                JSONObject boltzmann = (JSONObject) selection.get("boltzmann");
                int t0 =(int) ((long) boltzmann.get("t0"));
                int tc = (int) ((long) boltzmann.get("tc"));
                return new BoltzmannSelector(t0, tc);
            case "pTournament":
                JSONObject pTournament = (JSONObject) selection.get("pTournament");
                double pthreshold = (double) pTournament.get("threshold");
                return new ProbabilisticTournamentsSelector(pthreshold);
            case "dTournament":
                JSONObject dTournament = (JSONObject) selection.get("dTournament");
                int dthreshold = (int) ((long) dTournament.get("threshold"));
                return new DeterministicTournamentsSelector(dthreshold);
            case "elite":
                return new EliteSelector();
            case "ranking":
                return new RankingSelector();
            case "roulette":
                return new RouletteSelector();
            case "universal":
                return new UniversalSelector();
        }
        return null;
    }
}
