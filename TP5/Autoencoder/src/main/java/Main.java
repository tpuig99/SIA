import models.Font;
import neuralNetwork.Function.ActivationFunction;
import neuralNetwork.Function.SigmoidFunction;
import neuralNetwork.Function.TanhFunction;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Font[] fonts = new Font[Constants.font1.length];

        double[][] trainingSet = new double[32][35];

        for(int i = 0; i < fonts.length ; i++) {
            fonts[i] = new Font(Constants.font2[i], 7, 5);
        }
        int from=0;
        int to=32;
        for (int k = from; k < to ; k++) {
            for (int j = 0; j < fonts[k].getAsArray().length ; j++) {
                trainingSet[k][j] = fonts[k].getAsArray()[j];
            }
        }
        ActivationFunction tn = new TanhFunction();
        ActivationFunction sig = new SigmoidFunction();

        MultilayerPerceptron nn = new MultilayerPerceptron(35, new int[]{40,12,2,12,40}, 35, 0.001,sig);
        System.out.printf("Error antes de entrenar %.3f\n",nn.getError(trainingSet,trainingSet));
        nn.train(trainingSet, trainingSet, 10000,0.70);
        System.out.printf("Error despues de entrenar %.3f\n\n",nn.getError(trainingSet,trainingSet));

        MultilayerPerceptron nn1 = new MultilayerPerceptron(35, new int[]{12,8,2,8,12}, 35, 0.001,tn);
        System.out.printf("Error antes de entrenar %.3f\n",nn1.getError(trainingSet,trainingSet));
        nn1.train(trainingSet, trainingSet, 10000,0.80);
        System.out.printf("Error despues de entrenar %.3f\n\n",nn1.getError(trainingSet,trainingSet));

        MultilayerPerceptron nn2 = new MultilayerPerceptron(35, new int[]{10,5,10}, 35, 0.001,tn);
        System.out.printf("Error antes de entrenar %.3f\n",nn2.getError(trainingSet,trainingSet));
        nn2.train(trainingSet, trainingSet, 10000,0.80);
        System.out.printf("Error despues de entrenar %.3f\n\n",nn2.getError(trainingSet,trainingSet));
        /*for (double[] item : trainingSet) {
            List<Double> l = nn.calculate(item);
            for (Double d : l) {
                //System.out.printf("%.2f ", d);
                System.out.print((d>0.5?1:0)+" ");
            }
            System.out.println();
            System.out.println(Arrays.toString(item));
            System.out.println("---------------------------------------------------------------");

        }
        int total = 0;
        int x;
        for (int i = from; i < to; i++) {
            x = 0;
            List<Double> l = nn.calculate(trainingSet[i]);
            for (int j = 0; j < trainingSet[i].length; j++) {
                double aux = (l.get(j)>0.5?1:0);
                if(aux!=trainingSet[i][j]){
                    x++;
                }
            }
            if(x!=0){
                total++;
                System.out.println("en i: "+i);
            }
        }
        System.out.println("total: "+total +"/"+(to-from));
    */
    }
}
