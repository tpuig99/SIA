package game;

public class Constants {
    public static final int SCALE = 30;

    public static final String map1 =     "      ###      \n" +
                                "      #.#      \n" +
                                "  #####.#####  \n" +
                                " ##         ## \n" +
                                "##  # # # #  ##\n" +
                                "#  ##     ##  #\n" +
                                "# ##  # #  ## #\n" +
                                "#     $@$     #\n" +
                                "####  ###  ####\n" +
                                "   #### ####   \n";

    public static final String map2 =   "######\n" +
                                        "#@ $.#\n" +
                                        "######\n";

    public enum Direction { down, up, left, right };
    public enum GameStatus { unfinished, won, lost };
}
