package makibear.network.geneticalgorithm.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import makibear.network.game.Quadtree;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class EntityManager
{
    private List<Entity> mToAdd = new ArrayList<>();
    private Map<Integer, Entity> mEntities = new ConcurrentHashMap<>();
    
    /* private List<Entity> mCollided = new ArrayList<>();
    private Quadtree mQuadTree = new Quadtree(0, new Rectangle(0, 0, Main.WIDTH, Main.HEIGHT));*/
    
    private int mLivingEntities = 0;
    
    public int getLivingEntitiesNumber() 
    {
        return mLivingEntities;
    }
    
    public void update(GameContainer container, StateBasedGame game, float delta) 
    {
        for (Entity e : mToAdd) 
        	mEntities.put(e.getId(), e);
        mToAdd.clear();
        
        /* mQuadTree.clear();
        for (Entity e : mEntities.values()) 
        {
            mQuadTree.insert(e);
        }*/
        
        mLivingEntities = 0;
        for (int i : mEntities.keySet()) 
        {
            Entity e = mEntities.get(i);
            if (e == null) { ++i;continue; }
            
            updateEntity(e, container, game, (int)delta);
            
            if (e.toDelete()) 
            	mEntities.remove(e.getId());
            else 
            {
                ++i;
                if (e instanceof LivingEntity) 
                	mLivingEntities++;
            }
        }
    }
    
    private void updateEntity(Entity e, GameContainer container, StateBasedGame game, int delta)
    {
        if (e == null) return;
        e.update(container, game, delta);
        //Collision detection
        /* mCollided.clear();
        mQuadTree.collideList(mCollided, e);
        for (int x = 0; x < mCollided.size(); ++x) 
        {
            Entity cEntity = mCollided.get(x);
            if (e != cEntity) 
            {
                e.inVision(cEntity);
                if (e.getShape().intersects(cEntity.getShape())) 
	                e.isCollision(cEntity);          
            }
        }*/
    }
    
    @SuppressWarnings("unused")
	private void drawQuadTree(Quadtree q, Graphics g) 
    {
        if (q != null) 
        {
            if (q.getBounds() != null) 
            	g.draw(q.getBounds());
            
            if (q.getNodes() != null) 
            	for (Quadtree q2 : q.getNodes())
                	drawQuadTree(q2, g);
        }
    }
    
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        for (Entity e : mEntities.values()) 
        	e.render(container, game, g);
        
        g.setColor(Color.red);
        // drawQuadTree(mQuadTree, g);
    }
    
    public void addEntity(Entity e)
    {
        mToAdd.add(e);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getEntity(int id)
    {
        return (T) mEntities.get(id);
    }
    
    @SuppressWarnings("unchecked")
    public <T> List<T> getAll(Class<T> c) 
    {
        List<T> ret = new ArrayList<T>();
        
        for (Entity e : mEntities.values())
        	if (c.isInstance(e))
            	ret.add((T)e);
        
        return ret;
    }
    
    @SuppressWarnings("unlikely-arg-type")
	public <T> void removeAll(Class<T> c)
    {
        for (int i = 0; i < mEntities.size();) 
        {
            Entity e = mEntities.get(i);
            
            if (c.isInstance(e)) 
            	mEntities.remove(e);
            else 
            	++i;
        }
    }

    public void addAll(List<GeneticEntity> e)
    {
        mToAdd.addAll(e);
    }
}