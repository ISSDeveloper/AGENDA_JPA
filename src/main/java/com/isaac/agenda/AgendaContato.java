package com.isaac.agenda;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.isaac.agenda.business.ContatoBss;
import com.isaac.agenda.color.Ansi;
import com.isaac.agenda.domain.Contato;
import com.isaac.agenda.exception.BssException;

public class AgendaContato {

	private BufferedReader br;
	private ContatoBss contatoBss = new ContatoBss();

	public AgendaContato(BufferedReader br) {
		this.br = br;
	}

	public void menu() throws Exception {

		boolean voltar = false;

		while (!voltar) {

			byte opcao = getMenuOpcao();

			switch (opcao) {
			case 1 -> listar();
			case 2 -> adicionar();
			case 3 -> alterar();
			case 4 -> remover();
			case 5 -> voltar = true;
			}
		}
	}

	private Byte getMenuOpcao() throws IOException {

		System.out.println(Ansi.BG_VERDE + Ansi.BRANCO + "     MENU CONTATO     " + Ansi.RESET);
		System.out.println("---------------------");
		System.out.println("1 - Lista de contatos");
		System.out.println("2 - Adicionar contato");
		System.out.println("3 - Alterar contato");
		System.out.println("4 - Remover contato");
		System.out.println("5 - Voltar menu");
		System.out.println("---------------------");

		return getOpcao();
	}

	private Byte getOpcao() throws IOException {

		System.out.print(Ansi.VERDE + "Digite uma opção: " + Ansi.RESET);
		String op = br.readLine();

		if (!("1".equals(op) || "2".equals(op) || "3".equals(op) || "4".equals(op) || "5".equals(op))) {

			System.out.println(Ansi.VERMELHO + "Opção invalida!" + Ansi.RESET);
			System.out.println();

			return getOpcao();
		}

		System.out.println();
		return Byte.valueOf(op);
	}

	private void listar() throws BssException {

		System.out.println(Ansi.BG_VERDE + Ansi.BRANCO + "       CONTATOS       " + Ansi.RESET);
		System.out.println();

		List<Contato> lista = contatoBss.getList();

		System.out.printf("----------------------------------------------------------------------%n");
		System.out.printf("| %-8s | %-40s | %12s |%n", "CÓDIGO", "NOME", "TELEFONE");
		System.out.printf("----------------------------------------------------------------------%n");

		lista.forEach(
				c -> System.out.printf("| %-8s | %-40s | %12s |%n", c.getCodContato(), c.getNome(), c.getTelefone()));

		System.out.printf("----------------------------------------------------------------------%n");
		System.out.println();
	}

	private void adicionar() throws IOException, NumberFormatException, BssException {

		System.out.println(Ansi.BG_VERDE + Ansi.BRANCO + "  ADICIONAR CONTATO   " + Ansi.RESET);
		System.out.println();

		System.out.println(Ansi.AMARELO + "Para cancelar operação digite: * " + Ansi.RESET);

		String nome = getNome();

		if (nome == null)
			return;

		Long telefone = getTelefone();

		if (telefone == null)
			return;

		Contato contato = contatoBss.create(new Contato(nome, telefone));

		System.out.println(Ansi.VERDE + "Contato adicionado!" + Ansi.RESET);
		System.out.println();

		List<Contato> lista = contatoBss.getList();

		System.out.printf("----------------------------------------------------------------------%n");
		System.out.printf("| %-8s | %-40s | %12s |%n", "CÓDIGO", "NOME", "TELEFONE");
		System.out.printf("----------------------------------------------------------------------%n");

		for (Contato c : lista) {
			if (c.getCodContato() == contato.getCodContato()) {
				System.out.printf(Ansi.VERDE + "| %-8s | %-40s | %12s |" + Ansi.RESET + "%n", "+" + c.getCodContato(),
						c.getNome(), c.getTelefone());
			} else {
				System.out.printf("| %-8s | %-40s | %12s |%n", c.getCodContato(), c.getNome(), c.getTelefone());
			}
		}

		System.out.printf("----------------------------------------------------------------------%n");
		System.out.println();
	}

	public String getNome() throws IOException {

		return getNome(null);
	}

	public String getNome(String nomeAntigo) throws IOException {

		System.out.print(Objects.isNull(nomeAntigo) ? "Nome: " : "Nome [" + nomeAntigo + "]: ");
		String nome = br.readLine();

		if (nome.trim().isEmpty()) {
			return nomeAntigo;
		}

		if ("*".equals(nome)) {

			System.out.println(Ansi.AMARELO + "Operação cancelada!" + Ansi.RESET + "\n");
			return null;
		}

		if (nome.length() > 40) {

			System.out.println(Ansi.VERMELHO + "Limite de caracteres alcançado!" + Ansi.RESET);
			return getNome();
		}

		return nome;
	}

	public Long getTelefone() throws IOException {

		return getTelefone(null);
	}

