package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.logging.log4j.Logger;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.BirthdayEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.HeightEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.NameEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.NationalityEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.OlympicParticipationsEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.PlaceOfBirthEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.SexEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation.WeightEvaluationRule;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.BirthdayFuserFavourSource;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.BirthdayFuserVoting;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.FavourSources_Participation;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.HeightFuserAverage;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.HeightFuserMedian;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.HeightFuserVoting;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.NameFuserFavourSource;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.NameFuserLongest;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.NameFuserShortest;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.NationalityFuserVoting;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.ParticipationFuserFavourSource;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.PoBFuserFavourSource;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.SexFuserVoting;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.WeightFuserAverage;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.WeightFuserMedian;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.WeightFuserMostRecent;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers.WeightFuserVoting;
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

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.OlympicParticipation;

public class AthleteFusingApp {
	/**
	* Application to run the data fusion
	* 
	* @author Hendrik Roeder & Tido Felix Marschall
	* 
	*/

	private static final Logger logger = WinterLogManager.activateLogger("trace");

	public static void main(String[] args) throws Exception {
		// Load the Data into FusibleDataSet
		System.out.println("*\n*\tLoading datasets\n*");

		FusibleDataSet<Athlete, Attribute> ds1 = new FusibleHashedDataSet<>();
		new AthleteXMLReader().loadFromXML(new File("data/input/20181029_figshare_Final.xml"),
				"/WinningAthletes/Athlete", ds1);
		ds1.printDataSetDensityReport();

		FusibleDataSet<Athlete, Attribute> ds2 = new FusibleHashedDataSet<>();
		new AthleteXMLReader().loadFromXML(new File("data/input/20181027_Kaggle_Final.xml"), "/WinningAthletes/Athlete",
				ds2);
		ds2.printDataSetDensityReport();

		FusibleDataSet<Athlete, Attribute> ds3 = new FusibleHashedDataSet<>();
		new AthleteXMLReader().loadFromXML(new File("data/input/20181025_Rio_Final.xml"), "/WinningAthletes/Athlete",
				ds3);
		ds3.printDataSetDensityReport();

		FusibleDataSet<Athlete, Attribute> ds4 = new FusibleHashedDataSet<>();
		new AthleteXMLReader().loadFromXML(new File("data/input/20181025_DBpedia_Final.xml"),
				"/WinningAthletes/Athlete", ds4);
		ds4.printDataSetDensityReport();

		FusibleDataSet<Athlete, Attribute> ds5 = new FusibleHashedDataSet<>();
		new AthleteXMLReader().loadFromXML(new File("data/input/20181027_Gymnasts_Final.xml"),
				"/WinningAthletes/Athlete", ds5);
		ds5.printDataSetDensityReport();

		FusibleDataSet<Athlete, Attribute> ds6 = new FusibleHashedDataSet<>();
		new AthleteXMLReader().loadFromXML(new File("data/input/20181102_FieldAthletes_Final.xml"),
				"/WinningAthletes/Athlete", ds6);
		ds6.printDataSetDensityReport();

		// Maintain Provenance
		// Scores (e.g. from rating)
		// Don't change!
		ds1.setScore(2); // figshare 2
		ds2.setScore(1); // Kaggle 1
		ds3.setScore(5); // Rio 5
		ds4.setScore(3); // DBpedia 3
		ds5.setScore(4); // Gymnasts 4
		ds6.setScore(6); // FieldAthletes 6

		// Date (e.g. last update)
		DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd")
				.parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0).parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
				.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0).toFormatter(Locale.ENGLISH);

		ds1.setDate(LocalDateTime.parse("2016-08-21", formatter)); // figshare (data until Olympics 2016)
		ds2.setDate(LocalDateTime.parse("2014-07-14", formatter)); // Kaggle (only data until 2014, but updated 2017)
		ds3.setDate(LocalDateTime.parse("2016-08-22", formatter)); // Rio (also until Olympia 2016, but maybe more recent)
		ds4.setDate(LocalDateTime.parse("2014-01-02", formatter)); // DBpedia (assume older than official data)
		ds5.setDate(LocalDateTime.parse("2014-01-01", formatter)); // Gymnast (assume older than official data 
																	//and older than Wikipedia fact tables)
		ds6.setDate(LocalDateTime.parse("2018-10-15", formatter)); // Field (Official organization -> continuously
																	// updated -> use day of download)

		// load correspondences
		System.out.println("*\n*\tLoading correspondences\n*");
		CorrespondenceSet<Athlete, Attribute> correspondences = new CorrespondenceSet<>();
		correspondences.loadCorrespondences(new File("data/correspondences/kaggle_figshare_ML_correspondences.csv"),
				ds2, ds1);
		correspondences.loadCorrespondences(new File("data/correspondences/rio_figshare_ML_correspondences.csv"), ds3,
				ds1);
		correspondences.loadCorrespondences(
				new File("data/correspondences/DBpedia_figshare_LC_correspondences_5.csv"), ds4, ds1);
		
		correspondences.loadCorrespondences(new File("data/correspondences/gymnasts_figshare_ML_correspondences.csv"),
				ds5, ds1);
		correspondences.loadCorrespondences(new File("data/correspondences/field_figshare_ML_correspondences.csv"), ds6,
				ds1);
		correspondences.loadCorrespondences(new File("data/correspondences/figshare_self_correspondences.csv"), ds1,
				ds1);

		// write group size distribution
		correspondences.printGroupSizeDistribution();

		// load the gold standard
		System.out.println("*\n*\tEvaluating results\n*");
		DataSet<Athlete, Attribute> gs = new FusibleHashedDataSet<>();
		new AthleteXMLReader().loadFromXML(new File("data/goldstandard/GS_Fusion.xml"), "/WinningAthletes/Athlete", gs);

		for (Athlete a : gs.get()) {
			System.out.println(String.format("gs: %s", a.getIdentifier()));
		}
		
		//Checking for elements in OlympicParticipation
		
		// define the fusion strategy
		DataFusionStrategy<Athlete, Attribute> strategy = new DataFusionStrategy<>(new FusibleAthleteFactory());
		// write debug results to file
		strategy.activateDebugReport("data/output/debugResultsDatafusion.csv", -1, gs);

		// add attribute fusers
		
		//Name Fuser
		strategy.addAttributeFuser(Athlete.NAME, new NameFuserFavourSource(), new NameEvaluationRule());
		//strategy.addAttributeFuser(Athlete.NAME, new NameFuserShortest(), new NameEvaluationRule());
		//strategy.addAttributeFuser(Athlete.NAME, new NameFuserLongest(), new NameEvaluationRule());
		
		//BirthdayFuser
		strategy.addAttributeFuser(Athlete.BIRTHDAY, new BirthdayFuserFavourSource(), new BirthdayEvaluationRule());
		//strategy.addAttributeFuser(Athlete.BIRTHDAY, new BirthdayFuserVoting(), new BirthdayEvaluationRule());
		
		//PlaceOfBirthFuser  -> DONE
		strategy.addAttributeFuser(Athlete.PLACEOFBIRTH, new PoBFuserFavourSource(), new PlaceOfBirthEvaluationRule());
		
		//SexFuser -> DONE
		strategy.addAttributeFuser(Athlete.SEX, new SexFuserVoting(), new SexEvaluationRule());
		
		//NationalityFuser -> DONE
		strategy.addAttributeFuser(Athlete.NATIONALITY, new NationalityFuserVoting(), new NationalityEvaluationRule());
		
		//WeightFuser
		strategy.addAttributeFuser(Athlete.WEIGHT, new WeightFuserVoting(), new WeightEvaluationRule());
		//strategy.addAttributeFuser(Athlete.WEIGHT, new WeightFuserAverage(), new WeightEvaluationRule());
		//strategy.addAttributeFuser(Athlete.WEIGHT, new WeightFuserMedian(), new WeightEvaluationRule());
		//strategy.addAttributeFuser(Athlete.WEIGHT, new WeightFuserMostRecent(), new WeightEvaluationRule());
		
		//HeightFuser
		strategy.addAttributeFuser(Athlete.HEIGHT, new HeightFuserVoting(), new HeightEvaluationRule());
		//strategy.addAttributeFuser(Athlete.HEIGHT, new HeightFuserMedian(), new HeightEvaluationRule());
		//strategy.addAttributeFuser(Athlete.HEIGHT, new HeightFuserAverage(), new HeightEvaluationRule());
		
		//ParticipationFuser -> DONE
		strategy.addAttributeFuser(Athlete.OLYMPICPARTICIPATIONS, new ParticipationFuserFavourSource(),
				new OlympicParticipationsEvaluationRule());

		// create the fusion engine
		DataFusionEngine<Athlete, Attribute> engine = new DataFusionEngine<>(strategy);
		//

		// print consistency report
		engine.printClusterConsistencyReport(correspondences, null);

		// print record groups sorted by consistency
		engine.writeRecordGroupsByConsistency(new File("data/output/recordGroupConsistencies.csv"), correspondences,
				null);

		// run the fusion
		System.out.println("*\n*\tRunning data fusion\n*");
		FusibleDataSet<Athlete, Attribute> fusedDataSet = engine.run(correspondences, null);

		// write the result
		new AthleteXMLFormatter().writeXML(new File("data/output/fused.xml"), fusedDataSet);
		fusedDataSet.printDataSetDensityReport();

		// evaluate
		DataFusionEvaluator<Athlete, Attribute> evaluator = new DataFusionEvaluator<>(strategy);

		double accuracy = evaluator.evaluate(fusedDataSet, gs, null);

		System.out.println(String.format("Accuracy: %.2f", accuracy));
		
	}
}
