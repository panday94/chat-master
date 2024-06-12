import { get, put } from '@/utils/request'

// 登录
export function fetchVerify<T>(data: object) {
    return get<T>({
        url: '/app/api/oauth/token',
        data,
    })
}

// 获取用户信息
export function fetchSession<T>() {
    return get<T>({
        url: '/app/user',
    })
}

// 获取用户可用模型信息
export function fetchModel<T>() {
    return get<T>({
        url: '/app/user/model',
    })
}

// 修改个人信息
export function updateUser<T>(data: object) {
    return put<T>({
        url: '/app/user',
        data: data
    })
}

// 开启上下文
export function updateContext<T>(data: object) {
    return put<T>({
        url: '/app/user/context',
        data: data
    })
}

// 修改密码
export function updatePassword<T>(data: object) {
    return put<T>({
        url: '/app/user/password/update',
        data: data
    })
}