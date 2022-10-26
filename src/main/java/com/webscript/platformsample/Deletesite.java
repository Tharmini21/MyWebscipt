package com.webscript.platformsample;

import java.io.IOException;

import org.activiti.engine.impl.util.json.JSONException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.alfresco.service.ServiceRegistry;
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
	   	public SiteInfo siteInfo;
		public ServiceRegistry serviceRegistry;
	    public void setSiteService(SiteService siteService){
	        this.siteService = siteService;
	    }
	    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		     this.serviceRegistry = serviceRegistry;
		}
	    public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {

	    	String shortName = req.getParameter("shortname");
	        try {
	        	deleteSite(shortName);
	            }
	        catch (JSONException err) {
	        err.printStackTrace();
	       } 
	        System.out.println("Site with "+ shortName +"is deleted");
	    
	  }
	  	   
	   public void deleteSite(String shortName)
	   {
		 SiteInfo siteInfo = serviceRegistry.getSiteService().getSite(shortName);
	     //SiteInfo siteInfo = siteService.getSite(shortName);
	     if (siteInfo != null)
	     {
	       LOGGER.debug("Deleting site: " + shortName);
	       serviceRegistry.getSiteService().deleteSite(shortName);
	       //siteService.deleteSite(shortName);
	     }
	     else
	     {
	    	 LOGGER.debug("Not deleting site: " + shortName + ", as it doesn't appear to exist");
	     }
	   }
	   
	   /*public void deleteSite(String shortName) {
	     this.siteService.deleteSite(shortName); 
	   }*/
	  
}
