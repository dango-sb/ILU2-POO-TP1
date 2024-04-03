package villagegaulois;


import exceptions.VillageSansChefException;
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

	public String afficherVillageois() throws VillageSansChefException {
		if(chef == null) {
			throw new VillageSansChefException("Ce village n'a pas de chef.");
		}
		else {
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
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre "
				+ nbProduit + " " + produit + ".\n");
		int indEtal = marche.trouverEtalLibre();
		if(indEtal == -1) {
			chaine.append("Il n'y a plus de place.\n");
		}
		else {
			marche.utiliserEtal(indEtal,vendeur,produit,nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit 
					+ " é l'étal " + indEtal + ".");
		}
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
			chaine.append("Les vendeurs qui proposent des fleurs sont :\n");
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
	private static class Marche{
		private Etal[] etals;
	
		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if(!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			Etal[] etalsProduit = new Etal[this.etals.length];
			int nbEtals = 0;
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].contientProduit(produit)) {
					etalsProduit[nbEtals] = etals[i];
					nbEtals++;
				}
			}
			return etalsProduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			String marche = "";
			int nbEtalsVides = 0;
			for (int i = 0; i < etals.length; i++) {
				if(!etals[i].isEtalOccupe()) {
					nbEtalsVides++;
				}
				else {
					marche = marche + etals[i].afficherEtal();
				}
			}
			if(nbEtalsVides != 0) {
				marche = marche + "Il reste " + nbEtalsVides + " etals non utlisés dans le marché.\n";
			}
			return marche;
		}
	}
}