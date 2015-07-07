var $cerrar = $('#clear');
$cerrar.click(function(evt) {
        console.log("ENTRO CERRAR");
        localStorage.clear();
        location.reload();
       });