/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author zapi24
 */
public class Monster extends LabyrinthCharacter{
    
    private final static int INITIAL_HEALTH=5;
    
    public Monster(String name, float strength, float intelligence){
        
        super(name,intelligence,strength,INITIAL_HEALTH);
    }
   
    @Override
    public float attack(){ 
        
        return Dice.intensity(super.getStrength());
    }
    
    @Override
    public boolean defend(float receivedAttack){ //Deberia tener un override?
        
        boolean isDead=dead(); //FUNCIONA, NO HE PUESTO super.dead()
        
        if(!isDead){
            float defensiveEnergy=Dice.intensity(super.getIntelligence());
            
            if(defensiveEnergy<receivedAttack){
                
                gotWounded();
                isDead=dead();
            }
            
        }
            
        return isDead;
    }
    
}
