/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.Random;
import java.util.ArrayList;

/**
 *
 * @author zapi24
 */
public class Dice {
    
    
    private final static int MAX_USES=5; //Numero max de usos de armas y escudos
    private final static float MAX_INTELLIGENCE=10; //Valor max de inteligencia en jugadores y monstruos
    private final static float MAX_STRENGTH=10; //Valor max de fuerza en jugadores y monstruos
    private final static double RESURRECT_PROB=0.3; //Prob de que un jugador sea resucitado en cada turno
    private final static int WEAPONS_REWARD=2; //num max de armas recibidas al ganar un combate
    private final static int SHIELDS_REWARD=3; //num max de escudos recibidos al ganar un combate
    private final static int HEALTH_REWARD=5; //num max de unidades de salud recibidas al ganar un combate
    private final static int MAX_ATTACK=3; //max potencia de las armas
    private final static int MAX_SHIELD=2; //max potencia de los escudos
          
    private static Random generator=new Random();
    
    public Dice(){
        
        
    }
    
    
    
    public static int randomPos(int max){
        int valorAleatorio=generator.nextInt(max); //Genera entero aleatorio donde max-1 es el numero mas grande posible
        
        return valorAleatorio;
    }
    
    public static int whoStars(int nplayers){ 
        int valorAleatorio=generator.nextInt(nplayers); 
        
        return valorAleatorio;
    }
    
    public static float randomIntelligence(){
        float valorAleatorio=generator.nextFloat()*MAX_INTELLIGENCE; //El valor minimo es 0, pero si no la implementacion sería *(rango(>0)+min)
        
        return valorAleatorio;
    }
    
    public static float randomStrength(){
        float valorAleatorio=generator.nextFloat()*MAX_STRENGTH; //El valor minimo es 0, pero si no la implementacion sería *(rango(>0)+min)
        
        return valorAleatorio;
    }
    
    public static boolean resurrectPlayer(){
        float valorAleatorio=generator.nextFloat(); //Valor aleatorio entre 0 y 1
        
        if(valorAleatorio<=RESURRECT_PROB){
            
            return true;
        }
        else{
            
            return false;
        }
    }
    
    public static int weaponsReward(){
        int valorAleatorio=generator.nextInt(WEAPONS_REWARD/*+1*/); //Se debe incluir el numero de WEAPONS_REWARD)!??
        
        return valorAleatorio;
    }
    
    public static int shieldsReward(){
        int valorAleatorio=generator.nextInt(SHIELDS_REWARD/*+1*/); 
        
        return valorAleatorio;
    }
    
     public static int healthReward(){
        int valorAleatorio=generator.nextInt(HEALTH_REWARD/*+1*/); 
        
        return valorAleatorio;
    }
     
     public static float weaponPower(){
        float valorAleatorio=generator.nextFloat()*MAX_ATTACK; //Valor float aleatorio entre 0 y MAX_ATTACK
         
        return valorAleatorio;
     }
     
     public static float shieldPower(){
        float valorAleatorio=generator.nextFloat()*MAX_SHIELD;
        
        return valorAleatorio;
     }
     
     public static int usesLeft(){
         int valorAleatorio=generator.nextInt(MAX_USES);
         
         return valorAleatorio;
     }
     
     public static float intensity(float competence){
         float valorAleatorio=generator.nextFloat()*competence;
         
         return valorAleatorio;
     }
     
     public static boolean discardElement(int usesLeft){
         boolean descarte;
         float valorInver= (float) usesLeft/MAX_USES;
         
         //System.out.print(valorInver+" "+generator.nextFloat());
         if(valorInver<generator.nextFloat() || usesLeft==0){
         
             descarte=true;
         }
         else{
             
             descarte=false;
         }
         
         return descarte;
     }
     
     public static  Directions nextStep(Directions preference, ArrayList<Directions> validMoves,float intelligence){
         float valorAleatorio=Dice.randomIntelligence();
         
         if(valorAleatorio < intelligence){// Devolver la preferencia si se cumple la condición de inteligencia
             
             return preference;
         }
         else{ // Elegir aleatoriamente una dirección de las válidas si no se cumple la condición de inteligencia
             int indiceAleatorio = generator.nextInt(validMoves.size());
             
             return validMoves.get(indiceAleatorio);
         
         }
     }
}
