package model;

import graphics.MapPanel;

import java.awt.*;

/**
 * A forrásokért felelős osztály, amely a model.Field osztály egyik leszármazottja.
 */
public class SourceField extends Field {

    /**
     * Elindít egy egység vizet a vízforrásból, mely továbbfolyik a vízhálózatra.
     */
    @Override
    public void flowWater() {
        for (Field field: neighbours) {
            if(!field.isBlocked()){
                Water water = new Water();
                setCurrentWater(water);
                UI.startWater(this,this.currentWater);
                field.setCurrentWater(water);
                setCurrentWater(null);
            }
        }
    }

    /**
     * Forrástól kiinduló cső elhelyeze
     * @param pf
     * @return
     */
    @Override
    public boolean placePipe(PipeField pf){
        pf.setSource(this);
        this.AddNeighbour(pf);
        return true;
    }

    /**
     * Forrásról cső felvétele
     * @param pf
     * @param m
     * @return
     */
    @Override
    public boolean pickUpPipe(PipeField pf, Mechanic m){
        return false;
    }

    /**
     * Grafikus megjelenítés
     * @param g
     */
    @Override
    public void DrawMe(Graphics g) {
        Game.mapPanel.DrawSource(this,g);
    }
}
