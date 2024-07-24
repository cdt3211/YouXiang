document.addEventListener('DOMContentLoaded', function() {

    document.getElementById('linkA').addEventListener('click', function() {
        showContent.call(this, 'myPost');
        return false;
    });
    document.getElementById('linkB').addEventListener('click', function() {
        showContent.call(this, 'myCollection');
        return false;
    });
    document.getElementById('linkC').addEventListener('click', function() {
        showContent.call(this, 'myLikes');
        return false;
    });
    var defaultLink = document.getElementById('linkA');
    showContent.call(defaultLink, 'myPost');
});

function showContent(contentId) {
    // 隐藏所有内容盒子
    var contentBoxes = document.getElementsByClassName('all');
    for (var i = 0; i < contentBoxes.length; i++) {
        contentBoxes[i].style.display = 'none';
    }

    // 显示指定ID的内容盒子
    var contentBox = document.getElementById(contentId);
    if (contentBox) {
        contentBox.style.display = 'block';
    }

    document.querySelectorAll('.nav a').forEach(function(link) {
        link.classList.remove('active-link');
    });
    this.classList.add('active-link');
}




