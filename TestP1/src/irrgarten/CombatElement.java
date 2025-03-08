/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author zapi24
 */
public abstract class CombatElement {
    
    private float effect;
    private int uses;
    
    public CombatElement(float effect,int uses){
        
        this.effect=effect;
        this.uses=uses;
    }

    public float getEffect() {
        return effect;
    }

    public int getUses() {
        return uses;
    }

    public void setEffect(float effect) {
        this.effect = effect;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }
    
    protected float produceEffect(){
         if(uses>0){
            
           uses--;
           return uses;
        }
        else{
            
            return 0;
        } 
    }
    
    public boolean discard(){
        
        return Dice.discardElement(uses);
    }
    
    @Override
    public String toString(){
        
        return "[" + effect + "," + uses + "]";
    }
    
    
}
