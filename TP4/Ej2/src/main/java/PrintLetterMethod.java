public class PrintLetterMethod {
    void printLetter(int[] patron,int size){
        for (int i = 0; i < patron.length; i++) {
            if(i!=0 && i%size==0){
                System.out.print("\n");
            }
            char aux = patron[i] == 1 ? '*':' ';
            System.out.print(aux);
        }
        System.out.println();
        System.out.println();
    }
}
