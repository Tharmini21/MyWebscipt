package com.webscript.beans;

import org.alfresco.service.cmr.site.SiteInfo;
import org.alfresco.service.cmr.site.SiteService;
import org.apache.log4j.Logger;

public class DeleteBean {
	 private Logger logger = Logger.getLogger(DeleteBean.class);
	 private SiteService siteService;	 
	 public void deleteSite(String shortName)
	   {
		 SiteInfo siteInfo = siteService.getSite(shortName);
	     if (siteInfo != null)
	     {
	       logger.debug("Deleting site: " + shortName);
	       siteService.deleteSite(shortName);
	     }
	     else
	     {
	    	 logger.debug("Not deleting site: " + shortName + ", as it doesn't appear to exist");
	     }
	   }
	 
     public SiteService getSiteService() {
	        return siteService;
	    }
	 public void setSiteService(SiteService siteService)
	    {
	        this.siteService = siteService;
	    }	 
}
