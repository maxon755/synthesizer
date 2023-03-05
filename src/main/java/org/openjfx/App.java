package org.openjfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.openjfx.synthesizer.Synthesizer;

/**
 * JavaFX App
 */
public class App extends Application {


    @Override
    public void start(Stage stage) {

        Synthesizer synthesizer = new Synthesizer();

        Pane pianoKeyboard = PianoKeyboardFactory.createPianoKeyboard();
        pianoKeyboard.setFocusTraversable(true);

        var scene = new Scene(pianoKeyboard);
        stage.setScene(scene);

        scene.setOnKeyPressed(keyEvent -> {
            pianoKeyboard.getChildren()
                    .stream()
                    .map(pianoKey -> (PianoKeyboardFactory.PianoKey) pianoKey)
                    .filter(pianoKey -> pianoKey.getKeyCode().equals(keyEvent.getCode()))
                    .forEach(pianoKey -> {
                        pianoKey.configureTransition();
                        synthesizer.playNote(pianoKey.getNote());
                    });
        });

        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}