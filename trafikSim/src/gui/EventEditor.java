package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import control.Controller;
import control.EventBuilder;
import control.MakeVehicleFaultyEventBuilder;
import control.NewBikeEventBuilder;
import control.NewCarEventBuilder;
import control.NewDirtRoadEventBuilder;
import control.NewJunctionEventBuilder;
import control.NewLanesRoadEventBuilder;
import control.NewMostCrowdedJunctionEventBuilder;
import control.NewRoadEventBuilder;
import control.NewRoundRobinEventBuilder;
import control.NewVehicleEventBuilder;
import model.TrafficSimulator;

@SuppressWarnings("serial")
public class EventEditor extends JPanel implements ActionListener { // this is a JPanel object -> with initGUI 1config
																	// from constructor call, we create a object

	private JScrollPane _scrollPane;
	private JFileChooser _fc;
	private File _currentFile;
	private JTextArea _textArea;
	private TitledBorder _titledBrd;
	private JLabel _statusBar;

	public static Border defaultBorder = BorderFactory.createLineBorder(Color.black, 2);

	public EventEditor(TrafficSimulator model, Controller ctrl, String currentFile, JPanel statusBar) {
		_currentFile = new File(currentFile);
		_statusBar = (JLabel) statusBar.getComponent(0);
		initGUI();
	}

	public JTextArea getTextArea() {
		return _textArea;
	}

	private void initGUI() {

		_fc = new JFileChooser();
		_fc.setCurrentDirectory(new File("./cv_docs"));

		_textArea = new JTextArea(40, 30);
		_textArea.setEditable(true);
		_textArea.setLineWrap(true);
		_textArea.setWrapStyleWord(true);

		if (_currentFile == null)
			_titledBrd = new TitledBorder(defaultBorder, "Events");
		else {
			_titledBrd = new TitledBorder(defaultBorder, "Events: " + _currentFile.getName());
			_textArea.setText(readFile(_currentFile));
		}

		setBorder(_titledBrd);
		setLayout(new BorderLayout());

		_scrollPane = new JScrollPane(_textArea);
		add(_scrollPane, BorderLayout.CENTER);
		createPopupMenu();
	}

	@Override
	public TitledBorder getBorder() {
		return _titledBrd;
	}

	public void setBorder(String s) {
		_titledBrd.setTitle(s);
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("load".equals(e.getActionCommand())) {
			loadFile();
			_statusBar.setText("Events have been loaded!");
		} else if ("save".equals(e.getActionCommand())) {
			saveFile();
			_statusBar.setText("Events have been saved!");
		} else if ("CLEAR".equals(e.getActionCommand())) {
			_textArea.setText("");
			_statusBar.setText("Events editor cleared!");
		} else if ("RRJUNCTION".equals(e.getActionCommand())) {
			templateBuilder(new NewRoundRobinEventBuilder());
			_statusBar.setText("Round robin junction template!");
		} else if ("MCJUNCTION".equals(e.getActionCommand())) {
			templateBuilder(new NewMostCrowdedJunctionEventBuilder());
			_statusBar.setText("Most crowded junction template!");
		} else if ("JUNCTION".equals(e.getActionCommand())) {
			templateBuilder(new NewJunctionEventBuilder());
			_statusBar.setText("Junction template!");
		} else if ("DIRTROAD".equals(e.getActionCommand())) {
			templateBuilder(new NewDirtRoadEventBuilder());
			_statusBar.setText("Dirt road template!");
		} else if ("LANESROAD".equals(e.getActionCommand())) {
			templateBuilder(new NewLanesRoadEventBuilder());
			_statusBar.setText("Lanes road template!");
		} else if ("ROAD".equals(e.getActionCommand())) {
			templateBuilder(new NewRoadEventBuilder());
			_statusBar.setText("Road template!");
		} else if ("BIKE".equals(e.getActionCommand())) {
			templateBuilder(new NewBikeEventBuilder());
			_statusBar.setText("Bike template!");
		} else if ("CAR".equals(e.getActionCommand())) {
			templateBuilder(new NewCarEventBuilder());
			_statusBar.setText("Car template!");
		} else if ("VEHICLE".equals(e.getActionCommand())) {
			templateBuilder(new NewVehicleEventBuilder());
			_statusBar.setText("Vehicle template!");
		} else if ("FAULTY".equals(e.getActionCommand())) {
			templateBuilder(new MakeVehicleFaultyEventBuilder());
			_statusBar.setText("Make Vehicle Faulty template!");
		}

	}

