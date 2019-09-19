$(document).ready(function(){
		$('#tabelaProdutos').pageMe({
		  pagerSelector:'#paginacao',
		  activeColor: 'green',
		  prevText:'Anterior',
		  nextText:'Seguinte',
		  showPrevNext:true,
		  hidePageNumbers:false,
		  perPage:5
		});
	});