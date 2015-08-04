var $eventDiv = $('#listadoEvents');

$.ajax({
    url:'/users/'+localStorage.getItem('idUser')+'/events',
    type:'GET',
    headers: {ACCESS_TOKEN : localStorage.getItem('token')},
    success: function(events){
    console.log(events);

    var html='';
    html+='<table>';

     html += '<tr>';
            html += '<td><h3>NOMBRE DE EVENTO</h3></td>';
            html += '</tr>';

    for(var i = 0; i<events.length; i++){
    var trid='rec-'+events[i].id;
        //html += '<tr id="'+ trid+'" onClick="inviteToEvent('+trid.replace("rec-","")+');">';
        html += '<td>' + events[i].name + ' <button class="btn btn-info photo" data-id="'+events[i].id+'" style="float:right;" onClick="metodo('+trid.replace("rec-","")+');">Agregar Foto</button><button class="btn btn-info user" style="float:right" onClick="inviteToEvent('+trid.replace("rec-","")+');">Invitar</button></td>';
        html += '</tr>';
    }
    html += '</table>';

    $eventDiv.html(html);

    for(var i=0;i<events.length;i++){
        var event = events[i];
        var trid='evento'+event.id;
        var $tr=$('#'+trid);

        var $editButton = $('.photo', $tr);
        $editButton.click(function() {

            var id=$(this).atrr('data-id');
            alert("Photo evento:"+ id);
        });
    }

  },
    error:function(){
        console.log("An error ocurred");
    }
});

function inviteToEvent(idEvent){

    var tokenStorageValue = localStorage.getItem('token');
    console.log("verificando token");
            if(tokenStorageValue) {
                location.href="listUser.html?idEvent="+idEvent;
            }

}