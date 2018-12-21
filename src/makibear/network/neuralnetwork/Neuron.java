package makibear.network.neuralnetwork;

public class Neuron 
{
    public Dendrite[] mDendrites;
    public double mBias;
    public double mValue;
    public double mDelta;
    
    public class Dendrite 
    {
        public double weight;
    };
}
