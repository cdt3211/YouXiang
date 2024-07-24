// 注册时检查密码是否一致
function checkPasswords() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;

    if (password !== confirmPassword) {
        alert("密码和确认密码不一致，请重新输入！");
        return false; // 阻止表单提交
    }
    return true; // 密码一致，可以继续后续操作，如表单提交
}

//弹窗报错
document.addEventListener("DOMContentLoaded", function() {
    // Check for login error message
    var loginErrorMessage = document.getElementById("loginError").textContent;
    if (loginErrorMessage) {
        alert("登录错误: " + loginErrorMessage);
    }

    // Check for registration error message
    var registerErrorMessage = document.getElementById("registerError").textContent;
    if (registerErrorMessage) {
        alert("注册错误: " + registerErrorMessage);
    }
});

//板块切换
let switchCtn = document.querySelector("#switch-cnt");
let switchC1 = document.querySelector("#switch-c1");
let switchC2 = document.querySelector("#switch-c2");
let switchCircle = document.querySelectorAll(".switch_circle");
let switchBtn = document.querySelectorAll(".switch-btn");
let aContainer = document.querySelector("#a-container");
let bContainer = document.querySelector("#b-container");
let allButtons = document.querySelectorAll(".submit");

let getButtons = (e) => e.preventDefault()
let changeForm = (e) => {
    // 修改类名
    switchCtn.classList.add("is-gx");
    setTimeout(function () {
        switchCtn.classList.remove("is-gx");
    }, 1500)
    switchCtn.classList.toggle("is-txr");
    switchCircle[0].classList.toggle("is-txr");
    switchCircle[1].classList.toggle("is-txr");

    switchC1.classList.toggle("is-hidden");
    switchC2.classList.toggle("is-hidden");
    aContainer.classList.toggle("is-txl");
    bContainer.classList.toggle("is-txl");
    bContainer.classList.toggle("is-z");
}
// 点击切换
let shell = (e) => {
    for (var i = 0; i < allButtons.length; i++)
        allButtons[i].addEventListener("click", getButtons);
    for (var i = 0; i < switchBtn.length; i++)
        switchBtn[i].addEventListener("click", changeForm)
}
window.addEventListener("load", shell);