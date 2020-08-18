package game;

public class Constants {
    public static final int SCALE = 30;
    public static final String medium =     "      ###      \n" +
                                "      #.#      \n" +
                                "  #####.#####  \n" +
                                " ##         ## \n" +
                                "##  # # # #  ##\n" +
                                "#  ##     ##  #\n" +
                                "# ##  # #  ## #\n" +
                                "#     $@$     #\n" +
                                "####  ###  ####\n" +
                                "   #### ####   \n";

    public static final String easy = "  #### \n"+
                                      " ##  # \n"+
                                      "##   ##\n"+
                                      "# .$. #\n"+
                                      "# #$# #\n"+
                                      "#  @  #\n"+
                                      "#######\n";
    public static final String hard =
                    "  #######\n" +
                    "  #  .  #\n" +
                    " ###$#  #\n" +
                    " #  . . #\n" +
                    "###$#$ ##\n" +
                    "#  . . .#\n" +
                    "# $#$#$ #\n" +
                    "#  @ #  #\n" +
                    "#########\n";

    public enum Direction { down, up, left, right };
    public enum GameStatus { unfinished, won, lost };
}
