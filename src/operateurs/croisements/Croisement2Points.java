package operateurs.croisements;


import Configurations.Config;

import variables.Individu;

import java.util.BitSet;

public class Croisement2Points extends Croisement{

	Individu offspring1;
	Individu offspring2; 
	Config config;

	public Croisement2Points(Individu parent1, Individu parent2, double _xProbability, Config cfg) {
		super(parent1, parent2, _xProbability);

		offspring1 = new Individu(parent1.getChromosome().getGenotype());
		offspring2 = new Individu(parent2.getChromosome().getGenotype());
		config = cfg;
		double alea = config.getRandom();
		if (alea <= super.getXProbability()){
			doCross(parent1, parent2);
		}
	}

	@Override
	public void doCross(Individu parent1, Individu parent2) {
		int nbGenes = parent1.getChromosome().length();
		int numGene = (int)(getRandomValue()*nbGenes);
		int numGene2;
		do {
			numGene2 = (int) (getRandomValue() * nbGenes);
		}while(numGene2 < numGene);

		int tailleDuGene = parent1.getChromosome().getGenotype().getGene(numGene).getNbBits();
		int numBit = (int)(getRandomValue()*tailleDuGene);
		int numBit2 = (int)(getRandomValue()*tailleDuGene);
		for (int i = 0; i < numGene; i++){
			offspring1.setBitAllele(i, parent1.getAllele(i).getBitValue());
			offspring2.setBitAllele(i, parent2.getAllele(i).getBitValue());
		}
		for (int i = numGene2+1; i < tailleDuGene; i++){
			offspring1.setBitAllele(i, parent1.getAllele(i).getBitValue());
			offspring2.setBitAllele(i, parent2.getAllele(i).getBitValue());
		}

		BitSet allele1 = new BitSet(tailleDuGene);
		BitSet allele2 = new BitSet(tailleDuGene);
		BitSet allele3 = new BitSet(tailleDuGene);
		BitSet allele4 = new BitSet(tailleDuGene);
		for (int i = 0; i < tailleDuGene; i++){
			if (i < numBit){
				allele1.set(i, parent1.getAllele(numGene).getBitValue().get(i));
				allele2.set(i, parent2.getAllele(numGene).getBitValue().get(i));
			} else{
				allele1.set(i, parent2.getAllele(numGene).getBitValue().get(i));
				allele2.set(i, parent1.getAllele(numGene).getBitValue().get(i));
			}
			if(i > numBit2){
				allele3.set(i, parent1.getAllele(numGene).getBitValue().get(i));
				allele4.set(i, parent2.getAllele(numGene).getBitValue().get(i));
			}else{
				allele3.set(i, parent2.getAllele(numGene).getBitValue().get(i));
				allele4.set(i, parent1.getAllele(numGene).getBitValue().get(i));
			}
		}
		offspring1.setBitAllele(numGene, allele1);
		offspring2.setBitAllele(numGene, allele2);
		offspring1.setBitAllele(numGene2, allele3);
		offspring2.setBitAllele(numGene2, allele4);

		for (int i = numGene+1; i < numGene2; i++){
			offspring1.setBitAllele(i, parent2.getAllele(i).getBitValue());
			offspring2.setBitAllele(i, parent1.getAllele(i).getBitValue());
		}

	}

	public Individu getOffspring(int i){
		if (i == 1){
			return offspring1;
		}else{
			return offspring2;
		}
	}

	public double getRandomValue(){
		return config.getRandom();
	}
}
