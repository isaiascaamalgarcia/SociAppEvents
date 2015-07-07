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
        localStorage.setItem('email', formPost[0].value)
        localStorage.setItem('password', formPost[1].value);

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

    function load() {

        var userStorageValue = localStorage.getItem('email');
        var passStorageValue = localStorage.getItem('password');
        if(userStorageValue && passStorageValue) {
            var data = {'email': userStorageValue,
                        'password': passStorageValue
                        }
            location.href="dashboard.html";
        }
    }

    function dashLoad() {

            var userStorageValue = localStorage.getItem('email');
            var passStorageValue = localStorage.getItem('password');
            if(!userStorageValue && !passStorageValue) {
                var data = {'email': userStorageValue,
                            'password': passStorageValue
                            }
                location.href="login.html";
            }
        }
