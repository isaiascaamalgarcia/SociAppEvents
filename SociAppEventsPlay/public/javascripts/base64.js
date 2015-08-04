var idEvent = getUrlVars()["idEvent"];

function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi,
    function(m,key,value) {
        vars[key] = value;
        });
    return vars;
}


var $savePhoto = $('#saveImagen');
console.log("POST PHOTO");

var globalNombreImagen="";
var globalBase64="";
var globalBase64Small="";
var extensionImage="";
var aux=false;

$savePhoto.click(function(evt) {
console.log("ENTRO PHOTO");
    aux= validation();
     console.log(aux);
        if (aux)
        {
            var nombre = document.getElementById('title').value;
            var descripcion = document.getElementById('description').value;
            var dayfecha = document.getElementById('fecha').value;

            var myObj = {title:nombre, url:"", urlSmall:"", description:descripcion, base64:globalBase64,
            base64Small:globalBase64Small, type:extensionImage, datePhoto:dayfecha};

            console.log(myObj);
            console.log(dayfecha);
               $.ajax({
                    url:'/users/'+localStorage.getItem('idUser')+'/events/'+idEvent+'/photos',
                    headers: {ACCESS_TOKEN : localStorage.getItem('token')},
                    type :  "POST",
                    contentType: 'application/json',
                    dataType: 'JSON',
                    data: JSON.stringify(myObj),
                    success : function(data) {
                    console.log("success");
                    console.log(data);
                    },
                    error : function() {
                    console.log("An error ocurred");
                    }
            });
             loscation.reload();
        }
});

function validation() {
     var nombre = document.getElementById('title').value;
     var descripcion = document.getElementById('description').value;
     var dayfecha = document.getElementById('fecha').value;

    if(nombre== "") {
        alert("Ingresa Nombre de Evento.");
        console.log("VACIO ");
        return false;
    }
    if(dayfecha==""){
        alert("Ingrese Fecha.");
        console.log("VACIO ");
        pass.focus();
        return false;
    }
    if(descripcion== "") {
       alert("Ingrese Descripcion evento.");
               console.log("VACIO ");
        return false;
    }
    return true;
}


function encodeImage(){
var input = event.target.files;
var photoFile = input[0];
console.log(photoFile);

            var reader = new FileReader();
            reader.onload = function(){
            var dataURL = reader.result;
            var output = document.getElementById('imgTest');

           var now = new Date();
           var todayUTC = new Date(Date.UTC(now.getFullYear(), now.getMonth(), now.getDate()));
           document.getElementById("fecha").value =   todayUTC.toISOString().slice(0, 10).replace(/-/g, '-');

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
            var base64Small=finalFile.replace("data:image/"+extImg+";base64,", "");
            console.log("La extension es: "+extImg+" la cadena base64 de la img grande es "+stringBase64);
            console.log("La extension es: "+extImg+" la cadena base64 de la img pequeña es "+base64Small);
            globalBase64=stringBase64;
            globalBase64Small=base64Small;
            extensionImage=extImg;
    };
   reader.readAsDataURL(photoFile);
};