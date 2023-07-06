package comp1110.ass2;

import comp1110.ass2.gui.Game;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.List;
import java.util.regex.Pattern;

public class ConstructionPane extends Pane {
    public Construction construction;
    public Player player;
    private double x;
    private double y;
    private Rotate rotate;
    private final double WIDTH;
    private final double HEIGHT;

    /**
     * Create a Construction Pane, which is an object in the map that can be snapped,
     * and drag the Draggable Construction Pane onto it to build.
     *
     * @param x The x-coordinate of the origin of this Construction Pane.
     * @param y The y-coordinate of the origin of this Construction Pane.
     * @param rotate The rotation angle of the origin of this Construction Pane.
     * @param WIDTH The width of the origin of this Construction Pane.
     * @param HEIGHT The height of the origin of this Construction Pane.
     * @param backgroundFile The path of background image of the origin of this
     *                      Construction Pane.
     * @param construction The Construction that this Construction Pane is bound to.
     */
    public ConstructionPane(double x, double y, double rotate, double WIDTH, double HEIGHT, String backgroundFile, Construction construction) {
        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
//        this.rotate = new Rotate(rotate, this.WIDTH / 2, this.HEIGHT / 2);
        this.rotate = new Rotate(rotate, 0, 0);
        this.construction = construction;

//        this.setLayoutX(this.x + (this.WIDTH / 2));
//        this.setLayoutY(this.y + (this.HEIGHT / 2));
//        this.setRotate(this.rotate);
//        this.setLayoutX(this.x);
//        this.setLayoutY(this.y);

        this.setLayoutX(this.x);
        this.setLayoutY(this.y);
        this.getTransforms().add(this.rotate);


        this.setPrefWidth(this.WIDTH);
        this.setPrefHeight(this.HEIGHT);
        this.changeBackground(backgroundFile);
    }

