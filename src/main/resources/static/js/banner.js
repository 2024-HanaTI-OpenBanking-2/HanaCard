let currentSlide = 0;

function showSlide(index) {
    const slides = document.querySelectorAll('.banner-slide');
    const totalSlides = slides.length;
    if (index >= totalSlides) {
        currentSlide = 0;
    } else if (index < 0) {
        currentSlide = totalSlides - 1;
    } else {
        currentSlide = index;
    }
    const newTransformValue = -currentSlide * 100 + '%';
    document.querySelector('.banner-container').style.transform = 'translateX(' + newTransformValue + ')';
}

function nextSlide() {
    showSlide(currentSlide + 1);
}

function prevSlide() {
    showSlide(currentSlide - 1);
}

document.addEventListener('DOMContentLoaded', () => {
    showSlide(currentSlide);
});


