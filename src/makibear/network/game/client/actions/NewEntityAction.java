package makibear.network.game.client.actions;

import com.esotericsoftware.kryonet.Connection;

import alexmog.network.packets.NewEntityPacket;
import makibear.network.game.GameScreen;
import makibear.network.game.client.PacketAction;
import makibear.network.geneticalgorithm.entities.Entity;
import makibear.network.geneticalgorithm.entities.Food;
import makibear.network.geneticalgorithm.entities.LivingEntity;

public class NewEntityAction extends PacketAction 
{
    @Override
    public void run(Connection connection, Object packet) throws Exception
    {
        NewEntityPacket p = (NewEntityPacket) packet;
        
        Entity e = null;
        
        if (p.isLiving)
        {
            LivingEntity le = new LivingEntity();
            le.setDamages(p.damages);
            le.setGeneration(p.generation);
            le.setHatchTime(p.hatchTime);
            le.setMaxAge(p.maxAge);
            le.setMaxHp(p.maxHp);
            le.setTeam(p.team);
            e = le;
        } 
        else 
        	e = new Food();
        e.getShape().setX(p.x);
        e.getShape().setY(p.y);
        e.setId(p.id);
        
        if (e != null) GameScreen.mEntityManager.addEntity(e);
    }
}