package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Locale;
import java.util.Scanner;

public class Symulacja {
    private String plikTekstowy = "tekst.txt";
    private CzefoCzerwonoCzarne czefoCzerwonoCzarne;
    private final char[] tablicaZnakow = {'\'', '\t', '\n', '\f', '"', '/', ',', ';', ':', '(', ')', '{', '}', '[', ']',
            '*', '&', '^', '%', '$', '#', '@', '!', '?', '.', '`', '~', '+', '-', '_', '=', '\\', ' ', '‘', '…', '|'};
    private String calyTekst;

    public Symulacja(){
        czefoCzerwonoCzarne = new CzefoCzerwonoCzarne();
        calyTekst = "";
        stworzCzefo();
        wyswietlSymulacje();

    }

    private void wyswietlSymulacje(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" + calyTekst + "\n");

        System.out.println("Skorowidz: ");

        System.out.println("------------------------------------------------");
        czefoCzerwonoCzarne.inorder();
        System.out.println("------------------------------------------------");
        czefoCzerwonoCzarne.przeszukiwanieWWszerz();
        System.out.println("\nPodaj wezel ktory chcesz usunac: ");
        czefoCzerwonoCzarne.usunWezel(scanner.nextLine());
        czefoCzerwonoCzarne.przeszukiwanieWWszerz();
        System.out.println("------------------------------------------------");
    }

    private void stworzCzefo(){
        wczytaj();
    }

    private void wczytaj(){
        int i = 0;
        try {
            BufferedReader odczyt = new BufferedReader(new FileReader(plikTekstowy));
            String s = "";
            while (true){
                s = odczyt.readLine();
                if (s == null){
                    break;
                }
                i++;
                dodajDoDrzewo(s.toLowerCase(Locale.ROOT), i);
                calyTekst += s + "\n";
                s = "";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void dodajDoDrzewo(String s, int k){
        String[] linia = s.split(" ");
        String doDodania;
        for (int i = 0; i < linia.length; i++){
            doDodania = usunZnaki(linia[i]);
            if (!doDodania.equals("")){
                czefoCzerwonoCzarne.dodaj(doDodania, k);
            }
        }
    }

    private String usunZnaki(String s){
        String wynik = "";
        char es;
        boolean czyDodac = true;

        for (int j = 0; j < s.length(); j++) {
            es = s.charAt(j);
            for (int i = 0; i < tablicaZnakow.length; i++) {
                if (es == tablicaZnakow[i]){
                    czyDodac = false;
                }
            }
            if (czyDodac){
                wynik += es;
            }
        }

        return wynik;
    }
}
