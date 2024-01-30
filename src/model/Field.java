package model;

import graphics.GameGui;

import java.awt.*;
import java.util.ArrayList;

import java.util.LinkedHashSet;

/**
 * Osztály, mellyel reprezentáljuk a pályán található mezőket.
 */
public abstract class Field implements Step, Drawable{

    /**
     * Grafikus megjelenítést véégző objektum
     */
    protected GameGui gameGui;

    /**
     * A forrásmezője a Fieldnek.
     */
    protected Field source;
    /**
     * Célmezője a Fieldnek.
     */
    protected Field destination;
    /**
     * A model.Field szomszédjai.
     */
    protected LinkedHashSet<Field> neighbours = new LinkedHashSet<>();
    /**
     * A Fielden elhelyezkedő játékosok.
     */
    protected ArrayList<Player> players = new ArrayList<>();

    /**
     * A mező tartalmaz-e vizet, vagy sem.
     */
    protected Water currentWater;

    /**
     * A mezőelem gátolja a víz továbbhaladását.
     */
    protected boolean blocked;

    /**
     * Visszaadja, hogy a játékmező blokkolt-e.
     * @return
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Beállítja, hogy a játékmező blokkolt-e.
     * @param blocked
     */

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    /**
     * Beállítja a model.Field forrásmezőjét.
     * @param source
     */
    public void setSource(Field source) {
        this.source = source;
    }
    /**
     * Visszaadja a model.Field forrásmezőjét.
     */
    public Field getSource() {
        return source;
    }

    /**
     * Beállítja a model.Field célmezőjét.
     * @param destination
     */
    public void setDestination(Field destination) {
        this.destination = destination;
    }
    /**
     * Visszaadja a model.Field célmezőjét.
     */
    public Field getDestination() {
        return destination;
    }

    /**
     * Beállítja a model.Field szomszédjait.
     * @param neighbors
     */
//    public void setNeighbours(LinkedHashSet<model.Field> neighbors) {
//        this.neighbours = neighbors;
//    }

    /**
     * Hozzáad egy szomszédot a Fieldhez, kölcsönös műküdést biztosít.
     * @param other
     */
    public void AddNeighbour(Field other){
        neighbours.add(other);
        other.neighbours.add(this);
    }
    /**
     * Eltávolít egy szomszédot a Fieldnél, kölcsönös műküdést biztosít.
     * @param other
     */
    public void RemoveNeighbour(Field other){
        neighbours.remove(other);
        other.neighbours.remove(this);
    }

    /**
     * Beállítja a model.Field szomszédjait.
     * @param neighbours
     */

    public void setNeighbours(LinkedHashSet<Field> neighbours) {
        this.neighbours = neighbours;
    }
    /**
     * Visszaadja a model.Field szomszédjait.
     */
    public LinkedHashSet<Field> getNeighbours() {
        return neighbours;
    }

    /**
     * Visszaadja a model.Field szomszédjait index szerint
     */

    public Field getNeighbourByIdx(int i){
        return (Field)neighbours.toArray()[i];
    }
    /**
     * Beállítja a Fielden levő játékosokat.
     * @param players
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Visszaadja a Fielden levő játékosokat listában.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Fielden levő játékosokhoz hozzáad egy újat.
     * @param player
     * @return
     */
    public boolean addPlayer(Player player) {
        //System.out.println("\t->addPlayer(player)");
        this.players.add(player);
        //System.out.println("\t<-addPlayer(player)");
        return true;
    }

    /**
     * Visszaadja a ha foglalt a mező.
     * @return
     */

    public boolean isOccupied(){
        return false;
    }
    /**
     * Fielden levő játékosok közül elvesz egyet.
     * @param player
     */
    public void removePlayer(Player player) {
        this.players.remove(players.indexOf(player));
    }

    /**
     * Felülírandó, a vízfolyás működéséhez szükséges.
     */
    public void flowWater() {
        if(currentWater != null) {
            destination.setCurrentWater(this.getCurrentWater());
            this.setCurrentWater(null);
        }
    }

    /**
     * Felülírandó, a javítás működéséhez szükséges.
     */
    public boolean fix() {
        return false;
    }
    /**
     * Felülírandó, a pumpa elrontás működéséhez szükséges.
     */
    public boolean breakPump() {
        return false;
    }

    /**
     * Felülírandó, a cső törés működéséhez szükséges.
     */
    public boolean puncturePipe() {
        return false;
    }

    /**
     * Felülírandó, a csőfelvétel működéséhez szükséges.
     * @param pipe
     * @param m
     * @return
     */

    public boolean pickUpPipe(PipeField pipe, Mechanic m) {
        if (m.getPipeInventory() == null) {
            for (Field f : neighbours) {
                if (f == pipe) {
                    m.setPipeInventory(pipe);
                    pipe.setInInventory(true);
                    pipe.RemoveNeighbour(pipe.getSource());
                    //pipe.setSource(null);
                    pipe.setDestination(null);
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

    /**
     * Felülírandó, a pumpafelvétel működéséhez szükséges.
     * @param m
     * @return
     */
    public boolean pickUpPump(Mechanic m) {
        return false;
    }

    public boolean insertPump(){
        return false;
    }

    /**
     * Felülírandó, az átállítás működéséhez szükséges.
     * @param input
     * @param output
     * @return
     */
    public boolean adjust(PipeField input, PipeField output) {
          return false;
    }

    /**
     * Beállítja a tartalmazott vizet.
     * @param w
     */
    public void setCurrentWater(Water w) {
        this.currentWater = w;
        //System.out.println("field setcurrentwater");
        if(w != null){
            w.setCurrentField(this);
        }
    }

    /**
     * Lekéri a tartalmazott vizet-
     * @return
     */
    public Water getCurrentWater() {
        return this.currentWater;
    }

    /**
     * körönként a víz folyását biztosítja
     */
    public void step(){
        flowWater();
    }

    /**
     * Felülírandó, a cső csúszóssá tétele
     * @return
     */
    public boolean makePipeSlippery(){
        return false;
    }

    /**
     * Felülírandó, a cső ragadóssá tétele
     * @return
     */

    public boolean makePipeSticky(){
        return false;
    }

    /**
     * Felülirandó, A cső csúszósságát adja vissza.
     */
    public boolean isSlippery(){
        return false;
    }

    /**
     * Felülirandó, A cső felvételénél használjuk
     */
    public boolean getInInventory(){return false;}
    /**
     * Felülirandó, A cső ragadósságát adja vissza.
     */
    public boolean isSticky(){
        return false;
    }

    /**
     * Felülirandó, A pumpa töröttségét adja vissza.
     */
    public boolean isBroken(){return false;}
    /**
     * Felülirandó, A cső töröttségéi állapotás adja vissza.
     */
    public boolean isPunctured(){return false;}

    /**
     * Felülirandó, A cső lerakása
     */
    public boolean placePipe(PipeField pf){
        return false;
    }
    /**
     * Felülirandó, A pumpa lerakása
     */
    public boolean placePump(PumpField pf){return false;}

    /**
     * Grafikus megjelenítéshez szükséges.
     * @param g
     */
    public void DrawMe(Graphics g){}
}
