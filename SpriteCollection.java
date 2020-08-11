import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * A SpriteCollection class.
 * hold a collection of sprites.
 *
 * @author : Ganaiem Hosny
 */
public class SpriteCollection {
    private List sprites;

    /**
     * Constructor.
     * initialize the sprites list.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList();
    }


    /**
     * Add sprite to the sprites collection.
     *
     * @param s sprite to be add to the collection.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }


    /**
     * Call timePassed() on all sprites.
     *
     */
    public void notifyAllTimePassed() {
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = (Sprite) sprites.get(i);
            sprite.timePassed(); //call timePassed.
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d : a surface to draw the sprites on.
     *
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = (Sprite) sprites.get(i);
            sprite.drawOn(d); //call drawOn
        }
    }
}