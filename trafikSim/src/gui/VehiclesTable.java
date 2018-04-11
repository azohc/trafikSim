package gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import control.SimulatorError;
import model.Event;
import model.RoadMap;
import model.TrafficSimulator;

public class VehiclesTable extends JPanel implements TrafficSimulatorObserver{
	
	class VehiclesTableModel extends AbstractTableModel{

		String[] header = {"Road", "ID", "Location"};
		
		@Override
		public int getColumnCount() {
			return header.length;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
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
		this.setLayout(new BorderLayout());
		_vehiclesModel = new VehiclesTableModel();
		
		JTable t = new JTable(_vehiclesModel);
		this.add(new JScrollPanel(t, JScrollPane..), BorderLayout.CENTER);
	}
	
	
	@Override
	public void addSimError(int time, RoadMap map, List<Event> events, SimulatorError e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStep(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
		_map = map;
		_vehiclesModel.refresh();
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
		_map = map;
		_vehiclesModel.refresh();
	}

	@Override
	public void addReset(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
		_map = map;
		_vehiclesModel.refresh();
	}

	
	
}
