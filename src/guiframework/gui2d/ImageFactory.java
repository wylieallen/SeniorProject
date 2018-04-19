package guiframework.gui2d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class ImageFactory
{
    //All state images
    private static final BufferedImage spaceBackground = loadImage("resources/Images/space-background.jpeg");

    //Trading Post Images
    private static final BufferedImage tradingPostLabel = loadImage("resources/Images/tp_title_base.png");
    private static final BufferedImage buyButtonBase = loadImage("resources/Images/buy_button_base_new.png");
    private static final BufferedImage buyButtonHover = loadImage("resources/Images/button_buy-items_hover.png");
    private static final BufferedImage buyButtonPress = loadImage("resources/Images/button_buy-items_press.png");
    private static final BufferedImage sellButtonBase = loadImage("resources/Images/sell_button_base_new.png");
    private static final BufferedImage sellButtonHover = loadImage("resources/Images/button_sell-items_hover.png");
    private static final BufferedImage sellButtonPress = loadImage("resources/Images/button_sell-items_press.png");
    private static final BufferedImage bountyButtonBase = loadImage("resources/Images/bounty_button_base_new.png");
    private static final BufferedImage bountyButtonHover = loadImage("resources/Images/button_bounty-missions_hover.png");
    private static final BufferedImage bountyButtonPress = loadImage("resources/Images/button_bounty-missions_press.png");
    private static final BufferedImage turnInMissionButton = loadImage("resources/Images/turn_in_mission_button.png");
    private static final BufferedImage acceptBountyMissionButton = loadImage("resources/Images/accept_mission_button.png");
    private static final BufferedImage bountyMissionBorderImage = loadImage("resources/Images/bounty_mission_border.png");
    private static final BufferedImage exitButtonBase = loadImage("resources/Images/exit_tp_base_new.png");
    private static final BufferedImage exitButtonHover = loadImage("resources/Images/button_exit_hover.png");
    private static final BufferedImage exitButtonPress = loadImage("resources/Images/button_exit_press.png");

    //Overworld Images
    private static final BufferedImage overworldLabel = loadImage("resources/Images/label_overworld.png");
    private static final BufferedImage hangarMenuButton = loadImage("resources/Images/hangar_menu_button.png");
    private static final BufferedImage skillsMenuButton = loadImage("resources/Images/skills_menu_button_.png");
    private static final BufferedImage exitSkillsMenuButton = loadImage("resources/Images/exit_skills_button.png");
    private static final BufferedImage closeSkillInfoButton = loadImage("resources/Images/close_skill_info_button.png");
    private static final BufferedImage skillInfoButton = loadImage("resources/Images/skill_info_button.png");
    private static final BufferedImage flySkillInfo = loadImage("resources/Images/fly_skill_info.png");
    private static final BufferedImage comSkillInfo = loadImage("resources/Images/com_skill_info.png");
    private static final BufferedImage charSkillInfo = loadImage("resources/Images/char_skill_info.png");
    private static final BufferedImage skillIncreaseButton = loadImage("resources/Images/skill_up_button.png");
    private static final BufferedImage exitGameButton = loadImage("resources/Images/exit_game_button.png");
    private static final BufferedImage enterNodeButton = loadImage("resources/Images/enter_node_button.png");
    private static final BufferedImage nodeButton = loadImage("resources/Images/node_button.png");
    private static final BufferedImage tradingPostBase = loadImage("resources/Images/button_trading-post_base.png");
    private static final BufferedImage tradingPostHover = loadImage("resources/Images/button_trading-post_hover.png");
    private static final BufferedImage tradingPostPress = loadImage("resources/Images/button_trading-post_press.png");
    private static final BufferedImage battleZoneBase = loadImage("resources/Images/button_battle-zone_base.png");
    private static final BufferedImage battleZoneHover = loadImage("resources/Images/button_battle-zone_hover.png");
    private static final BufferedImage battleZonePress = loadImage("resources/Images/button_battle-zone_press.png");
    private static final BufferedImage setShipbutton = loadImage("resources/Images/set_ship_button.png");
    private static final BufferedImage changePartsButton = loadImage("resources/Images/change_parts_button.png");
    private static final BufferedImage exitHangarButton = loadImage("resources/Images/exit_hangar_button.png");
    private static final BufferedImage arrowRight = loadImage("resources/Images/double-arrow-right.png");
    private static final BufferedImage arrowLeft = loadImage("resources/Images/double-arrow-left.png");
    private static final BufferedImage backToHangarButton = loadImage("resources/Images/back_to_hangar_button.png");

    //Item and Ship related images
    private static final BufferedImage shipImageBlack = loadImage("resources/Images/Ship-sprite-1-black-back.png");
    private static final BufferedImage shipImageWhite= loadImage("resources/Images/Ship-sprite-1.png");
    private static final BufferedImage legendaryShipImageBlackLarge = loadImage("resources/Images/Ship-sprite-legendary.png");
    private static final BufferedImage epicShipImageBlackLarge = loadImage("resources/Images/Ship-sprite-epic.png");
    private static final BufferedImage rareShipImageBlackLarge = loadImage("resources/Images/Ship-sprite-rare.png");
    private static final BufferedImage commonShipImageBlackLarge = loadImage("resources/Images/Ship-sprite-common.png");
    private static final BufferedImage commonEngineImage = loadImage("resources/Images/ship-engine-sprite-common.jpg");
    private static final BufferedImage commonShieldImage = loadImage("resources/Images/ship-shield-sprite-common.png");
    private static final BufferedImage commonBoostSpecialImage = loadImage("resources/Images/ship-boost-special-sprite-comm.png");
    private static final BufferedImage commonHealingSpecialImage = loadImage("resources/Images/ship-healing-special-sprite-co.png");
    private static final BufferedImage commmonStealthSpecialImage = loadImage("resources/Images/ship-stealth-special-sprite-co.png");
    private static final BufferedImage commonHomingWeaponImage = loadImage("resources/Images/ship-homing-weapon-sprite-common.png");
    private static final BufferedImage commonLinearWeaponImage = loadImage("resources/Images/ship-linear-weapon-sprite-common.png");

    private static final BufferedImage notFoundImage = loadImage("resources/Images/image-not-found-image.jpg");
    private static final BufferedImage commonFuelPackImage = loadImage("resources/Images/fuel-cell-sprite-black-back.png");
    private static final BufferedImage commonHealthPackImage = loadImage("resources/Images/health-pack-sprite-black-back.png");
    private static final BufferedImage commonShieldPackImage = loadImage("resources/Images/shield-pack-sprite-black-back.png");

    public static BufferedImage makeBorderedRect(int width, int height, Color bodyColor, Color borderColor)
    {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(bodyColor);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(borderColor);
        g2d.drawRect(0, 0, width - 1, height - 1);
        return image;
    }

    public static BufferedImage makeCenterLabeledRect(int width, int height, Color bodyColor, Color borderColor, Color textColor, String text)
    {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        //Added Font line. Can be moved to constructor
        g2d.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics metrics = g2d.getFontMetrics();
        g2d.setColor(bodyColor);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(borderColor);
        g2d.drawRect(0, 0, width - 1, height - 1);
        g2d.setColor(textColor);
        g2d.drawString(text, (width / 2) - (metrics.stringWidth(text) / 2), (height/2) + (metrics.getHeight()/4));
        return image;
    }

    public static BufferedImage makeLeftLabeledRect(int width, int height, Color bodyColor, Color borderColor, Color textColor, String text)
    {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics metrics = g2d.getFontMetrics();
        g2d.setColor(bodyColor);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(borderColor);
        g2d.drawRect(0, 0, width - 1, height - 1);
        g2d.setColor(textColor);
        g2d.drawString(text, 2, (height/2) + (metrics.getHeight()/4));
        return image;
    }

    private static BufferedImage loadImage(String file)
    {
        try
        {
            return ImageIO.read(new File(file));
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage getSpaceBackground(){
        return spaceBackground;
    }

    public static BufferedImage getTradingPostLabel(){
        return tradingPostLabel;
    }

    public static BufferedImage getBuyButtonBase() {
        return buyButtonBase;
    }

    public static BufferedImage getBuyButtonHover() {
        return buyButtonHover;
    }

    public static BufferedImage getBuyButtonPress() {
        return buyButtonPress;
    }

    public static BufferedImage getSellButtonBase() {
        return sellButtonBase;
    }

    public static BufferedImage getSellButtonHover() {
        return sellButtonHover;
    }

    public static BufferedImage getSellButtonPress() {
        return sellButtonPress;
    }

    public static BufferedImage getBountyButtonBase() {
        return bountyButtonBase;
    }

    public static BufferedImage getBountyButtonHover() {
        return bountyButtonHover;
    }

    public static BufferedImage getBountyButtonPress() {
        return bountyButtonPress;
    }

    public static BufferedImage getTurnInMissionButton(){return turnInMissionButton;}

    public static BufferedImage getAcceptBountyMissionButton() {return acceptBountyMissionButton;}

    public static BufferedImage getBountyMissionBorderImage() {return bountyMissionBorderImage;}

    public static BufferedImage getExitButtonBase() {
        return exitButtonBase;
    }

    public static BufferedImage getExitButtonHover() {
        return exitButtonHover;
    }

    public static BufferedImage getExitButtonPress() {
        return exitButtonPress;
    }


    public static BufferedImage getOverworldLabel() {
        return overworldLabel;
    }

    public static BufferedImage getHangarMenuButton() {return hangarMenuButton;}

    public static BufferedImage getSkillsMenuButton() {return  skillsMenuButton;}

    public static BufferedImage getExitSkillsMenuButton() {return exitSkillsMenuButton;}

    public static BufferedImage getCloseSkillInfoButton() {return closeSkillInfoButton;}

    public static BufferedImage getSkillInfoButton() {return skillInfoButton;}

    public static BufferedImage getFlySkillInfo() {return  flySkillInfo;}

    public static BufferedImage getComSkillInfo() {return comSkillInfo;}

    public static BufferedImage getCharSkillInfo() {return charSkillInfo;}

    public static BufferedImage getSkillIncreaseButton() {return skillIncreaseButton;}

    public static BufferedImage getExitGameButton() {return exitGameButton;}

    public static BufferedImage getEnterNodeButton(){return enterNodeButton;}

    public static BufferedImage getNodeButton() {return nodeButton;}

    public static BufferedImage getTradingPostBase() {return tradingPostBase;}

    public static BufferedImage getTradingPostHover() {return tradingPostHover;}

    public static BufferedImage getTradingPostPress() {return tradingPostPress;}

    public static BufferedImage getBattleZoneBase() {return battleZoneBase;}

    public static BufferedImage getBattleZoneHover() {return battleZoneHover;}

    public static BufferedImage getBattleZonePress() {return battleZonePress;}

    public static BufferedImage getSetShipbutton() {return setShipbutton;}

    public static BufferedImage getChangePartsButton() {return changePartsButton;}

    public static BufferedImage getExitHangarButton() {return exitHangarButton;}

    public static BufferedImage getArrowRight() {return arrowRight;}

    public static BufferedImage getArrowLeft(){return arrowLeft;}

    public static BufferedImage getBackToHangarButton(){return backToHangarButton;}

    //ship image methods
    public static BufferedImage getShipImageBlack() {return shipImageBlack;}

    public static BufferedImage getShipImageWhite() {return shipImageWhite;}

    public static BufferedImage getLegendaryShipImageBlackLarge() {return legendaryShipImageBlackLarge;}

    public static BufferedImage getEpicShipImageBlackLarge() {return epicShipImageBlackLarge;}

    public static BufferedImage getRareShipImageBlackLarge() {return rareShipImageBlackLarge;}

    public static BufferedImage getCommonShipImageBlackLarge() {return commonShipImageBlackLarge;}

    public static BufferedImage getCommonEngineImage() {return commonEngineImage;}

    public static BufferedImage getCommonShieldImage() {return commonShieldImage;}

    public static BufferedImage getCommonBoostSpecialImage() {return commonBoostSpecialImage;}

    public static BufferedImage getCommonHealingSpecialImage() {return commonHealingSpecialImage;}

    public static BufferedImage getCommonStealthSpecialImage() {return commmonStealthSpecialImage;}

    public static BufferedImage getCommonHomingWeaponImage() {return commonHomingWeaponImage;}

    public static BufferedImage getCommonLinearWeaponImage(){return commonLinearWeaponImage;}

    //item image methods
    public static BufferedImage getNotFoundImage() {return notFoundImage;}

    public static BufferedImage getCommonFuelPackImage() {return commonFuelPackImage;}

    public static BufferedImage getCommonHealthPackImage() {return commonHealthPackImage;}

    public static BufferedImage getCommonShieldPackImage() {return commonShieldPackImage;}

}
