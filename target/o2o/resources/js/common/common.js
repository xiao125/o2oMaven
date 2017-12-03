/**
 * Created by Administrator on 2017/12/3.
 */

function changeVerifyCode(img) {
    img.src ="../Kaptcha?"+ Math.floor(Math.random()*100);
}