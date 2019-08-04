package com.anubhav.resource;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import org.junit.Test;

import com.anubhav.resource.domain.Response;

public class AllocatorTest {

	@Test
	public void testGetCost() {
		Allocator allocator = new Allocator();
		HashMap<String,HashMap<String,Float>> input = createInputData();
		List<Response> list = allocator.get_costs(input, 1, 1, 1);
		assertEquals(list.get(0).getTotal_cost().floatValue(), 0.894f,3);
	}
	
	@Test
	public void testGetCostForNoPriceInput() {
		Allocator allocator = new Allocator();
		HashMap<String,HashMap<String,Float>> input = createInputData();
		List<Response> list = allocator.get_costs(input, 1, 1, Float.MAX_VALUE);
		assertEquals(list.get(0).getTotal_cost().floatValue(), 0.12f,2);
	}
	
	private HashMap<String,HashMap<String,Float>> createInputData(){
		HashMap<String,HashMap<String,Float>> input = new HashMap<String,HashMap<String,Float>>();
		HashMap<String,Float> ip1 = new HashMap<String,Float>();
		ip1.put("large",0.12f);
		ip1.put("xlarge",0.23f);
		ip1.put("2xlarge",0.45f);
		ip1.put("8xlarge",1.4f);
		ip1.put("10xlarge",2.82f);
		ip1.put("4xlarge",0.774f);
		input.put("us-east", ip1);
		return input;
	}
}
