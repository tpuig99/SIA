package models;

import org.apache.commons.lang3.Conversion;

import java.util.Arrays;

public class Font {
    private int[][] font;

    public Font(int[] font, int height, int width) {
        this.font = new int[height][width];
        for (int i = 0; i < height; i++) {
            boolean[] bits = new boolean[5];
            for (int k = 4; k >= 0; k--) {
                bits[k] = (font[i] & (1 << k)) != 0;
            }

            for (int j = 0; j < width; j++) {
                this.font[i][j] = bits[j] ? 1 : 0;
            }
        }
    }

    public int[][] getFont() {
        return font;
    }

    public void setFont(int[][] font) {
        this.font = font;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] f : font) {
            for (int j : f) {
                stringBuilder.append(j == 1 ? '*' : ' ').append(' ');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
