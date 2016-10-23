package functions;

/*
 * Created by pavel on 04.10.16.
 */

public class OptimizationFunction implements IFunction {
    public float F = 0.5f;

    public float getLimit() {
        return 1;
    }

    public float getValue(int D, float[] values) {
        if (D != 2) {
            //new Exception("Optimization function is designed only for dimension of 2");
            return 0;
        }

        float x1 = values[0];
        float x2 = values[1];
        float g1 = 11;
        float g2 = 12;

        float f1 = x1;
        float g = 10 + x2;

        float alpha = 0.25f + 3.75f * ((g - g2) / (g1 - g2));
        float h = (float) (Math.pow(f1 / g, alpha) - (f1 / g) * Math.sin(Math.PI * F * f1 * g));

        return h;
    }
}

