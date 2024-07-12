import http from 'k6/http';
import { check, group } from 'k6';

// Options for the test
export const options = {
  vus: 3, // Number of Virtual Users
};

const orderId = '1018674689176219648';

export default function () {
  const url = `http://localhost:8080/order/status/${orderId}`;
  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  // Sending 3 requests concurrently
  let responses = [];

  group('Sending 3 concurrent requests', () => {
    responses = http.batch([
      ['PUT', url, null, params],
      ['PUT', url, null, params],
      ['PUT', url, null, params],
    ]);
  });

  // Check responses
  let success = 0;
  let badRequest = 0;

  responses.forEach((response) => {
    if (response.status === 200) {
      success++;
    } else if (response.status === 400) {
      badRequest++;
    }
  });

  check(success, { 'One request succeeded': (s) => s === 1 });
  check(badRequest, { 'Two requests failed with 400 error': (b) => b === 2 });
}
