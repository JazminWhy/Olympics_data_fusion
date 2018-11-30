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
package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model;

import org.w3c.dom.Node;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLMatchableReader;

/**
 * A {@link XMLMatchableReader} for {@link OlympicParticipation}s.
 * 
 * @author Jasmin Weimueller & Marius Bock & Hendrik Roeder
 * 
 */
public class OlympicParticipationXMLReader extends XMLMatchableReader<OlympicParticipation, Attribute> {

	@Override
	public OlympicParticipation createModelFromElement(Node node, String provenanceInfo) {
		String id = getValueFromChildElement(node, "ID");
		
		// create the object with id and provenance information
		OlympicParticipation olympicparticipation = new OlympicParticipation(id, provenanceInfo);

		// fill the attributes
		olympicparticipation.setYear(Integer.parseInt(getValueFromChildElement(node, "Year")));
		olympicparticipation.setSeason(getValueFromChildElement(node, "Season"));
		olympicparticipation.setCity(getValueFromChildElement(node, "City"));
		olympicparticipation.setOlympicTeam(getValueFromChildElement(node, "OlympicTeam"));
		olympicparticipation.setDisciplines(getValueFromChildElement(node, "Disciplines"));
		olympicparticipation.setEvent(getValueFromChildElement(node, "Event"));
		olympicparticipation.setMedal(getValueFromChildElement(node, "Medal"));

		// convert the date string into a DateTime object
		try {
			String year = getValueFromChildElement(node, "Year");
			if (year != null && !year.isEmpty()) {
				int dt = Integer.parseInt(year);
				olympicparticipation.setYear(dt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return olympicparticipation;
	}

}
