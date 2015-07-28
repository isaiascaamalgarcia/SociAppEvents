var idEvent = getUrlVars()["idEvent"];

function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi,
    function(m,key,value) {
        vars[key] = value;
        });
    return vars;
}

var $eventDiv = $('#listadoUser');

$.ajax({
    url:'/users',
    type:'GET',
    headers: {ACCESS_TOKEN : localStorage.getItem('token')},
    success: function(users){
    console.log(users);

    var html='';
    html+='<table>';

     html += '<tr>';
            html += '<td><h3>NOMBRE</h3></td>';
            html += '</tr>';

    for(var i = 0; i<users.length; i++){
    var trid='rec-'+users[i].id;
        html += '<tr id="'+ trid+'">';
        html += "<td onclick=\"(inviteUser('" + users[i].email + " '))\">" + users[i].name + "</td>";
        html += '</tr>';
    }
    html += '</table>';

    $eventDiv.html(html);

  },
    error:function(){
        console.log("An error ocurred");
    }
});

function inviteUser(email){
    console.log(email);
    console.log(idEvent);
    myObj = {};
    myObj["email"] = email;
    console.log(JSON.stringify(myObj));
    $.ajax({
        url:'/users/'+localStorage.getItem('idUser')+'/my-events/'+idEvent+'/guests',
        type:'POST',
        headers: {ACCESS_TOKEN : localStorage.getItem('token')},
        contentType: 'application/json',
        dataType: 'JSON',
        data: JSON.stringify(myObj),
        success: function(data){
        console.log(data);
      },
        error:function(){
            console.log("An error ocurred");
        }
    });
}

