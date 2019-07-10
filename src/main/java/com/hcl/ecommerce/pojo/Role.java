package com.hcl.ecommerce.pojo;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
	SELLER, BUYER;

	private static Map<String, Role> rolesMap=new HashMap<>();
	static {
		rolesMap.put("seller",SELLER);
		rolesMap.put("buyer",BUYER);
		
	}
	
	@JsonCreator
    public static Role forValue(String value) {
		if(value==null)
			return null;
        return rolesMap.get(value.toLowerCase());
    }
}
