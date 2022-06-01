package hiof.rammeverk.TheGameEngine.Behavior;

import hiof.rammeverk.TheGameEngine.ApplicationHandler;
import hiof.rammeverk.TheGameEngine.GameObjects.Cloneable;
import hiof.rammeverk.TheGameEngine.GameObjects.GameObject;

/**
 * Describes how the game behaves for each iteration.
 * <p>
 *     Use this to build the events in the game. <br>
 *     <strong>Example:</strong><br>
 *     If you want a boss-enemy to appear after a certain amount of time, here is the place to add it.
 * </p>
 * <ol> <strong>Step by step:</strong>
 *     <li>Create a new instance of {@code GameObject}.</li>
 *     <li>Add this instance to the {@code prototypes}-list in {@code Creator}. </li>
 *     <li>Create a new GameLoop (or you can test with the existing GameLoopExample)</li>
 *     <li>Use {@code cloneGameObject} in {@code Creator} to create a new {@code GameObject}.</li>
 *     <li>Add this {@code GameObject} to the {@code objects}-list found in {@code ApplicationHandler}.</li>
 *     <li>The {@code GameLoop} handles step 4 and 5. Repeat them to create many {@code GameObjects} from the same {@code prototype}.</li>
 * </ol>
 * @see GameObject
 * @see ApplicationHandler
 * @see Creator
 * @see Cloneable
 *
 */
public interface GameLoop {

    /**
     * Describe what should happen each iteration of the Game
     * <p>
     *     <strong>Tip:</strong><br>
     *     Use a counter to keep track of the game progress
     * </p>
     */
    void tick();

    /**
     * Called after a game is finished.<br>
     * Use it to reset the values used in your game loop.
     */
    void reset();
}
