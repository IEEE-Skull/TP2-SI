package problemes;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

import Configurations.Config;

import operateurs.croisements.Croisement;
import operateurs.croisements.Croisement1Point;
import operateurs.croisements.Croisement2Points;
import operateurs.mutations.Uniforme;
import operateurs.selections.Roulette;
import operateurs.selections.Tournoi;
import variables.*;


public class Parallelepipede {

	public List<Individu> popList = new ArrayList();
	public List<Individu> popFilleList = new ArrayList();
	public int popSize;
	public int nbGenerations = 1;
	private Config config;
	private int nbObj = 0;
	int generationNumber = 0;

	String crossoverType;
	double crossoverRate;
	double mutationRate;
	double replacementRate;

	Individu bestIndiv;

	boolean objToMaximise = true;
	
	public static void main(String[] args) {
		double tDeb = System.currentTimeMillis();
		new Parallelepipede();
		double tFin = System.currentTimeMillis();
		System.out.println("\nDuree execution : " + (tFin - tDeb)/1000 + "s");
	}

	public Parallelepipede(){
		config = new Config();
		crossoverType = config.getCrossoverType();
		crossoverRate = config.getCrossoverRate();
		mutationRate = config.getMutationRate();
		replacementRate = config.getReplacementRate();
		exec();
	}

	public void exec(){
		Genotype genotype = new Genotype(config);
		popSize = config.getPopSize();
		nbGenerations = config.getNbGeneration();

		Population pop = new Population(popSize, genotype);
		popList = pop.getAllIndividuals();

		// Evaluation de la population initiale
		for (int i = 0; i < popList.size(); i++){
			double f = evaluer(popList.get(i));
			popList.get(i).setFitness(f);
			if (i == 0){
				bestIndiv = new Individu(popList.get(i));
			} else{
				if (objToMaximise){
					if (popList.get(i).getFitness() > bestIndiv.getFitness()){
						bestIndiv = new Individu((popList.get(i)));
					}
				} else {
					if (popList.get(i).getFitness() < bestIndiv.getFitness()){
						bestIndiv = new Individu((popList.get(i)));
					}
				}

			}
		}
		
		System.out.println("Gen 1 ");
		System.out.println("Best indiv : " + bestIndiv.getAllele(0).getDoubleValue() + " " 
				+ bestIndiv.getAllele(1).getDoubleValue() + " "
				+ bestIndiv.getAllele(2).getDoubleValue() + " "
				+ " objectif : " + bestIndiv.getFitness());
		
		for (int n = 1; n < nbGenerations; n++){
			System.out.println("Gen : " + (n+1));
			popFilleList = regenerer();
			for (int i = 0; i < popFilleList.size(); i++){
				double f = evaluer(popFilleList.get(i));
				popFilleList.get(i).setFitness(f);
				if (i == 0 && n == 0){
					bestIndiv = new Individu(popFilleList.get(i));
				} else{
					if (objToMaximise){
						if (popFilleList.get(i).getFitness() > bestIndiv.getFitness()){
							bestIndiv = new Individu((popFilleList.get(i)));
						}
					} else {
						if (popFilleList.get(i).getFitness() < bestIndiv.getFitness()){
							bestIndiv = new Individu((popFilleList.get(i)));
						}
					}

				}
			}
			triListe(popList);
			triListe(popFilleList);
			
			remplacer();

			System.out.println("Best indiv : " + bestIndiv.getAllele(0).getDoubleValue() + " " 
					+ bestIndiv.getAllele(1).getDoubleValue() + " "
					+ bestIndiv.getAllele(2).getDoubleValue() + " "
					+ " objectif : " + bestIndiv.getFitness());
		}
		
	}

	
	
	public List<Individu> regenerer(){
	//	Tournoi selection = new Tournoi(popList, config);
		Roulette selection = new Roulette(popList, config);
		List<Individu> newPopList = new ArrayList();

		for (int i = 0; i < (int) popSize/2; i++){
			
			//Selection des parents pour le croisement
			Individu parent1 = new Individu(selection.doSelect());
			Individu parent2 = new Individu(selection.doSelect());

			Croisement crossover = null;
			if (crossoverType.equalsIgnoreCase("1PX")){
				crossover = new Croisement1Point(parent1,parent2, crossoverRate, config);
			}else{
				crossover = new Croisement2Points(parent1,parent2, crossoverRate, config);
			}
			Individu child1 = new Individu(crossover.getOffspring(1));
			Individu child2 = new Individu(crossover.getOffspring(2));
			
			// A decommenter pour effectuer la mutation
			child1 = new Uniforme(child1, mutationRate, config).doMutate();
			child2 = new Uniforme(child2, mutationRate, config).doMutate();

			newPopList.add(child1);
			newPopList.add(child2);
		} 

		return newPopList;
	}

	public double evaluer(Individu indiv){
		double a = indiv.getAllele(0).getDoubleValue();
		double b = indiv.getAllele(1).getDoubleValue();
		double c = indiv.getAllele(2).getDoubleValue();

		double f = (a*b*c)/(4*(a+b+c)) ;

		return f;

	}
	
	// Tri les listes par ordre croissant de valeur de fitness.
	public void triListe(List<Individu> list){
		for(int i = list.size()-1; i > 0; i--){
			for(int j = 0; j < i; j++){
				if (list.get(j).getFitness() < list.get(j+1).getFitness()){
					list.add(j, list.get(j+1));
					list.remove(j+2);
				}
			}
		}
	}
	
	public void remplacer(){
		int limite = (int) (popList.size() * (1 - replacementRate));
		for (int i = limite; i < popSize; i++ ){
			popList.remove(popList.size()-1);
		}
		for (int i = 0; i < popFilleList.size()-limite; i++ ){
			popList.add(popFilleList.get(limite + i));
		}
	}

}
