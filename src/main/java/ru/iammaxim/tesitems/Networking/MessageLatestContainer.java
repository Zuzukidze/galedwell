package ru.iammaxim.tesitems.Networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.iammaxim.tesitems.Inventory.InventoryContainer;
import ru.iammaxim.tesitems.Inventory.InventoryContainerClient;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by maxim on 3/4/17 at 12:55 PM.
 */
public class MessageLatestContainer implements IMessage {
    public InventoryContainer inv;

    public MessageLatestContainer() {}

    public MessageLatestContainer(InventoryContainer inv) {
        this.inv = inv;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        inv = (InventoryContainer) new InventoryContainerClient().loadFromNBT(ByteBufUtils.readTag(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, inv.writeToNBT());
    }

    public static class ClientHandler implements IMessageHandler<MessageLatestContainer, IMessage> {

        @Override
        public IMessage onMessage(MessageLatestContainer message, MessageContext ctx) {
            IPlayerAttributesCapability cap = TESItems.getCapability(TESItems.getClientPlayer());
            cap.setLatestContainer(message.inv);
            return null;
        }
    }
}
