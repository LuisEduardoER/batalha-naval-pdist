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
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class ListarJogos extends JDialog {

	
	private static final long serialVersionUID = 4394138207569213899L;
	private final JPanel contentPanel = new JPanel();
	private JTextField nomeNovoJogo;

	
	public ListarJogos() {
		
		setResizable(false);  setModal(true);
		setTitle("Novo Jogo - Listar Jogos");
		setBounds(100, 100, 552, 313);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JList<String> listaJogos = new JList<String>(); // STRING???
		listaJogos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		listaJogos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listaJogos.setBounds(49, 50, 181, 120);
		contentPanel.add(listaJogos);
		
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
