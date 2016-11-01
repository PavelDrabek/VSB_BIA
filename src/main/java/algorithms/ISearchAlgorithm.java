package algorithms;

import data.Element;

/**
 * Created by pavel on 25.10.16.
 */
public interface ISearchAlgorithm {
    Element[] GetPopulation();
    Element GetBest();
    int GetStep();
    void NextGeneration();
}
