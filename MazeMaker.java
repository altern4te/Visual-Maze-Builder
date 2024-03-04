import java.util.Arrays;
import java.util.Random;

public class MazeMaker 
{
	MazeNode[] mazeArr;
	String[] directions = {"N","E","S","W"};
	int[][] dirCoords = {{-1,0},{0,1},{1,0},{0,-1}};
	boolean[][] wallArr; // stores directional data for each node NESW
	int rows, cols;
	public MazeMaker(int l, int w)
	{
		reset(l,w);
	}
	
	// returns a new, completed maze and saves it to mazeArr for further operations
	public boolean[][] buildMaze()
	{
		Random rand = new Random();
		while(find(0) != find(rows*cols - 1) || mazeArr[find(0)].find() > -1*rows*cols)
		{
			//System.out.println(Arrays.toString(mazeArr));
			// determines the random node to check
			int rNode = rand.nextInt(rows*cols);
			//determines direction to check
			int dir = rand.nextInt(4);
			unionMaker(rNode, dir);
		}
		//System.out.println(Arrays.toString(mazeArr));
		wallArr[0][3] = true;
		wallArr[rows*cols - 1][1] = true;
		return wallArr;
	}
	// determines where to make a union based on directions
	private void unionMaker(int rNode, int dir)
	{
		//System.out.println("coord: " + rNode + "\ndir: " + dir);
		//do north
		if(dir == 0 && rNode - cols > -1)
			union(rNode, rNode - cols, dir);
		//do east
		else if(dir == 1 && rNode%cols + 1 < cols)
			union(rNode, rNode + 1, dir);
		//do south
		else if(dir == 2 && rNode + cols < mazeArr.length)
			union(rNode, rNode + cols, dir);
		//do west
		else if(dir == 3 && rNode%cols - 1 > -1)
			union(rNode, rNode - 1, dir);
	}
	// returns a path from the top left node [0,0] to the bottom right node in string form
	// Starts from outside and ends outside
	public String pathfinder(int x, int y, int dir, String path)
	{
		if(x == rows - 1 && y == cols  - 1)
			return directions[dir] + "E";
		if(x < 0 || y < 0)
			return "#";
		int coord = x*cols + y;
		boolean[] routes = {false,false,false,false};
		// finds all available routes via wallArr
		int routeNumber = 0;
		for(int i = 0; i < 4; i++)
		{
			if(i != (dir+2)%4)
			{
				routes[i] = wallArr[coord][i];
				if(routes[i])
					routeNumber++;
			}
		}
		// if there are non, return a # 
		if(routeNumber == 0)
			return directions[dir] + "#";
		String[] recursivePaths = new String[routeNumber];
		int k = 0;
		for(int i = 0; i < 4; i++)
		{
			if(routes[i])
			{
				String test = pathfinder(x + dirCoords[i][0], y + dirCoords[i][1], i, path + directions[dir]);
				if(!test.contains("#"))
				{
					recursivePaths[k] = test;
					k++;
				}
			}
		}
		if(k == 0)
			return directions[dir] + "#";
		if(k == 1)
			return directions[dir] + recursivePaths[0];
		else if(k == 2)
		{
			if(recursivePaths[0].length() < recursivePaths[1].length())
				return directions[dir] + recursivePaths[0];
			else 
				return directions[dir] + recursivePaths[1];
		}
		else 
		{
			String small;
			if(recursivePaths[0].length() < recursivePaths[1].length())
				small = recursivePaths[0];
			else
				small = recursivePaths[1];
			if(small.length() < recursivePaths[2].length())
				return directions[dir] + small;
			else
				return directions[dir] + recursivePaths[2];
		}
	}
	
	// resets the maze to a new l x w matrix with all -1 values
	public void reset(int l, int w)
	{
		rows = l;
		cols = w;
		mazeArr = new MazeNode[l*w];
		wallArr = new boolean[l*w][4];
		for(int i = 0; i < l*w; i++)
		{
			mazeArr[i] = new MazeNode(-1);
			wallArr[i][0] = wallArr[i][1] = wallArr[i][2] = wallArr[i][3] = false;
		}
	}
	
	private int find(int val)
	{
		MazeNode n = mazeArr[val];
		if(n.isRoot())
			return val;
		return find(n.find());
	}
	
	private boolean union(int n, int m, int dir)
	{
		int npar = find(n);
		int mpar = find(m);
		if(npar == mpar)
			return false;
		else if(mazeArr[npar].find() < mazeArr[mpar].find())
		{
			int temp = mazeArr[mpar].find();
			mazeArr[mpar].setParent(npar);
			mazeArr[npar].setParent(mazeArr[npar].find() + temp);
		}
		else
		{
			int temp = mazeArr[npar].find();
			mazeArr[npar].setParent(mpar);
			mazeArr[mpar].setParent(mazeArr[mpar].find() + temp);
		}
		wallArr[n][dir] = true;
		wallArr[m][(dir + 2)%4] = true;
		return true;
	}
}
