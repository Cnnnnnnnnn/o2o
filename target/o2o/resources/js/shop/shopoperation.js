$(function () {
    var initUrl = '/o2o/shop/getshopinitinfo';//获取店铺的初始信息
    var registerShopUrl = '/o2o/shop/registershop';//注册商店
    /*
    从后台获取店铺信息和区域信息返回给前台页面
     */
    getShopInitInfo();
    function getShopInitInfo() {
        /**
         *
         * 参数一、请求地址
         * 参数二、回调函数（会拿到后台传回来列表数据）
         */
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = "";
                var tempAreaHtml = "";
                data.shopCategoryList.map(function (itaem, index) {
                    tempHtml += '<option data-id="' + itaem.shopCategoryId + '">' + itaem.shopCategoryName + '</option>';
                });
                data.areaList.map(function (iteam, index) {
                    tempAreaHtml += '<option data-id="' + iteam.areaId + '">' + iteam.areaName + '</option>';
                });
                $('#shop-category').html(tempHtml);//店铺分类列表
                $('#area').html(tempAreaHtml);//区域列表
            }
        });
        /**
         * 提交按钮捕获数据，传到后台验证
         */
        $('#submit').click(function () {
            var shop = {};
            shop.shopName = $('#shop-name').val();
            shop.shopAddr = $('#shop-addr').val();
            shop.phone = $('#shop-phone').val();
            shop.shopDesc = $('#shop-desc').val();

            shop.shopCategory = {
                shopCategoryId: $('#shop-category').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            shop.area = {
                areaId: $('#area').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            var shopImg = $('#shop-img')[0].files[0];
            var formData = new FormData();
            formData.append('shopImg', shopImg);
            formData.append('shop', JSON.stringify(shop));
            var VerifyCodeActual = $('#j_captcha').val();
            if(VerifyCodeActual){
                formData.append('VerifyCodeActual',VerifyCodeActual);
                alert(formData.get("shop"));
                $.ajax({
                    url:registerShopUrl,    //提交地址
                    type:'post',    //提交方式
                    data:formData,  //提交的数据
                    contentType:false,
                    // contentType:"application/json",
                    cache:false,
                    processData:false,
                    /*
                    ajax提交后的回调函数，传回来
                     */
                    success:function (data) {
                        if(data.success){
                            $.toast("提交成功！");
                        }else {
                            $.toast("提交失败！"+data.errorMsg);
                        }
                        $('#captcha_img').click();//点击提交后，图片会自动更新一次
                    }
                });
            }else {
                $.toast("输入验证码！");
            }


        });
    }
});