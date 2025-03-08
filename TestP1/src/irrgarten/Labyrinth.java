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
public class Labyrinth {
    
    private static final char BLOCK_CHAR = 'X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char COMBAT_CHAR = 'C';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0;
    private static final int COL= 1;
    
    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;
    
    private Monster celdasMonstruo[][];
    private Player celdasJugador[][];
    private char celdasJuego[][];
    

    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol) {
        
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        
        //Declaramos las matrices con el tamaño indicado
        
        celdasMonstruo= new Monster[nRows][nCols];
        celdasJugador= new Player[nRows][nCols];
        celdasJuego= new char[nRows][nCols];
        
        
        for (int i = 0; i < nRows; i++) { //Añadimos e inicializamos con valores vacios y nulos
            for (int j = 0; j < nCols; j++) {
                
                celdasMonstruo[i][j]=null;
                celdasJugador[i][j]=null;
                celdasJuego[i][j]=EMPTY_CHAR;
            }
        }
        
        celdasJuego[exitRow][exitCol]=EXIT_CHAR;
    }

    public int getnRows() {
        return nRows;
    }

    public int getnCols() {
        return nCols;
    }
    
    public void changePlayer(int row, int col, FuzzyPlayer fplayer){
        
        this.celdasJugador[row][col]=fplayer;
    }
    
    
    public void aniadirObstaculo(int i,int j){ 
        //Podemos añadir un obstaculo, si la posicion esta vacia y es una posicion valida
        if(celdasJuego[i][j]==EMPTY_CHAR && posOK(i,j)){
            
            celdasJuego[i][j]=BLOCK_CHAR;
        }
    }
    
    /*public void addPlayer(int i, Player player){
    
        if(celdasJuego[0][i]==EMPTY_CHAR && posOK(0,i)){
        
            celdasJuego[0][i]=player.getNumero();
        }
    }*/
     //Bool para establecer si queremos randomizar la pos de los players
    public void spreadPlayers(ArrayList<Player> players, boolean random){
  
        int contador=0;
        for(int i=0; i<players.size();i++){
            
            Player p = players.get(i);
            
            int pos[]=new int[2];
            
            if(random){
                
                pos=randomEmptyPos();
                
            }
            else{
                
                pos[ROW]=0; //Los colocaremos dentro de la primera fila
                pos[COL]=contador; //Iran cogiendo las posiciones (0,0) (0,1) (0,2) (0,3) sucesivamente
                contador++;
            }
           
            putPlayer2D(-1,-1,pos[ROW],pos[COL],p); //Devuelve el monstruo a donde
        }
    }
    
    public boolean haveAWinner(){
        
        return celdasJugador[exitRow][exitCol]!=null; //Se cumplira la condicion si no esta vacia la posicion (exitRow,exitCol)
    }
    
    @Override
    public String toString(){
        String lista;
        
        lista= "Tamanio: ("+nRows+"x"+nCols+") -Salida: ("+exitRow+","+exitCol+")"+"\n";
        lista=lista+"Celdas de juego: "+"\n";
        
        for(int i=0;i<nRows;i++){
            for(int j=0;j<nCols;j++){
                
               lista=lista+ celdasJuego[i][j] +" ";
            }
            lista=lista+"\n";
        }
        
        //NO ES NECESARIO
        /*lista=lista+"Celdas de monstruos: "+"\n";
        for(int i=0;i<nRows;i++){
            for(int j=0;j<nCols;j++){
                
               lista=lista+ celdasMonstruo[i][j] +" ";
            }
            lista=lista+"\n";
        }*/
        
        /*lista=lista+"Celdas de jugadores: "+"\n";
        for(int i=0;i<nRows;i++){
            for(int j=0;j<nCols;j++){
                
               lista=lista+ celdasJugador[i][j] +" ";
            }
            lista=lista+"\n";
        }*/
                
        return lista;
    }
    
    public void addMonster(int row,int col, Monster monster){        
        if(posOK(row,col)){
            
            monster.setPos(row, col);
        
            //Seleccionamos la columna y el valor que introducimos en el
            celdasJuego[row][col]=MONSTER_CHAR;
            celdasMonstruo[row][col]=monster; 
        } 
    }
    
   public Monster putPlayer(Directions direction, Player player){
       int oldRow=player.getRow();
       int oldCol=player.getCol();
       
       int newPos[];
       newPos = new int[2];
       
       newPos=dir2Pos(oldRow,oldCol,direction);
       
       return putPlayer2D(oldRow,oldCol,newPos[ROW],newPos[COL],player);
    }
   
    public void addBlock(Orientations orientation, int startRow,int startCol, int length){
        int incRow,incCol;
        
        if(orientation == Orientations.VERTICAL){ //Bloque vertical
            
            
            incRow=1;
            incCol=0;
        }
        else{ //Bloque horizontal
            
            incRow=0;
            incCol=1;
        }
        
        int row=startRow;
        int col=startCol;
        
        
        while(posOK(row,col) && emptyPos(row,col) && length>0){
            
            aniadirObstaculo(row,col);
            
            length=length-1;
            row=row+incRow;
            col=col+incCol;
        }
        
    }
    
    
    public ArrayList<Directions> validMoves(int row,int col){
        ArrayList<Directions> output = new ArrayList<>();
        
        if(canStepOn(row+1,col)){
            
            output.add(Directions.DOWN);
            
        }
        
        if(canStepOn(row-1,col)){
            
            output.add(Directions.UP);
            
        }
        
        if(canStepOn(row,col+1)){
            
            output.add(Directions.RIGTH);
            
        }
        
        if(canStepOn(row,col-1)){
            
            output.add(Directions.LEFT);
        }
      
        return output;
    }
    
    public boolean posOK(int row, int col){
        
        return row>=0 && row<nRows && col>=0 && col<nCols;
    }
    
    public boolean emptyPos(int row, int col){
        if(posOK(row,col)){
            
            return celdasJuego[row][col]==EMPTY_CHAR;  
        }
        else{
            
            return false;
        }
    }
    
    public boolean monsterPos(int row,int col){
         if(posOK(row,col)){
            
            return celdasMonstruo[row][col]!=null ;  
        }
        else{
            
            return false;
        }
    }
    
    public boolean exitPos(int row,int col){
        
        return row==exitRow && col==exitCol;
    }
    
    public boolean combatPos(int row, int col){       
       if(posOK(row,col)){
        
            return celdasJuego[row][col]==COMBAT_CHAR && monsterPos(row,col) ;  
        }
        else{
            
            return false;
        }
    }
    
    public boolean canStepOn(int row, int col){
        
        return posOK(row,col) && (emptyPos(row,col) || monsterPos(row,col) || exitPos(row,col));
    }
    
    public void updateOldPos(int row, int col){
        
        if(posOK(row,col)){
            if(celdasJuego[row][col]==COMBAT_CHAR){
                
                celdasJuego[row][col]=MONSTER_CHAR; 
            }
            else{
                
                celdasJuego[row][col]=EMPTY_CHAR;
            }   
        } 
    }
    
    public int[] dir2Pos(int row, int col, Directions direction){
        int posicion[];
        posicion=new int[2];
        
        switch (direction) {
            case DOWN:
                
                posicion[0]=row+1; posicion[1]=col;
                break;
            case LEFT:
                
                posicion[0]=row; posicion[1]=col-1;
                break;
            case RIGTH:
                
                posicion[0]=row; posicion[1]=col+1;
                break;
            case UP:
                
                posicion[0]=row-1; posicion[1]=col;
                break;
        }
        
        return posicion;
    }
    
    public int[] randomEmptyPos(){
        int rowAux,colAux;
        int posicion[];
        posicion=new int[2];
        
        
        do{
         
            rowAux=Dice.randomPos(nRows);
            colAux=Dice.randomPos(nCols);
            
             
        }while(celdasJuego[rowAux][colAux]!=EMPTY_CHAR);
        
        posicion[ROW]=rowAux; posicion[COL]=colAux;
        return posicion;
    }
    
    public Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player){
        Monster output=null;
        
        if(canStepOn(row,col)){
            
            if(posOK(oldRow,oldCol)){
                
                Player p = celdasJugador[oldRow][oldCol];
                
                if(p == player){
                    
                    updateOldPos(oldRow,oldCol);
                    celdasJugador[oldRow][oldCol]=null;
                }
            }
            
            boolean monsterPos=monsterPos(row,col);
            
            if(monsterPos){ //Hay un monstruo
                
                celdasJuego[row][col]=COMBAT_CHAR;
                output=celdasMonstruo[row][col];
            }
            else{ //Es una celda vacia, o la celda de salida
                
                char number=player.getNumero();
                celdasJuego[row][col]=number;
            }
            
            celdasJugador[row][col]=player;
            player.setPos(row, col);  
        }
        
        return output;
    }
}
