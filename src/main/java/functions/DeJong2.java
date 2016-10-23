package functions;

/*
 * Created by pavel on 04.10.16.
 */

public class DeJong2 implements IFunction
{
    public float getLimit()
    {
        return 5;
    }

    public float getValue(int D, float[] values)
    {
        float sum = 0;
        for (int i = 0; i < D - 1; i++)
        {
            float x0 = values[i];
            float x1 = values[i + 1];
            float a = x0 * x0 - x1;
            float b = 1 - x0;
            sum += 100 * (a * a) + (b * b);
        }
        return sum;
    }
}
