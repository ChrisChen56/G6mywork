$(".alert-area").on("click",".xx",function(){
			var $ContextPath = $("#getContextPath").val();
			var $mbrno = $("#mbrno").val();
			var $index = parseInt($(this).closest("button")[0].value);
			//由後往前數的索引值
			var redisIndex = $index * (-1);
			console.log(redisIndex);
			console.log('enter ajax');
			$.ajax({
				type: "POST",
				url: $ContextPath+'/front-end/shgm/shgm.do?action=MsgUpdate',
				data: {"mbrno":$mbrno,"index":redisIndex},
				dataType: "json",
				cache: false,
				success: function(response){
					var total = parseInt($("#circle").text());
					if(total === 1)
						$("#circle").remove();
					$("#circle").text(total-1);
					console.log(response.success);
				},
				error: function(result) {
					alert("目前不允許此操作");
				}
			});
		});