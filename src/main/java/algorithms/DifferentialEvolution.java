package algorithms;

import data.Element;
import functions.IFunction;
import sample.Generation;

/**
 * Created by pavel on 01.11.16.
 */
public class DifferentialEvolution implements ISearchAlgorithm {

    IFunction f;
    Generation generation;

    DifferentialEvolution(IFunction f, Generation generation) {
        this.f = f;
        this.generation = generation;
    }

    @Override
    public Element[] GetPopulation() {
        return new Element[0];
    }

    @Override
    public Element GetBest() {
        return null;
    }

    @Override
    public int GetStep() {
        return 0;
    }

    @Override
    public void NextGeneration() {

    }

}
