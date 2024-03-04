
public class MazeNode 
{
	private int parent;
	
	// instantiates parent
	public MazeNode(int par)
	{
		parent = par;
	}
	// sets parent to new value
	public void setParent(int par)
	{
		parent = par;
	}
	public int find()
	{
		return parent;
	}
	public boolean isRoot()
	{
		return parent < 0;
	}
	public String toString()
	{
		return ""+parent;
	}
}
