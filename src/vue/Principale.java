package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import modele.*;
import vue.tortue.VueTortueBalle;
import vue.tortue.VueTortueJoueuse;

/**
 * @author Victor Lequay
 * @creation 9 mai 2014
 */
public class Principale extends JFrame implements ActionListener {

    private Terrain terrain;
    private Cage but1;
    private Cage but2;
    private List<Joueuse> equipe1;
    private List<Joueuse> equipe2;
    private Balle balle;
    List<Thread> tList;

    public Principale() {
        super("Tortoise Soccer Project : Jullian - Lequay - Ringot");
        initComponents();
        tList = new ArrayList<>();
        initialiserPartie();
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());
        // Boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));
        getContentPane().add(buttonPanel, "North");

        JButton b = new JButton("Démarrer");
        b.addActionListener(this);
        buttonPanel.add(b);
        b = new JButton("Interrompre");
        b.addActionListener(this);
        buttonPanel.add(b);
        b = new JButton("Réinitialiser");
        b.addActionListener(this);
        buttonPanel.add(b);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        but1 = new Cage(new Rectangle(0, 150, 6, 100), new Rectangle(0, 150, 10, 100));
        but2 = new Cage(new Rectangle(600 - 7, 150, 6, 100), new Rectangle(600 - 11, 150, 10, 100));
        terrain = new Terrain(but1, but2); //500, 400);
        getContentPane().add(terrain, "Center");
        pack();
        this.setResizable(false);
        setVisible(true);
    }

    private void initialiserPartie() {
        balle = new Balle();
        balle.setPosition(300, 200);
        balle.couleur(4);
        VueTortueBalle vueBalle = new VueTortueBalle(balle);
        terrain.addTortue(vueBalle);
        Joueuse joueuse;
        VueTortueJoueuse vue;

        equipe1 = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            joueuse = new Joueuse(null, "joueuse_" + i, 100 + 100 * (i % 2), Terrain.HEIGHT / 2 - 100 + 100 * (i % 3), terrain);
            joueuse.couleur(7);
            joueuse.setDir(0);
            joueuse.setCage(this.but2);
            vue = new VueTortueJoueuse(joueuse);
            terrain.addTortue(vue);
            equipe1.add(joueuse);
        }
        for (Joueuse j : equipe1) {
            for (Joueuse k : equipe1) {
                if (!j.getNom().equals(k.getNom())) {
                    j.ajouterAmie(k);
                }
            }
        }

        equipe2 = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            joueuse = new Joueuse(null, "joueuse_" + i, Terrain.HEIGHT + 100 * (i % 2), Terrain.HEIGHT / 2 - 100 + 100 * (i % 3), terrain);
            joueuse.couleur(8);
            joueuse.setDir(180);
            joueuse.setCage(but1);
            vue = new VueTortueJoueuse(joueuse);
            terrain.addTortue(vue);
            equipe2.add(joueuse);
        }
        equipe2.get(0).setBonneJoueuse(true);
        equipe1.get(0).setBonneJoueuse(true);
        equipe1.get(1).setBalle(balle);
        terrain.setPossesseurBalle(equipe1.get(1));
        for (Joueuse j : equipe2) {
            for (Joueuse k : equipe2) {
                if (!j.getNom().equals(k.getNom())) {
                    j.ajouterAmie(k);
                }
            }
            for (Joueuse k : equipe1) {
                j.ajouterAdversaire(k);
            }
        }
        for (Joueuse k : equipe1) {
            for (Joueuse q : equipe2) {
                k.ajouterAdversaire(q);
            }
        }

        for (Joueuse j : equipe1) {
            Thread t = new Thread(j);
            tList.add(t);
        }
        for (Joueuse j : equipe2) {
            Thread t = new Thread(j);
            tList.add(t);
        }
        for (Thread t : tList) {
            t.start();
        }
    }

    private void demarrerPartie() {
        //equipe2.get(0).restart();
        for (Joueuse j : equipe1) {
            j.restart();
        }
        for (Joueuse j : equipe2) {
            j.restart();
        }
    }

    private void pausePartie() {
        for (Joueuse t : equipe1) {
            t.pause();
        }
        for (Joueuse t : equipe2) {
            t.pause();
        }
    }

    private void reinitPartie() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String c = e.getActionCommand();

        if (c.equals("Démarrer")) {
            demarrerPartie();
        } else if (c.equals("Interrompre")) {
            pausePartie();
        } else if (c.equals("Réinitialiser")) {
            reinitPartie();
        }

        terrain.repaint();
    }

    // efface tout et reinitialise la feuille
    public void effacer() {
        terrain.reset();
        terrain.repaint();
    }

}
