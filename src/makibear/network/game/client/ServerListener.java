package makibear.network.game.client;

import java.util.Map.Entry;

import makibear.network.game.Main;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import alexmog.network.packets.HandshakePacket;

public class ServerListener extends Listener 
{
    public class MyEntry implements Entry<Connection, Object> 
    {
        private Connection mConnection;
        private Object mPacket;
        
        public MyEntry(Connection connection, Object packet)
        {
            mConnection = connection;
            mPacket = packet;
        }
        
        @Override
        public Connection getKey() 
        {
            return mConnection;
        }

        @Override
        public Object getValue() 
        {
            return mPacket;
        }

        @Override
        public Object setValue(Object value) 
        {
            return mPacket;
        }
        
    }
    
    @Override
    public void connected(Connection c) 
    {
        c.sendTCP(new HandshakePacket());
    }
    
    @Override
    public void received(Connection c, Object packet) 
    {
        synchronized(Main.actions) 
        {
            Main.actions.add(new MyEntry(c, packet));
        }
    }
    
    @Override
    public void disconnected(Connection c) 
    {}
}
