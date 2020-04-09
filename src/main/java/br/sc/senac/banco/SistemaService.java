package br.sc.senac.banco;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sistema")

public class SistemaService {

	public static ArrayList<Conta> contas = new ArrayList<Conta>();

	@PostMapping("/add-default")
	public void addDefault() {

		Conta conta = new Conta("Titular 1", 0, 500);
		contas.add(0, conta);

		conta = new Conta("Titular 2", 1, 500);
		contas.add(1, conta);
	}
	
	@PostMapping("/cadastrarcliente/{titular}")
	public Conta cadastrarCliente(@PathVariable String titular) {
		Conta conta = new Conta(titular, contas.size(), 500);
		contas.add(conta);
		return conta;
	}

	@GetMapping("/depositar/{numeroConta}/{valorDeposito}")
	public double efetuarDeposito(@PathVariable int numeroConta, @PathVariable double valorDeposito) {
		contas.get(numeroConta).depositar(valorDeposito);
		return valorDeposito;
	}

	@GetMapping("/transferir/{numeroContaOrigem}/{numeroContaDestino}/{valor}")
	public double efetuarTransferencia(@PathVariable int numeroContaOrigem, @PathVariable int numeroContaDestino,
			@PathVariable double valor) {

		Conta ContaDestino = contas.get(numeroContaDestino);
		contas.get(numeroContaOrigem).transferirPara(ContaDestino, valor);
		return valor;
	}

	@GetMapping("/sacar/{numeroConta}/{valorSaque}")
	public double efetuarSaque(@PathVariable int numeroConta, @PathVariable double valorSaque) {
		Conta conta = contas.get(numeroConta);
		if (!conta.sacar(valorSaque)) {
			return 0;

		}
		return valorSaque;

	}

	@GetMapping("/extrato/{numeroconta}")
	public Conta imprimirExtrato(@PathVariable int numeroconta) {
		if (numeroconta <= -1 || numeroconta > contas.size()) {
			Conta conta = new Conta("", 0, 0);
			return conta;
		}
		Conta conta = contas.get(numeroconta);
		return conta;
	}

}
