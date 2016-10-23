package sample;

import functions.Ackey2;
import functions.DeJong1;
import functions.IFunction;
import javafx.JavaFXChartFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.jzy3d.chart.*;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
//        Parent root = FXMLLoader .load(getClass().getResource("/sample.fxml"));
        stage.setTitle("BIA Projekt | DRA0042");

        // Jzy3d
        JavaFXChartFactory factory = new JavaFXChartFactory();
        final AWTChart chart = (AWTChart) factory.newChart(Quality.Advanced, "offscreen");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample.fxml"));
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> aClass) {
                return new Controller(chart);
            }
        });
        Pane p = loader.load();
        Controller controller = (Controller) loader.getController();

        // let factory bind mouse and keyboard controllers to JavaFX node
        factory.bindImageView(chart, controller.imageView);

        // JavaFX
        Scene scene = new Scene(p);
        stage.setScene(scene);
        stage.show();

        factory.addSceneSizeChangedListener(chart, scene);

//        controller.handleButtonActionShow(null);

    }


}
