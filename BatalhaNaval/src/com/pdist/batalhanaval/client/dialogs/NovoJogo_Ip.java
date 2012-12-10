package com.pdist.batalhanaval.client.dialogs;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.pdist.batalhanaval.client.main.LoginServidor_IP;
import com.pdist.batalhanaval.client.main.VarsGlobais;

import java.io.IOException;

public class NovoJogo_Ip extends JDialog {
	
	private static final long serialVersionUID = 1578538003101261327L;
	private final JPanel contentPanel = new JPanel();
	private JTextField inputIP;
	private JTextField inputNome;
	private JTextField inputPorto;

	public NovoJogo_Ip() {
				
		setResizable(false);  setModal(true);
		setTitle("Batalha Naval - Novo Jogo");
		setBounds(100, 100, 378, 241);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		//Adiciona texto a pedir IP e TextField respectivo
		
		inputIP = new JTextField();
		inputIP.setText("127.0.0.1");
		inputIP.setBounds(136, 42, 94, 20);
		contentPanel.add(inputIP);
		inputIP.setColumns(10);
		
		JLabel lblIp = new JLabel("IP Servidor:");
		lblIp.setFont(new Font("Arial", Font.BOLD, 12));
		lblIp.setBounds(40, 45, 94, 14);
		contentPanel.add(lblIp);
		
		
		//Adiciona texto a pedir o NOME e TextField respectivo
		
		inputNome = new JTextField();
		inputNome.setBounds(136, 84, 158, 20);
		contentPanel.add(inputNome);
		inputNome.setColumns(10);
		
		JLabel lblUsername = new JLabel("Nome:");
		lblUsername.setFont(new Font("Arial", Font.BOLD, 12));
		lblUsername.setBounds(68, 87, 70, 14);
		contentPanel.add(lblUsername);
		
		JLabel lblPorta = new JLabel("Porto:");
		lblPorta.setFont(new Font("Arial", Font.BOLD, 12));
		lblPorta.setBounds(240, 27, 34, 14);
		contentPanel.add(lblPorta);
		
		inputPorto = new JTextField();
		inputPorto.setText("5001");
		inputPorto.setColumns(10);
		inputPorto.setBounds(240, 42, 57, 20);
		contentPanel.add(inputPorto);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
				//---Eventos-OK--
				okButton.addActionListener(new ActionListener() {
					   public void actionPerformed(ActionEvent evt) {	
						   
						   JOptionPane.showMessageDialog(contentPanel,
								    "IP: "+inputIP.getText()+"Port: "+inputPorto.getText()+"\nNome: "+inputNome.getText());
						   
						   try {
							   
							Thread t = new Thread(new LoginServidor_IP(inputIP.getText(),inputNome.getText(),inputPorto.getText() ) );
							t.setDaemon(true);
							t.start();
							VarsGlobais.NovoJogoThreadCreated = true;
							dispose();
							
						   } catch (IOException e) {
							// TRATAR
							e.printStackTrace();
						}
						  
					   }
				});
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				
				//---Eventos-CANCEL--
				cancelButton.addActionListener(new ActionListener() {
					   public void actionPerformed(ActionEvent evt) {						   
						   dispose();
					   }
				});
			}
		}	
		   
		   
	}
}
