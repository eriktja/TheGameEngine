package hiof.rammeverk.TheGameEngine;

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
 *
 */
public class GameLoopExample implements GameLoop {

    /**
     * Counts each {@code tick}
     * <p>
     *     Used to time when something happen in the game
     * </p>
     */
    private int counter = 0;
    /**
     * A simple stage-counter controlled by {@code counter}.
     * <p>
     *     Used to determine stages in the game.
     * </p>
     */
    private int stage = 1;
    private ApplicationHandler app;
    private Creator creator;

    /**
     * Constructor for the class.
     *
     *     <ul><strong>Needs and {@code ApplicationHandler} to:</strong>
     *         <li>get access to the {@code objects}-list.</li>
     *         <li>get access to the {@code Creator} to use the {@code createGameObject}-method.</li>
     *     </ul>

     * @param app {@code ApplicationHandler}
     * @see ApplicationHandler
     * @see Creator
     */
    public GameLoopExample(ApplicationHandler app){
        this.app = app;
        creator = app.getCreator();
    }

    /**
     * Called after a game is finished.<br>
     * Use it to reset the values used in your game loop.
     */
    @Override
    public void reset() {
        counter = 0;
        stage = 1;
    }

    /**
     * Describe what should happen each iteration of the Game
     * <p>
     *     <strong>Tip:</strong><br>
     *     Use a counter to keep track of the game progress
     * </p>
     * @see Creator
     * @see GameObject
     * @see ApplicationHandler
     */
    @Override
    public void tick() {
        counter++;
        if(counter == 2){
            HeadUpDisplay.setHealth(100);
            app.addObject(creator.cloneGameObject(Id.PLAYER));
            app.addObject(creator.cloneGameObject(Id.SMART_ENEMY));
            app.addObject(creator.cloneGameObject(Id.ENEMY_1));
        }
        if(counter % 100 == 0)
            stage++;
        if(stage % 2 == 0 && counter % 100 == 0){
            app.addObject(creator.cloneGameObject(Id.SMART_ENEMY));
            app.addObject(creator.cloneGameObject(Id.BASIC_ENEMY));
        }
        if(stage > 15 && counter % 100 == 0)
            app.addObject(creator.cloneGameObject(Id.SMART_ENEMY));
    }
}
