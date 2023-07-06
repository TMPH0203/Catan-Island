package comp1110.ass2.gui;

import comp1110.ass2.*;
import comp1110.ass2.ConstructionPane.DraggableConstructionPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game extends Application {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    public static List<DraggableConstructionPane> draggableConstructionPaneList = new ArrayList<>();
    public static Player humanPlayer = PlayerAndMapInstance.humanPlayer;
    public static Player.AIPlayer aiPlayer = PlayerAndMapInstance.aiPlayer;
    private static Button[] resources = new Button[7];
    private static Button endStage = new Button();
    private static Rectangle[] table = new Rectangle[14];
    public static Text[] num = new Text[7];
    private final Group root = new Group();
    private static int turn = 1;
    public static int stageA = 0;
    private static Text currentStage = new Text();
    private static Text lastRoundScore = new Text(59,100, "Last Round Score: 0");
    private static Text totalScore = new Text(293, 100, "Total Score: 0");
    private static Text currentRound = new Text(59, 130, "Current Round: 1");
    private static char[] operate = new char[] {'r' , '0'};
    private static Button trade = new Button("Trade");
    private static Button reset = new Button("Reset");
    private static Button help = new Button("Help");
    private static boolean ifAi = false;
    private static Text aiTotalScore = new Text("AI Total Score: 0");
    private static Text title = new Text();
    private static Group initTurnBar = new Group();

    /**
     * The method turn is used to initialize the stage button;
     * This method would initialize the appearance and function of the button;
     * The game would switch the stage of the game or goto the next turn.
     */
    public static void turn() {
        endStage.setBackground(new Background(new BackgroundFill(Paint.valueOf("#196BE5"), new CornerRadii(1), new Insets(3))));
        endStage.setLayoutX(266);
        endStage.setLayoutY(316);
        endStage.setPrefWidth(93);
        endStage.setPrefHeight(40);
        endStage.setText("Stage End");
        endStage.setFont(Font.font("Open Sans", 14));
        endStage.setOnAction((ActionEvent e) -> {
            if (stageA == 0) {//going into build stage
                stageA = 1;
                humanPlayer.stage = stageA;
                endStage.setText("Turn End");
                String stageS;
                operate[0] = 'r';
                trade.setEffect(null);
                if (stageA == 0) {
                    stageS = "Roll Dice";
                } else {
                    stageS = "Build Construction";
                }
                currentStage.setText("Current Stage: " + stageS);
                if(ifAi) {
                    aiPlayer.aiRoll();
                }
                updateGameState();
            } else { // End the turn
                stageA = 0;
                operate = new char[]{'r', '0'};
                humanPlayer.stage = stageA;
                aiPlayer.stage = stageA;
                endStage.setText("Stage End");
                String stageS;
                if (stageA == 0) {
                    stageS = "Roll Dice";
                } else {
                    stageS = "Build Construction";
                }
                currentStage.setText("Current Stage: " + stageS);
                for (var each : draggableConstructionPaneList) {
                    each.setX(each.originX);
                    each.setY(each.originY);
                    each.setLayoutX(each.originX);
                    each.setLayoutY(each.originY);
                    each.setUsed(false);
                    each.paneRotate(new Rotate(0, 0, 0));
                }
                humanPlayer.setTurnMark(turn - 1);
                humanPlayer.totalMark += humanPlayer.mark[turn - 1];
                humanPlayer.turnReset();
                if (ifAi) {
                    aiPlayer.aiAction();
                    aiPlayer.setTurnMark(turn - 1);
                    aiPlayer.totalMark += aiPlayer.mark[turn - 1];
                    aiPlayer.turnReset();
                }
                updateGameState();
                lastRoundScore.setText("Last Round Score: " + humanPlayer.mark[turn - 1]);
                totalScore.setText("Total Score: " + humanPlayer.totalMark);
                if (ifAi) {
                    aiTotalScore.setText("AI Total Score: " + aiPlayer.totalMark);
                }
                turn++;
                if (turn > 15) {
                    gameSettlement();
                }
                currentRound.setText("Current Round: " + turn);
            }
        });
    }

    /**
     * A popup asks the player if they want an AI opponent;
     */
    public static void ifAI() {
        Alert alertWindow = new Alert(Alert.AlertType.NONE, "Do you need an AI rival?");
        alertWindow.initStyle(StageStyle.UNDECORATED);
        alertWindow.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alertWindow.setTitle("AI rival");

        // options
        ButtonType yes = new ButtonType("YES!");
        ButtonType no = new ButtonType("NO!");
        alertWindow.getButtonTypes().addAll(yes, no);


        // show the dialog
        Optional<ButtonType> result = alertWindow.showAndWait();

        if (result.get() == yes){
            // user choose to have an AI rival;
            // 激活AI玩家

            ifAi = true;
        }
        else{
            // user choose not to have an AI rival;
            ifAi = false;
        }
    }

    /**
     * This method is used to initialize the reset button in the game;
     * All the data in the game would be reset to the initial status.
     */
    public static void initResetButton() {
        reset.setOnAction((ActionEvent e) -> {
            humanPlayer.totalReset();
            aiPlayer.totalReset();
            turn = 1;
            ifAI();
            lastRoundScore.setText("Last Round Score: 0");
            totalScore.setText("Total Score: 0");
            currentRound.setText("Current Round: 1");
            currentStage.setText("Current Stage: Roll Dice");
            if (ifAi) {
                aiTotalScore.setText("AI Total Score: 0");
                title.setText("Catan -- PVE Mode");
            } else {
                aiTotalScore.setText("");
                title.setText("Catan -- Solo Mode");
            }

            updateResource(humanPlayer, num);

        });
        reset.setBackground(new Background(new BackgroundFill(Paint.valueOf("#196BE5"), new CornerRadii(1), new Insets(3))));
        reset.setLayoutX(141);
        reset.setLayoutY(370);
        reset.setPrefWidth(93);
        reset.setPrefHeight(40);
        reset.setFont(Font.font("Open Sans", 14));

    }

    /**
     * This method will reset the game state at the end of the game and
     * calculate the score for all players, including human players and
     * AI players (if any).
     */
    public static void gameSettlement() {
        Alert alertWindow = new Alert(Alert.AlertType.NONE);
        alertWindow.initStyle(StageStyle.UNDECORATED);
        alertWindow.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        if (ifAi) {
            alertWindow.setContentText("Your total mark is " + humanPlayer.totalMark + "\n" +
                    "AI total mark is " + aiPlayer.totalMark);
        }
        else {
            alertWindow.setContentText("Your total mark is " + humanPlayer.totalMark);
        }

        alertWindow.setTitle("Game over");
        // options
        ButtonType playAgain = new ButtonType("Play Again!");
        ButtonType closeGame = new ButtonType("Close");
        alertWindow.getButtonTypes().setAll(playAgain, closeGame);

        // show the dialog
        Optional<ButtonType> result = alertWindow.showAndWait();

        if (result.get() == playAgain){
            // user chose play again
            humanPlayer.totalReset();
            aiPlayer.totalReset();
            turn = 1;
            stageA = 0;
            updateGameState();
            lastRoundScore.setText("Last Round Score: " + humanPlayer.mark[turn - 1]);
            totalScore.setText("Total Score: " + humanPlayer.totalMark);
            ifAI();
            if (ifAi) {
                aiTotalScore.setText("AI Total Score: 0");
                title.setText("Catan -- PVE Mode");
            } else {
                aiTotalScore.setText("");
                title.setText("Catan -- Solo Mode");
            }
        }
        else{
            // user chose quit
            Platform.exit();
        }
    }

    /**
     * update the state of game
     */
    public static void updateGameState() {
        updateDraggableConstructionPaneList();
        updateResource(humanPlayer, num);
    }

    /**
     * Initializes the Construction Pane that the human player can drag.
     */
    public static void initDraggableConstructionPaneList() {
        for (int i = 0; i < 4; i++) {
            DraggableConstructionPane draggableRoad = new DraggableConstructionPane(71, 440.16, 0, 46,
                    19.84, "file:assets/BuildTips/Road.png",
                    new Construction(ConstructionType.ROAD, 0, false), humanPlayer);
            draggableConstructionPaneList.add(draggableRoad);


            DraggableConstructionPane draggableJoker = new DraggableConstructionPane(81.78, 491.9, 0, 26.1,
                    45, "file:assets/BuildTips/Knight.png",
                    new Construction(ConstructionType.JOKER, 1, false), humanPlayer);
            draggableConstructionPaneList.add(draggableJoker);

            DraggableConstructionPane draggableSettlement = new DraggableConstructionPane(74, 551, 0, 40.09,
                    51.28, "file:assets/BuildTips/Settlement.png",
                    new Construction(ConstructionType.SETTLEMENT, 4, false), humanPlayer);
            draggableConstructionPaneList.add(draggableSettlement);

            DraggableConstructionPane draggableCity = new DraggableConstructionPane(69.11, 618, 0, 53.89,
                    48.59, "file:assets/BuildTips/City.png",
                    new Construction(ConstructionType.CITY, 7, false), humanPlayer);
            draggableConstructionPaneList.add(draggableCity);
        }
    }

    /**
     * Update the Construction Pane that the human player can drag.
     */
    public static void updateDraggableConstructionPaneList() {
        if (draggableConstructionPaneList.size() > 0) {
            for (int i = 0; i < draggableConstructionPaneList.size(); i++) {
                if (!draggableConstructionPaneList.get(i).isUsed()) {
                    if (CatanDice.checkResources(draggableConstructionPaneList.get(i).construction.toString(),
                            humanPlayer.resource)) {
                        draggableConstructionPaneList.get(i).construction.setAvailable(true);
                        if (draggableConstructionPaneList.get(i).construction.getType() == ConstructionType.ROAD) {
                            draggableConstructionPaneList.get(i).changeBackground("file:assets/BuildTips/Road_available.png");
                        } else if (draggableConstructionPaneList.get(i).construction.getType() == ConstructionType.JOKER) {
                            draggableConstructionPaneList.get(i).changeBackground("file:assets/BuildTips/Knight_available.png");
                        } else if (draggableConstructionPaneList.get(i).construction.getType() == ConstructionType.SETTLEMENT) {
                            draggableConstructionPaneList.get(i).changeBackground("file:assets/BuildTips/Settlement_available.png");
                        } else if (draggableConstructionPaneList.get(i).construction.getType() == ConstructionType.CITY) {
                            draggableConstructionPaneList.get(i).changeBackground("file:assets/BuildTips/City_available.png");
                        }
                    } else {
                        draggableConstructionPaneList.get(i).construction.setAvailable(false);
                        if (draggableConstructionPaneList.get(i).construction.getType() == ConstructionType.ROAD) {
                            draggableConstructionPaneList.get(i).changeBackground("file:assets/BuildTips/Road.png");
                        } else if (draggableConstructionPaneList.get(i).construction.getType() == ConstructionType.JOKER) {
                            draggableConstructionPaneList.get(i).changeBackground("file:assets/BuildTips/Knight.png");
                        } else if (draggableConstructionPaneList.get(i).construction.getType() == ConstructionType.SETTLEMENT) {
                            draggableConstructionPaneList.get(i).changeBackground("file:assets/BuildTips/Settlement.png");
                        } else if (draggableConstructionPaneList.get(i).construction.getType() == ConstructionType.CITY) {
                            draggableConstructionPaneList.get(i).changeBackground("file:assets/BuildTips/City.png");
                        }
                    }
                }
                else {
                    if (draggableConstructionPaneList.get(i).construction.getType() == ConstructionType.ROAD) {
                        draggableConstructionPaneList.get(i).changeBackgroundAlignCaught("file:assets/BuildTips/Road_available.png");
                    } else if (draggableConstructionPaneList.get(i).construction.getType() == ConstructionType.JOKER) {
                        draggableConstructionPaneList.get(i).changeBackgroundAlignCaught("file:assets/BuildTips/Knight_available.png");
                    } else if (draggableConstructionPaneList.get(i).construction.getType() == ConstructionType.SETTLEMENT) {
                        draggableConstructionPaneList.get(i).changeBackgroundAlignCaught("file:assets/BuildTips/Settlement_available.png");
                    } else if (draggableConstructionPaneList.get(i).construction.getType() == ConstructionType.CITY) {
                        draggableConstructionPaneList.get(i).changeBackgroundAlignCaught("file:assets/BuildTips/City_available.png");
                    }
                }
            }
        }
    }

    /**
     * This method would initialize the turn bar, including the text message inside the bar,
     * @return The group includes all the elements in the bar.
     */
    private static void initTurnBar() {
        Rectangle background = new Rectangle(437, 126);
        background.setX(32);
        background.setY(27);
        background.setFill(Paint.valueOf("#FFFBE0"));
        background.setStroke(Paint.valueOf("#283D80"));
        background.setStrokeWidth(2);
        initTurnBar.getChildren().addAll(background);
        title = new Text(59, 70, "Catan");
        title.setFont(Font.font("Inter", 28));
        title.setFill(Paint.valueOf("#F8C822"));
        initTurnBar.getChildren().addAll(title);
        lastRoundScore.setFont(Font.font("inter", 20));
        initTurnBar.getChildren().addAll(lastRoundScore);
        totalScore.setFont(Font.font("inter", 20));
        initTurnBar.getChildren().addAll(totalScore);
        currentRound.setFont(Font.font("inter", 20));
        initTurnBar.getChildren().addAll(currentRound);
        aiTotalScore.setX(293);
        aiTotalScore.setY(130);
        aiTotalScore.setFont(Font.font("inter", 20));
        initTurnBar.getChildren().addAll(aiTotalScore);
        if (ifAi) {
            title.setText("Catan -- PVE Mode");
            aiTotalScore.setText("AI Total Score: 0");
        } else {
            title.setText("Catan -- Solo Mode");
            aiTotalScore.setText("");
        }
    }

    /**
     * Refresh the resource table when the values of resources changed.
     *
     * @param player the player class
     * @param num    the table of resources
     */
    public static void updateResource(Player player, Text[] num) {
        int dice = player.dice;
        num[0].setText(Integer.toString(dice));
        for (int i = 1; i < 7; i++) {
            num[i].setText(Integer.toString(player.resource[i - 1]));
        }
    }

    /**
     * Set background of the GUI.
     */
    private void initCatanBackground() {
        BackgroundSize backgroundSize = new BackgroundSize(WINDOW_WIDTH, WINDOW_HEIGHT, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("file:assets/Background.png"),
                null, null, null, backgroundSize);
        Background background = new Background(backgroundImage);
        Pane backgroundPane = new Pane();
        backgroundPane.setBackground(background);
        backgroundPane.setLayoutX(0);
        backgroundPane.setLayoutY(0);
        backgroundPane.setPrefWidth(WINDOW_WIDTH);
        backgroundPane.setPrefHeight(WINDOW_HEIGHT);
        root.getChildren().add(backgroundPane);
    }

    /**
     * Set the popup and its text for the help button in the UI.
     *
     * @param help The button will bring up the help message.
     * @param text The help text in popup.
     */
    private void initHelpText(Button help, String text) {
        Alert alertWindow = new Alert(Alert.AlertType.NONE, text, ButtonType.OK);
        alertWindow.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        help.setOnMouseClicked(event -> {
            // set title of popup
            alertWindow.setTitle("Help");

            // show the dialog
            alertWindow.showAndWait();
        });
        help.setBackground(new Background(new BackgroundFill(Paint.valueOf("#196BE5"), new CornerRadii(1), new Insets(3))));
        help.setLayoutX(266);
        help.setLayoutY(370);
        help.setPrefWidth(93);
        help.setPrefHeight(40);
        help.setFont(Font.font("Open Sans", 14));
    }

    /**
     * This method would initialize resource bar including the resources status and the buttons of resources and dice.
     * @param resource The list of resources' amount.
     * @param player The player needed to be operated.
     */
    private void initResourceBar(int[] resource, Player player) {
        player.dice = 6;
        Rectangle background = new Rectangle(437, 126);
        background.setStroke(Color.BLACK);
        background.setX(32);
        background.setY(184);
        background.setFill(Paint.valueOf("#FFFBE0"));
        root.getChildren().add(background);
        String dice_s = Integer.toString((player.dice));
        String[] icons = {"file:assets/resources/Dice.png",
                "file:assets/resources/Ore.png",
                "file:assets/resources/Grain.png",
                "file:assets/resources/Wool.png",
                "file:assets/resources/Timber.png",
                "file:assets/resources/Brick.png",
                "file:assets/resources/Gold.png"};

        for (int i = 0; i < 7; i++) {
            table[i * 2] = new Rectangle();
            table[i * 2].setHeight(60);
            table[i * 2].setWidth(59);
            table[i * 2].setX(46 + 58 * i);
            table[i * 2].setY(208);
            table[i * 2].setStrokeWidth(1);
            table[i * 2].setStroke(Color.BLACK);
            table[i * 2].setFill(Color.TRANSPARENT);
            root.getChildren().add(table[i * 2]);
            table[i * 2 + 1] = new Rectangle();
            table[i * 2 + 1].setHeight(29);
            table[i * 2 + 1].setWidth(59);
            table[i * 2 + 1].setX(46 + 58 * i);
            table[i * 2 + 1].setY(267);
            table[i * 2 + 1].setFill(Color.TRANSPARENT);
            table[i * 2 + 1].setStrokeWidth(1);
            table[i * 2 + 1].setStroke(Color.BLACK);

            root.getChildren().add(table[i * 2 + 1]);
            if (i == 0) {
                num[i] = new Text(70, 290, dice_s);
            } else {
                num[i] = new Text(70 + 58 * i, 290, Integer.toString(resource[i - 1]));
            }
            num[i].setFont(Font.font("Arial", 18));
            root.getChildren().add(num[i]);
            Image icon = new Image(icons[i]);
            resources[i] = new Button();
            resources[i].setPadding(Insets.EMPTY);
            ImageView Icon = new ImageView(icon);
            Icon.setFitWidth(50);
            Icon.setFitHeight(49);
            resources[i].setGraphic(Icon);
            resources[i].setPrefHeight(57);
            resources[i].setPrefWidth(57);
            resources[i].setLayoutX(47 + 58 * i);
            resources[i].setLayoutY(209);
            root.getChildren().add(resources[i]);
        }
        trade.setFont(Font.font("Open Sans", 14));
        trade.setBackground(new Background(new BackgroundFill(Paint.valueOf("#196BE5"), new CornerRadii(1), new Insets(3))));
        trade.setLayoutX(141);
        trade.setLayoutY(316);
        trade.setPrefWidth(93);
        trade.setPrefHeight(40);
        DropShadow shadow = new DropShadow();

        trade.setOnAction((ActionEvent e) -> {
            if (stageA == 0) {
                if (operate[0] == 'r') {
                    operate[0] = 't';
                    trade.setEffect(shadow);
                } else {
                    operate[0] = 'r';
                    trade.setEffect(null);
                }
                player.resourceOperate(operate);
            }
        });
        for (int i = 0; i < 7; i++) {
            int finalI = i;
            resources[i].setOnAction((ActionEvent e) -> {
                operate[1] = Integer.toString(finalI).toCharArray()[0];
                player.resourceOperate(operate);
                updateResource(player, num);
            } );
        }


        trade.setEffect(null);
        root.getChildren().add(trade);

        currentStage.setText("Current Stage: " + "Roll Dice");
        currentStage.setX(46);
        currentStage.setY(203);
        currentStage.setFont(Font.font("Inter",18));
        root.getChildren().addAll(currentStage);
    }

    /**
     * Init the Construct panel and add these elements to root.
     */
    private void initConstructPanel() {
        final double X = 62;
        final double Y = 413.68;
        final double WIDTH = 377;
        final double HEIGHT = 259.32;

        BackgroundSize backgroundSize = new BackgroundSize(WIDTH, HEIGHT, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("file:assets/BuildTips/BuildTip_blank.png"),
                null, null, null, backgroundSize);
        Background background = new Background(backgroundImage);

        Pane constructPanel = new Pane();
        constructPanel.setLayoutX(X);
        constructPanel.setLayoutY(Y);
        constructPanel.setPrefWidth(WIDTH);
        constructPanel.setPrefHeight(HEIGHT);
        constructPanel.setBackground(background);
        updateDraggableConstructionPaneList();
        root.getChildren().add(constructPanel);
        root.getChildren().addAll(draggableConstructionPaneList);
    }

    /**
     * Init the background of map.
     */
    private void initMap() {
        final double X = 524;
        final double Y = 24;
        final double WIDTH = 651;
        final double HEIGHT = 652;

        BackgroundSize backgroundSize = new BackgroundSize(WIDTH, HEIGHT, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("file:assets/island-one-with-numbering_1.png"),
                null, null, null, backgroundSize);
        Background background = new Background(backgroundImage);

        Pane constructPanel = new Pane();
        constructPanel.setLayoutX(X);
        constructPanel.setLayoutY(Y);
        constructPanel.setPrefWidth(WIDTH);
        constructPanel.setPrefHeight(HEIGHT);
        constructPanel.setBackground(background);
        root.getChildren().add(constructPanel);
    }

    @Override
    public void start(Stage stage) throws Exception {


        // add background
        ifAI();
        initCatanBackground();
        initResourceBar(humanPlayer.resource, humanPlayer);

        turn();
        root.getChildren().addAll(endStage);
        initTurnBar();
        root.getChildren().addAll(initTurnBar);
        initResetButton();
        root.getChildren().addAll(reset);
        initHelpText(help, "    Catan is a board game which you can play on your own or play with other player on different board." +
                " There are six kinds of resources in the game, the player would have six dices to roll to judge the resources of the current turn." +
                " The resource can be reassign by click on the corresponding resource and click on the dice." +
                " The dices can be rerolled for three times in one turn, there is no dice limit in one roll." +
                " The resources would be reset in each turn." +
                " The resources can be used to build construction in the construction stage, each construction would have corresponding score." +
                " The goal of the game is to earn as much score as you can in 15 turns." +
                "\n         GOOD LUCK!!!");
        root.getChildren().addAll(help);
        initDraggableConstructionPaneList();
        initConstructPanel();
        initMap();
        root.getChildren().addAll(humanPlayer.constructionPaneList);
        root.getChildren().addAll(aiPlayer.constructionPaneList);

        // set help button for scoreboard
        Button scoreHelp = new Button("?");
        initHelpText(scoreHelp, "This scoreboard shows your current total score" +
                " and the score from the previous round. The total score here after " +
                "fifteen rounds will be your final score for the game.");
        scoreHelp.setBackground(new Background(new BackgroundFill(Paint.valueOf("#D3D3D3"),
                new CornerRadii(10), new Insets(3))));
        scoreHelp.setLayoutX(477);
        scoreHelp.setLayoutY(27);
        scoreHelp.setPrefSize(24, 24);
        root.getChildren().add(scoreHelp);

        // set help button for dice and resources panel
        Button rollDiceHelp = new Button("?");
        initHelpText(rollDiceHelp, "This panel records the number of dice and resources you " +
                "currently have available. When you are not satisfied with the resources you get " +
                "from this dice roll, you can click on them. Clicking the resource once will reduce " +
                "it and return you a dice, which you can re-roll. You can re-roll up to three times " +
                "in a round.");
        rollDiceHelp.setBackground(new Background(new BackgroundFill(Paint.valueOf("#D3D3D3"),
                new CornerRadii(10), new Insets(3))));
        rollDiceHelp.setLayoutX(477);
        rollDiceHelp.setLayoutY(184);
        rollDiceHelp.setPrefSize(24, 24);
        root.getChildren().add(rollDiceHelp);

        // set help button for construct panel
        Button constructHelp = new Button("?");
        initHelpText(constructHelp, "The constructions you can currently build are recorded on this panel. " +
                "The constructions that can currently be constructed are shown in green. " +
                "Drag them to the corresponding positions on the map to construct them.");
        constructHelp.setBackground(new Background(new BackgroundFill(Paint.valueOf("#D3D3D3"),
                new CornerRadii(10), new Insets(3))));
        constructHelp.setLayoutX(447);
        constructHelp.setLayoutY(413.7);
        constructHelp.setPrefSize(24, 24);
        root.getChildren().add(constructHelp);

        // set help button for map
        Button mapHelp = new Button("?");
        initHelpText(mapHelp, "The map records the status of all constructions on the island. " +
                "Unbuilt constructions are shown in white, already built constructions are shown in green, " +
                "and used knights are shown in red. Right click to swap resources");
        mapHelp.setBackground(new Background(new BackgroundFill(Paint.valueOf("#D3D3D3"),
                new CornerRadii(10), new Insets(3))));
        mapHelp.setLayoutX(1136);
        mapHelp.setLayoutY(39);
        mapHelp.setPrefSize(24, 24);
        root.getChildren().add(mapHelp);

        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle("Catan");
        stage.setScene(scene);
        stage.show();
    }
}
