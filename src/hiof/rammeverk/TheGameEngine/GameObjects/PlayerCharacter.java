package hiof.rammeverk.TheGameEngine.GameObjects;

import hiof.rammeverk.TheGameEngine.*;
import hiof.rammeverk.TheGameEngine.Helpers.Limiter;
import hiof.rammeverk.TheGameEngine.Window.HeadUpDisplay;
import hiof.rammeverk.TheGameEngine.Window.Window;
import hiof.rammeverk.TheGameEngine.Behavior.Creator;
import hiof.rammeverk.TheGameEngine.Behavior.ObjectCreator;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Example of a {@code GameObject}-subclass.
 * <p>
 *     This class has movement depending on {@code KeyInput}.<br>
 *     The {@code tick()}-method applies this movement to the {@code GameObject}.
 * </p>
 * <p>
 *     This class has collision detection for {@code GameObjects} with {@code Id.BULLET}.<br>
 *     The {@code collision}-method applies this detection.<br>
 *     {@code collision} uses a {@code GameObjects} {@code hitBox()}-method.
 * </p>
 * @see GameObject
 * @see Bullet
 * @see Id
 * @see Rectangle
 * @see ApplicationHandler
 * @see Limiter
 */
public class PlayerCharacter extends GameObject {
    private static PlayerCharacter prototype;
    private int width = 30;
    private int height = 100;
    private int limitRight;
    private int limitLeft;
    private int limitTop;
    private int limitBottom;

    /**
     * Standard constructor that will position {@code this} object in the middle of screen.
     * @param id {@code Id}
     * @param app {@code Applicationhandler}
     * @see Id
     * @see ApplicationHandler
     */
    private PlayerCharacter(Id id, ApplicationHandler app) {
        super(id, app);

        setMovementSpeed(10);
    }

    private PlayerCharacter(PlayerCharacter playerCharacter) {
        super(playerCharacter.id, playerCharacter.app);
        setMovementSpeed(playerCharacter.getMovementSpeed());
        setHeight(playerCharacter.getHeight());
        setWidth(playerCharacter.getWidth());
        setColor(playerCharacter.getColor());
    }

    /**
     * Create a prototype. Add to {@code Creator.prototypes}.
     * <p>
     *     Instantiates an instance of {@code PlayerCharacter}.
     *     <br>Add this instance to {@code Creator.prototypes}.
     *     <br>Use {@code Creator.createGameObject} to instantiate a new object from {@code prototypes}.
     *     <br>Add this instance to {@code ApplicationHandler.objects}.
     * </p>
     * @param id {@code Id}
     * @param app {@code ApplicationHandler}
     * @return {@code PlayerCharacter}
     * @see Id
     * @see Creator
     * @see ObjectCreator
     * @see ApplicationHandler
     * @see GameObject
     */
    public static PlayerCharacter create(Id id, ApplicationHandler app){
        if(prototype == null)
            prototype = new PlayerCharacter(id, app);
        return prototype;
    }


    /**
     * Create a new instance of the class which is a copy of the prototype.
     * @return PlayerCharacter copy of prototype.
     * @see Cloneable
     */
    @Override
    public PlayerCharacter cloneObject() {
        return new PlayerCharacter(prototype);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Behavior of {@code PlayerCharacter} for each iteration ({@code tick}) of the game.
     * <p>
     *     This implementation will move from {@code KeyInput}-commands<br>
     * </p>
     * <p>
     *     The {@code PlayerCharacter} can shoot with SPACE-button.
     *     {@code moveLeft} is a  {@code boolean} value that <br>
     *     determines which direction the {@code Bullet} will go.
     *
     * </p>
     * @see ApplicationHandler
     * @see Window
     * @see GameObject
     * @see Bullet
     * @see KeyInput
     */
    @Override
    public void tick() {
        xPos += velX;
        yPos += velY;
        if (velX < 0) {
            movingLeft = true;
        }else if (velX > 0)
            movingLeft = false;
        xPos = Limiter.border(xPos, limitLeft, app.getWindowWidth() - 48 - limitRight);
        yPos = Limiter.border(yPos, limitTop, app.getWindowHeight() - 130 - limitBottom);
        collision();
    }

    /**
     * How to draw the {@code GameObject} to the window.
     * @param g {@code Graphics}
     * @see Graphics
     */
    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int) xPos, (int) yPos, width, height);
    }

    /**
     * This implementation of {@code collision} detect collision with {@code Id.BASIC_ENEMY} and {@code Id.SMART_ENEMY}.
     * <br> Uses {@code hitBox} and {@code Rectangle}.
     * @see Rectangle
     * @see Id
     */
    private void collision(){
        for(int i = 0; i < super.app.objects.size(); i++){
            if(app.objects.get(i).getId() == Id.BASIC_ENEMY || app.objects.get(i).getId() == Id.SMART_ENEMY){
                // Collision
                if(this.hitBox().intersects(app.objects.get(i).hitBox())){
                    HeadUpDisplay.setHealth(HeadUpDisplay.getHealth() -2);
                }
            }else if(app.objects.get(i).getId() == Id.BOSS_ENEMY){
                if(this.hitBox().intersects(app.objects.get(i).hitBox())){
                    HeadUpDisplay.setHealth(HeadUpDisplay.getHealth() -1);
                }
            }
        }
    }

    /**
     * Determine {@code hitBox} of the {@code GameObject}.
     * <p>
     *     A {@code hitBox} is the area the of the {@code GameObject} that is checked for {@code collision}.
     *     <br> I.e this can be to deal damage or receive damage.
     * </p>
     * @return Rectangle {@code java.awt.Rectangle}
     * @see Rectangle
     */
    @Override
    public Rectangle hitBox() {
        return new Rectangle((int) xPos, (int) yPos, width, height);
    }

    public void setLimitRight(int limitRight) {
        this.limitRight = limitRight;
    }

    public void setLimitLeft(int limitLeft) {
        this.limitLeft = limitLeft;
    }

    public void setLimitTop(int limitTop) {
        this.limitTop = limitTop;
    }

    public void setLimitBottom(int limitBottom) {
        this.limitBottom = limitBottom;
    }
}
