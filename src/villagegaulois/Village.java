package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
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
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + String.valueOf(nbProduit) + " " + produit + ".");
		int etalLibre = this.marche.trouverEtalLibre();
		if (etalLibre == -1) {
			chaine.append("Il n'y a pas de l'étal libré.");
		}
		else {
			this.marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des fleurs à l'étal "+ String.valueOf(etalLibre) + ".");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsProduit = this.marche.trouverEtals(produit);
		if (etalsProduit != null) {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :");
			for (Etal e: etalsProduit) {
				chaine.append("- " + e.getVendeur().getNom());
			}
		}
		else {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.");
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		Etal etalTrouve = marche.trouverVendeur(vendeur);
		System.out.println(etalTrouve.afficherEtal());
		return etalTrouve;
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalOccupe = rechercherEtal(vendeur);
		return etalOccupe.libererEtal();	
	} 
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"" + this.getNom() + "\"possède plusieurs étals :");
		chaine.append(marche.afficherMarche());
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
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int etalsOccupes=0;
			for (int i=0;i>this.capacite;i++) {
				if (this.etals[i].isEtalOccupe()) {
					chaine.append((this.etals[i].afficherEtal()));
					etalsOccupes++;
				}
			}
			chaine.append("Il reste " + String.valueOf(this.capacite - etalsOccupes) + "�tals non utilis�s dans la march�");
			return chaine.toString();
		}
	}
}