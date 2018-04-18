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
public class VehiclesTable extends JPanel implements TrafficSimulatorObserver{
	
	public static Border defaultBorder = BorderFactory.createLineBorder(Color.black, 2);

	class VehiclesTableModel extends AbstractTableModel{

		private final String[] header = {"Road", "ID", "Location"};
		
		public String getColumnName(int pos) {
			return header[pos];	
		}
		
		@Override
		public int getColumnCount() {
			return header.length;
		}

		@Override
		public int getRowCount() {
			return _map.getVehicles().size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			String s = "";
			switch(columnIndex) {
			case 0:
				s = _map.getVehicles().get(rowIndex).getId();
			break;
			case 1: 
				s = _map.getVehicles().get(rowIndex).getRoad().getId();
			break;
			case 2:
				s = "" + _map.getVehicles().get(rowIndex).get_location();
				break;
			}
			refresh();
			return null;
		}
		
		void refresh() {
			fireTableDataChanged();
		}
		
	}
	
	
	private RoadMap _map;
	private VehiclesTableModel _vehiclesModel;

	VehiclesTable(TrafficSimulator model){
		_map = null;
		initGUI();
		model.addObserver(this);
		
	}
	
	private void initGUI() {
		this.setBorder(new TitledBorder(defaultBorder, "Vehicles"));
		this.setLayout(new BorderLayout());
		_vehiclesModel = new VehiclesTableModel();
		
		JTable t = new JTable(_vehiclesModel);
				
		this.add(new JScrollPane(t), BorderLayout.CENTER); //check
		this.setVisible(true);
	}
	
	
	
	@Override
	public void addSimError(int time, RoadMap map, List<Event> events, SimulatorError e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addStep(int time, RoadMap map, List<Event> events) {
		_map = map;
		_vehiclesModel.refresh();
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		_map = map;
		_vehiclesModel.refresh();
	}

	@Override
	public void addReset(int time, RoadMap map, List<Event> events) {
		_map = map;
		_vehiclesModel.refresh();
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		_map = map;
		_vehiclesModel.refresh();

	}

	
	
}
