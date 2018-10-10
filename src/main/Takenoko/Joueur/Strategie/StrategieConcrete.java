package Takenoko.Joueur.Strategie;

import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoord;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrig;
import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;

import java.util.List;
import java.util.Optional;

public class StrategieConcrete extends AbstractStrategie {
    public StrategieCoord strategieCoord;
    public StrategieIrrig strategieIrrig;

    public StrategieConcrete(StrategieCoord strategieCoord, StrategieIrrig strategieIrrig) {
        this.strategieCoord = strategieCoord;
        this.strategieIrrig = strategieIrrig;
    }

    public StrategieConcrete() {
        this.strategieCoord = null;
        this.strategieIrrig = null;
    }

    public void initialize(StrategieCoord strategieCoord, StrategieIrrig strategieIrrig) {
        this.strategieCoord = strategieCoord;
        this.strategieIrrig = strategieIrrig;
    }

    @Override
    public List<CoordAxial> getCoords(Plateau p, Plot plot) {
        return strategieCoord.getCoords(p, plot);
    }

    @Override
    public List<CoordAxial> getCoords(Plateau p) {
        return strategieCoord.getCoords(p);
    }

    @Override
    public CoordAxial getCoord(Plateau p, Plot plot) {
        return strategieCoord.getCoord(p, plot);
    }

    @Override
    public CoordAxial getCoord(Plateau p) {
        return strategieCoord.getCoord(p);
    }

    @Override
    public String getStrategieLabel() {
        return getStrategieCoordLabel() + " " + getStrategieIrrigLabel();
    }

    @Override
    public String getStrategieCoordLabel() {
        return strategieCoord.getStrategieCoordLabel();
    }

    @Override
    public String getStrategieIrrigLabel() {
        return strategieIrrig.getStrategieIrrigLabel();
    }

    @Override
    public Optional getIrrig(Plateau p) {
        return strategieIrrig.getIrrig(p);
    }
}
