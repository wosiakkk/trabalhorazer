$(".buscarItens").click(function () {
	var $row = $(this).closest("tr"); //encontrando a linha na qual o botão foi clicado
	var idPedido = $row.find(".idPedido").text(); //pegando o id do pedido linha
			
	$.ajax({
		type: 'GET',
		url: '/itens/'+idPedido,
		success: function (data) {
			console.log(data);
			var item ='';
			$.each(data, function (i, item) {
				var label = '<div class="divider"></div><h6>'+item.quantidade+' - '+ item.produto.descricao+'</h6><br/><div class="divider"></div>'
				$(".modal-content").append(label);
			})
					
			$("#modalPedidos").modal('open');
				     
		},
		error: function (data) {
           alert(data + "Probelma ao retornar os dados.");
        }
	});
});