    /**
     * Create a Construction Pane for human player, which is an object in the map that can be snapped,
     * and drag the Draggable Construction Pane onto it to build.
     *
     * @param x The x-coordinate of the origin of this Construction Pane.
     * @param y The y-coordinate of the origin of this Construction Pane.
     * @param rotate The rotation angle of the origin of this Construction Pane.
     * @param WIDTH The width of the origin of this Construction Pane.
     * @param HEIGHT The height of the origin of this Construction Pane.
     * @param backgroundFile The path of background image of the origin of this
     *                      Construction Pane.
     * @param player Player associated with this Construction Pane.
     * @param construction The Construction that this Construction Pane is bound to.
     */
    public ConstructionPane(double x, double y, double rotate, double WIDTH, double HEIGHT, String backgroundFile, Player player, Construction construction) {
        this.x = x;
        this.y = y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.rotate = new Rotate(rotate, 0, 0);
        this.player = player;
        this.construction = construction;

        this.setLayoutX(this.x);
        this.setLayoutY(this.y);
        this.getTransforms().add(this.rotate);
        this.setPrefWidth(this.WIDTH);
        this.setPrefHeight(this.HEIGHT);
        this.changeBackground(backgroundFile);

        this.setOnMousePressed(event -> {
            if (this.construction.isBuilt() &&
                    this.construction.getType() == ConstructionType.JOKER &&
                    event.getButton() == MouseButton.SECONDARY &&
                    this.player.stage == 0) {
                Group root = new Group();
                Button[] resources = new Button[6];
                Rectangle[] table = new Rectangle[12];
                String[] icons = {"file:assets/resources/Ore.png",
                        "file:assets/resources/Grain.png",
                        "file:assets/resources/Wool.png",
                        "file:assets/resources/Timber.png",
                        "file:assets/resources/Brick.png",
                        "file:assets/resources/Gold.png"};
                Stage swapResourcesStage = new Stage();
                swapResourcesStage.setTitle("Swap resources: Select the resource you want to be swapped.");
                for (int i = 0; i < 6; i++) {
                    table[i * 2] = new Rectangle();
                    table[i * 2].setHeight(60);
                    table[i * 2].setWidth(59);
                    table[i * 2].setX(46 + 58 * i);
                    table[i * 2].setY(30);
                    table[i * 2].setStrokeWidth(1);
                    table[i * 2].setStroke(Color.BLACK);
                    table[i * 2].setFill(Color.TRANSPARENT);

                    Image icon = new Image(icons[i]);
                    resources[i] = new Button();
                    resources[i].setPadding(Insets.EMPTY);
                    ImageView Icon = new ImageView(icon);
                    Icon.setFitWidth(50);
                    Icon.setFitHeight(49);
                    resources[i].setGraphic(Icon);
                    resources[i].setPrefHeight(57);
                    resources[i].setPrefWidth(57);
                    resources[i].setLayoutX(100 + 58 * i);
                    resources[i].setLayoutY(31);
                }
                if (Pattern.matches("^J[1-5]$",this.construction.toString())) {
                    for (int i = 0; i < 6; i++) {
                        int finalI = i;
                        resources[i].setOnMousePressed((MouseEvent pressEvent) -> {
                            if (this.player.swap("swap " + finalI + " " + (this.construction.getNumber() - 1))) {
                                this.construction.setType(ConstructionType.KNIGHT);
                                this.changeBackground("file:assets/" + this.construction + ".png");
                                Game.updateResource(Game.humanPlayer, Game.num);
                                swapResourcesStage.close();
                            }
                        });
                    }
                }
                else {
                    final int[] resourceSwappedPair = new int[2];
                    final int[] clickNum = new int[1];
                    for (int i = 0; i < 6; i++) {
                        int finalI = i;
                        resources[i].setOnMousePressed((MouseEvent pressEvent) -> {
                            if (clickNum[0] == 0) {
                                resourceSwappedPair[0] = finalI;
                                swapResourcesStage.setTitle("Swap resources: Select the resource you want have.");
                                clickNum[0]++;
                            }
                            else if (clickNum[0] == 1) {
                                resourceSwappedPair[1] = finalI;
                                if (this.player.swap("swap " + resourceSwappedPair[0] + " " +
                                        resourceSwappedPair[1])) {
                                    this.construction.setType(ConstructionType.KNIGHT);
                                    this.changeBackground("file:assets/" + this.construction + ".png");
                                    Game.updateResource(Game.humanPlayer, Game.num);
                                    swapResourcesStage.close();
                                }
                                else {
                                    swapResourcesStage.setTitle("Swap resources: Select the resource you want " +
                                            "to be swapped.");
                                    clickNum[0] = 0;
                                }
                            }
                        });
                    }
                }

                root.getChildren().addAll(resources);
                Scene scene = new Scene(root, 550, 126);
                swapResourcesStage.setScene(scene);
                swapResourcesStage.setResizable(false);
                swapResourcesStage.showAndWait();
            }
        });
    }

    /**
     * Set the x-coordinate of the origin of this Construction Pane.
     *
     * @param x The x-coordinate of the origin of this Construction Pane.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Set the y-coordinate of the origin of this Construction Pane.
     *
     * @param y The y-coordinate of the origin of this Construction Pane.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Set the rotation angle of the origin of this Construction Pane.
     *
     * @param newRotate A new Rotate object.
     */
    public void paneRotate(Rotate newRotate) {
        Rotate reverse = new Rotate(-1*this.rotate.getAngle(), 0, 0);
        this.getTransforms().add(reverse);
        this.rotate = newRotate;
        this.getTransforms().add(this.rotate);
    }

    /**
     * Get the string representation of this Construction Pane of the
     * Construction.
     *
     * @return The string representation of this Construction Pane of
     * the Construction.
     */
    @Override
    public String toString() {
        return this.construction.toString();
    }

