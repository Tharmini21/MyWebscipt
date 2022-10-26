package com.webscript.beans;

import org.alfresco.service.cmr.site.SiteInfo;
import org.alfresco.service.cmr.site.SiteService;
import org.alfresco.service.cmr.site.SiteVisibility;
import org.apache.log4j.Logger;
public class SiteBean {
	 private Logger logger = Logger.getLogger(SiteBean.class);
	 private SiteService siteService;
	    
	 public void createSite(String sitePreset, String shortName, String title, String description,String visibility) {
		   System.out.println("data--- " + this.siteService.getSite(shortName));
		   if(this.siteService.getSite(shortName) == null){
		       SiteVisibility siteVisibility = SiteVisibility.valueOf(visibility);
		       SiteInfo siteInfo = this.siteService.createSite(sitePreset, shortName, title, description, siteVisibility);
		       System.out.println("siteinfo ------------------  " + siteInfo.getShortName());
		   }else {
		       System.out.println("Site is already exists");
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
