import request from './core/axios';

export function testing() {
    return request({
        url: '/api/template/testing',
        method: 'get'
    })
}