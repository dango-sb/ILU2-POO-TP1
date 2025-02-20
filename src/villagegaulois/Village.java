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
		marche = new Marche(nbEtals);
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
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	private class Marche{
		private Etal[] etals; 
		private int nbEtals;
		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			this.nbEtals = nbEtals;
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			int i=0;
			for(Etal e: etals) {
				if (!e.isEtalOccupe())
					return i;
				i++;
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			Etal[] etalsProduit = new Etal[nbEtals];
			int i = 0;
			for(Etal e: etals) {
				if (e.contientProduit(produit))
					etalsProduit[i++] = e;
			}
			return etalsProduit;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for(Etal e: etals) {
				if (e.getVendeur()==gaulois)
					return e;
			}
			return null;
		}
		
		public String afficherMarche() {
			int nbEtalsOccupe = 0;
			String info="";
			for(Etal e: etals) {
				if(!e.isEtalOccupe()) {
					info = info + "Il reste " + (nbEtals - nbEtalsOccupe) + " étals non utilisés dans le marché\n";
					return info;
				}
				info = info + e.afficherEtal();
			}
			return info;
		}
	}
	
	//Interaction avec class intern//
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder(vendeur.getNom() + " cherche un endroit pour vendre" + nbProduit + " " + produit + ".\n");
		int nbEtalLibre = marche.trouverEtalLibre();
		if (nbEtalLibre==-1) {
			chaine.append("Il n'y a pas des etals libres.\n");
			return chaine.toString(); 
		}
		marche.utiliserEtal(nbEtalLibre, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + "vend ses " + produit + " à l'étal n°" + nbEtalLibre + ".\n");
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		int nbVendeurs = 0;
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsProduit = marche.trouverEtals(produit);
		String[] nomVendeurs = new String[etalsProduit.length];
		
		for (int i = 0; i < etalsProduit.length; i++) {
			if(etalsProduit[i] != null) {
				nomVendeurs[nbVendeurs] = etalsProduit[i].getVendeur().getNom();
				nbVendeurs++;
			}
		}
		
		if(nbVendeurs == 0) {
			chaine.append("Il n'y a pas de vendeurs de " + produit + " au marché.\n");
		}
		else if(nbVendeurs == 1) {
			chaine.append("Seulement " + nomVendeurs + " propose des " 
					+ produit + " au marché.\n");
		}
		else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < nbVendeurs; i++) {
				chaine.append("- " + nomVendeurs[i] + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return this.marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalVendeur = rechercherEtal(vendeur);
		return etalVendeur.libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"" + nom + "\" posséde plusieurs étals :\n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
}