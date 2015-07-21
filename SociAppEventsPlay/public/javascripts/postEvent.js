var $saveEvents = $('#saveEvents');

$saveEvents.click(function(evt) {
console.log("ENTRO EVENT");
    aux= validation();
     console.log(aux);
        if (aux)
        {
        var nombre = document.getElementById('nameEvent').value;
            var fecha = document.getElementById('dateEvent').value;
            var desc = document.getElementById('desc').value;

            var myObj = {name:nombre, day:fecha, description:desc};


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




















