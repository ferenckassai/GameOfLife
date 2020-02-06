/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.io.Serializable;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author ferenc
 */
public class Cell implements Serializable {

    private boolean deathNextRound = false;
    private boolean bornNextRound = false;
    private Rectangle rectangle;
    private int size;
    private boolean onMouseClickBlocked = false;
    private boolean mousePressed = false;
   

    public Cell() {
    }

    public Cell(int size) {
        this.size = size;
           rectangle = new Rectangle(size, size);
        rectangle.setFill(Color.WHITE);
        
     
            rectangle.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {                
                                                     
                   if (!onMouseClickBlocked && mousePressed==true){
                      
                    if (rectangle.getFill().equals(Color.BLACK)) {
                        rectangle.setFill(Color.WHITE);
                    } else {
                        rectangle.setFill(Color.BLACK);
                    }
                    }
                }
            
            });
            
            rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {                
                                                     
                   if (!onMouseClickBlocked && mousePressed==false){
                      
                    if (rectangle.getFill().equals(Color.BLACK)) {
                        rectangle.setFill(Color.WHITE);
                    } else {
                        rectangle.setFill(Color.BLACK);
                    }
                    }
                }
            
            });
            
           
            
    }
            

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isDeathNextRound() {
        return deathNextRound;
    }

    public void setDeathNextRound(boolean deathNextRound) {
        this.deathNextRound = deathNextRound;
    }

    public boolean isBornNextRound() {
        return bornNextRound;
    }

    public void setBornNextRound(boolean bornNextRound) {
        this.bornNextRound = bornNextRound;
    }

    public boolean isOnMouseClickBlocked() {
        return onMouseClickBlocked;
    }

    public void setOnMouseClickBlocked(boolean onMouseClickBlocked) {
        this.onMouseClickBlocked = onMouseClickBlocked;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
              
        this.mousePressed = mousePressed;
        
       }

    
}
