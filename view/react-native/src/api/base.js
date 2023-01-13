import request from './axios';

export async function getPicture(params) {
  return request('/captcha/get',{
    method: 'POST',
    data: params,
  });
}
export async function reqCheck(params) {
  return request('/captcha/check',{
    method: 'POST',
    data: params,
  });
}
