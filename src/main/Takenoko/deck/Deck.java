package takenoko.deck;

import takenoko.util.exceptions.EmptyDeckException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Une pioche regroupe un enssemble d'objets qui peuvent être piochés depuis le haut de la pile et insérés en dessous.
 * @param <T> Classe des objets composants la pioche
 */
@Component
@Configurable
public class Deck<T> {

    private Deque<T> deque;

    /**
     * Création d'une pioche à partir d'une liste d'objets
     * @param elements éléments de base composant la pioche au départ
     */
    public Deck(List<T> elements){
        List<T> cpy = new ArrayList<>(elements);
        Collections.shuffle(cpy);
        deque = new ArrayDeque<>(cpy);
    }

    /**
     * Création d'une pioche vide
     */
    public Deck(){
        deque = new ArrayDeque<>();
    }

    /**
     * Permet de piocher un élément
     * @return l'élément au sommet de la piocher
     * @throws EmptyDeckException renvoie une exception dans le cas d'une pioche non autorisée (pioche vide)
     */
    public T draw() throws EmptyDeckException {
        if (isEmpty()){
                throw new EmptyDeckException();
        }
        return deque.pollFirst();
    }

    /**
     * Permet de piocher plusieurs éléments
     * @param n nombre d'éléments à piocher
     * @return liste d'éléments piochés
     * @throws EmptyDeckException renvoie une exception dans le cas d'une pioche non autorisée (pioche vide)
     */
    public List<T> draw(int n) throws EmptyDeckException {
        List<T> out = new ArrayList<>();
        if (n >= size()){
            for (int i = 0; i <= size(); i++){
                out.add(draw());
            }
        }else{
            for (int i = 0; i < n; i++){
                out.add(draw());
            }
        }
        return out;
    }

    /**
     * Permet d'insérer un élément à la base de la pioche
     * @param element éléments à insérer
     */
    public void insertBottom(T element){
        deque.addLast(element);
    }

    /**
     * Renvoie la hauteur de la pioche
     * @return nombre d'éléments constituant la pioche
     */
    public int size(){
        return deque.size();
    }

    /**
     * Test si la pioche est vide
     * @return vrai si la pioche est vide
     */
    public boolean isEmpty(){
        return deque.isEmpty();
    }


}
