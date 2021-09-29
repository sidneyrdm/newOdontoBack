package io.github.sidneyrdm.Odonto.controller;

import io.github.sidneyrdm.Odonto.controller.dto.ServicoDTO;
import io.github.sidneyrdm.Odonto.model.Cliente;
import io.github.sidneyrdm.Odonto.model.Servico;
import io.github.sidneyrdm.Odonto.repository.ClienteRepository;
import io.github.sidneyrdm.Odonto.repository.ServicoRepository;
import io.github.sidneyrdm.Odonto.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicos")
@CrossOrigin("http://localhost:4200")
public class ServicoController {

    private final ServicoRepository servicoRepository;
    private final ClienteRepository clienteRepository;
    private final BigDecimalConverter bigDecimalConverter;

    @Autowired
    public ServicoController(ServicoRepository servicoRepository,
                             ClienteRepository clienteRepository,
                             BigDecimalConverter bigDecimalConverter)
    {
        this.clienteRepository = clienteRepository;
        this.servicoRepository = servicoRepository;
        this.bigDecimalConverter = bigDecimalConverter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Servico salvar(@RequestBody ServicoDTO servicoDTO){
        Servico servico = new Servico();
        LocalDate data = LocalDate.parse(servicoDTO.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Cliente cliente = this.clienteRepository.findById(servicoDTO.getIdCliente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Cliente inexistente"));

        servico.setDescricao(servicoDTO.getDescricao());
        servico.setValor( this.bigDecimalConverter.converter(servicoDTO.getPreco()));
        servico.setCliente(cliente);
        servico.setDataCadastro(data);

        return servicoRepository.save(servico);
    }

    @GetMapping
    public List<Servico> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "mes", required = false) Integer mes)
    {
        return servicoRepository.findByNameAndMes("%"+nome+"%", mes);
    }

    @GetMapping("/servicos-geral")
    public List<Servico> list(){
        return servicoRepository.findAll();
    }

    @GetMapping("{id}")
    public Servico getByID(@PathVariable Integer id){
        return servicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        servicoRepository.findById(id)
                .map(servico -> {
                    servicoRepository.delete(servico);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @Valid @RequestBody Servico servicoAtualizado){

        servicoRepository.findById(id)
                .map(servico -> {
                    servico.setCliente(servicoAtualizado.getCliente());
                    servico.setDataCadastro(servicoAtualizado.getDataCadastro());
                    servico.setDescricao(servicoAtualizado.getDescricao());
                    servico.setValor(servicoAtualizado.getValor());
                    return servicoRepository.save(servico);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

}

