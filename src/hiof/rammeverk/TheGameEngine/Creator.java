package hiof.rammeverk.TheGameEngine;

import java.util.LinkedList;
import java.util.List;

/**
 * Creates new {@code GameObjects} from the {@code prototypes}-list
 * <p>
 *     Create an instance of the {@code GameObject} you want to add in game.<br>
 *     Add this instance to the {@code prototypes}-list. <br>
 *     Use the {@code cloneGameObject()}-method to add object to the game loop.
 * </p>
 * <p>
 *     This application uses this interface to implement the <em>Prototype pattern</em>
 * </p>
 * @see GameObject
 */
public interface Creator {

    /**
     * List of {@code GameObjects} stored as {@code prototypes}.
     */
    List<GameObject> prototypes = new LinkedList<>();

    /**
     * Create a copy of a GameObject stored in the {@code prototypes}-list.
     * @param id {@code Id} of the {@code GameObject}
     * @return {@code GameObject}
     * @see Id
     * @see ApplicationHandler
     * @see GameObject
     * @see Cloneable
     */
    GameObject cloneGameObject(Id id);

    /**
     * Add a new instance of a {@code GameObject} to the {@code prototypes}-list.
     * @param prototype {@code GameObject}
     * @see GameObject
     */
    void addPrototype(GameObject prototype);
}
