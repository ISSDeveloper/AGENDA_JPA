package com.isaac.agenda;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.isaac.agenda.business.ContatoBss;
import com.isaac.agenda.business.LigacaoBss;
import com.isaac.agenda.color.Ansi;
import com.isaac.agenda.domain.Contato;
import com.isaac.agenda.domain.Ligacao;
import com.isaac.agenda.domain.LigacaoId;
import com.isaac.agenda.exception.BssException;
import com.isaac.agenda.util.Funcoes;

public class AgendaLigacao {

	private BufferedReader br;
	private LigacaoBss ligacaoBss = new LigacaoBss();
	private ContatoBss contatoBss = new ContatoBss();

	public AgendaLigacao(BufferedReader br) {
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

		System.out.println(Ansi.BG_VERDE + Ansi.BRANCO + "     MENU LIGAÇÃO    " + Ansi.RESET);
		System.out.println("---------------------");
		System.out.println("1 - Lista de ligação");
		System.out.println("2 - Adicionar ligação");
		System.out.println("3 - Alterar ligação");
		System.out.println("4 - Remover ligação");
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

		System.out.println(Ansi.BG_VERDE + Ansi.BRANCO + "       LIGAÇÕES       " + Ansi.RESET);
		System.out.println();

		List<Ligacao> lista = ligacaoBss.getList();

		System.out.printf(
				"---------------------------------------------------------------------------------------------------------------------------%n");
		System.out.printf("| %-11s | %-25s | %-11s | %-40s | %20s |%n", "CÓD.CONTATO", "CONTATO", "CÓD.LIGAÇÃO",
				"OBSERVACAO", "DATA E HORA");
		System.out.printf(
				"---------------------------------------------------------------------------------------------------------------------------%n");

		for (Ligacao ligacao : lista) {

			String dataFormatada = Funcoes.dateToStr(ligacao.getDataHora());

			System.out.printf("| %-11s | %-25s | %-11s | %-40s | %20s |%n", ligacao.getId().getCodContato(),
					ligacao.getContato().getNome(), ligacao.getId().getCodLigacao(), ligacao.getObservacao(),
					dataFormatada);

		}

		System.out.printf(
				"---------------------------------------------------------------------------------------------------------------------------%n");
		System.out.println();
	}

	private void adicionar() throws Exception {

		System.out.println(Ansi.BG_VERDE + Ansi.BRANCO + "  ADICIONAR LIGAÇÃO   " + Ansi.RESET);
		System.out.println();

		System.out.printf("----------------------------------------------------------------------%n");
		System.out.printf("| %-8s | %-40s | %12s |%n", "CÓDIGO", "NOME", "TELEFONE");
		System.out.printf("----------------------------------------------------------------------%n");

		contatoBss.getList().forEach(
				c -> System.out.printf("| %-8s | %-40s | %12s |%n", c.getCodContato(), c.getNome(), c.getTelefone()));

		System.out.printf("----------------------------------------------------------------------%n");
		System.out.println();

		System.out.println(Ansi.AMARELO + "Para cancelar operação digite: * " + Ansi.RESET);

		Contato contato = getContato();

		if (contato == null)
			return;

		String obs = getObservacao();

		if (obs == null)
			return;

		Date dataHora = getDataHora();

		if (dataHora == null)
			return;

		Ligacao lig = ligacaoBss.create(new Ligacao(contato, obs, dataHora));

		System.out.println(Ansi.VERDE + "Contato adicionado!" + Ansi.RESET);
		System.out.println();

		List<Ligacao> lista = ligacaoBss.getList();

		System.out.printf(
				"---------------------------------------------------------------------------------------------------------------------------%n");
		System.out.printf("| %-11s | %-25s | %-11s | %-40s | %20s |%n", "CÓD.CONTATO", "CONTATO", "CÓD.LIGAÇÃO",
				"OBSERVACAO", "DATA E HORA");
		System.out.printf(
				"---------------------------------------------------------------------------------------------------------------------------%n");

		for (Ligacao ligacao : lista) {

			String dataFormatada = Funcoes.dateToStr(ligacao.getDataHora());

			if (ligacao.getId().equals(lig.getId())) {
				System.out.printf(Ansi.VERDE + "| %-11s | %-25s | %-11s | %-40s | %20s |%n" + Ansi.RESET,
						ligacao.getId().getCodContato(), ligacao.getContato().getNome(),
						"+" + ligacao.getId().getCodLigacao(), ligacao.getObservacao(), dataFormatada);
			} else {
				System.out.printf("| %-11s | %-25s | %-11s | %-40s | %20s |%n", ligacao.getId().getCodContato(),
						ligacao.getContato().getNome(), ligacao.getId().getCodLigacao(), ligacao.getObservacao(),
						dataFormatada);
			}

		}

		System.out.printf(
				"---------------------------------------------------------------------------------------------------------------------------%n");
		System.out.println();
	}

