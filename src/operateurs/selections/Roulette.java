package operateurs.selections;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import Configurations.Config;

import com.sun.source.doctree.SummaryTree;
import variables.Allele;
import variables.Individu;
import variables.Population;

import javax.naming.directory.InitialDirContext;


public class Roulette {

	private List<Individu> pop = new ArrayList();
	private BitSet bitValue;
	private Config config;
	
	public Roulette(List<Individu> _pop, Config cfg){
		this.pop = _pop;
		config = cfg;
	}
	
	public Individu doSelect(){
		double SumFitness=0;
		SumFitness=sumFitness(pop);
		double RanD= Math.random()*SumFitness;
		int INDEX=0;
		Collections.sort(pop, new Comparator<Individu>() {
			public int compare(Individu I1, Individu I2) {
				if (I1.getFitness() > I2.getFitness()) return -1;
				if (I1.getFitness() < I2.getFitness()) return 1;
				return 0;
			}});
		double MaxProb=0;
	//	System.out.println(SumFitness+" : "+RanD+" ");
		for(int i=0;i<pop.size();i++){
			Individu I = pop.get(i);
			MaxProb+=I.getFitness();
			if(MaxProb >= RanD){
				INDEX = i;
				break;
			}
		//	System.out.println(I.toString()+" "+I.getFitness());
		}
		return pop.get(INDEX);
	}
	
	public double sumFitness(List<Individu> pop){
		double Fitness=0,SumFitness=0;
		Individu I= pop.get(0);
		Allele A;
		// 5
		int NumGene = I.getAllele(0).getGene().getNbBits();
		bitValue = new BitSet(NumGene);
		int counter,intValue,NumBitsChromosome;
		NumBitsChromosome = config.getNbVariables()*NumGene-1;
		counter = NumBitsChromosome;
		for(int i=0;i<pop.size();i++){
			I = pop.get(i);
			for(int j=0;j<config.getNbVariables();j++){
				A = I.getAllele(j);
				bitValue=A.getBitValue();
				for(int z=0;z<NumGene;z++){
					if(bitValue.get(z) == true){
						Fitness += 	Math.pow(2, counter);
					}
					counter--;
				}
			}
			pop.get(i).setFitness(Fitness);
			SumFitness+=Fitness;
			Fitness=0;
			counter=NumBitsChromosome;
		}
		return SumFitness;
	}

}
