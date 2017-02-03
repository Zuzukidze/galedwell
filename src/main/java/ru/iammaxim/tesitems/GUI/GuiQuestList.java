package ru.iammaxim.tesitems.GUI;

import ru.iammaxim.tesitems.GUI.Elements.Button;
import ru.iammaxim.tesitems.GUI.Elements.HorizontalDivider;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.DoubleStateFrameLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.HeaderLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.ScrollableLayout;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.VerticalLayout;
import ru.iammaxim.tesitems.GUI.Elements.Text;
import ru.iammaxim.tesitems.Questing.Quest;
import ru.iammaxim.tesitems.Questing.QuestManager;

/**
 * Created by Maxim on 20.07.2016.
 */
public class GuiQuestList extends Screen {
    VerticalLayout quests;

    public GuiQuestList() {
        contentLayout.setElement(
                new ScrollableLayout().setElement(
                        new VerticalLayout()
                                .add(new HeaderLayout("Quests"))
                                .add(new HorizontalDivider())
                                .add(quests = new VerticalLayout())
                                .add(new HorizontalDivider())
                                .add(new Button("New quest").setOnClick(e -> {

                                }))));

        QuestManager.questList.forEach((id, quest) -> addQuest(quest));
    }

    public void addQuest(Quest quest) {
        quests.add(new DoubleStateFrameLayout()
        .setFirstState(new Text(quest.name).setColor(0xFF0066CC).setOnClick(e -> {

        })));
    }
}
