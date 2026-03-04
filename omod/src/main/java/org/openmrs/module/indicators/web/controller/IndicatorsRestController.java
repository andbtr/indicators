package org.openmrs.module.indicators.web.controller;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.openmrs.module.indicators.api.IndicatorsService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * REST Controller for Indicators - supports Basic Auth
 */
@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + "/indicators")
public class IndicatorsRestController extends BaseRestController {
	
	@Autowired
	IndicatorsService indicatorsService;
	
	@RequestMapping(value = "/lastconcepts", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> getLastConcepts(
			@RequestParam(value = "limit", defaultValue = "50") Integer limit) {
		return indicatorsService.getLastConcepts(limit).stream().map(concept -> {
			Map<String, Object> row = new LinkedHashMap<String, Object>();
			row.put("conceptId", concept.getConceptId());
			row.put("uuid", concept.getUuid());
			row.put("display", concept.getDisplayString());
			row.put("dateCreated", concept.getDateCreated());
			return row;
		}).collect(Collectors.toList());
	}
}
