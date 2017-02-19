package ru.iammaxim.tesitems.Scripting.GaledwellLang.Operations;

import ru.iammaxim.tesitems.Scripting.GaledwellLang.Runtime;

/**
 * Created by maxim on 2/19/17 at 3:21 PM.
 */
public class OperationGoTo extends Operation {
    public OperationLabel labelToGo;

    public OperationGoTo(OperationLabel label) {
        this.labelToGo = label;
    }

    @Override
    public void run(Runtime runtime) throws InvalidOperationException {
        runtime.currentCursorPos = labelToGo.index;
    }
}