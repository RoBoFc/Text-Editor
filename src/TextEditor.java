import javax.swing.*;

import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextEditor extends JFrame implements ActionListener
{
	JTextArea ta;
	JScrollPane sp;
	JSpinner fontSizeSpinner;
	JLabel fontLabel;
	JButton changeColor;
	JComboBox fontBox;
	
	JMenuBar mb;
	JMenu file;
	JMenuItem save;
	JMenuItem open;
	JMenuItem exit;
	
	TextEditor()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Text Editor");
		this.setResizable(false);
		this.setSize(700,700);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		
		ta = new JTextArea();
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setFont(new Font("Arial",Font.PLAIN,20));
		
		sp = new JScrollPane(ta);
		sp.setPreferredSize(new Dimension(680,620));
		//sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		//------------------------MENU BAR---------------------
		
		mb = new JMenuBar();
		
		file = new JMenu("File");
		file.setFont(new Font(null,Font.PLAIN,15));
		
		open = new JMenuItem("Open");
		save = new JMenuItem("Save");
		exit = new JMenuItem("Exit");
		
		open.addActionListener(this);
		save.addActionListener(this);
		exit.addActionListener(this);
		
		file.add(save);
		file.add(open);
		file.add(exit);
		
		mb.add(file);
		mb.setBorder(BorderFactory.createLineBorder(Color.black,1));
		mb.setForeground(Color.black);
		mb.setBackground(new Color(200,200,255));
		
		//------------------------MENU BAR---------------------
		
		
		fontSizeSpinner = new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50,25));
		fontSizeSpinner.setValue(20);
		fontSizeSpinner.setFocusable(false);
		fontSizeSpinner.setForeground(Color.black);
		fontSizeSpinner.setBackground(new Color(200,200,255));
		fontSizeSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e)
			{
				ta.setFont(new Font(ta.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));
			}
			
		});
		
		fontLabel = new JLabel("Font");
		fontLabel.setPreferredSize(new Dimension(40,25));
		fontLabel.setFont(new Font(null,Font.PLAIN,15));
		fontLabel.setForeground(Color.black);
		
		changeColor = new JButton("Change Color");
		changeColor.setPreferredSize(new Dimension(115,30));
		changeColor.setFocusable(false);
		changeColor.setBackground(new Color(200,200,255));
		changeColor.setForeground(Color.black);
		changeColor.addActionListener((ActionListener) this);
		
		String[] font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
				
		fontBox = new JComboBox(font);
		fontBox.setPreferredSize(new Dimension(230,30));
		fontBox.setForeground(Color.black);
		fontBox.setBackground(new Color(200,200,255));
		fontBox.setSelectedItem("Arial");
		fontBox.addActionListener(this);
		
		
		this.setJMenuBar(mb);
		this.add(fontLabel);
		this.add(fontSizeSpinner);
		this.add(changeColor);
		this.add(fontBox);
		this.add(sp);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==changeColor)
		{
			JColorChooser c = new JColorChooser();
			Color color = c.showDialog(null, "Choose a color", Color.black);
			ta.setForeground(color);
		}
		
		if(e.getSource()==fontBox)
		{
			ta.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, ta.getFont().getSize()));
		}
			
		if(e.getSource()==open)
		{
			JFileChooser fc = new JFileChooser("Choose a file to Open...");
			fc.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
			fc.setFileFilter(filter);
			
			int response = fc.showOpenDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION)
			{
				File file = new File(fc.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				try 
				{
					fileIn = new Scanner(file);
					if(file.isFile())
					{
						while(fileIn.hasNextLine())
						{
							String line = fileIn.nextLine();
							ta.setText(line+"\n");
						}
					}
					
				}
				catch (FileNotFoundException e1)
				{
					e1.printStackTrace();
				}
				finally
				{
					fileIn.close();
				}
			}
		}
		
		if(e.getSource()==save)
		{
			JFileChooser fc = new JFileChooser();
			fc.setBackground(Color.black);
			fc.setCurrentDirectory(new File("."));
			
			int response = fc.showSaveDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION)
			{
				File file;
				PrintWriter fileOut = null;
				
				file = new File(fc.getSelectedFile().getAbsolutePath());
				try
				{
					fileOut = new PrintWriter(file);
					fileOut.println(ta.getText());
				}
				catch (FileNotFoundException e1)
				{
					e1.printStackTrace();
				}
				finally
				{
					fileOut.close();
				}
			}
			
		}
		
		if(e.getSource()==exit)
		{
			System.exit(0);
		}
	}
}
