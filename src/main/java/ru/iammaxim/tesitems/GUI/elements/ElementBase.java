package ru.iammaxim.tesitems.GUI.elements;

import joptsimple.internal.Strings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;

/**
 * Created by maxim on 11/7/16 at 4:22 PM.
 */
public abstract class ElementBase {
    protected int left;
    protected int top;
    protected int right;
    protected int bottom;
    protected int width;
    protected int height;
    protected ElementBase parent;
    protected int marginH = 0;
    protected int marginV = 0;
    protected boolean focused;

    public ElementBase(ElementBase parent) {
        this.parent = parent;
    }

    public static void drawColoredRect(Tessellator tess, int left, int top, int right, int bottom, int color) {
        float b = (float)(color & 0xFF)/255;
        float g = (float)((color >> 8) & 0xFF)/255;
        float r = (float)((color >> 16) & 0xFF)/255;
        float a = (float)((color >> 24) & 0xFF)/255;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GlStateManager.color(r, g, b, a);
        VertexBuffer buf = tess.getBuffer();
        buf.begin(7, DefaultVertexFormats.POSITION);
        buf.pos(left, bottom, 0.0D).endVertex();
        buf.pos(right, bottom, 0.0D).endVertex();
        buf.pos(right, top, 0.0D).endVertex();
        buf.pos(left, top, 0.0D).endVertex();
        tess.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GlStateManager.disableBlend();
    }

    public void checkClick(int mouseX, int mouseY) {
        if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom) {
            click(mouseX - left, mouseY - top);
        }
    }

    public void checkHover(int mouseX, int mouseY) {
        if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom) {
            if (!focused)
                onFocus();
            focused = true;
        } else {
            if (focused)
                onFocusLost();
            focused = false;
        }
    }

    public void onFocusLost() {
    }

    public void onFocus() {
    }

    public ElementBase getParent() {
        return parent;
    }

    public ElementBase getRoot() {
        if (parent == null) return this;
        return parent.getRoot();
    }

    public int getHorizontalMargin() {
        return marginH;
    }

    public void setHorizontalMargin(int margin) {
        this.marginH = margin;
    }

    public int getVerticalMargin() {
        return marginV;
    }

    public void setVerticalMargin(int margin) {
        this.marginV = margin;
    }

    public int getWidth() {
        return width + 2 * marginH;
    }

    public int getHeight() {
        return height + 2 * marginV;
    }

    //calculates width and height
    public void calculateSize() {
        this.width = getWidth();
        this.height = getHeight();
    }

    public void setBounds(int left, int top, int right, int bottom) {
        this.left = left + marginH;
        this.right = right - marginH;
        this.top = top + marginV;
        this.bottom = bottom - marginV;
        this.width = this.right - this.left;
        this.height = this.bottom - this.top;
    }

    public abstract void draw(int mouseX, int mouseY);

    public void keyTyped(char c, int keyCode) {}

    public void click(int relativeX, int relativeY) {}

    public void drawTexturedRect(Tessellator tess, int left, int top, int right, int bottom, ResourceLocation texture) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        VertexBuffer vb = tess.getBuffer();
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, bottom, 0).tex(0, 1).endVertex();
        vb.pos(right, bottom, 0).tex(1, 1).endVertex();
        vb.pos(right, top, 0).tex(1, 0).endVertex();
        vb.pos(left, top, 0).tex(0, 0).endVertex();
        tess.draw();
        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_TEXTURE_2D);;
    }

    public void drawTexturedRect(Tessellator tess, int left, int top, int right, int bottom, float UVx, float UVy, ResourceLocation texture) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        VertexBuffer vb = tess.getBuffer();
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(left, bottom, 0).tex(0, UVy).endVertex();
        vb.pos(right, bottom, 0).tex(UVx, UVy).endVertex();
        vb.pos(right, top, 0).tex(UVx, 0).endVertex();
        vb.pos(left, top, 0).tex(0, 0).endVertex();
        tess.draw();
        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public void onRescale() {}
}