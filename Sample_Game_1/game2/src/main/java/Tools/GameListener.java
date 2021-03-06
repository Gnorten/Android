package Tools;

import javax.microedition.khronos.opengles.GL10;

/**
 * A GameListener is the main mean of implementing the game.
 * It is attached to a {@link Game_Activity_1} and will be
 * called whenever a frame has to be rendered or the game
 * needs to be initialized.
 *
 * @author mzechner
 *
 */
public interface GameListener {
    /**
     * Called when the game has to be initialized
     * @param activity The GameActivity this listener is attached to
     * @param gl The GL
     */
    public void setup(Game_Activity_1 activity, GL10 gl);

    /**
     * Called when a new frame has to be rendered. Here all game
     * related operations are performed, e.g. simulate the game
     * world, process the input and so on.
     * @param activity
     * @param gl
     */
    public void mainLoopIteration(Game_Activity_1 activity, GL10 gl);
}
