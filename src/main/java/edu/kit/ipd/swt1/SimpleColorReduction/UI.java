package edu.kit.ipd.swt1.SimpleColorReduction;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;

/**
 * Created by Sebastian Schindler on 05.06.2014.
 */
public class UI extends Application {
    private String inputFile;
    private String outputFile;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("SimpleColorReduction");
        AnchorPane root = new AnchorPane();

        AnchorPane source = new AnchorPane();
        AnchorPane file = new AnchorPane();


        final TextField sourcePath = new TextField();
        sourcePath.setText(inputFile);



        Button chooseSource = new Button();
        chooseSource.setText("...");
        chooseSource.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser chooser = new FileChooser();
                chooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("PNG Images", "*.png")
                );
                File source = chooser.showOpenDialog(primaryStage);
                if (source != null) {
                    inputFile = source.toString();
                    sourcePath.setText(inputFile);
                }
            }
        });


        file.setRightAnchor(chooseSource, 5.0);
        file.getChildren().add(chooseSource);

        source.getChildren().add(file);


        AnchorPane target = new AnchorPane();

        Button targetButton = new Button();
        targetButton.setText("...");
        targetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser chooser = new FileChooser();
                chooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("PNG Images", "*.png")
                );
                File target = chooser.showSaveDialog(primaryStage);
                if (target != null) {
                    outputFile = target.toString();
                }
            }
        });


        target.getChildren().add(targetButton);


        BorderPane slider = new BorderPane();


        final Slider depthSlider = new Slider();
        depthSlider.setMin(3.0);
        depthSlider.setMax(24.0);
        depthSlider.setValue(3.0);
        depthSlider.setBlockIncrement(3.0);
        depthSlider.setMajorTickUnit(3.0);
        depthSlider.setShowTickMarks(true);
        depthSlider.setShowTickLabels(true);
        depthSlider.setBlockIncrement(3.0);
        depthSlider.setMinorTickCount(0);
        depthSlider.setSnapToTicks(true);

        slider.setCenter(depthSlider);

        BorderPane startButton = new BorderPane();
        Button startGen = new Button();
        startGen.setText("Starte Reduzierung");
        startGen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.genStrings(inputFile, outputFile, (int) depthSlider.getValue());
            }
        });
        startButton.setCenter(startGen);


        root.setLeftAnchor(source, 10.0);
        root.setTopAnchor(source, 10.0);

        root.setRightAnchor(target, 10.0);
        root.setTopAnchor(target, 10.0);

        root.setLeftAnchor(slider, 10.0);
        root.setRightAnchor(slider, 10.0);
        root.setBottomAnchor(slider, 100.0);

        root.setBottomAnchor(startButton, 30.0);
        root.setLeftAnchor(startButton, 5.0);
        root.setRightAnchor(startButton, 5.0);

        root.getChildren().add(source);
        root.getChildren().add(target);
        root.getChildren().add(slider);
        root.getChildren().add(startButton);

        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }
}
