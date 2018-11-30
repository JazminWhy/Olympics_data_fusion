/*
 * Copyright (c) 2017 Data and Web Science Group, University of Mannheim, Germany (http://dws.informatik.uni-mannheim.de/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.evaluation;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Athlete;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;
import de.uni_mannheim.informatik.dws.winter.similarity.string.MaximumOfTokenContainment;

/**
 * {@link EvaluationRule} for the name of {@link Athlete}s. The rule compares
 * the name of two {@link Athlete}s and returns true in case their similarity
 * based on {@link MaximumOfTokenContainment} is 1.0. This means that either the
 * fused name is completely included in the gold standard or vice versa.
 * 
 * @author Hendrik Roeder
 * 
 */
public class NameEvaluationRule extends EvaluationRule<Athlete, Attribute> {

	SimilarityMeasure<String> sim = new MaximumOfTokenContainment();

	@Override
	public boolean isEqual(Athlete record1, Athlete record2, Attribute schemaElement) {
		return sim.calculate(record1.getName(), record2.getName()) == 1.0;
	}

	public boolean isEqual(Athlete record1, Athlete record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute) null);
	}

}
