package functions;

/*
 * Created by pavel on 04.10.16.
 */

public class Ackey2 implements IFunction
{
    public float getLimit() { return 5; }

    public float getValue(int D, float[] values)
    {
        float sum = 0;
        for (int i = 0; i < D - 1; i++)
        {
            float x0 = values[i];
            float x1 = values[i + 1];
            sum += (float)( 20 + Math.E - 20 / (Math.pow(Math.E, (0.2 * Math.sqrt( (x0*x0 + x1*x1) / 2 )) )) - Math.pow(Math.E, 0.5 * (Math.cos(2 * Math.PI * x0) + Math.cos(2 * Math.PI * x1) )) );
        }
        return sum;
    }
}
