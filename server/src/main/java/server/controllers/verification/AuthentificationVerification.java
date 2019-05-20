package server.controllers.verification;

import server.GameEngine;
import server.controllers.exception.AuthentificationRequiredException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Final class to verify authentification
 */
@Component
public final class AuthentificationVerification {
    @Autowired
    private static GameEngine game;

    public static boolean verify(String requested,int playerID, String ip, Logger log) throws AuthentificationRequiredException {
        if (ip == null) return true; //Local testing dont have IP
        if(ip.equals("127.0.0.1")){
            log.info("New request : {} from localhost",requested);
            return true;
        }else{
            if(playerID == -1){
                log.warn("No authentificated access at {} from {} => Access Denied",requested,ip);
                throw new AuthentificationRequiredException();
            }else{
                if (game.getClientsId().contains(playerID)){
                    log.info("New request : {} from player {} ({})",requested,playerID,ip);
                    return true;
                }else{
                    log.warn("Invalid authentification access at {} from {} => Access Denied",requested,ip);
                    throw new AuthentificationRequiredException("Error : Invalid authentification");
                }

            }
        }

    }

    public static boolean verify(int playerID, String ip, Logger log) throws AuthentificationRequiredException {
        return verify("",playerID,ip,log);
    }

    public static String getRemoteAddress() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
            return request.getRemoteAddr();
        }
        return null;
    }

    //
}
