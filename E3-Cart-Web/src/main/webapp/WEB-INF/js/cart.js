var CART = {
	itemNumChange : function(){
		$(".increment").click(function(){//＋
			var _thisInput = $(this).siblings("input");
			_thisInput.val(eval(_thisInput.val()) + 1);
			
			$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".action",function(data){
				CART.refreshTotalPrice();
				CART.refreshPrice(_thisInput,_thisInput.attr("itemId"));
			});
		});
		$(".decrement").click(function(){//-
			var _thisInput = $(this).siblings("input");
			if(eval(_thisInput.val()) == 1){
				return ;
			}
			_thisInput.val(eval(_thisInput.val()) - 1);
			$("#total_price").val(_thisInput.attr("itemPrice") * _thisInput.val());//重新计算小记
			$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".action",function(data){
				CART.refreshTotalPrice();
				CART.refreshPrice(_thisInput,_thisInput.attr("itemId"));
			});
		});
		/*$(".itemnum").change(function(){
			var _thisInput = $(this);
			$.post("/service/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val(),function(data){
				CART.refreshTotalPrice();
			});
		});*/
	},
	refreshTotalPrice : function(){ //重新计算总价
		var total = 0;
		$(".itemnum").each(function(i,e){
			var _this = $(e);
			total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
		});
		$("#allMoney2").html(new Number(total/100).toFixed(2)).priceFormat({ //价格格式化插件
			 prefix: '¥',
			 thousandsSeparator: ',',
			 centsLimit: 2
		});
	},
	refreshPrice : function(e,itemid){ //重新计算商品小计
		// 计算新的商品小计
		var total = e.attr("itemPrice") * e.val();
		// 获取小计元素
		$(".totalprice").each(function(i,e){
			var _this = $(e);
			var recentId = _this.attr("itemid");
			if (recentId == itemid) {
				_this.html(new Number(total/100).toFixed(2)).priceFormat({ //价格格式化插件
			     prefix: '¥',
			     thousandsSeparator: ',',
			     centsLimit: 2
				});
				
			}
		});
		
	}
};

$(function(){
	CART.itemNumChange();
});