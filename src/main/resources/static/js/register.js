function checkPasswords() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;

    if (password !== confirmPassword) {
        alert("密码和确认密码不一致，请重新输入！");
        return false; // 阻止表单提交
    }
    return true; // 密码一致，可以继续后续操作，如表单提交
}