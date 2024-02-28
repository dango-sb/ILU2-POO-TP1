package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	private static class Marche {
		private Etal[] etals;
		private int capacite;
		
		private Marche(int nombreEtals) {
			this.etals = new Etal[nombreEtals];
			this.capacite = nombreEtals;
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit); 
		} 
		
		private int trouverEtalLibre() {
			for (int i=0;i<capacite;i++) {
				if (this.etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			Etal[] etalsProd = new Etal[this.capacite];
			int j=0;
			for (int i=0;i<capacite;i++) {
				if (this.etals[i].contientProduit(produit)) {
					etalsProd[j++] = this.etals[i];
				}
			}
			return etalsProd;
		}
		
		private Etal trouverVendeur(Gaulois gaulois)
		{
			for (int i=0;i<this.capacite;i++) {
				if (this.etals[i].getVendeur()==gaulois) {
					return this.etals[i];
				}
			}
			return null;
		}
		
		private void afficherMarche() {
			int etalsOccupes=0;
			for (int i=0;i>this.capacite;i++) {
				if (this.etals[i].isEtalOccupe()) {
					this.etals[i].afficherEtal();
					etalsOccupes++;
				}
			}
			if (this.capacite == etalsOccupes) {
				System.out.println("Il reste " + String.valueOf(this.capacite - etalsOccupes) + "étals non utilisés dans la marché");
			}
		}
	}
}