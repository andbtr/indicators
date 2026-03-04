package org.openmrs.module.indicators;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.module.indicators.Item;
import org.openmrs.module.indicators.api.dao.IndicatorsDao;
import org.openmrs.module.indicators.api.impl.IndicatorsServiceImpl;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

/**
 * This is a unit test, which verifies logic in IndicatorsService. It doesn't extend
 * BaseModuleContextSensitiveTest, thus it is run without the in-memory DB and Spring context.
 */
public class IndicatorsServiceTest {
	
	@InjectMocks
	IndicatorsServiceImpl basicModuleService;
	
	@Mock
	IndicatorsDao dao;
	
	@Mock
	UserService userService;
	
	@Before
	public void setupMocks() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void saveItem_shouldSetOwnerIfNotSet() {
		//Given
		Item item = new Item();
		item.setDescription("some description");
		
		when(dao.saveItem(item)).thenReturn(item);
		
		User user = new User();
		when(userService.getUser(1)).thenReturn(user);
		
		//When
		basicModuleService.saveItem(item);
		
		//Then
		assertThat(item, hasProperty("owner", is(user)));
	}
}
