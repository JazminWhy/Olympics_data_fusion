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
import de.uni_mannheim.informatik.dws.winter.similarity.string.LevenshteinEditDistance;

/**
 * {@link EvaluationRule} for the nationality of {@link Athlete}s. The rule
 * calculates the edit-distance for the nationality of two {@link Athlete}s and
 * returns true, in case their distance based on {@link LevenshteinEditDistance}
 * is 1 or less This accounts for possible typos in the nationalities, but can
 * confuse Ireland and Iceland.
 * 
 * @author Hendrik Roeder
 * 
 */
public class NationalityEvaluationRule extends EvaluationRule<Athlete, Attribute> {

	private LevenshteinEditDistance dist = new LevenshteinEditDistance();

	public boolean isEqual(Athlete record1, Athlete record2, Attribute schemaElement) {
		return dist.calculate(record1.getNationality(), record2.getNationality()) <= 1.0;
	}

	public boolean isEqual(Athlete record1, Athlete record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute) null);
	}

}
