package NeuralNetwork;

import java.text.DecimalFormat;
import java.util.Random;

public class Globals {
    public static final Random random = new Random(System.currentTimeMillis());

    public static void printProgressbar(int current, int total, int barLength, boolean printPercentage, boolean printStatus, String endMessage) {
        boolean isFinished = current == total;

        if(isFinished && endMessage != null) {
            System.out.println(endMessage);
            return;
        }

        double currentPercent = current / (float)total * 100;
        int p = (int)((currentPercent * barLength / 100f) + .5f);

        StringBuilder sb = new StringBuilder();

        sb.append('[');
        for (int i = 0; i < barLength; ++i) {
            if (i >= p)
                sb.append(' ');
            else
                sb.append('■');
        }
        sb.append(']');

        DecimalFormat df = new DecimalFormat("#.##");

        if(printPercentage)
            sb.append(" " + df.format(currentPercent) + "%");

        if(printStatus)
            sb.append(" (" + current + "/" + total + ")");

        sb.append( isFinished ? '\n' : '\r' );

        System.out.print(sb.toString());
    }
}
