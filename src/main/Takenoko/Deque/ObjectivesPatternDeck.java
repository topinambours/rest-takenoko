package Takenoko.Deque;

import Takenoko.Objectives.ObjectiveCard;
import Takenoko.Objectives.PatternObjectiveCard;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ObjectivesPatternDeck extends ObjectivesDeck {

    public ObjectivesPatternDeck() {
        super.init(importFromJsonFile());
    }

    public List<ObjectiveCard> importFromJsonFile(){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("pattern.json")).getFile());
        ObjectMapper mapper = new ObjectMapper();

        List<ObjectiveCard> out = new ArrayList<>();
        MappingIterator<PatternObjectiveCard> i = null;
        try {
            i = mapper.readValues(
                    new JsonFactory().createParser(file), new TypeReference<ArrayList<PatternObjectiveCard>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out.addAll((Collection<? extends ObjectiveCard>) i.readAll().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

}
