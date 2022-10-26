package com.webscript.scripts;

import java.util.HashMap;
import java.util.Map;

import org.alfresco.service.cmr.site.SiteInfo;
import org.alfresco.service.cmr.site.SiteService;
import org.apache.log4j.Logger;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;
import com.webscript.beans.DeleteBean;

public class DeleteSite extends DeclarativeWebScript {
	
	   Logger logger = Logger.getLogger(DeleteSite.class);
	   private SiteService siteService;	    
	   private DeleteBean deleteBean;
	
	    @Override
	    protected Map<String, Object> executeImpl(WebScriptRequest req,Status status) {
	        String shortName= req.getParameter("shortName");
           if (shortName == null) {
	            logger.debug("shortName not set");
	            status.setCode(400, "Required data has not been provided");
	            status.setRedirect(true);
	        } else {
	            if(this.siteService.getSite(shortName) != null){
	            	logger.debug("Deleting site: " + shortName);
	       	        siteService.deleteSite(shortName);
	       	     }
	       	     else
	       	     {
	       	    	logger.debug("Not deleting site: " + shortName + ", as it doesn't appear to exist");
	       	     }
	        }

	        logger.debug("Setting model");
	        Map<String, Object> model = new HashMap<String, Object>();
	        model.put("shortName", shortName);
	        return model;
	    }

	    public SiteService getSiteService() {
	        return siteService;
	    }
	    public void setSiteService(SiteService siteService)
	    {
	        this.siteService = siteService;
	    }

	    public DeleteBean getDeleteBean() {
	        return deleteBean;
	    }

	    public void setDeleteBean(DeleteBean deleteBean) {
	        this.deleteBean = deleteBean;
	    }

}
