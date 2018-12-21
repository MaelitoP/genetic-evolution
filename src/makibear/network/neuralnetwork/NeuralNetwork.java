package makibear.network.neuralnetwork;

import java.util.Random;

public class NeuralNetwork 
{
    public static double e = 2.7183;
    
    public class Layer 
    {
        public Neuron[] neurons;
    }
    
    private Layer[] mLayers;
    private Random mRand;
    
    public NeuralNetwork(int inputCount, int hiddenLayerCount, int outputCount) 
    {
        double[] layerArray = new double[3];
        layerArray[0] = inputCount;
        layerArray[1] = hiddenLayerCount;
        layerArray[2] = outputCount;
        mRand = new Random();
        
        mLayers = new Layer[3];
        mLayers[0] = new Layer();
        mLayers[1] = new Layer();
        mLayers[2] = new Layer();
        
        for (int i = 0; i < mLayers.length; ++i) 
        {
            mLayers[i].neurons = new Neuron[(int)layerArray[i]];
            
            for (int j = 0; j < layerArray[i]; ++j) 
            {
                mLayers[i].neurons[j] = new Neuron();
                if (i != 0) // do not modify 0, cause thoes are the inputs neurons
                { 
                    mLayers[i].neurons[j].mBias = getRand();
                    mLayers[i].neurons[j].mDendrites = new Neuron.Dendrite[(int)layerArray[i - 1]];
                    
                    for (int k = 0; k < layerArray[i - 1]; ++k) 
                    {
                        mLayers[i].neurons[j].mDendrites[k] = mLayers[i].neurons[j].new Dendrite();
                        mLayers[i].neurons[j].mDendrites[k].weight = getRand();
                    }
                }
            }
        }
    }
    
    public void randomize()
    {
        for (int i = 1; i < mLayers.length; ++i) 
        {
            for (int j = 0; j < mLayers[i].neurons.length; ++j) 
            {
                mLayers[i].neurons[j].mBias = getRand();
                for (int k = 0; k < mLayers[i - 1].neurons.length; ++k) 
                	mLayers[i].neurons[j].mDendrites[k].weight = getRand();
            }
        }
    }
    
    public double[] getWeights() 
    {
        double[] ret = new double[getDendritesNum()];
        
        int dendrites = 0;
        for (int i = 1; i < mLayers.length; ++i)
        {
            for (int j = 0; j < mLayers[i].neurons.length; ++j) 
            	for (int k = 0; k < mLayers[i - 1].neurons.length; ++k) 
                	ret[dendrites++] = mLayers[i].neurons[j].mDendrites[k].weight;
        }
        return ret;
    }
    
    public void setWeights(double[] weights) 
    {
        int dendrites = 0;
        for (int i = 1; i < mLayers.length; ++i)
        	for (int j = 0; j < mLayers[i].neurons.length; ++j) 
            	for (int k = 0; k < mLayers[i - 1].neurons.length; ++k) 
                	mLayers[i].neurons[j].mDendrites[k].weight = weights[dendrites++];
    }
    
    public int getDendritesNum() 
    {
        int ret = 0;
        
        for (int i = 1; i < mLayers.length; ++i) 
        	for (int j = 0; j < mLayers[i].neurons.length; ++j) 
            	ret += mLayers[i - 1].neurons.length;
        
        return ret;
    }
    
    public void setBias(double[] bias) 
    {
        int biasNb = 0;
        for (int i = 1; i < mLayers.length; ++i)
        	for (int j = 0; j < mLayers[i].neurons.length; ++j)
            	mLayers[i].neurons[j].mBias = bias[biasNb++];
    }
    
    public double[] getBias() 
    {
        double[] ret = new double[getBiasCount()];
        int ctr = 0;
        
        for (int i = 1; i < mLayers.length; ++i) 
        	for (int j = 0; j < mLayers[i].neurons.length; ++j) 
            	ret[ctr++] = mLayers[i].neurons[j].mBias;
        
        return ret;
    }
    
    public int getBiasCount() 
    {
        int ctr = 0;
        
        for (int i = 1; i < mLayers.length; ++i)
            ctr += mLayers[i].neurons.length - 1;
        
        return ctr;
    }
    
    public double[] process(double[] inputs) 
    {
        if (inputs.length != mLayers[0].neurons.length) return null;
        
        for (int i = 0; i < mLayers.length; ++i) 
        {
            for (int j = 0; j < mLayers[i].neurons.length; ++j) 
            {
                if (i == 0)
                	mLayers[i].neurons[j].mValue = inputs[j];
                else 
                {
                    mLayers[i].neurons[j].mValue = 0;
                    
                    for (int k = 0; k < mLayers[i - 1].neurons.length; ++k) 
                    	mLayers[i].neurons[j].mValue = mLayers[i].neurons[j].mValue
                        + mLayers[i - 1].neurons[k].mValue * mLayers[i].neurons[j].mDendrites[k].weight;
                    
                    mLayers[i].neurons[j].mValue = action(mLayers[i].neurons[j].mValue);
                }
            }
        }
        
        double[] ret = new double[mLayers[mLayers.length - 1].neurons.length];
        
        for (int i = 0; i < mLayers[mLayers.length - 1].neurons.length; ++i) 
        {
            ret[i] = mLayers[mLayers.length - 1].neurons[i].mValue;
            
            if (ret[i] > 1)
                ret[i] = 1;
            else if (ret[i] < -1)
                ret[i] = -1;
        }
        return ret;
    }
    
    private double action(double x) 
    {
        return (1 / (1 + Math.exp(x * -1)));
    }
    
    private double getRand()
    {
        return (mRand.nextDouble() * 2) - 1;
    }
}