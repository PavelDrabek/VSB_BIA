package functions;

/*
 * Created by pavel on 04.10.16.
 */

public class Griewangk implements IFunction
{
    public float getLimit() { return 100; }

    public float getValue(int D, float[] values)
    {
        float sumA = 0;
        float sumB = 0;
        for (int i = 0; i < D; i++)
        {
            sumA += (values[i] * values[i]) / 4000;
            sumB += (float)Math.cos(values[i] / (float)Math.sqrt(i + 1));
        }
        return 1 + sumA - sumB;
    }
}

