package functions;

/*
 * Created by pavel on 04.10.16.
 */

public class Ackey1 implements IFunction
{
    public float getLimit() { return 500; }

    public float getValue(int D, float[] values)
    {
        float sum = 0;
        for (int i = 0; i < D - 1; i++)
        {
            float x0 = values[i];
            float x1 = values[i + 1];
            sum += (float)( (1 / Math.pow(Math.E, 5)) * Math.sqrt (x0 * x0 + x1 * x1) + 3 * Math.cos(2 * x0) + Math.sin(2 * x1) );
        }
        return sum;
    }
}
