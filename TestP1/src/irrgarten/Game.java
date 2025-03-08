/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author zapi24
 */
public class Game {
    
    private static final int MAX_ROUNDS=10;
    private static final int TAM_LABERINTO=12;
    private static final int NUM_MONSTERS=10;
    private int nMonsters=0;
    private int currentPlayerIndex;
    private String log;
    private Labyrinth laberinto;
    
    
    ArrayList <Monster> monstruos= new ArrayList<>();
    ArrayList <Player> jugadores= new ArrayList<>();
   
    
    public Game(int nPLayers,Boolean debug){
        
        currentPlayerIndex=Dice.whoStars(nPLayers); //Determinamos quien va a empezar
        log ="";
        
        //Añadimos la salida y creamos el laberinto
        int randomExit=Dice.randomPos(TAM_LABERINTO);
        
        laberinto = new Labyrinth(TAM_LABERINTO,TAM_LABERINTO,(TAM_LABERINTO-1),randomExit); //Deben estar en el lateral inferior
        
        
        if(!debug){ //Configuracion normal
             //Añadimos jugadores al arraylist
            for(int i=0;i<nPLayers;i++){
            
                char playerId = (char) ('0' + i);
                jugadores.add(new Player(playerId,Dice.randomIntelligence(),Dice.randomStrength()));
            }   
            // Configurar el laberinto
            configureLabyrinth();
        }
        else{//Configuracion de prueba
            for(int i=0;i<nPLayers;i++){
            
                char playerId = (char) ('0' + i);
                jugadores.add(new Player(playerId,4,4));
            }
            laberinto.addBlock(Orientations.HORIZONTAL, 2, 0, 10);
            
            Monster monstruo=new Monster("Minion" ,5,5);
            monstruos.add(monstruo);
            laberinto.addMonster(1, 1, monstruo);
            
            monstruo=new Monster("Minion" ,20,20);
            monstruos.add(monstruo);
            laberinto.addMonster(1, 2, monstruo);
           
            laberinto.spreadPlayers(jugadores, false);
        }
     
    }
    
    
    public Labyrinth getLaberinto() {
        return laberinto;
    }
    
    public String getLog(){
        
        return log;
    }
    
    
    public boolean finished(){
        
        return laberinto.haveAWinner();
    }
    
    public boolean nextStep(Directions preferredDirection){
        log="";
        Player currentPlayer=jugadores.get(currentPlayerIndex);
        
        if(!currentPlayer.dead()){
            if(actualDirection(preferredDirection) != preferredDirection){ //Comprueba que la direccion, es realmente la direccion actual
                
                logPlayerNoOrders();
            }
            
            Monster monster=laberinto.putPlayer(actualDirection(preferredDirection), currentPlayer);
       
            
            if(monster == null){ //Al moverse no habia monstruo
               
                logNoMonster(preferredDirection);
                
            }
            else{ //Si habia monstruo, empieza el combate
                
                
                manageReward(combat(monster)); //Se establece recompensa, al terminar el combate
            }  
        }
        else{
            
            manageResurrection(); //Si esta muerto, que se tire al dado si puede revivir
        }
        
        boolean endGame = finished();
        if(!endGame){ //Comprueba que el juego no haya terminado, al ganar un jugador
        
            nextPlayer();
        }
    
        return endGame;
    }
    
    
    
    public GameState getGameState(){
        String players="",monsters="";
        
        for(int i=0;i<jugadores.size();i++){
            
            players=players+jugadores.get(i).toString()+"\n";
        }
        
        for(int i=0;i<monstruos.size();i++){
            
            monsters=monsters+monstruos.get(i).toString()+"\n";
        }
        
        return new GameState(laberinto.toString(),players,monsters,currentPlayerIndex,finished(),log);
    }
    
    public final void configureLabyrinth(){ 
        
        //Introducimos los obstaculos, de forma custom
        laberinto.addBlock(Orientations.HORIZONTAL, 2, 0, 10);
        laberinto.addBlock(Orientations.HORIZONTAL,5,0,5);
        laberinto.addBlock(Orientations.HORIZONTAL,5,7,5);
        laberinto.addBlock(Orientations.HORIZONTAL, 7, 4, 4);
        laberinto.addBlock(Orientations.VERTICAL, 8, 1, 3);
        laberinto.addBlock(Orientations.VERTICAL, 8, 10, 3);
        laberinto.addBlock(Orientations.HORIZONTAL, 9, 2, 3);
        laberinto.addBlock(Orientations.HORIZONTAL, 9,7,3);
        
        //Introducimos los monstruos
        while(nMonsters<NUM_MONSTERS){
            
            int randomRow=Dice.randomPos(TAM_LABERINTO);
            int randomCol=Dice.randomPos(TAM_LABERINTO);
            if(laberinto.emptyPos(randomRow, randomCol)){ 
                    
                    Monster monstruo=new Monster("Minion" ,Dice.randomStrength(),Dice.randomIntelligence());
                    monstruos.add(monstruo);
                    laberinto.addMonster(randomRow, randomCol, monstruo);
                    nMonsters++;
                }
        } 
         //Introducimos los jugadores
        laberinto.spreadPlayers(jugadores, false); 
    }
    
