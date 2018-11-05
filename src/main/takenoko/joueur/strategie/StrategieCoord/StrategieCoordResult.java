package takenoko.joueur.strategie.StrategieCoord;

import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;

public class StrategieCoordResult {
    private final int pos;
    private final Plot plot;
    private final CoordAxial coordAxial;

    public StrategieCoordResult(int pos, Plot plot, CoordAxial coordAxial) {
        this.pos = pos;
        this.plot = plot;
        this.coordAxial = coordAxial;
    }

    public int getPos() {
        return pos;
    }

    public Plot getPlot() {
        return plot;
    }

    public CoordAxial getCoordAxial() {
        return coordAxial;
    }
}
