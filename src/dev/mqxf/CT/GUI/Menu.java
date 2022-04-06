package dev.mqxf.CT.GUI;

import dev.mqxf.CT.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu extends JFrame {

	private JButton compile;
	private JTextArea classes;
	private JTextArea compileText;
	private JTextArea output;
	private JLabel classIdentifier;
	private JLabel instructions;
	private JPanel topPanel;
	private JPanel interPanel;
	private JPanel mainPanel;
	private JPanel totalPanel;
	private JScrollPane out;
	private JScrollPane in;

	public Menu() {
		initiate();
	}

	private void compile() {
		String out = Main.init(compileText.getText(), classes.getText());
		output.setText(out);
	}

	private void exit() {
		dispose();
		System.exit(0);
	}

	private void initiate() {
		setTitle("Century Tech Tracker/Analyzer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMaximumSize(new Dimension(1920, 1080));
		setMinimumSize(new Dimension(1280, 820));
		setPreferredSize(new Dimension(1440, 1000));
		Dimension d1 = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((d1.width - (getSize()).width) / 2, (d1.height - (getSize()).height) / 2);

		topPanel = new JPanel();
		topPanel.setMaximumSize(new Dimension(1920, 50));
		topPanel.setMinimumSize(new Dimension(1280, 50));
		topPanel.setPreferredSize(new Dimension(1440, 50));
		topPanel.setOpaque(false);

		interPanel = new JPanel();
		interPanel.setMaximumSize(new Dimension(1920, 50));
		interPanel.setMinimumSize(new Dimension(1280, 50));
		interPanel.setPreferredSize(new Dimension(1440, 50));
		interPanel.setOpaque(false);

		totalPanel = new JPanel();
		totalPanel.setMaximumSize(new Dimension(1920, 1080));
		totalPanel.setMinimumSize(new Dimension(1280, 1080));
		totalPanel.setPreferredSize(new Dimension(1440, 980));
		totalPanel.setOpaque(false);

		mainPanel = new JPanel();
		mainPanel.setMaximumSize(new Dimension(1920, 980));
		mainPanel.setMinimumSize(new Dimension(1280, 980));
		mainPanel.setPreferredSize(new Dimension(1440, 880));
		mainPanel.setOpaque(false);

		classes = new JTextArea(1, 20);
		classes.setVisible(true);

		classIdentifier = new JLabel();
		classIdentifier.setText("Class name (don't use spaces):");
		classIdentifier.setVisible(true);

		interPanel.add(classIdentifier);
		interPanel.add(classes);

		instructions = new JLabel();
		instructions.setText("Copy data directly from century tech and paste into the left box. Don't remove new lines. Remove any names other than first and last.");
		instructions.setSize(400, 150);
		instructions.setVisible(true);
		topPanel.add(instructions);

		compileText = new JTextArea(35, 25);
		compileText.setSize(800, 400);
		compileText.setVisible(true);
		in = new JScrollPane(compileText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(in);

		compile = new JButton();
		compile.setText("Calculate & Save");
		compile.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				compile();
			}
		});
		compile.setSize(100, 50);
		compile.setVisible(true);
		mainPanel.add(compile);

		output = new JTextArea(35, 55);
		output.setEditable(false);
		output.setBackground(null);
		output.setBorder(null);
		output.setVisible(true);
		output.setText("The stats will be displayed here.");
		output.setSize(800, 400);

		out = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(out);

		totalPanel.add(interPanel);
		interPanel.setBounds(0, 0, 1920, 50);
		totalPanel.add(topPanel);
		topPanel.setBounds(0, 50, 1920, 50);
		totalPanel.add(mainPanel);
		mainPanel.setBounds(0, 100, 1920, 980);
		add(totalPanel);
	}

}
