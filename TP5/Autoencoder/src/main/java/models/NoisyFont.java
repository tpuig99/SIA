package models;

public class NoisyFont {
    private int[][] matrix;

    public NoisyFont(int[] font, int height, int width, int noiseLevel) {
        double noiseProbability = noiseLevel / 10.0;
        this.matrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            boolean[] bits = new boolean[5];
            for (int k = 4; k >= 0; k--) {
                bits[4-k] = (font[i] & (1 << k)) != 0;
            }

            for (int j = 0; j < width; j++) {
                this.matrix[i][j] = bits[j] ? 1 : 0;
                if (noiseProbability > Math.random()) {
                    this.matrix[i][j] = bits[j] ? 0 : 1;
                }
            }
        }
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int[] getAsArray(){
        int[] toReturn = new int[matrix.length * matrix[0].length];
        int k = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                toReturn[k++] = matrix[i][j];
            }
        }
        return toReturn;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] f : matrix) {
            for (int j : f) {
                stringBuilder.append(j == 1 ? '*' : ' ').append(' ');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
