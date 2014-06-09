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
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


/**
 * Created by Sebastian Schindler on 07.06.2014.
 * Draws an UI to set source and target Files and target bitdepth
 */
public class UI extends Application {
    private Stage primStage;
    private String sourcePath;
    private String targetPath;

    private ImageView sourceView = new ImageView();
    private ImageView targetView = new ImageView();
    private double targetDepth = 3.0;

    /**
     * Main methods
     * Starts the UI
     * @param args arguments from commandline
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Sets the scene of the application
     * @param primaryStage primary Stage
     */
    @Override
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
        sourceField.setMinWidth(170);
        sourceField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (checkPath(sourceField.getText(), true) && isPNG(sourceField.getText())) {
                    sourcePath = sourceField.getText();
                    setSourccePreview();
                } else {
                    showWarning("Datei nicht gefunden");
                }
            }
        });

        Button chooseSource = new Button("...");
        chooseSource.setMaxWidth(25);
        chooseSource.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser chooser = new FileChooser();
                chooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("PNG Images", "*.png")
                );
                File source = chooser.showOpenDialog(primStage);
                if (source != null) {
                    sourcePath = source.toString();
                    sourceField.setText(sourcePath);
                    setSourccePreview();
                }
            }
        });
        file1.getChildren().addAll(sourceField, chooseSource);

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
        targetField.setMinWidth(170);
        targetField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (checkPath(targetField.getText(), false) && isPNG(targetField.getText())) {
                    targetPath = targetField.getText();
                } else {
                    showWarning("Ungültiger Dateiname");
                }
            }
        });

        Button chooseTarget = new Button("...");
        chooseTarget.setMaxWidth(25);
        chooseTarget.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser chooser = new FileChooser();
                chooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("PNG Images", "*.png")
                );
                File target = chooser.showSaveDialog(primStage);
                if (target != null) {
                    targetPath = target.toString();
                    targetField.setText(targetPath);
                }
            }
        });
        file2.getChildren().addAll(targetField, chooseTarget);

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
        //slider.setPadding(new Insets(5, 0, 0, 0));
        slider.setLeftAnchor(label, 10.0);
        slider.setTopAnchor(rectangle, 9.0);
        slider.setTopAnchor(depthSlider, 22.0);
        slider.setLeftAnchor(depthSlider, 7.0);
        slider.setRightAnchor(depthSlider, 7.0);
        slider.getChildren().addAll(rectangle, label, depthSlider);


        bottom.setTop(slider);

        BorderPane startButton = new BorderPane();
        Button startGen = new Button();
        startGen.setText("Starte Reduzierung");
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
                Main.genStrings(sourcePath, targetPath, (int) depthSlider.getValue());
                showWarning("Bild erfolgreich generiert!");
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
            if (test.exists()) {
                return true;
            } else return false;
        } else {
            File filePath = test.getParentFile();
            if (filePath.exists()) {
                return true;
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

    /**
     * Simply checks if the file extension is "png" or "PNG"
     * @param path Path to the file
     * @return True, if file is a png
     */
    public boolean isPNG(String path) {
        int i = path.length() - 3;
        if (path.substring(i).equals("png") || path.substring(i).equals("PNG")) {
            return true;
        }
        return false;
    }
}
