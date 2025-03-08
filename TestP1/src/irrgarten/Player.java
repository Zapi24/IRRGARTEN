/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author zapi24
 */
public class Player extends LabyrinthCharacter{
    
    
    private ArrayList<Weapon> armas = new ArrayList<>();
    private ArrayList<Shield> escudos = new ArrayList<>();
    
    private final static int INITIAL_HEALTH=10;
    private final static int MAX_WEAPONS=2;
    private final static int MAX_SHIELDS=3;
    private final static int HITS2LOSE=3;
    
     private char numero;
     private int consecutiveHits=0;
     
     private CardDeck<Shield> shieldCardDeck = new ShieldCardDeck();
     private CardDeck<Weapon> weaponCardDeck = new WeaponCardDeck(); 
    
   
    public Player(char numero, float intelligence, float strength) {
         
        super("Player#"+numero,intelligence,strength,INITIAL_HEALTH);
         this.numero=numero;
       
    }
    
     public void resurrect(){
        
        armas.clear(); //Vaciamos la lista de armas
        escudos.clear(); //Vaciamos la lista de escudos
        super.setHealth(INITIAL_HEALTH); //podria hacerlo de otra manera al igual que en ruby?
        consecutiveHits=0;  
    }
    
    public char getNumero() {
        return numero;
    }
    
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        int size=validMoves.size();
        boolean contained=validMoves.contains(direction);
        
        if(size>0 && !contained){
            //Si hay movimientos disponibles, pero no esta el que buscamos, nos movemos hacia el primer movimiento de la lista
            return validMoves.get(0); 
        }
        else{
            
            return direction;
        }
        
    }
    
    @Override
    public float attack(){ //Hay que añadir override?
        
        return super.getStrength()+sumWeapons();      
    }

    
    @Override
    public boolean defend(float receivedAttack){
        
        return manageHit(receivedAttack); 
    }
    
    public void receiveReward(){
        
        int wReward=Dice.weaponsReward();
        int sReward=Dice.shieldsReward();
        
        for(int i=0;i<wReward;i++){ //Deberia empezar en uno?
            
            Weapon wnew = newWeapon();
            receiveWeapon(wnew);
        }
        
        for(int i=0;i<sReward;i++){ //Deberia de empezar en 1?
            
            Shield snew = newShield();
            receiveShield(snew);
        }
        
        int extraHealth=Dice.healthReward();
        
        super.setHealth(super.getHealth()+extraHealth); //Le sumamos la vida extra a la actual
    }
    
    @Override
    public String toString(){
        String string="";
        string=string+super.toString();
        
        if(!armas.isEmpty()){ //Mostramos las armas
            
            string=string+"\n       Armas: ";
            for(int i=0;i<armas.size();i++){
                
                string=string+armas.get(i)+" ";
            }
        }
        
        if(!escudos.isEmpty()){ //Mostramos las armas
            
            string=string+"\n       Escudos: ";
            for(int i=0;i<escudos.size();i++){
                
                string=string+escudos.get(i)+" ";
            }
        }
        return string;
    }
    
    private void receiveWeapon(Weapon w){
        
        Iterator<Weapon> iterator = armas.iterator();  //Usamos next, para evitar coredumpeds
        
       while(iterator.hasNext()){ 
            
            Weapon si = iterator.next(); 
           
            
            if(si.discard()){
                
                iterator.remove();
            }
        }
        
        if(armas.size() < MAX_WEAPONS){
            
            armas.add(w);
        }
       
    }
    
    private void receiveShield(Shield s){
        
        Iterator<Shield> iterator = escudos.iterator();
        while (iterator.hasNext()) {
            
            Shield si = iterator.next();

        
            if(si.discard()){
                
                iterator.remove(); // Usar el método remove del iterador evita la ConcurrentModificationException
            }
        }

        if (escudos.size() < MAX_SHIELDS) {
            
                escudos.add(s);
        }
 
    }
    
    private Weapon newWeapon(){ //Lar armas las coje de la baraja
                
        return weaponCardDeck.nextCard();
    }
    
    private Shield newShield(){ //Los escudos los coje de la baraja
        
        return shieldCardDeck.nextCard();
    }
    
    protected float sumWeapons(){
        float sumaArmas=0;
        
        for(int i=0;i<armas.size();i++){
            
            sumaArmas=sumaArmas+armas.get(i).attack();
        }
        
        return sumaArmas;
    }
    
    protected float sumShields(){
        float sumaEscudos=0;
        
        for(int i=0;i<escudos.size();i++){
            
            sumaEscudos=sumaEscudos+escudos.get(i).protect();
        }
        
        return sumaEscudos;
    }
    
    protected float defensiveEnergy(){
        
        return super.getIntelligence()+sumShields();
    }
    
    private boolean manageHit(float receivedAttack){
        
        if(defensiveEnergy()<receivedAttack){
            
            
            gotWounded(); //Pierde uno de vida
            incConsecutiveHits(); //Aumenta el numero de golpes consecutivos que recibe
        }
        else{
            
            resetHits(); //Si se defiende, se resetean los golpes consecutivos
        }
       
        //Si esta muerto, o tiene el numero de golpes consecutivos maximos, pierde
        if(consecutiveHits == HITS2LOSE || dead()){ 
            
            resetHits();
            return true;
        }
        else{
            
            return false;
        }  
    }
    
    public void resetHits(){
        
        consecutiveHits=0;
    }
    
    public void incConsecutiveHits(){
        
        consecutiveHits++;
    }
    
    
}
