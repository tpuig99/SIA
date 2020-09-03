
import crossovers.*;
import finishCriteria.*;
import implementations.FillAllImplementation;
import implementations.FillParentImplementation;
import implementations.Implementation;
import mutations.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import selectors.*;
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
import java.util.ArrayList;
import java.util.List;

import static subjectModels.Constants.ItemType.ARMOR;

public class Main {
    static Selector sParent1, sParent2, sSubject1, sSubject2;
    static int selectionSize, parentSize, populationSize;
    static double a, b, p_m;
    static Mutation mutation;
    static Crossover crossover;
    static FinishCriteria finishCriteria;
    static Implementation fill;
    static JSONObject configObj;
    static Role role;
    static String path;

    public static void main(String[] args){
        readFromConfig();

        List<Subject> population = new ArrayList<>();
        //saldría de config, voy a elegir 100
        int subjCant = 100;
        Role role = Role.ARCHER;
        RandomSubject rdm = new RandomCharacter();
        for (int i = 0; i < subjCant; i++) {
            population.add(rdm.randomCharacter(role));
        }
    }

    private static void readFromConfig() {
        JSONParser jsonParser = new JSONParser();
        configObj = null;
        try {
            configObj = (JSONObject) jsonParser.parse(new FileReader("../config.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (configObj != null) {
            populationSize = (int) ((long) configObj.get("population"));
            parentSize = (int) ((long) configObj.get("parentSize"));
            selectionSize = (int) ((long) configObj.get("selectionSize"));
            crossover = parseCrossover((JSONObject) configObj.get("chosenCrossover"));
            mutation = parseMutation((JSONObject) configObj.get("chosenMutation"));
            parseParentSelection((JSONObject) configObj.get("chosenParentSelection"));
            parseSubjectSelection((JSONObject) configObj.get("chosenSubjectSelection"));
            finishCriteria = parseFinishCriteria((JSONObject) configObj.get("chosenFinishCriteria"));
        }
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
        a = (double) parentObject.get("a");
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
                int percentage = (int) ((long) structure.get("percentage"));
                return new StructureCriteria(percentage, sGenerationsLimit);
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
        sSubject1 = parseSelector(selection1, (JSONObject) configObj.get("subjectSelection"));
        sSubject2 = parseSelector(selection2, (JSONObject) configObj.get("subjectSelection"));
        b = (double) subjectObject.get("b");
        fill = parseFill((String) subjectObject.get("fill"));
    }

    private static Implementation parseFill(String name) {
        if (name.equals("fillParent")) {
            return new FillParentImplementation(sSubject1, sSubject2, (float) b);
        }
        else if (name.equals("fillAll")) {
            return new FillAllImplementation(sSubject1, sSubject2, (float) b);
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
