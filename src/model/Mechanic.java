package model;

import graphics.MapPanel;

import java.awt.*;

/**
 * A szerelőkért felelős osztály, amely a model.Player osztály leszármazottja. A pumpákkal és a
 * csövekkel kapcsolatos műveleteket valósítja meg.
 */
public class Mechanic extends Player {

    /**
     * Pumparaktár
     */
    private int pumpInventory;
    /**
     * Csőraktár
     */
    private PipeField pipeInventory;

    /**
     * Játékos típus
     * @param Name
     * @param field
     */
    public Mechanic(String Name, Field field) {
        super(Name, field);
    }
    /**
     * Beállítja a szerelő pumparaktárát.
     * @param pumpInventory
     */
    public void setPumpInventory(int pumpInventory) {
        this.pumpInventory = pumpInventory;
        //System.out.println("Pumpák száma: " + pumpInventory);
    }

    /**
     * Lekéri a szerelő pumparaktárát.
     */
    public int getPumpInventory() {
        return pumpInventory;
    }

    /**
     * Beállítja a szerelő csőraktárát.
     * @param pipe
     */
    public void setPipeInventory(PipeField pipe) {
        this.pipeInventory = pipe;
        //System.out.println("Csövek száma: " + pipeInventory);
    }

    /**
     * Lekéri a szerelő csőraktárát.
     * @return
     */
    public PipeField getPipeInventory() {
        return pipeInventory;
    }

    /**
     * A cső vagy pumpa megjavításáért felelős függvény.
     */
    @Override
    public void fix() {
        this.getField().fix();
        Game.nextPlayer();
    }

    /**
     * A szerelő elhelyez egy új csövet a csőhálózatban
     */
    @Override
    public void installPipe() {
        if (pipeInventory == null) {
            UI.inventoryEmpty();
            return;
        }
        this.getField().placePipe(pipeInventory);
        UI.placePipe(this, pipeInventory);//ez nem jo
        setPipeInventory(null);
        Game.nextPlayer();
        //System.out.println("  <-m.installPipe(ef, input, output)");
    }

    /**
     * A szerelő elhelyez egy új pumpát a csőhálózaton
     */
    @Override
    public void installPump() {
        if (pumpInventory == 0){
            UI.inventoryEmpty();
            return;
        }
        //System.out.println("  ->m.installPump(ef, input, output)");
        PumpField pf = new PumpField();
        this.getField().placePump(pf);

        UI.placePump(this, pf);
        setPumpInventory(getPumpInventory()-1);
        Game.nextPlayer();
        //System.out.println("  <-m.installPump(ef, input, output)");
    }

    /**
     * A szerelő felvesz a ciszternáról egy pumpát.
     */
    @Override
    public void pickUpPump() {
        //this.setPumpInventory(1);
        //System.out.println("Pump Inventory of mechanic: "+pumpInventory+ "\n->getfield().pickUpPump()");
        if(getField().pickUpPump(this)) {
            //System.out.println("Pump Picked Up");
            Game.nextPlayer();
        }
        else{UI.canNotPickUp();}
            //System.out.println("Could not pick Up More Pumps");
        //System.out.println("<-getfield().pickUpPump()\nPump Inventory of mechanic: "+pumpInventory);
    }

    /**
     * A szerelő felvesz a ciszternáról egy csövet.
     * @param pipe
     */
    @Override
    public void pickUpPipe(PipeField pipe) {
        if(!pipe.isOccupied()){
            //System.out.println("  ->m.pickUpPipe(pipe)");
            if (this.getField().pickUpPipe(pipe,this)){
                Game.nextPlayer();
            }
            else{
                UI.inventoryFull();
            }
        }
        else{
            UI.canNotPickUp();
        }

    }

    /**
     * A szerelő elhelyez egy pumpát egy csövet kettévágva, amennyiben van nála pumpa, és már nem osztott az a cső
     */
    @Override
    public void insertPump(){

        //System.out.println("  ->m.insertPump()");
        if (pumpInventory == 0) return;
        this.getField().insertPump();
        setPumpInventory(getPumpInventory()-1);
        //System.out.println("  <-m.insertPump()");
    }

    /**
     * Grafikus megjelenítéshez tulajdonságok lekérdezése
     * @return
     */
    @Override
    public String getParameter() {
        String parameter = super.getParameter();
        parameter += "\nPipe Inventory: ";
        if (pipeInventory != null) {
            parameter += "1 ";
        }
        else { parameter += "0 "; }

        parameter += "\nPump Inventory: " + pumpInventory;

        return parameter;
    }

    /**
     * Grafikus megjelenítéshez kirajzolás
     * @param g
     */
    @Override
    public void DrawMe(Graphics g) {
        Game.mapPanel.DrawMechanic(this,g);
    }
}
