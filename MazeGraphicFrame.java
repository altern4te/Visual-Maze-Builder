import java.awt.*;
import javax.swing.*;

public class MazeGraphicFrame extends JFrame
{
	//automatically sizes graphic frame to screen size
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int)screenSize.getHeight();
	public static final int HEIGHT = (int)screenSize.getHeight();
	
	public MazeGraphicFrame(String framename)
	{
		
		super(framename);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH,HEIGHT);
		add(new MazeGraphicPanel());
		setVisible(true);
		
	}
	
}
