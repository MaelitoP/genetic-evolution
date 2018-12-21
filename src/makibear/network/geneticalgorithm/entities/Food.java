package makibear.network.geneticalgorithm.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.StateBasedGame;

public class Food extends Entity 
{
    public Food() 
    {
        mShape = new Circle(0, 0, 5);
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) 
    {
        g.setColor(Color.white);
        g.fill(mShape);
    }

}
