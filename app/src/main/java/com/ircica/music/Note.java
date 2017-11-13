package com.ircica.music;

import java.util.HashMap;

public class Note {

    private HauteurNote hauteurNote;
    private int octave;
    private float frequence;

    public Note(HauteurNote hauteurNote, int octave){

        this.hauteurNote = hauteurNote;
        this.octave = octave;
        this.frequence = calculateFrequency(this.hauteurNote.getFrequence(), this.octave);
    }

    public float getFrequence(){

        return this.frequence;
    }

    private float calculateFrequency(float frequence, int octave) {

        return frequence * (float) Math.pow((double) frequence, (double) octave);
    }


}
