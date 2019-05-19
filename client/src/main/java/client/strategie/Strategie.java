package client.strategie;

import takenoko.irrigation.CoordIrrig;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.util.List;

public interface Strategie {
    Tuile selectTuile(List<Tuile> tuiles);
    CoordAxial selectEmplacement(List<CoordAxial> coordAxials);
    CoordIrrig selectIrrigationEmplacement(List<CoordIrrig> coordIrrigs);
    CoordAxial selectPandaEmplacement(List<CoordAxial> coordAxials);
}
