package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.Factions.Faction;
import ru.iammaxim.tesitems.Factions.FactionManager;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.Networking.MessageOpenGui;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 01.01.2017.
 */
public class GuiFactionList extends Screen {

    public GuiFactionList() {
        ScrollableLayout root1 = new ScrollableLayout(contentLayout);
        contentLayout.setElement(root1);
        root1.setHeight((int) (res.getScaledHeight() * 0.8));
        VerticalLayout root2 = new VerticalLayout(root1);
        root1.setElement(root2);
        root2.add(new Text(root2, "Faction list").center(true));
        root2.add(new HorizontalDivider(root2));
        VerticalLayout factionsLayout = new VerticalLayout(root2);
        root2.add(factionsLayout);
        root2.add(new HorizontalDivider(root2));
        root2.add(new Button(root2).setText("Create new faction").setOnClick(b -> {
            Faction f = new Faction("");
            f.id = -1;
            TESItems.getMinecraft().displayGuiScreen(new GuiFactionEditor(f));
        }));

        FactionManager.factions.forEach((id, f) -> {
            factionsLayout.add(new Text(factionsLayout, "[Edit: " + f.name + "]") {
                @Override
                public void click(int relativeX, int relativeY) {
                    mc.displayGuiScreen(new GuiFactionEditor(f));
                }
            });
        });

        root.doLayout();
    }
}