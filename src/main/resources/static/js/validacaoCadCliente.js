/*Validação do cadastro de cliente utilizando JQueryValidate*/
$("#formCliente").validate({
		//definindo as regras
        rules: {
            cpf: {
            	required: true,
            	minlength: 15
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
