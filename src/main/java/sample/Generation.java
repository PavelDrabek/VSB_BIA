package sample;

import data.Element;
import functions.IFunction;
import org.jzy3d.maths.Range;

import java.util.Random;

/**
 * Created by pavel on 10.10.16.
 */
public class Generation {

    int D;
    IFunction f;
    public Range[] limits;
    public Element[] population;

    boolean isDiscrete = true;

    Random rand;

    public int GetPopSize() { return population.length; }
    public int GetDimension() { return D; }

    public Generation(int D, int popSize, IFunction f, Range[] limits) {
        this.D = D;
        this.f = f;
        this.limits = limits;
        SetPopSize(popSize);
        rand = new Random();
    }

    public void SetPopSize(int popSize) {
        population = new Element[popSize];
    }

    public void GenerateFirst() {

        for (int p = 0; p < GetPopSize(); p++) {
            population[p] = GenerateRandomElement(D);
        }
    }

    public Element GenerateRandomElement(int D) {
        Element e = new Element(D);
        for (int i = 0; i < D; i++) {
            e.params[i] = GetRandomValueInRange(i);
        }
        return e;
    }

    public float GetRandomValueInRange(int index) {
        return GetRandomFloat(limits[index]);
    }

    public void FixElement(int popIndex, int paramIndex) {
        if(isDiscrete) {
            population[popIndex].params[paramIndex] = FixToInt(population[popIndex].params[paramIndex]);
        }
        population[popIndex].params[paramIndex] = FixToRangeLoop(population[popIndex].params[paramIndex], limits[paramIndex]);
    }

    public Element FixElement(Element el) {
        for (int i = 0; i < D; i++) {
            if(isDiscrete) {
                el.params[i] = FixToInt(el.params[i]);
            }
            el.params[i] = FixToRangeLoop(el.params[i], limits[i]);
        }
        return el;
    }


    public void FixElement(int index) {
        for (int i = 0; i < D; i++) {
            FixElement(index, i);
        }
    }

    public void FixPopulation() {
        for (int p = 0; p < GetPopSize(); p++) {
            FixElement(p);
        }
    }

    public void CalcFitness() {
        for (int p = 0; p < GetPopSize(); p++) {
            population[p].SetFitness(f.getValue(D, population[p].params));
        }
    }

    public float GetRandomInt(Range limit) {
        return rand.nextInt((int)limit.getRange()) + limit.getMin();
    }

    public float GetRandomFloat(Range limit) {
        return rand.nextFloat() * limit.getRange() + limit.getMin();
    }

    private float FixToRangeLoop(float x, Range limit) {
        while(x < limit.getMin()) {
            x += limit.getRange();
        }
        while(x > limit.getMax()) {
            x -= limit.getRange();
        }
        return x;
    }

    private float FixToRangeClamp(float x, Range limit) {
        if(x < limit.getMin()) {
            x =  limit.getMin();
        }
        if(x > limit.getMax()) {
            x = limit.getMax();
        }
        return x;
    }

    private int FixToInt(float x) {
        return Math.round(x);
    }
}
