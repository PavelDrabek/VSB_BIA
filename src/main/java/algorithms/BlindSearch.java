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

    BlindSearch(Generation gen) {
        this.gen = gen;
    }

    public Element GetBest() {
        return best;
    }

    public void Next() {
        NextGeneration();

        for (int p = 0; p < gen.GetPopSize(); p++) {
            gen.population[p].SetFitness(f.getValue(D, gen.population[p].params));
            if(best == null || gen.population[p].GetFitness() < best.GetFitness()) {
                best = gen.population[p];
            }
        }
    }

    private void NextGeneration() {
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
