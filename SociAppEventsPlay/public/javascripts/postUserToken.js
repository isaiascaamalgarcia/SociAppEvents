var $x = $('#save');

function showForm() {
    document.getElementById("events").style.display = "block";

}
function hiddenForm() {
    document.getElementById("events").style.display = "none";
}

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
                    localStorage.setItem('token',data.token);
                    localStorage.setItem('user_id',data.user.id);
                    console.log(data);

                },
                error : function() {
                console.log("An error ocurred");
                }
            });
});

    function load() {

        var tokenStorageValue = localStorage.getItem('token');
        if(tokenStorageValue) {
            location.href="dashboard.html";
        }
    }

    function dashLoad() {

            var tokenStorageValue = localStorage.getItem('token');
            if(!tokenStorageValue) {
                location.href="login.html";
            }
        }
