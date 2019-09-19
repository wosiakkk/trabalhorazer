/*Validação do cadastro de cliente utilizando JQueryValidate*/
$("#formProduto").validate({
		//definindo as regras
        rules: {
        	descricao: {
            	required: true,
            	maxlength: 50,
            	minlength: 3
            }
        },
        //defindo as mensagens
        messages: {
        	descricao:{
                required: "O campo descrição é obrigatório",
                maxlength: "O campo descrição tem no máximo 50 caracteres ",
                minlength: "O campo descrição tem no mínimo 3 caracteres"	
            }
        },
        //definndo o elemento para receber o conatiner de erros
        errorElement : 'div',
        errorLabelContainer: '.diverro'
});
