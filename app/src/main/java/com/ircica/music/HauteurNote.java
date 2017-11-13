package com.ircica.music;

public enum HauteurNote {
    DO(32.7f), DOd(34.65f), REb(34.65f), RE(36.71f), REd(38.89f), MIb(38.89f), MI(41.20f), FA(43.65f), FAd(46.25f), SOLb(46.25f), SOL(49f), SOLd(51.91f), LAb(51.91f), LA(55f), LAd(58.27f), SIb(58.27f), SI(61.74f);;

    private float frequence;

    HauteurNote(float frequence) {

        this.frequence = frequence;
    }

    public float getFrequence() {
        return frequence;
    }
}
