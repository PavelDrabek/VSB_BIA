package algorithms;

import data.Element;
import functions.IFunction;
import sample.Generation;

/**
 * Created by pavel on 18.10.16.
 */
public class BlindSearch {

    IFunction f;
    int D;
    public Generation gen;

    Element best = null;
    int step;

    public BlindSearch(IFunction f, int D, Generation gen) {
        this.gen = gen;
        this.D = D;
        this.f = f;
        step = 0;
        Compute();
    }

    public Element GetBest() {
        return best;
    }
    public int GetStep() { return step; }

    public void Next() {
        GenerateNextGeneration();
        Compute();
        step++;
    }

    private void Compute() {
        for (int p = 0; p < gen.GetPopSize(); p++) {
            gen.population[p].SetFitness(f.getValue(D, gen.population[p].params));
            if(best == null || gen.population[p].GetFitness() < best.GetFitness()) {
                best = gen.population[p];
            }
        }
    }

    private void GenerateNextGeneration() {
        gen.GenerateFirst();

        for (int p = 0; p < gen.GetPopSize(); p++) {
            gen.population[p] = new Element(D);
            for (int i = 0; i < D; i++) {
                gen.population[p].params[i] = gen.GetRandomFloat(gen.limits[i]);
                gen.FixElement(p, i);
            }
        }
    }
}
