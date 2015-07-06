/*$('#loginForm').on('submit', function(e){

    var formulario = document.getElementById('loginForm');

    var datos = {};
    for(var i = 0; i < formulario.length; i++){
        if(formulario[i].name){
            datos[formulario[i].name] = formulario[i].value
        }
    }
        e.preventDefault();
    $.ajax({
        url: "/accessTokens",
        type: "POST",
        data: JSON.stringify(datos),
        dataType: "application/json",
        success: function(data) {
        console.log(data);
        }

    });

});*/

var $x = $('#save');

$x.click(function(evt) {
    var formPost = document.getElementById('loginForm');
            var myObj = {};
                for(var i = 0; i < formPost.length; i++){

                    if(formPost[i].name){
                       myObj[formPost[i].name] = formPost[i].value
                    }
             }
        console.log(myObj);


            $.ajax({
                url:'/accessTokens',
                type :  "POST",
                contentType: 'application/json',
                dataType: 'JSON',
                data: JSON.stringify(myObj),
                success : function(data) {
                location.href= 'dashboard.html';
                console.log(data);
                },
                error : function() {
                console.log("An error ocurred");
                }
            });
});
