// Token cache
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


// User cache
export function saveUser(token) {
    localStorage.setItem("USER_KEY", token)
}

export function getUser() {
    let token = localStorage.getItem('USER_KEY')
    if (token === undefined || token === 'undefined' || token === null) {
        token = ''
    }
    return token
}

export function clearUser() {
    localStorage.removeItem('USER_KEY')
}
