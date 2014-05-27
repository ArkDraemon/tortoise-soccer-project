package modele;

import vue.Terrain;

/**
 * @author Victor Lequay
 * @creation 21 mai 2014
 */
public class StrategieAttaquant extends Strategie {

    private StrategieAttaquant(Terrain t) {
        super(t);
    }

    public static Strategie getInstance(Terrain t) {
        if (instance == null) {
            instance = new StrategieAttaquant(t);
        }
        return instance;
    }

    @Override
    protected void definirObjectif(Joueuse j) {
        if (j.getBalle() != null) {
            j.setObjectif(j.getCage());
        } else {
            Objectif o = this.terrain.getPossBalle();
            if (j.getEuclidean(o) <= j.getDistancePerception()) {
                j.setObjectif(o);
            } else {
                j.setObjectif(null);
            }
        }
    }

    @Override
    protected void gererBalle(Joueuse j) {
    }

}
