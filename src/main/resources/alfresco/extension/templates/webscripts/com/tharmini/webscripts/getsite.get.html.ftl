<#assign datetimeformat="yyyy-MM-dd HH:mm:ss">
<h2>Site: '${Site}', Title: '${title}',description: '${description}', visibility: '${visibility}',created date: '${created}'/h2>
<p>The time is now: "${currentDateTime?string(datetimeformat)}</p> 