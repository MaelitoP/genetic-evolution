package makibear.network.geneticalgorithm.entities;

import makibear.network.game.GameScreen;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class LivingEntity extends Entity 
{
	private Circle spikeCircle;
    private Vector2f leftSensor, rightSensor;
    private Entity[] mNearestEntities = new Entity[4];
    
    @SuppressWarnings("unused")
	private int age, maxAge, team,
				generation = 0, damages = 0, hatchTime = 0, maxLife = 0;
    private float angle, life, spikeLength;
    
    public LivingEntity() 
    {
        mShape = new Circle(0, 0, 20);
        spikeCircle = new Circle(mShape.getCenterX(), mShape.getCenterY(), 0);
    }

    private Vector2f extendPoint(Vector2f center, float angle, int length)
    {
        float rad = (float)(angle * Math.PI / 180);
        return new Vector2f((float)(center.x + (Math.cos(rad) * length)),
                (float)(center.y + (Math.sin(rad) * length)));
    }
    
    public void update(GameContainer container, StateBasedGame game, int delta) 
    {
        if (generation > GameScreen.bestGeneration) 
        	GameScreen.bestGeneration = generation;

        Vector2f newloc = new Vector2f(mShape.getLocation());
        newloc.x += mShape.getWidth() / 2;
        newloc.y += mShape.getHeight() / 2;
        leftSensor = extendPoint(newloc, (float)(angle - 45 - 90), 12);
        rightSensor = extendPoint(newloc, (float)(angle + 45 - 90), 12);
        
        spikeCircle.setRadius(spikeLength * 40);
        
        spikeCircle.setCenterX(mShape.getCenterX());
        spikeCircle.setCenterY(mShape.getCenterY());
        
        super.update(container, game, delta);
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) 
    {
        g.setColor(Color.yellow);
        g.fill(spikeCircle);
        g.setColor(new Color(red, green, blue));
        g.fill(mShape);
        g.setColor(Color.red);
        g.draw(mShape);
        g.setColor(life > 0 ? new Color((int)(255 - (((float)life / (float)maxLife) * 255f)),
                (int)(((float)life / (float)maxLife) * 255f), 0) : Color.red);
        g.fillRect(mShape.getX(),
                mShape.getCenterY() + mShape.getHeight() / 2 + 2,
                mShape.getWidth() * (float)((float)life / (float)maxLife), 10);
        g.setColor(Color.red);
        g.drawRect(mShape.getX(),
                mShape.getCenterY() + mShape.getHeight() / 2 + 2, mShape.getWidth(), 10);
        g.setColor(Color.white);
        g.drawString("G: " + generation + "", mShape.getX() - mShape.getWidth(), mShape.getCenterY() + 28);
        g.drawString(team == 0 ? "Herbivore" : "Carnivore", mShape.getX() - mShape.getWidth(), mShape.getCenterY() + 40);
        g.drawString("D: " + damages, mShape.getX() - mShape.getWidth(), mShape.getCenterY() + 55);
        g.drawString("H: " + hatchTime, mShape.getX() - mShape.getWidth(), mShape.getCenterY() + 70);
        g.drawString("HP: " + maxLife, mShape.getX() - mShape.getWidth(), mShape.getCenterY() + 85);
        g.drawString("MA: " + maxAge, mShape.getX() - mShape.getWidth(), mShape.getCenterY() + 100);
                
        if (life <= 0) return;
        
        if (rightSensor == null || leftSensor == null) return;
        
        //What right eye see
        g.setColor(Color.yellow);
        g.drawOval(rightSensor.getX() - 5, rightSensor.getY() - 5, 10, 10);
        g.setColor(Color.blue);
        g.drawOval(leftSensor.getX() - 5, leftSensor.getY() - 5, 10, 10);

        for (int i = 0; i < mNearestEntities.length; ++i) 
        {
            Entity e = mNearestEntities[i];
            if (e != null && e.getShape() != null) 
            {
                g.setColor(new Color(e.red, e.green, e.blue));
                Vector2f sensor = i % 2 == 0 ? leftSensor : rightSensor;
                g.drawLine(e.getShape().getCenterX(), e.getShape().getCenterY(), sensor.getX(), sensor.getY());
            }
        }
    }
    
    public float getLife() 
    {
        return life;
    }

    public void setDamages(int damages2) 
    {
        damages = damages2;
    }

    public void setGeneration(int generation2) 
    {
        generation = generation2;
    }

    public void setHatchTime(int hatchTime2)
    {
        hatchTime = hatchTime2;
    }

    public void setMaxAge(int maxAge2) 
    {
        maxAge = maxAge2;
    }

    public void setMaxHp(int maxHp) 
    {
        maxLife = maxHp;
    }

    public void setTeam(int team2) 
    {
        team = team2;
    }

    public void setLife(int actualHp) 
    {
        life = actualHp;
    }

    public void setAge(int age2) 
    {
        age = age2;
    }

    public void setAngle(float angle2) 
    {
        angle = angle2;
    }

    public void setColor(float blue, float red, float green) 
    {
        this.blue = blue;
        this.red = red;
        this.green = green;
    }

    public void constructLookingAt(int[] lookAt) 
    {
        for (int i = 0; i < lookAt.length; ++i) 
        {
            int id = lookAt[i];
            if (id != 0)
            	mNearestEntities[i] = GameScreen.mEntityManager.getEntity(id);
        }
    }

    public void setSpikeLength(float spikeLength2) 
    {
        spikeLength = spikeLength2;
    }
}