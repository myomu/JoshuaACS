$("#login-button").click(function(event){
    event.preventDefault();

    console.log('test');
    $('form').fadeOut(500);
    $('.wrapper').addClass('form-success');
});

function checkIdAndPwd() {

    let id = document.loginForm.loginId;
    let password = document.querySelector('#password');
    let msg = document.querySelector('.error-message');

    if (id.value === "") {
        msg.style.display = 'block';
        msg.innerHTML = "아이디를 입력해 주세요.";
        id.focus();
        return false;
    } else {
        msg.innerHTML = "";
    }

    if (password.value === "") {
        msg.style.display = 'block';
        msg.innerHTML = "비밀번호를 입력해 주세요.";
        password.focus();
        return false;
    } else {
        msg.innerHTML = "";
    }
    
}