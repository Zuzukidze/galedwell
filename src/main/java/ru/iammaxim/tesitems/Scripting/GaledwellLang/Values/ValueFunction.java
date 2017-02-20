package ru.iammaxim.tesitems.Scripting.GaledwellLang.Values;

import net.minecraft.nbt.NBTTagCompound;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Compiler.CompilerDebugRuntime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.GaledwellLang;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

import java.util.Arrays;

/**
 * Created by maxim on 2/12/17 at 1:16 AM.
 */
public abstract class ValueFunction extends Value {
    public int id;
    public int[] args;

    public ValueFunction(int id, int... args) {
        this.id = id;
        this.args = args;

        GaledwellLang.log("created function with " + args.length + " arguments");
    }

    public ValueFunction(String name, String... args) {
        this(name.hashCode(), convertNameArrayToIDArray(args));
        CompilerDebugRuntime.addName(name.hashCode(), name);
    }

    private static int[] convertNameArrayToIDArray(String[] args) {
        int[] newArgs = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            newArgs[i] = args[i].hashCode();
            CompilerDebugRuntime.addName(newArgs[i], args[i]);
        }
        return newArgs;
    }

/*    @Override
    public String toString(Runtime runtime, int indent) {
        return "{\n" +
                Utils.indent(indent + 1) + "\"function\":\n" +
                Utils.indent(indent + 2) + "{\n" +
                Utils.indent(indent + 3) + "\"path\": " + id + ",\n" +
                Utils.indent(indent + 3) + "\"args\": " + Arrays.toString(args) + "\n" +
                Utils.indent(indent + 2) + "}\n" +
                Utils.indent(indent + 1) + "}";
    }*/

    @Override
    public String toString() {
        String[] argNames = new String[args.length];

        for (int i = 0; i < args.length; i++) {
            argNames[i] = CompilerDebugRuntime.getName(args[i]);
        }

        return "function: " + CompilerDebugRuntime.getName(id) + " (" + Arrays.toString(argNames) + ")";
    }

    @Override
    public Value operatorPlus(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorSubtract(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorMultiply(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorDivide(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorLess(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorLessEquals(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorEquals(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorMoreEquals(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public Value operatorMore(Value right) throws InvalidOperationException {
        throw new InvalidOperationException("Not implemented");
    }

    @Override
    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("type", "function");
        return tag;
    }

    @Override
    public String valueToString() {
        return toString();
    }

    public abstract void call(Runtime runtime, Value... args) throws InvalidOperationException;
}
