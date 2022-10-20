// extract file attributes
// var status ="";
var title = args.title;
var description = args.description;
function main() {
// extract file
var file = null;
const inputFile = document.getElementById("file");

for (const file of inputFile.fields) {
  if (file.name == "file" && file.isFile)
  {
    file = field;
  }
}
// foreach (field in formdata.fields)
// {
//   if (field.name == "file" && field.isFile)
//   {
//     file = field;
//   }
// }
        
// ensure file has been uploaded
if (file.filename == "")
{
  status.code = 400;
  status.message = "Uploaded file cannot be located";
  status.redirect = true;
}
else
{
  // create document in company home from uploaded file
  upload = companyhome.createFile(file.filename) ;
  upload.properties.content.guessMimetype(file.filename);
  upload.properties.content.write(file.content);
  upload.properties.title = title;
  upload.properties.description = description;
  upload.save();
  // setup model for response template
  model.upload = upload;
}
}
main();