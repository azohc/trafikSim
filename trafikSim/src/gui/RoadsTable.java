package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;


import control.SimulatorError;
import model.Event;
import model.RoadMap;
import model.TrafficSimulator;

@SuppressWarnings("serial")
public class RoadsTable extends JPanel implements TrafficSimulatorObserver {

	public static Border defaultBorder = BorderFactory.createLineBorder(Color.black, 2);

	class RoadsTableModel extends AbstractTableModel{

		private final String[] header = {"ID", "Source", "Target", "Length", "Max Speed", "Vehicles"};

		public String getColumnName(int pos) {
			return header[pos];	
		}
		
		@Override
		public int getColumnCount() {
			return header.length;
		}

		@Override
		public int getRowCount() {
			return _map.getRoads().size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch(columnIndex) {
				case 0:	return _map.getRoads().get(rowIndex).getId();
				case 1:	return _map.getRoads().get(rowIndex).getSource().getId();
				case 2:	return _map.getRoads().get(rowIndex).getDestination().getId();
				case 3: return _map.getRoads().get(rowIndex).getLength();
				case 4: return _map.getRoads().get(rowIndex).getMaxSpeed();
				case 5: return _map.getRoads().get(rowIndex).getVehicles();
				default: return null;
			}
		}
		
		void refresh() {
			fireTableDataChanged();
		}
		
	}
		
	private RoadMap _map;
	private RoadsTableModel _roadsModel;
	
	public RoadsTable(TrafficSimulator model) {
		_map = null;
		initGUI();
		model.addObserver(this);
	}
	
	private void initGUI() {
		this.setBorder(new TitledBorder(defaultBorder, "Roads"));
		this.setLayout(new BorderLayout());
		_roadsModel = new RoadsTableModel();
		
		JTable t = new JTable(_roadsModel);
		t.setShowGrid(false);
		
		JScrollPane jsp = new JScrollPane(t);
		jsp.getViewport().setBackground(Color.white);
		
		this.add(jsp, BorderLayout.CENTER);	
		this.setVisible(true);
	}

	@Override
	public void addSimError(int time, RoadMap map, List<Event> events, SimulatorError e) {
	}

	@Override
	public void addStep(int time, RoadMap map, List<Event> events) {
		_map = map;
		_roadsModel.refresh();
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		_map = map;
		_roadsModel.refresh();
	}

	@Override
	public void addReset(int time, RoadMap map, List<Event> events) {
		_map = map;
		_roadsModel.refresh();
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		_map = map;
		_roadsModel.refresh();

	}

}
