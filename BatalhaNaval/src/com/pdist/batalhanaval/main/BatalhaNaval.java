package com.pdist.batalhanaval.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

import com.pdist.batalhanaval.dialogs.NovoJogo_Dialog;

public class BatalhaNaval {

	private JFrame frmBatalhaNavalV;
	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BatalhaNaval window = new BatalhaNaval();
					window.frmBatalhaNavalV.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BatalhaNaval() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBatalhaNavalV = new JFrame();
		frmBatalhaNavalV.setTitle("Batalha Naval v0.1");
		frmBatalhaNavalV.getContentPane().setBackground(Color.GRAY);
		frmBatalhaNavalV.setBounds(100, 100, 450, 300);
		frmBatalhaNavalV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBatalhaNavalV.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial", Font.PLAIN, 12));
		menuBar.setBounds(0, 0, 434, 21);
		frmBatalhaNavalV.getContentPane().add(menuBar);
		
		JMenu mnJogo = new JMenu("Jogo");
		menuBar.add(mnJogo);
		
		JMenuItem mntmNovoJogo = new JMenuItem("Novo Jogo");
		mntmNovoJogo.setAction(action);
		mnJogo.add(mntmNovoJogo);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mnJogo.add(mntmSair);
		
		JMenu mnOpes = new JMenu("Op\u00E7\u00F5es");
		menuBar.add(mnOpes);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Novo Jogo");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(frmBatalhaNavalV,
				    "Ainda n�o implementado!");
			try {
				NovoJogo_Dialog dialog = new NovoJogo_Dialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}
	}
}
