package com.anubhav.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.anubhav.resource.domain.Response;

public class Allocator {
	HashMap<String, Integer> entryToIndex = new HashMap<String,Integer>();
	HashMap<Integer,String> indexToEntry=new HashMap<Integer,String>();
	public static final int SERVER_TYPES=6;
	public static final int CPU_IN_LAST_SERVER=32;
	public List<Response> get_costs(HashMap<String,HashMap<String,Float>> instances , int hours , int cpu , float price) {
		List<Response> list = new ArrayList<Response>();
		initializeEntryToIndex();
		for(Entry<String, HashMap<String,Float>> e: instances.entrySet()) {
			Response resp = process(e.getValue(),hours,cpu,price);
			if(resp==null)continue;
			resp.setRegion(e.getKey());
			list.add(resp);
		}
		return list;
	}
	
	private Response process(HashMap<String,Float> instance , int hours ,int cpu,float price) {
		Response resp=new Response();
		price/=hours;
		float[] array=new float[SERVER_TYPES];
		Arrays.fill(array, Float.MAX_VALUE);
		for(Entry<String , Float> e:instance.entrySet()) {
			array[entryToIndex.get(e.getKey())]=e.getValue();
		}
		int[] parent=new int[SERVER_TYPES];
		for(int i=0;i<parent.length;i++)parent[i]=i;
		normalizeCost(array,parent);
		int serverCpu = CPU_IN_LAST_SERVER , cpuIndex=SERVER_TYPES-1;
		int tCpu=0;
		float tPrice=0;
		int[] nos = new int[SERVER_TYPES];
		if(price!=Float.MAX_VALUE) {
			while(serverCpu>0 && tPrice < price) {
				if(array[cpuIndex]+tPrice > price) {
					serverCpu/=2;cpuIndex--;continue;
				}
				nos[parent[cpuIndex]]+=Math.pow(2, cpuIndex - parent[cpuIndex]);
				tPrice+=array[cpuIndex];
				tCpu+=serverCpu;
			}
		}
		else {
			while(serverCpu>0 && tCpu < cpu) {
				if(tCpu + serverCpu > cpu) {
					serverCpu/=2;cpuIndex--;continue;
				}
				nos[parent[cpuIndex]]+=Math.pow(2, cpuIndex - parent[cpuIndex]);
				tPrice+=array[cpuIndex];
				tCpu+=serverCpu;
			}
		}
		if(tCpu < cpu || tPrice > price)return null;
		initializeIndexToEntry();
		resp.setTotal_cost(tPrice*hours);
		for(int i=0;i<nos.length;i++) {
			if(nos[i]!=0) {
				StringBuilder sb=new StringBuilder("("+indexToEntry.get(i)+","+nos[i]+")");
				resp.getServers().add(sb.toString());
			}
		}
		return resp;
	}
	
	private void normalizeCost(float[] array , int[] parent) {
		for(int i=0;i<array.length;i++) {
			int count=2;
			for(int j=i-1;j>=0;j--) {
				if(array[j]*count < array[i]) {
					array[i]=array[j]*count;
					parent[i]=j;
				}
				count*=2;
			}			
		}
	}
	
	private void initializeEntryToIndex() {
		entryToIndex.put("large", 0);
		entryToIndex.put("xlarge", 1);
		entryToIndex.put("2xlarge", 2);
		entryToIndex.put("4xlarge", 3);
		entryToIndex.put("8xlarge", 4);
		entryToIndex.put("10xlarge", 5);
	}
	
	private void initializeIndexToEntry() {
		indexToEntry.put(0,"large");
		indexToEntry.put(1,"xlarge");
		indexToEntry.put(2,"2xlarge");
		indexToEntry.put(3,"4xlarge");
		indexToEntry.put(4,"8xlarge");
		indexToEntry.put(5,"10xlarge");
	}
}
