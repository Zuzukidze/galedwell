package ru.iammaxim.tesitems.Factions;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ru.iammaxim.tesitems.Dialogs.DialogTopic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxim on 8/5/16 at 7:56 PM.
 */
public class Faction {
    public int id;
    public String name;
    public List<DialogTopic> topics = new ArrayList<>();

    public Faction(String name) {
        this.name = name;
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("name", name);
        tag.setInteger("index", id);
        NBTTagList list = new NBTTagList();
        topics.forEach((topic) -> list.appendTag(topic.writeToNBT()));
        tag.setTag("topics", list);
        return tag;
    }

    public static Faction loadFromNBT(NBTTagCompound tag) {
        Faction faction = new Faction(tag.getString("name"));
        faction.id = tag.getInteger("index");
        NBTTagList tagList = (NBTTagList) tag.getTag("topics");
        for (int i = tagList.tagCount() - 1; i >= 0; i--) {
            faction.topics.add(DialogTopic.readFromNBT(tagList.getCompoundTagAt(i)));
        }
        return faction;
    }
}
