package functions;

/*
 * Created by pavel on 04.10.16.
 */

public class Schwefel implements IFunction
{
    public float getLimit() { return 500; }

    public float getValue(int D, float[] values)
    {
        float sum = 0;
        for (int i = 0; i < D; i++)
        {
            sum += -values[i] * (float)Math.sin(Math.sqrt(Math.abs(values[i])));
        }
        return sum;
    }
}