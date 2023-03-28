package com.company;

import java.util.ArrayList;
import java.util.LinkedList;

public class CzefoCzerwonoCzarne {
    public class Wezel {
        String slowo;
        Wezel prawy;
        Wezel lewy;
        Wezel rodzic;
        int kolor;
        ArrayList<Integer> numeryWierszy;

        public Wezel(){
            numeryWierszy = new ArrayList<>();
        }
    }

    private Wezel korzen;
    private Wezel TNULL;
    private final String polskieZnaki = "a ą b c ć d e ę f g h i j k l ł m n ń o ó p q r s ś t u v w x y z ź ż";

    private void preOrderPomoc(Wezel wezel){
        if (wezel != TNULL){
            System.out.println(wezel.slowo + " ");
            preOrderPomoc(wezel.lewy);
            preOrderPomoc(wezel.prawy);
        }
    }

    private void inOrderPomoc(Wezel wezel){
        if (wezel != TNULL){
            inOrderPomoc(wezel.prawy);
//            System.out.println(wezel.slowo);
            System.out.printf("%-29s %-2s %-20s\n", wezel.slowo, "-", wypiszLinijki(wezel.numeryWierszy));
            inOrderPomoc(wezel.lewy);
        }
    }

    private String wypiszLinijki(ArrayList<Integer> lista){
        String wynik = "";
        for (int i = 0; i < lista.size() - 1; i++) {
            wynik += lista.get(i) + ", ";
        }
        wynik += lista.get(lista.size() - 1);
        return wynik;
    }

    private void postOrderPomoc(Wezel wezel){
        if (wezel != TNULL){
            postOrderPomoc(wezel.lewy);
            postOrderPomoc(wezel.prawy);
            System.out.println(wezel.slowo + " ");
        }
    }

    private void przeszukiwanieWWszerzPomoc(Wezel wezel) {
        LinkedList<Wezel> queue = new LinkedList<>();
        queue.addFirst(wezel);
        while (!queue.isEmpty()) {
            Wezel w = queue.pop();
            System.out.printf("%-29s %-2s %-20s\n", w.slowo, "-", wypiszLinijki(w.numeryWierszy));
            if (w.lewy != TNULL)
                queue.add(w.lewy);
            if (w.prawy != TNULL)
                queue.add(w.prawy);
        }
    }

    private Wezel przeszukiwanieCzefaPomoc(Wezel wezel, String haslo){
        if (wezel.equals(TNULL) || haslo.equals(wezel.slowo)){
            return wezel;
        }
        //todo wtf
        if (0 < compare(haslo, wezel.slowo)){
            return przeszukiwanieCzefaPomoc(wezel.lewy, haslo);
        }
        return przeszukiwanieCzefaPomoc(wezel.prawy, haslo);
    }

    private void naprawPoUsunieciu(Wezel wezel){
        Wezel xd;

        while (!wezel.equals(korzen) && wezel.kolor == 0){
            if (wezel.equals(wezel.rodzic.lewy)){
                xd = wezel.rodzic.prawy;
                if (xd.kolor == 1){
                    xd.kolor = 0;
                    wezel.rodzic.kolor = 1;
                    lewaRotacja(wezel.rodzic);
                    xd = wezel.rodzic.prawy;
                }

                if (xd.lewy.kolor == 0 && xd.prawy.kolor == 0){
                    xd.kolor = 1;
                    wezel = wezel.rodzic;
                } else {
                    if (xd.prawy.kolor == 0){
                        xd.lewy.kolor = 0;
                        xd.kolor = 1;
                        prawaRotacja(xd);
                        xd = wezel.rodzic.prawy;
                    }

                    xd.kolor = wezel.rodzic.kolor;
                    wezel.rodzic.kolor = 0;
                    xd.prawy.kolor = 0;
                    lewaRotacja(wezel.rodzic);
                    wezel = korzen;
                }
            } else {
                xd = wezel.rodzic.lewy;
                if (xd.kolor == 1){
                    xd.kolor = 0;
                    wezel.rodzic.kolor = 1;
                    prawaRotacja(wezel.rodzic);
                    xd = wezel.rodzic.lewy;
                }

                if (xd.prawy.kolor == 0 && xd.rodzic.kolor == 0){
                    xd.kolor = 1;
                    wezel = wezel.rodzic;
                } else {
                    if (xd.lewy.kolor == 0){
                        xd.prawy.kolor = 0;
                        xd.kolor = 1;
                        lewaRotacja(xd);
                        xd = wezel.rodzic.lewy;
                    }

                    xd.kolor = wezel.rodzic.kolor;
                    wezel.rodzic.kolor = 0;
                    xd.lewy.kolor = 0;
                    prawaRotacja(wezel.rodzic);
                    wezel = korzen;
                }
            }
        }
        wezel.kolor = 0;
    }