	public void templateBuilder(EventBuilder event) {

		_textArea.setText(_textArea.getText() + event.template() + System.getProperty("line.separator"));

	}

	public void saveFile() {
		int returnVal = _fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			writeFile(file, _textArea.getText());
		}
	}

	public void loadFile() {
		int returnVal = _fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			String s = readFile(file);
			_textArea.setText(s);
			_titledBrd = new TitledBorder(defaultBorder, "Events: " + file.getName());
			setBorder(_titledBrd);
		}

	}

	public static String readFile(File file) {
		String s = "";
		try {
			Scanner scan = new Scanner(file);
			s = scan.useDelimiter("\\A").next();
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static void writeFile(File file, String content) {
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.print(content);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createPopupMenu() { 
		JPopupMenu menu = new JPopupMenu();

		JMenu templates = new JMenu("Add Template");

		JMenuItem rrJunction = new JMenuItem("New RR Junction");
		templates.add(rrJunction);
		rrJunction.setActionCommand("RRJUNCTION");
		rrJunction.addActionListener(this);

		JMenuItem mcJunction = new JMenuItem("New MC Junction");
		templates.add(mcJunction);
		mcJunction.setActionCommand("MCJUNCTION");
		mcJunction.addActionListener(this);

		JMenuItem junction = new JMenuItem("New Junction");
		templates.add(junction);
		junction.setActionCommand("JUNCTION");
		junction.addActionListener(this);

		JMenuItem dirtRoad = new JMenuItem("New Dirt Road");
		templates.add(dirtRoad);
		dirtRoad.setActionCommand("DIRTROAD");
		dirtRoad.addActionListener(this);

		JMenuItem lanesRoad = new JMenuItem("New Lanes Road");
		templates.add(lanesRoad);
		lanesRoad.setActionCommand("LANESROAD");
		lanesRoad.addActionListener(this);

		JMenuItem road = new JMenuItem("New Road");
		templates.add(road);
		road.setActionCommand("ROAD");
		road.addActionListener(this);

		JMenuItem bike = new JMenuItem("New Bike");
		templates.add(bike);
		bike.setActionCommand("BIKE");
		bike.addActionListener(this);

		JMenuItem car = new JMenuItem("New Car");
		templates.add(car);
		car.setActionCommand("CAR");
		car.addActionListener(this);

		JMenuItem vehicle = new JMenuItem("New Vehicle");
		templates.add(vehicle);
		vehicle.setActionCommand("VEHICLE");
		vehicle.addActionListener(this);

		JMenuItem faulty = new JMenuItem("Make Vehicle Faulty");
		templates.add(faulty);
		faulty.setActionCommand("FAULTY");
		faulty.addActionListener(this);

		menu.add(templates);
		menu.addSeparator();

		JMenuItem load = new JMenuItem("Load");
		menu.add(load);
		load.setActionCommand("load");
		load.addActionListener(this);

		JMenuItem save = new JMenuItem("Save");
		menu.add(save);
		save.setActionCommand("save");
		save.addActionListener(this);

		JMenuItem clear = new JMenuItem("Clear");
		menu.add(clear);
		clear.setActionCommand("CLEAR");
		clear.addActionListener(this);

		class MouseClickListener extends MouseAdapter {

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger())
					doPop(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger())
					doPop(e);
			}

			private void doPop(MouseEvent e) {
				menu.show(e.getComponent(), e.getX(), e.getY());
			}
		}

		_textArea.addMouseListener(new MouseClickListener());

	}

}
