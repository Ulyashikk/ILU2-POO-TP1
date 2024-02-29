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
		marche = new Marche(nbEtals);
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
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		int indiceEtalLibre = marche.trouverEtalLibre();
        if (indiceEtalLibre != -1) {
            marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
            return "Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (indiceEtalLibre + 1) + ".\n";
        } 
        return vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n";
        
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder text = new StringBuilder();
		Etal[] etalsProduit = marche.trouverEtals(produit);
		if (etalsProduit.length == 0) {
			text.append("Il n'y a pas de vendeur qui propose de " + produit + " au marché.\n");
		} else if (etalsProduit.length == 1) {
			text.append("Seul le vendeur " + (etalsProduit[0].getVendeur()).getNom() + " propose des " + produit + " au marché.\n");
		} else {
			text.append("Les vendeurs qui proposent des fleurs sont :\n");
			for (int i = 0; i < etalsProduit.length; i++) {
				text.append("- " + (etalsProduit[i].getVendeur()).getNom() + "\n");
			}
		}
		return text.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
        return marche.trouverVendeur(vendeur);
    }

	public String partirVendeur(Gaulois vendeur) {
		Etal etalVendeur = rechercherEtal(vendeur);
		return etalVendeur.libererEtal();
	}

    public String afficherMarche() {
        return marche.afficherMarche();
    }
	
	private class Marche {
		private Etal[] etals;
		
		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for(int i = 0; i<nbEtals; i++) etals[i] = new Etal();
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (indiceEtal >= 0 && indiceEtal < etals.length) 
                etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i< etals.length; i++) {
				if(!(etals[i].isEtalOccupe())) return i;
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			Etal[] produitEtal;
			int nbEtal = 0;
			
			for(int i = 0; i< etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) nbEtal++;
			}
			
			produitEtal = new Etal[nbEtal];
			for(int i = 0, j = 0; i< etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					produitEtal[j] = etals[i];
					j++;
				}
			}
			
			return produitEtal;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0, j = 0; i< etals.length; i++) {
				if(etals[i].getVendeur() == gaulois) return etals[i];
			}
			return null;
		}
		
		private String afficherMarche() {
			int nbEtalVide = 0;
			StringBuilder text = new StringBuilder();
			
			for (int i = 0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe()) text.append(etals[i].afficherEtal());
				else nbEtalVide++;
			}
			
			if(nbEtalVide != 0)
				text.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			
			return text.toString();
		}
		
	}
	
	
	
	
	
	
}