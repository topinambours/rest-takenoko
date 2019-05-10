package core.controllers.verification;

import core.GameEngine;
import core.controllers.exception.AuthentificationRequiredException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Final class to verify authentification
 */
public final class AuthentificationVerification {
    @Autowired
    private GameEngine game;

    public static boolean verify(String requested,int playerID, String ip, Logger log) throws AuthentificationRequiredException {
        if(ip.equals("127.0.0.1")){
            log.info(String.format("New request : $s from localhost",requested));
            return true;
        }else{
            if(playerID == -1){
                throw new AuthentificationRequiredException();
            }else{
                log.info(String.format("New request : $s from player $d ($s)",requested,playerID,ip));
                return true;
            }
        }

    }

    public static boolean verify(int playerID, String ip, Logger log) throws AuthentificationRequiredException {
        return verify("",playerID,ip,log);
    }

    //
}
