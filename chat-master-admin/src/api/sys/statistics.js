import request from '@/utils/request'

// 查询首页总数据
export function getTotalData(query) {
    return request({
        url: '/statistics/index/total',
        method: 'get',
        params: query
    })
}

// 查询首页折线图数据
export function getLineData(query) {
    return request({
        url: '/statistics/index/line',
        method: 'get',
        params: query
    })
}

// 查询首页雷达图数据
export async function getRaddarData(query) {
    return request({
        url: '/statistics/index/raddar',
        method: 'get',
        params: query
    })
}

// 查询首页饼图数据
export async function getPieData(query) {
    return request({
        url: '/statistics/index/pie',
        method: 'get',
        params: query
    })
}

// 查询首页柱状图数据
export async function getBarData(query) {
    return request({
        url: '/statistics/index/bar',
        method: 'get',
        params: query
    })
}