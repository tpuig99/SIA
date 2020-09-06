import crossovers.*;
import finishCriteria.*;
import implementations.FillAllImplementation;
import implementations.FillParentImplementation;
import implementations.Implementation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class TestMain extends Application {
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

    private Subject bestCharacter;
    private int bestGen;

    private Subject worstCharacter;
    private int worstGen;

    private Subject bestWorstCharacter;
    private int bestWorstGen;

    private static ObservableList<XYChart.Data> aList, bList;
    private static ObservableList<XYChart.Series> seriesList;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        // Create empty series
        seriesList = FXCollections.observableArrayList();

        // XYChart.Data(8, 115)
        aList = FXCollections.observableArrayList();
        seriesList.add(new XYChart.Series("BestFitness", aList));

        // XYChart.Data(8, 115)
        bList = FXCollections.observableArrayList();
        seriesList.add(new XYChart.Series("WorstFitness", bList));

        // Create axes
        Axis xAxis = new NumberAxis("Generations", 0, 100, 1);
        Axis yAxis = new NumberAxis("Fitness", 5,40,0.2);

        LineChart chart = new LineChart(xAxis, yAxis, seriesList);

        root.getChildren().add(chart);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        run();
    }

    public void run() {
        readFromConfig();

        List<Subject> population = new ArrayList<>();
        RandomSubject rdm = new RandomCharacter(path);
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
        if(finishCriteria instanceof SolutionCriteria && worstCharacter.getFitness()<((SolutionCriteria) finishCriteria).getFitnessCriteria()){
            System.out.println("Finished because of a limit, not fulfill the criteria.");
        }
        System.out.println("Best in generation "+bestGen);
        System.out.println(bestCharacter);
        System.out.println();
        System.out.println("Worst in generation "+worstGen);
        System.out.println(worstCharacter);
        System.out.println();
        System.out.println("Best minimum in generation "+bestWorstGen);
        System.out.println(bestWorstCharacter);

    }

    private void getInformation(List<Subject> population) {
        System.out.println(generation);
        Subject worstS =Collections.max(population);
        Subject bestS =Collections.min(population);
        System.out.println("Min fitness: "+worstS.getFitness());
        System.out.println("Max fitness: "+bestS.getFitness());
        System.out.println("Population Size: "+population.size());
        System.out.println();

        if(bestCharacter == null||bestCharacter.getFitness()<bestS.getFitness()){
            bestCharacter = bestS.cloneSubject();
            bestGen=generation;
        }

        if(bestWorstCharacter == null||bestWorstCharacter.getFitness()<worstS.getFitness()){
            bestWorstCharacter = worstS.cloneSubject();
            bestWorstGen=generation;
        }
        if(worstCharacter == null||worstCharacter.getFitness()>worstS.getFitness()){
            worstCharacter = worstS.cloneSubject();
            worstGen=generation;
        }

        Double min =worstS.getFitness();
        Double max =bestS.getFitness();
        int aux = 0;
        for (Subject subject: population) {
            aux+=subject.getFitness();
        }
        Double prom=aux/(double)population.size();

        seriesList.get(0).getData().add(new XYChart.Data(generation, max));
        seriesList.get(1).getData().add(new XYChart.Data(generation, min));

        // aList.add(new XYChart.Data(generation, max));
        // bList.add(new XYChart.Data(generation, min));
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
            path = (String) configObj.get("path");
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
