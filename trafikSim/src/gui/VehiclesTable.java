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

		private final String[] header = {"Road", "ID", "Location", "Speed", "Km", "Faulty Units", "Itinerary"};
		
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
			switch(columnIndex) {
				case 0:	return _map.getVehicles().get(rowIndex).getId();
				case 1:	return _map.getVehicles().get(rowIndex).getRoad().getId();
				case 2:	return _map.getVehicles().get(rowIndex).getLocation();
				case 3: return _map.getVehicles().get(rowIndex).getSpeed();
				case 4: return _map.getVehicles().get(rowIndex).getKilometrage();
				case 5: return _map.getVehicles().get(rowIndex).getFaultyTime();
				case 6: return _map.getVehicles().get(rowIndex).getItinerary();
				default: return null;
			}
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
		t.setShowGrid(false);
		
		JScrollPane jsp = new JScrollPane(t);
		jsp.getViewport().setBackground(Color.white);
		
		this.add(jsp, BorderLayout.CENTER);	
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
