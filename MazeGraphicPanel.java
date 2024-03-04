import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.*;

public class MazeGraphicPanel extends JPanel implements MouseMotionListener, MouseListener
{
	int l,w, nl, nw;
	boolean box1u, box1b, box2u, box2b, box3, path;
	boolean[][] mazeArr;
	MazeMaker m;
	Color fadeRed = new Color(255, 0, 0, 50);
	Color fadeGreen = new Color(0, 255, 0, 50);
	Font mazeFont;
	public MazeGraphicPanel()
	{
		l = nl = 50;
		w = nw = 50;
		box1u = box1b = box2u = box2b = false;
		box3 = path = true;
		m = new MazeMaker(l,w);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public boolean isFocusTraversable() 
	{
		return true;
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		mazeFont = new Font("Monospaced", Font.PLAIN, this.getWidth()/16);
		g.setFont(mazeFont);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		// box 1
		if(box1u == true)
		{
			g.setColor(fadeGreen);
			g.fillRect(this.getWidth()/4, this.getHeight()/16, this.getWidth()/8, this.getHeight()/16);
		}
		else if(box1b == true)
		{
			g.setColor(fadeRed);
			g.fillRect(this.getWidth()/4, this.getHeight()/8, this.getWidth()/8, this.getHeight()/16);
		}
		
		g.setColor(Color.WHITE);
		g.drawRect(this.getWidth()/4, this.getHeight()/16, this.getWidth()/8, this.getHeight()/8);
		g.drawString("" + nl, this.getWidth()/4,this.getHeight()/16 + 11*this.getHeight()/128);
		// box 2
		if(box2u == true)
		{
			g.setColor(fadeGreen);
			g.fillRect(7*this.getWidth()/16, this.getHeight()/16, this.getWidth()/8, this.getHeight()/16);
		}
		else if(box2b == true)
		{
			g.setColor(fadeRed);
			g.fillRect(7*this.getWidth()/16, this.getHeight()/8, this.getWidth()/8, this.getHeight()/16);
		}
		g.setColor(Color.WHITE);
		g.drawRect(7*this.getWidth()/16, this.getHeight()/16, this.getWidth()/8, this.getHeight()/8);
		g.drawString("" + nw, 7*this.getWidth()/16,this.getHeight()/16 + 11*this.getHeight()/128);
		// box 3
		g.setColor(Color.WHITE);
		g.drawRect(5*this.getWidth()/8, this.getHeight()/16, this.getWidth()/8, this.getHeight()/8);
		if(box3 == true)
		{
			m.reset(nl, nw);
			l = nl;
			w = nw;
			mazeArr = m.buildMaze();
			System.out.println(m.pathfinder(0, 0, 1, ""));
			box3 = false;
			path = false;
		}
		if(path)
			g.drawString("New", 5*this.getWidth()/8,this.getHeight()/16 + 11*this.getHeight()/128);
		else
			g.drawString("Sol", 5*this.getWidth()/8,this.getHeight()/16 + 11*this.getHeight()/128);
		int cellWidth = (this.getWidth()/2)/w;
		int cellHeight = (this.getHeight()/2)/w;
		// draws the maze
		for(int i = 0; i < mazeArr.length; i++)
		{
			int x = (i % w) * cellWidth + this.getWidth()/4;
			int y = ((i - (i%w)) / w) * cellHeight + this.getHeight()/4;
			g.setColor(Color.WHITE);
			if(mazeArr[i][0] == false)
				g.drawLine(x, y, x + cellWidth, y);
			if(mazeArr[i][1] == false)
				g.drawLine(x + cellWidth,y, x + cellWidth, y + cellHeight);
			if(mazeArr[i][2] == false)
				g.drawLine(x, y + cellHeight, x + cellWidth, y + cellHeight);
			if(mazeArr[i][3] == false)
				g.drawLine(x, y, x, y + cellHeight);
		}
		if(path)
		{
			g.setColor(Color.RED);
			int x = -cellWidth/2 + this.getWidth()/4;
			int y = cellHeight/2 + this.getHeight()/4;
			String pathFound = m.pathfinder(0, 0, 1, "");
			String[] pathArr = pathFound.split("");
			for(int i = 0; i < pathArr.length; i++)
			{
				int newx = x;
				int newy = y;
				switch(pathArr[i])
				{
					case "N":
						newy -= cellHeight;
						g.drawLine(x, y, newx, newy);
						break;
					case "E": 
						newx += cellWidth;
						g.drawLine(x, y, newx, newy);
						break;
					case "S": 
						newy += cellHeight;
						g.drawLine(x, y, newx, newy);
						break;
					case "W": 
						newx -= cellWidth;
						g.drawLine(x, y, newx, newy);
						break;
					default: break;
				}
				x = newx;
				y = newy;
			}
		}
	}

	public void mouseClicked(MouseEvent e) 
	{
		
	}

	public void mousePressed(MouseEvent e) 
	{
		if(e.getX() > this.getWidth()/4 && e.getX() < this.getWidth()/4 + this.getWidth()/8)
		{
			if(e.getY() > this.getHeight()/16 && e.getY() < this.getHeight()/16 + this.getHeight()/16)
			{
				if(nl != 250)
					nl++;
			}
			else if (e.getY() > this.getHeight()/16 + this.getHeight()/16 && e.getY() < this.getHeight()/16 + this.getHeight()/8)
			{
				if(nl != 2)
					nl--;
			}
		}
		
		if(e.getX() > 7*this.getWidth()/16 && e.getX() < 7*this.getWidth()/16 + this.getWidth()/8)
		{
			if(e.getY() > this.getHeight()/16 && e.getY() < this.getHeight()/16 + this.getHeight()/16)
			{
				if(nw != 250)
					nw++;
			}
			else if (e.getY() > this.getHeight()/16 + this.getHeight()/16 && e.getY() < this.getHeight()/16 + this.getHeight()/8)
			{
				if(nw != 2)
					nw--;
			}
		}
		
		if(e.getX() > 5*this.getWidth()/8 && e.getX() < 5*this.getWidth()/8 + this.getWidth()/8)
		{
			if(e.getY() > this.getHeight()/16 && e.getY() < this.getHeight()/16 + this.getHeight()/8)
			{
				if(path)
					box3 = true;
				else
					path = true;
			}
		}
		
		repaint();
	}

	public void mouseReleased(MouseEvent e) 
	{
		
	}

	public void mouseEntered(MouseEvent e) 
	{
		
		
	}

	public void mouseExited(MouseEvent e) 
	{
		
	}

	public void mouseDragged(MouseEvent e) 
	{
	
	}

	public void mouseMoved(MouseEvent e) 
	{
		if(e.getX() > this.getWidth()/4 && e.getX() < this.getWidth()/4 + this.getWidth()/8)
		{
			if(e.getY() > this.getHeight()/16 && e.getY() < this.getHeight()/16 + this.getHeight()/16)
			{
				box1u = true;
				box1b = false;
			}
			else if (e.getY() > this.getHeight()/16 + this.getHeight()/16 && e.getY() < this.getHeight()/16 + this.getHeight()/8)
			{
				box1u = false;
				box1b = true;
			}
			else
				box1u = box1b = false;
		}
		else 
			box1u = box1b = false;
		
		if(e.getX() > 7*this.getWidth()/16 && e.getX() < 7*this.getWidth()/16 + this.getWidth()/8)
		{
			if(e.getY() > this.getHeight()/16 && e.getY() < this.getHeight()/16 + this.getHeight()/16)
			{
				box2u = true;
				box2b = false;
			}
			else if (e.getY() > this.getHeight()/16 + this.getHeight()/16 && e.getY() < this.getHeight()/16 + this.getHeight()/8)
			{
				box2u = false;
				box2b = true;
			}
			else
				box2u = box2b = false;
		}
		else 
			box2u = box2b = false;
		
		repaint();
	}
}
