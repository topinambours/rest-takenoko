package takenoko.tuile;

import lombok.Data;
import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;

import java.util.ArrayList;
import java.util.List;

@Data
public class CoordAxial {

    private int q;
    private int r;

    public CoordAxial(){
        this.q = 0;
        this.r = 0;
    }

    public CoordAxial(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getR() {
        return r;
    }

    /**
     * Setter de R
     * @param r la nouvelle valeur de r
     */
    public void setR(int r) {
        this.r = r;
    }

    /**
     * permet de faire la somme de 2 coordonnées axiales
     * @param coo la coordonnée à ajouter à celle-ci
     * @return la somme des 2 coordonnées
     */
    public CoordAxial add(CoordAxial coo) {
        return new CoordAxial(q + coo.getQ(), r + coo.getR());
    }

    /**
     * rend les 6 coordonnées des hexagones voisins
     * @return Liste de CoordAxial correspondant aux 6 emplacements voisins
     */
    public List<CoordAxial> computeNeighborCoords() {
        ArrayList<CoordAxial> res = new ArrayList<>();
        res.add(new CoordAxial(q + 1, r - 1));
        res.add(new CoordAxial(q + 1, r));
        res.add(new CoordAxial(q, r - 1));
        res.add(new CoordAxial(q, r + 1));
        res.add(new CoordAxial(q - 1, r));
        res.add(new CoordAxial(q - 1, r + 1));
        return res;
    }

    /**
     * rend les coordonnées des 6 arêtes du pourtour de l'hexagone
     * @return Liste de CoordIrrig correspondant aux 6 arêtes de l'hexagone
     */
    public List<CoordIrrig> computeBorderCoords() {
        ArrayList<CoordIrrig> res = new ArrayList<CoordIrrig>();
        res.add(new CoordIrrig(q + 1, r - 1, Orient.S));
        res.add(new CoordIrrig(q + 1, r, Orient.W));
        res.add(new CoordIrrig(q, r, Orient.N));
        res.add(new CoordIrrig(q, r + 1, Orient.N));
        res.add(new CoordIrrig(q, r, Orient.W));
        res.add(new CoordIrrig(q, r, Orient.S));
        return res;
    }


    /**
     * Permet de savoir si une coordonnée est dans la même line que
     * la coordonnée courante.
     * @param coord une coordonnée
     * @return true ou false
     */
    public boolean isInLine(CoordAxial coord){
        int qn = coord.getQ();
        int rn = coord.getR();
        int qsous = q-qn;
        int rsous = r-rn;
        int qabs = Math.abs(qsous);
        int rabs = Math.abs(rsous);

        if((q==qn) || (r==rn) || (qabs==rabs && qsous != rsous)){
            if(q==qn && r == rn){
                return false;
            }
            else {
                return true;
            }
        }
        else{
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoordAxial that = (CoordAxial) o;

        if (q != that.q) return false;
        return r == that.r;
    }

    @Override
    public int hashCode() {
        int result = q;
        result = 31 * result + r;
        return result;
    }

    @Override
    public String toString() {
        return "(" + q +
                "," + r +
                ')';
    }
}
