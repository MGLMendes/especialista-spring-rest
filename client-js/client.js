function consultarRestaurantes() {

    $.ajax({
        url: "http://localhost:8080/cozinhas",
        type: "get",

        success: function(response) {
            $("#conteudo").text(JSON.stringify(response))
        }
    });
}



function fecharRestaurante() {
    $.ajax({
        url: "http://localhost:8080/restaurantes/1/fechar",
        type: "put",

        success: function(response) {
            alert("Restaurante foi fechado!");
        }
    });
}

$("#consulta").click(consultarRestaurantes);
$("#fecha").click(fecharRestaurante);