package functions;

/**
 * Created by pavel on 04.10.16.
 */
public interface IFunction {
    float getLimit();
    float getValue(int dimension, float[] values);
}
