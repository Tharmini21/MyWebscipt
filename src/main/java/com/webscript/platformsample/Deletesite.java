package com.webscript.platformsample;

import java.io.IOException;

import org.activiti.engine.impl.util.json.JSONException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.alfresco.service.cmr.site.SiteInfo;
import org.alfresco.service.cmr.site.SiteService;
import org.alfresco.service.cmr.site.SiteVisibility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;

public class Deletesite extends AbstractWebScript {
	 private static Log LOGGER = LogFactory.getLog(Deletesite.class);
	    private SiteService siteService;
	    public void setSiteService(SiteService siteService)
	    {
	        this.siteService = siteService;
	    }
	    
	    public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {

	    	String shortName = req.getParameter("sitename");
	        try {
	        	deleteSite(shortName);
	            }
	        catch (JSONException err) {
	        err.printStackTrace();
	       } 
	        System.out.println("Site with "+ shortName +"is deleted");
	    
	  }
	  
	   public void deleteSite(String shortName) {
	     this.siteService.deleteSite(shortName);
		//return null;  
	   }
	  
}
