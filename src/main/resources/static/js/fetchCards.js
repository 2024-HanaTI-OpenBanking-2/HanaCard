document.getElementById('loadCardsBtn').addEventListener('click', function () {
    const container = document.getElementById('cardContainer');
    const loadingSpinner = document.createElement('div');
    loadingSpinner.id = 'loadingSpinner';
    loadingSpinner.style.display = 'flex'; // 스피너 표시
    loadingSpinner.innerHTML = '<img src="/images/Loading_icon.gif" alt="Loading..."/>';
    container.appendChild(loadingSpinner);

    fetch('/api/card-list')
        .then(response => response.json())
        .then(data => {
            loadingSpinner.style.display = 'none'; // 데이터 로드 완료 후 스피너 숨김
            container.innerHTML = ''; // 기존 카드 정보를 지우고 새로운 정보를 추가합니다.
            data.forEach(card => {
                const cardElement = document.createElement('div');
                cardElement.className = 'card-slide';
                cardElement.innerHTML = `
                    <div class="card" data-card-id="${card.customerCardId}">
                        <img src="${card.base64EncodedImage ? `data:image/jpeg;base64,${card.base64EncodedImage}` : '/images/travellog_img.gif'}" alt="${card.cardName}">
                        <div class="card-info">
                            <p>${card.cardName}</p>
                            <div class="card-info-container">
                                <p style="display: none;">ID: ${card.customerCardId}</p>
                                <p>잔액 : ${card.cardBalance}</p>
                                <a href="#" class="charge-link">충전하기</a>
                            </div>
                        </div>
                    </div>
                `;
                container.appendChild(cardElement);

                // 충전하기 링크에 이벤트 리스너 추가
                let chargeLink = cardElement.querySelector('.charge-link');
                chargeLink.addEventListener('click', function (event) {
                    event.preventDefault(); // 기본 링크 동작 방지
                    showChargeModal(this.closest('.card').getAttribute('data-card-id'));
                });
            });
        })
        .catch(error => {
            console.error('Error fetching data: ', error);
            loadingSpinner.style.display = 'none'; // 에러 시 스피너 숨김
        });
});

let currentIndex = 0; // 슬라이더의 현재 위치 인덱스

function updateSliderPosition() {
    const slideWidth = document.querySelector('.card-slide').clientWidth;
    const container = document.getElementById('cardContainer');
    container.style.transform = `translateX(-${currentIndex * slideWidth}px)`;
}

// 이동 로직 추가 및 수정
function nextCard() {
    const slides = document.querySelectorAll('.card-slide');
    if (currentIndex < slides.length - 1) {
        currentIndex++;
        updateSliderPosition();
    }
}

function prevCard() {
    if (currentIndex > 0) {
        currentIndex--;
        updateSliderPosition();
    }
}

// modal.js
// 금액 버튼 클릭 시 처리

function showChargeModal(cardId) {
    // 모든 modal-screen 요소들을 숨기고
    document.querySelectorAll('.modal-screen').forEach(screen => {
        screen.classList.remove('active');
    });

    // 카드 선택 화면을 활성화하고, 계좌 정보를 불러옵니다
    document.getElementById('cardSelectionScreen').classList.add('active');

    // 모달 컨테이너를 보이게 함
    document.getElementById('modalContainer').classList.remove('hidden');

    // 계좌 정보를 불러옴
    loadAccountInfo(cardId);  // 이제 여기에서 cardId를 활용하여 계좌 정보를 불러옵니다
}

