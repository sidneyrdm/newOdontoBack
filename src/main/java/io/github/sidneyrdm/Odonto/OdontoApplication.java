package io.github.sidneyrdm.Odonto;

import ch.qos.logback.core.net.server.Client;
import io.github.sidneyrdm.Odonto.model.Cliente;
import io.github.sidneyrdm.Odonto.repository.ClienteRepository;
import org.h2.command.Command;
import org.hibernate.query.criteria.internal.expression.function.CurrentDateFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.format.datetime.standard.DateTimeContext;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class OdontoApplication {

	@Bean
	public CommandLineRunner run(@Autowired ClienteRepository clienteRepository){
		return args -> {
			Cliente cliente = new Cliente();
			cliente.setCpf("12345678911");
			cliente.setNome("Jonatas Barboza");
			cliente.setTelefone("81983736968");

			clienteRepository.save(cliente);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(OdontoApplication.class, args);
	}

}
