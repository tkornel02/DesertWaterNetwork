package model;

/**
 * A víz objektumot reprezentáló osztály.
 */

public class Water {
    private static int waterCounter;
    private int nr;
    Water(){
        nr = waterCounter++;

    }
    /**
     * A víz aktuális mezője.
     */
    private Field currentField;

    /**
     * A víz aktuális mezőjét állítja be.
     * @param currentField
     */
    public void setCurrentField(Field currentField) {
        this.currentField = currentField;
    }

    /**
     * A víz aktuális mezőjét adja vissza.
     * @return
     */
    public Field getCurrentField() {
        return currentField;
    }

    /**
     * Grafikus részen tulajdonságok kiírásához.
     * @return
     */
    @Override
    public String toString() {
        String res = super.toString().substring(0,11).replace("model.Water","w") + "" +nr;
        return res;
    }
}