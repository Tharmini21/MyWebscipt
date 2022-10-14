var siteCreationLog = [];

function main() {
	model.success = false;

	
	var shortName = json.get("shortName");
    var sitePreset = json.get("sitePreset");
    var title = json.get("title");
    var description = json.get("description");
    var visibility = json.get("visibility");

	var sitePreset = sitePreset != null ? sitePreset : "site-dashboard";

	if (shortName != null) {
		shortName = shortName.replace(/[^0-9a-zA-Z\-\s]/g, "");
		shortName = shortName.trim();
		shortName = shortName.replace(/\s+/g, "-").toLowerCase();
		/*var title = args.title != null ? args.title : shortName;
		var visibility = args.visibility != null ? args.visibility : "PUBLIC";
		var description = args.description != null ? args.description : "";*/

		var jsonPayload = '{"title":"' + title + '","visibility":"' + visibility + '","description":"' + description + '","sitePreset":"'
				+ sitePreset + '","shortName":"' + shortName + '"}';
		
		var clientRequest = jsonPayload.toString();
		var clientJSON = JSON.parse(clientRequest);

		var remoteConnection = remote.connect("alfresco");
		var repoResponse = remoteConnection.post("/api/sites", clientRequest, "application/json");

		if (repoResponse.status == 400) {
			siteCreationLog.push("Site already exists!");
			model.siteCreationLog = siteCreationLog;
		} else if (repoResponse.status == 401) {
			siteCreationLog.push("Unable to create site. Please check the logs for error details!");
			model.siteCreationLog = siteCreationLog;
		} else {
			var jsonRespFrmRepo = JSON.parse(repoResponse);
			if (jsonRespFrmRepo.shortName) {
				for (var each = 0; each < 3 && !model.success; each++) {
					var tokens = [];
					tokens["siteid"] = jsonRespFrmRepo.shortName;
					//setup surf-config.
					model.success = sitedata.newPreset(clientJSON.sitePreset, tokens);
				}
				if (model.success) {
					siteCreationLog.push("Site created successfully.");
					model.siteCreationLog = siteCreationLog;
				} else {
					conn.del("/api/sites/" + encodeURIComponent(jsonRespFrmRepo.shortName));
					//status.setCode(status.STATUS_INTERNAL_SERVER_ERROR, "error.create");
				}
			} else if (jsonRespFrmRepo.status.code) {
				//status.setCode(jsonRespFrmRepo.status.code, jsonRespFrmRepo.message);
			}
		}
	} else {
		siteCreationLog.push("Unable to create site.");
		model.siteCreationLog = siteCreationLog;
	}
}

main();