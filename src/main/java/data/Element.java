package data;

/**
 * Created by pavel on 10.10.16.
 */
public class Element {

    int D;
    public float[] params;

    public Element(int D) {
        this.D = D;
        params = new float[D + 1];
    }

    public float GetFitness() {
        return params[D];
    }

    public void SetFitness(float fitness) {
        params[D] = fitness;
    }
}
