package com.webscript.scripts;

import java.util.HashMap;
import java.util.Map;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.alfresco.service.cmr.site.SiteService;
import org.apache.log4j.Logger;
import com.webscript.beans.SiteBean;

public class CreateSite extends DeclarativeWebScript {
	
	   Logger logger = Logger.getLogger(CreateSite.class);
	   private SiteService siteService;	    
	   private SiteBean siteBean;
	
	    @Override
	    protected Map<String, Object> executeImpl(WebScriptRequest req,Status status) {
	    	String shortName= req.getParameter("shortName");
	    	String sitePreset= req.getParameter("sitePreset");
	    	String title= req.getParameter("title");
	    	String description= req.getParameter("description");
	    	String visibility= req.getParameter("visibility");

	        if (shortName == null || title == null || visibility == null) {
	            logger.debug("shortName, title, or visibility not set");
	            status.setCode(400, "Required data has not been provided");
	            status.setRedirect(true);
	        } else {
	            logger.debug("Getting current data");
	            if(this.siteService.getSite(shortName) != null){
	                logger.debug("shortName already exists");
	                status.setCode(404, "shortName already found:" + shortName);
	                status.setRedirect(true);
	            } else {
	            	siteBean.createSite(sitePreset, shortName, title, description, visibility);
	                logger.debug("Back from siteBean.create()");
	            }
	        }

	        logger.debug("Setting model");
	        Map<String, Object> model = new HashMap<String, Object>();
	        model.put("shortName", shortName);
	        model.put("sitePreset", sitePreset);
	        model.put("title", title);
	        model.put("description", description);
	        model.put("visibility", visibility);
	        return model;
	    }

	    public SiteService getSiteService() {
	        return siteService;
	    }
	    public void setSiteService(SiteService siteService)
	    {
	        this.siteService = siteService;
	    }

	    public SiteBean getSiteBean() {
	        return siteBean;
	    }

	    public void setSiteBean(SiteBean siteBean) {
	        this.siteBean = siteBean;
	    }
}
