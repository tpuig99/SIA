import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Hopfield {
    private int patrons [][];
    private double weight [][];
    private int neuronsAmount;
    private boolean hasNames = false;
    private HashMap<int[],String> map;

    public Hopfield(int[][] patrons) {
        if(patrons.length==0)
            throw new RuntimeException("You need at least one patron");
        this.patrons = patrons;
        this.neuronsAmount = patrons[0].length;
        weight = new double[neuronsAmount][neuronsAmount];
        for (int i = 0; i < neuronsAmount; i++) {
            for (int j = 0; j < neuronsAmount; j++) {
                double h = 0;
                if(i!=j){
                    for (int[] p:patrons) {
                        h+=p[i]*p[j];
                    }
                }
                weight[i][j]=h/neuronsAmount;
            }
        }
    }
    public Hopfield(List<Constants> patronsConstants) {
        if(patronsConstants.size()==0)
            throw new RuntimeException("You need at least one patron");
        int[][] patrons = new int[patronsConstants.size()][patronsConstants.get(0).getRepresentation().length];
        this.hasNames = true;
        this.map = new HashMap<>();
        for (int i = 0; i < patronsConstants.size(); i++) {
            patrons[i]=patronsConstants.get(i).getRepresentation();
            map.put(patronsConstants.get(i).getRepresentation(),patronsConstants.get(i).getName());
        }

        this.patrons = patrons;
        this.neuronsAmount = patrons[0].length;
        weight = new double[neuronsAmount][neuronsAmount];
        for (int i = 0; i < neuronsAmount; i++) {
            for (int j = 0; j < neuronsAmount; j++) {
                double h = 0;
                if(i!=j){
                    for (int[] p:patrons) {
                        h+=p[i]*p[j];
                    }
                }
                weight[i][j]=h/neuronsAmount;
            }
        }
    }

    public int resolvePatron(int[]patron,Integer rowSize){
        int[] S = Arrays.copyOf(patron,patron.length);
        int[] nextS = Arrays.copyOf(patron,patron.length);
        boolean stable;
        int count = 0;
        PrintLetterMethod print = new PrintLetterMethod();
        do{
            if(rowSize!=null)
                System.out.println("Step "+(count++)+":");
            stable = true;
            if(rowSize!=null)
                print.printLetter(S,rowSize);
            for (int i = 0; i < neuronsAmount; i++) {
                double h = 0;
                //if(j!=i)
                for (int j = 0; j < neuronsAmount; j++) {
                    h+=weight[i][j]*S[j];
                }
                int next = sgn(h);
                nextS[i]=next == 0? S[i] : next;
                if(nextS[i]!=S[i])
                    stable = false;
            }
            S = Arrays.copyOf(nextS,nextS.length);
        }while (!stable);
        if(rowSize!=null)
            System.out.println("We got it stable.\n\n");
        for (int[] p: patrons) {
            if(Arrays.equals(p, S)){
               if(rowSize!=null) {
                   String s = hasNames ? map.get(p) : "";
                   System.out.println("It has an associate patron and it is:" + s + "\n");
                   print.printLetter(p, rowSize);
               }
                return 1;
            }
        }
        if(rowSize!=null) {
            System.out.println("It is a spurious state: \n");
            print.printLetter(S, rowSize);
        }
        return -1;
    }

    private int sgn(double h) {
        if(Double.compare(h,0.0) == 0){
            return 0;
        }
        return h > 0 ? 1 : -1;
    }
}
