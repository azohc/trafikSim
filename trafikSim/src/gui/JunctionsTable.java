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
import model.Junction;
import model.RoadMap;
import model.TrafficSimulator;

@SuppressWarnings("serial")
public class JunctionsTable extends JPanel implements TrafficSimulatorObserver {

	public static Border defaultBorder = BorderFactory.createLineBorder(Color.black, 2);
	
	class JunctionsTableModel extends AbstractTableModel{

		private final String[] header = {"ID", "Green", "Red"};
		
		@Override
		public String getColumnName(int pos) {
			return header[pos];	
		}
		
		@Override
		public int getColumnCount() {
			return header.length;
		}

		@Override
		public int getRowCount() {
			return _map.getJunctions().size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Junction j = _map.getJunctions().get(rowIndex);

			switch(columnIndex) {
				case 0:	return j.getId();
				case 1:	{
					if(j.getGreenLightIndex() != -1)
						return "[" + j.getRoadsInfo().get(j.getGreenLightIndex()) + "]";
					return  "[]"; 
				}
				case 2:	{
					String s = "[";
								
					if(j.getRoadsInfo().isEmpty())
						return "[]";
					
					for(int i = 0 ; i < j.getRoadsInfo().size() - 1; i++)	
						if(!j.getRoadsInfo().get(i).hasGreenLight())
							s += j.getRoadsInfo().get(i) + ",";

					if(s.equals("["))
						return "[]";
					
					return s.substring(0, s.length() - 1) + "]";
				}
				default: return null;
			}
		}
		
		void refresh() {
			fireTableDataChanged();
		}
		
	}
	
	private RoadMap _map;
	private JunctionsTableModel _junctsModel;
	
	public JunctionsTable(TrafficSimulator model) {
		_map = null;
		initGUI();
		model.addObserver(this);
	}
	
	private void initGUI() {
		this.setBorder(new TitledBorder(defaultBorder, "Junctions"));
		this.setLayout(new BorderLayout());
		_junctsModel = new JunctionsTableModel();
		
		JTable t = new JTable(_junctsModel);
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
		_junctsModel.refresh();
	}

	@Override
	public void addEvent(int time, RoadMap map, List<Event> events) {
		_map = map;
		_junctsModel.refresh();
	}

	@Override
	public void addReset(int time, RoadMap map, List<Event> events) {
		_map = map;
		_junctsModel.refresh();
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		_map = map;
		_junctsModel.refresh();

	}
	
}
