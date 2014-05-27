package modele;

import vue.Terrain;

/**
 * @author Victor Lequay
 * @creation 21 mai 2014
 */
public abstract class Strategie {

    protected Terrain terrain;
    protected static Strategie instance = null;

    protected Strategie(Terrain terrain) {
        this.terrain = terrain;
    }

    public void seDeplacer(Joueuse j) {
        definirObjectif(j);
        gererBalle(j);
    }
    
    protected abstract void definirObjectif(Joueuse j);
    protected abstract void gererBalle(Joueuse j);
}
