package com.pdist.batalhanaval.client.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import com.pdist.batalhanaval.client.main.LoginServidor;
import com.pdist.batalhanaval.client.main.VarsGlobais;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class NovoJogo extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1578538003101261327L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;

	public NovoJogo() {
		
		
		setResizable(false);
		setModal(true);
		setTitle("Batalha Naval - Novo Jogo");
		setBounds(100, 100, 378, 241);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textField = new JTextField();
		textField.setText("127.0.0.1");
		textField.setBounds(136, 42, 105, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblIp = new JLabel("IP Servidor:");
		lblIp.setFont(new Font("Arial", Font.BOLD, 12));
		lblIp.setBounds(40, 45, 94, 14);
		contentPanel.add(lblIp);
		
		textField_1 = new JTextField();
		textField_1.setBounds(136, 84, 158, 20);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblUsername = new JLabel("Nome:");
		lblUsername.setFont(new Font("Arial", Font.BOLD, 12));
		lblUsername.setBounds(68, 87, 70, 14);
		contentPanel.add(lblUsername);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
				okButton.addActionListener(new ActionListener() {
					   public void actionPerformed(ActionEvent evt) {						   
						   JOptionPane.showMessageDialog(contentPanel,
								    "IP:"+textField.getText()+"\nNome:"+textField_1.getText());
						   
						   try {
							Thread t = new Thread(new LoginServidor(textField.getText(),textField_1.getText()) );
							t.setDaemon(true);
							t.start();
							VarsGlobais.NovoJogoThreadCreated = true;
							dispose();
						   } catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						  
					   }
				});
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				
				cancelButton.addActionListener(new ActionListener() {
					   public void actionPerformed(ActionEvent evt) {						   
						   dispose();
					   }
				});
			}
		}	
		   
		   
	}
	

	
	
}
