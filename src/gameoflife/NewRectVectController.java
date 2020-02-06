/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import static javafx.application.ConditionalFeature.FXML;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.MatrixSize;
import model.RectangleVector;

/**
 * FXML Controller class
 *
 * @author ferenc
 */
public class NewRectVectController implements Initializable {

    
    private MatrixSize matrixSize;
    private Stage stage;
    int c = 0;
    int r = 0;
    int s = 0;

    @FXML
    private javafx.scene.control.Button cancel;
    @FXML
    private TextField noOfRows;
    @FXML
    private TextField noOfCols;
    @FXML
    private TextField cellSize;

    public NewRectVectController() {

    }

    @FXML
    private void okPressed() {

        try {
            c = Integer.parseInt(noOfCols.getText());
            r = Integer.parseInt(noOfRows.getText());
            s = Integer.parseInt(cellSize.getText());

            if (c <= 0 || r <= 0 || s <= 0) {
                c = 1;
                r = 1;
                s = 1;

                ButtonType ok = new ButtonType("Understood", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Only positive whole numbers can be set!" + "\n" + "All parameters set to 1.", ok);

                alert.setTitle("Incorrect parameters.");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.equals(ok)) {

                }

            }

            matrixSize = new MatrixSize();
            matrixSize.setNoOfCols(c);
            matrixSize.setNoOfRows(r);
            matrixSize.setSizeOfRect(s);

            cancelPressed();

        } catch (NumberFormatException ex) {

            ButtonType ok = new ButtonType("Understood", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Only positive whole numbers can be set!", ok);

            alert.setTitle("Wrong number format");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.equals(ok)) {

            }

        }

    }

    @FXML
    private void cancelPressed() {
        stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public MatrixSize getMatrixSize() {
        return matrixSize;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