	private Date getDataHora() throws IOException {

		return getDataHora(null);
	}

	private Date getDataHora(Date dataHoraAntiga) throws IOException {

		System.out.print(Ansi.VERDE + "Digite a data e hora [dd/mm/yyyy hh:mm]: " + Ansi.RESET);
		String dataHora = br.readLine();

		if (dataHora.trim().isEmpty()) {
			if (Objects.nonNull(dataHoraAntiga))
				return dataHoraAntiga;
		}

		if ("*".equals(dataHora)) {

			System.out.println(Ansi.AMARELO + "Operação cancelada!" + Ansi.RESET + "\n");
			return null;
		}

		Date data = Funcoes.strToDate(dataHora);

		if (Objects.isNull(data)) {

			System.out.println(Ansi.VERMELHO + "Data e hora invalida!" + Ansi.RESET);
			return getDataHora();
		}

		return data;
	}

	private String getObservacao() throws IOException {
		return getObservacao(null);
	}

	private String getObservacao(String obsAntigo) throws IOException {

		System.out.print(Ansi.VERDE + "Digite a observação: " + Ansi.RESET);
		String obs = br.readLine();

		if (obs.trim().isEmpty()) {
			return obsAntigo;
		}

		if ("*".equals(obs)) {

			System.out.println(Ansi.AMARELO + "Operação cancelada!" + Ansi.RESET + "\n");
			return null;
		}

		if (obs.length() > 100) {

			System.out.println(Ansi.VERMELHO + "Limete de caracteres ultrapassado!" + Ansi.RESET + "\n");
			return getObservacao(obsAntigo);
		}

		return obs;

	}

	private void alterar() throws Exception {

		System.out.println(Ansi.BG_VERDE + Ansi.BRANCO + "   ALTERAR LIGAÇÃO  " + Ansi.RESET);
		System.out.println();

		List<Ligacao> lista = ligacaoBss.getList();

		System.out.printf(
				"---------------------------------------------------------------------------------------------------------------------------%n");
		System.out.printf("| %-11s | %-25s | %-11s | %-40s | %20s |%n", "CÓD.CONTATO", "CONTATO", "CÓD.LIGAÇÃO",
				"OBSERVACAO", "DATA E HORA");
		System.out.printf(
				"---------------------------------------------------------------------------------------------------------------------------%n");

		for (Ligacao ligacao : lista) {

			String dataFormatada = Funcoes.dateToStr(ligacao.getDataHora());

			System.out.printf("| %-11s | %-25s | %-11s | %-40s | %20s |%n", ligacao.getId().getCodContato(),
					ligacao.getContato().getNome(), ligacao.getId().getCodLigacao(), ligacao.getObservacao(),
					dataFormatada);

		}

		System.out.printf(
				"---------------------------------------------------------------------------------------------------------------------------%n");
		System.out.println();

		System.out.println(Ansi.AMARELO + "Para cancelar operação digite: * " + Ansi.RESET);

		Long codContato = getCodCodigo(lista);

		if (codContato == null)
			return;

		Long codLigacao = getCodLigacao(codContato, lista);

		if (codLigacao == null)
			return;

		System.out.println(Ansi.AMARELO + "Precione 'Enter' para continuar com os mesmos dados" + Ansi.RESET);

		Ligacao lig = ligacaoBss.getEntity(new LigacaoId(codLigacao, codContato));

		String obs = getObservacao(lig.getObservacao());

		if (obs == null)
			return;

		Date dataHora = getDataHora(lig.getDataHora());

		if (dataHora == null)
			return;

		lig.setDataHora(dataHora);
		lig.setObservacao(obs);

		ligacaoBss.update(lig);

		System.out.println(Ansi.VERDE + "Ligação alterada!" + Ansi.RESET);

		lista = ligacaoBss.getList();

		System.out.printf(
				"---------------------------------------------------------------------------------------------------------------------------%n");
		System.out.printf("| %-11s | %-25s | %-11s | %-40s | %20s |%n", "CÓD.CONTATO", "CONTATO", "CÓD.LIGAÇÃO",
				"OBSERVACAO", "DATA E HORA");
		System.out.printf(
				"---------------------------------------------------------------------------------------------------------------------------%n");

		for (Ligacao ligacao : lista) {

			String dataFormatada = Funcoes.dateToStr(ligacao.getDataHora());

			if (ligacao.getId().equals(lig.getId())) {
				System.out.printf(Ansi.VERDE + "| %-11s | %-25s | %-11s | %-40s | %20s |%n" + Ansi.RESET,
						ligacao.getId().getCodContato(), ligacao.getContato().getNome(),
						ligacao.getId().getCodLigacao(), ligacao.getObservacao(), dataFormatada);
			} else {
				System.out.printf("| %-11s | %-25s | %-11s | %-40s | %20s |%n", ligacao.getId().getCodContato(),
						ligacao.getContato().getNome(), ligacao.getId().getCodLigacao(), ligacao.getObservacao(),
						dataFormatada);
			}

		}

		System.out.printf(
				"---------------------------------------------------------------------------------------------------------------------------%n");
		System.out.println();

	}

