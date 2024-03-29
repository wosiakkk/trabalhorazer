/*Validação do cadastro de cliente utilizando JQueryValidate*/
$("#formCliente").validate({
		//definindo as regras
        rules: {
            cpf: {
            	required: true,
            	minlength: 14
            },
            nome: {
            	required: true,
            	maxlength: 50,
            	minlength: 5
            },
            sobrenome: {
            	required: true,
				maxlength: 50,
	            minlength: 5
			},
        },
        //defindo as mensagens
        messages: {
        	cpf:{
                required: "O campo CPF é obrigatório",
                minlength: "O campo CPF tem no mínimo 15 caracteres"	
            },
            nome:{
            	required: "O campo nome é obrigatório",
            	maxlength: "O campo nome tem no máximo 50 caracteres ",
            	minlength: "O campo nome tem no mínimo 5 caracteres"
            },
            sobrenome: {
            	required: "O campo sobrenome é obrigatório",
            	maxlength: "O campo sobrenome tem no máximo 50 caracteres ",
            	minlength: "O campo sobrenome tem no mínimo 5 caracteres"
            }
        },
        //definndo o elemento para receber o conatiner de erros
        errorElement : 'div',
        errorLabelContainer: '.diverro'
});
/*função criada para retirar a mensagem de sucesso que fica mostrando após 
 * haver erros de validação, misturando as mensagens, com isso é verificado se há mensagem de sucesso,
 * quando haver mensagens de erros geradas pelo validade,
 * se houver a div de sucesso é escondida*/
function verificarMensagem() {
	
	if(!($(".sucessoBack").is(':empty'))){
		$("#divSucesso").hide();
	}
};

/*Validação da busca de cpf para cadastro de pedido*/
$("#formBusca").validate({
	// definindo as regras
    rules: {
        cpf: {
        	required: true,
        	minlength: 14
        }
    },
    // defindo as mensagens
    messages: {
    	cpf:{
            required: "O campo CPF é obrigatório",
            minlength: "O campo CPF tem no mínimo 15 caracteres"	
        },
    },
    // definndo o elemento para receber o conatiner de erros
    errorElement : 'div',
    errorLabelContainer: '.diverro'
});