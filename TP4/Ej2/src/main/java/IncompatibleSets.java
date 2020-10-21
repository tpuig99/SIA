import java.util.ArrayList;
import java.util.List;

public class IncompatibleSets {
    void test(){
        List<Constants> c1 = new ArrayList<>();
        c1.add(Constants.T);
        c1.add(Constants.V);
        c1.add(Constants.C);
        c1.add(Constants.P);
        c1.add(Constants.N);
        c1.add(Constants.H);
        c1.add(Constants.K);
        List<String> list = new ArrayList<>();
        List<Constants> c2 = new ArrayList<>(c1);
        for (Constants cl1: c1) {
            c2.remove(cl1);
            List<Constants> c3 = new ArrayList<>(c2);
            for (Constants cl2:c2) {
                c3.remove(cl2);
                List<Constants> c4 = new ArrayList<>(c3);
                for (Constants cl3: c3) {
                    c4.remove(cl3);
                    for (Constants cl4: c4) {
                        List<Constants> aux = new ArrayList<>();
                        aux.add(cl1);
                        aux.add(cl2);
                        aux.add(cl3);
                        aux.add(cl4);
                        Hopfield h = new Hopfield(aux);
                        boolean flag = true;
                        for (int i = 0; i < aux.size() && flag; i++) {
                            int x = h.resolvePatron(aux.get(i).getRepresentation(),null);
                            if(x<0) {
                                flag = false;
                                String letters = aux.get(0).getName()+" "+aux.get(1).getName()+" "+aux.get(2).getName()+" "+aux.get(3).getName();
                                list.add(letters);
                            }
                        }
                    }
                }

            }

        }
        for (String ans:list) {
            System.out.println(ans);
        }
    }
}
