/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author zapi24
 */
public abstract class LabyrinthCharacter {
    
    private String name;
    private float intelligence;
    private float strength;
    private float  health;
    private int row;
    private int col;

    public LabyrinthCharacter(String name, float intelligence, float strength, float health) {
        
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = health;
    }
    
    public LabyrinthCharacter(LabyrinthCharacter other){
    
    
        this(other.name,other.intelligence,other.strength,other.health);
    }
    
    public boolean dead(){
        
        return health < 0;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    protected float getIntelligence() {
        return intelligence;
    }

    protected float getStrength() {
        return strength;
    }

    protected float getHealth() {
        return health;
    }
    
    protected void setHealth(float health){
        
        this.health = health;
    }
    
    public void setPos(int row, int col){
    
        this.row=row;
        this.col=col;
    }
    
    @Override
    public String toString(){
        
        return "Pos: ("+row+","+col+") -Nombre: "+name+" -Vida: "+health+" -Fuerza: "+strength+" -Inteligencia: "+intelligence;
    }
    
    protected void gotWounded(){
        
        this.health--;
    }
    
    abstract float attack();
            
    abstract boolean defend(float attack);  
        
    
    
    
    
    
    
    
}
