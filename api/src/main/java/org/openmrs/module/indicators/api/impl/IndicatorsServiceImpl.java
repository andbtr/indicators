/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.indicators.api.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.openmrs.Concept;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.indicators.Item;
import org.openmrs.module.indicators.api.IndicatorsService;
import org.openmrs.module.indicators.api.dao.IndicatorsDao;

public class IndicatorsServiceImpl extends BaseOpenmrsService implements IndicatorsService {
	
	IndicatorsDao dao;
	
	UserService userService;

	ConceptService conceptService;
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(IndicatorsDao dao) {
		this.dao = dao;
	}
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setConceptService(ConceptService conceptService) {
		this.conceptService = conceptService;
	}
	
	@Override
	public Item getItemByUuid(String uuid) throws APIException {
		return dao.getItemByUuid(uuid);
	}
	
	@Override
	public Item saveItem(Item item) throws APIException {
		if (item.getOwner() == null) {
			item.setOwner(userService.getUser(1));
		}
		
		return dao.saveItem(item);
	}

	@Override
	public List<Concept> getLastConcepts(int limit) throws APIException {
		int safeLimit = Math.min(Math.max(limit, 1), 200);

		return conceptService.getAllConcepts().stream()
		        .sorted(Comparator.comparing(Concept::getDateCreated,
		                Comparator.nullsLast(Date::compareTo)).reversed())
		        .limit(safeLimit)
		        .collect(Collectors.toList());
	}
}
