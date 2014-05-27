
package vue.tortue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import modele.Tortue;

/**
 *  @author Victor Lequay
 *  @creation 7 mai 2014
 */
public class VueTortueBalle extends VueTortue{

    public VueTortueBalle(Tortue tortue) {
        super(tortue);
    }

    @Override
    public void drawTurtle(Graphics graph) {
        if (graph == null) {
            return;
        }

        // la position de la tortue p
        Point p = new Point(tortue.getX(), tortue.getY());

        graph.setColor(Color.red);
        graph.fillOval(p.x-5, p.y-5, 10, 10);
    }

    
}
