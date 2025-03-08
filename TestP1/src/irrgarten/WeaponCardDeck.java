/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author zapi24
 */
public class WeaponCardDeck extends CardDeck<Weapon> {
    
    public static final int ADD_WEAPONS=5;  //Numero de weapons maximos para añadir
    
    @Override
    protected void addCards(){
        
        for(int i=0 ; i< ADD_WEAPONS ; i++){
            
            super.addCard(new Weapon(Dice.weaponPower(),Dice.usesLeft()));
        }
        
    }
}
