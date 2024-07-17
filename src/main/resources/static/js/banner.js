let currentSlide = 0;
let slideInterval;  // 슬라이드 인터벌을 저장할 변수 선언

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

function startSlideShow() {
    slideInterval = setInterval(nextSlide, 4000);  // 4000ms 마다 nextSlide 함수를 호출
}

function stopSlideShow() {
    clearInterval(slideInterval);  // 인터벌 취소
}

document.addEventListener('DOMContentLoaded', () => {
    startSlideShow();  // 문서 로드 완료 후 슬라이드 쇼 시작
    showSlide(currentSlide);
});

// 필요한 경우 슬라이드 쇼를 멈출 수 있도록 stopSlideShow 함수를 호출할 수 있습니다.
