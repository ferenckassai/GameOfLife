/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Vector;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 *
 * @author ferenc
 */
public class ToBeSaved implements Serializable {

    private MatrixSize matrixSize;
    private boolean[][] cellColours;

    public ToBeSaved() {
    }

    public ToBeSaved(RectangleVector matrix) {
        matrixSize = new MatrixSize(
                (int)matrix.getMatrix()[0][0].firstElement().getRectangle().getHeight(),
                matrix.getMatrix().length,
                matrix.getMatrix()[0].length);
        
        // saving of cell colours in the array      
              
        cellColours = new boolean [matrixSize.getNoOfRows()][matrixSize.getNoOfCols()];
        
        
         for (int i = 0; i < matrix.getMatrix().length; i++) {

            for (int j = 0; j < matrix.getMatrix()[i].length; j++) {

            if (matrix.getMatrix()[i][j].firstElement().getRectangle().getFill().equals(Color.BLACK)){
                cellColours [i][j] = true;
            }
            else{
                cellColours [i][j] = false;
            }

            }

        }
        
        
    }
    
    
    public MatrixSize getMatrixSize() {
        return matrixSize;
    }

    public void setMatrixSize(MatrixSize matrixSize) {
        this.matrixSize = matrixSize;
    }

    public boolean[][] getCellColours() {
        return cellColours;
    }

    public void setCellColours(boolean[][] cellColours) {
        this.cellColours = cellColours;
    }

}
