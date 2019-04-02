package communication.container;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Container<T> {

    List<T> content;

    public Container(){
        content = new ArrayList<>();
    }

    public Container(T singleElement){
        this();
        content.add(singleElement);
    }

    public Container(List<T> multipleElement){
        content = new ArrayList<>(multipleElement);
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
