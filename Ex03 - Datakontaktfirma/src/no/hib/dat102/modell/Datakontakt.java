package no.hib.dat102.modell;

/**
 * Datakontakt
 * 
 * @author Kristoffer-Andre Kalliainen
 *
 */
public class Datakontakt {
	private final static int SDTK = 100;
	private Medlem[] medlemstabell;
	private int antallMedlemmer;

	public Datakontakt() {
		this(SDTK);
	}

	public Datakontakt(int stoerrelse) {
		antallMedlemmer = 0;
		medlemstabell = new Medlem[stoerrelse];
	}

	/**
	 * @return the medlemstabell
	 */
	public Medlem[] getMedlemstabell() {
		return medlemstabell;
	}

	/**
	 * @param medlemstabell
	 *            the medlemstabell to set
	 */
	public void setMedlemstabell(Medlem[] medlemstabell) {
		this.medlemstabell = medlemstabell;
	}

	/**
	 * @return the antallMedlemmer
	 */
	public int getAntallMedlemmer() {
		return antallMedlemmer;
	}

	/**
	 * @param antallMedlemmer
	 *            the antallMedlemmer to set
	 */
	public void setAntallMedlemmer(int antallMedlemmer) {
		this.antallMedlemmer = antallMedlemmer;
	}

	/**
	 * @return the sdtk
	 */
	public static int getSdtk() {
		return SDTK;
	}

	/**
	 * Legger til et nytt medlem i medlemstabellen
	 * 
	 * @param person
	 *            Medlem som skal legges til
	 */
	public void leggTilMedlem(Medlem person) {
		if (antallMedlemmer == medlemstabell.length) {
			utvidKapasitet();
		}
		medlemstabell[antallMedlemmer] = person;
		antallMedlemmer++;
	}

	private void utvidKapasitet() {
		Medlem[] hjelpetabell = new Medlem[(int) Math.ceil(2 * medlemstabell.length)];
		for (int i = 0; i < medlemstabell.length; i++) {
			hjelpetabell[i] = medlemstabell[i];
		}
		medlemstabell = hjelpetabell;
	}

	/**
	 * Finner indeksen til medlemmet i medlemstabellen dersom medlemmet er
	 * registrert, ellers returneres indeks lik -1.
	 * 
	 * @param medlemsnavn
	 *            Medlemsnavn
	 * @return Indeks til medlemmet, ellers -1
	 */
	public int finnMedlemsIndeks(String medlemsnavn) {
		int pos = -1;
		for (int i = 0; (i < antallMedlemmer) && (pos == -1); i++) {
			if (medlemstabell[i].getNavn().equals(medlemsnavn)) {
				pos = i;
			}
		}
		return pos;
	}

	/**
	 * Finner ut om et medlem (identifisert med medlemsnavn) passer med et annet
	 * medlem (dersom det finnes i medlemstabellen). Dette medlemmet skal v�re
	 * det f�rste som passer og ikke er "koblet". Metoden oppdaterer
	 * medlemstabellen dersom det finner en partner, og returnerer eventuell
	 * indeks til partneren i medlemstabellen (eller -1).
	 * 
	 * @param medlemsnavn
	 *            Medlemsnavn
	 * @return Indeks til partner, ellers -1.
	 */
	public int finnPartnerFor(String medlemsnavn) {
		int medlemsIndeks = finnMedlemsIndeks(medlemsnavn);
		int matchIndeks = -1;
		for (int i = 0; i < medlemstabell.length && matchIndeks == -1; i++) {
			if (medlemstabell[i] != null && !medlemstabell[i].equals(medlemstabell[medlemsIndeks]) && medlemstabell[i].passerTil(medlemstabell[medlemsIndeks])) {
				medlemstabell[i].setStatusIndeks(medlemsIndeks);
				medlemstabell[medlemsIndeks].setStatusIndeks(i);

				matchIndeks = i;
			}
		}
		return matchIndeks;
	}

	/**
	 * Oppdaterer medlemstabellen, slik at dette medlemmet(identifisert ved
	 * medlemsnavn) og dets partner, dersom det fins, ikke lenger er "koblet"
	 * (dvs. begge f�r statusindeks -1)
	 * 
	 * @param medlemsnavn
	 *            Medlemsnavn
	 */
	public void tilbakestillStatusIndeks(String medlemsnavn) {
		int medlemsIndex = finnMedlemsIndeks(medlemsnavn);
		int matchIndex = finnPartnerFor(medlemsnavn);

		if (matchIndex > -1 && medlemsIndex > -1) {
			medlemstabell[medlemsIndex].setStatusIndeks(-1);
			medlemstabell[matchIndex].setStatusIndeks(-1);
		}
	}

	public int antall() {
		return antallMedlemmer;
	}

	public boolean erTom() {
		return (antall() == 0);
	}
}
