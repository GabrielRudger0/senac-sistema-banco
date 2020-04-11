package br.sc.senac.banco;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		contas.put(numeroConta, conta);
		return conta;
	}

	@GetMapping("/depositar/{numeroConta}/{valorDeposito}")
	public ResponseEntity<Conta> efetuarDeposito(@PathVariable String numeroConta, @PathVariable double valorDeposito) {
		if (!contas.containsKey(numeroConta)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Conta conta = contas.get(numeroConta);
		conta.depositar(valorDeposito);
		return new ResponseEntity<>(conta, HttpStatus.OK);
	}

	@GetMapping("/transferir/{numeroContaOrigem}/{numeroContaDestino}/{valor}")
	public ResponseEntity<Conta> efetuarTransferencia(@PathVariable String numeroContaOrigem, @PathVariable String numeroContaDestino,
			@PathVariable double valor) {
		if (!contas.containsKey(numeroContaOrigem) || !contas.containsKey(numeroContaDestino)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Conta contaDestino = contas.get(numeroContaDestino);
		contas.get(numeroContaOrigem).transferirPara(contaDestino, valor);
		return new ResponseEntity<>(contaDestino, HttpStatus.OK);
	}

	@GetMapping("/sacar/{numeroConta}/{valorSaque}")
	public ResponseEntity<Conta> efetuarSaque(@PathVariable String numeroConta, @PathVariable double valorSaque) {
		if (!contas.containsKey(numeroConta)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Conta conta = contas.get(numeroConta);
		conta.sacar(valorSaque);
		return new ResponseEntity<>(conta, HttpStatus.OK);
	}

	@GetMapping("/extrato/{numeroConta}")
	public Conta imprimirExtrato(@PathVariable String numeroConta) {
		if (contas.containsKey(numeroConta)) {
			Conta conta = contas.get(numeroConta);
			return conta;
		}
		Conta conta = new Conta("Não Encontrado", "Não Encontrado", 0);
		return conta;
	}

}
