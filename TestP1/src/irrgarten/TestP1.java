/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;
import interfaz.GuiUI;

/**
 *
 * @author zapi24
 */
public class TestP1 {

    //CREAMOS LOS DISTINTOS ENUMERADOS UTILIZADOS EN LAS DISTINTAS CLASES DEL PROYECTO
    
    public static void main(String[] args) {
        // TODO code application logic here
       
        TextUI vista_texto=new TextUI();
        
        
        Game juego=new Game(1,true); //TRUE para modo debug activado
        Controller controlador=new Controller(juego,vista_texto);
        
        
        boolean mode=false; //Para indicar el modo de vista
        
        if(mode){
            
            GuiUI pantalla = new GuiUI();
            VController vcontrolador = new VController(juego,pantalla);
            vcontrolador.play();
        }
        else{
            
            controlador.play(); //Modo de texto
        }
        
        
        
        //TEST DE LOS ENUM
        
       /* Directions abajo=Directions.DOWN;
        
        System.out.print(abajo+"\n"+"\n");
        
        //TEST DE CLASE WEAPON
        
        Weapon armatest=new Weapon(2,4);
        System.out.print(armatest.discard()+"\n");
        System.out.print(armatest.toString()+"\n"+"\n");
        
        //TEST DE CLASE SHIELD
        
       Shield escudotest=new Shield(3,4);
       System.out.print(escudotest.discard()+"\n");
       System.out.print(escudotest.toString()+"\n"+"\n");
       
       //TEST DE LA CLASE DICE
       
       //Dice dicetest=new Dice();
       System.out.print(Dice.randomPos(10)+"\n");
       System.out.print(Dice.whoStars(5)+"\n");
       System.out.print(Dice.randomIntelligence()+"\n");
       System.out.print(Dice.randomStrength()+"\n"+"\n");
       
       System.out.print(Dice.resurrectPlayer()+"\n"+"\n");
       
       System.out.print(Dice.weaponsReward()+"\n");
       System.out.print(Dice.shieldsReward()+"\n");
       System.out.print(Dice.healthReward()+"\n");
       System.out.print(Dice.weaponPower()+"\n");
       System.out.print(Dice.shieldPower()+"\n");
       System.out.print(Dice.usesLeft()+"\n"+"\n");
       
       System.out.print(Dice.discardElement(3)+"\n"+"\n");
       
       //TEST DE LA CLASE MONSTER
       
       Monster monstruo=new Monster("Jhonny",10,10);
       
       System.out.print(monstruo.dead()+"\n");
       System.out.print(monstruo.attack()+"\n");
       
       monstruo.setPos(2, 3);
       System.out.print(monstruo.toString()+"\n"+"\n");
       monstruo.gotWounded();
       
       //TEST DE LA CLASE JUGADOR
       
       Player jugador=new Player('0',10,10);
       jugador.setPos(2, 3);
       System.out.print(jugador.toString()+"\n");
       
       jugador.gotWounded();
       System.out.print(jugador.toString()+"\n"+"\n");
       
       //TEST DE LA CLASE LABYRINTH
       
       Labyrinth laberinto=new Labyrinth(5,7,4,4);
       System.out.print(laberinto.toString()+"\n");
       System.out.print(laberinto.haveAWinner()+"\n");
       
       laberinto.addMonster(2, 3, monstruo);
       System.out.print(laberinto.toString()+"\n");
       
       System.out.print(laberinto.posOK(9, 0)+"\n");
       System.out.print(laberinto.emptyPos(2, 2)+"\n");
       System.out.print(laberinto.monsterPos(2, 2)+"\n");
       System.out.print(laberinto.exitPos(4, 4)+"\n");
       
       int posicion[]=new int[2];
       
       posicion=laberinto.dir2Pos(3, 3, Directions.LEFT);
       System.out.print("("+ posicion[0]+","+posicion[1]+")"+"\n"); 
       
       
       posicion=laberinto.randomEmptyPos();
       System.out.print("("+ posicion[0]+","+posicion[1]+")"+"\n"+"\n");  //(2,3) es la unica no vacia, hay un monstruo
       
       
       
       //TEST DE LA CLASE GAME
       
       Game juego=new Game(5);
       System.out.print(juego.finished()+"\n");
       System.out.print(juego.getLaberinto().toString()+"\n");
       
       juego.logMonsterWon();
       juego.logPlayerWon();
       juego.nextPlayer();
       juego.logResurrected();
       
       //Test de la clase GameState, partiendo de Game
       GameState juego_actual= juego.getGameState();
       
       System.out.print(juego_actual.getPlayers());
       System.out.print(juego_actual.getMonsters());
       System.out.print(juego_actual.getLog()+"\n");
       System.out.print(juego_actual.getCurrentPlayer()+"\n");
       
       
      
       System.out.print(juego.getLog()+"\n");*/
    }
    
}
