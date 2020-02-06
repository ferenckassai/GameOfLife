/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;


import controller.RunLife;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.RectangleVector;
import model.Cell;
import model.MatrixSize;
import model.ToBeSaved;

/**
 *
 * @author ferenc
 */
public class GameOfLifeFX extends Application {

    private Thread simulation;
    private RectangleVector matrix;
    private MatrixSize matrixSize = new MatrixSize(20, 80, 30);
    private boolean simulationIsRunning;
    private GridPane gridpane;
    private ScrollPane pane;
    private Button run;
    private Button stop;
    private Label draw;

    @Override
    public void start(Stage primaryStage) {

        /*
        ami hiányzik még:
        
        
        3.) látszódjon, hogy meddig tart a grid
        
        4.) képernyőméretnek megfelelő legyen a grid
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.getHeight();
        screenSize.getWidth();
        
        5.) szebb ikon, nagyobb felbontásban, felirat nem kell
        
         */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        System.out.println(screenSize.getHeight());
//        System.out.println(screenSize.getWidth());
        
        simulationIsRunning = false;

        Group root = new Group();

        // MENU
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");

        final MenuItem newM = new MenuItem("New");
        fileMenu.getItems().add(newM);
        newM.setOnAction(e -> newRectangleVector());

        final MenuItem save = new MenuItem("Save");
        fileMenu.getItems().add(save);
        save.setOnAction(e -> saveMatrix(primaryStage));

        final MenuItem open = new MenuItem("Open");
        fileMenu.getItems().add(open);
        open.setOnAction(e -> openMatrix(primaryStage));

        fileMenu.getItems().add(new SeparatorMenuItem());

        final MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().add(exit);
        exit.setOnAction(e -> exit());

        menuBar.getMenus().add(fileMenu);

        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

        //BUTTONS RUN AND STOP
        run = new Button("Run");
        stop = new Button("Stop");

        run.setLayoutX(730);
        stop.setLayoutX(800);

        run.setStyle("-fx-background-color: Chartreuse");

        run.setOnMouseClicked(e -> startSimulation());
        stop.setOnMouseClicked(e -> stopSimulation());

        draw = new Label("Press \"D\" to draw!");
        draw.setLayoutX(500);
        draw.setLayoutY(6);
        draw.setVisible(true);

        draw.setTextFill(Color.CRIMSON);

        //SCENE
        Scene scene = new Scene(root,300,250);
        scene.setFill(Paint.valueOf("white"));

        //GRIDPANE
        gridpane = new GridPane();

        // CONSTRUCTION OF THE MATRIX
        matrix = new RectangleVector(matrixSize);

        setGridPane(gridpane, matrix);

        AnchorPane ap = new AnchorPane();

        pane = new ScrollPane();

        pane.setContent(gridpane);

        pane.getViewportBounds().contains(100, 100);

        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        pane.hbarPolicyProperty();

        pane.setFitToHeight(true);
        pane.setFitToWidth(true);
        pane.setPannable(true);

        // ha ez nincs, akkor nem scrollolhato, hanem akkora lesz mint a gridpane
        // de viszont nagy képernyőn is ekkora mard
        pane.setPrefSize(1365, 670);
        pane.setPrefSize(screenSize.getWidth()-1, screenSize.getHeight()-98);

        pane.setStyle("-fx-background-color: white;");

        ap.getChildren().add(pane);
        AnchorPane.setTopAnchor(pane, 30.0);
        root.getChildren().add(ap);
        root.getChildren().add(menuBar);
        root.getChildren().add(run);
        root.getChildren().add(stop);
        root.getChildren().add(draw);

        //space pressed for drawing
        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.D) {
                for (int i = 0; i < matrix.getMatrix().length; i++) {

                    for (int j = 0; j < matrix.getMatrix()[i].length; j++) {

                        matrix.getMatrix()[i][j].firstElement().setMousePressed(true);

                    }
                }
                if (!simulationIsRunning) {
                    draw.setVisible(false);
                }

            }
        });

        //space released to stopp drawing
        root.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.D) {
                for (int i = 0; i < matrix.getMatrix().length; i++) {

                    for (int j = 0; j < matrix.getMatrix()[i].length; j++) {

                        matrix.getMatrix()[i][j].firstElement().setMousePressed(false);

                    }
                }
                if (!simulationIsRunning) {
                    draw.setVisible(true);
                }
            }
        });

        primaryStage.setTitle("Game of Life Cellular Automat");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.getIcons().add(new Image("/images/GoL.png", 16, 16, true, true));
        primaryStage.show();
    }

    public void setGridPane(GridPane gridPane, RectangleVector matrix) {

        for (int i = 0; i < matrix.getMatrix().length; i++) {

            for (int j = 0; j < matrix.getMatrix()[i].length; j++) {

                Cell cell = matrix.getMatrix()[i][j].firstElement();

                GridPane.setConstraints(cell.getRectangle(), i, j);
                gridpane.getChildren().addAll(cell.getRectangle());

            }

        }
    }

    public void exit() {

        if (simulationIsRunning) {

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.ERROR, "Do you really want to quit?", yes, no);
            alert.setTitle("Simulation is still running.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no) == yes) {
                System.exit(0);
            }

        } else {
            System.exit(0);

        }
    }

    public void ifRunning() {

        // checks whether simulation is running and gives an alert
        if (simulationIsRunning) {

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.ERROR, "Do you want to stop?", yes, no);
            alert.setTitle("Simulation is still running.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(no) == yes) {
                stopSimulation();
            }
        }

    }

    public void newRectangleVector() {

        ifRunning();

        if (!simulationIsRunning) { // if simulation is not stopped

            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gameoflife/NewRectVect.fxml"));

                Parent root = (Parent) loader.load();

                NewRectVectController newRectVectCont = loader.getController();
                loader.setController(newRectVectCont);

                stage.setTitle("Setting new matrix");

                stage.setScene(new Scene(root));
                stage.showAndWait();

                if (newRectVectCont.getMatrixSize() != null) {
                    matrixSize = newRectVectCont.getMatrixSize();
                    emptyGridpane();

                    RectangleVector matrix2 = new RectangleVector(matrixSize);

                    matrix = matrix2;
                    setGridPane(gridpane, matrix);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void emptyGridpane() {
        // eleiminates cells from gridpane
        for (int i = 0; i < matrix.getMatrix().length; i++) {

            for (int j = 0; j < matrix.getMatrix()[i].length; j++) {

                Cell cell = matrix.getMatrix()[i][j].firstElement();
                GridPane.setConstraints(cell.getRectangle(), i, j);
                gridpane.getChildren().removeAll(cell.getRectangle());

            }

        }

    }

    public void startSimulation() {
        // blocking access to cells
        if (!simulationIsRunning) {
            for (int i = 0; i < matrix.getMatrix().length; i++) {

                for (int j = 0; j < matrix.getMatrix()[i].length; j++) {
                    matrix.getMatrix()[i][j].firstElement().setOnMouseClickBlocked(true);
                }
            }
            simulationIsRunning = true;

            // running controller
            RunLife runlife = new RunLife(matrix);
            simulation = new Thread(runlife);
            simulation.setDaemon(true);
            simulation.start();
            run.setStyle("-fx-background-color: Lightgray");
            draw.setVisible(false);
            stop.setStyle("-fx-background-color: Chartreuse");
        }
    }

    public void stopSimulation() {
        if (simulation != null) {
            simulationIsRunning = false;
            simulation.interrupt();
            // setting back the initial state (cell clicking, draw label etc.)
            for (int i = 0; i < matrix.getMatrix().length; i++) {

                for (int j = 0; j < matrix.getMatrix()[i].length; j++) {
                    matrix.getMatrix()[i][j].firstElement().setOnMouseClickBlocked(false);
                    matrix.getMatrix()[i][j].firstElement().setBornNextRound(false);
                    matrix.getMatrix()[i][j].firstElement().setDeathNextRound(false);
                }
            }

            run.setStyle("-fx-background-color: Chartreuse");
            draw.setVisible(true);
            stop.setStyle("-fx-background-color: Lightgray");

        }
    }

    public void saveMatrix(Stage stage) {
        ifRunning();

        if (!simulationIsRunning) { // if simulation is not stopped

            ToBeSaved toBeSaved = new ToBeSaved(matrix);
            FileOutputStream fos = null;
            File file = null;
            try {
                FileChooser fcSave = new FileChooser();
                FileChooser.ExtensionFilter golFilter
                        = new FileChooser.ExtensionFilter("Game of life files (*.gol)", "*.gol");
                fcSave.getExtensionFilters().add(golFilter);

                fcSave.setTitle("Saving the matrix");
                file = fcSave.showSaveDialog(stage);

                if (file != null) {
                    fos = new FileOutputStream(file);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(toBeSaved);
                    oos.close();
                }

            } catch (FileNotFoundException ex) {
                saveFailed(); //method for displaying error message
            } catch (IOException ex) {
                saveFailed();
                ex.printStackTrace();

            } finally {
                try {
                    if (file != null) {
                        fos.close();
                    }
                } catch (IOException ex) {
                    saveFailed();
                }
            }

        }
    }

    public void openMatrix(Stage stage) {

        ifRunning();

        if (!simulationIsRunning) { // if simulation is not stopped

            FileChooser fcOpen = new FileChooser();
            FileChooser.ExtensionFilter golFilter
                    = new FileChooser.ExtensionFilter("Game of life files (*.gol)", "*.gol");
            fcOpen.getExtensionFilters().add(golFilter);
            fcOpen.setTitle("Opening a matrix");
            File file = fcOpen.showOpenDialog(stage);
            ToBeSaved toBeSaved = new ToBeSaved();

            if (file != null) {

                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    toBeSaved = (ToBeSaved) ois.readObject();
                    ois.close();

                } catch (FileNotFoundException ex) {
                    openFailed(); //method for displaying error message
                } catch (IOException ex) {
                    openFailed();
                } catch (ClassNotFoundException ex) {
                    openFailed();
                } finally {
                    try {
                        fis.close();
                    } catch (IOException ex) {
                        openFailed();
                    }
                }
// setting up grid pane wiht the new matrix
                emptyGridpane();

                RectangleVector newMatrix = new RectangleVector(toBeSaved.getMatrixSize());

                for (int i = 0; i < newMatrix.getMatrix().length; i++) {

                    for (int j = 0; j < newMatrix.getMatrix()[i].length; j++) {

                        if (toBeSaved.getCellColours()[i][j]) {
                            matrix.getMatrix()[i][j].firstElement().getRectangle().setFill(Color.BLACK);
                        }

                    }

                    matrix = newMatrix;
                    emptyGridpane();
                    setGridPane(gridpane, matrix);
                }

            }
        }
    }

    public void saveFailed() {
        ButtonType ok = new ButtonType("Roger that.", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.ERROR, "File could not be saved.", ok);

        alert.setTitle("Error during saving file");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.equals(ok)) {

        }
    }

    public void openFailed() {

        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.ERROR, "File was not found.", ok);

        alert.setTitle("Error during opening");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.equals(ok)) {

        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
