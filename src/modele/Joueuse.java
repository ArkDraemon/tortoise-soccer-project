package modele;

import java.awt.Point;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import vue.Cage;
import vue.Principale;
import vue.Terrain;

/**
 * @author Victor Lequay
 * @creation 7 mai 2014
 */
public class Joueuse extends Tortue implements Runnable {

    protected String nom;
    protected String nomEquipe;
    protected List<Joueuse> equipe;
    protected List<Joueuse> adversaires;
    private Balle balle;
    private Terrain terrain;
    private Cage cage;
    private boolean bonneJoueuse;
    private int distancePerception;
    private volatile boolean paused;
    private Strategie strategie;
    private Objectif objectif;
    private Principale principale;

    public Joueuse(Balle balle, String nom, String nomEquip, int x, int y, Terrain t, Principale p) {
        super(x, y);
        this.nom = nom;
        this.nomEquipe = nomEquip;
        this.balle = balle;
        equipe = new ArrayList<>();
        adversaires = new ArrayList<>();
        terrain = t;
        paused = true;
        bonneJoueuse = false;
        distancePerception = 100;
        principale = p;
        this.strategie = StrategieAttaquant.getInstance(t);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters">
    public List<Joueuse> getEquipe() {
        return equipe;
    }

    public List<Joueuse> getAdversaires() {
        return adversaires;
    }

    public Balle getBalle() {
        return balle;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Objectif getObjectif() {
        return objectif;
    }

    public Cage getCage() {
        return cage;
    }

    public int getDistancePerception() {
        return distancePerception;
    }

    public String getNom() {
        return nom;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Setters">
    public void setObjectif(Objectif objectif) {
        this.objectif = objectif;
    }

    public boolean isBonneJoueuse() {
        return bonneJoueuse;
    }

    public void setBonneJoueuse(boolean bonneJoueuse) {
        this.bonneJoueuse = bonneJoueuse;
    }

    public void setDistancePerception(int distancePerception) {
        this.distancePerception = distancePerception;
    }

    public void setCage(Cage c) {
        this.cage = c;
    }

    public void setBalle(Balle b) {
        this.balle = b;
        this.balle.setDir(this.dir);
        this.balle.setPosition(this.x - 5, this.y);
    }

    //</editor-fold>
    public void pause() {
        this.paused = true;
    }

    public void restart() {
        this.paused = false;
    }

    public void passerBalle(Joueuse j) {
        if (j.isBonneJoueuse() || this.isBonneJoueuse()) {
            j.setBalle(balle);
            terrain.setPossesseurBalle(j);
            this.balle = null;
        } else {
            if (new Random().nextBoolean()) {
                j.setBalle(balle);
                terrain.setPossesseurBalle(j);
                this.balle = null;
            }
        }
    }

    public void perdreBalle(Joueuse j) {
        if (j.isBonneJoueuse() || !this.isBonneJoueuse()) {
            j.setBalle(balle);
            terrain.setPossesseurBalle(j);
            this.balle = null;
        } else {
            if (new Random().nextBoolean()) {
                j.setBalle(balle);
                terrain.setPossesseurBalle(j);
                this.balle = null;
            }
        }
    }

    private int getDirTo(Point p) {
        return getDirTo(p.x, p.y);
    }

    private int getDirTo(int x, int y) {
        double dx = x - this.x;
        double dy = y - this.y;
        if (dx == 0) {
            if (dy > 0) {
                return 90;
            } else {
                return -90;
            }
        }
        int angle = (int) Math.round(Math.atan(dy / dx) / Tortue.ratioDegRad);
        if (dx < 0) {
            if (angle < 0) {
                return 180 + angle;
            } else {
                return angle - 180;
            }
        }
        return angle;
    }

    public void gererBalle() {
        if (this.balle != null) {
            this.balle.setPosition(this.x + 5, this.y);
            if (this.cage.isInCage(this)) {
                System.out.println("BUUUUUT");
                if (this.nomEquipe.equals("equipe2")) {
                    this.principale.setScoreEquipe2(this.principale.getScoreEquipe2() + 1);
                } else {
                    this.principale.setScoreEquipe2(this.principale.getScoreEquipe1() + 1);
                }
                
            }
        }
        for (Joueuse t : this.equipe) {
            if (getEuclidean(t) < 15) {
                if (this.balle != null) {
                    passerBalle(t);
                }
            }
        }
        for (Joueuse t : this.adversaires) {
            if (getEuclidean(t) < 15) {
                if (this.balle != null) {
                    perdreBalle(t);
                }
            }
        }
    }

    public void ajouterAmie(Joueuse t) {
        this.equipe.add(t);
    }

    public void ajouterAdversaire(Joueuse j) {
        this.adversaires.add(j);
    }

    public double getEuclidean(Objectif o) {
        return sqrt(pow(o.getPosition().getY() - this.y, 2) + pow(o.getPosition().getX() - this.x, 2));
    }

    @Override
    public void run() {
        while (true) {
            if (!paused) {
                this.strategie.definirObjectif(this);
                if (this.objectif != null) {
                    this.setDir(this.getDirTo(this.objectif.getPosition()));
                    avancer(2);
                }
                gererBalle();
                setChanged();
                notifyObservers();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Joueuse.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
