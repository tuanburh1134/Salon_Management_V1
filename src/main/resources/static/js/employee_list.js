document.addEventListener('DOMContentLoaded', function() {

    // --- PHẦN ẨN/HIỆN NÚT "CLEAR" ---
    const searchInput = document.getElementById('searchInput');
    const selects = document.querySelectorAll('form#searchForm select');
    // Chỉ khai báo một lần duy nhất ở đây
    const clearButtonContainer = document.getElementById('clearButtonContainer');

    if (searchInput && clearButtonContainer) {
        const checkFilters = () => {
            const isKeywordEmpty = searchInput.value.trim() === '';
            const areAllSelectsEmpty = Array.from(selects).every(select => select.value === '');

            if (isKeywordEmpty && areAllSelectsEmpty) {
                clearButtonContainer.style.display = 'none';
            } else {
                clearButtonContainer.style.display = 'block';
            }
        };

        searchInput.addEventListener('input', checkFilters);
        selects.forEach(select => select.addEventListener('change', checkFilters));
        checkFilters();
    }
    // Tự động ẩn thông báo thành công sau 3 giây
    const alert = document.querySelector('.alert.alert-success, .alert-success');
    if (alert) {
        setTimeout(() => {
            alert.classList.add('fade');
            setTimeout(() => {
                alert.style.display = 'none';
            }, 500); // Đợi hiệu ứng fade hoàn tất
        }, 3000);
    }

    // --- PHẦN MỚI ---
    // Xử lý logic cho Modal xác nhận xóa
    const deleteConfirmModal = document.getElementById('deleteConfirmModal');
    if (deleteConfirmModal) {
        deleteConfirmModal.addEventListener('show.bs.modal', function(event) {
            // Lấy nút đã kích hoạt modal
            const button = event.relatedTarget;

            // Lấy thông tin từ các thuộc tính data-* của nút
            const employeeId = button.getAttribute('data-employee-id');
            const employeeName = button.getAttribute('data-employee-name');

            // Cập nhật nội dung của modal
            const modalBody = deleteConfirmModal.querySelector('.modal-body');
            modalBody.innerHTML = `Bạn có chắc chắn muốn xóa nhân viên <strong>${employeeName}</strong>? <br/>Hành động này không thể hoàn tác.`;

            // Cập nhật action của form xóa bên trong modal
            const deleteForm = deleteConfirmModal.querySelector('#deleteForm');
            // Dòng này là quan trọng nhất, nó sửa lỗi của bạn
            deleteForm.setAttribute('action', `/employees/${employeeId}/delete`);
            console.log('Đã cập nhật action của form thành:', deleteForm.getAttribute('action'));
        });
    }

});