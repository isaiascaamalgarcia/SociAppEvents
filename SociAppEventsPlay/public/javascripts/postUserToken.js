$('#loginForm').on('submit', function(e){
    var email = document.getElementById('').value;
    var pass = document.getElementById('').value;
    var d = { "email": email,
              "password": pass
    };
    $.ajax({
        url: "/accessTokens",
        type: "POST",
        data: JSON.stringify(d),
        dataType: "application/json",
        success: function() {
                    location.href= 'dashboard.html';
                }

    });
});