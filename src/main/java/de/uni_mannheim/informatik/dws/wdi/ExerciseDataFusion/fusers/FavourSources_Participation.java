package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.fusers;

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


import java.util.Collection;

import de.uni_mannheim.informatik.dws.winter.datafusion.conflictresolution.ConflictResolutionFunction;
import de.uni_mannheim.informatik.dws.winter.model.FusedValue;
import de.uni_mannheim.informatik.dws.winter.model.Fusible;
import de.uni_mannheim.informatik.dws.winter.model.FusibleValue;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;

/**
 * Customization to use different dataset scores for participations than for other attributes
 * Favour sources {@link ConflictResolutionFunction}: returns the value from the
 * dataset with the highest data set score, which can represent the rating of
 * this dataset or any other score
 * 
 * @author Tido Felix Marschall
 * 
 * @param <ValueType>	the type of the values that are fused
 * @param <RecordType>	the type that represents a record
 */
public class FavourSources_Participation<ValueType, RecordType extends Matchable & Fusible<SchemaElementType>, SchemaElementType extends Matchable>
		extends ConflictResolutionFunction<ValueType, RecordType, SchemaElementType> {

	@Override
	public FusedValue<ValueType, RecordType, SchemaElementType> resolveConflict(
			Collection<FusibleValue<ValueType, RecordType, SchemaElementType>> values) {

		FusibleValue<ValueType, RecordType, SchemaElementType> highestScore = null;
		int highestScore_num = 0;

		for (FusibleValue<ValueType, RecordType, SchemaElementType> value : values) {
			int newScore = 0;
			int valueInt = (int)(value.getDataSourceScore());
			switch (valueInt) {
				case 1: newScore = 5;
				break;
				case 2: newScore = 6;
				break;
				case 3: newScore = 3;
				break;
				case 4: newScore = 2;
				break;
				case 5: newScore = 4;
				break;
				case 6: newScore = 1;
				break;
				default: newScore = 0;
				break;
			}
			
			if (highestScore_num == 0
					|| newScore  > highestScore_num) {
				highestScore = value;
				highestScore_num = newScore;
			}
		}

		return new FusedValue<>(highestScore);
	}

}

