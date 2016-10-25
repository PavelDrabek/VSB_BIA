package sample;


import algorithms.BlindSearch;
import data.Element;
import functions.*;
import functions.IFunction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.jzy3d.chart.AWTChart;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.primitives.pickable.PickablePoint;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ImageView imageView;
    public ComboBox comboFunction;
    public TextField minX, maxX, precX, minY, maxY, precY;
    public TextField customEl, dim, popSize;
    public CheckBox checkDiscrete;
    public Label lFitness;

    AWTChart chart;
    Shape surface, pointsShape;
    Range xRange, yRange;
    int stepsX, stepsY;
    IFunction selectedFunction;
    Generation generation;
    BlindSearch search;

    public Controller(AWTChart chart) {
        this.chart = chart;
        pointsShape = new Shape();

        xRange = new Range(0,0);
        yRange = new Range(0,0);
        stepsX = stepsY = 80;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboFunction.getItems().addAll(
                DeJong1.class.getSimpleName(),
                DeJong2.class.getSimpleName(),
                DeJong3.class.getSimpleName(),
                DeJong4.class.getSimpleName(),
                Rastrigin.class.getSimpleName(),
                Schwefel.class.getSimpleName(),
                Griewangk.class.getSimpleName(),
                SinEvelope.class.getSimpleName(),
                StretchedVSineWave.class.getSimpleName(),
                Ackey1.class.getSimpleName(),
                Ackey2.class.getSimpleName(),
                OptimizationFunction.class.getSimpleName());

        comboFunction.setValue(comboFunction.getItems().get(0));

        handleButtonActionShow(null);
//        getDemoChart(chart, new DeJong1(), new Range(-3, 3), new Range(-3, 3));
//        getDemoChart(chart, new Ackey2(), new Range(0, 3), new Range(-3, 3));
    }

    public void RefreshChart() {
        getDemoChart(chart, selectedFunction, xRange, yRange, stepsX, stepsY);
    }

    private void GetRange() {
        xRange.setMin(Float.parseFloat(minX.getText()));
        xRange.setMax(Float.parseFloat(maxX.getText()));
        yRange.setMin(Float.parseFloat(minY.getText()));
        yRange.setMax(Float.parseFloat(maxY.getText()));
        stepsX = Integer.parseInt(precX.getText());
        stepsY = Integer.parseInt(precY.getText());
    }

    private void SetRange() {
        minX.setText(String.valueOf(xRange.getMin()));
        maxX.setText(String.valueOf(xRange.getMax()));
        minY.setText(String.valueOf(yRange.getMin()));
        maxY.setText(String.valueOf(yRange.getMax()));
        precX.setText(String.valueOf(stepsX));
        precY.setText(String.valueOf(stepsY));
    }

    @FXML
    protected void handleButtonActionShow(ActionEvent event) {
//        System.out.print("function: " + (String)comboFunction.getValue());
        selectedFunction = GetFunction((String)comboFunction.getValue());
        handleButtonActionReset(null);
    }

    @FXML
    protected void handleButtonActionSet(ActionEvent event) {
        GetRange();
        RefreshChart();
    }

    @FXML
    protected void handleButtonActionReset(ActionEvent event) {
        xRange = new Range(-selectedFunction.getLimit(), selectedFunction.getLimit());
        yRange = new Range(-selectedFunction.getLimit(), selectedFunction.getLimit());
        SetRange();
        RefreshChart();
    }

    @FXML
    protected void handleButtonActionCalcFitness(ActionEvent event) {
        String[] split = customEl.getText().split(";");
        float[] values = new float[split.length];
        for (int i = 0; i < split.length; i++) {
            values[i] = Float.valueOf(split[i]);
        }
        lFitness.setText("Fitness = " + selectedFunction.getValue(values.length, values));
    }

    @FXML
    protected void handleButtonActionGeneratePop(ActionEvent event) {

        if(checkDiscrete.isSelected()) {
            stepsX = (int)(xRange.getRange() + 1);
            stepsY = (int)(yRange.getRange() + 1);
            RefreshChart();
        }

        GeneratePopulation();
        search = new BlindSearch(selectedFunction, generation.D, generation);
        ShowGeneration(generation, search.GetBest());
    }

    @FXML
    protected void handleButtonActionNextStep(ActionEvent event) {
        NextGeneration();
    }

    private void GeneratePopulation() {
        int D = Integer.parseInt(dim.getText());
        int pop = Integer.parseInt(popSize.getText());
        Range[] limits = new Range[D];

        if(D == 2) {
            limits[0] = xRange;
            limits[1] = yRange;
        } else {
            for (int i = 0; i < D; i++) {
                limits[i] = xRange;
            }
        }

        generation = new Generation(D, pop, selectedFunction, limits);
        generation.isDiscrete = checkDiscrete.isSelected();
        generation.GenerateFirst();
        generation.FixPopulation();
//        generation.CalcFitness();
    }

    private IFunction GetFunction(String name) {
        if(name.equals(DeJong1.class.getSimpleName())) {
            return new DeJong1();
        } else if(name.equals(DeJong2.class.getSimpleName())) {
            return new DeJong2();
        } else if(name.equals(DeJong3.class.getSimpleName())) {
            return new DeJong3();
        } else if(name.equals(DeJong4.class.getSimpleName())) {
            return new DeJong4();
        } else if(name.equals(Rastrigin.class.getSimpleName())) {
            return new Rastrigin();
        } else if(name.equals(Schwefel.class.getSimpleName())) {
            return new Schwefel();
        } else if(name.equals(Griewangk.class.getSimpleName())) {
            return new Griewangk();
        } else if(name.equals(SinEvelope.class.getSimpleName())) {
            return new SinEvelope();
        } else if(name.equals(StretchedVSineWave.class.getSimpleName())) {
            return new StretchedVSineWave();
        } else if(name.equals(Ackey1.class.getSimpleName())) {
            return new Ackey1();
        } else if(name.equals(Ackey2.class.getSimpleName())) {
            return new Ackey2();
        } else if(name.equals(OptimizationFunction.class.getSimpleName())) {
            return new OptimizationFunction();
        } else {
            return new DeJong1();
        }
    }

    private void ShowGeneration(Generation generation, Element best) {
        chart.getScene().getGraph().remove(pointsShape);
        pointsShape.clear();
        for (int i = 0; i < generation.popSize; i++) {
            Element e = generation.population[i];
            AddElementToShape(pointsShape, e, Color.BLACK, 5);
        }
        if(best != null) {
            AddElementToShape(pointsShape, best, Color.RED, 10);
        }
        chart.getScene().getGraph().add(pointsShape);
    }

    private void AddElementToShape(Shape shape, Element e, Color color, float radius) {
        Coord3d coord = new Coord3d(e.params[0], e.params[1], e.GetFitness());
        PickablePoint point = new PickablePoint(coord, color, radius);
        pointsShape.add(point);
    }

    private void NextGeneration() {
        search.Next();
        ShowGeneration(search.gen, search.GetBest());
    }

    private AWTChart getDemoChart(AWTChart chart, IFunction function, Range xRange, Range yRange, int precX, int precY) {
        // Define a function to plot
        Mapper mapper = new FunctionMapper(function);

        // Create the object to represent the function over the given range.
        OrthonormalGrid grid = new OrthonormalGrid(xRange, precX, yRange, precY);

        chart.getScene().getGraph().remove(surface);
        surface = Builder.buildOrthonormal(grid, mapper);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .6f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(true);
//        surface.setBoundingBoxColor(Color.BLACK);
        surface.setWireframeWidth(1);

        chart.getScene().getGraph().add(surface);

        return chart;
    }
}
