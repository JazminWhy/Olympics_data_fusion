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

import java.util.HashSet;
import java.util.Set;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Athlete;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.OlympicParticipation;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * {@link EvaluationRule} for the actors of {@link Movie}s. The rule simply
 * compares the full set of actors of two {@link Movie}s and returns true, in
 * case they are identical.
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class OlympicParticipationsEvaluationRule extends EvaluationRule<Athlete, Attribute> {

	@Override
	public boolean isEqual(Athlete record1, Athlete record2, Attribute schemaElement) {
		Set<String> olympicParticipations1 = new HashSet<>();

		for (OlympicParticipation o : record1.getOlympicParticipations()) {
			olympicParticipations1.add(o.getYear()+o.getSeason()+o.getCity()+o.getOlympicTeam()+o.getDisciplines()+o.getEvent()+o.getMedal());
		}

		Set<String> olympicParticipations2 = new HashSet<>();

		for (OlympicParticipation o : record2.getOlympicParticipations()) {
			olympicParticipations2.add(o.getYear()+o.getSeason()+o.getCity()+o.getOlympicTeam()+o.getDisciplines()+o.getEvent()+o.getMedal());
		}

		return olympicParticipations1.containsAll(olympicParticipations2) &&
				olympicParticipations2.containsAll(olympicParticipations1);
	}

	/* (non-Javadoc)
	 * @see de.uni_mannheim.informatik.wdi.datafusion.EvaluationRule#isEqual(java.lang.Object, java.lang.Object, de.uni_mannheim.informatik.wdi.model.Correspondence)
	 */
	@Override
	public boolean isEqual(Athlete record1, Athlete record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute)null);
	}

}
