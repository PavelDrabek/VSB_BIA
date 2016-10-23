package functions;

/*
 * Created by pavel on 04.10.16.
 */

public class DeJong4 implements IFunction
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
            sum += values[i] * values[i] * values[i] * values[i];
        }
        return sum;
    }
}

