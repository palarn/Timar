package com.example.freyds.timar3;

import junit.framework.TestCase;

public class LaunTest extends TestCase {

    public void testDagvinna() throws Exception {
        Laun laun = new Laun(9.0, 15.0, 17.0, 2000, 3000, 6);
        int result = laun.dagvinna();
        assertEquals(12000, result);
    }

    public void testInnFyrirUt() throws Exception {
        Laun laun = new Laun(6.0, 15.0, 17.0, 2000, 3000, 9);
        int result = laun.innFyrirUt();
        assertEquals(20000, result);
    }

    public void testInnFyrirUtFyrir() throws Exception {
        Laun laun = new Laun(2.0, 6.0, 17.0, 2000, 3000, 4);
        int result = laun.innFyrirUtFyrir();
        assertEquals(12000, result);
    }

    public void testInnFyrirUtEftir() throws Exception {
        Laun laun = new Laun(6.0, 19.0, 17.0, 2000, 3000, 13);
        int result = laun.innFyrirUtEftir();
        assertEquals(30000, result);
    }

    public void testInnFyrirUtEftirEftir() throws Exception {
        Laun laun = new Laun(6.0, 2.0, 17.0, 2000, 3000, 20);
        int result = laun.innFyrirUtEftirEftir();
        assertEquals(51000, result);
    }

    public void testInnEftirUtEftir() throws Exception {
        Laun laun = new Laun(19.0, 2.0, 17.0, 2000, 3000, 7);
        int result = laun.innEftirUtEftir();
        assertEquals(21000, result);
    }

    public void testInnEftirUt() throws Exception {
        Laun laun = new Laun(19.0, 10.0, 17.0, 2000, 3000, 15);
        int result = laun.innEftirUt();
        assertEquals(43000, result);
    }

    public void testInnEftirUtEftirEftir() throws Exception {
        Laun laun = new Laun(19.0, 18.00, 17.0, 2000, 3000, 23);
        int result = laun.innEftirUtEftirEftir();
        assertEquals(60000, result);
    }

    public void testInnUtEftir() throws Exception {
        Laun laun = new Laun(9.0, 19.0, 17.0, 2000, 3000, 10);
        int result = laun.innUtEftir();
        assertEquals(22000, result);
    }

    public void testInnUtEftirFyrir() throws Exception {
        Laun laun = new Laun(9.0, 1.0, 17.0, 2000, 3000, 16);
        int result = laun.innUtEftirFyrir();
        assertEquals(40000, result);
    }

    public void testInnUtEftirEftir() throws Exception {
        Laun laun = new Laun(9.0, 11.0, 17.0, 2000, 3000, 26);
        int result = laun.innUtEftirEftir();
        assertEquals(70000, result);
    }
}