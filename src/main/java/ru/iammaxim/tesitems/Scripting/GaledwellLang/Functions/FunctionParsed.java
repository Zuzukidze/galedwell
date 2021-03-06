package ru.iammaxim.tesitems.Scripting.GaledwellLang.Functions;


import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.InvalidOperationException;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations.Operation;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.Value;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueFunction;

import java.util.ArrayList;

/**
 * Created by maxim on 2/12/17 at 12:29 PM.
 */
public class FunctionParsed extends ValueFunction {
    public ArrayList<Operation> operations;

    public FunctionParsed(int id, int[] args, ArrayList<Operation> operations) {
        super(id, args);
        this.operations = operations;
    }

    @Override
    public void call(Runtime runtime, Value... arguments) throws InvalidOperationException {
        ArrayList<Integer> savedValuesArgs = new ArrayList<>();
        ArrayList<Value> savedValues = new ArrayList<>(arguments.length);

        // save args
        for (int i = 0; i < arguments.length; i++) {
            Value v = runtime.variableStorage.getField(args[i]);
            if (v != null) {
                savedValues.add(v);
                savedValuesArgs.add(args[i]);
            }
            runtime.variableStorage.setField(args[i], arguments[i]);
        }

        // run program
        // iterate over all operators
        runtime.currentCursorPos = 0;
        int size = operations.size();
        runtime.currentFunctionLength = size;
        while (runtime.currentCursorPos < size) {
            Operation op = operations.get(runtime.currentCursorPos);
//            System.out.println("executing " + runtime.currentCursorPos + ": " + op);
            op.run(runtime);
            runtime.currentCursorPos++;
        }

        // remove args
        for (int i = 0; i < arguments.length; i++) {
            runtime.variableStorage.removeField(args[i]);
        }

        // restore args
        for (int i = savedValues.size() - 1; i >= 0; i--) {
            runtime.variableStorage.setField(savedValuesArgs.get(i), savedValues.get(i));
        }
    }
}
