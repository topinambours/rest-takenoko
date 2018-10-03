package Takenoko.Objectives.Patterns;

import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pattern {
    private List<PatternTile> tiles;

    public Pattern(List<PatternTile> tiles) {
        this.tiles = tiles;
    }

    Pattern(Pattern pattern) {
        this.tiles = pattern.tiles;
    }

    public PatternTile get(int i) {
        return tiles.get(i);
    }

    public Pattern rotate60() {
        return new Pattern(tiles.stream()
                .map(t -> t.rotate60())
                .collect(Collectors.toList()));
    }

    public boolean check(Plateau plateau, CoordAxial coo) {
        return tiles.stream()
                .allMatch(t -> t.matchPlot(plateau, coo));
    }

    boolean checkAll(Plateau plateau) {
        return plateau.getPlots()
                .entrySet()
                .stream()
                .anyMatch(c -> this.check(plateau, c.getKey()));
    }

    public boolean checkAllRotate(Plateau plateau) {
        Pattern pat = new Pattern(this);
        for (int i = 0; i < 6; i++) {
            pat = pat.rotate60();
            if (pat.checkAll(plateau)) return true;
        }
        return false;
    }

}