// 카드 선택 후 금액 입력 화면으로 이동
// 카드 선택 후 금액 입력 화면으로 이동
function goToAmountScreen() {
    const selectedAccount = document.getElementById('cardSelect').value;
    const selectedAccountText = document.getElementById('cardSelect').selectedOptions[0].textContent;
    const selectedAccountNumber = document.getElementById('cardSelect').selectedOptions[0].getAttribute('data-account-number');

    // 선택된 계좌 정보와 계좌 번호를 금액 입력 화면에 표시
    document.querySelector('#amountScreen h5').textContent = `선택된 계좌: ${selectedAccountText}`;

    // 화면 전환
    document.getElementById('cardSelectionScreen').classList.remove('active');
    document.getElementById('amountScreen').classList.add('active');

    // 여기에서 accountId를 사용하는 대신 selectedAccount를 사용해야 합니다.
    document.getElementById('selectedAccount').dataset.accountId = selectedAccountNumber; // 올바르게 계좌 ID 저장
}


function goToConfirmation(data) {
    document.querySelector('#confirmationScreen .charged-amount').textContent = `${data.amount.toLocaleString()}원을 충전하였습니다.`;
    document.querySelector('#bankAccountDetails').textContent = `${data.bankCodeStd} ${data.accountNum}`;
    document.querySelector('#finalBalance').textContent = `${parseFloat(data.balanceAmt).toLocaleString()}원`;

    document.getElementById('amountScreen').classList.remove('active');
    document.getElementById('confirmationScreen').classList.add('active');
}


function closeModal() {
    document.querySelectorAll('.modal-screen').forEach(screen => {
        screen.classList.remove('active');
    });
    document.getElementById('modalContainer').classList.add('hidden');
}

function initModal() {
    document.getElementById('modalContainer').classList.add('hidden');
    document.getElementById('cardSelectionScreen').classList.add('active');
    document.getElementById('amountScreen').classList.remove('active');
    document.getElementById('confirmationScreen').classList.remove('active');
}

function loadAccountInfo(cardId) {
    const spinner = document.getElementById('spinner');
    if (!cardId) {
        console.error('Invalid card ID:', cardId);
        alert('카드 ID가 유효하지 않습니다.');
        return;
    }

    spinner.style.display = 'flex'; // 스피너 표시

    fetch(`/api/account-list?customer-card-id=${cardId}`)
    .then(response => {
        if (!response.ok) throw new Error('Network response was not ok');
        return response.json();
    })
    .then(data => {
        updateAccountSelect(data); // 계좌 정보를 바탕으로 select 옵션 업데이트
        spinner.style.display = 'none'; // 데이터 로드 완료 후 스피너 숨김
    })
    .catch(error => {
        console.error('Error fetching account data:', error);
        alert('계좌 정보를 불러오는 데 실패했습니다.');
        spinner.style.display = 'none'; // 에러 발생 시 스피너 숨김
    });
}


function updateAccountSelect(accounts) {
    const select = document.getElementById('cardSelect');
    select.innerHTML = ''; // 기존의 옵션들을 초기화
    accounts.forEach(account => {
        const option = document.createElement('option');
        option.value = account.accountId; // 실제 accountId를 value로 설정
        option.setAttribute('data-account-number', account.accountNum); // 계좌번호 데이터 속성 추가
        option.textContent = `${account.bankCodeStd}: ${account.accountNum}`; // 은행명과 계좌번호 마지막 4자리
        select.appendChild(option);
    });
}

document.querySelectorAll('.amount-button').forEach(button => {
    button.addEventListener('click', function () {
        const amount = parseInt(document.getElementById('amountInput').value);
        const addAmount = parseInt(this.getAttribute('data-amount'));
        document.getElementById('amountInput').value = amount + addAmount;
    });
});

function submitCharge() {
    const amount = document.getElementById('amountInput').value;
    const accountId = document.getElementById('selectedAccount').dataset.accountId;
    console.log(amount)
    console.log(accountId)


    fetch('/api/paymoney-charge', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            accountId: accountId,
            amount: amount
        })
    })
        .then(response => response.json())
        .then(data => {
            // console.log('Success:', data);
            goToConfirmation(data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}


// 페이지 로드 시 모달 초기화
document.addEventListener('DOMContentLoaded', initModal);

