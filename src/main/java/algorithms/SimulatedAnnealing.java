package algorithms;

import com.oracle.tools.packager.Log;
import data.Element;
import functions.IFunction;
import sample.Generation;

import java.util.Random;

/**
 * Created by pavel on 25.10.16.
 */
public class SimulatedAnnealing implements ISearchAlgorithm {
    IFunction f;
    public Generation gen;

    Element best = null;
    int step;

    double cooldownCoef = 0.8; // 0.8 - 0.95
    double temp = 50;

    Random random = null;

    public SimulatedAnnealing(IFunction f, Generation gen) {
        random = new Random();
        this.gen = gen;
//        this.gen.SetPopSize(1);
        this.gen.GenerateFirst();
        this.gen.FixPopulation();
        this.f = f;
        step = 0;
        Compute();
    }

    public Element GetBest() {
        return best;
    }
    public int GetStep() { return step; }
    public Element[] GetPopulation() { return gen.population; }

    public void NextGeneration() {
        GenerateNextGeneration();
        Compute();
        temp *= cooldownCoef;
        step++;
    }

    private void Compute() {
        for (int p = 0; p < gen.GetPopSize(); p++) {
            gen.population[p].SetFitness(f.getValue(gen.GetDimension(), gen.population[p].params));
            if(best == null || gen.population[p].GetFitness() < best.GetFitness()) {
                best = gen.population[p];
            }
        }
    }

    private void GenerateNextGeneration() {
        for (int p = 0; p < gen.GetPopSize(); p++) {
            gen.population[p] = GetNewElement(gen.population[p], temp);
//            gen.FixElement(p);
        }
    }

    Element GetNewElement(Element el, double t) {
        Element nEl = gen.FixElement(GetNextRandomState(el));
        double delta = f.getValue(gen.GetDimension(), el.params) - f.getValue(gen.GetDimension(), nEl.params);
        if(delta > 0) {
            return nEl;
        } else {
            double rnd = random.nextDouble();
            double exp = Math.exp(delta/t);
            System.out.print("rnd: " + rnd + ", exp: " + exp + ", delta: " + delta + ", t: " + t + "\n");
            if(rnd < exp)
                return nEl;
            else
                return el;
        }
    }

    Element GetNextRandomState(Element el) {
        int i1 = random.nextInt(gen.GetDimension());
        Element nEl = new Element(el);
        nEl.params[i1] = gen.GetRandomValueInRange(i1);
        return nEl;
    }
}
