document.addEventListener('DOMContentLoaded', () => {
    const images = document.querySelectorAll('img[data-fallback]');

    images.forEach((img) => {
        img.onerror = function () {
            this.src = this.getAttribute('data-fallback');
        };
    });
});
