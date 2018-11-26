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
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ClusteredVote;
import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.Voting;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
import de.uni_mannheim.informatik.dws.winter.processing.Processable;
import de.uni_mannheim.informatik.dws.winter.similarity.SimilarityMeasure;
import de.uni_mannheim.informatik.dws.winter.similarity.numeric.AbsoluteDifferenceSimilarity;
/**
 * {@link AttributeValueFuser} for the date of {@link Movie}s. 
 * 
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class HeightFuserVoting extends AttributeValueFuser<Double, Athlete, Attribute> {

	public HeightFuserVoting() {
		//super(new ClusteredVoteCustom<Double, Athlete, Attribute>(new AbsoluteDifferenceSimilarity(100), 0.00001));
		super(new Voting<Double, Athlete, Attribute>());
	}
	
	@Override
	public boolean hasValue(Athlete record, Correspondence<Attribute, Matchable> correspondence) {
		return record.hasValue(Athlete.HEIGHT);
	}
	
	@Override
	public Double getValue(Athlete record, Correspondence<Attribute, Matchable> correspondence) {
		return record.getHeight();
	}

	@Override
	public void fuse(RecordGroup<Athlete, Attribute> group, Athlete fusedRecord, Processable<Correspondence<Attribute, Matchable>> schemaCorrespondences, Attribute schemaElement) {
		/*FusedValue<Double, Athlete, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		if (fused.getValue() == null) {
			Float f = null;
			fusedRecord.setHeight(f);
		}
		else {
			fusedRecord.setHeight((float)(double) fused.getValue());
		}
		fusedRecord.setAttributeProvenance(Athlete.HEIGHT, fused.getOriginalIds());*/
		FusedValue<Double, Athlete, Attribute> fused = getFusedValue(group, schemaCorrespondences, schemaElement);
		fusedRecord.setHeight(fused.getValue());
		fusedRecord.setAttributeProvenance(Athlete.HEIGHT, fused.getOriginalIds());
	}

}
