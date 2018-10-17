package Takenoko.Deque;

import Takenoko.Objectives.GardenObjectiveCard;
import Takenoko.Objectives.ObjectiveCard;
import Takenoko.Util.Console;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectivesGardenDeck extends ObjectivesDeck {

    public ObjectivesGardenDeck() {
        super.init(importFromJsonFile());
    }

    public List<ObjectiveCard> importFromJsonFile(){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("garden.json")).getFile());
        ObjectMapper mapper = new ObjectMapper();
        List<ObjectiveCard> obj = new ArrayList<>();
        try {
            obj = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, GardenObjectiveCard.class));
        } catch (IOException e) {
            Console.Log.debugPrint(e.getMessage());
        }
        return obj;
    }
}
