package VIS;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import flowers.Iris;

public class Visualiser extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Iris> flowers; 
	public Visualiser(){
	this.setPreferredSize(new Dimension(500,500));
}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (Iris flower : flowers) {
			g2d.drawRect((int)flower.getPetalRatio()*50,(int)flower.getSepalRatio()*50, 2, 2);
		}
		

	}

	public void paint(List<Iris> trainingIris) {
		flowers = new ArrayList<Iris>(trainingIris);
		
	}

}
