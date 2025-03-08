/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package irrgarten;

import interfaz.Cursor;
import interfaz.GuiUI;

/**
 *
 * @author zapi24
 */
public interface UI {   //LLama a los metodos que toda interfaz deberia tener
    
    public Directions nextMove() ;
     
    public void showGame(GameState gameState);
   
}
