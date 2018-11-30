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

import java.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Athlete;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

/**
 * {@link XMLFormatter} for {@link Athlete}s.
 * 
 * @author Jasmin Weimueller & Marius Bock & Hendrik Roeder
 * 
 */
public class AthleteXMLFormatter extends XMLFormatter<Athlete> {

	OlympicParticipationXMLFormatter OlympicParticipationFormatter = new OlympicParticipationXMLFormatter();

	@Override
	public Element createRootElement(Document doc) {
		return doc.createElement("WinningAthletes");
	}

	@Override
	public Element createElementFromRecord(Athlete record, Document doc) {
		Element athlete = doc.createElement("Athlete");

		athlete.appendChild(createTextElement("ID", record.getIdentifier(), doc));

		athlete.appendChild(createTextElementWithProvenance("Name", record.getName(),
				record.getMergedAttributeProvenance(Athlete.NAME), doc));

		if (record.getBirthday() == null) {
			athlete.appendChild(createTextElement("Birthday", "", doc));
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String birthday = record.getBirthday().format(formatter);

			athlete.appendChild(createTextElementWithProvenance("Birthday", birthday,
					record.getMergedAttributeProvenance(Athlete.BIRTHDAY), doc));
		}
		athlete.appendChild(createTextElementWithProvenance("PlaceOfBirth", record.getPlaceOfBirth(),
				record.getMergedAttributeProvenance(Athlete.PLACEOFBIRTH), doc));

		athlete.appendChild(createTextElementWithProvenance("Sex", record.getSex(),
				record.getMergedAttributeProvenance(Athlete.SEX), doc));

		athlete.appendChild(createTextElementWithProvenance("Nationality", record.getNationality(),
				record.getMergedAttributeProvenance(Athlete.NATIONALITY), doc));

		if (record.getWeight() != null) {
			athlete.appendChild(createTextElementWithProvenance("Weight", Double.toString(record.getWeight()),
					record.getMergedAttributeProvenance(Athlete.WEIGHT), doc));
		} else {
			athlete.appendChild(createTextElementWithProvenance("Weight", null,
					record.getMergedAttributeProvenance(Athlete.WEIGHT), doc));
		}
		if (record.getHeight() != null) {
			athlete.appendChild(createTextElementWithProvenance("Height", Double.toString(record.getHeight()),
					record.getMergedAttributeProvenance(Athlete.HEIGHT), doc));
		} else {
			athlete.appendChild(createTextElementWithProvenance("Height", null,
					record.getMergedAttributeProvenance(Athlete.HEIGHT), doc));
		}

		athlete.appendChild(createOlympicParticipationsElement(record, doc));

		return athlete;
	}

	protected Element createTextElementWithProvenance(String name, String value, String provenance, Document doc) {
		Element elem = createTextElement(name, value, doc);
		elem.setAttribute("provenance", provenance);
		return elem;
	}

	protected Element createOlympicParticipationsElement(Athlete record, Document doc) {
		Element OlympicParticipationsRoot = OlympicParticipationFormatter.createRootElement(doc);
		OlympicParticipationsRoot.setAttribute("provenance",
				record.getMergedAttributeProvenance(Athlete.OLYMPICPARTICIPATIONS));

		for (OlympicParticipation o : record.getOlympicParticipations()) {
			OlympicParticipationsRoot.appendChild(OlympicParticipationFormatter.createElementFromRecord(o, doc));
		}

		return OlympicParticipationsRoot;
	}
}
