package de.uni_mannheim.informatik.dws.wdi.ExerciseDataFusion.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import de.uni_mannheim.informatik.dws.winter.model.FusibleFactory;
import de.uni_mannheim.informatik.dws.winter.model.RecordGroup;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;

/**
 * A {@link FusibleFactory} for {@link Athlete}s
 * 
 * @author Hendrik Roeder and Tido Felix Marschall
 * 
 */
public class FusibleAthleteFactory implements FusibleFactory<Athlete, Attribute> {

	@Override
	public Athlete createInstanceForFusion(RecordGroup<Athlete, Attribute> cluster) {

		List<String> ids = new LinkedList<>();

		for (Athlete a : cluster.getRecords()) {
			ids.add(a.getIdentifier());
		}

		Collections.sort(ids);

		String mergedId = StringUtils.join(ids, '+');

		return new Athlete(mergedId, "fused");
	}
	
}
