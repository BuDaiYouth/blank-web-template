export function setToken(token) {
    localStorage.setItem("USER_TOKEN", token)
}

export function getToken() {
    let token = localStorage.getItem('USER_TOKEN')
    if (token === undefined || token === 'undefined' || token === null) {
        token = ''
    }
    return token
}

export function clearToken() {
    localStorage.removeItem('USER_TOKEN')
}