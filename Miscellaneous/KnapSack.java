/*
  Wimal Perera
  09/10008
  Evolutionary Computing - Assignment
 */

package ec.app.assignment;

import ec.*;
import ec.simple.*;
import ec.vector.*;

public class KnapSack extends Problem implements SimpleProblemForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Weights of the 10 items
	 */
	private int[] WEIGHTS = new int[] {
		14, 26, 19, 45, 5, 25, 34, 18, 30, 12			
	};
	
	/**
	 * Monetary values of the 10 items
	 */
	private int[] VALUES = new int[] {
		20, 24, 18, 70, 14, 23, 50, 17, 41, 21 	
	};
	
	/**
	 * Maximum weight supported by the Knapsack
	 */
	private int CAPACITY = 100;

	/**
	 * This is the java implementation of the fitness function
	 * for the Knapsack problem
	 * @param individual
	 * @return fitness as a value
	 */
	private int fitnessFunction(BitVectorIndividual individual) {
		
		int totalValue = 0;
		int totalWeight = 0;
				
		for (int i = 0; i < individual.genome.length; i++) {
			boolean isSelected = individual.genome[i];
			if(isSelected) {
				totalValue += VALUES[i];
				totalWeight += WEIGHTS[i];
			}
		}
		
		/* if the sack is under weight return the value as the fitness */
		if (totalWeight <= CAPACITY) {
			return totalValue;
		}
		/* else return the amount it is overweight as a negative fitness */
		else {
			return -(totalWeight - CAPACITY);
		}
	}

	/**
	 * Callback method of the ECJ framework which is used to evaluate 
	 * any individual against the fitness function.
	 */
	public void evaluate(final EvolutionState state, final Individual ind,
			final int subpopulation, final int threadnum) {
		if (ind.evaluated)
			return;

		if (!(ind instanceof BitVectorIndividual))
			state.output.fatal("Whoa!  It's not a BitVectorIndividual!!!", null);
		
		BitVectorIndividual ind2 = (BitVectorIndividual) ind;
		float fitnessValue = (float)this.fitnessFunction(ind2);
		
		if (!(ind2.fitness instanceof SimpleFitness)) {
			state.output.fatal("Whoa!  It's not a SimpleFitness!!!", null);
			return;
		}
		
		SimpleFitness fitness = (SimpleFitness)ind2.fitness;
		fitness.setFitness(state, fitnessValue, false);
		ind2.evaluated = true;
	}

	/**
	 * This method is optional. However it can be used to improve the 
	 * verbosity of the statistical output of GA.
	 */
	public void describe(final Individual ind, final EvolutionState state,
			final int subpopulation, final int threadnum, final int log,
			final int verbosity) {
	}
}
