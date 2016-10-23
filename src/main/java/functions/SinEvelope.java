package functions;

/*
 * Created by pavel on 04.10.16.
 */

public class SinEvelope implements IFunction
{
    public float getLimit() { return 3; }

    public float getValue(int D, float[] values)
    {
        float sum = 0;
        for (int i = 0; i < D - 1; i++)
        {
            float x0 = values[i];
            float x1 = values[i + 1];
            float numerator = (float)Math.sin(x0 * x0 + x1 * x1 - 0.5);
            float denominator = 1 + 0.001f * (x0 * x0 + x1 * x1);
            sum += 0.5f + ((numerator * numerator) / (denominator * denominator));
        }
        return -sum;
    }
}