    public void nextPlayer(){    
        currentPlayerIndex++; //Pasamos al siguiente jugador
        
        if( currentPlayerIndex>=jugadores.size()){ //Implica que el siguiente jugador, es el jugador 0 de vuelta
            
            currentPlayerIndex=0;
        }
    }
    
    public Directions actualDirection(Directions preferredDirection){
        
        ArrayList<Directions> movimientos_validos;
        int currentRow= jugadores.get(currentPlayerIndex).getRow();
        int currentCol= jugadores.get(currentPlayerIndex).getCol();
        
        movimientos_validos=laberinto.validMoves(currentRow, currentCol);
        
        //DEPURACION
        /*System.out.println("\nMOVIMIENTOS VALIDOS\n");
        
        for(int i=0;i<movimientos_validos.size();i++){
            
            System.out.println(movimientos_validos.get(i));
        }*/
      
        return jugadores.get(currentPlayerIndex).move(preferredDirection, movimientos_validos);
    
    }
    
    public GameCharacter combat(Monster monster){
        
        Player currentPlayer=jugadores.get(currentPlayerIndex);
        int rounds=0;
        GameCharacter winner = GameCharacter.PLAYER; 
        
        float playerAttack=currentPlayer.attack(); //Calculamos la fuerza de ataque del jugador
        boolean lose = monster.defend(playerAttack); //Vemos si el monstruo aguanta el golpe
        
        
        while(!lose && rounds<MAX_ROUNDS){ //Mientras ninguno de los dos pierda, la batalla continua
            
            float monsterAttack=monster.attack(); 
            lose =currentPlayer.defend(monsterAttack); //Ataca el monstruo
            winner = GameCharacter.MONSTER;
            rounds++;
            
            if(!lose){ //Ataca de vuelta el jugador
                
                playerAttack=currentPlayer.attack();
                lose = monster.defend(playerAttack);
                winner = GameCharacter.PLAYER;
            }
        }
        
        logRounds(rounds,MAX_ROUNDS);
        
        return winner;
    }
    
    public void manageReward(GameCharacter winner){
        
        if(winner == GameCharacter.PLAYER){
            
            Player currentPlayer=jugadores.get(currentPlayerIndex);
            currentPlayer.receiveReward();
            logPlayerWon();
        }
        else{
               
            logMonsterWon();
        }
    }
    
    // Método para sustituir Player por FuzzyPlayer
    private void replacePlayerWithFuzzy(Player player, FuzzyPlayer fuzzyPlayer){
          
        //Obtenemos la posicion del jugador, para modificarlo del laberinto, pues 
        int row = player.getRow();
        int col = player.getCol();
        
        fuzzyPlayer.setPos(row, col); //Le indicamos su posicion en el laberinto
            
        laberinto.changePlayer(row,col,fuzzyPlayer); //Cambiamos el tipo de jugador dentro del laberinto
        
        //Cambiamos el jugador 
        int index = jugadores.indexOf(player); // Encuentra el índice del jugador actual
        jugadores.remove(index); // Remueve al jugador actual del vector de jugadores
        jugadores.add(index, fuzzyPlayer); // Agrega al FuzzyPlayer en el mismo índice
    }
    
    
    public void manageResurrection() {
        if (Dice.resurrectPlayer()) {
            
            Player currentPlayer = jugadores.get(currentPlayerIndex);
            FuzzyPlayer fuzzyPlayer = new FuzzyPlayer(currentPlayer);
            currentPlayer.resurrect();
            replacePlayerWithFuzzy(currentPlayer, fuzzyPlayer); // Sustituye Player por FuzzyPlayer
            
  
            logResurrected(); 
        } 
        else {
            
            logPlayerSkipTurn();
        }
    }
    
    public void logPlayerWon(){
        
        log=log+"El jugador ha ganado el combate. \n";
    }
    
    public void logMonsterWon(){
        
        log=log+"Ha ganado el combate el monstruo \n";
    }
    
    public void logResurrected(){
        
        log=log+"El jugador ha sido resucitado, y se ha convertido en un FuzzyPlayer"+"\n";
    }
    
    public void logPlayerSkipTurn(){
        
        log=log+"El jugador ha perdido el turno por estar muerto. \n";
    }
    
    public void logPlayerNoOrders(){
        
        log=log+"El jugador no ha seguido las instrucciones del jugador humano (no fue posible).\n";
    }

    public void logNoMonster(Directions direccion) {
     
        log =log+"Al jugador no le ha sido posible moverse, o se ha movido a una celda vacía a la "+ direccion +".\n";
    }

    public void logRounds(int rounds, int max) {
        
        log =log+ "Se han producido " + rounds + " de " + max + " rondas de combate.\n";
    }
    
    
    
    
}




//Juadores 1º iterador 2º ramdon_intelligence 3º ramdon_strengh
//Monstruos 1ºnombrerandom 2º con el dado y 3º con el dado