    private void zamienWezly(Wezel u, Wezel v){
        if (u.rodzic == null){
            korzen = v;
        } else if (u.equals(u.rodzic.lewy)){
            u.rodzic.lewy = v;
        } else {
            u.rodzic.prawy = v;
        }
        v.rodzic = u.rodzic;
    }

    private void usunWezelPomoc(Wezel wezel, String haslo){
        Wezel z = TNULL;
        Wezel x, y;

        while (wezel != TNULL){
//            System.out.println(wezel.slowo.compareTo(haslo));
            if (wezel.slowo.compareTo(haslo) == 0){
                System.out.println("Wchodzi");
                z = wezel;
            }

            if (wezel.slowo.compareTo(haslo) >= 0){
                wezel = wezel.prawy;
            } else {
                wezel = wezel.lewy;
            }
        }

        if (z == TNULL){
            System.out.println("Nie ma takiego słowa!!! HAHAHAHA \n");
            return;
        }

        y = z;
        int yStokowyKolor = y.kolor;

        if (z.lewy == TNULL){
            x = z.prawy;
            zamienWezly(z, z.prawy);
        } else if (z.prawy == TNULL){
            x = z.lewy;
            zamienWezly(z, z.lewy);
        } else {
            y = minimum(z.prawy);
            yStokowyKolor = y.kolor;
            x = y.prawy;
            if (y.rodzic == z){
                x.rodzic = y;
            } else {
                zamienWezly(y, y.prawy);
                y.prawy = z.prawy;
                y.prawy.rodzic = y;
            }

            zamienWezly(z, y);
            y.lewy = z.lewy;
            y.lewy.rodzic = y;
            y.kolor = z.kolor;
        }
        if (yStokowyKolor == 0){
            naprawPoUsunieciu(x);
        }
    }

    private void naprawPoWstawieniu(Wezel k){
        Wezel u;

        while (k.rodzic.kolor == 1){
            if (k.rodzic == k.rodzic.rodzic.prawy){
                u = k.rodzic.rodzic.lewy;
                if (u.kolor == 1){
                    u.kolor = 0;
                    k.rodzic.kolor = 0;
                    k.rodzic.rodzic.kolor = 1;
                    k = k.rodzic.rodzic;
                } else {
                    if (k == k.rodzic.lewy){
                        k = k.rodzic;
                        prawaRotacja(k);
                    }
                    k.rodzic.kolor = 0;
                    k.rodzic.rodzic.kolor = 1;
                    lewaRotacja(k.rodzic.rodzic);
                }
            } else {
                u = k.rodzic.rodzic.prawy;

                if (u.kolor == 1){
                    u.kolor = 0;
                    k.rodzic.kolor = 0;
                    k.rodzic.rodzic.kolor = 1;
                    k = k.rodzic.rodzic;
                } else {
                    if (k == k.rodzic.prawy){
                        k = k.rodzic;
                        lewaRotacja(k);
                    }
                    k.rodzic.kolor = 0;
                    k.rodzic.rodzic.kolor = 1;
                    prawaRotacja(k.rodzic.rodzic);
                }
            }
            if (k == korzen){
                break;
            }
        }
        korzen.kolor = 0;
    }

    public CzefoCzerwonoCzarne(){
        TNULL = new Wezel();
        TNULL.kolor = 0;
        TNULL.lewy = null;
        TNULL.prawy = null;
        korzen = TNULL;
    }

    public void dodaj(String slowo, int linia){
        Wezel wezel = new Wezel();
        Wezel wezelek = przeszukajCzefo(slowo);

        if (wezelek.slowo == null) {
            wezel.rodzic = null;
            wezel.slowo = slowo;
            dodajDoListyWezla(wezel.numeryWierszy, linia);
            wezel.lewy = TNULL;
            wezel.prawy = TNULL;
            wezel.kolor = 1;


            Wezel y = null;
            Wezel x = this.korzen;

            while (x != TNULL) {
                y = x;
                if (0 < compare(wezel.slowo, x.slowo)) {
                    x = x.lewy;
                } else {
                    x = x.prawy;
                }
            }

            wezel.rodzic = y;
            if (y == null) {
                korzen = wezel;
            } else if (0 < compare(wezel.slowo, y.slowo)) {
                y.lewy = wezel;
            } else {
                y.prawy = wezel;
            }

            if (wezel.rodzic == null) {
                wezel.kolor = 0;
                return;
            }
            if (wezel.rodzic.rodzic == null) {
                return;
            }

            naprawPoWstawieniu(wezel);
        } else {
            dodajDoListyWezla(wezelek.numeryWierszy, linia);
        }
    }

