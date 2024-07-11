import http from 'k6/http';

export let options = {
    vus: 1000, // 가상 사용자 수
    duration: '1s', // 테스트 지속 시간
};

export default function () {
    const url = 'http://localhost:8080/order';
    const payload = JSON.stringify({
        // 필요한 경우 요청 페이로드 추가
        // key: 'value'
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    http.post(url, payload, params);
}
