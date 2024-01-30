package model;

import graphics.MapPanel;

import java.awt.*;

/** A szabotőrökért felelős osztály, amely a model.Player osztály leszármazottja. A pumpákkal és a
csövekkel kapcsolatos műveleteket valósítja meg. */
public class Saboteur extends Player{

    /**
     * Játékos típus.
     * @param Name
     * @param field
     */
    public Saboteur(String Name, Field field) {
        super(Name, field);
    }

    /**
     * A cső csúszóssá tétele.
     */
    @Override
    public void makePipeSlippery(){
        //System.out.println("->s.makePipeSlippery");
        if(!this.getField().makePipeSlippery()) {
            //System.out.println("Invalid Move");
            return;
        }
        Game.nextPlayer();
        //System.out.println("<-s.makePipeSlippery");
    }


    /**
     * Tulajdonságok grafikus megjelenítése
     * @return
     */
    @Override
    public String getParameter() {
        String parameter = super.getParameter();

        return parameter;
    }


    /**
     * A szabotőr grafikus kirajzolása
     * @param g
     */
    @Override
    public void DrawMe(Graphics g) {
        Game.mapPanel.DrawSaboteur(this,g);
    }
}
