package br.sc.senac.banco;

public class Conta {

	private String titular;
	private double saldo;
	private int numeroConta;

	public Conta(String titular, int numeroConta, double saldo) {
		this.titular = titular;
		this.numeroConta = numeroConta;
		this.saldo = saldo;
	}

	public int getNumeroConta() {
		return numeroConta;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public double getSaldo() {
		return saldo;
	}

	@Override
	public String toString() {
		return "Numero da conta: " + this.numeroConta + "\nTitular: " + this.titular + "\nSaldo: " + this.saldo;
	}

	public void depositar(double valorDeposito) {
		this.saldo += valorDeposito;
	}

	public boolean sacar(double valorSaque) {
		if (valorSaque > this.saldo) {
			return false;
		}
		this.saldo -= valorSaque;
		return true;
	}
	
	public boolean transferirPara(Conta contaDestino, double valor) {
		boolean isPodeTransferir = this.sacar(valor);
		if (isPodeTransferir) {
			contaDestino.depositar(valor);
		}
		return isPodeTransferir;
	}

}
