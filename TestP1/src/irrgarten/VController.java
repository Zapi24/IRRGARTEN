/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import javax.swing.*; 

/**
 *
 * @author zapi24
 */
public class VController extends JFrame{

    private Game game;
    private UI ui;

    public VController(Game game, UI ui) {
        this.game = game;
        this.ui = ui;
    }

    public void play() {
         
        boolean endOfGame = false;
            
        while (!endOfGame) {
                
            ui.showGame(game.getGameState());
            Directions direction = ui.nextMove(); 
            endOfGame = game.nextStep(direction);
            }
           ui.showGame(game.getGameState());
    }
    
}