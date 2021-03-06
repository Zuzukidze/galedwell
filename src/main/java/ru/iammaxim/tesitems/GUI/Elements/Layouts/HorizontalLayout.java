package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Created by maxim on 11/7/16 at 4:19 PM.
 */
public class HorizontalLayout extends LayoutBase implements LayoutWithList {
    protected int spacing = 4;
    protected ArrayList<ElementBase> elements = new ArrayList<>();
    protected boolean center = false;

    public HorizontalLayout center(boolean center) {
        this.center = center;
        return this;
    }

    public HorizontalLayout() {}

    public int getSpacing() {
        return spacing;
    }

    public HorizontalLayout setSpacing(int spacing) {
        this.spacing = spacing;
        return this;
    }

    @Override
    public HorizontalLayout add(ElementBase element) {
        elements.add(element);
        element.setParent(this);
        return this;
    }

    @Override
    public void remove(ElementBase element) {
        elements.remove(element);
    }

    @Override
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
    public void checkRightClick(int mouseX, int mouseY) {
        try {
            super.checkRightClick(mouseX, mouseY);
            elements.forEach(e -> e.checkRightClick(mouseX, mouseY));
        } catch (ConcurrentModificationException e) {
        }
    }

    @Override
    public void doLayout() {
        int _w = getWidth();
        int y = top + paddingTop;
        int x;
        int x_max = right - paddingRight;
        if (center) x = left + (width - _w) / 2;
        else x = left + paddingLeft;
        for (ElementBase element : elements) {
            int w;
            int h;
            int h_override = element.getHeightOverride();
            if (h_override == FILL)
                h = height - paddingTop - paddingBottom;
            else
                h = Math.min(height - paddingTop - paddingBottom, element.getHeight());

            int w_override = element.getWidthOverride();
            if (w_override == FILL)
                w = x_max - x - paddingRight;
            else
                w = Math.min(element.getWidth(), x_max - x);
            
            element.setBounds(x, y, x + w, y + h);
            if (element instanceof LayoutBase)
                ((LayoutBase) element).doLayout();
            x += w + spacing;
        }
    }

    @Override
    public List<ElementBase> getChildren() {
        return elements;
    }

    @Override
    public int getHeight() {
        int height = 0;
        for (ElementBase e : elements) {
            int h = e.getHeight();
            if (parent != null)
                h = (int) Math.min(h, new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() * 0.8 - paddingTop - paddingBottom);
            if (h > height) height = h;
        }
        height += paddingTop + paddingBottom;
        return height;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        elements.forEach(e -> e.draw(mouseX, mouseY));
    }

    @Override
    public int getWidth() {
        int width = 0;
        for (ElementBase e : elements) {
            width += e.getWidth();
        }
        width += (elements.size() - 1) * spacing;
        width += paddingLeft + paddingRight;
        return width;
    }

    public int getTop() {
        return top;
    }

    public HorizontalLayout setTop(int top) {
        this.top = top;
        return this;
    }

    @Override
    public void checkHover(int mouseX, int mouseY) {
        elements.forEach(e -> e.checkHover(mouseX, mouseY));
    }

    @Override
    public void onResize() {
        elements.forEach(ElementBase::onResize);
    }

    @Override
    public String getName() {
        return "HorizontalLayout";
    }
}
