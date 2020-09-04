import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GeneticGraphs {
    double[] generations;
    double[] bestPerGeneration;

    public GeneticGraphs(double[] generations, double[] bestPerGeneration) {
        this.generations = generations;
        this.bestPerGeneration = bestPerGeneration;
    }

    public void showGraphic() {
        double [][] data = new double[2][];

        data[0] = generations;
        data[1] = bestPerGeneration;

        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("fitness", data);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.GREEN);
        renderer.setSeriesStroke(0, new BasicStroke(2));

        JFreeChart chart = ChartFactory.createXYLineChart("Generation Evolution", "Generations", "Fitness", dataset);
        chart.getXYPlot().getRangeAxis().setRange(0, 100);
        chart.getXYPlot().setRenderer(renderer);

        BufferedImage image = chart.createBufferedImage(800, 600);
        try {
            ImageIO.write(image, "png", new File("fitnessGraphic.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
