package makibear.network.geneticalgorithm.entities;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

public class Entity 
{
	protected Shape mShape;
	
    protected List<Entity> mInVision = new ArrayList<>();
    protected List<Entity> mCollided = new ArrayList<>();
    
    protected double mRotation;
    protected boolean mToDelete = false;
    protected float red = 1, green = 1, blue = 1;
    protected int mId;
    
    public void update(GameContainer container, StateBasedGame game, int delta)
    {
        mInVision.clear();
        mCollided.clear();
    }
    
    public void setId(int id)
    {
        mId = id;
    }
    
    public int getId()
    {
        return mId;
    }
    
    public void render(GameContainer container, StateBasedGame game, Graphics g) {}
    
    public void inVision(Entity e) 
    {
        mInVision.add(e);
    }
    
    public Shape getShape() 
    {
        return mShape;
    }
    
    public boolean toDelete() 
    {
        return mToDelete;
    }

    public void isCollision(Entity e)
    {
        mCollided.add(e);
    }

    public void setToRemove(boolean b)
    {
        mToDelete = true;
    }
}