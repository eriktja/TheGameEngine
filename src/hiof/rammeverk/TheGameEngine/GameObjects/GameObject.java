package hiof.rammeverk.TheGameEngine.GameObjects;

import hiof.rammeverk.TheGameEngine.ApplicationHandler;
import hiof.rammeverk.TheGameEngine.Behavior.Creator;
import hiof.rammeverk.TheGameEngine.Window.Window;
import hiof.rammeverk.TheGameEngine.Helpers.Limiter;
import hiof.rammeverk.TheGameEngine.Behavior.ObjectCreator;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;

/**
 * A basic GameObject-superclass.
 * <p>
 *     This class is not abstract, and it has basic implementation.<br>
 *     Any class that inherits from this class will inherit the basic implementation unless it is overridden.
 * </p>
 * <p>
 *     Making a new class that inherits from this class is easy, because of the basic implementations. <br>
 *     You can make changes and instantly see the changes in the game window.
 * </p>
 * <p>
 *     Implements Cloneable-interface with
 * </p>
 * @see ApplicationHandler
 * @see GameObject
 * @see Bullet
 * @see Id
 * @see Rectangle
 * @see Cloneable
 */
public class GameObject implements Cloneable {
    protected ApplicationHandler app;
    protected float xPos;
    protected float yPos;
    protected Id id;
    protected float velX;
    protected float velY;
    private float movementSpeed;
    private Color color = Color.RED;

    protected boolean movingLeft = false;

    protected int width = 70;
    protected int height = 70;


    /**
     * Standard constructor that will position {@code this} object in the middle of screen.
     * @param id {@code Id}
     * @param app {@code Applicationhandler}
     * @see Id
     * @see ApplicationHandler
     */
    public GameObject(Id id, ApplicationHandler app) {
        this.id = id;
        this.app = app;
        setXPos(app.getWindowWidth() / 2.0f);
        setYPos(app.getWindowHeight() / 2.0f);
    }

    /**
     * Create a prototype. Add to {@code Creator.prototypes}.
     * <p>
     *     Instantiates an instance of {@code GameObject}.
     *     <br>Add this instance to {@code Creator.prototypes}.
     *     <br>Use {@code Creator.createGameObject} to instantiate a new object from {@code prototypes}.
     *     <br>Add this instance to {@code ApplicationHandler.objects}.
     * </p>
     * @param id {@code Id}
     * @param app {@code ApplicationHandler}
     * @return {@code GameObject}
     * @see Id
     * @see Creator
     * @see ObjectCreator
     * @see ApplicationHandler
     */
    public static GameObject create(Id id, ApplicationHandler app) {
        return new GameObject(id, app);
    }

    /**
     * Create a new instance of the class which is a copy of the prototype.
     * @return GameObject copy of prototype.
     * @see Cloneable
     */
    @Override
    public GameObject cloneObject() {
        GameObject obj = new GameObject(this.id, this.app);
        obj.id = this.id;
        obj.app = this.app;
        obj.movementSpeed = this.movementSpeed;
        obj.height = this.height;
        obj.width = this.width;
        return obj;
    }

    /**
     * Behavior of {@code GameObject} for each iteration ({@code tick}) of the game.
     * <p>
     *     This implementation will have the object move at a certain speed,
     *     <br> and turn around if it hits the edge of the window.
     * </p>
     * @see ApplicationHandler
     * @see Window
     */
    public void tick() {

    }

    /**
     * How to draw the {@code GameObject} to the window.
     * @param g {@code Graphics}
     * @see Graphics
     */
    public void render(Graphics g){
        g.setColor(color);
        g.fillRect((int) xPos, (int) yPos, width, height);
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

    public Rectangle hitBox() {
        return new Rectangle((int) xPos, (int) yPos, width, height);
    }

    /**
     * Movement pattern that makes this GameObject follow another GameObject.
     * @param gameObject {@code GameObject}
     */
    public void movementPattern1(GameObject gameObject) {
        if(Float.isNaN(velX) || Float.isNaN(velY)){
            velX = 0;
            velY = 0;
        }

        xPos += velX;
        yPos += velY;
        yPos = Limiter.border(yPos, 0, app.getWindowHeight() - 100);

        float diffX = xPos - gameObject.getXPos() - 8;
        float diffY = yPos - gameObject.getYPos() - 8;
        float distance = (float) Math.hypot(xPos - gameObject.getXPos(), yPos - gameObject.getYPos());

        velX = (float) ((-1.0/distance) * diffX);
        velY = (float) ((-1.0/distance) * diffY);
    }

    /**
     * Movement pattern that keeps GameObject within screen-bounds,
     * <br> and changes trajectory when hitting the edge of the screen
     */
    public void movementPattern2() {
        if (velX == 0 || velY == 0) {
            velX = movementSpeed;
            velY = movementSpeed;
        }
        if (xPos >= app.getWindowWidth() || xPos <= 0) velX *= -1;
        if (yPos >= app.getWindowHeight() || yPos <= 0) velY *= -1;

        xPos += velX;
        yPos += velY;

    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public float getXPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}

