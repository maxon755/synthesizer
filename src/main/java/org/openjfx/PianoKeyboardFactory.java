package org.openjfx;

import javafx.animation.FillTransition;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.openjfx.synthesizer.Note;

public class PianoKeyboardFactory {
    public static Pane createPianoKeyboard() {
        AnchorPane pianoKeyboard = new AnchorPane();

        addKey(pianoKeyboard, new WhiteKey(Note.C, KeyCode.A), 0);
        addKey(pianoKeyboard, new WhiteKey(Note.D, KeyCode.S), 100);
        addKey(pianoKeyboard, new WhiteKey(Note.E, KeyCode.D), 200);
        addKey(pianoKeyboard, new WhiteKey(Note.F, KeyCode.F), 300);
        addKey(pianoKeyboard, new WhiteKey(Note.G, KeyCode.G), 400);
        addKey(pianoKeyboard, new WhiteKey(Note.A, KeyCode.H), 500);
        addKey(pianoKeyboard, new WhiteKey(Note.H, KeyCode.J), 600);
        addKey(pianoKeyboard, new WhiteKey(Note.C2, KeyCode.K), 700);
        addKey(pianoKeyboard, new WhiteKey(Note.D2, KeyCode.L), 800);
        addKey(pianoKeyboard, new WhiteKey(Note.E2, KeyCode.SEMICOLON), 900);

        addKey(pianoKeyboard, new BlackKey(Note.Cd, KeyCode.W), 75);
        addKey(pianoKeyboard, new BlackKey(Note.Eb, KeyCode.E), 175);
        addKey(pianoKeyboard, new BlackKey(Note.Fd, KeyCode.T), 375);
        addKey(pianoKeyboard, new BlackKey(Note.Gd, KeyCode.Y), 475);
        addKey(pianoKeyboard, new BlackKey(Note.B, KeyCode.U), 575);
        addKey(pianoKeyboard, new BlackKey(Note.Cd2, KeyCode.O), 775);
        addKey(pianoKeyboard, new BlackKey(Note.Eb2, KeyCode.P), 875);


        return pianoKeyboard;
    }

    static void addKey(AnchorPane pianoKeyboard, PianoKey key, double leftOffset) {
        AnchorPane.setLeftAnchor(key, leftOffset);

        pianoKeyboard.getChildren().add(key);
    }

    public abstract static class PianoKey extends StackPane {

        protected Note note;
        protected KeyCode keyCode;

        PianoKey(Note note, KeyCode keyCode) {
            this.note = note;
            this.keyCode = keyCode;

            createShape();
        }

        abstract void createShape();

        public abstract void drawKeyPressed();

        public abstract void drawKeyReleased();

        public Note getNote() {
            return note;
        }

        public KeyCode getKeyCode() {
            return keyCode;
        }
    }

    private static class WhiteKey extends PianoKey {

        Rectangle rectangle;


        WhiteKey(Note note, KeyCode keyCode) {
            super(note, keyCode);
        }

        @Override
        void createShape() {

            rectangle = new Rectangle(100, 350, Color.WHITE);

            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(2.5);

            getChildren().addAll(rectangle, new Text(keyCode.name()));
        }

        @Override
        public void drawKeyPressed() {
            FillTransition transition = new FillTransition(
                    Duration.seconds(0.15),
                    rectangle,
                    Color.WHITE,
                    Color.BISQUE
            );
            transition.play();
        }

        @Override
        public void drawKeyReleased() {
            FillTransition transition = new FillTransition(
                    Duration.seconds(0.15),
                    rectangle,
                    Color.BISQUE,
                    Color.WHITE
            );
            transition.play();
        }
    }

    private static class BlackKey extends PianoKey {
        Rectangle rectangle;

        BlackKey(Note note, KeyCode keyCode) {
            super(note, keyCode);
        }

        @Override
        void createShape() {
            rectangle = new Rectangle(50, 225, Color.BLACK);

            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(2.5);

            Text name = new Text();
            name.setText(keyCode.name());
            name.setFill(Color.WHITE);

            getChildren().addAll(rectangle, name);
        }

        @Override
        public void drawKeyPressed() {
            FillTransition transition = new FillTransition(
                    Duration.seconds(0.15),
                    rectangle,
                    Color.BLACK,
                    Color.GRAY
            );
            transition.play();
        }

        @Override
        public void drawKeyReleased() {
            FillTransition transition = new FillTransition(
                    Duration.seconds(0.15),
                    rectangle,
                    Color.GRAY,
                    Color.BLACK
            );
            transition.play();
        }
    }
}
