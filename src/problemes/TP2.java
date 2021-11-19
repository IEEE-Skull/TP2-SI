package problemes;
import java.util.*;

import Configurations.Config;

import operateurs.croisements.Croisement;
import operateurs.croisements.Croisement1Point;
import operateurs.croisements.Croisement2Points;
import operateurs.mutations.Uniforme;
import operateurs.selections.Roulette;
import operateurs.selections.Tournoi;
import variables.*;

public class TP2 {
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
        new TP2();
        double tFin = System.currentTimeMillis();
        System.out.println("\nDuree execution : " + (tFin - tDeb)/1000 + "s");
    }

    public TP2(){
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
        double Sol=0;
        Population pop = new Population(popSize, genotype);
        popList = pop.getAllIndividuals();
        int NumGene = popList.get(0).getAllele(0).getGene().getNbBits();
        int counter = config.getNbVariables()*NumGene-1;
        while(counter >= 0) {
            Sol += Math.pow(2, counter);
            counter--;
        }
        // Evaluation de la population initiale
        Roulette selection = new Roulette(popList, config);
        double SumF = selection.sumFitness(popList);
        Collections.sort(popList, new Comparator<Individu>() {
            public int compare(Individu I1, Individu I2) {
                if (I1.getFitness() > I2.getFitness()) return -1;
                if (I1.getFitness() < I2.getFitness()) return 1;
                return 0;
            }});
        bestIndiv = new Individu(popList.get(0));
        System.out.println("Gen 1 ");
        System.out.print("Best indiv : " + bestIndiv.getAllele(0).getStringValue() + " ");
        for(int i=1;i<config.getNbVariables();i++){
            System.out.print(bestIndiv.getAllele(i).getStringValue() + " ");
        }
        System.out.println(" objectif : " + bestIndiv.getFitness());
        Roulette selectionFille;
        for (int n = 1; n < nbGenerations; n++){
            System.out.println("Gen : " + (n+1));
            popFilleList = regenerer();
            selectionFille =  new Roulette(popFilleList, config);
            double SumFille = selectionFille.sumFitness(popFilleList);
            Collections.sort(popFilleList, new Comparator<Individu>() {
                public int compare(Individu I1, Individu I2) {
                    if (I1.getFitness() > I2.getFitness()) return -1;
                    if (I1.getFitness() < I2.getFitness()) return 1;
                    return 0;
                }});
            if(objToMaximise) {
                if (popFilleList.get(0).getFitness() > bestIndiv.getFitness()) {
                    bestIndiv = new Individu((popFilleList.get(0)));
                }
            }else{
                break;
            }
            remplacer();
            System.out.print("Best indiv : " + bestIndiv.getAllele(0).getStringValue() + " ");
            for(int i=1;i<config.getNbVariables();i++){
                System.out.print(bestIndiv.getAllele(i).getStringValue() + " ");
            }
            System.out.println(" objectif : " + bestIndiv.getFitness());
            if(bestIndiv.getFitness() == Sol){
                objToMaximise = false;
            }
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
