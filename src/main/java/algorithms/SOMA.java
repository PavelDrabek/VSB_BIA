package algorithms;

import data.Element;
import functions.IFunction;
import sample.Generation;

import java.util.Random;

/**
 * Created by pavel on 08.11.16.
 */
public class SOMA implements ISearchAlgorithm {

    IFunction f;
    Generation generation;
    int step;
    Random random;

    float pathLength;   // rychle 3
    float pathStep;     // rychle 0.3 | pomalu 0.11
    float PRT;          // rychle 0.3 | pomalu 0.2
    Element best;

    public SOMA(IFunction f, Generation generation, float pathLength, float pathStep, float PT) {
        this.f = f;
        this.generation = generation;
        this.generation.GenerateFirst();
        this.generation.FixPopulation();
        random = new Random();
        this.pathLength = pathLength;
        this.pathStep = pathStep;
        this.PRT = PT;
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
//        Element dir = new Element(e.GetDimension());
//        double t = random.nextDouble() * pathLength;

        Element PTRVector = new Element(e.GetDimension());
        for (int i = 0; i < PTRVector.GetDimension(); i++) {
            PTRVector.params[i] = (random.nextDouble() < PRT) ? 1 : 0;
        }

        Element lBest = null;
        for (int i = 0; i < pathLength / pathStep; i++) {
            Element l = e.add(best.odd(e).mul(pathStep * i).mul(PTRVector));
            l = generation.FixElement(l);
            l.SetFitness(f.getValue(l.GetDimension(), l.params));
            if(lBest == null || l.GetFitness() < lBest.GetFitness()) {
                lBest = l;
            }
        }

//        Element n = e.add(dir);
        return lBest;
    }
}
