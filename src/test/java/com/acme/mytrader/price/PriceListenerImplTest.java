package com.acme.mytrader.price;

import com.acme.mytrader.execution.ExecutionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class PriceListenerImplTest {

	@Mock
	private ExecutionService executionService;

	private PriceListenerImpl priceListner;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		priceListner = new PriceListenerImpl("IBM", 55, 100, executionService);
	}

	@Test
	public void testPriceUpdateToBuyTread() {
		priceListner.priceUpdate("IBM", 50);
		verify(executionService, times(1)).buy("IBM", 50, 100);
		Assert.assertTrue(priceListner.isTradeExecuted());
	}

	@Test
	public void testPriceUpdateWithGreaterPriceLimit() {
		priceListner.priceUpdate("IBM", 60);
		verifyNoMoreInteractions(executionService);
		Assert.assertFalse(priceListner.isTradeExecuted());
	}

	@Test
	public void testTreadAlreadyExecuted() {
		priceListner.setTradeExecuted(true);
		priceListner.priceUpdate("IBM", 60);
		verifyNoMoreInteractions(executionService);
	}

}