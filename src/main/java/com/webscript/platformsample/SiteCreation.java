package com.webscript.platformsample;

import java.io.IOException;

import org.activiti.engine.impl.util.json.JSONException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.site.SiteInfo;
import org.alfresco.service.cmr.site.SiteService;
import org.alfresco.service.cmr.site.SiteVisibility;
import org.alfresco.util.ParameterCheck;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
public class SiteCreation extends AbstractWebScript {

    private static Log LOGGER = LogFactory.getLog(SiteCreation.class);
    //private SiteInfo siteInfo;
   // private ServiceRegistry serviceRegistry;
    private SiteService siteService;
   /* public void setServiceRegistry(ServiceRegistry serviceRegistry)
    {
    	this.serviceRegistry = serviceRegistry;
    }*/
        
    public void setSiteService(SiteService siteService)
    {
        this.siteService = siteService;
    }
    
    @Override
    public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {

    	JSONObject reqData = (JSONObject) req.parseContent();
    	LOGGER.info("CreateSite reqData: "+reqData);
    	JSONObject obj = new JSONObject(); 
    	obj.put("JSonData", reqData);   	
        String shortName = "", visibility="", sitePreset="", title="", description = "";
       
        try {
            shortName= (String) reqData.get("shortName");
            sitePreset= (String) reqData.get("sitePreset");
            title= (String) reqData.get("title");
            description= (String) reqData.get("description");
            visibility= (String) reqData.get("visibility");
        	/*shortName= "test-site";
            sitePreset= "site-dashboard";
            title= "Test Site";
            description= "Test Site description";
            visibility= "PUBLIC";   
            SiteInfo siteInfo = createSite(sitePreset,shortName,title,description,visibility);*/
            createSite(sitePreset,shortName,title,description,visibility);
            }
        catch (JSONException err) {
        err.printStackTrace();
       } 
        System.out.println("Site with "+ shortName +"is needed to be created");
    
  }
  
   public void createSite(String sitePreset, String shortName, String title, String description,String visibility) {
   System.out.println("data--- " + this.siteService.getSite(shortName));
   if(this.siteService.getSite(shortName) == null){
       SiteVisibility siteVisibility = SiteVisibility.valueOf(visibility);
       SiteInfo siteInfo = this.siteService.createSite(sitePreset, shortName, title, description, siteVisibility);
       System.out.println("siteinfo ------------------  " + siteInfo.getShortName());
   }else {
       System.out.println("Site is already exists");
   }
   /*ParameterCheck.mandatoryString("visibility", visibility);
   SiteVisibility siteVisibility = SiteVisibility.valueOf(visibility);
   return this.siteService.createSite(sitePreset, shortName, title, description, siteVisibility);*/
   }
  
}