package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import ru.iammaxim.tesitems.GUI.Elements.ElementBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxim on 11/7/16 at 4:38 PM.
 */
public class FrameLayout extends LayoutBase {
    protected ElementBase element;

    public FrameLayout() {}

    @Override
    public void keyTyped(char c, int keyCode) {
        element.keyTyped(c, keyCode);
    }

    @Override
    public void checkHover(int mouseX, int mouseY) {
        element.checkHover(mouseX, mouseY);
    }

    @Override
    public void checkClick(int mouseX, int mouseY) {
        super.checkClick(mouseX, mouseY);
        element.checkClick(mouseX, mouseY);
    }

    public FrameLayout setElement(ElementBase element) {
        this.element = element;
        element.setParent(this);
        return this;
    }

    @Override
    public void doLayout() {
        element.setBounds(left + paddingLeft, top + paddingTop, right - paddingRight, bottom - paddingBottom);
        if (element instanceof LayoutBase)
            ((LayoutBase) element).doLayout();
    }

    @Override
    public List<ElementBase> getChildren() {
        ArrayList<ElementBase> list = new ArrayList<>();
        list.add(element);
        return list;
    }

    @Override
    public int getWidth() {
        if (widthOverride != -1)
            return widthOverride;

        return element.getWidth() + paddingLeft + paddingRight + marginLeft + marginRight;
    }

    @Override
    public int getHeight() {
        if (heightOverride != -1)
            return heightOverride;

        return element.getHeight() + paddingTop + paddingBottom + marginTop + marginBottom;
    }

    @Override
    public void onResize() {
        element.onResize();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (element != null)
            element.draw(mouseX, mouseY);
        else
            System.out.println("no element");
    }


}
