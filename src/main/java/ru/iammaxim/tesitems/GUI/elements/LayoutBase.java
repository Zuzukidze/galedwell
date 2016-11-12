package ru.iammaxim.tesitems.GUI.elements;

/**
 * Created by maxim on 11/7/16 at 4:17 PM.
 */
public abstract class LayoutBase extends ElementBase {
    protected int padding = 0;

    public LayoutBase(ElementBase parent) {
        super(parent);
    }

    public abstract void doLayout();

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getPadding() {
        return padding;
    }
}