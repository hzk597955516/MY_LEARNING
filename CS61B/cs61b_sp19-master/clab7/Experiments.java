import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        BST<Integer> test = new BST<>();
        List<Integer> xvalues = new ArrayList<>();
        List<Double> yvalues = new ArrayList<>();
        for (int i = 1; i <= 5000; i++){
            ExperimentHelper.insertrandomly(test);
            xvalues.add(i);
            yvalues.add(test.AverageDepth());
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("The test", xvalues, yvalues);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
        BST<Integer> test = new BST<>();
        List<Integer> xvalues = new ArrayList<>();
        List<Double> yvalues = new ArrayList<>();
        for (int i = 1; i <= 1000; i++){
            ExperimentHelper.insertrandomly(test);
        }
        xvalues.add(1);
        yvalues.add(test.AverageDepth());

        for (int j = 1; j <= 1000; j++){
            ExperimentHelper.deleterandomly(test);
            ExperimentHelper.insertrandomly(test);
            xvalues.add(j);
            yvalues.add(test.AverageDepth());
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("The test 2", xvalues, yvalues);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment3() {
        BST<Integer> test = new BST<>();
        List<Integer> xvalues = new ArrayList<>();
        List<Double> yvalues = new ArrayList<>();
        for (int i = 1; i <= 500; i++){
            ExperimentHelper.insertrandomly(test);
        }
        xvalues.add(1);
        double first = test.AverageDepth();
        yvalues.add(first);

        for (int j = 1; j <= 200000; j++){
            test.deleteTakingRandom(test.getRandomKey());
            ExperimentHelper.insertrandomly(test);
            xvalues.add(j);
            yvalues.add(test.AverageDepth());
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("The test 2", xvalues, yvalues);

        new SwingWrapper(chart).displayChart();
        System.out.println((double)test.AverageDepth() / first);
    }

    public static void main(String[] args) {
        experiment3();
    }
}
