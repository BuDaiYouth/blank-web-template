import request from './core/axios';

export function login(params) {
    return request({
        url: '/api/basic/user/login',
        method: 'post',
        data: params
    })
}