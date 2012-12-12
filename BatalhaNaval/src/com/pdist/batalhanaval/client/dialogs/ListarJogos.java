package com.pdist.batalhanaval.client.dialogs;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import com.pdist.batalhanaval.server.mensagens.Mensagem;

public class ListarJogos extends JDialog {

	
	private static final long serialVersionUID = 4394138207569213899L;
	private final JPanel contentPanel = new JPanel();
	private JTextField nomeNovoJogo;
	private ArrayList<String> nomeJogos = null;

	
	@SuppressWarnings("rawtypes")
	public ListarJogos(Mensagem listajogos) {
		
		setResizable(false);  setModal(true);
		setTitle("Novo Jogo - Listar Jogos");
		setBounds(100, 100, 552, 313);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		//ListaJogo - Provisorio TODO
		
		nomeJogos = listajogos.getNomesJogos();		
		
		String[] listaJogos = { "", "", "", "", ""};		
		listaJogos[0] = nomeJogos.get(0);
		listaJogos[1] = nomeJogos.get(1);
		listaJogos[2] = nomeJogos.get(2);
		listaJogos[3] = nomeJogos.get(3);
		listaJogos[4] = nomeJogos.get(4);
		
		@SuppressWarnings("unchecked")
		JList lista = new JList(listaJogos);
		lista .setSelectedIndex(2); //selecionar o 3º
		
		lista .setFont(new Font("Tahoma", Font.PLAIN, 14));
		lista .setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lista .setBounds(49, 50, 181, 120);
		contentPanel.add(lista);	
		
		//TESTE
		
		
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEntrar.setBounds(88, 181, 106, 33);
		contentPanel.add(btnEntrar);
		
		JLabel lblListaDeJogos = new JLabel("Lista de Jogos:");
		lblListaDeJogos.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblListaDeJogos.setBounds(77, 13, 158, 26);
		contentPanel.add(lblListaDeJogos);
		
		JLabel lblNovoJogo = new JLabel("Novo Jogo:");
		lblNovoJogo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNovoJogo.setBounds(377, 13, 106, 26);
		contentPanel.add(lblNovoJogo);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(276, 0, 2, 285);
		contentPanel.add(separator);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(304, 98, 46, 23);
		contentPanel.add(lblNome);
		
		nomeNovoJogo = new JTextField();
		nomeNovoJogo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nomeNovoJogo.setBounds(351, 97, 148, 26);
		contentPanel.add(nomeNovoJogo);
		nomeNovoJogo.setColumns(10);
		
		JButton Criar = new JButton("Criar");
		Criar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Criar.setBounds(377, 181, 106, 33);
		contentPanel.add(Criar);
	}
}
