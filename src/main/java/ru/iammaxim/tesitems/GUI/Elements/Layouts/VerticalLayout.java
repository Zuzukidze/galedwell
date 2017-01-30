package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Created by maxim on 11/7/16 at 4:19 PM.
 */
public class VerticalLayout extends LayoutBase {
    protected int spacing = 4;
    protected ArrayList<ElementBase> elements = new ArrayList<>();
    private boolean limitHeight = true;

    public ArrayList<ElementBase> getElements() {
        return elements;
    }

    public VerticalLayout() {
    }

    public VerticalLayout add(ElementBase element) {
        elements.add(element);
        element.setParent(this);
        return this;
    }

    public void remove(ElementBase element) {
        elements.remove(element);
    }

    public void clear() {
        elements.clear();
    }

    @Override
    public void keyTyped(char c, int keyCode) {
        elements.forEach(e -> e.keyTyped(c, keyCode));
    }

    @Override
    public void checkClick(int mouseX, int mouseY) {
        try {
            super.checkClick(mouseX, mouseY);
            elements.forEach(e -> e.checkClick(mouseX, mouseY));
        } catch (ConcurrentModificationException e) {
        }
    }

    @Override
    public void doLayout() {
        int y = top + paddingTop;
        int y_max = bottom - paddingBottom;
        int x = left + paddingLeft;
        for (ElementBase element : elements) {
            int w = Math.min(width - paddingLeft - paddingRight, element.getWidth());
            if (w == FILL) {
                w = width - paddingLeft - paddingRight;
            }
            int h;
            if (limitHeight) {
                h = Math.min(element.getHeight(), y_max - y);
            } else {
                h = element.getHeight();
            }
            element.setBounds(x, y, x + w, y + h);
            if (element instanceof LayoutBase)
                ((LayoutBase) element).doLayout();
            y += h + spacing;
        }
        y -= spacing;
        System.out.println("doLayout final height: " + (y - top - paddingTop));
    }

    @Override
    public List<ElementBase> getChildren() {
        return elements;
    }

    @Override
    public int getHeight() {
        if (heightOverride != -1)
            return heightOverride;

        int height = 0;
        for (ElementBase e : elements) {
            height += e.getHeight();
        }
        height += (elements.size() - 1) * spacing;
        height += paddingTop + paddingBottom;
/*        System.out.println("returning height: " + height + "\n" + String.join("\n\t",
                Arrays.asList(
                        Arrays.asList(
                                Thread.currentThread().getStackTrace()
                        ).stream().map(
                                in -> in.toString()
                        ).toArray())));*/

        System.out.println("verticalLayout height: " + height);
        return height;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        elements.forEach(e -> e.draw(mouseX, mouseY));
    }

    @Override
    public int getWidth() {
        if (widthOverride != -1)
            return widthOverride;

        int width = 0;
        for (ElementBase e : elements) {
            int w = e.getWidth();
            if (w > width) width = w;
        }
        width += paddingLeft + paddingRight + marginLeft + marginRight;
        return width;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    @Override
    public void checkHover(int mouseX, int mouseY) {
        elements.forEach(e -> e.checkHover(mouseX, mouseY));
    }

    @Override
    public void onResize() {
        elements.forEach(ElementBase::onResize);
    }

    public boolean isLimitHeight() {
        return limitHeight;
    }

    public VerticalLayout setLimitHeight(boolean limitHeight) {
        this.limitHeight = limitHeight;
        return this;
    }
}
