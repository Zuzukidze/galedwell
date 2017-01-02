package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Created by maxim on 11/7/16 at 5:56 PM.
 */
public class ScreenCenteredLayout extends FrameLayout {
    public ScreenCenteredLayout(ElementBase parent) {
        super(parent);
    }

    @Override
    public void doLayout() {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        int width = (int) Math.min(getWidth(), res.getScaledWidth() * 0.5f - 2 * padding);
        int height = getHeight();
        setBounds(
                (res.getScaledWidth() - width)/2,
                (res.getScaledHeight() - height)/2,
                (res.getScaledWidth() + width)/2,
                (res.getScaledHeight() + height)/2);
        super.doLayout();
    }
}