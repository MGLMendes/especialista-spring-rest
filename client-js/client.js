function consultar() {
    $.ajax({
        url: "http://localhost:8080/formas-pagamento",
        type: "get",
      
        success: function(response) {
          preencherTabela(response);
        }
      });
  }
  
  
  function preencherTabela(formasPagamento) {
    $("#tabela tbody tr").remove();
  
    $.each(formasPagamento, function(i, formaPagamento) {
      var linha = $("<tr>");
  
      linha.append(
        $("<td>").text(formaPagamento.id),
        $("<td>").text(formaPagamento.descricao)
      );
  
      linha.appendTo("#tabela");
    });
  }
  

function cadastrar() {

    var formaPagamento = JSON.stringify({
        "descricao": $("#campo-descricao").val()
    });

    console.log(formaPagamento)

    $.ajax({
        url: "http://localhost:8080/formas-pagamento",
        type: "post", 

        data: formaPagamento,
        contentType: "application/json",

        success: function() {
            alert("Forma Pagamento Cadastrada");
            consultar();

        },

        error: function(error) {
            if (error.status == 400) {
                var problem = JSON.parse(error.responseText)
                alert(problem.userMessage)

            } else {
                alert("Erro ao cadastrar forma de pagamento!")
            }

        }
    })
    
}

$("#btn-consultar").click(consultar);
  
$("#btn-cadastrar").click(cadastrar);