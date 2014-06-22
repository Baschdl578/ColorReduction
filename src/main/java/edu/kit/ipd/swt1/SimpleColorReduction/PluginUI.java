package edu.kit.ipd.swt1.SimpleColorReduction;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.jis.options.Options;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;


/**
 * Created by Sebastian Schindler on 19.06.2014.
 */
public class PluginUI extends Application {
    private Stage primStage;
    private static ImageView sourceView = new ImageView();
    private static ImageView targetView = new ImageView();

    static String sourcePath;
    static String targetPath;
    static double targetDepth;

    /**
     * Main methods
     * Starts the UI
     * @param args arguments from commandline
     */
    public static void main(String[] args) {
        sourcePath = args[0];
        targetPath = args[1];
        launch(args);
    }

    /**
     * Sets the scene of the application
     * @param primaryStage PrimaryStage
     */
    public void start(final Stage primaryStage) {
        primStage = primaryStage;
        primaryStage.setTitle("SimpleColorReduction");
        primaryStage.setMinWidth(458);
        primaryStage.setResizable(false);

        sourceView.setFitWidth(150.0);
        sourceView.setFitHeight(150.0);


        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));

        root.setLeft(topLeft());
        root.setRight(topRight());
        root.setBottom(bottom());

        setSourccePreview();

        primaryStage.setScene(new Scene(root, 500, 370));
        primaryStage.show();
    }

    /**
     * Builds the upper left part of the application
     * @return top left Pane
     */
    public AnchorPane topLeft() {
        Rectangle rectangle = new Rectangle();
        rectangle.autosize();
        rectangle.setWidth(240);
        rectangle.setHeight(240);
        rectangle.setFill(Paint.valueOf(Color.WHITE.toString()));
        rectangle.setStroke(Paint.valueOf(Color.GRAY.toString()));
        rectangle.setStrokeWidth(1.0);
        rectangle.setStrokeType(StrokeType.INSIDE);

        Label label = new Label("Quelldatei:");
        label.setLabelFor(rectangle);
        label.getStylesheets().add("labelstyle.css");

        HBox file1 = new HBox();
        file1.setSpacing(10);

        final TextField sourceField = new TextField();
        sourceField.setMinWidth(220);
        sourceField.setText(sourcePath);
        sourceField.setDisable(true);


        file1.getChildren().add(sourceField);

        StackPane imageView = new StackPane();
        Rectangle rectangle2 = new Rectangle();
        rectangle2.autosize();
        rectangle2.setWidth(152);
        rectangle2.setHeight(152);
        rectangle2.setFill(Paint.valueOf(Color.WHITE.toString()));
        rectangle2.setStroke(Paint.valueOf(Color.GRAY.toString()));
        rectangle2.setStrokeWidth(1.0);
        rectangle2.setStrokeType(StrokeType.INSIDE);
        imageView.setAlignment(Pos.CENTER);
        imageView.getChildren().addAll(rectangle2, sourceView);


        AnchorPane topLeft = new AnchorPane();
        topLeft.setLeftAnchor(label, 5.0);
        topLeft.setTopAnchor(label, 0.0);
        topLeft.setTopAnchor(rectangle, 9.0);
        topLeft.setTopAnchor(file1, 22.0);
        topLeft.setLeftAnchor(file1, 11.0);
        topLeft.setTopAnchor(imageView, 70.0);
        topLeft.setLeftAnchor(imageView, 44.0);
        topLeft.getChildren().addAll(rectangle, label, file1, imageView);

        topLeft.setPrefHeight(250.0);

        return topLeft;
    }

    /**
     * Builds the upper right part of the application
     * @return top right Pane
     */
    public AnchorPane topRight() {
        Rectangle rectangle = new Rectangle();
        rectangle.autosize();
        rectangle.setWidth(240);
        rectangle.setHeight(240);
        rectangle.setFill(Paint.valueOf(Color.WHITE.toString()));
        rectangle.setStroke(Paint.valueOf(Color.GRAY.toString()));
        rectangle.setStrokeWidth(1.0);
        rectangle.setStrokeType(StrokeType.INSIDE);

        Label label = new Label("Zieldatei:");
        label.setLabelFor(rectangle);
        label.getStylesheets().add("labelstyle.css");

        HBox file2 = new HBox();
        file2.setSpacing(10);

        final TextField targetField = new TextField();
        targetField.setMinWidth(220);
        targetField.setText(targetPath);
        targetField.setDisable(true);

        file2.getChildren().add(targetField);

        StackPane imageView = new StackPane();
        Rectangle rectangle2 = new Rectangle();
        rectangle2.autosize();
        rectangle2.setWidth(152);
        rectangle2.setHeight(152);
        rectangle2.setFill(Paint.valueOf(Color.WHITE.toString()));
        rectangle2.setStroke(Paint.valueOf(Color.GRAY.toString()));
        rectangle2.setStrokeWidth(1.0);
        rectangle2.setStrokeType(StrokeType.INSIDE);
        imageView.setAlignment(Pos.CENTER);
        imageView.getChildren().addAll(rectangle2, targetView);


        AnchorPane topRight = new AnchorPane();
        topRight.setLeftAnchor(label, 5.0);
        topRight.setTopAnchor(label, 0.0);
        topRight.setTopAnchor(rectangle, 9.0);
        topRight.setTopAnchor(file2, 22.0);
        topRight.setLeftAnchor(file2, 11.0);
        topRight.setTopAnchor(imageView, 70.0);
        topRight.setLeftAnchor(imageView, 44.0);
        topRight.getChildren().addAll(rectangle, label, file2, imageView);

        topRight.setPrefHeight(250.0);

        return topRight;
    }

    /**
     * Builds the bottom part of the application
     * @return bottom Pane
     */
    public BorderPane bottom() {
        BorderPane bottom = new BorderPane();

        AnchorPane slider = new AnchorPane();

        Rectangle rectangle = new Rectangle();
        rectangle.autosize();
        rectangle.setWidth(490);
        rectangle.setHeight(60);
        rectangle.setFill(Paint.valueOf(Color.WHITE.toString()));
        rectangle.setStroke(Paint.valueOf(Color.GRAY.toString()));
        rectangle.setStrokeWidth(1.0);
        rectangle.setStrokeType(StrokeType.INSIDE);

        Label label = new Label("Farbtiefe wählen:");
        label.setLabelFor(rectangle);
        label.getStylesheets().add("labelstyle.css");


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
        depthSlider.setValue(targetDepth);
        depthSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                targetDepth = number2.doubleValue();
                if (sourcePath != null) {
                    SimpleColorReduction reduct = new SimpleColorReduction();
                    reduct.setDestBitDepth((int) targetDepth);
                    InputStream is;
                    try {
                        BufferedImage src = ImageIO.read(new File(sourcePath));

                        if (src.getWidth() >= 150 || src.getHeight() >= 150) {
                            BufferedImage prevIMG = new BufferedImage(150, 150, src.getType());

                            for (int i = 0; i < 150; i++) {
                                for (int j = 0; j < 150; j++) {
                                    prevIMG.setRGB(i, j, src.getRGB(i, j));
                                }
                            }
                            src = prevIMG;
                        }
                        reduct.setSourceImage(src);
                        reduct.generateImage();
                        BufferedImage temp = reduct.getReducedImage();
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        ImageIO.write(temp, "png", os);
                        is = new ByteArrayInputStream(os.toByteArray());
                        Image targetPreview = new Image(is);
                        targetView.setImage(targetPreview);
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        slider.setLeftAnchor(label, 10.0);
        slider.setTopAnchor(rectangle, 9.0);
        slider.setTopAnchor(depthSlider, 22.0);
        slider.setLeftAnchor(depthSlider, 7.0);
        slider.setRightAnchor(depthSlider, 7.0);
        slider.getChildren().addAll(rectangle, label, depthSlider);


        bottom.setTop(slider);

        BorderPane startButton = new BorderPane();
        Button startGen = new Button();
        startGen.setText("Reduzieren und Verkleinern");
        startGen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (sourcePath == null) {
                    showWarning("Bitte ein Quellbild bestimmen!");
                    return;
                }
                if (targetPath == null) {
                    showWarning("Bitte einen Speicherort bestimmen!");
                    return;
                }
                startGen((int) depthSlider.getValue());
                showWarning("Bild(er) erfolgreich generiert!");
            }
        });
        startButton.setPadding(new Insets(12, 7, 7, 7));
        startButton.setCenter(startGen);
        bottom.setBottom(startButton);

        return bottom;
    }

    /**
     * Shows a warning Dialog
     * @param message Message to display
     */
    public void showWarning(String message) {
        final Stage dialogStage = new Stage();
        dialogStage.setResizable(false);
        dialogStage.initOwner(primStage);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        VBox box = new VBox();
        box.setSpacing(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(7));
        Text text = new Text(message);
        Button okButton = new Button("OK");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dialogStage.close();
            }
        });
        box.getChildren().addAll(text, okButton);
        dialogStage.setScene(new Scene(box));
        dialogStage.show();
    }

    /**
     * Checks if a filepath is valid
     * @param path Filepath
     * @param isExistingFile true, if the file already exists, false if it is to be created
     * @return True, if the Path is valid
     */
    public boolean checkPath(String path, boolean isExistingFile) {
        File test = new File(path);
        if (isExistingFile && !test.isDirectory()) {
            return test.exists();
        } else {
            if (!isExistingFile) {
                return test.exists();
            }
        }
        return false;
    }

    /**
     * Sets the unreduced image for the source and target previews
     */
    public void setSourccePreview() {
        InputStream is;
        try {
            BufferedImage src = ImageIO.read(new File(sourcePath));
            if (src.getWidth() >= 150 || src.getHeight() >= 150) {
                BufferedImage prevIMG = new BufferedImage(150, 150, src.getType());

                for (int i = 0; i < 150; i++) {
                    for (int j = 0; j < 150; j++) {
                        prevIMG.setRGB(i, j, src.getRGB(i, j));
                    }
                }
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(prevIMG, "png", os);
                is = new ByteArrayInputStream(os.toByteArray());
            } else {
                is = new FileInputStream(sourcePath);
            }
            Image sourcePreview = new Image(is);
            sourceView.setImage(sourcePreview);
            targetView.setImage(sourcePreview);
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void startGen(int depth) {
        org.jis.Main main = PluginIntegrator.getMain();
        File[] files = PluginIntegrator.getFiles();
        for (int i = 0; i < files.length; i++) {
            String tempDir = System.getProperty("java.io.tmpdir");
            Options options = Options.getInstance();
            String width = Integer.toString(options.getHmax());
            String height = Integer.toString(options.getVmax());
            String quality = Integer.toString((int) (options.getQuality() * 100));
            String[] args = new String[5];
            args[0] = "input=" + files[i].getPath();
            args[1] = "output=" + tempDir + files[i].getName();
            args[2] = "quality=" + quality;
            args[3] = "hmax=" + width;
            args[4] = "vmax=" + height;

            if (!checkPath(options.getOutput_dir(), false)) {
                new File(options.getOutput_dir()).mkdirs();
            }

            main.main(args);

            String in = tempDir + "\\" + files[i].getName();
            String out = options.getOutput_dir() + "\\" + files[i].getName();
            Main.genStrings(in, out, depth);
        }
    }
}

