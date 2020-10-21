import java.util.ArrayList;
import java.util.List;

public class MainEj2 {
    private static int ROW_SIZE = 5;
    public static void main(String[] args) {
       //new IncompatibleSets().test();
        List<Constants> constants = new ArrayList<>();
        constants.add(Constants.H);
        constants.add(Constants.V);
        constants.add(Constants.C);
        constants.add(Constants.K);
        Hopfield h = new Hopfield(constants);
        h.resolvePatron(Constants.H_3.getRepresentation(),ROW_SIZE);

    }
}
