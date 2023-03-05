package org.openjfx.synthesizer;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

public class Synthesizer {

    private javax.sound.midi.Synthesizer synthesizer;

    private MidiChannel midiChannel;

    private Instrument currentInstrument;

    public Synthesizer() {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();

            Instrument[] instruments = synthesizer.getLoadedInstruments();
            midiChannel = synthesizer.getChannels()[0];
            setCurrentInstrument(instruments[0]);
        } catch (MidiUnavailableException e) {
            System.out.println("Cannot get synthesizer");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void playNote(Note note) {
        midiChannel.noteOn(note.synthCode, 80);
    }

    public Instrument[] getInstruments() {
        return synthesizer.getLoadedInstruments();
    }

    public Instrument getCurrentInstrument() {
        return currentInstrument;
    }

    public void setCurrentInstrument(Instrument instrument) {
        midiChannel.programChange(instrument.getPatch().getProgram());
        currentInstrument = instrument;
    }
}
