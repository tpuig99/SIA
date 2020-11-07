import models.Font;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Font[] fonts = new Font[Constants.font1.length];

        for(int i = 0; i < fonts.length ; i++) {
            fonts[i] = new Font(Constants.font1[i], 7, 5);
            System.out.println();
            System.out.println(fonts[i]);
            System.out.println("/////////////////////");
        }
    }
}
