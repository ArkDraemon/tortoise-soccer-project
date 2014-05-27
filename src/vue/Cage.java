
package vue;

import java.awt.Point;
import java.awt.Rectangle;
import modele.Joueuse;
import modele.Objectif;

/**
 *  @author Victor Lequay
 *  @creation 14 mai 2014
 */
public class Cage implements Objectif{
    private Rectangle dessin;
    private Rectangle zone;
    
    public Cage(Rectangle d, Rectangle z){
        this.zone = z;
        this.dessin = d;
    }
    
    public boolean isInCage(Joueuse j){
        return zone.contains(j.getX(), j.getY());
    }

    @Override
    public Point getPosition() {
        return new Point((int)this.dessin.getCenterX(), (int)this.dessin.getCenterY());
    }
    
}
