/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package irrgarten;



public class Controller{
    
    private Game game;
    private TextUI view;
    
    
    public Controller(Game game, TextUI view) {
        this.game = game;
        this.view = view;
    }
    
    public void play() {
        
       
        
    boolean endOfGame = false;
            
    //Modo de interfaz de texto
            
            
            
    while (!endOfGame) {
        
        view.showGame(game.getGameState()); 
        Directions direction = view.nextMove(); 
        //System.out.println(direction);        //DEPURACION
        endOfGame = game.nextStep(direction);
        }
            
        view.showGame(game.getGameState());    
    }
    
}