package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Visualizer extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Collision Chart");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Hash");
        yAxis.setLabel("Number of collisions");
        final LineChart<Number, Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Collision Chart");

        Scene scene = new Scene(lineChart, 800, 600);

        /*
            Replace 'main.HashFunctions::hashCode' with your own hash function you want to visualize
         */
        lineChart.getData().add(getGraphFromHashFunction(HashFunctions::hashCode3, "HashFunction", 3));

        stage.setScene(scene);
        stage.show();

    }

    private XYChart.Series<Number, Number> getGraphFromHashFunction(Function<String, Integer> function, String name, int maxWordLength) {
        HashFunctionEvaluator<Integer> evaluator = new HashFunctionEvaluator<>();
        Map<Integer, Set<String>> map = evaluator.checkForCollisions(function, maxWordLength);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);

        for (Map.Entry<Integer, Set<String>> entry : map.entrySet().stream().filter(v -> v.getValue().size() > 1).collect(Collectors.toList())) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue().size()));
        }
        return series;
    }

    public static void main(String[] args) {
        launch();
    }

}
