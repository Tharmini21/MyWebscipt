package com.webscript.platformsample;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyDemoHello extends DeclarativeWebScript {
	
	 protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
	        Map<String, Object> model = new HashMap<String, Object>();
	        model.put("currentDateTime", new Date());
	        return model;
	    }
}

