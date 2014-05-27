
package vue.tortue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import modele.Tortue;
import modele.Joueuse;

/**
 *  @author Victor Lequay
 *  @creation 7 mai 2014
 */
public class VueTortueJoueuse extends VueTortue{

    public VueTortueJoueuse(Tortue tortue) {
        super(tortue);
    }

    @Override
    public void drawTurtle(Graphics graph) {
        if (graph == null) {
            return;
        }

		//Calcule les 3 coins du triangle a partir de
        // la position de la tortue p
        Point p = new Point(tortue.getX(), tortue.getY());
        Polygon arrow = new Polygon();

		//Calcule des deux bases
        //Angle de la droite
        double theta = Tortue.ratioDegRad * (-tortue.getDir());
        //Demi angle au sommet du triangle
        double alpha = Math.atan((float) Tortue.rb / (float) Tortue.rp);
        //Rayon de la fleche
        double r = Math.sqrt(Tortue.rp * Tortue.rp + Tortue.rb * Tortue.rb);
		//Sens de la fleche

        //Pointe
        Point p2 = new Point((int) Math.round(p.x + r * Math.cos(theta)),
                (int) Math.round(p.y - r * Math.sin(theta)));
        arrow.addPoint(p2.x, p2.y);
        arrow.addPoint((int) Math.round(p2.x - r * Math.cos(theta + alpha)),
                (int) Math.round(p2.y + r * Math.sin(theta + alpha)));

        //Base2
        arrow.addPoint((int) Math.round(p2.x - r * Math.cos(theta - alpha)),
                (int) Math.round(p2.y + r * Math.sin(theta - alpha)));

        arrow.addPoint(p2.x, p2.y);
        graph.setColor(tortue.getColor());
        graph.fillPolygon(arrow);
        //graph.drawString(((Joueuse)tortue).getNom(), tortue.getX()+5, tortue.getY());
    }

    
}
