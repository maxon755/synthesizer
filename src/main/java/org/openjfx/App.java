package org.openjfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.openjfx.synthesizer.Synthesizer;

import javax.sound.midi.SoundbankResource;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        Synthesizer synthesizer = new Synthesizer();

        Pane pianoKeyboard = PianoKeyboardFactory.createPianoKeyboard();

        var scene = new Scene(new VBox(
                createInstrumentSelector(synthesizer),
                pianoKeyboard
        ));

        stage.setScene(scene);

        ArrayList<KeyCode> pressedKeys = new ArrayList<>();

        scene.setOnKeyPressed(keyEvent -> {
            if (pressedKeys.contains(keyEvent.getCode())) {
                return;
            }

            pressedKeys.add(keyEvent.getCode());

            pianoKeyboard.getChildren()
                    .stream()
                    .map(pianoKey -> (PianoKeyboardFactory.PianoKey) pianoKey)
                    .filter(pianoKey -> pianoKey.getKeyCode().equals(keyEvent.getCode()))
                    .forEach(pianoKey -> {
                        pianoKey.drawKeyPressed();
                        synthesizer.playNote(pianoKey.getNote());
                    });
        });

        scene.setOnKeyReleased(keyEvent -> {
            pressedKeys.remove(keyEvent.getCode());

            pianoKeyboard.getChildren()
                    .stream()
                    .map(pianoKey -> (PianoKeyboardFactory.PianoKey) pianoKey)
                    .filter(pianoKey -> pianoKey.getKeyCode().equals(keyEvent.getCode()))
                    .forEach(pianoKey -> {
                        synthesizer.stopNote(pianoKey.getNote());
                        pianoKey.drawKeyReleased();
                    });
        });

        stage.setResizable(false);
        stage.show();
    }

    ComboBox<String> createInstrumentSelector(Synthesizer synthesizer) {
        String[] instrumentNames = Arrays.stream(synthesizer.getInstruments()).map(SoundbankResource::getName).toArray(String[]::new);

        ObservableList<String> options = FXCollections.observableArrayList(Arrays.asList(instrumentNames));
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setValue(synthesizer.getCurrentInstrument().getName());

        comboBox.setOnAction(actionEvent -> {
            int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();

            synthesizer.setCurrentInstrument(synthesizer.getInstruments()[selectedIndex]);
        });

        return comboBox;
    }

    public static void main(String[] args) {
        launch();
    }
}