    /**
     * Change the background of the Construction Pane.
     *
     * @param backgroundFile The path of background image of the origin
     *                       of this Construction Pane.
     */
    public void changeBackground(String backgroundFile) {
        this.setPrefWidth(this.WIDTH);
        this.setPrefHeight(this.HEIGHT);
        BackgroundSize backgroundSize= new BackgroundSize(this.WIDTH, this.HEIGHT, true, true, true,true);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(backgroundFile),
                null, null, null, backgroundSize);
        Background background = new Background(backgroundImage);
        this.setBackground(background);
    }

    /**
     * Calculates the distance between two Construction Panes.
     *
     * @param x The x-coordinate of the origin of another Construction Pane.
     * @param y The y-coordinate of the origin of another Construction Pane.
     * @return The distance between two Construction Panes.
     */
    private double distance(double x, double y) {
        return Math.sqrt((this.x - x) *
                (this.x - x) +
                (this.y - y) *
                        (this.y - y));
    }

    public static class DraggableConstructionPane extends ConstructionPane {
        public final double originX;
        public final double originY;
        public List<ConstructionPane> constructionPaneList;
        public Player player;
        private double mouseX;
        private double mouseY;
        private boolean used;
        private ConstructionPane caught;

        /**
         * Constructs a Draggable Construction Pane. The draggable construction part in the UI is
         * implemented here. It can be dragged and snapped to other Construction Panes, thus completing
         * the construction on the UI.
         *
         * @param x The x-coordinate of the origin of this Draggable Construction Pane.
         * @param y The y-coordinate of the origin of this Draggable Construction Pane.
         * @param rotate The rotation angle of the origin of this Draggable Construction Pane.
         * @param WIDTH The width of the origin of this Draggable Construction Pane.
         * @param HEIGHT The height of the origin of this Draggable Construction Pane.
         * @param backgroundFile The path of background image of the origin of this
         *                       Draggable Construction Pane.
         * @param construction The Construction that this Draggable Construction Pane is bound to.
         * @param player Player associated with this Draggable Construction Pane.
         */
        public DraggableConstructionPane(double x, double y, double rotate, double WIDTH, double HEIGHT,
                                         String backgroundFile, Construction construction, Player player) {
            super(x, y, rotate, WIDTH, HEIGHT, backgroundFile, construction);
            this.originX = x;
            this.originY = y;
            this.constructionPaneList = player.constructionPaneList;
            this.player = player;
            this.used = false;
            this.onMouseReleasedProperty();
            this.setOnMousePressed(event -> {
                if (this.construction.isAvailable()) {
                    mouseX = event.getSceneX();
                    mouseY = event.getSceneY();
                    this.toFront();
                }
            });
            this.setOnMouseDragged(event -> {
                if (this.construction.isAvailable()) {
                    double dispx = event.getSceneX() - mouseX;
                    double dispy = event.getSceneY() - mouseY;
                    this.setLayoutX(super.x + dispx);
                    this.setLayoutY(super.y + dispy);
                    super.x += dispx;
                    super.y += dispy;
                    mouseX += dispx;
                    mouseY += dispy;
                }
            });
            this.setOnMouseReleased(event -> {
                if (this.construction.isAvailable()) {
                    var caught = this.catchNearestConstruction(super.x, super.y, this.constructionPaneList);
                    if (!this.used && caught == null) {
                        super.x = this.originX;
                        super.y = this.originY;
                        this.setLayoutX(this.originX);
                        this.setLayoutY(this.originY);
                        this.paneRotate(new Rotate(0, 0, 0));
                    }
                    else if (!this.used && caught != null) {
                        if (this.player.build("build " + caught)) {
                            super.x = caught.x;
                            super.y = caught.y;
                            this.setLayoutX(caught.x);
                            this.setLayoutY(caught.y);
                            this.caught = caught;
                            this.paneRotate(caught.rotate);
                            this.setUsed(true);
                        }
                        else {
                            super.x = this.originX;
                            super.y = this.originY;
                            this.setLayoutX(this.originX);
                            this.setLayoutY(this.originY);
                            this.paneRotate(new Rotate(0, 0, 0));
                        }
                    }
                    else if (this.used && caught == null) {
                        if (this.player.demolish("demolish " + this.caught)) {
                            super.x = this.originX;
                            super.y = this.originY;
                            this.setLayoutX(this.originX);
                            this.setLayoutY(this.originY);
                            this.setUsed(false);
                            this.paneRotate(new Rotate(0, 0, 0));
                        }
                        else {
                            super.x = this.caught.x;
                            super.y = this.caught.y;
                            this.setLayoutX(this.caught.x);
                            this.setLayoutY(this.caught.y);
                            this.paneRotate(this.caught.rotate);
                        }

                    }
                    else if (this.used && caught != null) {
                        if (this.player.demolish("demolish " + this.caught)) {
                            if (this.player.build("build " + caught)) {
                                super.x = caught.x;
                                super.y = caught.y;
                                this.setLayoutX(caught.x);
                                this.setLayoutY(caught.y);
                                this.caught = caught;
                                this.paneRotate(caught.rotate);
                            }
                            else {
                                super.x = this.originX;
                                super.y = this.originY;
                                this.setLayoutX(this.originX);
                                this.setLayoutY(this.originY);
                                this.setUsed(false);
                                this.paneRotate(new Rotate(0, 0, 0));
                            }
                        }
                        else {
                            super.x = this.caught.x;
                            super.y = this.caught.y;
                            this.setLayoutX(this.caught.x);
                            this.setLayoutY(this.caught.y);
                            this.paneRotate(this.caught.rotate);
                        }

                    }
//                    else {
//                        super.x = this.originX;
//                        super.y = this.originY;
//                        this.setLayoutX(this.originX);
//                        this.setLayoutY(this.originY);
//                        this.paneRotate(new Rotate(0, 0, 0));
////                        Game.updateDraggableConstructionPaneList();
//                    }
                    Game.updateGameState();
                }
            });
        }

        /**
         * Change the background of the Construction Pane as same as the pane caught.
         *
         * @param backgroundFile The path of background image of the origin
         *                       of this Construction Pane.
         */
        public void changeBackgroundAlignCaught(String backgroundFile) {
            this.setPrefWidth(caught.WIDTH);
            this.setPrefHeight(caught.HEIGHT);
            BackgroundSize backgroundSize= new BackgroundSize(caught.WIDTH, caught.HEIGHT, true, true, true,true);
            BackgroundImage backgroundImage = new BackgroundImage(new Image(backgroundFile),
                    null, null, null, backgroundSize);
            Background background = new Background(backgroundImage);
            this.setBackground(background);
        }

        /**
         * Get this Draggable Construction Pane is used or not.
         *
         * @return true iff the Draggable Construction Pane is used, false otherwise.
         */
        public boolean isUsed() {
            return used;
        }

        /**
         * Set this Draggable Construction Pane is used or not.
         *
         * @param used true iff the Draggable Construction Pane is used, false otherwise.
         */
        public void setUsed(boolean used) {
            this.used = used;
        }

        /**
         * Find the nearest Construction Pane to the Draggable Construction Pane
         * in a specified list of Construction Pane.
         *
         * @param x The x-coordinate of the origin of this Construction Pane.
         * @param y The y-coordinate of the origin of this Construction Pane.
         * @param constructionPaneList The List of Construction Pane it will find.
         * @return The nearest Construction Pane it finds.
         */
        private ConstructionPane findNearestBuildableConstruction(double x, double y,
                                                                  List<ConstructionPane> constructionPaneList) {
            double minDistance = Double.MAX_VALUE;
            int i, minIndex = -1;
            for (i = 0; i < constructionPaneList.size(); i++) {
                if (constructionPaneList.get(i).construction.isAvailable() &&
                        !constructionPaneList.get(i).construction.isBuilt() &&
                        constructionPaneList.get(i).construction.toString().charAt(0) ==
                                this.construction.toString().charAt(0)){
                    double newDistance = constructionPaneList.get(i).distance(x, y);
                    if (newDistance < minDistance) {
                        minDistance = newDistance;
                        minIndex = i;
                    }
                }
            }
            if (minIndex < 0) {
                return null;
            }
            else {
                return constructionPaneList.get(minIndex);
            }
        }

        /**
         * Catch the nearest Construction Pane to the Draggable Construction Pane
         * in a specified list of Construction Pane within a certain range.
         * @param x The x-coordinate of the origin of this Construction Pane.
         * @param y The y-coordinate of the origin of this Construction Pane.
         * @param constructionPaneList The List of Construction Pane it will find.
         * @return The nearest Construction Pane it catches.
         */
        private ConstructionPane catchNearestConstruction(double x, double y,
                                                          List<ConstructionPane> constructionPaneList) {
            ConstructionPane theNearest = findNearestBuildableConstruction(x, y, constructionPaneList);
            if (theNearest == null) {
                return null;
            }
            else if (theNearest.distance(x, y) <= 75) {
                return theNearest;
            }
            else {
                return null;
            }
        }
    }
}
