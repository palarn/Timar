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

    public int inDay_outDay(){
        int money = (int)(dagvinnukaup * hours);
        return money;
    }

    public int inNight_outDay(){
        double yfirvinnutimi = 8-in;
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }

    public int inNight_outNight(){
        int money = (int)(yfirvinnukaup * hours);
        return money;
    }

    public int inNight_outEvening(){
        double yfirvinnutimi = (8-in) + (out - yfirvinnutala);
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }

    public int inNight_outNightAfter(){
        double yfirvinnutimi = (8-in) + (24-yfirvinnutala) + out;
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }

    public int inEvening_outEvening(){
        int money = (int)(yfirvinnukaup * hours);
        return money;
    }

    public int inEvening_outDay(){
        double yfirvinnutimi = (24-in) + 8;
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }

    public int inEvening_outEveningAfter(){
        double yfirvinnutimi = (24-in) + 8 + (out-yfirvinnutala);
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }

    public int inDay_outEvening(){
        double yfirvinnutimi = out - yfirvinnutala;
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }

    public int inDay_outNight(){
        double yfirvinnutimi = out + (24-yfirvinnutala);
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }

    public int inDay_outDayAfter(){
        double yfirvinnutimi = (out-8) + 8 + (24-yfirvinnutala);
        double yfirvinnulaun = yfirvinnutimi*yfirvinnukaup;
        double dagvinnulaun = dagvinnukaup*(hours-yfirvinnutimi);
        int money = (int)(yfirvinnulaun+dagvinnulaun);
        return money;
    }
}
