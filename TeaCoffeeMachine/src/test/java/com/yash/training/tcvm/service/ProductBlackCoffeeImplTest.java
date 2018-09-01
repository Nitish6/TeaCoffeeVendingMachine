package com.yash.training.tcvm.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.yash.training.tcvm.dao.ConsumptionMaterialQuantity;
import com.yash.training.tcvm.dao.ProductCost;
import com.yash.training.tcvm.domain.Container;
import com.yash.training.tcvm.domain.ProductParameters;

@RunWith(MockitoJUnitRunner.class)
public class ProductBlackCoffeeImplTest {

	@InjectMocks
	private	ProductBlackCoffeeImpl blackCoffeeImpl;

	@Mock
	private MaterialManager materialManager;

	@Mock
	private ConsumptionMaterialQuantity consumption;

	@Mock
	private Container containerQuantity;

	@Mock
	private ProductCost productCost;

	@Mock
	private ProductParameters productParameters;

	@Before
	public void shouldinitializeContainer(){
		containerQuantity = Container.getCapacity();
		containerQuantity.setCoffeeContainer(2000d);
		containerQuantity.setTeaContainer(2000d);
		containerQuantity.setMilkContainer(10000d);
		containerQuantity.setSugarContainer(8000d); 
		containerQuantity.setWaterContainer(15000d);
	}


	@Test
	public void shouldReturnTrueIfEnoughMaterialPresent(){
		Map<String, Double> ingredients = new HashMap<>();
		ingredients.put("coffee", 3d);
		ingredients.put("water", 100d); 
		ingredients.put("milk", 0d);
		ingredients.put("sugar", 15d);

		Map<String, Double> materialQuantityLeft = new HashMap<>();

		materialQuantityLeft.put("QuantityLeft", 2000d);
		materialQuantityLeft.put("milkQuantityLeft", 2000d);
		materialQuantityLeft.put("sugarQuantityLeft", 770d);
		materialQuantityLeft.put("waterQuantityLeft", 1300d);
		materialQuantityLeft.put("coffeeQuantityLeft", 1994d);

		when(consumption.blackCoffeeConsumption()).thenReturn(ingredients);
		when(materialManager.getMaterialQuantiyLeft(containerQuantity, ingredients, 2)).thenReturn(materialQuantityLeft);

		Boolean actualAvailabilityStatus = blackCoffeeImpl.checkProductMaterialsQuantityAvailability(2);

		verify(consumption).blackCoffeeConsumption();
		verify(materialManager).getMaterialQuantiyLeft(containerQuantity, ingredients, 2);

		assertEquals(true, actualAvailabilityStatus);
	}

	@Test(expected = RuntimeException.class)
	public void shouldReturnExceptionIfMaterialNotPresent(){

		Map<String, Double> ingredients = new HashMap<>();
		ingredients.put("coffee", 5d);
		ingredients.put("water", 60d);
		ingredients.put("milk", 40d);
		ingredients.put("sugar", 15d);

		Map<String, Double> materialQuantityLeft = new HashMap<>();

		materialQuantityLeft.put("QuantityLeft", 1d);
		materialQuantityLeft.put("milkQuantityLeft", 9d);
		materialQuantityLeft.put("sugarQuantityLeft", 1d);
		materialQuantityLeft.put("waterQuantityLeft", 1d);
		materialQuantityLeft.put("coffeeQuantityLeft", 2d);

		when(consumption.blackCoffeeConsumption()).thenReturn(ingredients);
		when(materialManager.getMaterialQuantiyLeft(containerQuantity, ingredients, 8)).thenReturn(materialQuantityLeft);

		blackCoffeeImpl.checkProductMaterialsQuantityAvailability(8);
	}

	@Test
	public void shouldReturnProductCostIfContentAvailable(){

		Integer drinkCount = 2;

		Map<String, Double> cost = new HashMap<>();
		cost.put("tea", 10d);
		cost.put("black ", 5d);
		cost.put("coffee", 15d);
		cost.put("black coffee", 10d);

		when(productCost.getProductCost()).thenReturn(cost);

		Double actualCost = blackCoffeeImpl.getProductCost(drinkCount);

		Double expectedCost = 20.0;

		assertEquals(expectedCost, actualCost);

		verify(productCost).getProductCost();

	}

}
