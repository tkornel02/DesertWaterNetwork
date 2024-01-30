package model;

import graphics.MapPanel;

import java.awt.*;

/**
 * A pumpák működéséért felelős osztály, amely a model.Field osztály egyik leszármazottja,
 * a pumpákon végezhető műveleteket valósítja meg. Illetve tárolja a hozzá kötött elemeket listában.
 */
public class PumpField extends Field {

    /**
     * A pumpa aktuális állapota, törött vagy jól működik.
     */
    private boolean broken;

    /**
     * A pumpa tárolt víztartalma
     */
    private Water storedWater;

    /**
     * model.Step interfész felüldefiniált lépése, véletlenszerűen eltörheti a pumpát, tovább folyatja a vizet.
     */
    @Override
    public void step() {
        if (!broken){
            if (Math.random() < 0.01) {
                this.breakPump();
                UI.brokenPump(this);
            } else {
                //System.out.println("model.PumpField is still working.");
            }
        }
        flowWater();
    }

    /**
     * Átállítja a destination mező currentWater objektumát a jelenlegi mező vízére, azt pedig törli.
     */
    @Override
    public void flowWater() {
        if (broken || destination == null){
            return;
        }
        if(destination.blocked){
            this.setBlocked(true);
            UI.blockedWaterPath(this);
            return;
        }
        else{
            this.setBlocked(false);
        }
        if (storedWater != null) {
            destination.setCurrentWater(this.storedWater);
            this.storedWater = null;
        }
        if (currentWater != null) {
            this.storedWater = this.currentWater;
            this.currentWater = null;
        }
    }

    public Water getStoredWater() {
        return storedWater;
    }

    /**
     * Ellenőrzi, hogy a pumpa nem törött-e, ugyanis ekkor nem lehet állítani, majd meghívja a changePumpSettingset.
     * @param input
     * @param output
     * @return
     */
    @Override
    public boolean adjust(PipeField input, PipeField output) {
        //System.out.println("\t->field.adjust");
        //System.out.println("\tIs the pump broken? Y/N");
        if (broken) {
            //System.out.println("\tY");
            //System.out.println("\t<-field.adjust: false");
            return false;
        }
        this.changePumpSettings(input, output);
        //System.out.println("\t<-field.adjust: true");
        return true;
    }

    /**
     * Ellenőrzi, hogy a pumpa nem törött-e, ugyanis ekkor nem lehet állítani, majd meghívja a changePumpSettingset.
     * @param input
     * @param output
     */
    public void changePumpSettings(PipeField input, PipeField output) {
        // A régi csövek blokkoltságának beállítása.
        UI.changePumpSettings(this);
        this.source.setBlocked(true);
        this.destination.setBlocked(true);
        UI.blockedCheck((PipeField) this.source, (PipeField) this.destination,true);
        //System.out.println("\tN");
        //System.out.println("\t\t->this.changePumpSettings(input, output);");
        // A pumpa új aktív csöveit beállítja.
        this.source = input;
        this.destination = output;
        UI.changePumpSettings(this);
        // A pumpa új aktív csöveinek blokkoltságának beállítása.
        this.source.setBlocked(false);
        this.destination.setBlocked(false);
        UI.blockedCheck((PipeField) this.source, (PipeField) this.destination,false);

        //System.out.println("\t\t<-this.changePumpSettings(input, output);");
        //System.out.println("\tCurrent model.PumpField settings changed. \nNew source: " + input + "\t New destination: " + output);
    }


    /**
     * Ellenőrzi, hogy törött-e a pumpa, amennyiben nem, broken attribútumot true-ra állítja.
     */
    @Override
    public boolean breakPump() {
        if (broken) {
            //System.out.println("Pump " + this + " is already broken.");
            return false;
        }
        this.setBroken(true);
        UI.breakPump(this);
        //System.out.println("Pump " + this + " has been broken.");
        return true;
    }

    /**
     * Ellenőrzi, hogy törött-e a pumpa, amennyiben igen, broken attribútumot false-ra állítja.
     */
    @Override
    public boolean fix() {
        if (!broken) {
            UI.invalidAction("fix not broken pipe");
            return false;
        }
        else {
            setBroken(false);
            UI.fixPump(this);
            return true;
        }
    }

    /**
     * Beállítja a pumpát töröttre.
     * @param broken
     */
    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    /**
     * Lekéri, hogy törött e a pumpa.
     * @return
     */
    public boolean isBroken() {
        return broken;
    }

    /**
     * Pumpán állva cső elhelyezése.
     * @param pf
     * @return
     */
    @Override
    public boolean placePipe(PipeField pf){
        this.AddNeighbour(pf);
        if(pf.getDestination() != null){
            pf.setSource(this);
            pf.setDestination(null);
        } else{
            pf.setDestination(this);
        }
        pf.setInInventory(false);
        return true;
    }

    /**
     * Grafikus megjelenítés.
     * @param g
     */
    @Override
    public void DrawMe(Graphics g) {
        Game.mapPanel.DrawPump(this,g);
    }
}

