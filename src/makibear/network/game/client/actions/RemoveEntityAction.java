package makibear.network.game.client.actions;

import com.esotericsoftware.kryonet.Connection;

import alexmog.network.packets.RemoveEntityPacket;
import makibear.network.game.GameScreen;
import makibear.network.game.client.PacketAction;
import makibear.network.geneticalgorithm.entities.Entity;

public class RemoveEntityAction extends PacketAction 
{
    @Override
    public void run(Connection connection, Object packet) throws Exception 
    {
        RemoveEntityPacket p = (RemoveEntityPacket)packet;
        
        Entity e = GameScreen.mEntityManager.getEntity(p.id);
        if (e != null) e.setToRemove(true);
    }
}