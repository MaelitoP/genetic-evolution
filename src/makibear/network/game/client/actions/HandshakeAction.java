package makibear.network.game.client.actions;

import java.text.SimpleDateFormat;

import com.esotericsoftware.kryonet.Connection;

import alexmog.network.packets.HandshakePacket;
import makibear.network.game.GameScreen;
import makibear.network.game.client.PacketAction;

public class HandshakeAction extends PacketAction 
{
    @Override
    public void run(Connection connection, Object packet) throws Exception
    {
        HandshakePacket p = (HandshakePacket) packet;
        GameScreen.startTimestamp = p.startTimestamp;
        GameScreen.mStartDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(p.startTimestamp);
        GameScreen.mTimeElapsed = System.currentTimeMillis();
    }
}