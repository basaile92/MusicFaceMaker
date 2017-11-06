package com.ircica.music;

/**
 * Created by basaile92 on 06/11/2017.
 */

public class Main {


    public static void main(String [] args){

        Notes notes = new Notes();

        System.out.println(notes.getFrequency(1, NomNotes.DO));
        System.out.println(notes.getFrequency(3, NomNotes.FA));
        System.out.println(notes.getFrequency(2, NomNotes.SIb));
        System.out.println("Salut");

    }

}
