package VIS;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.util.List;

import javax.swing.JFrame;

import flowers.Iris;

public class Window extends JFrame {
private Visualiser visualiser = new Visualiser();
public Window(){
	this.setPreferredSize(new Dimension(500,500));
	this.setLocation(200, 100);
	this.add(visualiser);
	this.pack();
	this.setVisible(true);
}

public void paint(List<Iris> trainingIris) {
	visualiser.paint(trainingIris);
	this.repaint();
	
}

}
