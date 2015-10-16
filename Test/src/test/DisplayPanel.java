package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * To display the data
 * @author Zonglin Li
 *
 */
public class DisplayPanel extends JFrame {

	private JPanel contentPane;
	public DrawPane panel; 
	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public DisplayPanel(long[] toDisplay) {
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("Display");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 520, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		panel = new DrawPane(toDisplay);
		contentPane.add(panel, BorderLayout.CENTER);
	}
	
	public DisplayPanel(long[] toDisplay, long max) {
		setTitle("Display");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 520, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		panel = new DrawPane(toDisplay, max);
		contentPane.add(panel, BorderLayout.CENTER);
	}
	
	public class DrawPane extends JPanel{
		long[] Data;
		double Xmax;
		double Ymax;
		int XO = 9;
		int YO = 279;
		double pixelsPerScaleX;
		double pixelsPerScaleY;
		
		public DrawPane(long[] data){
			Data = data;
			long max = -1;
			for(long i : data){
				if(i > max){
					max = i;
				}
			}
			pixelsPerScaleX = 460.0 / (data.length);
			pixelsPerScaleY = 250.0 / max;
			this.repaint();
		}
		
		public DrawPane(long[] data, long max){
			Data = data;
			pixelsPerScaleX = 460.0 / (data.length);
			pixelsPerScaleY = 250.0 / max;
			this.repaint();
		}
		
		public void paint(Graphics g){
			super.paint(g);
			//Draw the y axe, with arrow
			g.drawLine(9, 9, 9, 279);
			g.drawLine(9, 9, 14, 19);
			g.drawLine(9, 9, 4, 19);
			//Draw the x axe, with arrow
			g.drawLine(489, 279, 9, 279);
			g.drawLine(489, 279, 479, 274);
			g.drawLine(489, 279, 479, 284);
			g.setColor(Color.BLUE);
			int height = 0;
			int x = 0;
			int y = 0;
			for(int i = 0; i < Data.length; i++){
				height = (int)(Data[i] * pixelsPerScaleY);
				x = (int)(i * pixelsPerScaleX + XO);
				y = (int)(YO - height);
				g.fillRect(x, y, (int)(pixelsPerScaleX - 15.0 / Data.length), height);
			}
		}
		
		public void plot(long[] data){
			if(data.length != Data.length){
				return;
			}
			Data = data;
			this.repaint();
		}
	}

}

