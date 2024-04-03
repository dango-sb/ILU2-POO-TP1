package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
		if(etalOccupe) {
			etalOccupe = false;
			StringBuilder chaine = new StringBuilder(
					"Le vendeur " + vendeur.getNom() + " quitte son étal, ");
			int produitVendu = quantiteDebutMarche - quantite;
			if (produitVendu > 0) {
				chaine.append(
						"il a vendu " + produitVendu  + " " + produit + " parmi les " 
				+ quantiteDebutMarche + " qu'il voulait vendre.\n");
			} else {
				chaine.append("il n'a malheureusement rien vendu.\n");
			}
			return chaine.toString();
		}
		else {
			return "L'étal est déjà libre.";
		}
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'étal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) 
			throws IllegalArgumentException, IllegalStateException {
		if(quantiteAcheter < 1) {
			throw new IllegalArgumentException("La quantité achetée doit-être "
					+ " un entier supèrieur à 0."); 
		}
		if(!etalOccupe) {
			throw new IllegalStateException("Cet étal est vide.");
		}
		else {
			StringBuilder chaine = new StringBuilder();
			try {
				chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
						+ " " + produit + " é " + vendeur.getNom());
			}
			catch(NullPointerException e){
				e.printStackTrace();
				return chaine.toString();
			}
			if (quantite == 0) {
				chaine.append(", malheureusement il n'y en a plus !");
				quantiteAcheter = 0;
			}
			if (quantiteAcheter > quantite) {
				chaine.append(", comme il n'y en a plus que " + quantite + ", "
						+ acheteur.getNom() + " vide l'étal de "
						+ vendeur.getNom() + ".\n");
				quantiteAcheter = quantite;
				quantite = 0;
			}
			if (quantite != 0) {
				quantite -= quantiteAcheter;
				chaine.append(". " + acheteur.getNom()
				+ ", est ravi de tout trouver sur l'étal de "
				+ vendeur.getNom() + "\n");
			}
			return chaine.toString();
		}
	}

	public boolean contientProduit(String produit) {
		return produit.equals(this.produit);
	}

}
