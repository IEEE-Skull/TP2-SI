package variables;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import Configurations.Config;


/**
 * 
 * Le genotype est l'ensemble des genes d'un individu
 * 
 */

public class Genotype {

	private Gene[] genesVector;
	private Config config;


	public Genotype(Config cfg) {
		List<String[]> genesList = new ArrayList();
		config = cfg;
		//Taille de chromosome 3
		int nbGenes = config.getNbVariables();
		genesVector = new Gene[nbGenes];
		// Create 3 genes
		for (int i = 0; i < nbGenes; i++){
			// Gene(0,5,0.1)
			genesVector[i] = new Gene(cfg.getMin(i), 
									  cfg.getMax(i),					
									  cfg.getPrecision(i), config);					
		}
	}

	/**
	 * Retourne le nombre de genes (la taille du chromosome) 
	 * 
	 * @return Nombre de genes
	 */
	public int length() {
		return genesVector.length;
	}

	/**
	 * Retourne le i^eme gene du genotype
	 * 
	 * @param i
	 * @return Le i^eme gene
	 */
	public Gene getGene(int i) {
		return genesVector[i];
	}


	/**
	 */
	public String toString() {
		String s = "";
		for (int i = 0; i < length(); i++) {
			s += genesVector[i].toString() + "\n";
		}
		return s;
	}
	
	

}
