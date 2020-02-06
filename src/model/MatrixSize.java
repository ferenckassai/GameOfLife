/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author ferenc
 */
public class MatrixSize implements Serializable{
    private int sizeOfRect;
    private int noOfRows;
    private int noOfCols;

    public MatrixSize() {
    }

    public MatrixSize(int sizeOfRect, int noOfRows, int noOfCols) {
        this.sizeOfRect = sizeOfRect;
        this.noOfRows = noOfRows;
        this.noOfCols = noOfCols;
    }

    public int getNoOfCols() {
        return noOfCols;
    }

    public void setNoOfCols(int noOfCols) {
        this.noOfCols = noOfCols;
    }

    public int getSizeOfRect() {
        return sizeOfRect;
    }

    public void setSizeOfRect(int sizeOfRect) {
        this.sizeOfRect = sizeOfRect;
    }

    public int getNoOfRows() {
        return noOfRows;
    }

    public void setNoOfRows(int noOfRows) {
        this.noOfRows = noOfRows;
    }
    
    
    
       
    
}
