package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

import org.apache.logging.log4j.Logger;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.ActorsEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.DateEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.DirectorEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.TitleEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.ActorsFuserUnion;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.DateFuserVoting;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.DirectorFuserLongestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.TitleFuserShortestString;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Athlete;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.AthleteXMLReader;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.FusibleAthleteFactory;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Athlete;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.AthleteXMLFormatter;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.AthleteXMLReader;
import de.uni_mannheim.informatik.dws.winter.datafusion.CorrespondenceSet;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEngine;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionEvaluator;
import de.uni_mannheim.informatik.dws.winter.datafusion.DataFusionStrategy;
import de.uni_mannheim.informatik.dws.winter.model.DataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleDataSet;
import de.uni_mannheim.informatik.dws.winter.model.FusibleHashedDataSet;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroupFactory;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.utils.WinterLogManager;

public class AthleteFusingApp 
{
	/*
	 * Logging Options:
	 * 		default: 	level INFO	- console
	 * 		trace:		level TRACE     - console
	 * 		infoFile:	level INFO	- console/file
	 * 		traceFile:	level TRACE	- console/file
	 *  
	 * To set the log level to trace and write the log to winter.log and console, 
	 * activate the "traceFile" logger as follows:
	 *     private static final Logger logger = WinterLogManager.activateLogger("traceFile");
	 *
	 */

	private static final Logger logger = WinterLogManager.activateLogger("trace");
	
	public static void main( String[] args ) throws Exception
    {
		// Load the Data into FusibleDataSet
		System.out.println("*\n*\tLoading datasets\n*");
		
		FusibleDataSet<Athlete, Attribute> ds1 = new FusibleHashedDataSet<>();
		new AthleteXMLReader().loadFromXML(new File("data/input/20181029_figshare_Final.xml"), "/WinningAthletes/Athlete", ds1);		
		ds1.printDataSetDensityReport();
		
		FusibleDataSet<Athlete, Attribute> ds2 = new FusibleHashedDataSet<>();
		new AthleteXMLReader().loadFromXML(new File("data/input/20181027_Kaggle_Final.xml"), "/WinningAthletes/Athlete", ds2);
		//ds2.printDataSetDensityReport();

		FusibleDataSet<Athlete, Attribute> ds3 = new FusibleHashedDataSet<>();
		new AthleteXMLReader().loadFromXML(new File("data/input/20181025_Rio_Final.xml"), "/WinningAthletes/Athlete", ds3);
		//ds3.printDataSetDensityReport();

		FusibleDataSet<Athlete, Attribute> ds4 = new FusibleHashedDataSet<>();
		new AthleteXMLReader().loadFromXML(new File("data/input/20181025_DBpedia_Final.xml"), "/WinningAthletes/Athlete", ds4);
		//ds4.printDataSetDensityReport();

		FusibleDataSet<Athlete, Attribute> ds5 = new FusibleHashedDataSet<>();
		new AthleteXMLReader().loadFromXML(new File("data/input/20181027_Gymnasts_Final.xml"), "/WinningAthletes/Athlete", ds5);
		//ds5.printDataSetDensityReport();

		FusibleDataSet<Athlete, Attribute> ds6 = new FusibleHashedDataSet<>();
		new AthleteXMLReader().loadFromXML(new File("data/input/20181102_FieldAthletes_Final.xml"), "/WinningAthletes/Athlete", ds6);
		//ds6.printDataSetDensityReport();
		
		// Maintain Provenance
		// Scores (e.g. from rating)
		ds1.setScore(1.0);
		ds2.setScore(1.0);
		ds3.setScore(1.0);
		ds4.setScore(1.0);
		ds5.setScore(1.0);
		ds6.setScore(1.0);

		// Date (e.g. last update)
		DateTimeFormatter formatter = new DateTimeFormatterBuilder()
		        .appendPattern("yyyy-MM-dd")
		        .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0)
		        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
		        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
		        .toFormatter(Locale.ENGLISH);
		
		//ds1.setDate(LocalDateTime.parse("2012-01-01", formatter));
		//ds2.setDate(LocalDateTime.parse("2010-01-01", formatter));
		//ds3.setDate(LocalDateTime.parse("2008-01-01", formatter));
		//ds4.setDate(LocalDateTime.parse("2018-10-01", formatter));
		//ds5.setDate(LocalDateTime.parse("2010-01-01", formatter));
		//ds6.setDate(LocalDateTime.parse("2008-01-01", formatter));

		// load correspondences
		System.out.println("*\n*\tLoading correspondences\n*");
		CorrespondenceSet<Athlete, Attribute> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(new File("data/correspondences/FINAL_kaggle_figshare_correspondences.csv"),ds2, ds1);
		correspondences.loadCorrespondences(new File("data/correspondences/rio_figshare_ML_correspondences.csv"),ds3, ds1);
		correspondences.loadCorrespondences(new File("data/correspondences/dbpedia_figshare_Athlete_correspondences.csv"),ds4, ds1);
		correspondences.loadCorrespondences(new File("data/correspondences/gymnast_figshare_ML_correspondences.csv"),ds5, ds1);
		correspondences.loadCorrespondences(new File("data/correspondences/field_figshare_ML_correspondences.csv"),ds6, ds1);
		
		// write group size distribution
		correspondences.printGroupSizeDistribution();
/*
		// load the gold standard
		System.out.println("*\n*\tEvaluating results\n*");
		DataSet<Athlete, Attribute> gs = new FusibleHashedDataSet<>();
		new AthleteXMLReader().loadFromXML(new File("data/goldstandard/gold.xml"), "/Athletes/Athlete", gs);

		for(Athlete m : gs.get()) {
			System.out.println(String.format("gs: %s", m.getIdentifier()));
		}

		// define the fusion strategy
		DataFusionStrategy<Athlete, Attribute> strategy = new DataFusionStrategy<>(new FusibleAthleteFactory());
		// write debug results to file
		strategy.activateDebugReport("data/output/debugResultsDatafusion.csv", -1, gs);
		
		// add attribute fusers
		strategy.addAttributeFuser(Athlete.TITLE, new TitleFuserShortestString(),new TitleEvaluationRule());
		strategy.addAttributeFuser(Athlete.DIRECTOR,new DirectorFuserLongestString(), new DirectorEvaluationRule());
		strategy.addAttributeFuser(Athlete.DATE, new DateFuserVoting(),new DateEvaluationRule());
		strategy.addAttributeFuser(Athlete.ACTORS,new ActorsFuserUnion(),new ActorsEvaluationRule());
		
		// create the fusion engine
		DataFusionEngine<Athlete, Attribute> engine = new DataFusionEngine<>(strategy);

		// print consistency report
		engine.printClusterConsistencyReport(correspondences, null);
		
		// print record groups sorted by consistency
		engine.writeRecordGroupsByConsistency(new File("data/output/recordGroupConsistencies.csv"), correspondences, null);

		// run the fusion
		System.out.println("*\n*\tRunning data fusion\n*");
		FusibleDataSet<Athlete, Attribute> fusedDataSet = engine.run(correspondences, null);

		// write the result
		new AthleteXMLFormatter().writeXML(new File("data/output/fused.xml"), fusedDataSet);

		// evaluate
		DataFusionEvaluator<Athlete, Attribute> evaluator = new DataFusionEvaluator<>(strategy);
		
		double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

		System.out.println(String.format("Accuracy: %.2f", accuracy));*/
    }
}
