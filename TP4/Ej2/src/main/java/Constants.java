public enum Constants {
    T("T", new int[]{1, 1,1, 1, 1,
            -1, -1, 1, -1, -1,
            -1, -1, 1, -1, -1,
            -1, -1, 1, -1, -1,
            -1, -1, 1, -1, -1}),
    T_1("T_1", new int[]{1, 1,1, 1, 1,
            -1, -1, 1, -1, -1,
            -1, -1, 1, 1, -1,
            -1, -1, 1, -1, 1,
            1, -1, 1, -1, -1}),
    T_2("T_2", new int[]{1, 1,1, 1, 1,
            -1, 1, 1, -1, -1,
            -1, -1, 1, 1, -1,
            -1, -1, 1, -1, 1,
            1, -1, 1, -1, -1}),
    T_3("T_3", new int[]{1, 1,1, 1, -1,
            -1, -1, 1, -1, -1,
            1, -1, 1, 1, -1,
            1, -1, 1, -1, 1,
            1, -1, 1, -1, -1}),
    V("V", new int[]{1, -1, -1, -1, 1,
            1, -1, -1, -1, 1,
            1, -1, -1, -1, 1,
            -1, 1, -1, 1, -1,
            -1, -1, 1, -1, -1}),
    V_1("V_1", new int[]{1, -1, -1, -1, 1,
            1, -1, -1, -1, 1,
            1, -1, 1, -1, 1,
            1, 1, -1, 1, -1,
            -1, 1, 1, -1, -1}),
    V_2("V_2", new int[]{1, -1, 1, -1, 1,
            1, -1, -1, -1, 1,
            1, -1, 1, -1, 1,
            1, 1, -1, 1, -1,
            -1, 1, 1, -1, -1}),
    V_3("V_3", new int[]{1, -1, -1, -1, 1,
            -1, -1, -1, 1, 1,
            1, -1, 1, 1, 1,
            1, 1, -1, 1, -1,
            -1, 1, 1, -1, -1}),
    C("C", new int[]{ -1,1,1,1,1,
            1,-1,-1,-1,-1,
            1,-1,-1,-1,-1,
            1,-1,-1,-1,-1,
            -1,1,1,1,1}),
    C_1("C_1", new int[]{ -1,1,1,1,1,
            1,-1,-1,-1,-1,
            1,-1,-1,1,-1,
            1,-1,1,-1,-1,
            1,1,1,1,1}),
    C_2("C_2", new int[]{-1,1,1,1,1,
            1,-1,-1,1,-1,
            1,-1,-1,1,-1,
            1,-1,1,-1,-1,
            1,-1,1,1,1}),
    C_3("C_3", new int[]{ -1,1,1,1,1,
            1,-1,-1,1,-1,
            -1,-1,-1,1,-1,
            1,-1,1,-1,-1,
            1,-1,1,1,1}),
    P("P", new int[]{ 1,1,1,1,1,
            1,-1,-1,-1,1,
            1,1,1,1,1,
            1,-1,-1,-1,-1,
            1,-1,-1,-1,-1}),
    P_1("P_1", new int[]{ 1,1,1,1,1,
            1,-1,-1,1,1,
            1,1,1,1,1,
            1,-1,-1,1,-1,
            1,1,-1,-1,-1}),
    P_2("P_2", new int[]{ 1,1,-1,1,1,
            1,-1,-1,-1,1,
            -1,1,1,1,-1,
            1,1,-1,1,-1,
            1,1,-1,-1,-1}),
    P_3("P_3", new int[]{ 1,1,-1,1,1,
            1,-1,1,-1,1,
            1,1,1,1,-1,
            1,-1,-1,1,-1,
            1,1,-1,-1,-1}),
    N("N", new int[]{ 1,-1,-1,-1,1,
            1,1,-1,-1,1,
            1,-1,1,-1,1,
            1,-1,-1,1,1,
            1,-1,-1,-1,1}),
    N_1("N_1", new int[]{ 1,-1,-1,-1,1,
            1,1,-1,1,1,
            1,1,1,-1,1,
            1,-1,-1,1,1,
            1,-1,-1,-1,-1}),
    N_2("N_2", new int[]{ 1,-1,-1,-1,1,
            1,1,-1,1,1,
            1,1,1,-1,1,
            1,-1,1,1,1,
            1,-1,-1,-1,-1}),
    N_3("N_3", new int[]{ -1,-1,-1,-1,1,
            1,1,-1,1,1,
            1,1,1,1,1,
            1,1,-1,1,1,
            1,-1,-1,-1,-1}),
    H("H", new int[]{ 1,-1,-1,-1,1,
            1,-1,-1,-1,1,
            1,1,1,1,1,
            1,-1,-1,-1,1,
            1,-1,-1,-1,1}),
    H_1("H_1", new int[]{ 1,-1,-1,-1,1,
            1,1,-1,-1,1,
            1,1,-1,1,1,
            1,-1,-1,-1,-1,
            1,-1,-1,-1,1}),
    H_2("H_2", new int[]{ 1,-1,1,-1,1,
            1,1,-1,-1,1,
            1,1,-1,1,1,
            -1,-1,-1,-1,-1,
            1,-1,-1,1,1}),
    H_3("H_3", new int[]{ -1,-1,-1,-1,-1,
            -1,1,-1,-1,1,
            1,-1,1,-1,-1,
            -1,-1,1,-1,1,
            1,-1,-1,-1,-1}),
    K("K", new int[]{ 1,-1,-1,1,-1,
            1,-1,1,-1,-1,
            1,1,-1,-1,-1,
            1,-1,1,-1,-1,
            1,-1,-1,1,-1}),
    K_1("K_1", new int[]{ 1,-1,-1,1,-1,
            1,-1,-1,-1,-1,
            1,1,-1,-1,-1,
            1,1,1,-1,1,
            1,-1,-1,1,-1}),
    K_2("K_2", new int[]{ 1,-1,-1,1,1,
            1,-1,-1,-1,-1,
            1,1,-1,-1,-1,
            1,1,1,-1,1,
            1,-1,-1,1,-1}),
    K_3("K_3", new int[]{ 1,-1,1,1,1,
            1,-1,1,-1,-1,
            1,-1,1,-1,-1,
            1,-1,1,-1,1,
            1,1,-1,1,-1});
    private String name;
    private int[] representation;
    Constants(String s,int[] rep) {
        name = s;
        representation = rep;
    }

    public String getName() {
        return name;
    }

    public int[] getRepresentation() {
        return representation;
    }

    public Constants getByLetter(char a){
        switch (Character.toUpperCase(a)){
            case 'T':{
                return Constants.T;
            }
            case 'V':{
                return Constants.V;
            }
            case 'C':{
                return Constants.C;
            }
            case 'P':{
                return Constants.P;
            }
            case 'N':{
                return Constants.N;
            }
            case 'H':{
                return Constants.H;
            }
            case 'K':{
                return Constants.K;
            }
        }
        return null;
    }
    public Constants getByLetterAndLevel(char a,int level){
        switch (Character.toUpperCase(a)){
            case 'T':{
                if(level == 1)
                   return Constants.T_1;
                if(level == 2)
                    return Constants.T_2;
                if(level == 3)
                    return Constants.T_3;
                return null;
            }
            case 'V':{
                if(level == 1)
                    return Constants.V_1;
                if(level == 2)
                    return Constants.V_2;
                if(level == 3)
                    return Constants.V_3;
                return null;
            }
            case 'C':{
                if(level == 1)
                    return Constants.C_1;
                if(level == 2)
                    return Constants.C_2;
                if(level == 3)
                    return Constants.C_3;
                return null;
            }
            case 'P':{
                if(level == 1)
                    return Constants.P_1;
                if(level == 2)
                    return Constants.P_2;
                if(level == 3)
                    return Constants.P_3;
                return null;
            }
            case 'N':{
                if(level == 1)
                    return Constants.N_1;
                if(level == 2)
                    return Constants.N_2;
                if(level == 3)
                    return Constants.N_3;
                return null;
            }
            case 'H':{
                if(level == 1)
                    return Constants.H_1;
                if(level == 2)
                    return Constants.H_2;
                if(level == 3)
                    return Constants.H_3;
                return null;
            }
            case 'K':{
                if(level == 1)
                    return Constants.K_1;
                if(level == 2)
                    return Constants.K_2;
                if(level == 3)
                    return Constants.K_3;
                return null;
            }
        }
        return null;
    }
}
