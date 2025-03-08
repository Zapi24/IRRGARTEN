/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/*import irrgarten.Directions;
import irrgarten.GameState;*/
import java.util.Scanner;


public class TextUI {
    
    private static Scanner in = new Scanner(System.in);
    
    private char readChar() {
        String s = in.nextLine();     
        return s.charAt(0);
    }
    

    public Directions nextMove() {
        System.out.print("\nDonde te quieres mover? (wasd) ");
        
        Directions direction = Directions.RIGTH;
        boolean gotInput = false;
        
        while (!gotInput) {
            char c = readChar();
            switch(c) {
                case 'w':
                    System.out.print(" UP\n");
                    direction = Directions.UP;
                    gotInput = true;
                    break;
                case 's':
                    System.out.print(" DOWN\n");
                    direction = Directions.DOWN;
                    gotInput = true;
                    break;
                case 'd':
                    System.out.print("RIGTH\n");
                    direction = Directions.RIGTH;
                    gotInput = true;
                    break;
                case 'a':
                    System.out.print(" LEFT\n");
                    direction = Directions.LEFT;
                    gotInput = true;    
                    break;
            }
        }    
        return direction;
    }
    
    public void showGame(GameState gameState) { 
        
        System.out.print("MOSTRANDO JUGADORES "+"\n"+"\n");
        System.out.print(gameState.getPlayers()+"\n"+"\n");
        
        System.out.print("MONSTRANDO MONSTRUOS "+"\n"+"\n");
        System.out.print(gameState.getMonsters()+"\n"+"\n");
        
        System.out.print("************************* "+"\n");
        System.out.print("* IMPRIMIENDO LABERINTO * "+"\n");
        System.out.print("************************* "+"\n"+"\n");
        System.out.print(gameState.getLabyrinthv()+"\n"+"\n");
        
        System.out.print("JUGADOR ACTUAL: "+gameState.getCurrentPlayer()+"\n");
        System.out.print("LOG DEL JUEGO: "+"\n"+"\n");
        System.out.print(gameState.getLog()+"\n");
        
        boolean winner=gameState.isWinner();
        
        if(!winner){
            
            System.out.print("\n"+"Aun no hay ningun ganador, el juego continua"+"\n");
        }
        else{
            
            
            System.out.println("╔═══════════════════════════════════════════════╗");
            System.out.println("║                                               ║");
            System.out.println("║    ¡¡¡FELICIDADES, VALIENTE!!!                ║");
            System.out.println("║    Te has alzado victorioso en esta batalla   ║");
            System.out.println("║    épica.                                     ║");
            System.out.println("║                                               ║");
            System.out.println("║    🏆 ¡Has ganado el juego y tu nombre        ║");
            System.out.println("║    resonará a lo largo de de la historia! 🏆  ║");
            System.out.println("║                                               ║");
            System.out.println("║    ¡Hoy, el reino celebra tu gloriosa         ║");
            System.out.println("║    victoria! 🎉                                 ║");
            System.out.println("║                                               ║");
            System.out.println("╚═══════════════════════════════════════════════╝");
            System.out.println("EL JUGADOR " + gameState.getCurrentPlayer() + " HA GANADO EL JUEGO");

        
        }
        
        
    }
    
}