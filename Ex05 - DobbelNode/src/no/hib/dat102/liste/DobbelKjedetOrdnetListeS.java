package no.hib.dat102.liste;

import no.hib.dat102.liste.adt.DobbelKjedetOrdnetListeSADT;

//********************************************************************
//  DobbeKjedetlOrdnetListeS.java       						//
//  Representerer en dobbeltkjedet ordnet liste med midtpeker.
//********************************************************************

public class DobbelKjedetOrdnetListeS<T extends Comparable<T>> implements DobbelKjedetOrdnetListeSADT<T> {

	private DobbelNode<T> foerste;
	private DobbelNode<T> midten;
	private DobbelNode<T> siste;
	private int antall;

	/******************************************************************
	 * Oppretter en tom liste.
	 ******************************************************************/
	public DobbelKjedetOrdnetListeS(T minVerdi, T maksVerdi) {

		// F�rste node
		DobbelNode<T> nyNode1 = new DobbelNode<T>(minVerdi);
		foerste = nyNode1;
		midten = foerste;

		// Siste node
		DobbelNode<T> nyNode2 = new DobbelNode<T>();
		nyNode2.setElement(maksVerdi);
		nyNode1.setNeste(nyNode2);
		nyNode2.setForrige(nyNode1);
		siste = nyNode2;

		antall = 0;
	}

	// ***********************************************************************************
	// *
	// *
	// ***********************************************************************************

	public void leggTil(T el) {

		// Setter inn ordnet f�r den noden p peker p�
		DobbelNode<T> p;

		if ((el.compareTo(foerste.getElement()) <= 0) || (el.compareTo(siste.getElement()) >= 0)) {
			// Ugyldig. Alternativt kan vi ha det som et forkrav!
			System.out.println("Ugyldig verdi. verdi > " + foerste.getElement() + "verdi < " + siste.getElement());

		} else { // Kun lovlige verdier

			antall++;

			if (el.compareTo(midten.getElement()) >= 0) {// Finn plass i siste
															// halvdel
				p = midten.getNeste();

			} else { // Finn plass i f�rste halvdel
				p = foerste.getNeste();
			}

			while (el.compareTo(p.getElement()) >= 0) {
				p = p.getNeste();
			} // while

			DobbelNode<T> nyNode = new DobbelNode<T>(el);

			nyNode.setNeste(p);
			nyNode.setForrige(p.getForrige());
			p.getForrige().setNeste(nyNode);
			p.setForrige(nyNode);

			nyMidten();

		} // else lovlige

	}//

	// **********************************************************************************
	// Hjelpemetode til � finne ny midten.
	// Mindre effektiv fordi vi m� gjennomg� ca halve listen, men greit nok,
	// ellers kan en teste p� om antall er partall er oddetall ved oppdatering
	// av midtpeker
	// *********************************************************************************
	private void nyMidten(/* DobbelNode<T> p */) {
		 int midtNR = antall / 2;
		 DobbelNode<T> p = foerste.getNeste();
		 for (int i = 1; i <= midtNR; i++) {
		 p = p.getNeste();
		 }
		midten = p;
	}//

	// ***********************************************************************************
	// *
	// *
	// ***********************************************************************************
	public boolean fins(T el) {
		boolean funnet = false;
		DobbelNode<T> p = null;
		if ((el.compareTo(foerste.getElement()) <= 0) || (el.compareTo(siste.getElement()) >= 0)) {
			// Ugyldig. Alternativt kan vi ha det som et forkrav!
			System.out.println("Ugyldig verdi. verdi > " + foerste.getElement() + "verdi < " + siste.getElement());

		} else { // Kun lovlige verdier
			if (el.compareTo(midten.getElement()) >= 0) { // Let i siste halvdel
				p = midten; // Midten defineres � tilh�re siste del
			} else { // Let i f�rste halvdel
				p = foerste.getNeste();
			}

			while (el.compareTo(p.getElement()) > 0) {
				p = p.getNeste();
			} // while

			// Test p� funnet
			if (el.compareTo(p.getElement()) == 0) {
				funnet = true;
			}
		} // else
		return funnet;
	}//

	// ***********************************************************************************
	// *
	// *
	// ***********************************************************************************

	public T fjern(T el) {
		T resultat = null;
		DobbelNode<T> p = null;
		boolean funnet = false;

		if ((el.compareTo(foerste.getElement()) <= 0) || (el.compareTo(siste.getElement()) >= 0)) {
			// Ugyldig. Alternativt kan vi ha det som et forkrav!
			System.out.println("Ugyldig verdi. verdi > " + foerste.getElement() + "verdi < " + siste.getElement());

		} else { // Kun lovlige verdier

			p = finn(el);
			if (p != null) {
				// Tar ut
				antall = antall - 1;

				p.getForrige().setNeste(p.getNeste());
				p.getNeste().setForrige(p.getForrige());
				// Oppadtere midten
				nyMidten();

				resultat = p.getElement();

			} // funnet

		} // lovlige
		return resultat;
	}//

	/* Alternativ kan fjern-metoden bruke finn-metoden */

	private DobbelNode<T> finn(T el) {
		DobbelNode<T> node = null;
		DobbelNode<T> p = null;

		// Kun lovlige verdier
		if (el.compareTo(midten.getElement()) >= 0) { // Let i siste halvdel
			p = midten; // Midten defineres � tilh�re siste del
		} else { // Let i f�rste halvdel
			p = foerste.getNeste();
		}
		while (el.compareTo(p.getElement()) > 0) {
			p = p.getNeste();
		} // while

		// Test p� funnet
		if (el.compareTo(p.getElement()) == 0) {
			node = p;
		}
		return node;
	}

	// ***********************************************************************************
	// *
	// *
	// ***********************************************************************************

	public void skrivListe() {
		DobbelNode<T> p = foerste;

		while (p != null) {
			System.out.print(p.getElement() + " ");
			p = p.getNeste();
		}

		System.out.println(
				"Foerste:" + foerste.getElement() + " Midten:" + midten.getElement() + " Siste:" + siste.getElement());
		System.out.println();

	}//

}// class
