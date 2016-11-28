package algorithms;

import data.Element;
import functions.IFunction;
import jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm;
import sample.Generation;

import java.util.Random;

/**
 * Created by pavel on 28.11.16.
 */
public class EvolutionStrategy implements ISearchAlgorithm {
    IFunction f;
    Generation generation;
    int step;
    Random random;

    float deviation = 0.3f;
    int mode;
    Element best;

    public EvolutionStrategy(IFunction f, Generation generation, float deviation, int mode) {
        this.f = f;
        this.generation = generation;
        this.generation.GenerateFirst();
        this.generation.FixPopulation();
        random = new Random();
        this.deviation = deviation;
        this.mode = mode;
        step = 0;

        if(GetPopulation().length < 4) {
            System.out.print("ERROR: NP (population size) must be 4 or more!");
        }

        for (int i = 0; i < generation.GetPopSize(); i++) {
            generation.population[i].SetFitness(f.getValue(generation.GetDimension(), generation.population[i].params));
            if(best == null || generation.population[i].GetFitness() < best.GetFitness()) {
                best = generation.population[i];
            }
        }
    }

    @Override
    public Element[] GetPopulation() {
        return generation.population;
    }

    @Override
    public Element GetBest() {
        return best;
    }

    @Override
    public int GetStep() {
        return step;
    }

    @Override
    public void NextGeneration() {
        Generation nGen = new Generation(generation.GetDimension(), generation.GetPopSize(), f, generation.limits);
        for (int i = 0; i < nGen.GetPopSize(); i++) {
            nGen.population[i] = GetNewElement(generation.population[i]);
            if(best == null || nGen.population[i].GetFitness() < best.GetFitness()) {
                best = nGen.population[i];
            }
        }
        generation = nGen;
        step++;
    }

    private Element GetNewElement(Element e) {

        Element n = new Element(e);

        for (int i = 0; i < e.GetDimension(); i++) {
            double r = random.nextGaussian();
            n.params[i] += deviation * r;
        }
        generation.FixElement(n);
        n.SetFitness(f.getValue(n.GetDimension(), n.params));

        if(mode == 0) {
            return n.GetFitness() < e.GetFitness() ? n : e;
        } else {
            return n;
        }
    }
}
