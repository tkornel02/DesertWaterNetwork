package model;

import graphics.MapPanel;

import java.awt.*;

/**
 * A csövekért felelős osztály, amely a model.Field osztály egyik leszármazottja, a csöveken végezhető
 * műveleteket valósítja meg.
 */
public class PipeField extends Field{

    private boolean inInventory;
    /**
     * A cső állapota.
     */
    private boolean punctured;
    /**
     * A csövön tartózkodik-e játékos.
     */
    private boolean occupied;

    /**
     * A cső ragadóssága
     */
    private boolean sticky;

    /**
     * A cső csúszósága
     */
    private boolean slippery;

    /**
     * A cső tisztaságához szükséges lépések száma
     */
    private int stepsToBeClean;

    /**
     * Random kapott szám, amely meghatározza, hogy a cső mennyi lépés múlva törhető. Eltörés után állítódik
     */
    private int stepsToBePuncturable;

    /**
     * Szabotőr, vagy random esemény hatására eltörik a cső.
     */

    public void setInInventory(boolean inInventory){
        this.inInventory = inInventory;
    }
    public boolean getInInventory(){return inInventory;}

    /**
     * A cső eltörése
     * @return
     */
    @Override
    public boolean puncturePipe() {
        //System.out.println("Is the Pipe Punctured? [y/n]");
        //System.out.println("\t->puncturePipe()");
        if(!punctured && stepsToBePuncturable == 0) {
            //System.out.println("N");
            //System.out.println("\t\t->setPunctured(true)");
            setPunctured(true);
            UI.puncturePipe(this);
            //System.out.println("\t\t<-setPunctured(true)");
            //System.out.println("\t"+this + "The Pipe Is Punctured Now");
            //System.out.println("\t<-puncturePipe()");
            return true;
        }
        else
        {
            //model.Player player = model.Game.getCurrentPlayer();
            UI.puncturePuncturedPipe(Game.getCurrentPlayer(),this);
            //System.out.println("Y");
            //System.out.println("\t"+this + "\tPipe Was Already punctured");
            //System.out.println("\t<-puncturePipe()");
            return false;
        }
    }

    /**
     * A cső törhetőségéhez lépésenként csökkenti az ezt ellenőrző értéket
     * A cső tisztaságához lépésenként csökkenti az ezt ellenőrző értéket
     * Lépteti a vizet
     */
    public void step(){
        if(stepsToBeClean == 1){
            setSticky(false);
            setSlippery(false);
            UI.pipeCleaned(this);
        }
        if(stepsToBePuncturable > 0){
            setStepsToBePuncturable(stepsToBePuncturable - 1);
        }
        if(stepsToBeClean > 0){
            setStepsToBeClean(stepsToBeClean - 1);
        }
        flowWater();
    }

    /**
     * A szerelők megjavítják a csövet, amennyiben az törött.
     */
    @Override
    public boolean fix() {
        //System.out.println("\nIs the Pipe Punctured? (y/n)" );

        if(punctured){
            //System.out.println("Punctured: "+ punctured +"\n-> Y - It is punctured and can be repaired" );
            //System.out.println("\t->mechanic.fix");
            setPunctured(false);

            int random = (int) ((Math.random() * 5) + 1);
            setStepsToBePuncturable(random);
            UI.fixPipe(this);
            //System.out.println("\t<-mechanic.fix \n<-Punctured: " + punctured);
            return true;
        }
        else {
            UI.fixGoodPipe(this);
            //System.out.println("Punctured: " + punctured + "\n-> N - It is not punctured, no need to fix it" + "\n<-" + punctured);
            return false;
        }
    }

    /**
     * Cső elhelyezése csőhöz kötve
     * @param pf
     * @return
     */
    @Override
    public boolean placePipe(PipeField pf){
            pf.setNeighbours(null);
            pf.setSource(this);
            pf.setDestination(null);
            this.AddNeighbour(pf);
            this.setInInventory(false);
            return true;
    }

    /**
     * Pumpa elhelyezése csőhöz kötve
     * @param pf
     * @return
     */
    @Override
    public boolean placePump(PumpField pf){
        if(this.destination == null){
            this.destination = pf;
            Game.getFields().add(pf);
            pf.setSource(this);
            this.AddNeighbour(pf);
            return true;
        }
        return false;
    }


    /**
     * Amennyiben a törött a cső, a víz kifolyik, és pontot kapnak a szabotőrök.
     */
    @Override
    public void flowWater() {
        //System.out.println("\t->pipe.flowWater");
        if(currentWater == null){
            //System.out.println("\t<-pipe.flowWater");
            return;
        }
        if (isPunctured() || this.destination == null) {
            this.currentWater.setCurrentField(null);
            setCurrentWater(null);
            Game.addSaboteurPoints();
            UI.addSaboteurPoint(this);
            return;
            //System.out.println(this + " pipe is broken, the water flowed out, saboteurs got a point!");
            //System.out.println("\t<-pipe.flowWater");
        }
        if(destination.blocked){
            this.setBlocked(true);
            UI.blockedWaterPath(this);
        }
        else{
            this.setBlocked(false);
            //System.out.println("Pipe vizek"+ currentWater);
            super.flowWater();
        }
        //System.out.println("\t<-pipe.flowWater");
    }

