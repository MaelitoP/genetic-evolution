package makibear.network.game.client;

import com.esotericsoftware.kryonet.Connection;

public abstract class PacketAction 
{
    public abstract void run(Connection connection, Object packet) throws Exception;
    public boolean needLoggedIn() { return false; }
}
