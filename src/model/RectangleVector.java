/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Vector;



/**
 *
 * @author ferenc
 */
public class RectangleVector implements Serializable {

    private static final long serialVersionUID = 1L;
    private int sizeOfRect;
    private int noOfrows;
    private int noOfColumns;
    //For 2 dimensions
    //Vector<Vector<Cell>> matrix;
    //Maybe it can be extended to 3 dimensions later on?
    private Vector<Cell>[][] matrix;
    

    public RectangleVector() {
    }

    public RectangleVector(int sizeOfRect, int noOfrows, int noOfColumns, Vector<Cell>[][] matrix) {
        this.sizeOfRect = sizeOfRect;
        this.noOfrows = noOfrows;
        this.noOfColumns = noOfColumns;
        this.matrix = matrix;
    }
    
    

    public RectangleVector(MatrixSize matrixSize) {
        this.sizeOfRect = matrixSize.getSizeOfRect();
        this.noOfrows = matrixSize.getNoOfRows();
        this.noOfColumns = matrixSize.getNoOfCols();
        
        matrix = new Vector[noOfrows][noOfColumns];
        
        for (int i = 0; i < matrix.length; i++) {
            
            for (int j = 0; j < matrix[i].length; j++) {
                
            matrix [i][j]= new Vector<Cell>();
            matrix[i][j].add(new Cell(sizeOfRect));
                
            }
        }

    }

    public int getSizeOfRect() {
        return sizeOfRect;
    }

    public void setSizeOfRect(int sizeOfRect) {
        this.sizeOfRect = sizeOfRect;
    }

    public int getNoOfrows() {
        return noOfrows;
    }

    public void setNoOfrows(int noOfrows) {
        this.noOfrows = noOfrows;
    }

    public int getNoOfColumns() {
        return noOfColumns;
    }

    public void setNoOfColumns(int noOfColumns) {
        this.noOfColumns = noOfColumns;
    }

    public Vector<Cell>[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(Vector<Cell>[][] matrix) {
        this.matrix = matrix;
    }

}
