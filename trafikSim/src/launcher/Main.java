package launcher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

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
import gui.MainFrame;
import ini.Ini;
import model.TrafficSimulator;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static Integer _timeLimit = null;
	private static String _inFile = null;
	private static String _outFile = null;
	
	
	private static EventBuilder[] _eventBuilders = {
		new NewJunctionEventBuilder(),
		new NewRoadEventBuilder(), 
		new NewVehicleEventBuilder(),
		new MakeVehicleFaultyEventBuilder(),
		new NewBikeEventBuilder(),
		new NewCarEventBuilder(),
		new NewDirtRoadEventBuilder(),
		new NewLanesRoadEventBuilder(),
		new NewMostCrowdedJunctionEventBuilder(),
		new NewRoundRobinEventBuilder()
	};
	private static int mode;
	

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseStepsOption(line);
			parseModeOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!

			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			// new Piece(...) might throw GameError exception
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg()
				.desc("Ticks to execute the simulator's main loop (default value is " + _timeLimitDefaultValue + ").")
				.build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Graphical representation option.").build());


		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		
		_inFile = line.getOptionValue("i");
		if (_inFile == null) {
			_inFile = line.getOptionValue("m");
			
			if(_inFile == null)
				throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}

	private static void parseStepsOption(CommandLine line) throws ParseException {
		String t = line.getOptionValue("t", _timeLimitDefaultValue.toString());
		try {
			_timeLimit = Integer.parseInt(t);
			assert (_timeLimit < 0);
		} catch (Exception e) {
			throw new ParseException("Invalid value for time limit: " + t);
		}
	}
	
	private static void parseModeOption(CommandLine line) throws ParseException {
		String value = line.getOptionValue("m");
		if(value.equals("batch"))
			mode = 0;
		else if(value.equals("gui"))
			mode = 1;
		else
			throw new ParseException("Invalid graphical representation mode");
			
	}

	
	/**
	 * This method run the simulator on all files that ends with .ini if the given
	 * path, and compares that output to the expected output. It assumes that for
	 * example "example.ini" the expected output is stored in "example.ini.eout".
	 * The simulator's output will be stored in "example.ini.out"
	 * 
	 * @throws IOException
	 */
	private static void test(String path) throws IOException {

		File dir = new File(path);

		if ( !dir.exists() ) {
			throw new FileNotFoundException(path);
		}
		
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ini");
			}
		});

		for (File file : files) {
			test(file.getAbsolutePath(), file.getAbsolutePath() + ".out", file.getAbsolutePath() + ".eout", 100);
		}

	}

	private static void test(String inFile, String outFile, String expectedOutFile, int timeLimit) throws IOException {
		_outFile = outFile;
		_inFile = inFile;
		_timeLimit = timeLimit;
		startBatchMode();
		boolean equalOutput = (new Ini(_outFile)).equals(new Ini(expectedOutFile));
		System.out.println("Result for: '" + _inFile + "' : "
				+ (equalOutput ? "OK!" : ("not equal to expected output +'" + expectedOutFile + "'")));
	}

	/**
	 * Run the simulator in batch mode
	 * 
	 * @throws IOException
	 */
	private static void startBatchMode() throws IOException {
		InputStream i = new FileInputStream(_inFile);
		OutputStream o;
		
		if(_outFile == null) 
			o = System.out;
		else
			o = new FileOutputStream(_outFile);
	
		TrafficSimulator trafikSim = new TrafficSimulator(o);
		
		Controller ctrler = new Controller(trafikSim);
		
		ctrler.setEventBuilders(_eventBuilders);
		ctrler.setOutputStream(o);
		ctrler.loadEvents(i);
		
		trafikSim.run(_timeLimit);
	}
	
	private static void startGUIMode() throws IOException {
		OutputStream o = null;
		
		TrafficSimulator trafikSim = new TrafficSimulator(o);
		Controller ctrler = new Controller(trafikSim);
		
		ctrler.setEventBuilders(_eventBuilders);
		ctrler.setOutputStream(o);
				
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame(ctrler, trafikSim, _inFile);
			}
		});
	}
	

	private static void start(String[] args) throws IOException {
		parseArgs(args);
		
		switch(mode){
			case 0:
			startBatchMode();
			break;
			case 1:
			startGUIMode();
			break;
		}
		
	}

	public static void main(String[] args) throws IOException, InvocationTargetException, InterruptedException {

		// example command lines:
		// --help
		// -i cv_docs/examples/basic/04_faultyVehicle.ini -t 10 -o cv_docs/examples/basic/04_faultyVehicle.ini.out
		// -i cv_docs/100tickinis/advanced/11_car.ini -t 100 -o cv_docs/100tickinis/advanced/11_car.ini.out
	
		
		// -i cv_docs/100tickinis/advanced/13_lanesRoad.ini -m gui

		// Call start to start the simulator from command line, etc.
		start(args);
//		test("cv_docs/100tickinis/advanced");
		//test("cv_docs/100tickinis/err");	//cant test err file since there is no .eout
		}

}