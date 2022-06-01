package hiof.rammeverk.TheGameEngine;

import hiof.rammeverk.TheGameEngine.Window.Window;
import hiof.rammeverk.TheGameEngine.GameObjects.GameObject;
import hiof.rammeverk.TheGameEngine.Behavior.Creator;

import java.awt.Graphics;
import java.util.LinkedList;

/**
 * Contains the core of the game logic.
 * <p>
 *     Every object in the game engine requires an instance of {@code ApplicationHandler}
 *     The objects will get their relative position from the {@code windowHeight} and {@code windowWidth}.
 *     The game window will get its size from {@code windowHeight} and {@code windowWidth}.
 * </p>
 * <ul> {@code ApplicationHandler} controls the following
 *     <li>Size of {@code Window}</li>
 *     <li>{@code GameObjects} in game</li>
 *     <li>Position of {@code GameObjects}</li>
 *     <li>{@code render()} for all {@code GameObjects} in {@code objects}</li>
 * </ul>
 * @see Window
 * @see GameObject
 */
public interface ApplicationHandler {
    /**
     * A list of {@code GameObjects} to be displayed in game
     * <p>
     *     This list keeps track of all <em>in game elements</em>.
     *     These elements are the {@code GameObjects} the game-creator wants to be displayed at any given time.
     * </p>
     */
    LinkedList<GameObject> objects = new LinkedList<>();

    /**
     * Get the {@code Creator}.
     *  <p>
     *     The {@code Creator} have a list of {@code GameObjects} called {@code prototypes}.
     *     Call the {@code Creator} to add a new {@code GameObject} to the {@code objects}-list.
     * </p>
     * @see Creator
     */
    Creator getCreator();

    /**
     * Set which {@code Creator} to be used in the game
     * <p>
     *     The {@code Creator} have a list of {@code GameObjects} called {@code prototypes}.
     *     Call the {@code Creator} to add a new {@code GameObject} to the {@code objects}-list.
     * </p>
     * @see Creator
     */
    void setCreator(Creator creator);

    /**
     * Control what happens for each iteration({@code tick}) of the game sequence.
     */
    void tick();

    /**
     * Used to draw/render all {@code GameObjects} to the screen.
     * @param g {@code Graphics} from Java.AWT
     */
    void render(Graphics g);

    /**
     * Remove all {@code GameObjects}, but the {@code Id.PLAYER} from {@code objects}-list in {@code ApplicationHandler}.
     * @see GameObject
     * @see ApplicationHandler
     */
    void clearAllExceptPlayer();

    /**
     * Remove all {@code GameObjects} from {@code objects}-list in {@code ApplicationHandler}.
     * @see GameObject
     * @see ApplicationHandler
     */
    void clearAll();

    /**
     * Add a {@code GameObject} to the {@code objects}-list in {@code ApplicationHandler}
     * @param object GameObject
     * @see GameObject
     * @see ApplicationHandler
     */
    void addObject(GameObject object);

    /**
     * Remove specific {@code GameObject} from the {@code objects}-list in {@code ApplicationHandler}
     * @param object GameObject
     * @see GameObject
     * @see ApplicationHandler
     */
    void removeObject(GameObject object);

    /**
     *
     * @return the {@code windowWidth} used in the {@code ApplicationHandler}.
     */
    int getWindowWidth();

    /**
     *
     * @return the {@code windowHeight} used in the {@code ApplicationHandler}.
     */
    int getWindowHeight();

}
