package com.ircica.music;

import java.util.HashMap;
import java.util.Map;

public class Notes{

    private static HashMap<NomNotes, Float> notes = new HashMap<NomNotes, Float>();
    private NomNotes nom;


    private void initNotes(){

        notes.put(NomNotes.DO, 32.7f);
        notes.put(NomNotes.DOd, 34.65f);
        notes.put(NomNotes.REb, 34.65f);
        notes.put(NomNotes.RE, 36.71f);
        notes.put(NomNotes.REd, 38.89f);
        notes.put(NomNotes.MIb, 38.89f);
        notes.put(NomNotes.MI, 41.20f);
        notes.put(NomNotes.FA, 43.65f);
        notes.put(NomNotes.FAd, 46.25f);
        notes.put(NomNotes.SOLb, 46.25f);
        notes.put(NomNotes.SOL, 49f);
        notes.put(NomNotes.SOLd, 51.91f);
        notes.put(NomNotes.LAb, 51.91f);
        notes.put(NomNotes.LA, 55f);
        notes.put(NomNotes.LAd, 58.27f);
        notes.put(NomNotes.SIb, 58.27f);
        notes.put(NomNotes.SI, 61.74f);

    }

    public Notes(){
        initNotes();
    }

    public Float getFrequency(int octave, NomNotes nomNotes){


            return notes.get(nomNotes) * pow(2, (octave));


    }

    private float pow(float i, int n){

        if(n == 0){
            return 1;
        }else
            return i * pow(i , n-1);
    }




}
