package gui;
import javax.swing.*;

import control.Controller;
import model.TrafficSimulator;


import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class Toolbar implements ActionListener {

	private final String LOAD = "load";
	private final String SAVE = "save";
	private final String CLEAR = "clear";
	private final String QUIT = "quit";
	
	private TrafficSimulator _model;
	private Controller _ctrl;


	public Toolbar(TrafficSimulator model, Controller ctrl) {
		_model = model;
		_ctrl = ctrl;
	}

	public JToolBar createJToolBar() {
		JToolBar toolBar = new JToolBar();

		JButton load = new JButton();
		load.setActionCommand(LOAD);
		load.setToolTipText("Load a file");
		load.addActionListener(this);
		load.setIcon(new ImageIcon(loadImage("cv_docs/icons/open.png")));
		toolBar.add(load);

		JButton save = new JButton();
		save.setActionCommand(SAVE);
		save.setToolTipText("Save a file");
		save.addActionListener(this);
		save.setIcon(new ImageIcon(loadImage("cv_docs/icons/save.png")));
		toolBar.add(save);

		JButton clear = new JButton();
		clear.setActionCommand(CLEAR);
		clear.setToolTipText("Clear Text");
		clear.addActionListener(this);
		clear.setIcon(new ImageIcon(loadImage("cv_docs/icons/clear.png")));
		toolBar.add(clear);

		return toolBar;
	}

	public void actionPerformed(ActionEvent e) {
//		if (LOAD.equals(e.getActionCommand())) mega todo
//			loadFile();
//		else if (SAVE.equals(e.getActionCommand()))
//			saveFile();
//		else if (CLEAR.equals(e.getActionCommand()))
//			textArea.setText("");
//		else if (QUIT.equals(e.getActionCommand()))
//			System.exit(0);
	}

//	private void saveFile() {			mega todo
//		int returnVal = fc.showSaveDialog(null);
//		if (returnVal == JFileChooser.APPROVE_OPTION) {
//			File file = fc.getSelectedFile();
//			writeFile(file, textArea.getText());
//		}
//	}
//
//	private void loadFile() {
//		int returnVal = fc.showOpenDialog(null);
//		if (returnVal == JFileChooser.APPROVE_OPTION) {
//			File file = fc.getSelectedFile();
//			String s = readFile(file);
//			textArea.setText(s);
//		}
//	}
//
//	public static String readFile(File file) {
//		String s = "";
//		try {
//			s = new Scanner(file).useDelimiter("\\A").next();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		return s;
//	}
//
//	public static void writeFile(File file, String content) {
//		try {
//			PrintWriter pw = new PrintWriter(file);
//			pw.print(content);
//			pw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	private static Image loadImage(String path) {
		return Toolkit.getDefaultToolkit().createImage(path);
	}
}