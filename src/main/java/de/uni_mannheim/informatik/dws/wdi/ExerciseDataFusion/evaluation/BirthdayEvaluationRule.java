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

/**
 * {@link EvaluationRule} for the birthday of {@link Athlete}s. The rule simply
 * compares the exact birthdays of two {@link Athlete}s.
 * 
 * @author Hendrik Roeder
 * 
 */
public class BirthdayEvaluationRule extends EvaluationRule<Athlete, Attribute> {

	@Override
	public boolean isEqual(Athlete record1, Athlete record2, Attribute schemaElement) {
		if (record1.getBirthday() == null && record2.getBirthday() == null)
			return true;
		else if (record1.getBirthday() == null ^ record2.getBirthday() == null)
			return false;
		else
			return record1.getBirthday().getYear() == record2.getBirthday().getYear();
	}

	public boolean isEqual(Athlete record1, Athlete record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute) null);
	}

}
