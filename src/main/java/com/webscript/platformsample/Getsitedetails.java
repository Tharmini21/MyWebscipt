package com.webscript.platformsample;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.service.cmr.site.SiteInfo;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.site.SiteService;
import org.alfresco.service.cmr.site.SiteVisibility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

public class Getsitedetails extends AbstractWebScript{
	private static Log LOGGER = LogFactory.getLog(Getsitedetails.class);
	public ServiceRegistry serviceRegistry;
	public SiteInfo siteInfo;
   /* public ServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }
    public SiteInfo getSiteInfo() {
        return siteInfo;
    }*/
	 public void setServiceRegistry(ServiceRegistry serviceRegistry) {
	        this.serviceRegistry = serviceRegistry;
	    }
	    public void setSiteInfo(SiteInfo siteInfo) {
	        this.siteInfo = siteInfo;
	    }
    public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {
        try {
        	
        	 String shortName = req.getParameter("shortName");
    		 System.out.println( "shortName!" +shortName );
    		 LOGGER.info("shortName: "+shortName);
    		 SiteInfo siteInfo = serviceRegistry.getSiteService().getSite(shortName);
    		 String title = siteInfo.getTitle();
    		 String description = siteInfo.getDescription();
    		 SiteVisibility visibility = siteInfo.getVisibility();
    		 Date created = siteInfo.getCreatedDate();
            JSONObject obj = new JSONObject();  
            obj.put("Site", siteInfo.getShortName());
            obj.put("title", siteInfo.getTitle());
            obj.put("description", siteInfo.getDescription());
            obj.put("visibility", siteInfo.getVisibility());
            obj.put("created", siteInfo.getCreatedDate());
            String jsonString = obj.toString();
            res.getWriter().write(jsonString);
        } catch (JSONException e) {
            throw new WebScriptException("Unable to serialize JSON");
        }
    }
    
/*	 protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) 
	    {
		 String shortName = req.getParameter("shortName");
		 System.out.println( "shortName!" +shortName );
		 LOGGER.info("shortName: "+shortName);
		// String shortName= (String) req.getParameter("shortName");
		 //String siteShortName = "my-test";
		 SiteInfo siteInfo = serviceRegistry.getSiteService().getSite(shortName);
		 String title = siteInfo.getTitle();
		 String description = siteInfo.getDescription();
		 SiteVisibility visibility = siteInfo.getVisibility();
		 Date created = siteInfo.getCreatedDate();

	        Map<String, Object> model = new HashMap<String, Object>();
	        model.put("Site", siteInfo.getShortName());
            model.put("title", siteInfo.getTitle());
            model.put("description", siteInfo.getDescription());
            model.put("visibility", siteInfo.getVisibility());
            model.put("created", siteInfo.getCreatedDate());

	        return model;
	    }*/
	 
}
