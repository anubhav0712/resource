package com.anubhav.resource;

import java.util.Comparator;

import com.anubhav.resource.domain.Response;

public class ResponseComparator implements Comparator<Response>{

	public int compare(Response o1, Response o2) {
		if(o1.getTotal_cost() - o2.getTotal_cost() > 0.0f) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
