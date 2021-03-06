package no.hib.dat102.utsyn;

import java.util.Iterator;
import java.util.Scanner;

import no.hib.dat102.mengde.adt.MengdeADT;
import no.hib.dat102.mengde.kjedet.KjedetMengde;
import no.hib.dat102.modell.Datakontakt;
import no.hib.dat102.modell.Hobby;
import no.hib.dat102.modell.Medlem;

/**
 * Klasse for tekstgrensesnitt
 * 
 * @author Kristoffer-Andre Kalliainen
 *
 */
public class Tekstgrensesnitt {
	private Scanner tast;
	private Hobby nyHobby;

	/**
	 * Leser opplysningene om et medlem fra tastatur
	 * 
	 * @return Nytt Medlem
	 */
	public Medlem lesMedlem() {
		tast = new Scanner(System.in);

		System.out.print("Oppgi navn: ");
		String navn = tast.next();

		MengdeADT<Hobby> hobbyer = new KjedetMengde<Hobby>();
		System.out.print("Oppgi hobbyer, avslutt med zzz : ");
		String streng = tast.next();
		// leser inn hobbyer
		while (!streng.equals("zzz")) {
			nyHobby = new Hobby(streng);
			hobbyer.leggTil(nyHobby);

			System.out.print("Oppgi hobbyer, avslutt med zzz : ");
			streng = tast.next();
		} // while

		// setter standard status indeks til -1
		int statusIndeks = -1;

		Medlem nyMedlem = new Medlem(navn, statusIndeks);
		nyMedlem.setHobbyer(hobbyer);
		return nyMedlem;
	}

	/**
	 * Skriver hobbylisten for et medlem
	 * 
	 * @param medlem
	 *            Medlem
	 */
	public void skrivHobbyListe(Medlem medlem) {
		System.out.println(medlem.getHobbyer().toString());
	}

	/**
	 * Skriver ut p� skjermen en oversikt over medlemmer som er koblet til
	 * hverandre i medlemstabellen til enhver tid. Et slikt par skal kun vises
	 * en gang p� utskriftlisten. Metoden skriver ogs� ut antall par som er
	 * funnet.
	 * 
	 * @param arkiv
	 *            Datakontakt arkiv som skal skrives ut
	 */
	public void skrivParListe(Datakontakt arkiv) {
		KjedetMengde<Integer> par = new KjedetMengde<Integer>();
		int partnerIndex = -1;
		int antallPar = 0;
		for (int i = 0; i < arkiv.getAntallMedlemmer(); i++) {
			partnerIndex = arkiv.getMedlemstabell()[i].getStatusIndeks();
			if (partnerIndex > -1 && !par.inneholder(i) && arkiv.getMedlemstabell()[i] != null) {
				par.leggTil(i);
				par.leggTil(partnerIndex);
				antallPar++;
			}
		}

		System.out.printf("%15s%15s%n", "Par: (Antall: " + antallPar + ")", "Interesser:");

		Iterator<Integer> teller = par.oppramser();
		Integer id1;
		Integer id2;
		for (int i = 0; i < antallPar; i++) {
			id1 = teller.next().intValue();
			id2 = teller.next().intValue();
			System.out.printf("%-20s",
					arkiv.getMedlemstabell()[id1].getNavn() + " og " + arkiv.getMedlemstabell()[id2].getNavn());

			// Hobbyer for id1 og id2 vil v�re like
			System.out.printf("%15s%n", arkiv.getMedlemstabell()[id1].getHobbyer());
		}
	}
}
