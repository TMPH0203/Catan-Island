package comp1110.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Player {
    public List<ConstructionPane> constructionPaneList = new ArrayList<>();
    public String board = "";
    public int[] resource = new int[6];
    public int dice = 0;
    public int rollTime = 0;
    public int[] mark = new int[15];
    public int stage = 0;
    public int totalMark = 0;
    private List<ConstructionPane> thisTurnList = new ArrayList<>();

    /**
     * Empty constructor for some test.
     */
    public Player() {
    }

    /**
     * Construct a player instance.
     *
     * @param map the map (Construction Pane List) associate with this player.
     */
    public Player(List<ConstructionPane> map) {
//        resource = new int[] {6, 6, 6, 6, 6, 6};
//        resource = new int[] {1, 1, 1, 1, 1, 1};
        resource = new int[]{0, 0, 0, 0, 0, 0};
        this.constructionPaneList.addAll(map);
        updateBuildState();
    }

    /**
     * Roll the dices which are needed to be rolled again;
     * Modify the corresponding resources;
     * @param resource_replaced the resources amount which are to be replaced;
     */
    public void re_roll(int[] resource_replaced){
        int[] resources = this.resource;
        int num = 0;
        boolean state = true;
        for (int i = 0; i < 6; i++) {
            if (resources[i] >= resource_replaced[i]) {
                num += resource_replaced[i];
            }
            else {
                state = false;
                break;
            }
        }
        for (int i = 0; i< 6; i++) {
            if (state) {
                resources[i] -= resource_replaced[i];
            }
        }
        if (state) {
            CatanDice.rollDice(num, resources);
            this.resource = resources;
        }
        else {
            System.out.println("Invalid Operation!");
        }
    }

    /**
     * reset dice and resource when the round ends;
     */
    public void turnReset() {
        this.dice = 6;
        this.rollTime = 0;
        this.resource = new int[6];
        this.thisTurnList.clear();
        this.stage = 0;
    }

    /**
     * reset dice and resource when the game ends;
     */
    public void totalReset() {
        this.turnReset();
        this.mark = new int[15];
        this.totalMark = 0;
        for (var each : this.constructionPaneList) {
            each.construction.setBuilt(false);
            each.construction.setAvailable(false);
            each.changeBackground("file:assets/BuildTips/Blank.png");
        }
        updateBuildState();
    }

    /**
     * Settle the score for each turn.
     *
     * @param turn The number of turn.
     */
    public void setTurnMark(int turn) {
        if (this.thisTurnList.size() == 0) {
            this.mark[turn] = -2;
        } else {
            for (var each : this.thisTurnList) {
                if (Pattern.matches("^R(\\d|1[0-5])$", each.construction.toString())) {
                    this.mark[turn] += 1;
                } else {
                    this.mark[turn] += each.construction.getNumber();
                }

            }
        }
    }



    /**
     * Generates a string corresponding to the current map construction state.
     *
     * @param constructionPaneList The Construction Pane List of the player.
     * @return The string corresponding to the current map construction state.
     */
    public String initBoardState(List<ConstructionPane> constructionPaneList) {
        StringBuilder newBoard = new StringBuilder();
        List<ConstructionPane> builtList = new ArrayList<>();
        for (ConstructionPane constructionPane : constructionPaneList) {
            if (constructionPane.construction.isBuilt()) {
                builtList.add(constructionPane);
            }
        }
        for (int i = 0; i < builtList.size(); i++) {
            newBoard.append(builtList.get(i).toString());
            if (i < builtList.size() - 1) {
                newBoard.append(",");
            }
        }
        return newBoard.toString();
    }

    /**
     * Update the build field of the Construction Pane List of the player.
     */
    public void updateBuildState() {
        this.board = initBoardState(this.constructionPaneList);
        for (var each : this.constructionPaneList) {
            if (CatanDice.checkBuildConstraints(each.toString(), this.board)) {
                each.construction.setAvailable(true);
            }
        }
    }


    /**
     * This method is used to realize some operation of the resources, including rolling dice, rolling the dice again,
     * and trade the resources.
     *
     * @param operate A two-bits char array used to operate the resources, the first bit indicates the operation, 'r'
     *                means "rolling the dice" and 't' means "trade". The second bit indicates the resource button which
     *                was triggered.
     */
    public void resourceOperate(char[] operate) {
        char mode = operate[0];
        int num = operate[1] - '0';
        if (stage == 0) {
            if (mode == 't') {
                if (num > 0 && num < 6 && resource[5] >= 2) {
                    this.resource[num - 1]++;
                    this.resource[5] -= 2;
                }
            } else if (mode == 'r' && rollTime < 3) {
                if (num == 0) {
                    CatanDice.rollDice(this.dice, this.resource);
                    this.dice = 0;
                    this.rollTime++;
                } else if (this.resource[num - 1] > 0) {
                    this.resource[num - 1]--;
                    this.dice++;
                }
            }
        }
    }

    /**
     * Swap resources by using resource joker.
     *
     * @param action A string representing the swap action and the resources being swapped.
     * @return true iff swapping successfully, false otherwise.
     */
    public boolean swap(String action) {
        String[] swapStr = action.split(" ");
        if (swapStr.length != 3 ||
                !swapStr[0].equals("swap") ||
                !Pattern.matches("^[0-5]$", swapStr[1]) ||
                !Pattern.matches("^[0-5]$", swapStr[2]) ||
                swapStr[1].equals(swapStr[2])) {
            System.out.println("The format of action (\"" + action + "\") is incorrect.");
            return false;
        }
        if (this.resource[Integer.parseInt(swapStr[1])] <= 0) {
            System.out.println("We do not have enough resources to swap.");
            return false;
        }
        this.resource[Integer.parseInt(swapStr[1])]--;
        this.resource[Integer.parseInt(swapStr[2])]++;
        return true;
    }

    /**
     * Build a Construction.
     *
     * @param action A string representing the build action and the Construction being built.
     * @return true iff the Construction is building successfully, false otherwise.
     */
    public boolean build(String action) {
        String[] buildStr = action.split(" ");
        if (buildStr.length != 2 ||
                !buildStr[0].equals("build") ||
                !Pattern.matches("(^R(\\d|1[0-5])$)|(^S([3-5]|7|9|11)$)|(^C(7|12|[2-3]0)$)|(^J([1-6])$)",
                        buildStr[1])) {
            System.out.println("The format of action (\"" + action + "\") is incorrect.");
            return false;
        }
        if (!CatanDice.checkBuildConstraints(buildStr[1], this.board)) {
            System.out.println("This place (" + buildStr[1] + ") cannot be built right now.");
            return false;
        }
        if (!CatanDice.checkResources(buildStr[1], this.resource)) {
            System.out.println("There are not enough resources to build this structure.");
            return false;
        }

        if (Pattern.matches("^R(\\d|1[0-5])$", buildStr[1])) {
            this.resource[3] -= 1;
            this.resource[4] -= 1;
        } else if (Pattern.matches("^J([1-6])$", buildStr[1])) {
            this.resource[0] -= 1;
            this.resource[1] -= 1;
            this.resource[2] -= 1;
        } else if (Pattern.matches("^S([3-5]|7|9|11)$", buildStr[1])) {
            this.resource[1] -= 1;
            this.resource[2] -= 1;
            this.resource[3] -= 1;
            this.resource[4] -= 1;
        } else if (Pattern.matches("^C(7|12|[2-3]0)$", buildStr[1])) {
            this.resource[0] -= 3;
            this.resource[1] -= 2;
        }
        for (var each : this.constructionPaneList) {
            if (each.construction.toString().equals(buildStr[1])) {
                each.construction.setBuilt(true);
                if (Pattern.matches("^R(\\d|1[0-5])$", buildStr[1])) {
                    each.changeBackground("file:assets/R.png");
                } else {
                    each.changeBackground("file:assets/" + buildStr[1] + ".png");
                }
                this.thisTurnList.add(each);
            }
        }
        updateBuildState();
        return true;
    }

    /**
     * Demolish a Construction.
     *
     * @param action A string representing the demolish action and the Construction being built.
     * @return true iff the Construction is demolishing successfully, false otherwise.
     */
    public boolean demolish(String action) {
        String[] demolishStr = action.split(" ");
        if (demolishStr.length != 2 ||
                !demolishStr[0].equals("demolish") ||
                !Pattern.matches("(^R(\\d|1[0-5])$)|(^S([3-5]|7|9|11)$)|(^C(7|12|[2-3]0)$)|(^J([1-6])$)",
                        demolishStr[1])) {
            System.out.println("The format of action (\"" + action + "\") is incorrect.");
            return false;
        }
        for (var each : this.constructionPaneList) {
            if (each.construction.toString().equals(demolishStr[1])) {
                if (!each.construction.isBuilt()) {
                    return false;
                } else {
                    List<ConstructionPane> tmpList = new ArrayList<>(this.constructionPaneList);
                    tmpList.remove(each);
                    String testConstrain = this.initBoardState(tmpList);
                    for (var testStruct : this.thisTurnList) {
                        if (!CatanDice.checkBuildConstraints(testStruct.construction.toString(), testConstrain)) {
                            return false;
                        }
                    }
                    each.construction.setBuilt(false);

                    if (Pattern.matches("^R(\\d|1[0-5])$", demolishStr[1])) {
                        this.resource[3] += 1;
                        this.resource[4] += 1;
                        each.changeBackground("file:assets/BuildTips/Blank.png");
                    } else if (Pattern.matches("^J([1-6])$", demolishStr[1])) {
                        this.resource[0] += 1;
                        this.resource[1] += 1;
                        this.resource[2] += 1;
                        each.changeBackground("file:assets/BuildTips/Blank.png");
                    } else if (Pattern.matches("^S([3-5]|7|9|11)$", demolishStr[1])) {
                        this.resource[1] += 1;
                        this.resource[2] += 1;
                        this.resource[3] += 1;
                        this.resource[4] += 1;
                        each.changeBackground("file:assets/BuildTips/Blank.png");
                    } else if (Pattern.matches("^C(7|12|[2-3]0)$", demolishStr[1])) {
                        this.resource[0] += 3;
                        this.resource[1] += 2;
                        each.changeBackground("file:assets/BuildTips/Blank.png");
                    }

                    this.thisTurnList.remove(each);
                    updateBuildState();
                    return true;
                }
            }
        }
        return false;
    }


    public  static class AIPlayer extends Player{

        /**
         * Construct an ai player instance which is same as player.
         *
         * @param map the map (Construction Pane List) associate with this ai player.
         */
        public AIPlayer(List<ConstructionPane> map) {
            super(map);
        }

        /**
         * Build a Construction but AI player has different background.
         *
         * @param action A string representing the build action and the Construction being built.
         * @return true iff the Construction is building successfully, false otherwise.
         */
        @Override
        public boolean build(String action) {
            String[] buildStr = action.split(" ");
            if (buildStr.length != 2 ||
                    !buildStr[0].equals("build") ||
                    !Pattern.matches("(^R(\\d|1[0-5])$)|(^S([3-5]|7|9|11)$)|(^C(7|12|[2-3]0)$)|(^J([1-6])$)",
                            buildStr[1])) {
                System.out.println("The format of action (\"" + action + "\") is incorrect.");
                return false;
            }
            if (!CatanDice.checkBuildConstraints(buildStr[1], this.board)) {
                System.out.println("This place (" + buildStr[1] + ") cannot be built right now.");
                return false;
            }
            if (!CatanDice.checkResources(buildStr[1], this.resource)) {
                System.out.println("There are not enough resources to build this structure.");
                return false;
            }

            if (Pattern.matches("^R(\\d|1[0-5])$", buildStr[1])) {
                this.resource[3] -= 1;
                this.resource[4] -= 1;
            } else if (Pattern.matches("^J([1-6])$", buildStr[1])) {
                this.resource[0] -= 1;
                this.resource[1] -= 1;
                this.resource[2] -= 1;
            } else if (Pattern.matches("^S([3-5]|7|9|11)$", buildStr[1])) {
                this.resource[1] -= 1;
                this.resource[2] -= 1;
                this.resource[3] -= 1;
                this.resource[4] -= 1;
            } else if (Pattern.matches("^C(7|12|[2-3]0)$", buildStr[1])) {
                this.resource[0] -= 3;
                this.resource[1] -= 2;
            }
            for (var each : this.constructionPaneList) {
                if (each.construction.toString().equals(buildStr[1])) {
                    each.construction.setBuilt(true);
                    each.changeBackground("file:assets/arrow.png");
                    super.thisTurnList.add(each);
                }
            }
            updateBuildState();
            return true;
        }

        private int[] calcDemand(ConstructionType type) {
            int[] resource_copy = Arrays.copyOf(this.resource, this.resource.length);
            if (type == ConstructionType.ROAD) {
                resource_copy[3] -= 1;
                resource_copy[4] -= 1;
            } else if (type == ConstructionType.JOKER) {
                resource_copy[0] -= 1;
                resource_copy[1] -= 1;
                resource_copy[2] -= 1;
            } else if (type == ConstructionType.SETTLEMENT) {
                resource_copy[1] -= 1;
                resource_copy[2] -= 1;
                resource_copy[3] -= 1;
                resource_copy[4] -= 1;
            } else if (type == ConstructionType.CITY) {
                resource_copy[0] -= 3;
                resource_copy[1] -= 2;
            }
            return resource_copy;
        }

        /**
         * Roll dice operation of AI player. The AI player will choose the
         * resource with the most surplus in exchange for the dice and re-roll the dice.
         */
        public void aiRoll() {
            this.resourceOperate(new char[]{'r', '0'});
            for (int i = 0; i < 2; i++) {
                int[] demandRoad = calcDemand(ConstructionType.ROAD);
                int[] demandJoker = calcDemand(ConstructionType.JOKER);
                int[] demandSettlement = calcDemand(ConstructionType.SETTLEMENT);
                int[] demandCity = calcDemand(ConstructionType.CITY);

                int[][] demands = new int[][] {demandRoad, demandJoker, demandSettlement, demandCity};

                int totalDemandRoad = Arrays.stream(demandRoad).sum();
                int totalDemandJoker = Arrays.stream(demandJoker).sum();
                int totalDemandSettlement = Arrays.stream(demandSettlement).sum();
                int totalDemandCity = Arrays.stream(demandCity).sum();

                int[] totalDemands = new int[] {totalDemandRoad, totalDemandJoker, totalDemandSettlement, totalDemandCity};
                int minDemand = Arrays.stream(totalDemands).min().orElse(Integer.MIN_VALUE);
                for (int j = 0; j < totalDemands.length; j++) {
                    if (totalDemands[j] == minDemand) {
                        int[] demand = demands[j];
                        for (int k = 0; k < demand.length; k++) {
                            if (demand[k] > 0) {
                                for (int l = 0; l < demand[k]; l++) {
                                    this.resourceOperate(new char[]{'r', (char) (k + '1')});
                                }
                            }
                        }
                    }
                }
                if (this.dice > 0) {
                    this.resourceOperate(new char[]{'r', '0'});
                }
            }
        }

        /**
         * AI build trade and swap.
         */
        public void aiAction() {
            int actionLength = 0;
            String[] bestActions = null;
            for (var each : this.constructionPaneList) {
                if(!each.construction.isBuilt()) {
                    String [] actions = CatanDice.buildPlan(each.construction.toString(), super.board, super.resource);
                    if (actions != null) {
                        if (actions.length > actionLength) {
                            bestActions = actions;
                            actionLength = actions.length;
                        }
                    }
                }

            }
            if (bestActions != null) {
                for (var each: bestActions) {
                    if(each.startsWith("b")) {
                        this.build(each);
                    }
                    else if(each.startsWith("s")) {
                        this.swap(each);
                    }
                    else if(each.startsWith("t")) {
                        this.resourceOperate(new char[] {'t', (char) (each.charAt(each.length() - 1) + 1)});
                    }
                }
            }
        }
    }
}
