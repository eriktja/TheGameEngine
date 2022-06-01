package hiof.rammeverk.TheGameEngine;

import java.awt.Graphics;

/**
 * Contains the core of the game logic.
 * <p>
 *     This class serves as an example of how {@code ApplicationHandler} can be created.
 * </p>
 * <p>
 *     Every object in the game engine requires an instance of {@code ApplicationHandler}
 *     The objects will get their relative position from the {@code windowHeight} and {@code windowWidth}
 *     The game window will get its size from {@code windowHeight} and {@code windowWidth}.
 * </p>
 */
public class App implements ApplicationHandler{
    /**
     * The width used throughout the Application.
     * <p>
     *     Used to decide width of <em>GameWindow</em> and position of {@code GameObjects}
     * </p>
     */
    private final int windowWidth;
    /**
     * The height used throughout the Application.
     * <p>
     *     Used to decide height of <em>GameWindow</em> and position of {@code GameObjects}
     * </p>
     */
    private final int windowHeight;

    /**
     * The {@code Creator} to be used in the game
     * <p>
     *     The {@code Creator} have a list of {@code GameObjects} called {@code prototypes}.
     *     Call the {@code Creator} to add a new {@code GameObject} to the {@code objects}-list.
     * </p>
     * @see Creator
     */
    private Creator creator;

    /**
     * Create a new instance of {@code ApplicationHandler.}
     * @param windowWidth Set {@code windowWidth}
     * @param windowHeight Set {@code }windowHeight
     * @param creator set {@code Creator}
     *
     */
    public App(int windowWidth, int windowHeight, Creator creator) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.creator = creator;
    }

    public App(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    /**
     * Get the {@code Creator}.
     *  <p>
     *     The {@code Creator} have a list of {@code GameObjects} called {@code prototypes}.
     *     Call the {@code Creator} to add a new {@code GameObject} to the {@code objects}-list.
     * </p>
     * @see Creator
     */
    @Override
    public Creator getCreator() {
        return creator;
    }

    /**
     * Set which {@code Creator} to be used in the game
     * <p>
     *     The {@code Creator} have a list of {@code GameObjects} called {@code prototypes}.
     *     Call the {@code Creator} to add a new {@code GameObject} to the {@code objects}-list.
     * </p>
     * @param creator {@code Creator}
     * @see Creator
     */
    @Override
    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    /**
     * Control what happens for each iteration({@code tick}) of the game sequence.
     */
    @Override
    public void tick(){
        for (int i = 0; i < objects.size(); i++){
            if(objects.get(i).getId() == Id.DEAD)
                removeObject(objects.get(i));
            else
                objects.get(i).tick();
        }
    }

    /**
     * Used to draw/render all {@code GameObjects} to the screen.
     * @param g {@code Graphics} from Java.AWT
     */
    @Override
    public void render(Graphics g){
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).render(g);
        }
    }

    /**
     * Remove all {@code GameObjects}, but the {@code Id.PLAYER} from {@code objects}-list in {@code ApplicationHandler}.
     * @see GameObject
     * @see ApplicationHandler
     */
    @Override
    public void clearAllExceptPlayer(){
        for (int i = objects.size() -1; i > 0; i--){
            if(objects.get(i).getId() != Id.PLAYER)
                objects.remove(objects.get(i));
        }
    }

    /**
     * Remove all {@code GameObjects} from {@code objects}-list in {@code ApplicationHandler}.
     * @see GameObject
     * @see ApplicationHandler
     */
    @Override
    public void clearAll() {
        for (int i = objects.size() - 1; i >= 0; i--){
            objects.remove(i);
        }
    }

    /**
     * Add a {@code GameObject} to the {@code objects}-list in {@code ApplicationHandler}
     * @param object GameObject
     * @see GameObject
     * @see ApplicationHandler
     */
    @Override
    public void addObject(GameObject object){
        this.objects.add(object);
    }


    /**
     * Remove specific {@code GameObject} from the {@code objects}-list in {@code ApplicationHandler}
     * @param object GameObject
     * @see GameObject
     * @see ApplicationHandler
     */
    @Override
    public void removeObject(GameObject object){
        this.objects.remove(object);
    }

    /**
     *
     * @return the {@code windowWidth} used in the {@code ApplicationHandler}.
     */
    @Override
    public int getWindowWidth()
    {
        return windowWidth;
    }

    /**
     *
     * @return the {@code windowHeight} used in the {@code ApplicationHandler}.
     */
    @Override
    public int getWindowHeight()
    {
        return windowHeight;
    }

}
