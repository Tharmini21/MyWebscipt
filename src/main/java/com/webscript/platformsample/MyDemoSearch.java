package com.webscript.platformsample;

import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;


public class MyDemoSearch extends DeclarativeWebScript {
    private static Log logger = LogFactory.getLog(MyDemoSearch.class);
    public ServiceRegistry serviceRegistry;

    public ServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }
   
	 protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
		Map<String, Object> model = new HashMap<String, Object>();
		JSONObject obj = new JSONObject();
	    logger.debug("Your 'Search' Web Script was started!");
	    int Sizeofpage =50;
	    int skip_count =0;
	    long countResult =0;
	    String path = req.getParameter("path");
	    String site = req.getParameter("siteShortName");
	    String contenttype = req.getParameter("type");
	    String skipcount = req.getParameter("skipCount");
	    String pagesize = req.getParameter("maxCount");
	    /*String path = "/app:company_home/st:sites/cm:swsdp/cm:dataLists//*";
	    String site = "swsdp";
	    String contenttype = "cm:content";
	    String skipcount = "0";
	    String pagesize = "10";*/
	    try {
	    	
	    	String searchQuery="";
	    	Sizeofpage = Integer.parseInt(pagesize);
	    	skip_count = Integer.parseInt(skipcount);
	    	if(site== "" || site==null) {
	    		searchQuery = "TYPE:" + contenttype;
	    	}
	    	else {
    		searchQuery = '"'+ "PATH:'" + path + "' AND SITE:'" + site + "' AND TYPE:'" + contenttype + "'" +'"';
   	    	} 
	  		/*String url = serviceRegistry.getNamespaceService().getNamespaceURI(contenttype.split(":")[0]);
    		System.out.println("Url:"+url);
    		searchQuery = "PATH:'/app:company_home/st:sites/cm:swsdp/cm:dataLists//*' AND SITE:'swsdp' AND TYPE:'cm:folder'";
    	    String qy = "PATH:\"/app:company_home/st:sites/cm:swsdp/cm:dataLists\" AND SITE:\"swsdp\" AND TYPE:\"cm:folder\"";*/
    		
    		countResult = getObjectCount(searchQuery,Sizeofpage,skip_count);
	    }
	    catch(Exception e) {
	    	logger.error(e.getMessage());
	    }
	    if(contenttype=="cm:folder") {
	    model.put("totalFolders", countResult);
	    model.put("totalDocument", 0);
	    }
	    else {
	    model.put("totalDocument", countResult);
	    model.put("totalFolders", 0);
	    }
		return model;
	 }
	 
	 public long getObjectCount(String query,int pagesize,int skipcount) {
		          StoreRef storeRef = new StoreRef(StoreRef.PROTOCOL_WORKSPACE, "SpacesStore");
		          long objectCount = 0;
		          long documentCount =0;
		          /*System.out.println(query);				
				  query ="PATH:'/app:company_home/st:sites/cm:swsdp/cm:dataLists//*' AND SITE:'swsdp' AND TYPE:'cm:folder'";
				  System.out.println("Manual query:" +query);*/
				  SearchParameters sp = new SearchParameters();
				  sp.addStore(storeRef);
				  sp.setLanguage(SearchService.LANGUAGE_FTS_ALFRESCO);
				  //sp.setQuery(query);
				  sp.setQuery("PATH:/app:company_home/st:sites/cm:swsdp/cm:dataLists//*' AND SITE:'swsdp' AND TYPE:'cm:content'");
				  //sp.setQuery(filter(language,query,files,folders));
				  sp.setMaxItems(pagesize);
				  sp.setSkipCount(skipcount);
				  //searchService.query(sp);
				  ResultSet resultSet = null;
				  List<NodeRef> result = new ArrayList<NodeRef>();
				 				  
				  try
				  {
				    resultSet = serviceRegistry.getSearchService().query(sp);
				    result = resultSet.getNodeRefs();
				  }
				  catch (AlfrescoRuntimeException alfException) {
						logger.error(alfException.getMessage(), alfException);
				  } 
				  finally
				  {
				    if (resultSet != null)
				    {
				      objectCount = resultSet.getNumberFound();
				      documentCount = resultSet.getNumberFound();
				      resultSet.close();
				    }
				  }
				  		
			return objectCount;
		}
	 public static void main( String[] args )
	    {
		MyDemoSearch demoobj = new MyDemoSearch();
		long FolderCountResult=0;
		String contenttype = "cm:folder" ;
		String searchQuery = "TYPE:'" + contenttype + "'";
		demoobj.executeImpl(null, null, null);
		FolderCountResult = demoobj.getObjectCount(searchQuery,0,0);
	        System.out.println( "FolderCountResult!" +FolderCountResult );
	    }
}