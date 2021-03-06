package no.hib.data102;

import no.hib.data102.adt.CDArkivADT;
import no.hib.data102.node.EmptyCollectionException;
import no.hib.data102.node.LinearNode;

/**
 * @author Kristoffer-Andre Kalliainen
 *
 */
public class CDArkiv2 implements CDArkivADT {
	private int antall;
	private LinearNode<CD> start;

	/**
	 * Oppretter ett tomt arkiv
	 */
	public CDArkiv2() {
		antall = 0;
		start = null;
	}

	@Override
	public CD[] hentCDTabell() {
		LinearNode<CD> node = start;
		CD[] cdtabell = new CD[antall];
		int i = 0;
		while (i < antall && node != null) {
			cdtabell[i] = node.getElement();
			node = node.getNeste();
			i++;
		}
		return cdtabell;
	}

	@Override
	public void leggTilCd(CD nyCd) {
		LinearNode<CD> temp = new LinearNode<CD>(nyCd);

		temp.setNeste(start);
		start = temp;
		antall++;
	}

	private LinearNode<CD> finnCd(int cdNr) {
		LinearNode<CD> p = start;
		LinearNode<CD> q = start;
		while (p != null) {
			if (p.getElement().getCdNr() == cdNr) {
				return q;
			}
			q = p;
			p = p.getNeste();
			
		}
		return null;
	}

	@Override
	public boolean slettCd(int cdNr) throws EmptyCollectionException {
		boolean tilstand = false;
		if (erTom()) {
			throw new EmptyCollectionException("CDArkiv");
		}
		// 1. m� s�ke etter node og sammenlige cdNr
		LinearNode<CD> resultat = finnCd(cdNr);

		// 2. Slette noden/cd'en
		if (resultat != null) {
			resultat.setNeste(resultat.getNeste());
			resultat = start;
			antall--;
			tilstand = true;
		}
		// 3. returnerer true hvis noden blir slettet
		return tilstand;
	}

	private CD[] finnFlerCd(String type, String soek) throws EmptyCollectionException {
		if (erTom()) {
			throw new EmptyCollectionException("CDArkiv");
		}
		CD[] c = new CD[antall];
		LinearNode<CD> p = start;
		LinearNode<CD> q = start;
		int i = 0;

		if (type.equals("Tittel")) {
			while (i < antall && p != null) {
				if (p.getElement().getTittel().contains(soek)) {
					c[i] = p.getElement();
					p = p.getNeste();
					i++;
				}
			}
		} else if (type.equals("Artist")) {
			while (i < antall && p != null) {
				if (p.getElement().getArtist().contains(soek)) {
					c[i] = p.getElement();
					p = p.getNeste();
					i++;
				}
			}
		}
		return c;
	}

	@Override
	public CD[] sokTittel(String delstreng) {
		return finnFlerCd("Tittel", delstreng);
	}

	@Override
	public CD[] sokArtist(String delstreng) {
		return finnFlerCd("Artist", delstreng);
	}

	@Override
	public int hentAntall() {
		return antall;
	}

	@Override
	public int hentAntall(Sjanger sjanger) {
		int antallCDISjanger = 0;
		LinearNode<CD> p = start;
		LinearNode<CD> q = start;
		int teller = 1;
		while (teller <= antall) {
			if (q.getElement().getSjanger() == sjanger) {
				antallCDISjanger++;
			}
			q = p;
			p = p.getNeste();
			teller++;
		}
		return antallCDISjanger;
	}

	/**
	 * Skjekker om arkivet er tomt
	 * 
	 * @return true visst arkivet er tomt
	 */
	private boolean erTom() {
		return (start == null);
	}

}
