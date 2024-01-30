package model;

import graphics.MapPanel;

import java.awt.*;

/**
 * A ciszternákért felelős osztály, amely a model.Field osztály egyik leszármazottja. A szerelőknek ide
 * kell eljuttatni a vizet a játék során, illetve itt tudnak felvenni új pumpákat és véletlenszerűen
 * keletkező új csöveket is.
 */
public class CisternField extends Field {

    /**
     * Nyilvántartja a ciszternán található pumpák számát.
     */
    private int pumpInventory = 10;

    /**
     * A ciszterna pumpinventory-jában lévő pumpákat vizsgálja ez a metódus. Amennyiben 1
     * vagy több pumpa elérhető, átad egy pumpát a Mechanicnak, trueval tér vissza, más esetben falseal.
     * @param m
     * @return
     */
    @Override
    public boolean pickUpPump(Mechanic m) {
        if (pumpInventory > 0) {
            //System.out.println("\t-> m.setPumpInventory( + 1)\n\t<-m.setPumpInventory");
            m.setPumpInventory(m.getPumpInventory() + 1);
            pumpInventory--;
            UI.pickedUpPump(this);
            //System.out.println("Pump Inventory of the pump: " + getPumpInventory());
            return true;
        }
        UI.invalidAction("cistern empty");
        return false;
    }

    /**
     * A csövek létrehozása. Visszatérési értéke egy pipeField objektum, melynek a source attribútuma
     * a ciszternára a destination pedig egy emptyfield-re van inicializálva.
     */
    public PipeField producePipe() {
            if (Math.random() < 0.3) {
                PipeField pipe = new PipeField();
                pipe.setDestination(this);
                this.AddNeighbour(pipe);
                Game.getFields().add(pipe);
                UI.generatePipe(this, pipe);
                //System.out.println("New " + pipe + " pipe generated in " + this + " cistern.");
                return pipe;
                }
        return null;
    }

    /**
     * A csiszternában lévő pumpák számának változtatása.
     * @param pumpInventory
     */
    public void setPumpInventory(int pumpInventory) {
        //System.out.println("->setPumpInventory(" + pumpInventory + ");");
        this.pumpInventory = pumpInventory;
        //System.out.println("<-setPumpInventory(" + pumpInventory + ");");
    }

    /**
     * A ciszternában lévő pumpák számának lekérdezése.
     */
    public int getPumpInventory() {
        return pumpInventory;
    }

    /**
     * Egy model.Water típusú objektum ciszternára való “lépésekor” egy ponttal növekszik a
     * model.Mechanic-ok pontja. Ehhez a model.Game osztály AddMechanicPoints() metódusát hívja meg.
     */
    @Override
    public void setCurrentWater(Water w){
        //System.out.println("->cistern.setCurrentWater()");
        Game.addMechanicPoints();
        UI.addMechanicPoint(this);
        //System.out.println("<-cistern.setCurrentWater()");
        //System.out.println(this + " the water reached the cistern, the mechanics got a point!");
    }

    /**
     * A lépésenként meghívott függvények
     */
    @Override
    public void step(){
        producePipe();
        flowWater();
    }

    /**
     * A csövek elhelyezése a cisternára.
     * @param pf
     * @return
     */

    @Override
    public boolean placePipe(PipeField pf) {
        pf.setDestination(this);
        this.AddNeighbour(pf);
        return true;
    }

    /**
     * Grafikus megjelenítést végző függvény.
     * @param g
     */
    @Override
    public void DrawMe(Graphics g) {
        Game.mapPanel.DrawCistern(this,g);
    }

    /**
     * A csövek felvétele a cisternáról.
     * @param pipe
     * @param m
     * @return
     */
    @Override
    public boolean pickUpPipe(PipeField pipe, Mechanic m) {
        if (m.getPipeInventory() == null) {
            for (Field f : neighbours) {
                if (f == pipe) {
                    m.setPipeInventory(pipe);
                    pipe.setInInventory(true);
                    pipe.RemoveNeighbour(pipe.getDestination());
                    pipe.setSource(null);
                    //pipe.setDestination(null);
                    UI.pickUp(pipe);
                    //System.out.println(m.getName() + " picked up " + pipe + " pipe from " + f + " field!");
                    return true;
                }
            }
        }
        else{
            UI.inventoryFull();
        }
        //System.out.println("      <-p.pickUpPipe(pipe,m)");
        return false;
    }
}
