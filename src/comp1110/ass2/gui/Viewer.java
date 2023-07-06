package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField playerTextField;
    private TextField boardTextField;


    /**
     * Show the state of a (single player's) board in the window.
     *
     * @param board_state The string representation of the board state.
     */
    void displayState(String board_state) {
        String[] board_elements = board_state.split(",");
        System.out.println();
        Image map = new Image("file:assets/island-one-with-numbering_1.png");
        ImageView maps = new ImageView(map);
        maps.setFitHeight(600);
        maps.setFitWidth(600);
        maps.setLayoutX(300);
        maps.setLayoutY(75);
        root.getChildren().add(maps);
        int i = 0;
        int length = board_elements.length;
        while (i < length) {
            showBlockState(board_elements[i]);
            i++;

        }

        // FIXME Task 5: implement the state viewer

    }

    /**
     * Light up the corresponding block if the block is built or used.
     *
     * @param board_element The string represent the asked block.
     */
    void showBlockState(String board_element) {
        char[] board_elements = board_element.toCharArray();
        char type = board_elements[0];
        char num = board_elements[1];
        if (type == 'J' || type == 'K') {
            switch (num) {
                case '1' -> blockPlace(board_element, 398, 172, 0);
                case '2' -> blockPlace(board_element, 399, 370, 0);
                case '3' -> blockPlace(board_element, 570, 470, 0);
                case '4' -> blockPlace(board_element, 741, 370, 0);
                case '5' -> blockPlace(board_element, 741, 173, 0);
                case '6' -> blockPlace(board_element, 570, 68, 0);
            }
        } else if (type == 'R') {
            switch (board_element) {
                case "R0" -> blockPlace("R", 452, 309, 120);
                case "R1" -> blockPlace("R", 375, 351, 0);
                case "R2" -> blockPlace("R", 452, 396, 60);
                case "R3" -> blockPlace("R", 454, 511, 120);
                case "R4" -> blockPlace("R", 375, 552, 0);
                case "R5" -> blockPlace("R", 454, 592, 60);
                case "R6" -> blockPlace("R", 552, 645, 0);
                case "R7" -> blockPlace("R", 630, 605, 120);
                case "R8" -> blockPlace("R", 630, 491, 60);
                case "R9" -> blockPlace("R", 626, 405, 120);
                case "R10" -> blockPlace("R", 628, 290, 60);
                case "R11" -> blockPlace("R", 630, 202, 120);
                case "R12" -> blockPlace("R", 726, 547, 0);
                case "R13" -> blockPlace("R", 795, 506, 120);
                case "R14" -> blockPlace("R", 799, 391, 60);
                case "R15" -> blockPlace("R", 798, 304, 120);
            }
        } else {
            switch (board_element) {
                case "C7" -> blockPlace(board_element, 318, 325, 0);
                case "C12" -> blockPlace(board_element, 318, 525, 0);
                case "C20" -> blockPlace(board_element, 825, 425, 0);
                case "C30" -> blockPlace(board_element, 835, 225, 0);
                case "S3" -> blockPlace(board_element, 503, 225, 0);
                case "S4" -> blockPlace(board_element, 504, 432, 0);
                case "S5" -> blockPlace(board_element, 512, 626, 0);
                case "S7" -> blockPlace(board_element, 685, 529, 0);
                case "S9" -> blockPlace(board_element, 678, 329, 0);
                case "S11" -> blockPlace(board_element, 681, 126, 0);
            }

        }

    }

    /**
     * Put the corresponding figure onto the asked position.
     * @param board_element The element that is supposed to be shown
     * @param x the x dimension of figure
     * @param y the y dimension of figure
     * @param degree the degree of figure
     */
    void blockPlace(String board_element, int x, int y, int degree) {
        Image element = new Image("file:assets/" + board_element + ".png");
        ImageView elements = new ImageView(element);
        double scale = 2.0 / 3.0;
        elements.setScaleX(scale);
        elements.setScaleY(scale);
        elements.setLayoutX(x);
        elements.setLayoutY(y);
        elements.setRotate(degree);
        root.getChildren().add(elements);
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Board State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(500);
        Button button = new Button("Show");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel, boardTextField, button);
        hb.setSpacing(10);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Board State Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(controls);
        makeControls();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
