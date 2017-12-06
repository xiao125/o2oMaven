/**
 * Created by Administrator on 2017/12/3.
 */


//随机生成四位数验证码
function changeVerifyCode(img) {
    img.src ="../Kaptcha?"+ Math.floor(Math.random()*100);
}

//识别url中的是否传入shopId参数
// 例如：http://127.0.0.1:8080/o2oMaven/shopadmin/shopoperation?shopId=1
function getQueryString(name) {

    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return '';
}