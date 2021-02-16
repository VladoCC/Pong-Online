package com.inkostilation.pong.desktop.display;

import com.badlogic.gdx.graphics.Color;

public class Colors {

    private static Color[][] colorPallets =
            {
                    {new Color(0xffffffff), new Color(0x6772a9ff), new Color(0x3a3277ff), new Color(0x000000ff)}, //ARQ4 PALETTE Created by ENDESGA
                    {new Color(0xe2f3e4ff), new Color(0x94e344ff), new Color(0x46878fff), new Color(0x332c50ff)}, //KIROKAZE GAMEBOY PALETTE Created by Kirokaze
                    {new Color(0xeff9d6ff), new Color(0xba5044ff), new Color(0x7a1c4bff), new Color(0X1b0326ff)}, //CRIMSON PALETTECreated by WildLeoKnight
                    {new Color(0xd0d058ff), new Color(0xa0a840ff), new Color(0x708028ff), new Color(0x405010ff)}, //NOSTALGIA PALETTE Created by WildLeoKnight
                    {new Color(0xff8e80ff), new Color(0xc53a9dff), new Color(0x4a2480ff), new Color(0x051f39ff)}, //LAVA-GB PALETTE Created by Aero
                    {new Color(0xdad3afff), new Color(0xd58863ff), new Color(0xc23a73ff), new Color(0x2c1e74ff)}, //AUTUMN CHILL PALETTECreated by Doph
                    {new Color(0xcfab51ff), new Color(0x9d654cff), new Color(0x4d222cff), new Color(0x210b1bff)}, //GOLD GB PALETTE Created by Isa
                    {new Color(0xffffc7ff), new Color(0xd4984aff), new Color(0x4e494cff), new Color(0x00303bff)}, //SLURRY GB PALETTE Created by Braquen
                    {new Color(0xffe2dbff), new Color(0xd9a7c6ff), new Color(0x8d89c7ff), new Color(0x755f9cff)}, //MOON CRYSTAL PALETTE Created by Doph
                    {new Color(0xc5dbd4ff), new Color(0x778e98ff), new Color(0x41485dff), new Color(0x221e31ff)}, //METALLIC GB PALETTE Created by Isa
                    {new Color(0xffffffff), new Color(0xffc96bff), new Color(0xe63900ff), new Color(0x5a0084ff)}, //FOXFIRE PALETTE Created by Lotaru
                    {new Color(0xd5e6cbff), new Color(0x9594c0ff), new Color(0x564295ff), new Color(0x1b1b1bff)}  //EB GB PLAIN FLAVOUR PALETTE Created by Space Sandwich
            };

    private static int index = 9;

    public static void changePallet() {
        index += 1;
        if (index >= colorPallets.length) {
            index = 0;
        }
        System.out.println(index);
    }

    private static Color[] getPallet() {
        return colorPallets[index];
    }

    public static Color getOutlineColor() {
        return getPallet()[0];
    }

    public static Color getBackgroundColor() {
        return getPallet()[3];
    }

    public static Color getLightColor() {
        return getPallet()[1];
    }

    public static Color getDarkColor() {
        return getPallet()[2];
    }
}
