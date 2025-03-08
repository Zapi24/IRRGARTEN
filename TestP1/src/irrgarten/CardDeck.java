/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * @author zapi24
 * @param <T>
 */
public abstract class CardDeck<T> { //Clase template
    
    public static final int FIRST_CARD = 0;
    ArrayList<T> cardDeck;

    public CardDeck() {
        
        this.cardDeck = new ArrayList<>();
    }
    
    protected void addCard(T card){
        
        this.cardDeck.add(card);
    }
    
    protected abstract void addCards();
    
    public T nextCard(){
        if(cardDeck.isEmpty()){ //Si estuviera vacia, debemos añadir mas cartas
            
            addCards();
            Collections.shuffle(cardDeck); //Baraja nuestro arraylist
            
        }
       T carta = cardDeck.remove(FIRST_CARD);; //Se coje la primera carta y la borramos
       
       return carta;
    }
        
        
    
    
    
}
