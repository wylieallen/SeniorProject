package gameview.drawstate;

import Model.Items.Consumables.HealthConsumable;
import Model.Items.Consumables.ShieldConsumable;
import Model.Items.Inventory;
import Model.Items.Item;
import Model.Items.RandomItemGenerator;
import Model.Map.Node;
import Model.Map.Overworld;
import Model.Map.Zones.BattleZone;
import Model.Map.Zones.TradingZone;
import Model.Map.Zones.Zone;
import Model.Pilot.Pilot;
import Model.Pilot.Player;
import Model.Ship.Ship;
import Model.Ship.ShipBuilder.ShipBuilder;
import Model.Ship.ShipParts.*;
import Model.Ship.ShipParts.Projectile.LinearProjectile;
import Model.Ship.ShipParts.SpecialType.BoostSpecial;
import Model.Ship.ShipParts.WeaponType.EnergyWeapon;
import Model.TradingPost.BountyMission;
import Model.TradingPost.TradingPost;
import Model.TradingPost.Wallet;
import Utility.Rarity;
import guiframework.Uberstate;
import guiframework.control.ClickableControlstate;
import guiframework.gui2d.Drawstate;
import guiframework.gui2d.ImageFactory;
import guiframework.control.clickable.Button;
import guiframework.control.clickable.ItemButton;
import guiframework.control.clickable.Overlay;
import guiframework.gui2d.displayable.Displayable;
import guiframework.gui2d.displayable.ImageDisplayable;
import guiframework.gui2d.displayable.StringDisplayable;
import guiframework.gui3d.Renderstate;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OverworldUberstate extends Uberstate
{
    private static final Font font = new Font("Arvo", Font.PLAIN, 30);
    private static final int HEIGHT = 1000;
    private static final int WIDTH = 1750;
    private static final int MARGIN = 10;
    //todo: might change items per page
    private static final int ITEMSPERPAGE = 10;
    private static final int SKILLCAP = 25;
    private int selectedShip = 0;
    private int selectedPart = 0;
    private int partsCount = 0;
    private List<Button> playerItems;
    private Overworld overworld;
    private Overlay mapOverlay;
    private Button skillsMenu;
    private ImageDisplayable nodeInfo;
    private Overlay selectedNode;
    private Overlay selectedShipOverlay;
    private Overlay selectedPartOverlay;
    private ImageDisplayable currentEngineImage;
    private ImageDisplayable currentShieldImage;
    private ImageDisplayable currentSpecialImage;
    private ImageDisplayable currentWeaponImage;
    private TradingPost currentTP;
    private Player currentPlayer;

    public OverworldUberstate(Renderstate renderstate) {
        super(new Drawstate(), renderstate, new ClickableControlstate());

        TradingPost tp1 = new TradingPost(new Inventory(20), new Wallet(500), new ArrayList<BountyMission>());
        overworld = Overworld.getOverworld();
        overworld.addNode(new Node(new BattleZone(1)));
        overworld.addNode(new Node(new BattleZone(2)));
        overworld.addNode(new Node(new BattleZone(3)));
        overworld.addNode(new Node(new TradingZone(tp1)));
        overworld.addNode(new Node(new BattleZone(4)));

        currentPlayer = new Player();
        currentPlayer.getPilotStats().levelUp();
        currentPlayer.getPilotStats().levelUp();
        currentPlayer.getPilotStats().levelUp();
//        Ship ship1 = new Ship(currentPlayer, new ShipHull(100, Rarity.COMMON, 500, 40));
//        ship1.equipEngine(new ShipEngine(100,100, Rarity.COMMON));
//        ship1.equipShield(new ShipShield(100,100, Rarity.COMMON));
//        ship1.equipSpecial(new BoostSpecial(100, 100,100,100, Rarity.COMMON));
//        ship1.getWeaponSlot1(new EnergyWeapon(100, new LinearProjectile(currentPlayer,)));
        ShipBuilder buildShip = new ShipBuilder();
        Ship ship1 = buildShip.buildRandomShip(currentPlayer, Rarity.COMMON);
        Ship ship2 = buildShip.buildRandomShip(currentPlayer, Rarity.RARE);
        Ship ship3 = buildShip.buildRandomShip(currentPlayer, Rarity.EPIC);
        Ship ship4 = buildShip.buildRandomShip(currentPlayer, Rarity.LEGENDARY);
        currentPlayer.getShipHangar().addShip(ship1);
        currentPlayer.getShipHangar().addShip(ship2);
        currentPlayer.getShipHangar().addShip(ship3);
        currentPlayer.getShipHangar().addShip(ship4);
        currentPlayer.setActiveShip(ship1);

        RandomItemGenerator RIG = new RandomItemGenerator();
        currentPlayer.getActiveShip().getInventory().addItem(new HealthConsumable(100,20));
        currentPlayer.getActiveShip().getInventory().addItem(new ShieldConsumable(200,50));
        currentPlayer.getActiveShip().getInventory().addItem(new HealthConsumable(100,20));
        currentPlayer.getActiveShip().getInventory().addItem(new ShieldConsumable(200,50));
        currentPlayer.getActiveShip().getInventory().addItem(new ShipEngine(100,100, Rarity.COMMON));
        currentPlayer.getActiveShip().getInventory().addItem(new ShipShield(100,100, Rarity.COMMON));
        currentPlayer.getActiveShip().getInventory().addItem(new ShipEngine(100,100, Rarity.LEGENDARY));
        currentPlayer.getActiveShip().getInventory().addItem(new ShipShield(100,100, Rarity.LEGENDARY));
        currentPlayer.getActiveShip().getInventory().addItem(new ShipEngine(100,100, Rarity.COMMON));
//        currentPlayer.getActiveShip().getInventory().addItem(new ShipShield(100,100, Rarity.LEGENDARY));
//        currentPlayer.getActiveShip().getInventory().addItem(new ShipEngine(100,100, Rarity.COMMON));
//        currentPlayer.getActiveShip().getInventory().addItem(new ShipShield(100,100, Rarity.LEGENDARY));
//        currentPlayer.getActiveShip().getInventory().addItem(new ShipEngine(100,100, Rarity.COMMON));
//        currentPlayer.getActiveShip().getInventory().addItem(new ShipShield(100,100, Rarity.LEGENDARY));
//        currentPlayer.getActiveShip().getInventory().addItem(new ShipEngine(100,100, Rarity.COMMON));
//        currentPlayer.getActiveShip().getInventory().addItem(new ShipShield(100,100, Rarity.LEGENDARY));
//        currentPlayer.getActiveShip().getInventory().addItem(new ShipEngine(100,100, Rarity.COMMON));
//        currentPlayer.getActiveShip().getInventory().addItem(new ShipShield(100,100, Rarity.LEGENDARY));
//        currentPlayer.getActiveShip().getInventory().addItem(new ShipEngine(100,100, Rarity.COMMON));
        for(int i =0; i < 10; i++) {
            ship1.getInventory().addItem(RIG.getRandomItem());
            ship2.getInventory().addItem(RIG.getRandomItem());
            ship3.getInventory().addItem(RIG.getRandomItem());
            ship4.getInventory().addItem(RIG.getRandomItem());
        }

        playerItems = new ArrayList<Button>();

        Drawstate drawstate = getDrawstate();
        ClickableControlstate controlstate = getControlstate();

        //Add space background
        ImageDisplayable spaceBackground =
                new ImageDisplayable(new Point(0,0), ImageFactory.getSpaceBackground());
        drawstate.addUnderlay(spaceBackground);

        mapOverlay = new Overlay(new Point(0, 0));
//        ImageDisplayable mapBackground = new ImageDisplayable(new Point(0,0),
//                //ImageFactory.makeBorderedRect(WIDTH, 900, Color.BLACK, Color.WHITE));
//                ImageFactory.getSpaceBackground());
//        mapOverlay.add(mapBackground);

        //Add title box
        ImageDisplayable overworldTitle =
                new ImageDisplayable(new Point(WIDTH/2 - (ImageFactory.getOverworldLabel().getWidth()/2),25), ImageFactory.getOverworldLabel());
        //mapOverlay.add(overworldTitle);


        for(int i = 0; i < overworld.getNodes().size(); i++) {
            Node node = overworld.getNode(i);
            int x = MARGIN*12 + (WIDTH/overworld.getNodes().size())*i;
            int y = (HEIGHT/2)-125;
            Button nodeButton = new Button(new Point(x, y),
                    ImageFactory.getNodeButton(),
                    ImageFactory.getNodeButton(),
                    ImageFactory.getNodeButton(),
                    () -> {
                        mapOverlay.remove(selectedNode);
                        mapOverlay.removeClickable(selectedNode);
                        selectedNode = new Overlay(new Point(WIDTH/2 - 200,HEIGHT-375));
                        ImageDisplayable snBackground = new ImageDisplayable(new Point(0,0), ImageFactory.makeBorderedRect(400,200, Color.WHITE, Color.GRAY));
                        selectedNode.add(snBackground);

//                        ImageDisplayable nodeDisplay = new ImageDisplayable(new Point(100, 75),
//                                ImageFactory.makeCenterLabeledRect(200, 50, Color.BLACK, Color.GRAY, Color.WHITE, node.getThisZone().getZoneType()));
                        selectedNode.add(new StringDisplayable( new Point(100, 25), () -> "" + node.getThisZone().getZoneType(), Color.BLUE, font));

                        Button travelToNode = new Button(new Point(100,125),
                                ImageFactory.makeCenterLabeledRect(200, 50, Color.BLUE, Color.GRAY, Color.WHITE, "Travel to Node"),
                                ImageFactory.makeCenterLabeledRect(200, 50, Color.GREEN, Color.GRAY, Color.WHITE, "Travel to Node"),
                                ImageFactory.makeCenterLabeledRect(200, 50, Color.YELLOW, Color.GRAY, Color.BLACK, "Travel to Node"),
                                () -> {
                                    System.out.println("Travel to " + node.getThisZone().getZoneType());
                                });
                        selectedNode.add(travelToNode);
                        selectedNode.addClickable(travelToNode);

                        mapOverlay.add(selectedNode);
                        mapOverlay.addClickable(selectedNode);

                    },

                    () -> {

                    },
                    //Hover: Show node info
                    () -> {
                        System.out.println("Hovering over " + node.getThisZone().getZoneType());
                        this.nodeInfo = new ImageDisplayable(new Point(x + 50, y + 50),
                                ImageFactory.makeCenterLabeledRect(200, 50, Color.WHITE, Color.GRAY, Color.BLACK, "" + node.getThisZone().getZoneType()));
                        mapOverlay.add(nodeInfo);
                    },
                    //Exit: Remove node info
                    () -> {
                        mapOverlay.remove(nodeInfo);
                    });
            mapOverlay.add(nodeButton);
            mapOverlay.addClickable(nodeButton);
        }

        drawstate.addLeftOverlay(mapOverlay);
        controlstate.add(mapOverlay);

        //Add Skills Menu button
        skillsMenu = new Button(new Point(1400, 25),
                ImageFactory.getSkillsMenuButton(),
                ImageFactory.getSkillsMenuButton(),
                ImageFactory.getSkillsMenuButton(),
                () -> {
                    //Remove map overlay so it cant be clicked under the stats display
                    mapOverlay.removeClickable(selectedNode);
                    mapOverlay.remove(selectedNode);
//                    mapOverlay.removeClickable(skillsMenu);
//                    mapOverlay.remove(skillsMenu);
                    drawstate.removeOverlay(mapOverlay);
                    controlstate.remove(mapOverlay);
//                    drawstate.removeOverlay(skillsMenu);
//                    controlstate.remove(skillsMenu);

                    Overlay skillsMenuOverlay = new Overlay(new Point(0,0));
//                    ImageDisplayable svBackground = new ImageDisplayable(new Point(0,0),
//                            ImageFactory.makeBorderedRect(600, 900, Color.WHITE, Color.GRAY ));
//                    skillsMenuOverlay.add(svBackground);

                    skillsMenuOverlay.add(new StringDisplayable( new Point(800, 150), () -> "Skill Points: " + currentPlayer.getPilotStats().getCurrentSkillPoints(), Color.GREEN, font));
                    skillsMenuOverlay.add(new StringDisplayable( new Point(100, 300), () -> "Flying: " + currentPlayer.getPilotStats().getFlying(), Color.GREEN, font));
                    skillsMenuOverlay.add(new StringDisplayable( new Point(100, 450), () -> "Combat: " + currentPlayer.getPilotStats().getCombat(), Color.GREEN, font));
                    skillsMenuOverlay.add(new StringDisplayable( new Point(100, 600), () -> "Charisma: " + currentPlayer.getPilotStats().getCharisma(), Color.GREEN, font));

                    //add skill level square images
                    List<ImageDisplayable> flyingLevels = new ArrayList<ImageDisplayable>();
                    List<ImageDisplayable> combatLevels = new ArrayList<ImageDisplayable>();
                    List<ImageDisplayable> charismaLevels = new ArrayList<ImageDisplayable>();

                    for(int i = 0; i < SKILLCAP; i++) {
                        //add flying square
                        ImageDisplayable flySquare = new ImageDisplayable(new Point(300+(i*50),300),
                                ImageFactory.makeBorderedRect(50,50, Color.WHITE, Color.BLACK));

                        if(i < currentPlayer.getPilotStats().getFlying() )
                            flySquare.setImage(ImageFactory.makeBorderedRect(50,50, Color.GREEN, Color.BLACK));

                        skillsMenuOverlay.add(flySquare);
                        flyingLevels.add(flySquare);

                        //add combat square
                        ImageDisplayable combatSquare = new ImageDisplayable(new Point(300+(i*50),450),
                                ImageFactory.makeBorderedRect(50,50, Color.WHITE, Color.BLACK));

                        if(i < currentPlayer.getPilotStats().getCombat() )
                            combatSquare.setImage(ImageFactory.makeBorderedRect(50,50, Color.GREEN, Color.BLACK));

                        skillsMenuOverlay.add(combatSquare);
                        combatLevels.add(combatSquare);

                        //add charisma square
                        ImageDisplayable charismaSquare = new ImageDisplayable(new Point(300+(i*50),600),
                                ImageFactory.makeBorderedRect(50,50, Color.WHITE, Color.BLACK));

                        if(i < currentPlayer.getPilotStats().getCharisma() )
                            charismaSquare.setImage(ImageFactory.makeBorderedRect(50,50, Color.GREEN, Color.BLACK));

                        skillsMenuOverlay.add(charismaSquare);
                        charismaLevels.add(charismaSquare);
                    }


                    Button increaseFly = new Button(new Point(1600, 300),
                            ImageFactory.getSkillIncreaseButton(),
                            ImageFactory.getSkillIncreaseButton(),
                            ImageFactory.getSkillIncreaseButton(),
                            () -> {
                                currentPlayer.getPilotStats().levelFlying();
                                skillsMenuOverlay.remove(flyingLevels.get(currentPlayer.getPilotStats().getFlying()-1));
                                flyingLevels.get(currentPlayer.getPilotStats().getFlying()-1).setImage(ImageFactory.makeBorderedRect(50,50, Color.GREEN, Color.BLACK));
                                skillsMenuOverlay.add(flyingLevels.get(currentPlayer.getPilotStats().getFlying()-1));
                            });
                    skillsMenuOverlay.add(increaseFly);
                    skillsMenuOverlay.addClickable(increaseFly);

                    Button increaseCombat = new Button(new Point(1600, 450),
                            ImageFactory.getSkillIncreaseButton(),
                            ImageFactory.getSkillIncreaseButton(),
                            ImageFactory.getSkillIncreaseButton(),
                            () -> {
                                currentPlayer.getPilotStats().levelCombat();
                                skillsMenuOverlay.remove(combatLevels.get(currentPlayer.getPilotStats().getCombat()-1));
                                combatLevels.get(currentPlayer.getPilotStats().getCombat()-1).setImage(ImageFactory.makeBorderedRect(50,50, Color.GREEN, Color.BLACK));
                                skillsMenuOverlay.add(combatLevels.get(currentPlayer.getPilotStats().getCombat()-1));
                            });
                    skillsMenuOverlay.add(increaseCombat);
                    skillsMenuOverlay.addClickable(increaseCombat);

                    Button increaseCharisma = new Button(new Point(1600, 600),
                            ImageFactory.getSkillIncreaseButton(),
                            ImageFactory.getSkillIncreaseButton(),
                            ImageFactory.getSkillIncreaseButton(),
                            () -> {
                                currentPlayer.getPilotStats().levelCharisma();
                                skillsMenuOverlay.remove(charismaLevels.get(currentPlayer.getPilotStats().getCharisma()-1));
                                charismaLevels.get(currentPlayer.getPilotStats().getCharisma()-1).setImage(ImageFactory.makeBorderedRect(50,50, Color.GREEN, Color.BLACK));
                                skillsMenuOverlay.add(charismaLevels.get(currentPlayer.getPilotStats().getCharisma()-1));
                            });
                    skillsMenuOverlay.add(increaseCharisma);
                    skillsMenuOverlay.addClickable(increaseCharisma);

                    //add skill info buttons
                    Button flyInfo = new Button(new Point(1675, 300),
                            ImageFactory.getSkillInfoButton(),
                            ImageFactory.getSkillInfoButton(),
                            ImageFactory.getSkillInfoButton(),
                            () -> {
                                Overlay flyInfoOverlay = new Overlay(new Point(0,0));
                                ImageDisplayable flyInfoText = new ImageDisplayable(new Point(0,0), ImageFactory.getFlySkillInfo());
                                int widthFIT = flyInfoText.getSize().width;
                                flyInfoText.getOrigin().setLocation((1750/2)-(widthFIT/2), 100);
                                flyInfoOverlay.add(flyInfoText);
                                Button closeFlyInfo = new Button(new Point(0, 0),
                                        ImageFactory.getCloseSkillInfoButton(),
                                        ImageFactory.getCloseSkillInfoButton(),
                                        ImageFactory.getCloseSkillInfoButton(),
                                        () -> {
                                            skillsMenuOverlay.remove(flyInfoOverlay);
                                            skillsMenuOverlay.removeClickable(flyInfoOverlay);
                                        });
                                int widthCFI = closeFlyInfo.getSize().width;
                                closeFlyInfo.getOrigin().setLocation((1750/2)-(widthCFI/2), 700);
                                flyInfoOverlay.add(closeFlyInfo);
                                flyInfoOverlay.addClickable(closeFlyInfo);
                                skillsMenuOverlay.add(flyInfoOverlay);
                                skillsMenuOverlay.addClickable(flyInfoOverlay);
                            });
                    skillsMenuOverlay.add(flyInfo);
                    skillsMenuOverlay.addClickable(flyInfo);

                    Button combatInfo = new Button(new Point(1675, 450),
                            ImageFactory.getSkillInfoButton(),
                            ImageFactory.getSkillInfoButton(),
                            ImageFactory.getSkillInfoButton(),
                            () -> {
                                Overlay combatInfoOverlay = new Overlay(new Point(0,0));
                                ImageDisplayable combatInfoText = new ImageDisplayable(new Point(0,0), ImageFactory.getComSkillInfo());
                                int widthFIT = combatInfoText.getSize().width;
                                combatInfoText.getOrigin().setLocation((1750/2)-(widthFIT/2), 100);
                                combatInfoOverlay.add(combatInfoText);
                                Button closeFlyInfo = new Button(new Point(0, 0),
                                        ImageFactory.getCloseSkillInfoButton(),
                                        ImageFactory.getCloseSkillInfoButton(),
                                        ImageFactory.getCloseSkillInfoButton(),
                                        () -> {
                                            skillsMenuOverlay.remove(combatInfoOverlay);
                                            skillsMenuOverlay.removeClickable(combatInfoOverlay);
                                        });
                                int widthCFI = closeFlyInfo.getSize().width;
                                closeFlyInfo.getOrigin().setLocation((1750/2)-(widthCFI/2), 700);
                                combatInfoOverlay.add(closeFlyInfo);
                                combatInfoOverlay.addClickable(closeFlyInfo);
                                skillsMenuOverlay.add(combatInfoOverlay);
                                skillsMenuOverlay.addClickable(combatInfoOverlay);
                            });
                    skillsMenuOverlay.add(combatInfo);
                    skillsMenuOverlay.addClickable(combatInfo);

                    Button charismaInfo = new Button(new Point(1675, 600),
                            ImageFactory.getSkillInfoButton(),
                            ImageFactory.getSkillInfoButton(),
                            ImageFactory.getSkillInfoButton(),
                            () -> {
                                Overlay charismaInfoOverlay = new Overlay(new Point(0,0));
                                ImageDisplayable charismaInfoText = new ImageDisplayable(new Point(0,0), ImageFactory.getCharSkillInfo());
                                int widthFIT = charismaInfoText.getSize().width;
                                charismaInfoText.getOrigin().setLocation((1750/2)-(widthFIT/2), 100);
                                charismaInfoOverlay.add(charismaInfoText);
                                Button closeFlyInfo = new Button(new Point(0, 0),
                                        ImageFactory.getCloseSkillInfoButton(),
                                        ImageFactory.getCloseSkillInfoButton(),
                                        ImageFactory.getCloseSkillInfoButton(),
                                        () -> {
                                            skillsMenuOverlay.remove(charismaInfoOverlay);
                                            skillsMenuOverlay.removeClickable(charismaInfoOverlay);
                                        });
                                int widthCFI = closeFlyInfo.getSize().width;
                                closeFlyInfo.getOrigin().setLocation((1750/2)-(widthCFI/2), 700);
                                charismaInfoOverlay.add(closeFlyInfo);
                                charismaInfoOverlay.addClickable(closeFlyInfo);
                                skillsMenuOverlay.add(charismaInfoOverlay);
                                skillsMenuOverlay.addClickable(charismaInfoOverlay);


                            });
                    skillsMenuOverlay.add(charismaInfo);
                    skillsMenuOverlay.addClickable(charismaInfo);

                    Button closeSkills = new Button(new Point(1350, 25),
                            ImageFactory.getExitSkillsMenuButton(),
                            ImageFactory.getExitSkillsMenuButton(),
                            ImageFactory.getExitSkillsMenuButton(),
                            () -> {
                                drawstate.removeOverlay(skillsMenuOverlay);
                                controlstate.remove(skillsMenuOverlay);

                                //readd map and upgrade stats and title
                                drawstate.addLeftOverlay(mapOverlay);
                                controlstate.add(mapOverlay);
//                                drawstate.addRightOverlay(skillsMenu);
//                                controlstate.add(skillsMenu);
                            });
                    skillsMenuOverlay.add(closeSkills);
                    skillsMenuOverlay.addClickable(closeSkills);


                    drawstate.addLeftOverlay(skillsMenuOverlay);
                    controlstate.add(skillsMenuOverlay);
                });

        mapOverlay.addClickable(skillsMenu);
        mapOverlay.add(skillsMenu);
//        drawstate.addRightOverlay(skillsMenu);
//        controlstate.add(skillsMenu);

        //Add Hangar button
        Button hangarButton = new Button(new Point(1050, 25),
                ImageFactory.getHangarMenuButton(),
                ImageFactory.getHangarMenuButton(),
                ImageFactory.getHangarMenuButton(),
                () -> {
                    selectedShip = 0;
                    //remove map overlay
                    mapOverlay.removeClickable(selectedNode);
                    mapOverlay.remove(selectedNode);
                    drawstate.removeOverlay(mapOverlay);
                    controlstate.remove(mapOverlay);

                    //create hangar overlay
                    Overlay hangarOverlay = new Overlay(new Point(0,0));

                    //Add exit hangar button
                    Button exitHangar = new Button(new Point(1425, 25),
                            ImageFactory.getExitHangarButton(),
                            ImageFactory.getExitHangarButton(),
                            ImageFactory.getExitHangarButton(),
                            () -> {
                                //remove hangar overlay
                                drawstate.removeOverlay(hangarOverlay);
                                controlstate.remove(hangarOverlay);

                                //readd map overlay
                                drawstate.addLeftOverlay(mapOverlay);
                                controlstate.add(mapOverlay);


                            });
                    hangarOverlay.add(exitHangar);
                    hangarOverlay.addClickable(exitHangar);

                    //add ship images
                    StringDisplayable shipTitle = new StringDisplayable(new Point(0, 0), () -> "Ship " + (selectedShip+1), Color.GREEN, font);
                    int height = shipTitle.getSize().height;
                    int width = shipTitle.getSize().width;
                    shipTitle.getOrigin().setLocation((WIDTH/2)-(width/2), (HEIGHT/2)-250);
                    hangarOverlay.add(shipTitle);
                    selectedShipOverlay = new Overlay(new Point(0,0));
                    if(currentPlayer.getActiveShip() == currentPlayer.getShipHangar().getShipAtIndex(selectedShip)) {
                        StringDisplayable activeShipTitle = new StringDisplayable(new Point(0, 0), () -> "Active Ship" , Color.GREEN, font);
                        int heightAST = activeShipTitle.getSize().height;
                        int widthAST = activeShipTitle.getSize().width;
                        activeShipTitle.getOrigin().setLocation((WIDTH/2)-(widthAST/2), (HEIGHT/2)-200);
                        selectedShipOverlay.add(activeShipTitle);
                        //hangarOverlay.add(activeShipTitle);
                    }
                    ImageDisplayable selectedShipImage = new ImageDisplayable(new Point((WIDTH/2)-75,(HEIGHT/2)-125), currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getShipImageBlack());
                    selectedShipOverlay.add(selectedShipImage);
                    hangarOverlay.add(selectedShipOverlay);

                    //add arrow buttons
                    Button leftArrow = new Button(new Point((WIDTH/2)-300, (HEIGHT/2)-125),
                            ImageFactory.getArrowLeft(),
                            ImageFactory.getArrowLeft(),
                            ImageFactory.getArrowLeft(),
                            () -> {
                                if(selectedShip > 0) {
                                    selectedShip--;
                                    hangarOverlay.remove(selectedShipOverlay);
                                    selectedShipOverlay = new Overlay(new Point(0,0));
                                    if(currentPlayer.getActiveShip() == currentPlayer.getShipHangar().getShipAtIndex(selectedShip)) {
                                        StringDisplayable activeShipTitle = new StringDisplayable(new Point(0, 0), () -> "Active Ship" , Color.GREEN, font);
                                        int heightAST = activeShipTitle.getSize().height;
                                        int widthAST = activeShipTitle.getSize().width;
                                        activeShipTitle.getOrigin().setLocation((WIDTH/2)-(widthAST/2), (HEIGHT/2)-200);
                                        selectedShipOverlay.add(activeShipTitle);
                                        //hangarOverlay.add(activeShipTitle);
                                    }
                                    selectedShipImage.setImage(currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getShipImageBlack());
                                    selectedShipOverlay.add(selectedShipImage);
                                    hangarOverlay.add(selectedShipOverlay);
                                }

                            });
                    hangarOverlay.add(leftArrow);
                    hangarOverlay.addClickable(leftArrow);

                    Button rightArrow = new Button(new Point((WIDTH/2)+125, (HEIGHT/2)-125),
                            ImageFactory.getArrowRight(),
                            ImageFactory.getArrowRight(),
                            ImageFactory.getArrowRight(),
                            () -> {
                                if(selectedShip < currentPlayer.getShipHangar().hangarSize()-1) {
                                    selectedShip++;
                                    hangarOverlay.remove(selectedShipOverlay);
                                    selectedShipOverlay = new Overlay(new Point(0,0));
                                    if(currentPlayer.getActiveShip() == currentPlayer.getShipHangar().getShipAtIndex(selectedShip)) {
                                        StringDisplayable activeShipTitle = new StringDisplayable(new Point(0, 0), () -> "Active Ship" , Color.GREEN, font);
                                        int heightAST = activeShipTitle.getSize().height;
                                        int widthAST = activeShipTitle.getSize().width;
                                        activeShipTitle.getOrigin().setLocation((WIDTH/2)-(widthAST/2), (HEIGHT/2)-200);
                                        selectedShipOverlay.add(activeShipTitle);
                                        //hangarOverlay.add(activeShipTitle);
                                    }
                                    selectedShipImage.setImage(currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getShipImageBlack());
                                    selectedShipOverlay.add(selectedShipImage);
                                    hangarOverlay.add(selectedShipOverlay);
                                }
                            });
                    hangarOverlay.add(rightArrow);
                    hangarOverlay.addClickable(rightArrow);

                    //add other buttons
                    Button setActiveShip = new Button(new Point((WIDTH/2)-225, (HEIGHT/2)+80),
                            ImageFactory.getSetShipbutton(),
                            ImageFactory.getSetShipbutton(),
                            ImageFactory.getSetShipbutton(),
                            () -> {
                                //If selected ship isnt active ship, make it active ship
                                if(currentPlayer.getActiveShip() != currentPlayer.getShipHangar().getShipAtIndex(selectedShip)) {
                                    currentPlayer.setActiveShip(currentPlayer.getShipHangar().getShipAtIndex(selectedShip));

                                    //Add active ship title
                                    StringDisplayable activeShipTitle = new StringDisplayable(new Point(0, 0), () -> "Active Ship" , Color.GREEN, font);
                                    int heightAST = activeShipTitle.getSize().height;
                                    int widthAST = activeShipTitle.getSize().width;
                                    activeShipTitle.getOrigin().setLocation((WIDTH/2)-(widthAST/2), (HEIGHT/2)-200);
                                    selectedShipOverlay.add(activeShipTitle);
                                }
                            });
                    hangarOverlay.add(setActiveShip);
                    hangarOverlay.addClickable(setActiveShip);

                    Button changeParts = new Button(new Point((WIDTH/2)-225, (HEIGHT/2)+170),
                            ImageFactory.getChangePartsButton(),
                            ImageFactory.getChangePartsButton(),
                            ImageFactory.getChangePartsButton(),
                            () -> {
                                //remove hangar overlay and add changeParts overlay
                                drawstate.removeOverlay(hangarOverlay);
                                controlstate.remove(hangarOverlay);

                                //clear buttons list and parts count
//                                for(int j = 0; j < playerItems.size(); j++) {
//                                    Button pButton = playerItems.get(j);
//                                    changePartsOverlay.remove(pButton);
//                                    changePartsOverlay.removeClickable(pButton);
//                                }
                                playerItems.clear();
                                partsCount = 0;
                                selectedPart = 0;

                                Overlay changePartsOverlay = new Overlay(new Point(0,0));

                                //Add current ship image and title
                                StringDisplayable partsShipTitle = new StringDisplayable(new Point(0, 0), () -> "Ship " + (selectedShip+1), Color.GREEN, font);
                                int widthPST = partsShipTitle.getSize().width;
                                int heightPST = partsShipTitle.getSize().height;
                                partsShipTitle.getOrigin().setLocation(180-(widthPST/2),0);
                                changePartsOverlay.add(partsShipTitle);
                                changePartsOverlay.add(new ImageDisplayable(new Point(180-75,100), currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getShipImageBlack()));

                                //Add current ship stats strings
                                StringDisplayable maxHealth = new StringDisplayable(new Point(0,0), () -> "Max Health: " + currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getShipStats().getMaxHealth(), Color.GREEN, font);
                                int widthMH = maxHealth.getSize().width;
                                int heightMH = maxHealth.getSize().height;
                                maxHealth.getOrigin().setLocation(180-(widthMH/2),300);
                                changePartsOverlay.add(maxHealth);
                                StringDisplayable maxSpeed = new StringDisplayable(new Point(0,0), () -> "Max Speed: " + currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getShipStats().getMaxSpeed(),Color.GREEN, font);
                                int widthMS = maxSpeed.getSize().width;
                                int heightMS = maxSpeed.getSize().height;
                                maxSpeed.getOrigin().setLocation(180-(widthMS/2),400);
                                changePartsOverlay.add(maxSpeed);
                                StringDisplayable maxShield = new StringDisplayable(new Point(0,0), () -> "Max Shield: " + currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getShipStats().getMaxShield(),Color.GREEN, font);
                                int widthMSH = maxShield.getSize().width;
                                int heightMSH = maxShield.getSize().height;
                                maxShield.getOrigin().setLocation(180-(widthMSH/2),500);
                                changePartsOverlay.add(maxShield);
                                StringDisplayable maxFuel = new StringDisplayable(new Point(0,0), () -> "Max Fuel: " + currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getShipStats().getMaxFuel(),Color.GREEN, font);
                                int widthMF = maxFuel.getSize().width;
                                int heightMF = maxFuel.getSize().height;
                                maxFuel.getOrigin().setLocation(180-(widthMF/2),600);
                                changePartsOverlay.add(maxFuel);
                                StringDisplayable damage = new StringDisplayable(new Point(0,0), () -> "Damage: " + currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getWeaponSlot1().getProjectile().getDamage(),Color.GREEN, font);
                                int widthDA = damage.getSize().width;
                                int heightDA = damage.getSize().height;
                                damage.getOrigin().setLocation(180-(widthDA/2),700);
                                changePartsOverlay.add(damage);

                                //add divider lines and current ship part images
                                ImageDisplayable dividerLineLeft = new ImageDisplayable(new Point(400,0), ImageFactory.makeBorderedRect(10,900,Color.GREEN, Color.GREEN));
                                changePartsOverlay.add(dividerLineLeft);

                                //add current ship parts images and titles
                                StringDisplayable currentEngine = new StringDisplayable(new Point(0,0), () -> "Engine",Color.GREEN, font);
                                int widthCE = currentEngine.getSize().width;
                                currentEngine.getOrigin().setLocation(525-(widthCE/2),0);
                                changePartsOverlay.add(currentEngine);
                                currentEngineImage = new ImageDisplayable(new Point(525-75,75), currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getEngineSlot().getImage());
                                changePartsOverlay.add(currentEngineImage);

                                StringDisplayable currentShield = new StringDisplayable(new Point(0,0), () -> "Shield",Color.GREEN, font);
                                int widthCS = currentShield.getSize().width;
                                currentShield.getOrigin().setLocation(525-(widthCS/2),225);
                                changePartsOverlay.add(currentShield);
                                currentShieldImage = new ImageDisplayable(new Point(525-75,300), currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getShieldSlot().getImage());
                                changePartsOverlay.add(currentShieldImage);

                                StringDisplayable currentSpecial = new StringDisplayable(new Point(0,0), () -> "Special",Color.GREEN, font);
                                int widthCSP = currentSpecial.getSize().width;
                                currentSpecial.getOrigin().setLocation(525-(widthCSP/2),450);
                                changePartsOverlay.add(currentSpecial);
                                currentSpecialImage = new ImageDisplayable(new Point(525-75,525), currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getSpecialSlot().getImage());
                                changePartsOverlay.add(currentSpecialImage);

                                StringDisplayable currentWeapon = new StringDisplayable(new Point(0,0), () -> "Weapon",Color.GREEN, font);
                                int widthCW = currentWeapon.getSize().width;
                                currentWeapon.getOrigin().setLocation(525-(widthCW/2),675);
                                changePartsOverlay.add(currentWeapon);
                                currentWeaponImage = new ImageDisplayable(new Point(525-75,750), currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getWeaponSlot1().getImage());
                                changePartsOverlay.add(currentWeaponImage);

                                ImageDisplayable dividerLineRight = new ImageDisplayable(new Point(650,0), ImageFactory.makeBorderedRect(10,900,Color.GREEN, Color.GREEN));
                                changePartsOverlay.add(dividerLineRight);

                                //Add player inventory displayables
                                Inventory playerInventory = currentPlayer.getActiveShip().getInventory();
                                StringDisplayable inventoryTitle = new StringDisplayable(new Point(0,0),() ->"Player Inventory: " + playerInventory.getcurrItemsNum() + "Items",Color.GREEN, font );
                                int widthIT = inventoryTitle.getSize().width;
                                inventoryTitle.getOrigin().setLocation((650)+((WIDTH-650)/2)-(widthIT/2), 0);
                                changePartsOverlay.add(inventoryTitle);


                                System.out.println(selectedPart);
                                selectedPartOverlay = new Overlay(new Point(0, 0));
                                ImageDisplayable spBackground = new ImageDisplayable(new Point(0, 0),
                                        ImageFactory.makeBorderedRect(200, 165, Color.GREEN, Color.GREEN));
                                ImageDisplayable itemName = new ImageDisplayable(new Point(0, 0),
                                        ImageFactory.makeCenterLabeledRect(200, 55, Color.GREEN, Color.GREEN, Color.BLACK, ""));
                                ImageDisplayable itemInfo = new ImageDisplayable(new Point(0, 55),
                                        ImageFactory.makeCenterLabeledRect(200, 55, Color.GREEN, Color.GREEN, Color.BLACK, ""));
//                                ImageDisplayable spBackground = new ImageDisplayable(new Point(0, 0),
//                                        ImageFactory.makeBorderedRect(200, 165, Color.WHITE, Color.GREEN));
//                                ImageDisplayable itemName = new ImageDisplayable(new Point(0, 0),
//                                        ImageFactory.makeCenterLabeledRect(200, 55, Color.GREEN, Color.GREEN, Color.BLACK, playerInventory.getItem(selectedPart).getName()));
//                                ImageDisplayable itemInfo = new ImageDisplayable(new Point(0, 55),
//                                        ImageFactory.makeCenterLabeledRect(200, 55, Color.GREEN, Color.GREEN, Color.BLACK, playerInventory.getItem(selectedPart).getAttributes().get(1)));
                                selectedPartOverlay.add(spBackground);
                                selectedPartOverlay.add(itemName);
                                selectedPartOverlay.add(itemInfo);


                                Button equipPart = new Button(new Point(0, 110),
                                        ImageFactory.makeCenterLabeledRect(200, 55, Color.GREEN, Color.GREEN, Color.BLACK, "Equip"),
                                        ImageFactory.makeCenterLabeledRect(200, 55, Color.GREEN, Color.GREEN, Color.WHITE, "Equip"),
                                        ImageFactory.makeCenterLabeledRect(200, 55, Color.GREEN, Color.GREEN, Color.BLACK, "Equip"),
                                        () -> {
                                            System.out.println("Equip Button pressed!");
                                            Item partEquip = playerInventory.getItem(selectedPart);
                                            //Equip part
                                            //Note: This also removes part from inventory and puts unequiped part into inventory
                                            partEquip.equip(currentPlayer.getShipHangar().getShipAtIndex(selectedShip));

                                            changePartsOverlay.remove(selectedPartOverlay);
                                            changePartsOverlay.removeClickable(selectedPartOverlay);

                                            //remove and readd current ship part images
                                            currentEngineImage.setImage(currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getEngineSlot().getImage());
                                            currentShieldImage.setImage(currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getShieldSlot().getImage());
                                            currentSpecialImage.setImage(currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getSpecialSlot().getImage());
                                            currentWeaponImage.setImage(currentPlayer.getShipHangar().getShipAtIndex(selectedShip).getWeaponSlot1().getImage());

                                            //clear buttons and parts count and make new ones
                                            for (int j = 0; j < playerItems.size(); j++) {
                                                Button pButton = playerItems.get(j);
                                                changePartsOverlay.remove(pButton);
                                                changePartsOverlay.removeClickable(pButton);
                                            }
                                            playerItems.clear();
                                            partsCount = 0;

                                            for (int i = 0; i < playerInventory.getcurrItemsNum() && partsCount < ITEMSPERPAGE; i++) {
                                                if (playerInventory.getItem(i).isShipPart()) {
                                                    int x = 700 + (200 * (partsCount % 5)) + ((partsCount % 5) * MARGIN) + MARGIN;
                                                    int y = 150 + (350 * (partsCount / 5));
                                                    ;
                                                    Item part = playerInventory.getItem(i);
                                                    Button partButton = new Button(new Point(x, y),
                                                            part.getImage(),
                                                            part.getImage(),
                                                            part.getImage(),
                                                            () -> {
                                                                changePartsOverlay.remove(selectedPartOverlay);
                                                                changePartsOverlay.removeClickable(selectedPartOverlay);

                                                                selectedPart = playerInventory.getIndex(part);

                                                                selectedPartOverlay.getOrigin().setLocation(x - 25, y + 120);
                                                                spBackground.setImage(ImageFactory.makeBorderedRect(200, 165, Color.GREEN, Color.GREEN));
                                                                itemName.setImage(ImageFactory.makeCenterLabeledRect(200, 55, Color.GREEN, Color.GREEN, Color.BLACK, part.getName()));
                                                                itemInfo.setImage(ImageFactory.makeCenterLabeledRect(200, 55, Color.GREEN, Color.GREEN, Color.BLACK, part.getAttributes().get(1)));

                                                                changePartsOverlay.add(selectedPartOverlay);
                                                                changePartsOverlay.addClickable(selectedPartOverlay);
                                                            });
                                                    changePartsOverlay.add(partButton);
                                                    changePartsOverlay.addClickable(partButton);
                                                    playerItems.add(partButton);
                                                    partsCount++;
                                                }
                                            }
                                        });
                                selectedPartOverlay.add(equipPart);
                                selectedPartOverlay.addClickable(equipPart);


                                for (int i = 0; i < playerInventory.getcurrItemsNum() && partsCount < ITEMSPERPAGE; i++) {
                                    if (playerInventory.getItem(i).isShipPart()) {
                                        int x = 700 + (200 * (partsCount % 5)) + ((partsCount % 5) * MARGIN) + MARGIN;
                                        int y = 150 + (350 * (partsCount / 5));
                                        ;
                                        Item part = playerInventory.getItem(i);
                                        Button partButton = new Button(new Point(x, y),
                                                part.getImage(),
                                                part.getImage(),
                                                part.getImage(),
                                                () -> {
                                                    changePartsOverlay.remove(selectedPartOverlay);
                                                    changePartsOverlay.removeClickable(selectedPartOverlay);

                                                    selectedPart = playerInventory.getIndex(part);

                                                    selectedPartOverlay.getOrigin().setLocation(x - 25, y + 120);
                                                    spBackground.setImage(ImageFactory.makeBorderedRect(200, 165, Color.GREEN, Color.GREEN));
                                                    itemName.setImage(ImageFactory.makeCenterLabeledRect(200, 55, Color.GREEN, Color.GREEN, Color.BLACK, part.getName()));
                                                    itemInfo.setImage(ImageFactory.makeCenterLabeledRect(200, 55, Color.GREEN, Color.GREEN, Color.BLACK, part.getAttributes().get(1)));
//                                                    selectedPartOverlay = new Overlay(new Point(x-25,y+120));
//                                                    ImageDisplayable spBackground = new ImageDisplayable(new Point(0,0),
//                                                            ImageFactory.makeBorderedRect(200, 165,Color.WHITE, Color.GREEN));
//                                                    ImageDisplayable itemName = new ImageDisplayable(new Point(0,0),
//                                                            ImageFactory.makeCenterLabeledRect(200,55,Color.GREEN,Color.GREEN,Color.BLACK, part.getName()));
//                                                    ImageDisplayable itemInfo = new ImageDisplayable(new Point(0,55),
//                                                            ImageFactory.makeCenterLabeledRect(200,55,Color.GREEN,Color.GREEN,Color.BLACK, part.getAttributes().get(1)));
//                                                    Button equipPart = new Button(new Point(0, 110),
//                                                            ImageFactory.makeCenterLabeledRect(200,55,Color.GREEN,Color.WHITE,Color.BLACK, "Equip"),
//                                                            ImageFactory.makeCenterLabeledRect(200,55,Color.GREEN,Color.WHITE,Color.BLACK, "Equip"),
//                                                            ImageFactory.makeCenterLabeledRect(200,55,Color.GREEN,Color.WHITE,Color.BLACK, "Equip"),
//                                                            () -> {
//                                                                //Equip part
//                                                                //Note: This also removes part from inventory and puts unequiped part into inventory
//                                                                part.equip();
//
//                                                                //clear buttons and make new ones
//                                                                for(int j = 0; j < playerItems.size(); j++) {
//                                                                    Button pButton = playerItems.get(j);
//                                                                    changePartsOverlay.remove(pButton);
//                                                                    changePartsOverlay.removeClickable(pButton);
//                                                                }
//                                                                playerItems.clear();
//
//                                                            });
//                                                    selectedPartOverlay.add(spBackground);
//                                                    selectedPartOverlay.add(itemName);
//                                                    selectedPartOverlay.add(itemInfo);
//                                                    selectedPartOverlay.add(equipPart);
//                                                    selectedPartOverlay.addClickable(equipPart);

                                                    changePartsOverlay.add(selectedPartOverlay);
                                                    changePartsOverlay.addClickable(selectedPartOverlay);
                                                });
                                        changePartsOverlay.add(partButton);
                                        changePartsOverlay.addClickable(partButton);
                                        playerItems.add(partButton);
                                        partsCount++;
                                    }
                                }

                                Button backToHangarButton = new Button(new Point(10, HEIGHT-200),
                                        ImageFactory.getBackToHangarButton(),
                                        ImageFactory.getBackToHangarButton(),
                                        ImageFactory.getBackToHangarButton(),
                                        () -> {
                                            //remove changeParts overlay and readd hangar overlay
                                            drawstate.removeOverlay(changePartsOverlay);
                                            controlstate.remove(changePartsOverlay);

                                            drawstate.addCenterOverlay(hangarOverlay);
                                            controlstate.add(hangarOverlay);
                                        });
                                changePartsOverlay.add(backToHangarButton);
                                changePartsOverlay.addClickable(backToHangarButton);


                                drawstate.addLeftOverlay(changePartsOverlay);
                                controlstate.add(changePartsOverlay);
                            });
                    hangarOverlay.add(changeParts);
                    hangarOverlay.addClickable(changeParts);


                    drawstate.addCenterOverlay(hangarOverlay);
                    controlstate.add(hangarOverlay);
                });
        mapOverlay.addClickable(hangarButton);
        mapOverlay.add(hangarButton);

        //Add Exit button
        Button exitButton = new Button(new Point(1400, 800),
                ImageFactory.getExitGameButton(),
                ImageFactory.getExitGameButton(),
                ImageFactory.getExitGameButton(),
                () -> {
                    System.exit(0);
                });
        mapOverlay.addClickable(exitButton);
        mapOverlay.add(exitButton);


        //drawstate.addCenterOverlay(overworldTitle);
    }
}
