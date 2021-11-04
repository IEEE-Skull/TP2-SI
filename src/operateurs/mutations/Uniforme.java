package operateurs.mutations;

import Configurations.Config;

import variables.Allele;
import variables.Individu;

import java.util.BitSet;


public class Uniforme {

	private Individu individu;
	private double probability;
	private Config config;
	

	public Uniforme (Individu individu, double probability, Config cfg){
		this.individu = individu;
		this.probability = probability;
		config = cfg;
	}
	
	public Individu doMutate(){
		//Nombre de Genes dans l'individu
		int nbGenes = individu.getChromosome().length();
		//Quel Gene choisir?
		int numGene = (int)(getRandomValue()*nbGenes);
		//Nombre de bits dans un gene
		int tailleDuGene = individu.getChromosome().getGenotype().getGene(numGene).getNbBits();
		//Random Bit du Gene choisi
		int numBit = (int)(getRandomValue()*tailleDuGene);
		BitSet alleleA = new BitSet(tailleDuGene);
		alleleA = individu.getAllele(numGene).getBitValue();
		if(alleleA.get(numBit) == true){
			alleleA.clear(numBit);
		}else{
			alleleA.set(numBit);
		}
		individu.setBitAllele(numGene,alleleA);
		return individu;
	}
	
	public double getRandomValue(){
		return config.getRandom();
	}
}
