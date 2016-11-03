package algorithms;

import data.Element;
import functions.IFunction;
import sample.Generation;

import java.util.Random;

/**
 * Created by pavel on 01.11.16.
 */
public class DifferentialEvolution implements ISearchAlgorithm {

    IFunction f;
    Generation generation;
    Random random;

    int step;
    float F;
    float CR;
    Element best;

    public DifferentialEvolution(IFunction f, Generation generation, float F, float CR) {
        this.f = f;
        this.generation = generation;
        this.generation.GenerateFirst();
        this.generation.FixPopulation();
        random = new Random();
        this.F = F; // 0-2 (0.3 - 0.9)
        this.CR = CR; // 0-1 (0.8-0.9)
        step = 0;

        if(GetPopulation().length < 4) {
            System.out.print("ERROR: NP (population size) must be 4 or more!");
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
            nGen.population[i] = GetNewElement(i);
            if(best == null || nGen.population[i].GetFitness() < best.GetFitness()) {
                best = nGen.population[i];
            }
        }
        generation = nGen;
        step++;
    }

    private Element GetNewElement(int index) {
        int i1, i2, i3 = index;
        do {
            i1 = random.nextInt(generation.GetPopSize());
        } while (i1 == index);
        do {
            i2 = random.nextInt(generation.GetPopSize());
        } while (i2 == index || i2 == i1);
        do {
            i3 = random.nextInt(generation.GetPopSize());
        } while (i3 == index || i3 == i2 || i3 == i1);

        Element diffVector = generation.population[i1].odd(generation.population[i2]).mul(F);
        Element noiseVector = generation.population[i3].add(diffVector);

        Element activeElement = generation.population[index];
        Element testElement = new Element(activeElement);

        for (int i = 0; i < generation.GetDimension(); i++) {
            if(random.nextFloat() < CR) {
                testElement.params[i] = noiseVector.params[i];
            }
        }
        generation.FixElement(testElement);
        activeElement.SetFitness(f.getValue(generation.GetDimension(), activeElement.params));
        testElement.SetFitness(f.getValue(generation.GetDimension(), testElement.params));

        if(activeElement.GetFitness() < testElement.GetFitness()) {
            return activeElement;
        } else {
            return testElement;
        }
    }
}
