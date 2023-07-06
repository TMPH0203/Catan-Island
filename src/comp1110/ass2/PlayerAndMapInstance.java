package comp1110.ass2;

import java.util.ArrayList;
import java.util.List;

public class PlayerAndMapInstance {
    public static List<ConstructionPane> humanConstructionPaneList = new ArrayList<>();
    public static List<ConstructionPane> aiConstructionPaneList = new ArrayList<>();
    public static Player humanPlayer = new Player(PlayerAndMapInstance.humanConstructionPaneList);
    public static Player.AIPlayer aiPlayer = new Player.AIPlayer(PlayerAndMapInstance.aiConstructionPaneList);

    /**
     * The code here is adding the map element of both Human player and AI player.
     */
    static{
        humanConstructionPaneList.add(new ConstructionPane(771, 274, 120, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 0, false)));
        humanConstructionPaneList.add(new ConstructionPane(623, 331, 0, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 1, false)));
        humanConstructionPaneList.add(new ConstructionPane(734, 355.02, 60, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 2, false)));
        humanConstructionPaneList.add(new ConstructionPane(771.1, 494, 120, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 3, false)));
        humanConstructionPaneList.add(new ConstructionPane(623, 545, 0, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 4, false)));
        humanConstructionPaneList.add(new ConstructionPane(737.6, 566.12, 60, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 5, false)));
        humanConstructionPaneList.add(new ConstructionPane(816, 649, 0, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 6, false)));
        humanConstructionPaneList.add(new ConstructionPane(961, 596, 120, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 7, false)));
        humanConstructionPaneList.add(new ConstructionPane(927.1, 459, 60, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 8, false)));
        humanConstructionPaneList.add(new ConstructionPane(958.3, 379.4, 120, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 9, false)));
        humanConstructionPaneList.add(new ConstructionPane(926.72, 241, 60, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 10, false)));
        humanConstructionPaneList.add(new ConstructionPane(962, 158, 120, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 11, false)));
        humanConstructionPaneList.add(new ConstructionPane(1005, 542.1, 0, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 12, false)));
        humanConstructionPaneList.add(new ConstructionPane(1142, 487, 120, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 13, false)));
        humanConstructionPaneList.add(new ConstructionPane(1110, 350, 60, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 14, false)));
        humanConstructionPaneList.add(new ConstructionPane(1145, 269, 120, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 15, false)));
        humanConstructionPaneList.add(new ConstructionPane(1144.94, 270.5, 120, 68.57, 25,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.ROAD, 15, false)));

        humanConstructionPaneList.add(new ConstructionPane(639, 144, 0, 37, 59.65,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.JOKER, 1, false)));
        humanConstructionPaneList.add(new ConstructionPane(641, 360, 0, 35.71, 58.56,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.JOKER, 2, false)));
        humanConstructionPaneList.add(new ConstructionPane(827, 468, 0, 36, 59.29,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.JOKER, 3, false)));
        humanConstructionPaneList.add(new ConstructionPane(1014, 362, 0, 37.73, 58,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.JOKER, 4, false)));
        humanConstructionPaneList.add(new ConstructionPane(1013, 148, 0, 36.15, 56,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.JOKER, 5, false)));
        humanConstructionPaneList.add(new ConstructionPane(827.4, 32, 0, 36, 59.04,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.JOKER, 6, false)));

        humanConstructionPaneList.add(new ConstructionPane(756, 201.27, 0, 39.07, 49.73,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.SETTLEMENT, 3, false)));
        humanConstructionPaneList.add(new ConstructionPane(756, 426, 0, 39, 49.64,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.SETTLEMENT, 4, false)));
        humanConstructionPaneList.add(new ConstructionPane(764, 635, 0, 40, 50.91,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.SETTLEMENT, 5, false)));
        humanConstructionPaneList.add(new ConstructionPane(952, 531, 0, 38.93, 49.55,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.SETTLEMENT, 7, false)));
        humanConstructionPaneList.add(new ConstructionPane(943, 314, 0, 39.29, 50.72,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.SETTLEMENT, 9, false)));
        humanConstructionPaneList.add(new ConstructionPane(948, 93, 0, 40, 51.64,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.SETTLEMENT, 11, false)));

        humanConstructionPaneList.add(new ConstructionPane(557, 311, 0, 55, 49.29,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.CITY, 7, false)));
        humanConstructionPaneList.add(new ConstructionPane(557, 528, 0, 55, 49,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.CITY, 12, false)));
        humanConstructionPaneList.add(new ConstructionPane(1113, 417, 0, 61.26, 48.59,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.CITY, 20, false)));
        humanConstructionPaneList.add(new ConstructionPane(1118, 200.77, 0, 56.78, 50.23,
                "file:assets/BuildTips/Blank.png", humanPlayer, new Construction(ConstructionType.CITY, 30, false)));

        humanPlayer.constructionPaneList = humanConstructionPaneList;
        humanPlayer.updateBuildState();

        aiConstructionPaneList.add(new ConstructionPane(701, 259, 30, 40, 20,
                "file:assets/BuildTips/Blank.png", new Construction(ConstructionType.ROAD, 0, false)));
        aiConstructionPaneList.add(new ConstructionPane(670, 290, 90, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 1, false)));
        aiConstructionPaneList.add(new ConstructionPane(690, 410, -30, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 2, false)));
        aiConstructionPaneList.add(new ConstructionPane(701, 478, 30, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 3, false)));
        aiConstructionPaneList.add(new ConstructionPane(669, 504, 90, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 4, false)));
        aiConstructionPaneList.add(new ConstructionPane(798, 584, 150, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 5, false)));
        aiConstructionPaneList.add(new ConstructionPane(859, 607, 90, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 6, false)));
        aiConstructionPaneList.add(new ConstructionPane(890, 582, 30, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 7, false)));
        aiConstructionPaneList.add(new ConstructionPane(881, 511, -30, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 8, false)));
        aiConstructionPaneList.add(new ConstructionPane(886, 364, 30, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 9, false)));
        aiConstructionPaneList.add(new ConstructionPane(880, 298, -30, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 10, false)));
        aiConstructionPaneList.add(new ConstructionPane(890, 147, 30, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 11, false)));
        aiConstructionPaneList.add(new ConstructionPane(1048, 499, 90, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 12, false)));
        aiConstructionPaneList.add(new ConstructionPane(1071, 472, 30, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 13, false)));
        aiConstructionPaneList.add(new ConstructionPane(1061, 404, -30, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 14, false)));
        aiConstructionPaneList.add(new ConstructionPane(1072, 257, 30, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.ROAD, 15, false)));

        aiConstructionPaneList.add(new ConstructionPane(669, 103, 90, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.JOKER, 1, false)));
        aiConstructionPaneList.add(new ConstructionPane(594, 377, 0, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.JOKER, 2, false)));
        aiConstructionPaneList.add(new ConstructionPane(854, 425, 90, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.JOKER, 3, false)));
        aiConstructionPaneList.add(new ConstructionPane(1042, 319, 90, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.JOKER, 4, false)));
        aiConstructionPaneList.add(new ConstructionPane(1040, 106, 90, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.JOKER, 5, false)));
        aiConstructionPaneList.add(new ConstructionPane(781, 53, 0, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.JOKER, 6, false)));

        aiConstructionPaneList.add(new ConstructionPane(716, 220, 0, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.SETTLEMENT, 3, false)));
        aiConstructionPaneList.add(new ConstructionPane(715, 441, 0, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.SETTLEMENT, 4, false)));
        aiConstructionPaneList.add(new ConstructionPane(721, 644, 0, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.SETTLEMENT, 5, false)));
        aiConstructionPaneList.add(new ConstructionPane(905, 545, 0, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.SETTLEMENT, 7, false)));
        aiConstructionPaneList.add(new ConstructionPane(899, 328, 0, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.SETTLEMENT, 9, false)));
        aiConstructionPaneList.add(new ConstructionPane(902, 109, 0, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.SETTLEMENT, 11, false)));

        aiConstructionPaneList.add(new ConstructionPane(583, 282, 90, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.CITY, 7, false)));
        aiConstructionPaneList.add(new ConstructionPane(583, 496, 90, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.CITY, 12, false)));
        aiConstructionPaneList.add(new ConstructionPane(1072, 440, 0, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.CITY, 20, false)));
        aiConstructionPaneList.add(new ConstructionPane(1072, 221, 0, 40, 20,
                "file:assets/BuildTips/Blank.png",  new Construction(ConstructionType.CITY, 30, false)));


        aiPlayer.constructionPaneList = aiConstructionPaneList;
        aiPlayer.updateBuildState();
    }
}
