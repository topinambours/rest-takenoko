package Takenoko.Deque;

import Takenoko.Objectives.ObjectiveCard;
import Takenoko.Objectives.PandaObjectiveCard;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjectivesPandaDeck extends ObjectivesDeck {

    public ObjectivesPandaDeck() {
        super.init(importFromJsonFile());
    }

    public List<ObjectiveCard> importFromJsonFile(){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("panda.json").getFile());
        ObjectMapper mapper = new ObjectMapper();
        List<ObjectiveCard> obj = new ArrayList<>();
        try {
            obj = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, PandaObjectiveCard.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
