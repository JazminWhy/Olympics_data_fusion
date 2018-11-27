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
package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers;

import java.time.LocalDateTime;

import de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model.Athlete;
import de.uni_mannheim.informatik.dws.winter.datafusion.AttributeValueFuser;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.meta.FavourSources;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;

/**
 * {@link AttributeValueFuser} for the birthday of {@link Athlete}s. This fuser
 * is based on {@link FavourSources}
 * 
 * @author Hendrik Roeder
 * 
 */
public class BirthdayFuserFavourSource extends AttributeValueFuser<LocalDateTime, Athlete, Attribute> {

	public BirthdayFuserFavourSource() {
		super(new FavourSources<LocalDateTime, Athlete, Attribute>());
	}

	public boolean hasValue(Athlete record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Athlete.BIRTHDAY);
	}

	public LocalDateTime getValue(Athlete record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getBirthday();
	}

	public void fuse(RecordGroup<Athlete, Attribute> group, Athlete fusedRecord,
			Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		FusedValue<LocalDateTime, Athlete, Attribute> fused = getFusedValue(group, schemaCorrespondences,
				schemaElement);
		fusedRecord.setBirthday(fused.getValue());
		fusedRecord.setAttributeProvenance(Athlete.BIRTHDAY, fused.getOriginalIds());
	}

}
