package sample;

import functions.IFunction;
import org.jzy3d.plot3d.builder.Mapper;

/**
 * Created by pavel on 04.10.16.
 */
public class FunctionMapper extends Mapper {

    private IFunction f;
    boolean roundInteger = false;

    public FunctionMapper(IFunction f) {
        super();
        this.f = f;
    }

    public void SetInteger(boolean roundInteger) {
        this.roundInteger = roundInteger;
    }

    @Override
    public double f(double v, double v1) {
        return f.getValue(2, new float[]{(float)v, (float)v1});
    }
}
