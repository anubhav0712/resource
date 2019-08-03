package com.anubhav.resource.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response{
private String region;
private Float total_cost;
private List<String> servers;
public Response() {
	servers = new ArrayList<String>();
}

}