    /**
     * A cső csúszóssá tétele.
     * @return
     */
    @Override
    public boolean makePipeSlippery(){
        if(!sticky) {
            setSlippery(true);
            setStepsToBeClean(6);
            //System.out.println("\tPipe " + this + " became slippery");
            UI.makePipeSlippery(this);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * A cső ragadóssá tétele.
     * @return
     */
    @Override
    public boolean makePipeSticky(){
        if(!slippery) {
            setSticky(true);
            setStepsToBeClean(6);
            //System.out.println("\tPipe " + this + " became sticky");
            UI.makePipeSticky(this);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * A cső megfelezése, és pumpa letétele.
     * Sikeres, ha a cső még nem volt megfelezve.
     * A kettéosztott cső mindkét fele rendelkezik az eredeti cső tulajdonságaival.
     * Legvégül az aktor a maga alá lehelyezett pumpán foglal helyet.
     * @param
     * @return
     */
    @Override
    public boolean insertPump() {
        //System.out.println("\t->insertPump(pump)");

        PumpField pump = new PumpField();
        PipeField pipe = new PipeField();

        Game.getFields().add(pump);
        Game.getFields().add(pipe);

        pipe.setPunctured(this.punctured);
        pipe.setSticky(this.sticky);
        pipe.setStepsToBeClean(this.stepsToBeClean);
        pipe.setStepsToBePuncturable(this.stepsToBePuncturable);
        pipe.setSource(pump);
        pipe.setDestination(this.destination);
        pipe.AddNeighbour(this.destination);
        //pipe.AddNeighbour(pump);

        pump.setSource(this);
        pump.setDestination(pipe);
        pump.AddNeighbour(this);
        pump.AddNeighbour(pipe);

        this.RemoveNeighbour(this.destination);
        this.setDestination(pump);
        //this.AddNeighbour(pump);

        UI.insertPump(this, pipe, pump);
        this.getPlayers().get(0).moveTo(pump);

        return true;
        //System.out.println("\t<-insertPump(pump)");
    }

    /**
     * A cső töröttségi állapotát állítja be.
     * @param punctured
     */
    public void setPunctured(boolean punctured) {
        this.punctured = punctured;
    }

    /**
     * A cső töröttségi állapotát adja vissza.
     * @return
     */
    public boolean isPunctured() {
        return punctured;
    }

    /**
     * A cső elfoglaltságát állítja be.
     * @param occupied
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /**
     * A cső elfoglaltságát adja vissza.
     * @return
     */
    @Override
    public boolean isOccupied() {
        if(players.size() > 0)  occupied = true;
        return occupied;
    }

    /**
     * A cső eltörhetőségéig szükséges lépések számát adja vissza.
     * @param 
     */
    public int getStepsToBePuncturable() {
        return stepsToBePuncturable;
    }
    
    /**
     * A cső eltörhetőségéig szükséges lépések számát állítja be.
     * @param stepsToBePuncturable
     */
    
    public void setStepsToBePuncturable(int stepsToBePuncturable) {
        this.stepsToBePuncturable = stepsToBePuncturable;
    }

    /**
     * Ellenőrzi, hogy rámozoghat-e a csőre a játékos
     * @param p
     * @return
     */
    @Override
    public boolean addPlayer(Player p) {
       // System.out.println("\tIs the field occupied?  Y/N\n");
        if (this.players.size()==0) {
            //System.out.println("N");
            //System.out.println("\t->addPlayer(player)");
            this.players.add(p);
            //System.out.println("\t<-addPlayer(player)");
            return true;
        }
        return false;
    }
    /**
     * A cső ragadósságát adja vissza.
     */
    @Override
    public boolean isSticky(){
        return sticky;
    }

    /**
     * A cső ragadósságát állítja be.
     */
    public void setSticky(boolean sticky){
        this.sticky = sticky;
    }

    /**
     * A cső csúszósságát adja vissza.
     */
    @Override
    public boolean isSlippery(){
        return slippery;
    }

    /**
     * A cső csúszósságát állítja be.
     */
    public void setSlippery(boolean slippery){
        this.slippery = slippery;
    }

    /**
     * Visszadja, hány lépés múlva veszti el a cső a ragadós/csúszós tulajdonságát.
     * @return
     */
    public int getStepsToBeClean(){
        return stepsToBeClean;
    }

    /**
     * Beállítja, hány lépés múlva veszti el a cső a ragadós/csúszós tulajdonságát.
     * @param stepsToBeClean
     */
    public void setStepsToBeClean(int stepsToBeClean){
        this.stepsToBeClean = stepsToBeClean;
    }

    /**
     * Grafikus megjelenítés.
     * @param g
     */
    @Override
    public void DrawMe(Graphics g){
        Game.mapPanel.DrawPipe(this,g);
    }

}
