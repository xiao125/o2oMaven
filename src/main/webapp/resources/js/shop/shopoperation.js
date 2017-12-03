/**
 * Created by Administrator on 2017/12/3.
 */
$(function () {
    // 从URL里获取shopId参数的值
   // var shopId = getQueryString('shopId');

    // 由于店铺注册和编辑使用的是同一个页面，
    // 该标识符用来标明本次是添加还是编辑操作
   // var isEdit = shopId ? true : false;
    // 用于店铺注册时候的店铺类别以及区域列表的初始化的URL
    var initUrl ='/o2oMaven/shopadmin/getshopinitinfo';

    // 注册店铺的URL
    var registerShopUrl = '/o2oMaven/shopadmin/registershop';

    // 编辑店铺信息的URL
    var editShopUrl = '/o2oMaven/shopadmin/modifyshop';

    alert(initUrl);

    getShopInitInfo();

   /* if(!isEdit){
        getShopInitInfo();
    }else {
      //  getShopInfo(shopId);
    }*/


    /**
     * {
    "shopCategoryList": [
        {
            "shopCategoryId": 2,
            "shopCategoryName": "牛肉火锅",
            "shopCategoryDesc": "test",
            "shopCategoryImg": "test",
            "priority": 3,
            "createTime": null,
            "lastEditTime": null,
            "parent": null
        }
    ],
    "success": true,
    "areaList": [
        {
            "areaId": 2,
            "areaName": "宝安",
            "priority": 6,
            "createTime": 1511690257000,
            "lastEditTime": 1511690035000
        },
        {
            "areaId": 1,
            "areaName": "西乡",
            "priority": 3,
            "createTime": 1511690235000,
            "lastEditTime": 1511690011000
        }
    ]
}
     */

// 取得所有二级店铺类别以及区域信息，并分别赋值进类别列表以及区域列表
    function getShopInitInfo() {

        $.getJSON(initUrl,function (data) { //data：获取后台返回的数据

            if (data.success){
                var tempHtml='';
                var tempAreaHtml='';
                data.shopCategoryList.map(function (item,index) {

                    tempHtml +='<option data-id="'+ item.shopCategoryId + '">'+item.shopCategoryName + '</option>';

                });

                data.areaList.map(function (item,index) {

                    tempAreaHtml +='<option data-id="' +item.areaId + '">' + item.areaName + '</option>';
                })

                $('#shop-category').html(tempHtml); //商铺分类
                $('#area').html(tempAreaHtml);  //区域分类
            }
        });
    }

    // 提交按钮的事件响应，分别对店铺注册和编辑操作做不同响应
    $('#submit').click(function () {
        // 创建shop对象
        var shop ={};
       /* if (isEdit){

            //若属于编辑，则给shopId赋值
            shop.shopId = shopId;
        }*/
        //获取表单里的数据并填充进对应的店铺属性中
        shop.shopName = $('#shop-name').val();
        shop.shopAddr = $('#shop-addr').val();
        shop.phone = $('#shop-phone').val();
        shop.shopDesc = $('#shop-desc').val();

        //选择选定好的店铺类别
        shop.shopCategory = {
            shopCategoryId : $('#shop-category').find('option').not(function () { //从列表中找出需要的option
                return !this.selected;
            }).data('id')

        };

        //选择选定好的区域信息
        shop.area = {

            areaId : $('#area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };

        // 获取上传的图片文件流
        var shopImg = $('#shop-img')[0].files[0];
        // 生成表单对象，用于接收参数并传递给后台
        var formData = new FormData();
        // 添加图片流进表单对象里
        formData.append("shopImg",shopImg);
        // 将shop json对象转成字符流保存至表单对象key为shopStr的的键值对里
        formData.append('shopStr',JSON.stringify(shop));
        // 获取表单里输入的验证码
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual){
            $.toast('请输入验证码!');
            return;
        }

        formData.append('verifyCodeActual',verifyCodeActual);
        // 将数据提交至后台处理相关操作
        $.ajax({

            url :  registerShopUrl,
            type : 'POST',
            data : formData,
            contentType : false,
            processData : false,
            cache : false,
            success : function (data) {
                if (data.success){
                    $.toast('提交成功!');
                   /* if(!isEdit){
                        // 若为注册操作，成功后返回店铺列表页
                        window.location.href = "/o2oMaven/shopadmin/shoplist";
                    }*/
                }else {

                    $.toast('提交失败!'+data.errMsg);
                }
                // 点击验证码图片的时候，注册码会改变
                $('#captcha_img').click();
            }
        });
    });

})