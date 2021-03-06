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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import de.uni_mannheim.informatik.dws.winter.model.AbstractRecord;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * A {@link AbstractRecord} representing an athlete.
 * 
 * @author Jasmin Weimueller & Marius Bock
 * 
 */
@SuppressWarnings("serial")
public class Athlete extends AbstractRecord<Attribute> implements Serializable {

	/*
	 * example entry <Athlete id="D-100253"> <Name>alain mimoun</Name>
	 * <Birthday>1921-01-01</Birthday> <PlaceOfBirth>algeria</PlaceOfBirth>
	 * <Sex>male</Sex> <Nationality>france</Nationality> <Weight>56</Weight>
	 * <Height>1.7</Height> <OlypmicParticipations> <OlypmicParticipation
	 * id="DP-100253"> </OlypmicParticipation> </OlypmicParticipations> </Athlete>
	 */
	
	//protected String id;
	//protected String provenance;
	private String Name;
	private LocalDateTime Birthday;
	private String PlaceOfBirth;
	private String Sex;
	private String Nationality;
	private Double Weight;
	private Double Height;
	private List<OlympicParticipation> OlympicParticipations;
	
	public Athlete(String identifier, String provenance) {
		super(identifier, provenance);
		//this.provenance = provenance;
		OlympicParticipations = new LinkedList<>();
	}

	public String getIdentifier() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public LocalDateTime getBirthday() {
		return Birthday;
	}

	public void setBirthday(LocalDateTime birthday) {
		Birthday = birthday;
	}

	public String getPlaceOfBirth() {
		return PlaceOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		PlaceOfBirth = placeOfBirth;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getNationality() {
		return Nationality;
	}

	public void setNationality(String nationality) {
		Nationality = nationality;
	}

	public Double getWeight() {
		return Weight;
	}

	public void setWeight(Double weight) {
		Weight = weight;
	}

	public Double getHeight() {
		return Height;
	}

	public void setHeight(Double height) {
		Height = height;
	}

	public List<OlympicParticipation> getOlympicParticipations() {
		return OlympicParticipations;
	}

	public void setOlympicParticipations(List<OlympicParticipation> olympicParticipations) {
		OlympicParticipations = olympicParticipations;
	} 

	/*
	 * @Override public String toString() { return
	 * String.format("[Movie %s: %s / %s / %s]", getIdentifier(), getTitle(),
	 * getDirector(), getDate().toString()); }
	 * 
	 * @Override public int hashCode() { return getIdentifier().hashCode(); }
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Athlete) {
			return this.getIdentifier().equals(((Athlete) obj).getIdentifier());
		} else
			return false;
	}
	private Map<Attribute, Collection<String>> provenance = new HashMap<>();
	private Collection<String> recordProvenance;

	public void setRecordProvenance(Collection<String> provenance) {
		recordProvenance = provenance;
	}

	public Collection<String> getRecordProvenance() {
		return recordProvenance;
	}

	public void setAttributeProvenance(Attribute attribute,
			Collection<String> provenance) {
		this.provenance.put(attribute, provenance);
	}

	@SuppressWarnings("unlikely-arg-type")
	public Collection<String> getAttributeProvenance(String attribute) {
		return provenance.get(attribute);
	}

	public String getMergedAttributeProvenance(Attribute attribute) {
		Collection<String> prov = provenance.get(attribute);

		if (prov != null) {
			return StringUtils.join(prov, "+");
		} else {
			return "";
		}
	}
	
	public static final Attribute NAME = new Attribute("Name");
	public static final Attribute BIRTHDAY = new Attribute("Birthday");
	public static final Attribute PLACEOFBIRTH = new Attribute("PlaceOfBirth");
	public static final Attribute SEX = new Attribute("Sex");
	public static final Attribute NATIONALITY = new Attribute("Nationality");
	public static final Attribute WEIGHT = new Attribute("Weight");
	public static final Attribute HEIGHT = new Attribute("Height");
	public static final Attribute OLYMPICPARTICIPATIONS = new Attribute("OlympicParticipations");	


	@Override
	public boolean hasValue(Attribute attribute) {
		if(attribute==NAME)
			return getName() != null && !getName().isEmpty();
		else if(attribute==BIRTHDAY)
			return getBirthday() != null;
		else if(attribute==PLACEOFBIRTH)
			return getPlaceOfBirth() != null && !getPlaceOfBirth().isEmpty();
		else if(attribute==SEX)
			return getSex() != null && !getSex().isEmpty();
		else if(attribute==NATIONALITY)
			return getNationality() != null && !getNationality().isEmpty();
		else if(attribute==WEIGHT)
			return Weight != null && !getWeight().isNaN();
		else if(attribute==HEIGHT)
			return Height != null && !getHeight().isNaN();
		else if(attribute==OLYMPICPARTICIPATIONS)
			return getOlympicParticipations() != null && getOlympicParticipations().size() > 0;
		
		return false;
	}

}
