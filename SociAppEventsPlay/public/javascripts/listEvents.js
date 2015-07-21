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
        html += '<tr id="'+ trid+'">';
        html += '<td>' + events[i].name + '</td>';
        html += '</tr>';
    }
    html += '</table>';

    $eventDiv.html(html);

  },
    error:function(){
        console.log("An error ocurred");
    }
});