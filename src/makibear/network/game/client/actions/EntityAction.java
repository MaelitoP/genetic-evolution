package makibear.network.game.client.actions;

import makibear.network.game.GameScreen;
import makibear.network.game.client.PacketAction;
import makibear.network.geneticalgorithm.entities.Entity;
import makibear.network.geneticalgorithm.entities.LivingEntity;

import com.esotericsoftware.kryonet.Connection;

import alexmog.network.packets.EntityPacket;

public class EntityAction extends PacketAction 
{
    @Override
    public void run(Connection connection, Object packet) throws Exception 
    {
        EntityPacket p = (EntityPacket)packet;
        
        Entity e = GameScreen.mEntityManager.getEntity(p.id);
        if (e != null) 
        {
            e.getShape().setX(p.x);
            e.getShape().setY(p.y);
            if (e instanceof LivingEntity) 
            {
                LivingEntity le = (LivingEntity)e;
                le.setLife(p.actualHp);
                le.setAge(p.age);
                le.setAngle(p.angle);
                le.setColor(p.blue, p.red, p.green);
                le.constructLookingAt(p.lookAt);
                le.setSpikeLength(p.spikeLength);
            }
        }
    }
}