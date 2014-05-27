package vue;

// package logo;
import java.awt.*;
import java.awt.event.MouseEvent;
import static java.lang.Math.abs;
import java.util.*;
import javax.swing.*;
import modele.Joueuse;
import modele.Tortue;
import vue.tortue.VueTortue;
import vue.tortue.VueTortueBalle;

public class Terrain extends JPanel implements Observer {

    private ArrayList<VueTortue> tortues; // la liste des tortues enregistrees
    private Tortue possesseurBalle;
    private Cage cage1;
    private Cage cage2;
    
    public static int HEIGHT = 450;
    public static int WIDTH = 600;

    public Terrain(Cage b1, Cage b2) {
        tortues = new ArrayList<>();
        this.cage1 = b1;
        this.cage2 = b2;
        this.setBackground(Color.white);
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void addTortue(VueTortue o) {
        tortues.add(o);
        o.getTortue().addObserver(this);
    }
    
    public void setPossesseurBalle(Tortue t){
        possesseurBalle = t;
        System.out.println(((Joueuse)t).getNom());
    }
    
    public Tortue getPossBalle(){
        return possesseurBalle;
    }

    public void reset() {
        for (VueTortue t : tortues) {
            t.getTortue().reset();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color c = g.getColor();

        Dimension dim = getSize();
        g.setColor(Color.white);
        g.fillRect(0, 0, dim.width, dim.height);
        g.setColor(c);

        showTurtles(g);
        g.setColor(Color.BLUE);
        g.drawOval(277, 202, 46, 46);
        g.drawRect(0, (HEIGHT/2)-50, 8, 100);
        g.drawRect(this.getWidth()-9, (HEIGHT/2)-50, 8, 100);
    }

    public void showTurtles(Graphics g) {
        for (VueTortue t : tortues) {
            t.drawTurtle(g);
        }
    }

    public Tortue mousePressed(MouseEvent e) {
        for (VueTortue vT : tortues) {
            if (abs(e.getX() - vT.getTortue().getX()) < 10 && abs(e.getY() - vT.getTortue().getY()) < 10 && !VueTortueBalle.class.isInstance(vT)) {
                return vT.getTortue();
            }
        }
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

}
