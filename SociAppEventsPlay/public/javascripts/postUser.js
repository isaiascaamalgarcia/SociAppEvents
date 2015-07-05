var $x = $('#save');
var aux=false;

$x.click(function(evt) {
 aux= validation();
 console.log(aux);
    if (aux)
    {
        var formPost = document.getElementById('insertForm');
        var myObj = {};
            for(var i = 0; i < formPost.length; i++){
            if(i!=2)
            {
                if(formPost[i].name){
                   myObj[formPost[i].name] = formPost[i].value
                 }
            }
         }
                /*var name=document.getElementById("name").placeholder = "Nombre completo";
                var email=document.getElementById("email").placeholder = "E-mail";
                var pass_1=document.getElementById("password").placeholder = "Contraseña";
                var pass_2=document.getElementById("password2").placeholder = "Repetir contraseña";*/
    console.log(myObj);


        $.ajax({
            url:'/users',
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
            location.reload();
        }
});

function validation() {
var pass = document.getElementById("insertForm");
    if(pass[1].value == "") {
        alert("Ingresaa la contraseña.");
        console.log("VACIO ");
        return false;
    }
    if((pass[1].value).length < 8){
        alert("Minimo de 8 caracteres.");
        console.log("MENOR 8 ");
        pass.focus();
        return false;
    }
    if(pass[1].value == "") {
        alert("Debe confirmar la contraseña.")
        console.log("CONFIRM");
        return false;
    }
    if(pass[1].value != pass[2].value) {
        alert("La contraseña no coincide.");
        console.log("!= ");
        return false;
    }
    return true;
}