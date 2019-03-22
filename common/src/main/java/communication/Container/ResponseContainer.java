package communication.Container;

/**
 * Un objet de type ResponseContainer contient un boolean et un message.
 */
public class ResponseContainer {
    public Boolean response;
    public String message;

    public ResponseContainer(){
        this.response = null;
        this.message = null;
    }

    public ResponseContainer(boolean b, String msg){
        this.response = b;
        this.message = msg;
    }

    @Override
    public String toString() {
        return String.format("<Response : %s, %s>", response, message);
    }

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