	private void remover() throws Exception {

		System.out.println(Ansi.BG_VERMELHO + Ansi.BRANCO + "    REMOVER LIGAÇÃO  " + Ansi.RESET);
		System.out.println();

		List<Ligacao> lista = ligacaoBss.getList();

		System.out.printf(
				"-------------------------------------------------------------------------------------------------------------------------%n");
		System.out.printf("| %-11s | %-25s | %-11s | %-40s | %20s |%n", "CÓD.CONTATO", "CONTATO", "CÓD.LIGAÇÃO",
				"OBSERVACAO", "DATA E HORA");
		System.out.printf(
				"-------------------------------------------------------------------------------------------------------------------------%n");

		for (Ligacao ligacao : lista) {

			String dataFormatada = Funcoes.dateToStr(ligacao.getDataHora());

			System.out.printf("| %-11s | %-25s | %-11s | %-40s | %20s |%n", ligacao.getId().getCodContato(),
					ligacao.getContato().getNome(), ligacao.getId().getCodLigacao(), ligacao.getObservacao(),
					dataFormatada);

		}

		System.out.printf(
				"-------------------------------------------------------------------------------------------------------------------------%n");
		System.out.println();

		System.out.println(Ansi.AMARELO + "Para cancelar operação digite: * " + Ansi.RESET);

		Long codContato = getCodCodigo(Ansi.VERMELHO, lista);

		if (codContato == null)
			return;

		Long codLigacao = getCodLigacao(Ansi.VERMELHO, codContato, lista);

		if (codLigacao == null)
			return;

		ligacaoBss.remove(new LigacaoId(codLigacao, codContato));
		System.out.print(Ansi.VERDE + "Ligação removida! " + Ansi.RESET);
		System.out.println("\n");
	}

	public Long getCodCodigo(List<Ligacao> lista) throws Exception {

		return getCodCodigo(Ansi.VERDE, lista);
	}

	public Long getCodCodigo(String color, List<Ligacao> lista) throws Exception {

		System.out.print(color + "Digite o código do contato: " + Ansi.RESET);
		String codContato = br.readLine();

		if ("*".equals(codContato)) {

			System.out.println(Ansi.AMARELO + "Operação cancelada!" + Ansi.RESET + "\n");
			return null;
		}

		try {
			Long cod = Long.parseLong(codContato);

			if (lista.stream().noneMatch(c -> c.getId().getCodContato() == cod)) {
				System.out.println(Ansi.VERMELHO + "Código do contato inexistente!" + Ansi.RESET);
				return getCodCodigo(color, lista);
			} else {
				return cod;
			}
		} catch (NumberFormatException e) {
			System.out.println(Ansi.VERMELHO + "Código invalido!" + Ansi.RESET);
			return getCodCodigo(lista);
		}
	}

	public Contato getContato() throws Exception {

		System.out.print(Ansi.VERDE + "Digite o código do contato: " + Ansi.RESET);
		String codContato = br.readLine();

		if ("*".equals(codContato)) {

			System.out.println(Ansi.AMARELO + "Operação cancelada!" + Ansi.RESET + "\n");
			return null;
		}

		try {
			Long cod = Long.parseLong(codContato);

			Contato contato = contatoBss.getEntity(cod);

			if (Objects.isNull(contato)) {
				System.out.println(Ansi.VERMELHO + "Código do contato inexistente!" + Ansi.RESET);
				return getContato();
			} else {
				return contato;
			}
		} catch (NumberFormatException e) {
			System.out.println(Ansi.VERMELHO + "Código invalido!" + Ansi.RESET);
			return getContato();
		}
	}

	public Long getCodLigacao(Long codContato, List<Ligacao> lista) throws IOException {

		return getCodLigacao(Ansi.VERDE, codContato, lista);
	}

	public Long getCodLigacao(String color, Long codContato, List<Ligacao> lista) throws IOException {

		System.out.print(color + "Digite o código da ligação: " + Ansi.RESET);
		String codLigacao = br.readLine();

		if ("*".equals(codLigacao)) {

			System.out.println(Ansi.AMARELO + "Operação cancelada!" + Ansi.RESET + "\n");
			return null;
		}

		try {
			Long cod = Long.parseLong(codLigacao);

			if (lista.stream()
					.noneMatch(c -> c.getId().getCodLigacao() == cod && c.getId().getCodContato() == codContato)) {
				System.out.println(Ansi.VERMELHO + "Código da ligação inexistente!" + Ansi.RESET);
				return getCodLigacao(color, codContato, lista);
			} else {
				return cod;
			}
		} catch (NumberFormatException e) {
			System.out.println(Ansi.VERMELHO + "Código invalido!" + Ansi.RESET);
			return getCodLigacao(color, codContato, lista);
		}
	}
}
