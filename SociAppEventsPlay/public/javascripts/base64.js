var reader = new FileReader();
    reader.onload = function(){
      var dataURL = reader.result;
      var output = document.getElementById('imgTest');
        function encodeImage(){
var d = new Date();
document.getElementById("fecha").value = d.toDateString();
    var input = event.target;
    var photoFile = input.files[0];
      output.src = dataURL;
      var canvas = document.createElement('canvas');
      canvas.width = 100;
      canvas.height = 100;
        var img = new Image();
        img.src = dataURL;
        var ctx = canvas.getContext("2d");
        ctx.drawImage(img, 0, 0, 100, 100);
            var extImg="";
            var indice=0;
            for (i = 0; i < dataURL.length; i++) {
                if(i>=11){
                    if(dataURL.charAt(i) != ';'){
                        extImg+= dataURL.charAt(i);
                    }
                    else{
                        indice = i;
                        break;
                    }
                }
            }
    var finalFile = canvas.toDataURL("image/"+extImg);
            var stringBase64 = dataURL.substring(indice+8);
            console.log("La extension es: "+extImg+" la cadena base64 de la img grande es "+stringBase64);
            console.log("La extension es: "+extImg+" la cadena base64 de la img pequeña es "+finalFile.replace("data:image/"+extImg+";base64,", ""));
    };
    reader.readAsDataURL(input.files[0]);
};