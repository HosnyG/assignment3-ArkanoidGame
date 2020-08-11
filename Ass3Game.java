/**
 * A final game.
 * two balls bouncing in screen with blocks and paddle show on the screen.
 *
 * @author : Ganaiem Hosny
 */

public class Ass3Game {

    /**
     * initialize a new game and run it.
     *
     * @param args : ignored.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}