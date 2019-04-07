import cucumber.api.CucumberOptions;
import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import cucumber.api.junit.Cucumber;
import io.cucumber.cucumberexpressions.ParameterType;
import io.cucumber.cucumberexpressions.Transformer;
import org.junit.runner.RunWith;
import takenoko.Couleur;
import takenoko.tuile.Amenagement;

import java.util.Locale;

import static java.util.Locale.ENGLISH;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class CucumberRunnerTest implements TypeRegistryConfigurer {

    @Override
    public Locale locale() {
        return ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.defineParameterType(new ParameterType<>(
                "couleur",
                "BLEU|VERT|JAUNE|ROSE",
                Couleur.class,
                (Transformer<Couleur>) Couleur::valueOf)
        );

        typeRegistry.defineParameterType(new ParameterType<>(
                "amenagement",
                "NONE|BASSIN|ENCLOS|ENGRAIS",
                Amenagement.class,
                (Transformer<Amenagement>) Amenagement::valueOf)
        );
    }
}