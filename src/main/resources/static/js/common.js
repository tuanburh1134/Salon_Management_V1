document.addEventListener('DOMContentLoaded', function() {
    // Tìm phần tử thông báo thành công
    const successAlert = document.querySelector('.alert-success');

    // Nếu phần tử này tồn tại trên trang
    if (successAlert) {
        // Bắt đầu đếm ngược 3 giây (3000 mili giây)
        setTimeout(() => {
            // Thêm class 'hide' để kích hoạt hiệu ứng mờ dần trong CSS
            successAlert.classList.add('hide');

            // Sau khi hiệu ứng mờ dần kết thúc (300ms), ẩn hoàn toàn phần tử
            // để nó không chiếm không gian trên trang
            setTimeout(() => {
                if (successAlert.parentElement) {
                    successAlert.parentElement.style.display = 'none';
                }
            }, 300); // Thời gian này phải khớp với transition trong CSS

        }, 3000); // 3 giây
    }
});