package core;

import communication.HTTPClient;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class MultiQueue {

    private final int TAKENOKO_MIN_PLAYERS = 2;
    private final int TAKENOKO_MAX_PLAYERS = 4;

    private Hashtable<Integer, Queue<HTTPClient>> queues;

    public MultiQueue(){
        this.queues = new Hashtable<>();

        for (int i = TAKENOKO_MIN_PLAYERS; i <= TAKENOKO_MAX_PLAYERS; i++){
            queues.put(i, new LinkedBlockingQueue<>());
        }
    }

    public void addClient(int queueKey, HTTPClient... client){
        this.queues.get(queueKey).addAll(Arrays.asList(client));
    }

    public List<HTTPClient> removeFromQueue(int queueKey, int n){
        List<HTTPClient> out = new ArrayList<>();

        while(n > 0){
            out.add(queues.get(queueKey).remove());
            n--;
        }
        return out;
    }

    public boolean enoughPlayerForGame(int queueKey){
        return queueKey <= queues.get(queueKey).size();
    }

    public Queue<HTTPClient> getQueue(int queueKey){
        return queues.get(queueKey);
    }

}
