/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;

/**
 *
 * @author zapi24
 */
public class FuzzyPlayer extends Player{
    

    public FuzzyPlayer(Player p) {
        super(p.getNumero(), p.getIntelligence(), p.getStrength());
    }
    
    @Override //Se mueve de forma distinta que un player 
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        
        
        return Dice.nextStep(direction, validMoves, super.getIntelligence());
    }
    
    @Override
    public float attack(){
        
        return Dice.intensity(super.getStrength())+super.sumWeapons();
    }
    
    protected float defenseEnergy(){
        
        return Dice.intensity(super.getIntelligence())+super.sumShields();
    }
    
    @Override
    public String toString(){
        
        return "Fuzzy " + super.toString();
    }
    
    
    
}
