import NeuralNetwork.Functions.ActivationFunction;
import NeuralNetwork.Functions.SigmoidActivationFunction;
import NeuralNetwork.Functions.TanhActivationFunction;
import NeuralNetwork.MLP;
import models.Font;

public class Main {
    public static void main(String[] args) {
        Font[] fonts = new Font[Constants.font1.length];

        double[][] trainingSet = new double[32][35];

        for(int i = 0; i < fonts.length ; i++) {
            fonts[i] = new Font(Constants.font1[i], 7, 5);
        }
        int from=0;
        int to=32;
        for (int k = from; k < to ; k++) {
            for (int j = 0; j < fonts[k].getAsArray().length ; j++) {
                trainingSet[k][j] = fonts[k].getAsArray()[j];
            }
        }

        ActivationFunction tn = new TanhActivationFunction(2);
        ActivationFunction sig = new SigmoidActivationFunction();
        /*
        List<double[]> one_layer_s = new ArrayList<>();
        List<double[]> one_layer_t = new ArrayList<>();

        for (int i = 0; i < 35; i++) {
            MultilayerPerceptron nn_s = new MultilayerPerceptron(35, new int[]{i,2,i}, 35, 0.001,sig);
            MultilayerPerceptron nn_t = new MultilayerPerceptron(35, new int[]{i,2,i}, 35, 0.001,tn);
            nn_s.train(trainingSet, trainingSet, 10000,0.70);
            nn_t.train(trainingSet, trainingSet, 10000,0.70);
            double t[] = new double[2];
            double s[] = new double[2];
            t[0]=i;
            t[1]= nn_t.getError(trainingSet,trainingSet);
            one_layer_t.add(t);
            s[0]=i;
            s[1]= nn_s.getError(trainingSet,trainingSet);
            one_layer_s.add(s);
            System.out.println("Con "+i+": t->"+t[1]+" | s->"+s[1]);
        }
        System.out.println(one_layer_s);
        System.out.println(one_layer_t);
       */
        MLP nn = new MLP(35, new int[]{40,12,2,12,40}, 35, 0.0012,tn);
        System.out.printf("Error antes de entrenar %.3f\n",nn.getError(trainingSet,trainingSet));
        nn.train(trainingSet, trainingSet, 10000,false,0.75);
        System.out.printf("Error despues de entrenar %.3f\n\n",nn.getError(trainingSet,trainingSet));

        MLP nn1 = new MLP(35, new int[]{20,3,2,3,20}, 35, 0.001,sig);
        System.out.printf("Error antes de entrenar %.3f\n",nn1.getError(trainingSet,trainingSet));
        nn1.train(trainingSet, trainingSet, 10000,false,0.80);
        System.out.printf("Error despues de entrenar %.3f\n\n",nn1.getError(trainingSet,trainingSet));

        MLP nn2 = new MLP(35, new int[]{10,2,10}, 35, 0.001,tn);
        System.out.printf("Error antes de entrenar %.3f\n",nn2.getError(trainingSet,trainingSet));
        nn2.train(trainingSet, trainingSet, 10000,false,0.80);
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
