package model;

import graphics.GameGui;

import java.awt.*;

/**
 * A játékosokért felelős absztrakt ősosztály, amelyből a szerelők és a szabotőrök származnak le.
 */
public abstract class Player implements Drawable {

    /**
     * A játék grafikus felületét tárolja el.
     */
    protected GameGui gameGui;

    /**
     * A játékos nevét tárolja el.
     */
    private String name;
    /**
     * Az a mező, amelyen jelenleg a játékos áll.
     */
    private Field field;

    /**
     * a játékos továbbléphet-e az általa beragasztózott csőről.
     */
    private boolean immuneToStick;

    /**
     * konstruktor, beaáálítja az elfoglalt mező és játékos kapcsolatát
     * @param Name
     * @param field
     */
    public Player (String Name, Field field)
    {
        this.name = Name;
        this.field = field;
        this.field.addPlayer(this);
    }

    /**
     * Beállítja a játékos nevét.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Lekéri a játékos nevét.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Felülírandó, pumpa lerakása
     */
    public void installPump() {
        UI.incorrectPlayer();
    }
    /**
     * Felülírandó, cső lerakása
     */
    public void installPipe() {
        UI.incorrectPlayer();
    }
    public void insertPump(){}
    /**
     * Felülírandó, cső javitasa
     */
    public void fix(){
        UI.incorrectPlayer();
    }
    /**
     * Egy pumpának a bemeneti és kimeneti csövének a beállítása a paraméterekként kapott megfelelő
     * értékekkel.
     * @param input
     * @param output
     */
    public void changePumpSettings(PipeField input, PipeField output) {
        //System.out.println("->player.changePumpSettings");
        field.adjust(input,output);
        Game.nextPlayer();
        //System.out.println("<-player.changePumpSettings");

    }

    /**
     * Kilyukasztja a csövet, a puncturePipe() függvényének meghívásával, ennek következtében a továbbiakban az itt
     * áthaladó víz a sivatagi homokba folyik.
     */
    public void puncturePipe() {
        //System.out.println("\n->s.puncturePipe");
        if(this.getField().puncturePipe()) {
            //System.out.println("Valid Move");
        }

            //System.out.println("\tInvalid Move");
        //System.out.println("<-s.puncturePipe");
        //Game.nextPlayer();
    }

    /**
     * A játékos átmegy a paraméterül kapott mezőre. Ha a mező foglalt, akkor nem lép át.
     * @param field
     */
    public void moveTo(Field field) {
        if(!field.getInInventory()) {
            if (this.field.getNeighbours().contains(field)) {
                //System.out.println("->moveTo(field)");
                if (this.field.isSticky() && !immuneToStick) {
                    //System.out.println("The pipe is sticky");
                    UI.moveFromStickyPipe((PipeField) this.field);
                    Game.nextPlayer();
                } else if (field.isSlippery() && field.addPlayer(this)) {
                    int random = (int) (Math.random() * 2); // 0 v 1
                    if (random == 0) {
                        moveTo(field.getDestination());
                        UI.moveToSlipperyPipe(field, this.getField());
                    } else {
                        UI.moveToSlipperyPipe(field, this.getField());
                        Game.nextPlayer();
                        return;
                    }
                    // 0 esetén túlsó pumpa, 1 esetén this->cső->this
                    Game.nextPlayer();
                } else if (field.addPlayer(this)) {
                    if (this.field.isSticky()) {
                        setImmuneToStick(false);
                    }
                    this.field.removePlayer(this);
                    UI.move(this.getField(), field);
                    this.field = field;
                    // System.out.println("\n<-moveTo(field)");
                    Game.nextPlayer();
                } else {
                    UI.moveToOccupiedPipe(field);
                    //System.out.println("<-moveTo(field): The pipe is occupied");
                }
            } else {
                UI.invalidAction("field is not neighbour");
            }
        }
    }
    /**
     * Cso csuszossá tétele
     */
    public void makePipeSlippery(){}
    /**
     * Cső felvétele
     */
    public void pickUpPump() {}
    /**
     * Cső ragadóssá tétele, illetve aki lerakja, azt immunissá teszi a ragadással szemben, hogy ne tudja leragasztani magát
     */

    public void makePipeSticky(){
        //System.out.println("->s.makePipeSticky");
        if(this.getField().makePipeSticky()) {
            setImmuneToStick(true);
            Game.nextPlayer();
        }
        else{
            //System.out.println("Invalid Move");
        }
        //System.out.println("<-s.makePipeSticky");
    }

    public String getParameter() {
        return "- ";
    }


    public void pickUpPipe(PipeField pipeField){}
    /**
     * Lekéri, hogy a játékos melyik mezőn áll.
     * @return
     */
    public Field getField() {
        return field;
    }

    /**
     * Beállítja a játékos mezőjét.
     * @param field
     */
    public void  setField(Field field){
        this.field=field;
    }

    /**
     * Lekéri, hogy a játékos továbbléphet-e az általa beragasztózott csőről.
     * @return
     */
    public boolean isImmuneToStick() {
        return immuneToStick;
    }

    /**
     * Beállítja, hogy a játékos továbbléphet-e az általa beragasztózott csőről.
     * @param immuneToStick
     */

    public void setImmuneToStick(boolean immuneToStick) {
        this.immuneToStick = immuneToStick;
    }

    /**
     * A játékos kirajzolása
     * @param g
     */
    public void DrawMe(Graphics g){}
}
