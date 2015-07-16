var $saveEvents = $('#saveEvents');

$saveEvents.click(function(evt) {
console.log("ENTRO EVENT");
    var nombre = document.getElementById('nameEvent').value;
    var fecha = document.getElementById('dateEvent').value;
    var desc = document.getElementById('desc').value;

    var myObj = {name:nombre, day:fecha, description:desc};


    console.log(myObj);
       $.ajax({
            url:'/users/'+localStorage.getItem('idUser')+'/events',
            headers: {ACCESS_TOKEN : "text/javascript; charset=utf-8"},
            type :  "POST",
            contentType: 'application/json',
            dataType: 'JSON',
            data: JSON.stringify(myObj),
            success : function(data) {
            console.log(data);
            },
            error : function() {
            console.log("An error ocurred");
            }
    });
});



