    private void dodajDoListyWezla(ArrayList<Integer> lista, int linia){
        boolean czyDodac = true;

        for (int i = 0; i < lista.size(); i++){
            if (lista.get(i) == linia){
                czyDodac = false;
            }
        }

        if (czyDodac) lista.add(linia);
    }

    public void preorder(){
        preOrderPomoc(this.korzen);
    }

    public void inorder(){
        inOrderPomoc(this.korzen);
    }

    public void postorder(){
        postOrderPomoc(this.korzen);
    }

    public void przeszukiwanieWWszerz(){
        przeszukiwanieWWszerzPomoc(this.korzen);
    }

    public Wezel przeszukajCzefo(String s){
        return przeszukiwanieCzefaPomoc(this.korzen, s);
    }

    public Wezel minimum(Wezel wezel){
        while (wezel.lewy != TNULL){
            wezel = wezel.lewy;
        }
        return wezel;
    }

    public Wezel maksimum(Wezel wezel){
        while (wezel.prawy != TNULL){
            wezel = wezel.prawy;
        }
        return wezel;
    }

    public Wezel successor(Wezel x){
        if (x.prawy != TNULL){
            return minimum(x.prawy);
        }
        Wezel y = x.rodzic;
        while (y != TNULL && x == y.prawy){
            x = y;
            y = y.rodzic;
        }
        return y;
    }

    public Wezel predecessor(Wezel x){
        if (x.lewy != TNULL){
            return maksimum(x.lewy);
        }

        Wezel y = x.rodzic;
        while (y != TNULL && x == y.lewy){
            x = y;
            y = y.rodzic;
        }

        return  y;
    }

    public void lewaRotacja(Wezel wezel){
        Wezel y = wezel.prawy;
        wezel.prawy = y.lewy;
        if (y.lewy != TNULL){
            y.lewy.rodzic = wezel;
        }

        y.rodzic = wezel.rodzic;
        if (wezel.rodzic == null){
            this.korzen = y;
        } else if (wezel == wezel.rodzic.lewy){
            wezel.rodzic.lewy = y;
        } else {
            wezel.rodzic.prawy = y;
        }

        y.lewy = wezel;
        wezel.rodzic = y;
    }

    public void prawaRotacja(Wezel x){
        Wezel y = x.lewy;
        x.lewy = y.prawy;

        if (y.prawy != TNULL){
            y.prawy.rodzic = x;
        }

        y.rodzic = x.rodzic;

        if (x.rodzic == null){
            this.korzen = y;
        } else if (x == x.rodzic.prawy){
            x.rodzic.prawy = y;
        } else {
            x.rodzic.lewy = y;
        }

        y.prawy = x;
        x.rodzic = y;
    }

    public void insert(String slowo){
        Wezel wezel = new Wezel();
        wezel.rodzic = null;
        wezel.slowo = slowo;
        wezel.lewy = TNULL;
        wezel.prawy = TNULL;
        wezel.kolor = 1;

        Wezel y = null;
        Wezel x = this.korzen;

        while (x != TNULL){
            y = x;
            if (0 < wezel.slowo.compareTo(x.slowo)){
                x = x.lewy;
            } else {
                x = x.prawy;
            }
        }

        wezel.rodzic = y;
        if (y == null){
            korzen = wezel;
        } else if (0 < wezel.slowo.compareTo(y.slowo)){
            y.lewy = wezel;
        } else {
            y.prawy = wezel;
        }

        if (wezel.rodzic == null){
            wezel.kolor = 0;
            return;
        }
        if (wezel.rodzic.rodzic == null){
            return;
        }

        naprawPoWstawieniu(wezel);
    }

    public Wezel getKorzen(){
        return this.korzen;
    }

    public void usunWezel(String slowo){
        usunWezelPomoc(this.korzen, slowo);
    }

    public int compare(String o1, String o2) {
        int len1, len2;
        len1 = o1.length();
        len2 = o2.length();

//        if(o1 != null) len1 = o1.length();
//        else len1 = 0;
//
//        if (o2 != null) len2 = o2.length();
//        else len2 = 0;
//
//        if (o1 == null || o2 == null) return len1 - len2;

        char[] s1, s2;
        s1 = o1.toCharArray();
        s2 = o2.toCharArray();
        int n = Math.min(s1.length, s2.length);

        for (int i = 0; i < n; i++) {
            if (s1[i] != s2[i]) {
                return polskieZnaki.indexOf(s1[i]) - polskieZnaki.indexOf(s2[i]);
            }
        }
        return len1 - len2;
    }
}
