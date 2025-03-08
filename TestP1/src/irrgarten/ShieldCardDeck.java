/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author zapi24
 */
public class ShieldCardDeck extends CardDeck<Shield>{
    
    public static final int ADD_SHIELDS=5;  //Numero de shiels maximos para añadir
    
    @Override
    protected void addCards(){
        
        for(int i=0 ; i< ADD_SHIELDS ; i++){
            
            super.addCard(new Shield(Dice.shieldPower(),Dice.usesLeft()));
        }
        
    }
}
