var $savePhoto = $('#saveImagen');
console.log("POST PHOTO");

var globalNombreImagen="";
var globalBase64="";
var globalBase64Small="";
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
            var desc = document.getElementById('desc').value;

            var myObj = {name:nombre, day:dayfecha, description:desc};


            console.log(myObj);
               $.ajax({
                    url:'/users/'+localStorage.getItem('idUser')+'/my-events',
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
             location.reload();
        }
});

function validation() {
    var nombre = document.getElementById('nameEvent').value;
    var fecha = document.getElementById('dateEvent').value;
    var desc = document.getElementById('desc').value;

    if(nombre== "") {
        alert("Ingresa Nombre de Evento.");
        console.log("VACIO ");
        return false;
    }
    if(fecha==""){
        alert("Ingrese Fecha.");
        console.log("VACIO ");
        pass.focus();
        return false;
    }
    if(desc== "") {
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

/*var d = new Date();
document.getElementById("fecha").value = d.toDateString();*/

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
            globalBase64=stringBase64;
            globalBase64Small
    };
   reader.readAsDataURL(photoFile);
};