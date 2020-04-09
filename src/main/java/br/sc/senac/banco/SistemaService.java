package br.sc.senac.banco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sistema")

public class SistemaService {
	
	public static Map<String, Conta> contas = new HashMap<String, Conta>();
	
	@PostMapping("/cadastrarcliente/{titular}/{numeroConta}")
	public Conta cadastrarCliente(@PathVariable String titular, @PathVariable String numeroConta) {
		Conta conta = new Conta(titular, numeroConta, 500);
		contas.replace(numeroConta, conta);
		return conta;
	}

	@GetMapping("/depositar/{numeroConta}/{valorDeposito}")
	public double efetuarDeposito(@PathVariable String numeroConta, @PathVariable double valorDeposito) {
		Conta conta = contas.get(numeroConta);
		conta.depositar(valorDeposito);
		return valorDeposito;
	}

	@GetMapping("/transferir/{numeroContaOrigem}/{numeroContaDestino}/{valor}")
	public double efetuarTransferencia(@PathVariable String numeroContaOrigem, @PathVariable String numeroContaDestino,
			@PathVariable double valor) {

		Conta ContaDestino = contas.get(numeroContaDestino);
		contas.get(numeroContaOrigem).transferirPara(ContaDestino, valor);
		return valor;
	}

	@GetMapping("/sacar/{numeroConta}/{valorSaque}")
	public double efetuarSaque(@PathVariable String numeroConta, @PathVariable double valorSaque) {
		Conta conta = contas.get(numeroConta);
		if (!conta.sacar(valorSaque)) {
			return 0;

		}
		return valorSaque;

	}

	@GetMapping("/extrato/{numeroConta}")
	public Conta imprimirExtrato(@PathVariable String numeroConta) {
		if (!contas.containsKey(numeroConta)) {
			Conta conta = new Conta("", "", 0);
			return conta;
		}
		Conta conta = contas.get(numeroConta);
		return conta;
	}

}
