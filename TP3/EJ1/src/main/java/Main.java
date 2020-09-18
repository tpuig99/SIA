import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ej1();
    }

    public static void ej1 () {
        // AND
        SimplePerceptron simplePerceptron = new SimplePerceptron(2);
        double[][] input = {{-1, 1}, {1, -1}, {-1, -1}, {1, 1}};
        double[] expected_output_AND = {-1, -1, -1, 1};
        int i = 0;
        int fails = 0;
        while (i++ < 100) {
            if (!Arrays.equals(simplePerceptron.train(input, expected_output_AND, 0.2, 10), expected_output_AND)) {
             fails ++;
            }
        }
        System.out.printf("There were %d fails\n", fails);

        // XOR
        SimplePerceptron simplePerceptron2 = new SimplePerceptron(2);
        double[] expectedOutputXOR = {1, 1, -1, -1};
        int XORi = 0;
        int XORfails = 0;
        while (XORi++ < 100) {
            if (!Arrays.equals(simplePerceptron2.train(input, expectedOutputXOR, 0.2, 100), expectedOutputXOR)) {
                XORfails ++;
            }
        }
        System.out.printf("There were %d fails\n", XORfails);


    }
}
