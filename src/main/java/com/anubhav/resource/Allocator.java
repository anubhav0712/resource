package com.anubhav.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.anubhav.resource.domain.Response;

public class Allocator {

	public List<Response> get_costs(HashMap<String,HashMap<String,Float>> instances , int hours , int cpu , float price) {
		List<Response> list = new ArrayList<Response>();
		Response resp=new Response();
		resp.setRegion("abc");
		resp.setTotal_cost(10.0f);
		list.add(resp);
		return list;
	}
}
