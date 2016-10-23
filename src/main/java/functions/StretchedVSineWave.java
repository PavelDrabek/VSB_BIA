package functions;

/*
 * Created by pavel on 04.10.16.
 */

public class StretchedVSineWave implements IFunction
{
    public float getLimit() { return 3; }

    public float getValue(int D, float[] values)
    {
        float sum = 0;
        for (int i = 0; i < D - 1; i++)
        {
            float x0 = values[i] * values[i];
            float x1 = values[i+1] * values[i+1];
            sum += (float)(Math.pow(x0 + x1, 1/4f) * Math.pow(Math.sin(50 * Math.pow(x0 + x1, 1/10f)), 2) + 1);
        }
        return sum;
    }
}

