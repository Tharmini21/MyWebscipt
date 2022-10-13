package com.webscript.platformsample;

import java.io.IOException;
import java.util.List;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.json.JSONException;
import org.json.JSONObject;
public class SimpleWebscript extends AbstractWebScript {
	public NodeService nodeService;
	public ServiceRegistry serviceRegistry;
	
    public void setNodeService(NodeService nodeService) {
	   this.nodeService = nodeService;
    }
    public void execute(WebScriptRequest req, WebScriptResponse res)  throws IOException {
       /* try {
            List<ChildAssociationRef> nodeRef = nodeService.getChildAssocs(companyHomeRef);
            for (ChildAssociationRef childAssoc : nodeRef) {
                NodeRef childNodeRef = childAssoc.getChildRef();
                JSONObject obj = new JSONObject();
                obj.put("nodeRef", childAssoc);
                obj.put("value", childAssoc.getQName());
                String jsonString = obj.toString();
                res.getWriter().write(jsonString);
               // ServiceRegistry serviceRegistry = (ServiceRegistry) context.getBean("ServiceRegistry");
            }
        } catch (JSONException e) {
            throw new WebScriptException("Unable to serialize JSON");

        }*/
    }
}