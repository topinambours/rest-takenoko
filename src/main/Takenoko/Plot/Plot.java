package Takenoko.Plot;

public class Plot {

    private CoordAxial coord;
    private Bambou bambou;

    public Plot(int q, int r){
        this.coord = new CoordAxial(q,r);
        this.bambou = null;
    }

    public Plot(CoordAxial coordAxial){
        this.coord = coordAxial;
        this.bambou = null;
    }

    public Plot(){
        this(0,0);
    }


    public int getq(){
        return coord.getQ();
    }

    public int getr(){
        return coord.getR();
    }

    public CoordAxial getCoord() {
        return coord;
    }

    public void setCoord(int q, int r){
        coord.setQ(q);
        coord.setR(r);
    }

    /**
     * Permet de savoir si le plot a un bambou
     * @return boolean true|false
     */
    public boolean haveBambou(){
        return bambou != null;
    }

    /**
     * Permet d'ajouter un Bambou
     * @return boolean true|false
     */
    public boolean addBambou(){
        if(!haveBambou()){
            bambou = new Bambou(this);
        }
        return haveBambou();
    }

    /**
     * Permet d'avoir le bambou du plot
     * @return Bambou
     */
    public Bambou getBambou(){
        if(this.haveBambou()){
            return bambou;
        }else{
            return null;
        }
    }

    public int getHauteurBambou(){
        if(this.haveBambou()){
            return this.getBambou().getHauteur();
        }else{
            return 0;
        }
    }

    public void pousserBambou(){
        if(this.haveBambou()){
            this.getBambou().addHauteur1();
        }
    }

    @Override
    public String toString(){
        return "Parcelle("+getq()+","+getr()+")";
    }

}
