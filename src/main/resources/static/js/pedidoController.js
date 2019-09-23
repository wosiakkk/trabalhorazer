/*Script qu controla adiçãod e campos de produto de forma dinâmica, restringindo a 4. E também faz a opreação de save usando ajax*/
$(document).ready(function() {
	var max_campos = 5; //máximo de campos para add
	var wrapper = $("#itens");//componente dos itens
	var add_botao = $(".addbotao");//o botão de add
	var count =1; //contador para as adições
	//event de click no botão de add
	$(".addbotao").click(function(e) {
		var $row = $(this).closest("tr"); //encontrando a linha na qual o botão foi clicado
		/*pegando os valores dos campos na linha clicada*/
		var quantidade = $row.find(".quantidade").val(); 
		var nomeProduto = $row.find(".descProduto").text();
		var idproduto = $row.find(".idProduto").text();
		var clientecpf = $("#cpfcliente").text();/*este campo é o cpf que já esta carregado na view atual*/
		if(quantidade!=0){ //verificando a quantidade de campos add
			
			if(count < max_campos){
				count++;
				//realiznado a 
				$(wrapper).append(
					'<div class="form-group">'+
					'<input type="text" value="'+idproduto+'" name="idproduto"  class="conteudo" hidden>'+
					'<input type="text" value="'+nomeProduto+'" style="width : 50px">'+" "+
					'<input type="text" value="'+quantidade+'" name="quantidade" class="conteudo" style="width : 30px">'+
					'<input type="text" value="'+clientecpf+'" name="clientecpf"  class="conteudo" hidden>'+
					'<a href="#" class="remove_field">Remover</a>'+
					'</div>'
				);
			}
				
		}else{
			alert("A quantidade não pode ser 0!");//caso seja 0, isso tbm é verificado no html
		}
	});
	//botão de remover os campos add
	$(wrapper).on("click",".remove_field", function(e) {
		e.preventDefault();
		$(this).parent('div').remove();
		count--;
	});
});
	
/*requisição ajax para a controller*/	
$("#enviar").click(function(e) {
	e.preventDefault();
	var jsonObj = [];//var para montar o json para envio
	//varrendo as divs form-group geradas dinamicamente
	$(".form-group").each(function(index, elem) {
	console.log(index);
	console.log(elem);
	item = {}; //vetor apra receber os dados
	var idproduto;
	var quantidade;
	var clientecpf;
	var count = 0;
	//pegando os valores de cada filho das divs geradas que contém os dados de cpf,idproduto e quantidade
	$(elem).children(".conteudo").each(function (index, elem) {
		var nome = $(elem).attr('name')
		//verificações para mosntar um json a cada 3 inputs de dados
		if(nome == "idproduto"){
			idproduto = $(elem).attr('value');
			count++;
		}
		if (nome == "quantidade"){
			quantidade = $(elem).attr('value');
			count++;
		}
		if (nome == "clientecpf"){
			clientecpf = $(elem).attr('value');
			count++;
		}
		console.log(count);
		//ao terminar um obj json formado por 3 atributos referente ao dto ListaPedidoDto
		if(count ==3){
			item["idproduto"] = idproduto;
			item["quantidade"] = quantidade;
			item["clientecpf"] = clientecpf;
			jsonObj.push(item);
			count=0;
		}
	});
});
//Montando o JSON e realiznado o ajax	
console.log(jsonObj);
jsonObj = JSON.stringify({'listaitens' : jsonObj});
$.ajax({
	type: 'POST',
	url : '/salvarpedido',
	data : jsonObj,
	dataType : 'json',
	contentType: 'application/json; charset=utf-8',
	complete : function() {
		window.location.replace('/sucessopedido');
    },
    error: function() {
    	window.location.replace('');
    }
	});
});
	
	
	
	