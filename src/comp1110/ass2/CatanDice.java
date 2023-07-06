package comp1110.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class CatanDice {

    /**
     * Check if the string encoding of a board state is well formed.
     * Note that this does not mean checking if the state is valid
     * (represents a state that the player could get to in game play),
     * only that the string representation is syntactically well formed.
     *
     * @param board_state: The string representation of the board state.
     * @return true iff the string is a well-formed representation of
     *         a board state, false otherwise.
     */
    public static boolean isBoardStateWellFormed(String board_state) {
        String[] boardElement = board_state.split(",");
        for (String element : boardElement) {
            if (Pattern.matches("(^R(\\d|1[0-5])$)|(^S([3-5]|7|9|11)$)|(^C(7|12|[2-3]0)$)|(^J([1-6])$)|(^K([1-6])$)|()", element)) {
            }
            else {
                return false;
            }
        }
        return true; // FIXME: Task #3
        // FIXED!
    }

    /**
     * Check if the string encoding of a player action is well formed.
     *
     * @param action: The string representation of the action.
     * @return true iff the string is a well-formed representation of
     *         a board state, false otherwise.
     */
    public static boolean isActionWellFormed(String action) {
        String[] str;
        str = action.split(" ");
        if(Objects.equals(str[0], "build"))
        {
            return Pattern.matches("^R(\\d|1[0-5])|^S([3-5]|7|9|11)|^C(7|12|[2-3]0)|^J([1-6])",str[1]);
        }
        else if(Objects.equals(str[0], "trade"))
        {
            return Integer.parseInt(str[1]) >= 0 &&
                    Integer.parseInt(str[1]) <= 5 &&
                    str.length == 2;
        }
        else if(Objects.equals(str[0], "swap"))
        {
            return Integer.parseInt(str[1]) >= 0 &&
                    Integer.parseInt(str[1]) <= 5 &&
                    Integer.parseInt(str[2]) >= 0 &&
                    Integer.parseInt(str[2]) <= 5 &&
                    str.length == 3;
        }
	    else
            return false; // FIXME: Task #4
    }

    /**
     * Roll the specified number of dice and add the result to the
     * resource state.
     *
     * The resource state on input is not necessarily empty. This
     * method should only _add_ the outcome of the dice rolled to
     * the resource state, not remove or clear the resources already
     * represented in it.
     *
     * @param n_dice: The number of dice to roll (>= 0).
     * @param resource_state: The available resources that the dice
     *        roll will be added to.
     *
     * This method does not return any value. It should update the given
     * resource_state.
     */
    public static void rollDice(int n_dice, int[] resource_state) {
        int[] dice_resource = {0,0,0,0,0,0};
        for(int i = 0; i < n_dice; i++){
            dice_resource[i] = (int) (6 * Math.random());
        }
        for(int i = 0 ; i < n_dice; i++){
            switch (dice_resource[i]) {
                case 0 -> resource_state[0]++;
                case 1 -> resource_state[1]++;
                case 2 -> resource_state[2]++;
                case 3 -> resource_state[3]++;
                case 4 -> resource_state[4]++;
                case 5 -> resource_state[5]++;
            }
        }
	// FIXME: Task #6
    }

    /**
     * Check if the specified structure can be built next, given the
     * current board state. This method should check that the build
     * meets the constraints described in section "Building Constraints"
     * of the README file.
     *
     * @param structure: The string representation of the structure to
     *        be built.
     * @param board_state: The string representation of the board state.
     * @return true iff the structure is a possible next build, false
     *         otherwise.
     */
    public static boolean checkBuildConstraints(String structure,
						String board_state) {
        // Get board state and build target
        String[] board = board_state.split(",");
        StringBuilder buildNum = new StringBuilder();
        for (int i = 1; i < structure.length(); i++) {
            buildNum.append(structure.charAt(i));
        }
        // When build target is Road
        if (structure.startsWith("R")) {
            if (Objects.equals(buildNum.toString(), "0"))
                return true;
//            int result = 0;
            boolean result = false;
            switch (structure) {
                case "R2":
                    for (String i : board) {
                        if (Objects.equals(i, "R0")) {
                            result = true;
                            break;
                        }
                    }
                    break;
                case "R5":
                    for (String i : board) {
                        if (Objects.equals(i, "R3")) {
                            result = true;
                            break;
                        }
                    }
                    break;
                case "R12":
                    for (String i : board) {
                        if (Objects.equals(i, "R7")) {
                            result = true;
                            break;
                        }
                    }
                    break;
                default:
                    for (String i : board) {
                        if (Objects.equals(i, "R" + (Integer.parseInt(buildNum.toString()) - 1))) {
                            result = true;
                            break;
                        }
                    }
                    break;
            }
            return result;
        }
        // When build target is Settlement
        else if (structure.startsWith("S")) {
            int result = 0;
            int sBuildNum = 0;
            int[] sNum = {3,4,5,7,9,11};
            if (Objects.equals(buildNum.toString(), "3"))
                return true;
            for(int j = 0; j < sNum.length; j++) {
                if (sNum[j] == Integer.parseInt(buildNum.toString()))
                    sBuildNum = sNum[j-1];
            }
            for(String i : board) {
                if (Objects.equals(buildNum.toString(), "4") && Objects.equals(i, "R2"))
                    result++;
                else if (!Objects.equals(buildNum.toString(), "4") && Objects.equals(i, "R" + buildNum))
                    result++;
                if (Objects.equals(i, "S" + sBuildNum))
                    result++;
            }
            return (result == 2);
        }
        // When build target is City
        else if (structure.startsWith("C")) {
            int result = 0;
            int cBuildNum = 0;
            int[] cNum = {7,12,20,30};
            if (Objects.equals(buildNum.toString(), "7"))
                return board_state.contains("R1");
            else {
                for(int j = 0; j < cNum.length; j++) {
                    if (cNum[j] == Integer.parseInt(buildNum.toString()))
                        cBuildNum = cNum[j-1];
                }
                for(String i : board) {
                    if (Objects.equals(buildNum.toString(), "12") && Objects.equals(i, "R4"))
                        result++;
                    else if (Objects.equals(buildNum.toString(), "20") && Objects.equals(i, "R13"))
                        result++;
                    else if (Objects.equals(buildNum.toString(), "30")&& Objects.equals(i, "R15"))
                        result++;
                    if (Objects.equals(i, "C" + cBuildNum))
                        result++;
                }
            }
            return (result == 2);
        }
        // When build target is Joker or Knight
        else if (structure.startsWith("J") || structure.startsWith("K")) {
            int result = 0;
            if (Objects.equals(buildNum.toString(), "1"))
                return true;
            for(String i : board) {
                if (Objects.equals(i, "J" + (Integer.parseInt(buildNum.toString()) - 1)) ||
                        Objects.equals(i, "K" + (Integer.parseInt(buildNum.toString()) - 1)))
                    result++;
            }
            return (result == 1);
        }
        else
            return false; // FIXME: Task #8
    }

    /**
     * Check if the available resources are sufficient to build the
     * specified structure, without considering trades or swaps.
     *
     * @param structure: The string representation of the structure to
     *        be built.
     * @param resource_state: The available resources.
     * @return true iff the structure can be built with the available
     *         resources, false otherwise.
     */
    public static boolean checkResources(String structure, int[] resource_state) {
        // FIXME: Task #7
        if (resource_state[3] >= 1 && resource_state[4] >= 1 && Pattern.matches("^R(\\d|1[0-5])$", structure)) {
            return true;
        }
        else if (resource_state[0] >= 1 && resource_state[1] >= 1 && resource_state[2] >= 1 && Pattern.matches("^J([1-6])$", structure)) {
            return true;
        }
        else
            if (resource_state[1] >= 1 && resource_state[2] >= 1 && resource_state[3] >= 1 && resource_state[4] >= 1 && Pattern.matches("^S([3-5]|7|9|11)$", structure)) {
            return true;
        }
        else return resource_state[0] >= 3 && resource_state[1] >= 2 && Pattern.matches("^C(7|12|[2-3]0)$", structure);
        // FIXED!
    }

    /**
     * Check if the available resources are sufficient to build the
     * specified structure, considering also trades and/or swaps.
     * This method needs access to the current board state because the
     * board state encodes which Knights are available to perform swaps.
     *
     * @param structure: The string representation of the structure to
     *        be built.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the structure can be built with the available
     *         resources, false otherwise.
     */
    public static boolean checkResourcesWithTradeAndSwap(String structure,
							 String board_state,
							 int[] resource_state) {
        // FIXME: Task #12
        if (checkResources(structure, resource_state)) {
            return true;
        }
        else {
            int[] resources_demand = Arrays.copyOf(resource_state, resource_state.length);
            if (Pattern.matches("^R(\\d|1[0-5])$", structure)) {
                resources_demand[3] -= 1;
                resources_demand[4] -= 1;
            }
            else if (Pattern.matches("^J([1-6])$", structure)) {
                resources_demand[0] -= 1;
                resources_demand[1] -= 1;
                resources_demand[2] -= 1;
            }
            else if (Pattern.matches("^S([3-5]|7|9|11)$", structure)) {
                resources_demand[1] -= 1;
                resources_demand[2] -= 1;
                resources_demand[3] -= 1;
                resources_demand[4] -= 1;
            }
            else if (Pattern.matches("^C(7|12|[2-3]0)$", structure)) {
                resources_demand[0] -= 3;
                resources_demand[1] -= 2;
            }

            if (Arrays.stream(Arrays.copyOf(resources_demand,
                    resources_demand.length - 1)).sum() +
                    (resources_demand[5] / 2) < 0) {
                return false;
            }
            else {
                for (int i = 0; i < resources_demand.length - 1; i++) {
                    if (resources_demand[i] < 0) {
                        if (board_state.contains("J" + (i + 1))) {
                            resources_demand[i] += 1;
                            board_state = board_state.replace("J" + (i + 1), "K" + (i + 1));
                        }
                    }
                    if (resources_demand[i] < 0) {
                        if (board_state.contains("J6")) {
                            resources_demand[i] += 1;
                            board_state = board_state.replace("J6", "K6");
                        }
                    }
                    if (resources_demand[i] < 0) {
                        if (resources_demand[5] / 2 > 0) {
                            resources_demand[i] += 1;
                            resources_demand[5] -= 2;
                        }
                    }
                    if (resources_demand[i] < 0) {
                        return false;
                    }
                }
            }
            return true;
        }
        // FIXED!
    }

    /**
     * Check if a player action (build, trade or swap) is executable in the
     * given board and resource state.
     *
     * @param action: String representatiion of the action to check.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the action is applicable, false otherwise.
     */
    public static boolean canDoAction(String action,
				      String board_state,
				      int[] resource_state) {
        String[] actionTarget = action.split(" ");
        String[] board = board_state.split(",");
        // when action is build
        if (Objects.equals(actionTarget[0], "build")) {
            if (!isActionWellFormed(action)) {
                return false;
            }
            if (!checkResources(actionTarget[1], resource_state)) {
                return false;
            }
            return checkBuildConstraints(actionTarget[1], board_state);
        }
        else if (Objects.equals(actionTarget[0], "trade")) {
            return resource_state[5] >= 2;
        }
        else if (Objects.equals(actionTarget[0], "swap")) {
            boolean result = false;
            for (String i : board){
                if(Objects.equals("J" + (Integer.parseInt(actionTarget[2]) + 1), i) || Objects.equals("J6", i))
                    result = true;
            }
            return result && resource_state[Integer.parseInt(actionTarget[1])] > 0;
        }
        return false; // FIXME: Task #9
    }

    /**
     * Check if the specified sequence of player actions is executable
     * from the given board and resource state.
     *
     * @param actions: The sequence of (string representatins of) actions.
     * @param board_state: The string representation of the board state.
     * @param resource_state: The available resources.
     * @return true iff the action sequence is executable, false otherwise.
     */
    public static boolean canDoSequence(String[] actions,
					String board_state,
					int[] resource_state) {
        boolean result = false;
        int[] originalResources = new int[6];
        String originalBoard = board_state;
        System.arraycopy(resource_state, 0, originalResources, 0, 6);
        for (String i : actions) {
            result = canDoAction(i, originalBoard, originalResources);
            if (result) {
                String[] action = i.split(" ");
                if (action[0].startsWith("b")) {
                    originalBoard = originalBoard + "," + action[1];
                    if (action[1].startsWith("R")) {
                        originalResources[3] --;
                        originalResources[4] --;
                    } else if (action[1].startsWith("S")) {
                        originalResources[1] --;
                        originalResources[2] --;
                        originalResources[3] --;
                        originalResources[4] --;
                    } else if (action[1].startsWith("C")) {
                        originalResources[0] -= 3;
                        originalResources[1] -= 2;
                    } else if (action[1].startsWith("J")) {
                        originalResources[0] --;
                        originalResources[1] --;
                        originalResources[2] --;
                    }
                } else if (action[0].startsWith("t")) {
                    originalResources[5] -=2;
                    originalResources[Integer.parseInt(action[1])] ++;
                } else if (action[0].startsWith("s")) {
                    originalResources[Integer.parseInt(action[1])] --;
                    originalResources[Integer.parseInt(action[2])] ++;
                    originalBoard = originalBoard.replace("J" + (Integer.parseInt(action[2]) +1),"K" + (Integer.parseInt(action[2]) +1));
                }
            }
        }
        return result; // FIXME: Task #11
    }

    /**
     * Find the path of roads that need to be built to reach a specified
     * (unbuilt) structure in the current board state. The roads should
     * be returned as an array of their string representation, in the
     * order in which they have to be built. The array should _not_ include
     * the target structure (even if it is a road). If the target structure
     * is reachable via the already built roads, the method should return
     * an empty array.
     * 
     * Note that on the Island One map, there is a unique path to every
     * structure. 
     *
     * @param target_structure: The string representation of the structure
     *        to reach.
     * @param board_state: The string representation of the board state.
     * @return An array of string representations of the roads along the
     *         path.
     */
    public static String[] pathTo(String target_structure,
				  String board_state) {
        String[] result = {};
        String[] path = {};
        String[] board = board_state.split(",");

        for(String i : board) {
            if (Objects.equals(i, target_structure))
                return result;
        }

        switch (target_structure) {
            case "R0" -> path = Arrays.copyOf(ShortestPath.pathToR0, ShortestPath.pathToR0.length);
            case "R1" -> path = Arrays.copyOf(ShortestPath.pathToR1, ShortestPath.pathToR1.length);
            case "R2" -> path = Arrays.copyOf(ShortestPath.pathToR2, ShortestPath.pathToR2.length);
            case "R3" -> path = Arrays.copyOf(ShortestPath.pathToR3, ShortestPath.pathToR3.length);
            case "R4" -> path = Arrays.copyOf(ShortestPath.pathToR4, ShortestPath.pathToR4.length);
            case "R5" -> path = Arrays.copyOf(ShortestPath.pathToR5, ShortestPath.pathToR5.length);
            case "R6" -> path = Arrays.copyOf(ShortestPath.pathToR6, ShortestPath.pathToR6.length);
            case "R7" -> path = Arrays.copyOf(ShortestPath.pathToR7, ShortestPath.pathToR7.length);
            case "R8" -> path = Arrays.copyOf(ShortestPath.pathToR8, ShortestPath.pathToR8.length);
            case "R9" -> path = Arrays.copyOf(ShortestPath.pathToR9, ShortestPath.pathToR9.length);
            case "R10" -> path = Arrays.copyOf(ShortestPath.pathToR10, ShortestPath.pathToR10.length);
            case "R11" -> path = Arrays.copyOf(ShortestPath.pathToR11, ShortestPath.pathToR11.length);
            case "R12" -> path = Arrays.copyOf(ShortestPath.pathToR12, ShortestPath.pathToR12.length);
            case "R13" -> path = Arrays.copyOf(ShortestPath.pathToR13, ShortestPath.pathToR13.length);
            case "R14" -> path = Arrays.copyOf(ShortestPath.pathToR14, ShortestPath.pathToR14.length);
            case "R15" -> path = Arrays.copyOf(ShortestPath.pathToR15, ShortestPath.pathToR15.length);
            case "S4" -> path = Arrays.copyOf(ShortestPath.pathToS4, ShortestPath.pathToS4.length);
            case "S5" -> path = Arrays.copyOf(ShortestPath.pathToS5, ShortestPath.pathToS5.length);
            case "S7" -> path = Arrays.copyOf(ShortestPath.pathToS7, ShortestPath.pathToS7.length);
            case "S9" -> path = Arrays.copyOf(ShortestPath.pathToS9, ShortestPath.pathToS9.length);
            case "S11" -> path = Arrays.copyOf(ShortestPath.pathToS11, ShortestPath.pathToS11.length);
            case "C7" -> path = Arrays.copyOf(ShortestPath.pathToC7, ShortestPath.pathToC7.length);
            case "C12" -> path = Arrays.copyOf(ShortestPath.pathToC12, ShortestPath.pathToC12.length);
            case "C20" -> path = Arrays.copyOf(ShortestPath.pathToC20, ShortestPath.pathToC20.length);
            case "C30" -> path = Arrays.copyOf(ShortestPath.pathToC30, ShortestPath.pathToC30.length);
        }

        ArrayList<String> pathResult = new ArrayList<>();
        boolean addPath = false;
        if(target_structure.startsWith("R")||target_structure.startsWith("S")||target_structure.startsWith("C")) {
            for (String pathValue : path) {
                for (String boardValue : board) {
                    if(Objects.equals(pathValue, boardValue)) {
                        addPath = false;
                        break;
                    } else
                        addPath = true;
                }
                if (addPath)
                    pathResult.add(pathValue);
            }
        }

        result = pathResult.toArray(new String[0]);

        return result; // FIXME: Task #13
    }

    /**
     * Generate a plan (sequence of player actions) to build the target
     * structure from the given board and resource state. The plan may
     * include trades and swaps, as well as bulding other structures if
     * needed to reach the target structure or to satisfy the build order
     * constraints.
     *
     * However, the plan must not have redundant actions. This means it
     * must not build any other structure that is not necessary to meet
     * the building constraints for the target structure, and it must not
     * trade or swap for resources if those resources are not needed.
     *
     * If there is no valid build plan for the target structure from the
     * specified state, return null.
     *
     * @param target_structure: The string representation of the structure
     *        to be built.
     * @param board_state: The string representation of the board state.
     * @return An array of string representations of player actions. If
     *         there exists no valid build plan for the target structure,
     *         the method should return null.
     */
    public static String[] buildPlan(String target_structure,
				     String board_state,
				     int[] resource_state) {
        // FIXME: Task #14
        int[] resource_copy = Arrays.copyOf(resource_state, resource_state.length);
        String[] path = pathTo(target_structure, board_state);
        String pathString = "";
        for (int i = 0; i < path.length; i++) {
            pathString += path[i] + ",";
        }
        if (!Pattern.matches("^R(\\d|1[0-5])$", target_structure)) {
            int[] preconditionJoker = new int[] {1, 2, 3, 4, 5, 6};
            int[] preconditionSettlement = new int[] {3, 4, 5, 7, 9, 11};
            int[] preconditionCity = new int[] {7, 12, 20, 30};

            if (Pattern.matches("^J([1-6])$", target_structure)) {
                for (var each : preconditionJoker) {
                    String precondition = "";
                    precondition += target_structure.substring(0, 1) + each;
                    if (!board_state.contains(precondition) && !board_state.contains("K" + each) && each < Integer.parseInt(target_structure.substring(1))) {
                        pathString += precondition + ",";
                    }
                }
            }
            else if (Pattern.matches("^S([3-5]|7|9|11)$", target_structure)) {
                for (var each : preconditionSettlement) {
                    String precondition = "";
                    precondition += target_structure.substring(0, 1) + each;
                    if (!board_state.contains(precondition) && each < Integer.parseInt(target_structure.substring(1))) {
                        pathString += precondition + ",";
                    }
                }
            }
            else if (Pattern.matches("^C(7|12|[2-3]0)$", target_structure)) {
                for (var each : preconditionCity) {
                    String precondition = "";
                    precondition += target_structure.substring(0, 1) + each;
                    if (!board_state.contains(precondition) && each < Integer.parseInt(target_structure.substring(1))) {
                        pathString += precondition + ",";
                    }
                }
            }
        }
        pathString += target_structure;
        String[] pathAndTarget = pathString.split(",");
        String plan = "";
        for (int i = 0; i < pathAndTarget.length; i++) {
            if (checkResources(pathAndTarget[i], resource_copy)) {
                plan += "build " + pathAndTarget[i] + ",";
                resource_copy = fakeBuild(pathAndTarget[i], resource_copy);
                board_state += "," + pathAndTarget[i];
            }
            else {
                int[] resource_demand = Arrays.copyOf(resource_copy, resource_copy.length);
                if (Pattern.matches("^R(\\d|1[0-5])$", pathAndTarget[i])) {
                    resource_demand[3] -= 1;
                    resource_demand[4] -= 1;
                }
                else if (Pattern.matches("^J([1-6])$", pathAndTarget[i])) {
                    resource_demand[0] -= 1;
                    resource_demand[1] -= 1;
                    resource_demand[2] -= 1;
                }
                else if (Pattern.matches("^S([3-5]|7|9|11)$", pathAndTarget[i])) {
                    resource_demand[1] -= 1;
                    resource_demand[2] -= 1;
                    resource_demand[3] -= 1;
                    resource_demand[4] -= 1;
                }
                else if (Pattern.matches("^C(7|12|[2-3]0)$", pathAndTarget[i])) {
                    resource_demand[0] -= 3;
                    resource_demand[1] -= 2;
                }

                if (Arrays.stream(Arrays.copyOf(resource_demand,
                        resource_demand.length)).sum() +
                        (resource_demand[5] / 2) < 0) {
                    return null;
                }
                else {
                    for (int j = 0; j < resource_demand.length - 1; j++) {
                        if (resource_demand[j] < 0) {
                            if (board_state.contains("J" + (j + 1)) && Arrays.stream(resource_demand).sum() >= 0) {
                                resource_demand[j] += 1;
                                resource_copy[j] += 1;
                                board_state = board_state.replace("J" + (j + 1), "K" + (j + 1));
                                //TODO
                                for (int k = 0; k < resource_demand.length; k++) {
                                    if (resource_demand[k] > 0) {
                                        resource_demand[k]--;
                                        resource_copy[k]--;
                                        plan += "swap " + k + " " + j + ",";
                                        break;
                                    }
                                }
                            }

                        }
                        if (resource_demand[j] < 0) {
                            if (board_state.contains("J6") && Arrays.stream(resource_demand).sum() >= 0) {
                                resource_demand[j] += 1;
                                resource_copy[j] += 1;
                                board_state = board_state.replace("J6", "K6");
                                //TODO
                                for (int k = 0; k < resource_demand.length; k++) {
                                    if (resource_demand[k] > 0) {
                                        resource_demand[k]--;
                                        resource_copy[k]--;
                                        plan += "swap " + k + " " + j + ",";
                                        break;
                                    }
                                }
                            }

                        }
                        if (resource_demand[j] < 0) {
                            if (resource_demand[5] / 2 > 0) {
                                resource_demand[j] += 1;
                                resource_demand[5] -= 2;
                                resource_copy[j] += 1;
                                resource_copy[5] -= 2;
                                plan += "trade " + j + ",";
                            }

                        }
                    }
                    for (var each : resource_demand) {
                        if (each < 0) {
                            return null;
                        }
                    }
                    plan += "build " + pathAndTarget[i] + ",";
                    resource_copy = fakeBuild(pathAndTarget[i], resource_copy);
                    board_state += "," + pathAndTarget[i];
                }
            }
        }
        return plan.split(",");
    }

    public static int[] fakeBuild(String target_structure, int[] resource_state) {
        if (Pattern.matches("^R(\\d|1[0-5])$", target_structure)) {
            resource_state[3] -= 1;
            resource_state[4] -= 1;
        }
        else if (Pattern.matches("^J([1-6])$", target_structure)) {
            resource_state[0] -= 1;
            resource_state[1] -= 1;
            resource_state[2] -= 1;
        }
        else if (Pattern.matches("^S([3-5]|7|9|11)$", target_structure)) {
            resource_state[1] -= 1;
            resource_state[2] -= 1;
            resource_state[3] -= 1;
            resource_state[4] -= 1;
        }
        else if (Pattern.matches("^C(7|12|[2-3]0)$", target_structure)) {
            resource_state[0] -= 3;
            resource_state[1] -= 2;
        }
        return resource_state;
    }
}
