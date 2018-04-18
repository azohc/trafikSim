package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import control.SimulatorError;
import control.SortedArrayList;
import model.Event;
import model.RoadMap;
import model.TrafficSimulator;

@SuppressWarnings("serial")
public class EventQueueTable extends JPanel implements TrafficSimulatorObserver {
	
	public static Border defaultBorder = BorderFactory.createLineBorder(Color.black, 2);

	class EventTableModel extends AbstractTableModel{
		private final String[] header = { "#", "Time", "Type" };

		
		public String getColumnName(int pos) {
			return header[pos];	
		}
		
		@Override
		public int getColumnCount() {
			return header.length;
		}

		@Override
		public int getRowCount() {
			return _eventQueue.size();
		}

		@Override
		public Object getValueAt(int row, int col) {

			switch(col) {
			case 0: return col; 	//TODO CHECK what to put here : also order list inversely, so lowest time is at top
			case 1: return ((Event) _eventQueue.get(row)).getTime();
			case 2: return ((Event) _eventQueue.get(row)).toString();	//TODO CHECK
			default: return null;
			}
		}
		
		void refresh() {
			fireTableDataChanged();
		}
	}
	
	private EventTableModel _eventTableModel;
	protected List _eventQueue;
	
	public EventQueueTable(TrafficSimulator model) {
		
		_eventQueue = null;
		initGUI();		
		model.addObserver(this);
	}
	
	
	public void initGUI() {
		
		this.setBorder(new TitledBorder(defaultBorder, "Events Queue"));
		this.setLayout(new BorderLayout());
		_eventTableModel = new EventTableModel();
		
		JTable t = new JTable(_eventTableModel);
	
		
		this.add(new JScrollPane(t), BorderLayout.CENTER); //check		
		this.setVisible(true);
	}
	
	@Override
	public void addSimError(int time, RoadMap map, List<Event> events, SimulatorError e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStep(int time, RoadMap map, List<Event> events) {

		_eventQueue = events;
		_eventTableModel.refresh();
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {

		_eventQueue = events;
		_eventTableModel.refresh();
	}

	@Override
	public void addReset(int time, RoadMap map, List<Event> events) {

		_eventQueue = events;
		_eventTableModel.refresh();
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {

		_eventQueue = events;
		_eventTableModel.refresh();
	}

}
