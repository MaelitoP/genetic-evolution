package makibear.network.geneticalgorithm.entities;

public abstract class GeneticEntity extends Entity 
{
    protected float mFitness;
    protected float mParentChance;
    
    public void setFitness(float fitness) 
    {
        mFitness = fitness;
    }
    
    public float getFitness()
    {
        return mFitness;
    }
    
    public void setParentChance(float parentChance)
    {
        mParentChance = parentChance;
    }
    
    public float getParentChance() 
    {
        return mParentChance;
    }
    
    public abstract GeneticEntity createChild(GeneticEntity parentB);
    public abstract void mutate();
    public abstract void reset();
}
