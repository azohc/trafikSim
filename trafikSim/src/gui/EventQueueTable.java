package gui;

import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import control.SimulatorError;
import model.Event;
import model.RoadMap;
import model.TrafficSimulator;

@SuppressWarnings("serial")
public class EventQueueTable extends JPanel implements TrafficSimulatorObserver {

	class EventTableModel extends AbstractTableModel{
		protected final String[] header = { "#", "Time", "Type" };
		private List _eventQueue;

		
		public EventTableModel() {
			_eventQueue = new LinkedList();
		}
		
		public String getColumnName(int pos) {
			return header[pos];	
		}
		
		@Override
		public int getColumnCount() {
			return header.length;
		}

		@Override
		public int getRowCount() {
			return 0;
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		void refresh() {
			fireTableDataChanged();
		}
	}
	
	private EventTableModel _eventTableModel;
	
	
	public EventQueueTable(TrafficSimulator model) {
		
		initGUI();		
		model.addObserver(this);
	}
	
	
	public void initGUI() {
		this.setLayout(new BorderLayout());
		_eventTableModel = new EventTableModel();
		
		JTable t = new JTable();
		
		t.setModel(_eventTableModel);
		
		this.add(new JScrollPane(t), BorderLayout.CENTER); //check		
		this.setVisible(true);
	}
	
	@Override
	public void addSimError(int time, RoadMap map, List<Event> events, SimulatorError e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStep(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReset(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		// TODO Auto-generated method stub
		
	}

}
