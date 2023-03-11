package org.openjfx.synthesizer;

public enum Note {
    C("C", 60),
    Cd("C#", 61),
    D("D", 62),
    Eb("Eb", 63),
    E("E", 64),
    F("F", 65),
    Fd("F#", 66),
    G("G", 67),
    Gd("G#", 68),
    A("A", 69),
    B("B",70),
    H("H", 71),
    C2("C2", 72),
    Cd2("CD2", 73),
    D2("D2", 74),
    Eb2("Eb2", 75),
    E2("E2", 76);

    final String name;

    final int synthCode;

    Note(String name, int synthCode) {
        this.name = name;
        this.synthCode = synthCode;
    }

    public String getName() {
        return name;
    }
}
