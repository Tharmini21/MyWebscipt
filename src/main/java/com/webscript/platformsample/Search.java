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
import org.alfresco.repo.nodelocator.NodeLocatorService;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.alfresco.service.cmr.security.PermissionService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.repo.security.authentication.AuthenticationUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import java.io.IOException;
import org.alfresco.service.namespace.QName;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
//public class Search extends DeclarativeWebScript {
public class Search extends AbstractWebScript {
	private static Log logger = LogFactory.getLog(Search.class);
	//private ServiceRegistry serviceRegistry;
	//public ServiceRegistry serviceRegistry;
	public NodeService nodeService;
	public SearchService searchService;
	public SearchService mockSearchService;
	public BeanFactory beanFactory = null;
	
	public ServiceRegistry serviceRegistry;

    public ServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException
	{
       this.beanFactory = beanFactory;
    }
    public void setNodeService(NodeService nodeService) {
	   this.nodeService = nodeService;
    }
    public Object getService(QName service)
    {
        return beanFactory.getBean(service.getLocalName());
    }
    static final QName SEARCH_SERVICE = QName.createQName(NamespaceService.ALFRESCO_URI, "SearchService");
    public SearchService getSearchService()
    {
        return mockSearchService == null ? (SearchService)getService(SEARCH_SERVICE) : mockSearchService;
    }
    public SearchService getSearchService1()
    {
      return serviceRegistry.getSearchService();
    }
    public void setSearchService(SearchService searchService) {
	   this.searchService = searchService;
	}
   

    //public ServiceRegistry getServiceRegistry() {
      //  return serviceRegistry;
    //}
   
    public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {
        try {
        	
        	String path = req.getParameter("path");
    	    String site = req.getParameter("site");
    	    String contenttype = req.getParameter("type");
    	    String skipcount = req.getParameter("skipCount");
    	    String pagesize = req.getParameter("maxCount");
        	String searchQuery="";
        	//int page_size = Integer.parseInt(pagesize);
	    	//int skip_count = Integer.parseInt(skipcount);
        	/*
        	if(site== "" || site==null) {
	    		searchQuery = "TYPE:'" + contenttype +"'";
	    	}
	    	else {
    		searchQuery = '"'+ "PATH:'" + path + "' AND SITE:'" + site + "' AND TYPE:'" + contenttype + "'" +'"';
   	    	}*/
        	
        	//searchQuery = "PATH:'" + path + "' AND SITE:'" + site + "' AND TYPE:'" + contenttype + "'";
        	searchQuery = "PATH:" + path + " AND SITE:" + site + " AND TYPE:" + contenttype;
       		long countResult = getObjectCount(searchQuery);
            JSONObject obj = new JSONObject();  
            obj.put("totalcounts", countResult);
            if(contenttype == "\"cm:content\"") {
            	obj.put("totalFolders", countResult);
            	obj.put("totalDocument", 0);
            	obj.put("SearchQuery Formation in Folder:", searchQuery);
            	
			    }
			    else {
			    	obj.put("totalDocument", countResult);
			    	obj.put("totalFolders", 0);
			    	obj.put("SearchQuery Formation in File:", searchQuery);
			    	
			    }
            // Convert to a JSON string and send it back to the caller
            String jsonString = obj.toString();
            res.getWriter().write(jsonString);
        } catch (JSONException e) {
            throw new WebScriptException("Unable to serialize JSON");
        }
    }
    
    public long getObjectCount(String query) {
    	
		 StoreRef storeRef = new StoreRef(StoreRef.PROTOCOL_WORKSPACE, "SpacesStore");
		 ResultSet results=null;		
		  long objectCount = 0;
		  SearchParameters sp;
		  ResultSet res;
		  sp = new SearchParameters();
		  sp.addStore(storeRef);
		  sp.setLanguage(SearchService.LANGUAGE_FTS_ALFRESCO);
		  sp.setQuery(query);
		  //sp.setQuery("TYPE:\"cm:content\"");
		  //sp.setQuery("PATH:'/app:company_home/st:sites/cm:swsdp/cm:dataLists//*' AND SITE:'swsdp' AND TYPE:'cm:content'");
		  sp.setMaxItems(10);
		  sp.setSkipCount(0);
		  try {
			  results = serviceRegistry.getSearchService().query(sp);
			  long length = results.getNumberFound();
		  }
		  catch (AlfrescoRuntimeException alfException) {
				logger.error(alfException.getMessage(), alfException);
		  } 
		  finally
		  {
		    if (results != null)
		    {
		      objectCount = results.getNumberFound();
		      results.close();
		    }
		  }
			return objectCount;
		}
    
    public static void main( String[] args )
    {
    Search demoobj = new Search();
	long FolderCountResult=0;
	String contenttype = "cm:folder" ;
	String searchQuery = "TYPE:'" + contenttype + "'";
	FolderCountResult = demoobj.getObjectCount(searchQuery);
        System.out.println( "FolderCountResult!" +FolderCountResult );
    }
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }
    
}
