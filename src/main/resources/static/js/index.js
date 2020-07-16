$(function() {
// 轮播图开始
		var prev = document.querySelector(".prev");
		var next = document.querySelector(".next");
		var btns = document.querySelectorAll(".btn");
		var swipe = document.querySelector(".swipe");
		var boxs = document.getElementsByClassName("box");

		var w = document.documentElement.clientWidth;

		// 声明当前展示的图片的顺序
		var index = 0;
		var autoTinmer;

		for (var i = 0; i < boxs.length; i++) {
			boxs[i].style.width = w + "px";
		}

		swipe.style.width = 5 * w + "px";
		// 自动播放
		// 封装函数
		// 判断索引，改变left值的函数
		function nextFn() {
			index = index == 4 ? 0 : ++index;
			// 定位元素left才会生效
			// console.log("next" + w)
			swipe.style.left = -index * w + "px";
		}

		// 定时器的函数
		function autoPlay() {
			autoTinmer = setInterval(function() {
				// left初始值为0，每张图片的left是图片宽度乘图片数量
				// 只有五张图片，能移动的left最大的left是四张图的，index等于4的时候恢复到0，否则index++
				nextFn();
				iconHover(index);
				for (var i = 0; i < boxs.length; i++) {
					boxs[i].style.backgroundImage = "url(img/"+(i+1) +".jpg)"
					boxs[i].style.backgroundRepeat = "no-repeat";
					boxs[i].style.backgroundSize = "100% 100%"
				}
			}, 3000);
		}
		// 页面一刷新就自动播放；
		autoPlay();

		//下一张
		$(".next1").click(function() { //下一张
			// 先停止自动播放 先展示完下一张
			clearInterval(autoTinmer);
			// 点击next立即展示下一张
			nextFn();
			iconHover(index);
			//继续自动播放
			autoPlay();
		});

		//上一张
		$(".prev1").click(function() {
			clearInterval(autoTinmer);
			// 点击prev立即展示上一张
			index = index == 0 ? 4 : --index;
			swipe.style.left = -index * w + "px";
			iconHover(index);
			autoPlay();
		});

		$(".next1").mouseenter(function() { //下一张
			$(".btn1").css("background-color", "white");
		});
		$(".next1").mouseleave(function() {
			$(".btn1").css("background-color", "transparent");
		});

		$(".prev1").mouseenter(function() { //下一张
			$(".btn1").css("background-color", "white");
		});
		$(".prev1").mouseleave(function() {
			$(".btn1").css("background-color", "transparent");
		});

		//鼠标碰触圆点图标实现图片左右轮播
		$(".icon-list li").click(function() {
			var index = $(this).index();
			// $(".pic-list").animate({
			// 	left: -index * 600
			// }, 500);

			clearInterval(autoTinmer);

			var i = this.getAttribute("data-i");
			swipe.style.left = -i * w + "px";
			index = i

			iconHover(index);
			autoPlay();
		});
		//实现被选图片对应圆点图标索引更新
		function iconHover(index) {
			$(".icon-list li").eq(index).addClass("active").siblings().removeClass("active");
		}

		// 点击对应顺序按钮
		// 循环遍历所有1-5的button
		for (var j = 0; j < btns.length; j++) {
			// 每一个btn绑定点击事件
			btns[j].onclick = function() {
				// 先停止自动播放的定时器
				clearInterval(autoTinmer);
				// 获取当前按钮在btns中的顺序获取到
				// 这里不能用indexOf方法，因为这是一个伪数组，不是一个数组，不能使用数组的方法
				// getAttribute获取为标签自定义html属性的值
				var i = this.getAttribute("data-i");
				// 根据这个顺序设置swipe的left值
				swipe.style.left = -i * w + "px";
				index = i
				// 恢复自动播放的定时器
				autoPlay();
			}
		}
// 轮播图结束
		//获取窗口大小事件
		function getWindowSize(w){
			for (var i = 0; i < boxs.length; i++) {
				boxs[i].style.width = w + "px";
			}
			swipe.style.width = 5 * w + "px";
		}

		$(window).resize(function () {          //当浏览器大小变化时
		    w = document.documentElement.clientWidth;
			getWindowSize(w);
			
		});
})