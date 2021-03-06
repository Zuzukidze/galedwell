package ru.iammaxim.tesitems.Scripting.GaledwellLang.Values;


import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by maxim on 2/12/17 at 12:59 PM.
 */
public class ValueInt extends Value {
    public int value;

    public ValueInt(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "int: " + value;
    }

/*    @Override
    public String toString(Runtime runtime, int indent) {
        return String.valueOf(value);
    }*/

    @Override
    public String valueToString() {
        return String.valueOf(value);
    }

    @Override
    public Value operatorPlus(Value right) {
        return new ValueInt(value + ((ValueInt) right).value);
    }

    @Override
    public Value operatorSubtract(Value right) {
        return new ValueInt(value - ((ValueInt) right).value);
    }

    @Override
    public Value operatorMultiply(Value right) {
        return new ValueInt(value * ((ValueInt) right).value);
    }

    @Override
    public Value operatorDivide(Value right) {
        return new ValueInt(value / ((ValueInt) right).value);
    }

    @Override
    public Value operatorLess(Value right) {
        return new ValueBoolean(value < ((ValueInt) right).value);
    }

    @Override
    public Value operatorLessEquals(Value right) {
        return new ValueBoolean(value <= ((ValueInt) right).value);
    }

    @Override
    public Value operatorEquals(Value right) {
        return new ValueBoolean(value == ((ValueInt) right).value);
    }

    @Override
    public Value operatorMoreEquals(Value right) {
        return new ValueBoolean(value >= ((ValueInt) right).value);
    }

    @Override
    public Value operatorMore(Value right) {
        return new ValueBoolean(value > ((ValueInt) right).value);
    }

    @Override
    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "int");
        tag.setInteger("value", value);
        return tag;
    }

    public static ValueInt loadValueFromNBT(NBTTagCompound tag) {
        return new ValueInt(tag.getInteger("value"));
    }

    @Override
    public Value operatorIncrement() {
        value++;
        return this;
    }

    @Override
    public Value operatorDecrement() {
        value--;
        return this;
    }

    public static boolean isValid(String value) {
        return value.matches("[0-9]*");
    }
}
