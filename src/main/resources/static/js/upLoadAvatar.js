function previewAvatar() {
        var file = document.getElementById('avatarFile').files[0];
        var reader = new FileReader();
        reader.onloadend = function () {
            document.getElementById('avatarPreview').src = reader.result;
        };
        if (file) {
            reader.readAsDataURL(file);
        } else {
            document.getElementById('avatarPreview').src = "https://youxiang-1317606226.cos.ap-beijing.myqcloud.com/d8600a84-e0e4-4a40-9b7d-8bccd5086621_Abner (2).png";
        }
    }