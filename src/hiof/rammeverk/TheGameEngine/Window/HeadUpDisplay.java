package hiof.rammeverk.TheGameEngine.Window;


import hiof.rammeverk.TheGameEngine.Helpers.Limiter;

import java.awt.Color;
import java.awt.Graphics;
// TODO Consider adding a way to save scores to a file
/**
 * Class used to display Player {@code health} points, {@code score} and {@code level}.
 * <p>
 *     You can make your own {@code HeadUpDisplay} by extending this class.
 *     Unless specified in {@code GameBuilder} this {@code HeadUpDisplay} will be used.
 * </p>
 * @see Window
 * @see GameBuilder
 */
public class HeadUpDisplay {
    private static float health = 100;
    private static int counter = 0;
    private int score = 0;
    private int level = 1;
    private int winCondition = 50;

    /**
     * Updates {@code score}, {@code level} and {@code health}
     */
    public void tick(){
        health = Limiter.border(health, 0, 100);
        score++;
        if(score % 100 == 0) level++;
    }

    /**
     * Draws {@code score}, {@code level} and {@code health} to the window.
     * @param g {@code Graphics}
     * @see Graphics
     */
    public void render(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(15, 15, 200, 32);
        g.setColor(new Color(200, 0, 0));
        g.fillRect(15, 15, (int)health * 2, 32);
        g.setColor(Color.BLACK);
        g.drawRect(15, 15, 200, 32);

        g.drawString("Score: " + score, 15, 64);
        g.drawString("Level: " + level, 15, 80);
        g.drawString("Kill count: " + counter, 15, 96);
    }

    /**
     * Called in {@code Window} to check if the game is lost.
     * <p>
     *     Used in {@code Window} to change {@code GameState} to {@code LOSE}
     * </p>
     * @return boolean - is player dead?
     * @see Window
     */
    public boolean loseGame(){
        return health <= 0;
    }

    /**
     * Called in {@code Window} to check if the game is won.
     * <p>
     *     Used in {@code Window} to change {@code GameState} to {@code WIN}
     * </p>
     * @return boolean - is game won?
     * @see Window
     */

    public boolean winGame(){
        return counter == winCondition;
    }

    /**
     * Reset all values used in HeadUpDisplay
     * <p>
     *     Used in the {@code Menu}-class when starting a new game.
     * </p>
     * @see Menu
     */
    public void reset(){
        counter = 0;
        setScore(0);
        setLevel(1);
        setHealth(100);
    }

    public static float getHealth() {
        return health;
    }

    public static void setHealth(float health) {
        HeadUpDisplay.health = health;
    }

    public String getScore() {
        return "" + score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void nextLevel(){
        level++;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public static int getCounter() {
        return counter;
    }

    public String getCounterToString() {
        return "" + counter;
    }

    public static void setCounter(int counter) {
        HeadUpDisplay.counter = counter;
    }

    public void setWinCondition(int winCondition) {
        this.winCondition = winCondition;
    }

    /**
     * Nicely formatted toString for saving your score to an external file.
     * @return score level and counter.
     */
    @Override
    public String toString() {
        return "Score: " + score +
                "\nLevel: " + level +
                "\nKill count: " + HeadUpDisplay.getCounter();
    }
}
