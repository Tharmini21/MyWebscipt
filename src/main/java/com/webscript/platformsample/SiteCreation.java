package com.webscript.platformsample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.alfresco.model.ContentModel;
//import org.activiti.engine.impl.util.json.JSONException;
//import org.activiti.engine.impl.util.json.JSONObject;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.site.SiteInfo;
import org.alfresco.service.cmr.site.SiteService;
import org.alfresco.service.cmr.site.SiteVisibility;
import org.alfresco.api.AlfrescoPublicApi;
//import com.webscript.platformsample.SiteVisibility;
import org.alfresco.util.ParameterCheck;
//import org.apache.chemistry.opencmis.commons.impl.json.parser.JSONParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.JSONObject;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;

import java.util.HashMap;
import java.util.Map;
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
    //protected Map<String, Object> executeImpl(WebScriptRequest req,Status status) {
    	/*try {
    	File file = new File("sample.json");
        JSONParser parser = new JSONParser();
        JSONObject data = (JSONObject) parser.parse(new FileReader(file.getAbsolutePath()));//path to the JSON file.
        System.out.println(data.toString());
    	}catch(JSONException err)
    	{
    		err.printStackTrace();
    	}*/
    	JSONParser jsonParser = new JSONParser();
    	try {
    	Object objnew = jsonParser.parse(new FileReader("C:/Users/Tharmini/projects/eclipse-workspace/my-webscript/src/main/resources/alfresco/extension/templates/webscripts/com/tharmini/webscripts/sample.json"));
    	JSONObject jsonObject = (JSONObject)objnew;
        String name = (String)jsonObject.get("shortName");
        String title = (String)jsonObject.get("title");
        LOGGER.info("CreateSite reqData: "+jsonObject);
    	 } catch(Exception e) {
             e.printStackTrace();
          }
    	JSONObject reqData = (JSONObject) req.parseContent();
    	 //JSONObject json = (JSONObject) new JSONParser().parse(req);
         //System.out.println(json);
    	System.out.println(reqData);
       	LOGGER.info("CreateSite reqData: "+reqData);
    	JSONObject obj = new JSONObject(); 
    	obj.put("JSonData", reqData);   	
        String shortName = "",visibility="",  sitePreset="", title="", description = "";
       // boolean visibility=false;
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            shortName= (String) req.getParameter("shortName");
            sitePreset= (String) req.getParameter("sitePreset");
            title= (String) req.getParameter("title");
            description= (String) req.getParameter("description");
            visibility= (String) req.getParameter("visibility");
            
            System.out.println(shortName);
            //visibility= Boolean.parseBoolean(req.getParameter("visibility"));
            
        	/*shortName= "test-site";
            sitePreset= "site-dashboard";
            title= "Test Site";
            description= "Test Site description";
            visibility= "PUBLIC";   
            SiteInfo siteInfo = createSite(sitePreset,shortName,title,description,visibility);*/
           // createSite(sitePreset,shortName,title,description,visibility);
            SiteInfo siteInfo = createSiteNew(sitePreset,shortName,title,description,visibility);
            model.put("Site", siteInfo.getShortName());
            model.put("title", siteInfo.getTitle());
            model.put("description", siteInfo.getDescription());

            
            }
        catch (JSONException err) {
        err.printStackTrace();
       } 
        System.out.println("Site with "+ shortName +"is needed to be created");
        //return model;
  }
    private SiteInfo createSiteNew(String sitePreset, String shortName, String title, String description,String visibility)
    {
      // Create a public site
      SiteVisibility siteVisibility = SiteVisibility.valueOf(visibility);
      return this.siteService.createSite(sitePreset, shortName, title, description, siteVisibility);
      /*SiteInfo siteInfo = this.siteService.createSite(sitePreset, shortName, title, description, siteVisibility);
      this.siteService.createContainer(shortName, "TestComponent", ContentModel.TYPE_FOLDER, null);
      return siteInfo;*/
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