package makibear.network.geneticalgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import makibear.network.geneticalgorithm.entities.GeneticEntity;

public class GeneticAlgorithm
{
    private float mElitismChance;
    private float mCrossOverChance;
    private float mMutationChance;
    private float mAverageFitness;
    private float mHighestFitness;
    
    private List<GeneticEntity> mNextGeneration = new ArrayList<GeneticEntity>();
    private List<GeneticEntity> mActualGeneration;
    
    private Random mRand = new Random();
    
    public GeneticAlgorithm(float crossOverChance, float mutationChance) 
    {
        this.mCrossOverChance = crossOverChance;
        this.mElitismChance = 100 - crossOverChance;
        this.mMutationChance = mutationChance;
    }
    
    public float getHighestFitness()
    {
        return this.mHighestFitness;
    }
    
    public float getAverageFitness()
    {
        return this.mAverageFitness;
    }
    
    public double evolve(List<GeneticEntity> entities) 
    {
        System.out.println("Evolving " + entities.size() + " entities");
        mActualGeneration = entities;
        
        //Process evolution
        calculateFitness();
        System.out.println("AfterFitness NextGen: " + mNextGeneration.size());
        elitism();
        System.out.println("AfterElitism NextGen: " + mNextGeneration.size());
        crossOver();
        System.out.println("AfterCrossOver NextGen: " + mNextGeneration.size());
        mutation();
        System.out.println("AfterMutation NextGen: " + mNextGeneration.size());
        copyEntities();
        
        mActualGeneration = null;
        mNextGeneration.clear();
        return mAverageFitness;
    }
    
    private void elitism() 
    {
        Collections.sort(mActualGeneration, new Comparator<GeneticEntity>()
        {
            @Override
            public int compare(GeneticEntity o1, GeneticEntity o2) 
	        {
            	return (int) (o2.getFitness() - o1.getFitness());
	        }
        });
        
        int numberOfElites = (int)(mActualGeneration.size() * mElitismChance / 100);
        for (int i = 0; i < numberOfElites; ++i)
        	mNextGeneration.add(mActualGeneration.get(i));
    }
    
    private void calculateFitness() 
    {
        mHighestFitness = 0;
        mAverageFitness = 0;
        
        for(GeneticEntity e : mActualGeneration)
        {
            mAverageFitness += e.getFitness();
            if (e.getFitness() > mHighestFitness) 
            	mHighestFitness = e.getFitness();
        }
        mAverageFitness /= mActualGeneration.size();
        
        for (GeneticEntity e : mActualGeneration) 
        {
            if (mHighestFitness == 0) 
            	e.setParentChance(100); 
            else 
            	e.setParentChance((e.getFitness() / mHighestFitness) * 100);
        }
    }
    
    private GeneticEntity selection()
    {
        int r = mRand.nextInt(100);
        
        for(GeneticEntity e : mNextGeneration)
        	if (e.getParentChance() > r) return e;
        return null;
    }
    
    private void crossOver() 
    {
        int nrOfCrossOver = (int)(mActualGeneration.size() * (mCrossOverChance / 100));
        for (int j = 0; j < nrOfCrossOver; ++j) 
        {
            GeneticEntity parentA = selection();
            GeneticEntity parentB = selection();
            
            mNextGeneration.add(parentA.createChild(parentB));
        }
    }
    
    private void mutation() 
    {
        for (GeneticEntity e : mNextGeneration) 
        	if (mRand.nextInt(100) < mMutationChance)
            	e.mutate();
    }
    
    private void copyEntities() 
    {
        for (int i = 0; i < mNextGeneration.size(); ++i) 
        {
            GeneticEntity e = mNextGeneration.get(i);
            mActualGeneration.set(i, e);
        }
        
        for (GeneticEntity e : mActualGeneration) 
        {
            e.setFitness(0);
            e.reset();
        }
    }
}