package functions;

/*
 * Created by pavel on 04.10.16.
 */

public class Rastrigin implements IFunction
{
    public float getLimit()
    {
        return 5;
    }

    public float getValue(int D, float[] values)
    {
        float sum = 0;
        for (int i = 0; i < D; i++)
        {
            sum += values[i] * values[i] - 10 * (float)Math.cos(2 * Math.PI * values[i]);
        }
        return 2 * D * sum;
    }
}

