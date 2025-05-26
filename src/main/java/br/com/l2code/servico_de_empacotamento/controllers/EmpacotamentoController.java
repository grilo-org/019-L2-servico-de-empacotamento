package br.com.l2code.servico_de_empacotamento.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.l2code.servico_de_empacotamento.models.dtos.ListaPedidosDTO;
import br.com.l2code.servico_de_empacotamento.models.dtos.ListaPedidosResponseDTO;
import br.com.l2code.servico_de_empacotamento.services.EmpacotamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/empacotamento")
@Tag(name = "Empacotamento", description = "API para processamento de empacotamento de pedidos")
public class EmpacotamentoController {

    private final EmpacotamentoService empacotamentoService;

    @Autowired
    public EmpacotamentoController(EmpacotamentoService empacotamentoService) {
        this.empacotamentoService = empacotamentoService;
    }

    @PostMapping("/processar")
    @Operation(summary = "Processar pedidos para empacotamento", description = "Recebe uma lista de pedidos e retorna a melhor forma de empacotá-los")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos processados com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListaPedidosResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ListaPedidosResponseDTO> processarPedidos(
            @Valid @RequestBody ListaPedidosDTO listaPedidos) {

        ListaPedidosResponseDTO response = empacotamentoService.processarPedidos(listaPedidos);
        return ResponseEntity.ok(response);
    }
}
