package hiof.rammeverk.TheGameEngine;

/**
 * Creates new {@code GameObjects} from the {@code prototypes}-list
 * <p>
 *     Create an instance of the {@code GameObject} you want to add in game.<br>
 *     Add this instance to the {@code prototypes}-list found in {@code Creator}. <br>
 *     Use the {@code cloneGameObject()}-method to add object to the game loop.
 * </p>
 *     <ul> <strong>This class implements several design patterns</strong>
 *         <li>Prototype pattern.</li>
 *         <li>Singleton pattern</li>
 *         <li>Factory pattern</li>
 *     </ul>
 * @see Creator
 * @see GameObject
 */
public class ObjectCreator implements Creator{
    private static Creator creator = null;

    /**
     * Create-method for the class.
     * <p>
     *     Return {@code this}.<br>
     *     Call {@code constructor} if {@code this} is {@code null}.
     * </p>
     * @return {@code Creator}
     * @see Creator
     */
    public static Creator create() {
        if(creator == null) {
            creator = new ObjectCreator();
        }
        return creator;
    }

    /**
     * Add a new instance of a {@code GameObject} to the {@code prototypes}-list.
     * @param prototype {@code GameObject}
     * @see Creator
     * @see GameObject
     */
    @Override
    public void addPrototype(GameObject prototype){
        prototypes.add(prototype);
    }

    /**
     * Create a copy of a prototype stored in the {@code prototypes}-list.
     * @param id {@code Id} of the {@code GameObject}
     * @return {@code GameObject}
     * @see Creator
     * @see Id
     * @see ApplicationHandler
     * @see GameObject
     */
    @Override
    public GameObject cloneGameObject(Id id) {
        for (int i = 0; i < prototypes.size(); i++) {
            if (prototypes.get(i).getId() == id) {
                return prototypes.get(i).cloneObject();
            }
        }
        return null;
    }
}
