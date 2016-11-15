package data;

/**
 * Created by pavel on 10.10.16.
 */
public class Element {

    int D;
    public float[] params;
    public int GetDimension() { return D; }

    public Element(int D) {
        this.D = D;
        params = new float[D + 1];
    }

    public Element(Element orig) {
        this(orig.D);
        for (int i = 0; i < D; i++) {
            params[i] = orig.params[i];
        }
    }

    public float GetFitness() {
        return params[D];
    }

    public void SetFitness(float fitness) {
        params[D] = fitness;
    }




    public Element odd(Element other) {
        Element result = new Element(this);
        for (int i = 0; i < result.GetDimension(); i++) {
            result.params[i] = params[i] - other.params[i];
        }
        return result;
    }

    public Element add(Element other) {
        Element result = new Element(this);
        for (int i = 0; i < result.GetDimension(); i++) {
            result.params[i] = params[i] + other.params[i];
        }
        return result;
    }

    public Element mul(float scalar) {
        Element result = new Element(this);
        for (int i = 0; i < result.GetDimension(); i++) {
            result.params[i] = params[i] * scalar;
        }
        return result;
    }

    public Element mul(Element e) {
        Element result = new Element(this);
        for (int i = 0; i < result.GetDimension(); i++) {
            result.params[i] = params[i] * e.params[i];
        }
        return result;
    }

}
