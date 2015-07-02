$('#insertForm').submit(function () {
var formulario = document.getElementById('insertForm');

var datos = {};
for(var i = 0; i < formulario.length; i++){
    if(formulario[i].name){
        datos[formulario[i].name] = formulario[i].value
    }
}   console.log(datos);
    e.preventDefault();
    $.ajax({
        type :  "POST",
        contentType: 'application/json',
        dataType: 'JSON',
        data: JSON.stringify(datos),
        url  :  "/users",
            success: function(data){
                console.log(data);
                alert('Se hizo');
            },
            error: function(data){
                console.log(data);
            }
    });
});
