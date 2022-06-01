package hiof.rammeverk.TheGameEngine.Helpers;

/**
 * Help set maximin and minimum value for certain variables.
 * <p>
 *     Makes sure the value is within specified limits.
 * </p>
 */
public interface Limiter {
    /**
     * Set limits for game elements.
     * @param value Variable to check limits for
     * @param min <em>Minimum</em> limit for {@code value}.
     * @param max <em>Maximum</em> limit for {@code value}.
     * @return value withing <em>Maximum</em> and <em>Minimum</em> limit
     */
    static float border(float value, float min, float max){
        if(value >= max)
            return max;
        else if(value <= min)
            return min;
        return value;
    }
}
