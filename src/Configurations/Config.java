package Configurations;

import java.util.Random;

public class Config {

	private int taillePopulation =  100;  // Nombre de solutions evaluees A chaque generation.
	private int nbGeneration = 		50000; 	 // Nombre de generations
	
	private double tauxCroisement = 0.9;
	private String typeCroisement = "2PX";
	private double tauxMutation = 0.1;
	private double tauxRemplacement = 0.2; // Pourcentage de parents qui seront remplacEs par des solutions filles
	
	//Default: 3
	// 7
	int nbVariables = 7;  // Nombre de parametres d'entree (taille du chromosome)
	private double[][] domaineDefinition = new double [nbVariables][3]; 
	
	long graine = 11;
	Random random;
	
	
	public Config(){
		
		for (int i = 0; i < nbVariables; i++) {
			domaineDefinition[i][0] = 0.0; // valeur min pour la variable
			//Default : 5.0
			// 31-0+1 = 32 Values 5 bits each Gene
			//3.1
			domaineDefinition[i][1] = 3.1; // valeur max pour la variable
			domaineDefinition[i][2] = 0.1; // precision pour la variable
		}
		
		random = new Random(graine); // Instanciation du generateur de nombre aleatoire (c'est lui qui sera utilisE dans tout le programme)
	}


	public int getPopSize(){
		return taillePopulation;
	}

	public int getNbGeneration(){
		return nbGeneration;
	}
	
	public double getCrossoverRate(){
		return tauxCroisement;
	}
	
	public String getCrossoverType(){
		return typeCroisement;
	}
	public double getMutationRate(){
		return tauxMutation;
	}
	
	public double getReplacementRate(){
		return tauxRemplacement;
	}
	
	public int getNbVariables(){ // Retourne le nombre de parametres du probleme
		return nbVariables;
	}
	
	public double getMin(int i){ // Retourne la borne inferieure de l'intervalle de variation du i^eme parametetre
		return domaineDefinition[i][0];
	}
	
	public double getMax(int i){ // Retourne la borne superieure de l'intervalle de variation du i^eme parametetre
		return domaineDefinition[i][1];
	}
	
	public double getPrecision(int i){ // Retourne la precision souhaitee pour le i^eme parametetre
		return domaineDefinition[i][2];
	}
	
	public double getRandom(){ // Genere un nombre pseudo-aleatoire
		return random.nextDouble();
	}
	
	public void setGraine(long g){
		graine = g;
		random = new Random(graine);
	}
}
