package variables;


import Configurations.Config;

/**
 * 
 * Un gene est une unitE de base de l'information qui caracterise un individu (une solution)
 * 
 */

public class Gene {
// gene[Min,Max,Precision]
	private double gene [] = new double [3];
	private Config config;

	public Gene(double minValue, double maxValue, double precision, Config cfg){
		gene[0] = minValue;
		gene[1] = maxValue;
		gene[2] = precision;
		config = cfg;
	}


	/**
	 * @return Returns the lower bound of the gene.
	 */
	public double getMin() {
		return gene[0];
	}

	/**
	 * @return Returns the upper bound of the gene.
	 */
	public double getMax() {
		return gene[1];
	}

	/**
	 * @return Returns the precision of the gene.
	 */
	public int getPrecision() {
		return (int)gene[2];
	}

	public int getNbBits(){
		// ln (Max-Min/Precision)
		double taille = Math.log((gene[1] - gene[0])/gene[2]) / Math.log(2);
		//Round up
		int nbBits = (int) Math.ceil(taille);
		//Return Number of Bits to code on
		return nbBits;
	}

	/**
	 * @return Returns the different information relative to the gene (type, lower and upper bounds)
	 */
	public String toString() {
		String s = "";
		for(int i = 0; i < gene.length; i++){
			s += gene[i] + " ";
		}
		return s;
	}

	public Config getConfig(){
		return config;
	}

}
