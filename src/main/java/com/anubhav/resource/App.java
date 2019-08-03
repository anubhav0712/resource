package com.anubhav.resource;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.anubhav.resource.domain.Response;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App {
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		Allocator allocator = new Allocator();
		Scanner in=new Scanner(System.in);
		HashMap<String, HashMap<String, Float>> instances = objectMapper.readValue(new File("instances.json"),
				new TypeReference<HashMap<String, HashMap<String, Float>>>() {
				});
		System.out.println("Instances loaded...");
		System.out.print("Enter hours:");
		int hours = in.nextInt();
		System.out.print("Enter cpu:");
		int cpu=in.nextInt();
		System.out.println("Enter price");
		float price = in.nextFloat();
		List<Response> response = allocator.get_costs(instances, hours, cpu, price);
		Collections.sort(response, new ResponseComparator());
		objectMapper.writeValue(new File("target/output.json"), response);
	}

}
