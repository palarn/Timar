package com.example.freyds.timar3;

import android.util.Log;

/**
 * Klasi sem reiknar heildarlaun utfra skradum timum og skradum launum
 * @author Anna Hafthorsdottir
 * @date 25. november 2014
 */

public class Laun {
    double in;
    double out;
    double yfirvinnutala;
    int dagvinnukaup;
    int yfirvinnukaup;
    double hours;

    public Laun(double in, double out, double yfirvinnutala, int dagvinnukaup, int yfirvinnukaup, double hours){
        this.in = in;
        this.out = out;
        this.yfirvinnutala = yfirvinnutala;
        this.dagvinnukaup = dagvinnukaup;
        this.yfirvinnukaup = yfirvinnukaup;
        this.hours = hours;
    }

    public int dagvinna(){
        int money = (int)(dagvinnukaup * hours);
        return money;
    }

    public int innFyrirUt(){
        double yfirvinnutimi = 8-in;
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }

    public int innFyrirUtFyrir(){
        int money = (int)(yfirvinnukaup * hours);
        return money;
    }

    public int innFyrirUtEftir(){
        double yfirvinnutimi = (8-in) + (out - yfirvinnutala);
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }

    public int innFyrirUtEftirEftir(){
        double yfirvinnutimi = (8-in) + (24-yfirvinnutala) + out;
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }

    public int innEftirUtEftir(){
        int money = (int)(yfirvinnukaup * hours);
        return money;
    }

    public int innEftirUt(){
        double yfirvinnutimi = (24-in) + 8;
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }

    public int innEftirUtEftirEftir(){
        double yfirvinnutimi = (24-in) + 8 + (out-yfirvinnutala);
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }

    public int innUtEftir(){
        double yfirvinnutimi = out - yfirvinnutala;
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }

    public int innUtEftirFyrir(){
        double yfirvinnutimi = out + (24-yfirvinnutala);
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }

    public int innUtEftirEftir(){
        double yfirvinnutimi = (out-8) + 8 + (24-yfirvinnutala);
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }
}
