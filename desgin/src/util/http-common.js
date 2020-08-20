import axios from 'axios';

// axios 객체 생성
export default axios.create({
//   baseURL: 'http://localhost:9999/happyhouse/api',
  baseURL: 'http://i3c202.p.ssafy.io',
  headers: {
    'Content-type': 'application/json',
  },
});