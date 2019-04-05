package core;

import communication.HTTPClient;

import java.util.Hashtable;

/**
 * Classe qui gère l'enssemble des données du serveur.
 * @TODO Les controllers se doivent d'intéragir avec ces données et non disposer de leur propre structures
 */
public class Server {

    public Hashtable<Integer, HTTPClient> getRegisteredUsers() {
        return registeredUsers;
    }

    protected Hashtable<Integer, HTTPClient> registeredUsers;

    protected MultiQueue queues;

    public Server(){
        this.registeredUsers = new Hashtable<>();
        this.queues = new MultiQueue();
    }
}
