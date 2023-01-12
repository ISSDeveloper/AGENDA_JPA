package com.isaac.agenda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.isaac.agenda.business.ContatoBss;
import com.isaac.agenda.business.LigacaoBss;
import com.isaac.agenda.color.Ansi;

public class Agenda {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	private static ContatoBss contatoBss = new ContatoBss();
	private static LigacaoBss ligacaoBss = new LigacaoBss();

	public static void main(String[] args) {
		try {

			AgendaContato agendaContato = new AgendaContato(br, contatoBss);
			AgendaLigacao agendaLigacao = new AgendaLigacao(br, contatoBss, ligacaoBss);

			System.out.printf(Ansi.BG_AZUL + Ansi.BRANCO + "       -AGENDA-       " + Ansi.RESET + "%n");

			while (true) {

				byte opcao = getMenuOpcao();

				switch (opcao) {
				case 1 -> agendaContato.menu();
				case 2 -> agendaLigacao.menu();
				case 3 -> {System.out.println("adeus..."); System.exit(0);}
				}
			}
		} catch (Exception e) {
			System.out.print(Ansi.VERMELHO + "Ocorreu algum erro: " + Ansi.RESET);
			e.printStackTrace();
		}
	}

	private static Byte getMenuOpcao() throws IOException {

		System.out.println(Ansi.BG_VERDE + Ansi.BRANCO + "         MENU         " + Ansi.RESET);
		System.out.println("---------------------");
		System.out.println("1 - Menu contato");
		System.out.println("2 - Menu ligação");
		System.out.println("3 - Sair");
		System.out.println("---------------------");

		return getOpcao();
	}

	private static Byte getOpcao() throws IOException {

		System.out.print(Ansi.VERDE + "Digite uma opção: " + Ansi.RESET);
		String op = br.readLine();

		if (!("1".equals(op) || "2".equals(op) || "3".equals(op))) {

			System.out.println(Ansi.VERMELHO + "Opção invalida!" + Ansi.RESET);
			System.out.println();

			return getOpcao();
		}

		System.out.println();
		return Byte.valueOf(op);
	}

}