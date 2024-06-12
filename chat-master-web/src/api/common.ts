import { get, post } from '@/utils/request'

// 发送短信
export function fetchSms<T>(data: object) {
    return post<T>({
        url: '/app/api/sms/send',
        data
    })
}

// 获取配置信息
export function fetchChatConfig<T>() {
    return get<T>({
        url: '/app/api/home/config',
    })
}

// 获取字典信息
export function fetchDictByType<T>(param: string) {
    return get<T>({
        url: '/app/api/dict/select/' + param,
    })
}

// 获取配置信息
export function fetchAgreement<T>(type: Number) {
    return get<T>({
        url: '/app/api/content/agreement/' + type,
    })
}

// 获取助手分类
export function listAssistantType<T>() {
    return get<T>({
        url: '/app/api/assistant/type',
    })
}

// 根据分类获取助手
export function listAssistantByType<T>(data: { current: number, size: number, typeId: number }) {
    return get<T>({
        url: '/app/api/assistant',
        data
    })
}

// 随机获取助手
export function listAssistantRandom<T>() {
    return get<T>({
        url: '/app/api/assistant/random',
    })
}