	public Long getTelefone(Long telefoneAntigo) throws IOException {

		System.out.print(Objects.isNull(telefoneAntigo) ? "Telefone : " : "Telefone [" + telefoneAntigo + "]: ");
		String telefone = br.readLine();

		if (telefone.trim().isEmpty()) {
			return telefoneAntigo;
		}

		if ("*".equals(telefone)) {

			System.out.println(Ansi.AMARELO + "Operação cancelada!" + Ansi.RESET + "\n");
			return null;
		}

		if (!isTelefone(telefone)) {

			System.out.println(Ansi.VERMELHO + "Telefone invalido!" + Ansi.RESET);
			return getTelefone();
		}

		return Long.valueOf(telefone);
	}

	private boolean isTelefone(String telefone) {

		if (telefone.length() != 11)
			return false;

		try {
			Long.parseLong(telefone);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void alterar() throws BssException, NumberFormatException, IOException {

		System.out.println(Ansi.BG_VERDE + Ansi.BRANCO + "   ALTERAR CONTATO  " + Ansi.RESET);
		System.out.println();

		List<Contato> lista = contatoBss.getList();

		System.out.printf("----------------------------------------------------------------------%n");
		System.out.printf("| %-8s | %-40s | %12s |%n", "CÓDIGO", "NOME", "TELEFONE");
		System.out.printf("----------------------------------------------------------------------%n");

		lista.forEach(
				c -> System.out.printf("| %-8s | %-40s | %12s |%n", c.getCodContato(), c.getNome(), c.getTelefone()));

		System.out.printf("----------------------------------------------------------------------%n");
		System.out.println();

		System.out.println(Ansi.AMARELO + "Para cancelar operação digite: * " + Ansi.RESET);

		Long codContato = getCodigo(lista);

		if (codContato == null)
			return;

		System.out.println(Ansi.AMARELO + "Precione 'Enter' para continuar com os mesmos dados" + Ansi.RESET);

		Contato contato = contatoBss.getEntity(codContato);

		String nome = getNome(contato.getNome());

		if (nome == null)
			return;

		Long telefone = getTelefone(contato.getTelefone());

		if (telefone == null)
			return;

		contato.setNome(nome);
		contato.setTelefone(telefone);
		contatoBss.update(contato);

		System.out.println(Ansi.VERDE + "Contato alterado!" + Ansi.RESET);

		lista = contatoBss.getList();

		System.out.println();
		System.out.printf("----------------------------------------------------------------------%n");
		System.out.printf("| %-8s | %-40s | %12s |%n", "CÓDIGO", "NOME", "TELEFONE");
		System.out.printf("----------------------------------------------------------------------%n");

		for (Contato c : lista) {
			if (c.getCodContato() == codContato) {
				System.out.printf(Ansi.VERDE + "| %-8s | %-40s | %12s |" + Ansi.RESET + "%n", c.getCodContato(),
						c.getNome(), c.getTelefone());
			} else {
				System.out.printf("| %-8s | %-40s | %12s |%n", c.getCodContato(), c.getNome(), c.getTelefone());
			}
		}

		System.out.printf("----------------------------------------------------------------------%n");
		System.out.println();
	}

	public Long getCodigo(List<Contato> lista) throws IOException {

		return getCodigo(Ansi.VERDE, lista);
	}

	public Long getCodigo(String color, List<Contato> lista) throws IOException {

		System.out.print(color + "Digite o código: " + Ansi.RESET);
		String codContato = br.readLine();

		if ("*".equals(codContato)) {

			System.out.println(Ansi.AMARELO + "Operação cancelada!" + Ansi.RESET + "\n");
			return null;
		}

		try {
			Long cod = Long.parseLong(codContato);

			if (lista.stream().noneMatch(c -> c.getCodContato() == cod)) {
				System.out.println(Ansi.VERMELHO + "Código inexistente!" + Ansi.RESET);
				return getCodigo(color, lista);
			} else {
				return cod;
			}
		} catch (NumberFormatException e) {
			System.out.println(Ansi.VERMELHO + "Código invalido!" + Ansi.RESET);
			return getCodigo(lista);
		}
	}

	private void remover() throws BssException, IOException {

		System.out.println(Ansi.BG_VERMELHO + Ansi.BRANCO + "    REMOVER CONTATO  " + Ansi.RESET);
		System.out.println();

		List<Contato> lista = contatoBss.getList();

		System.out.printf("----------------------------------------------------------------------%n");
		System.out.printf("| %-8s | %-40s | %12s |%n", "CÓDIGO", "NOME", "TELEFONE");
		System.out.printf("----------------------------------------------------------------------%n");

		lista.forEach(
				c -> System.out.printf("| %-8s | %-40s | %12s |%n", c.getCodContato(), c.getNome(), c.getTelefone()));

		System.out.printf("----------------------------------------------------------------------%n");
		System.out.println();

		System.out.println(Ansi.AMARELO + "Para cancelar operação digite: * " + Ansi.RESET);

		Long codContato = getCodigo(Ansi.VERMELHO, lista);

		if (codContato == null)
			return;

		contatoBss.remove(codContato);

		System.out.println(Ansi.VERDE + "Contato removido!" + Ansi.RESET);
		System.out.println();
	}

}
