package com.webscript.platformsample;
import org.alfresco.api.AlfrescoPublicApi;
public enum SiteVisibility {
	PUBLIC,             // Public site.  Visible and accessible by all
    MODERATED,          // Moderated site.  Visible to all, but only accessible via moderated invitation.
    PRIVATE       
}
