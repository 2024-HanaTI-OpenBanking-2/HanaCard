//card.js
let currentIndex = 0;

document.addEventListener('DOMContentLoaded', function() {
    console.log("문서가 완전히 로드되었습니다.");
    fetchCards();
});

// 카드 데이터를 불러오는 함수
async function fetchCards() {
    const response = await fetch('/api/cards');
    const cards = await response.json();
    displayCards(cards);
}

// 카드 데이터를 HTML로 변환하여 표시
function displayCards(cards) {
    const container = document.querySelector('.card-slides');
    container.innerHTML = cards.map(card => `
        <div class="card">
            <img src="${card.cardImageUrl}" alt="${card.cardName}" class="card-img"/>
            <div class="card-info">
                <h3>${card.cardName}</h3>
            </div>
        </div>
    `).join('');
}

// 이전 카드 보기
function prevCard() {
    if (currentIndex > 0) {
        currentIndex--;
        updateSlidePosition();
    }
}

// 다음 카드 보기
function nextCard() {
    const totalCards = document.querySelectorAll('.card').length;
    if (currentIndex < totalCards - 1) {
        currentIndex++;
        updateSlidePosition();
    }
}

// 슬라이드 위치 업데이트
function updateSlidePosition() {
    const slideWidth = document.querySelector('.card').clientWidth;
    const container = document.querySelector('.card-slides');
    container.style.transform = `translateX(-${currentIndex * slideWidth}px)`;
}

document.addEventListener('DOMContentLoaded', fetchCards);
