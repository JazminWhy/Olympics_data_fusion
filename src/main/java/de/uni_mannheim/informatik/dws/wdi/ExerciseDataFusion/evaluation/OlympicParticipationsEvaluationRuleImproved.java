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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Athlete;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.OlympicParticipation;
import de.uni_mannheim.informatik.dws.winter.datafusion.EvaluationRule;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * {@link EvaluationRule} for the {@link OlympicParticipation}s of
 * {@link Athlete}s. The rule compares the full set of participations of two
 * {@link Athlete}s and returns true, in case year, season,, city, team,
 * discipline, event and medal are correct for all participations To enable
 * this, the gold standard uses the same terminology for Disciplines and Events
 * that is also used by our central dataset. The team names use the official
 * Olympic country codes.
 * 
 * @author Hendrik Roeder
 * 
 */
public class OlympicParticipationsEvaluationRuleImproved extends EvaluationRule<Athlete, Attribute> {

	@Override
	public boolean isEqual(Athlete record1, Athlete record2, Attribute schemaElement) {
		List<OlympicParticipation> a_list = record1.getOlympicParticipations();
		List<OlympicParticipation> b_list = record2.getOlympicParticipations();

		int same = 0;

		int total = Math.max(a_list.size(), b_list.size());
		for (OlympicParticipation a : a_list) {
			String medal_a = a.getMedal();
			int year_a = a.getYear();
			String team_a = a.getOlympicTeam();
			String discipline_a = a.getDisciplines();
			String event_a = a.getEvent();
			String participation_a = medal_a + year_a + team_a + discipline_a + event_a;
			for (OlympicParticipation b : b_list) {
				String medal_b = b.getMedal();
				int year_b = b.getYear();
				String team_b = b.getOlympicTeam();
				String discipline_b = b.getDisciplines();
				String event_b = b.getEvent();
				String participation_b = medal_b + year_b + team_b + discipline_b + event_b;
				if (participation_a.equals(participation_b)) {
					same++;
					b_list.remove(b);
					break;
				}
			}
		}

		double similarity = same / total;
		return similarity == 1.0;

	}

	public boolean isEqual(Athlete record1, Athlete record2,
			Correspondence<Attribute, Matchable> schemaCorrespondence) {
		return isEqual(record1, record2, (Attribute) null);
	}

}
