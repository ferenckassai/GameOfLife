/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import model.Cell;
import model.RectangleVector;

/**
 *
 * @author ferenc
 */
public class RunLife implements Runnable, Serializable {

    private RectangleVector matrix;

    public RunLife() {
    }

    public RunLife(RectangleVector matrix) {
        this.matrix = matrix;
        
    }

    @Override
    public void run() {
        try {
            
            while (true){
            // mark new state of cells
            for (int i = 0; i < matrix.getMatrix().length; i++) {

                for (int j = 0; j < matrix.getMatrix()[i].length; j++) {

                    int noOfNeigh = numberOfNeighbours(matrix, i, j);

                    // if the cell is alive       
                    if (matrix.getMatrix()[i][j].firstElement().getRectangle().getFill().equals(Color.BLACK)) {

                        if (noOfNeigh > 3 || noOfNeigh < 2) {
                            matrix.getMatrix()[i][j].firstElement().setDeathNextRound(true);
                        }

                    }
                    // if the cell is not alive        
                    if (matrix.getMatrix()[i][j].firstElement().getRectangle().getFill().equals(Color.WHITE)) {

                        if (noOfNeigh == 3) {
                            matrix.getMatrix()[i][j].firstElement().setBornNextRound(true);
                        }

                    }
                }
            }

            // set new state of cells
            for (int i = 0; i < matrix.getMatrix().length; i++) {

                for (int j = 0; j < matrix.getMatrix()[i].length; j++) {

                    if (matrix.getMatrix()[i][j].firstElement().isBornNextRound()) {
                        matrix.getMatrix()[i][j].firstElement().getRectangle().setFill(Color.BLACK);
                    }

                    if (matrix.getMatrix()[i][j].firstElement().isDeathNextRound()) {
                        matrix.getMatrix()[i][j].firstElement().getRectangle().setFill(Color.WHITE);

                    }

                }
            }
            // stop thread for 200 ms

            Thread.sleep(200);
            }
        } catch (InterruptedException ex) {
            
        }
    }

    private int numberOfNeighbours(RectangleVector matrix, int i, int j) {
        int numberOfNeighbours = 0;

        //left-up
        if ((i - 1) > -1 && (j - 1) > -1) {

            if (matrix.getMatrix()[i - 1][j - 1].firstElement().getRectangle().getFill().equals(Color.BLACK)) {
                numberOfNeighbours++;
            }

        }

        //up
        if ((i - 1) > -1) {

            if (matrix.getMatrix()[i - 1][j].firstElement().getRectangle().getFill().equals(Color.BLACK)) {
                numberOfNeighbours++;
            }

        }

        //right-up
        if ((i - 1) > -1 && (j + 1) < matrix.getNoOfColumns()) {

            if (matrix.getMatrix()[i - 1][j + 1].firstElement().getRectangle().getFill().equals(Color.BLACK)) {
                numberOfNeighbours++;
            }

        }

        //right
        if ((j - 1) > -1) {

            if (matrix.getMatrix()[i][j - 1].firstElement().getRectangle().getFill().equals(Color.BLACK)) {
                numberOfNeighbours++;
            }

        }

        //left
        if ((j + 1) < matrix.getNoOfColumns()) {

            if (matrix.getMatrix()[i][j + 1].firstElement().getRectangle().getFill().equals(Color.BLACK)) {
                numberOfNeighbours++;
            }

        }

        //right-down
        if ((i + 1) < matrix.getNoOfrows() && (j - 1) > -1) {

            if (matrix.getMatrix()[i + 1][j - 1].firstElement().getRectangle().getFill().equals(Color.BLACK)) {
                numberOfNeighbours++;
            }

        }

        //down
        if ((i + 1) < matrix.getNoOfrows()) {

            if (matrix.getMatrix()[i + 1][j].firstElement().getRectangle().getFill().equals(Color.BLACK)) {
                numberOfNeighbours++;
            }

        }

        //left-down
        if ((i + 1) < matrix.getNoOfrows() && (j + 1) < matrix.getNoOfColumns()) {

            if (matrix.getMatrix()[i + 1][j + 1].firstElement().getRectangle().getFill().equals(Color.BLACK)) {
                numberOfNeighbours++;
            }

        }

        return numberOfNeighbours;
    }

    public RectangleVector getMatrix() {
        return matrix;
    }

    public void setMatrix(RectangleVector matrix) {
        this.matrix = matrix;
    }

}
