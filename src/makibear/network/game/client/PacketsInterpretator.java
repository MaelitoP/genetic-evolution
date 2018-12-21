package makibear.network.game.client;

import java.util.HashMap;
import java.util.Iterator;

import makibear.network.game.client.actions.EntityAction;
import makibear.network.game.client.actions.HandshakeAction;
import makibear.network.game.client.actions.NewEntityAction;
import makibear.network.game.client.actions.RemoveEntityAction;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;

import alexmog.network.packets.EntityPacket;
import alexmog.network.packets.HandshakePacket;
import alexmog.network.packets.NewEntityPacket;
import alexmog.network.packets.RemoveEntityPacket;

@SuppressWarnings("rawtypes")
public class PacketsInterpretator 
{
    private HashMap<Class, PacketAction> mPackets = new HashMap<Class, PacketAction>();
    
    public PacketsInterpretator() 
    {
        mPackets.put(EntityPacket.class, new EntityAction());
        mPackets.put(NewEntityPacket.class, new NewEntityAction());
        mPackets.put(RemoveEntityPacket.class, new RemoveEntityAction());
        mPackets.put(HandshakePacket.class, new HandshakeAction());
    }
    
    public boolean onPacketReceived(Connection connection, Object packet) 
    {
        Iterator<Class> it = mPackets.keySet().iterator();
        
        while (it.hasNext()) 
        {
            Class item = it.next();
            try 
            {
                if (packet.getClass().isAssignableFrom(item)) 
                {
                    PacketAction a = mPackets.get(item);
                    a.run(connection, packet);
                    
                    return true;
                }
            } 
            catch (Exception e) 
            	{ Log.error("PacketInterpretator", e); }
        }
        return false;
    }
}