package ru.iammaxim.tesitems.Scripting.GaledwellLang;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Compiler.CompilerDebugRuntime;

/**
 * Created by maxim on 2/12/17 at 11:32 AM.
 */
public class Utils {
    public static String[] splitByComma(String src) {
        return src.split("[ \\t]*,[ \\t]*");
    }

    public static String indent(int count) {
        StringBuilder outputBuffer = new StringBuilder(count);
        for (int i = 0; i < count; i++){
            outputBuffer.append(" ");
        }
        return outputBuffer.toString();
    }

    public static String[] getNames(int[] IDs) {
        String[] argNames = new String[IDs.length];
        for (int i = 0; i < IDs.length; i++) {
            argNames[i] = CompilerDebugRuntime.getName(IDs[i]);
        }
        return argNames;
    }

    public static void printStackTrace() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : elements) {
            System.out.println(element);
        }
    }
}
