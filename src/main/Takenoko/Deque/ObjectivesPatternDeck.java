package Takenoko.Deque;

import Takenoko.Objectives.ObjectiveCard;
import Takenoko.Objectives.PatternObjectiveCard;
import Takenoko.Util.Console;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
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
        JsonParser parser = null;
        try {
            parser = new JsonFactory().createParser(file);
            i = mapper.readValues(parser, new TypeReference<ArrayList<PatternObjectiveCard>>(){});
            out.addAll((Collection<? extends ObjectiveCard>) Objects.requireNonNull(i).readAll().get(0));
        } catch (IOException e) {
            Console.Log.debugPrint(e.getMessage());
        }
        finally {
            try {
                if (parser != null) {
                    parser.close();
                }
            } catch (IOException e) {
                Console.Log.debugPrint(e.getMessage());
            }
        }
        return out;
    }

}
