document.addEventListener('DOMContentLoaded', function() {
    // 获取所有的下拉菜单项
    var dropdownItems = document.querySelectorAll('.dropdown-content .dropdown-item');
    // 获取输入框元素
    var inputElement = document.getElementById('selected-item');

    // 为每个下拉菜单项添加点击事件监听器
    dropdownItems.forEach(function(item) {
        item.addEventListener('click', function(event) {
            // 阻止默认的链接跳转行为
            event.preventDefault();
            // 获取链接的自定义数据值（即链接文本）
            var value = this.getAttribute('data-value');
            // 将值设置到输入框中
            inputElement.value = value;
            // 可以选择关闭下拉菜单（如果需要的话）
            // 这里省略了关闭下拉菜单的代码，因为实现方式取决于你的CSS和HTML结构
        });
    });
});

var dropdown = document.getElementById("myDropdown");

// 切换下拉菜单显示/隐藏的函数
function toggleDropdown() {
    dropdown.classList.toggle("show");
}

// 当用户点击下拉菜单之外的任何地方时关闭下拉菜单
window.onclick = function(event) {
    if (!event.target.matches('.dropdown-toggle') && !event.target.matches('.dropdown-menu') && !event.target.matches('.dropdown-menu a')) {
        dropdown.classList.remove("show");
    }
}