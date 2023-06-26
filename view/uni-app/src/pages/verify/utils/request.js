// let baseUrl = "https://captcha.anji-plus.com/captcha-api"
let baseUrl = "http://10.108.11.46:8088"

export const myRequest = (option = {}) => {
    return new Promise((reslove, reject) => {
        uni.request({
            url: baseUrl + option.url,
            data: option.data,
            method: option.method || "GET",
            success: (result) => {
                reslove(result)
            },
            fail: (error) => {
                reject(error)
            }
        })
    })
}
