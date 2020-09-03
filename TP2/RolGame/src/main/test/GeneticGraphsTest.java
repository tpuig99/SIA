import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

class GeneticGraphsTest {
    GeneticGraphs geneticGraphs;

    @Test
    void showGraphic() {
        double[] generations = new double[50];
        double[] bestFitness = new double[50];
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            generations[i] = i;
            bestFitness[i] = random.nextInt(100) + Math.random();
        }
        Arrays.sort(bestFitness);
        geneticGraphs = new GeneticGraphs(generations, bestFitness);
        geneticGraphs.showGraphic();
    }
}