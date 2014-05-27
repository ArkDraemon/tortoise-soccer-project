package modele;

// package logo;
import java.awt.Color;
import java.awt.Point;
import java.util.*;

/**
 * ***********************************************************************
 *
 * Un petit Logo minimal qui devra etre ameliore par la suite
 *
 * Source originale : J. Ferber - 1999-2001
 *
 * Cours de DESS TNI - Montpellier II
 *
 * @version 2.0
 * @date 25/09/2001
 *
 *************************************************************************
 */
/**
 * La classe Tortue qui se deplace en coordonnees polaires
*
 */
public class Tortue extends Observable implements Objectif{

    // Attributs statiques	
    public static final int rp = 10, rb = 5; // Taille de la pointe et de la base de la fleche
    public static final double ratioDegRad = 0.0174533; // Rapport radians/degres (pour la conversion)

    protected int x, y;	// Coordonnees de la tortue
    protected int dir;	// Direction de la tortue (angle en degres)
    protected boolean crayon; // par defaut on suppose qu'on dessine
    protected int coul;

    // Methodes
    public void setColor(int n) {
        coul = n;
    }

    public Color getColor() {
        return decodeColor(coul);
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    
    public Tortue() { // FeuilleDessin f) {
        // feuille = f;
        // feuille.addTortue(this);	
        reset();
    }
    
    public Tortue(int x, int y){
        this.x = x;
        this.y = y;
        dir = -90;
        coul = 0;
        crayon = true;
    }

    public void reset() {
        // on initialise la position de la tortue
        x = 0;
        y = 0;
        dir = -90;
        coul = 0;
        crayon = true;
    }

    public void setPosition(int newX, int newY) {
        x = newX;
        y = newY;
    }

    /**
     * les procedures de base de fonctionnement de la tortue
     * @param dist
     */
    // avancer de n pas
    public void avancer(int dist) {
        int newX = (int) Math.round(x + dist * Math.cos(ratioDegRad * dir));
        int newY = (int) Math.round(y + dist * Math.sin(ratioDegRad * dir));

        x = newX;
        y = newY;
        if(x > 600){
            x = 600;
        }
        if(x < 0) {
            x = 0;
        }
        if(y < 0){
            y = 0;
        }
        if(y > 400){
            y = 400;
        }
    }
    
    public Color decodeColor(int c) {
        switch (c) {
            case 0:
                return (Color.black);
            case 1:
                return (Color.blue);
            case 2:
                return (Color.cyan);
            case 3:
                return (Color.darkGray);
            case 4:
                return (Color.red);
            case 5:
                return (Color.green);
            case 6:
                return (Color.lightGray);
            case 7:
                return (Color.magenta);
            case 8:
                return (Color.orange);
            case 9:
                return (Color.gray);
            case 10:
                return (Color.pink);
            case 11:
                return (Color.yellow);
            default:
                return (Color.black);
        }
    }

    // aller a droite
    public void droite(int ang) {
        dir = (dir + ang) % 360;
    }

    // aller a gauche
    public void gauche(int ang) {
        dir = (dir - ang) % 360;
    }

    // pour changer de couleur de dessin
    public void couleur(int n) {
        coul = n % 12;
    }

    public void couleurSuivante() {
        couleur(coul + 1);
    }

    @Override
    public Point getPosition() {
        return new Point(this.x, this.y);
    }
}
