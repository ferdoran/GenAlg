package de.fhkoeln.gm.ki.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;


import de.fhkoeln.gm.ki.alg.GeneticAlgorithm;
import de.fhkoeln.gm.ki.alg.fitnessFunctions.AbstractFitness;
import de.fhkoeln.gm.ki.alg.genes.AbstractGene;
import de.fhkoeln.gm.ki.alg.mutators.AbstractMutator;
import de.fhkoeln.gm.ki.alg.recombiners.AbstractRecombiner;
import de.fhkoeln.gm.ki.alg.reproducers.AbstractReproducer;
import de.fhkoeln.gm.ki.alg.selectors.AbstractSelector;
import de.fhkoeln.gm.ki.alg.util.DataSource;
import de.fhkoeln.gm.ki.alg.util.Individual;
import de.fhkoeln.gm.ki.alg.util.Initializer;
import de.fhkoeln.gm.ki.remoteControl.BotMonitor;
import de.fhkoeln.gm.ki.remoteControl.Connector;

import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.jfree.chart.ChartPanel;

import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTabbedPane;

public class GUI_Swing implements ActionListener {
	private DataSource ds;

	private Individual fittestIndividual;
	private Individual weakestIndividual; 

	private JFrame frmGeneticAlgorithmThingamagig;
	private JFormattedTextField PopSizeText;
	private JFormattedTextField GeneSizeText;
	private JFormattedTextField maxGensText;
	private JFormattedTextField fitnessTargetText;
	private JPanel popPanel;
	private boolean initialized=false;
	private ArrayList<JTextField> textFields;
	private JButton btnPauseUnpause;
	private JLabel lblStrongestKnownIndividual;
	private JLabel lblWeakestKnownIndividual;

	private String standardFilename = "GA_State.txt";
	
	private static GUI_Swing self;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Swing window = new GUI_Swing();
					self=window;
					window.frmGeneticAlgorithmThingamagig.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_Swing() {
		ds = DataSource.getInstance();
		ds.initialize();
		textFields = new ArrayList<JTextField>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGeneticAlgorithmThingamagig = new JFrame();
		frmGeneticAlgorithmThingamagig.setTitle("Genetic Algorithm Thingamagig 5000");
		frmGeneticAlgorithmThingamagig.setBounds(100, 100, 799, 600);
		frmGeneticAlgorithmThingamagig.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmGeneticAlgorithmThingamagig.getContentPane().add(tabbedPane);
		
		JPanel mainPanel = new JPanel();
		tabbedPane.addTab("Main Krempel", null, mainPanel, null);
		
		ChartPanel graphPanel = new ChartPanel(GraphData.getInstance().chart);
		tabbedPane.addTab("Graph Zahl", null, graphPanel, null);

		
		JScrollPane mutatorPane = new JScrollPane();
		
		JPanel mutatorPanel = new JPanel();
		mutatorPane.setViewportView(mutatorPanel);
		mutatorPanel.setLayout(new BoxLayout(mutatorPanel, BoxLayout.Y_AXIS));

		ButtonGroup mutatorGroup = new ButtonGroup();
		for(int i=0; i< ds.getMutators().size();i++){
			AbstractMutator mutator = ds.getMutators().get(i);
			JRadioButton mutatorButton = new JRadioButton(mutator.getName());
			mutatorPanel.add(mutatorButton);
			mutatorButton.setSelected(true);
			mutatorButton.addActionListener(mutator);
			mutatorGroup.add(mutatorButton);
			mutatorButton.doClick();
		}
		
		JScrollPane genesPane = new JScrollPane();
		genesPane.setToolTipText("Checked Genes will be used in Gene Pool Generation");
		
		JPanel genesPanel = new JPanel();
		genesPane.setViewportView(genesPanel);
		genesPanel.setLayout(new BoxLayout(genesPanel, BoxLayout.Y_AXIS));
		
		for(int i=0; i< ds.getGenes().size();i++){
			AbstractGene gene = ds.getGenes().get(i);
			JCheckBox chckbxBlock = new JCheckBox(gene.getDescription());
			genesPanel.add(chckbxBlock);
			chckbxBlock.setSelected(true);
			chckbxBlock.addItemListener(gene);
		}
/*
		JCheckBox chckbxTestblock2 = new JCheckBox("TestBlock");
		blocksPanel.add(chckbxTestblock2);
		JCheckBox chckbxTestblock3 = new JCheckBox("TestBlock");
		blocksPanel.add(chckbxTestblock3);
		JCheckBox chckbxTestblock4 = new JCheckBox("TestBlock");
		blocksPanel.add(chckbxTestblock4);
		JCheckBox chckbxTestblock5 = new JCheckBox("TestBlock");
		blocksPanel.add(chckbxTestblock5);
		JCheckBox chckbxTestblock6 = new JCheckBox("TestBlock");
		blocksPanel.add(chckbxTestblock6);
*/		
		JScrollPane fitnessFunctionPane = new JScrollPane();
		
		JPanel fitnessPanel = new JPanel();
		fitnessFunctionPane.setViewportView(fitnessPanel);
		fitnessPanel.setLayout(new BoxLayout(fitnessPanel, BoxLayout.Y_AXIS));
		
		ButtonGroup fitnessGroup = new ButtonGroup();
		for(int i=0; i< ds.getFitnessFunctions().size();i++){
			AbstractFitness fitness = ds.getFitnessFunctions().get(i);
			JRadioButton fitnessButton = new JRadioButton(fitness.getName());
			fitnessPanel.add(fitnessButton);
			fitnessButton.setSelected(true);
			fitnessButton.addActionListener(fitness);
			fitnessGroup.add(fitnessButton);
			fitnessButton.doClick();
		}
		
		JScrollPane popPane = new JScrollPane();
		popPane.getVerticalScrollBar().setUnitIncrement(50);
		popPanel = new JPanel();
		popPane.setViewportView(popPanel);
		popPanel.setLayout(new BoxLayout(popPanel, BoxLayout.Y_AXIS));
		

		
/*		txtTestgene = new JTextField();
		txtTestgene.setEditable(false);
		txtTestgene.setAlignmentY(Component.TOP_ALIGNMENT);
		txtTestgene.setText("TestGene");
		panel_1.add(txtTestgene);
		txtTestgene.setColumns(10);
		
		txtTestgene_1 = new JTextField();
		txtTestgene_1.setEditable(false);
		txtTestgene_1.setText("TestGene2");
		panel_1.add(txtTestgene_1);
		txtTestgene_1.setColumns(10);
*/		
		JButton btnGenerateGpool = new JButton("Initialize");
		btnGenerateGpool.setActionCommand("initialize");
		btnGenerateGpool.addActionListener(this);
		
		JButton btnStartMutating = new JButton("Execute");
		btnStartMutating.setActionCommand("execute");
		btnStartMutating.addActionListener(this);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setActionCommand("connect");
		btnConnect.addActionListener(this);
		
		btnPauseUnpause = new JButton("Pause/Resume");
		btnPauseUnpause.setActionCommand("pause");
		btnPauseUnpause.addActionListener(this);
		btnPauseUnpause.setEnabled(false);
		
		PopSizeText = new JFormattedTextField(NumberFormat.getIntegerInstance());
		PopSizeText.setText("50");
		PopSizeText.setColumns(10);

		
		GeneSizeText = new JFormattedTextField(NumberFormat.getIntegerInstance());
		GeneSizeText.setText("4");
		GeneSizeText.setColumns(10);
		
		
		JLabel lblPoolsize = new JLabel("Pop. Size:");
		lblPoolsize.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblNewLabel = new JLabel("Gene Size:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblGenes = new JLabel("Genes");
		
		JLabel lblMutators = new JLabel("Mutators");
		
		JLabel lblFitnessFunctions = new JLabel("Fitness Functions");
		
		JTextPane txtpnAnim = new JTextPane();
		txtpnAnim.setEditable(false);
		txtpnAnim.setText("Anim");
		
		JLabel lblSelectors = new JLabel("Selectors");
		
		JScrollPane selectorPane = new JScrollPane();
		
		JPanel selectorPanel = new JPanel();
		selectorPane.setViewportView(selectorPanel);
		selectorPanel.setLayout(new BoxLayout(selectorPanel, BoxLayout.Y_AXIS));

		ButtonGroup selectorGroup = new ButtonGroup();
		for(int i=0; i< ds.getSelectors().size();i++){
			AbstractSelector selector = ds.getSelectors().get(i);
			JRadioButton selectorButton = new JRadioButton(selector.getName());
			selectorPanel.add(selectorButton);
			selectorButton.setSelected(true);
			selectorButton.addActionListener(selector);
			selectorGroup.add(selectorButton);
			selectorButton.doClick();
		}

		
		JLabel lblRecombiners = new JLabel("Recombiners");
		
		JScrollPane recombinerPane = new JScrollPane();
		
		JPanel recombinerPanel = new JPanel();
		recombinerPane.setViewportView(recombinerPanel);
		recombinerPanel.setLayout(new BoxLayout(recombinerPanel, BoxLayout.Y_AXIS));
		
		ButtonGroup recombinerGroup = new ButtonGroup();
		for(int i=0; i< ds.getRecombiners().size();i++){
			AbstractRecombiner recombiner = ds.getRecombiners().get(i);
			JRadioButton recombinerButton = new JRadioButton(recombiner.getName());
			recombinerPanel.add(recombinerButton);
			recombinerButton.setSelected(true);
			recombinerButton.addActionListener(recombiner);
			recombinerGroup.add(recombinerButton);
			recombinerButton.doClick();
		}

		
		JScrollPane reproducerPane = new JScrollPane();
		
		JPanel reproducerPanel = new JPanel();
		reproducerPane.setViewportView(reproducerPanel);
		reproducerPanel.setLayout(new BoxLayout(reproducerPanel, BoxLayout.Y_AXIS));
		
		ButtonGroup reproducerGroup = new ButtonGroup();
		for(int i=0; i< ds.getReproducers().size();i++){
			AbstractReproducer reproducer = ds.getReproducers().get(i);
			JRadioButton reproducerButton = new JRadioButton(reproducer.getName());
			reproducerPanel.add(reproducerButton);
			reproducerButton.setSelected(true);
			reproducerButton.addActionListener(reproducer);
			reproducerGroup.add(reproducerButton);
			reproducerButton.doClick();
		}

		
		JLabel lblReproducers = new JLabel("Reproducers");
		
		JButton btnLoad = new JButton("Load");
		btnLoad.setActionCommand("load");
		btnLoad.addActionListener(this);
		
		JButton btnSave = new JButton("Save");
		btnSave.setActionCommand("save");
		btnSave.addActionListener(this);
		
		JLabel lblFitTarget = new JLabel("Fit. Target:");
		lblFitTarget.setHorizontalAlignment(SwingConstants.TRAILING);
		
		JLabel lblMaxGen = new JLabel("Max Gen:");
		lblMaxGen.setHorizontalAlignment(SwingConstants.TRAILING);
		
		fitnessTargetText = new JFormattedTextField(NumberFormat.getNumberInstance());
		fitnessTargetText.setText("0");
		
		maxGensText = new JFormattedTextField(NumberFormat.getIntegerInstance());
		maxGensText.setText("2000");
		
		JButton btnExecuteStrongest = new JButton("Execute");
		btnExecuteStrongest.setActionCommand("execStrongest");
		btnExecuteStrongest.addActionListener(this);
		
		JButton btnExecuteWeakest = new JButton("Execute");
		btnExecuteWeakest.setActionCommand("execWeakest");
		btnExecuteWeakest.addActionListener(this);
		
		lblStrongestKnownIndividual = new JLabel("Strongest known Individual:");
		
		lblWeakestKnownIndividual = new JLabel("Weakest known Individual:");
		
		JCheckBox chckbxMicropauses = new JCheckBox("Micropauses");
		chckbxMicropauses.setSelected(true);
		
		JCheckBox chckbxMacropauses = new JCheckBox("Macropauses");
		chckbxMacropauses.setSelected(true);
		
		GroupLayout groupLayout = new GroupLayout(mainPanel);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtpnAnim, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(5)
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(lblPoolsize, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(PopSizeText, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
								.addComponent(GeneSizeText, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblMaxGen, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblFitTarget, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(maxGensText)
								.addComponent(fitnessTargetText, GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
							.addGap(33))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(genesPane, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
								.addComponent(lblGenes, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMutators, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
								.addComponent(mutatorPane, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
								.addComponent(lblFitnessFunctions, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
								.addComponent(fitnessFunctionPane, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
								.addComponent(selectorPane, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
								.addComponent(lblSelectors, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblRecombiners, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
								.addComponent(recombinerPane, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
								.addComponent(lblReproducers, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
								.addComponent(reproducerPane, GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
							.addGap(146)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(btnExecuteStrongest)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblStrongestKnownIndividual, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(btnExecuteWeakest)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblWeakestKnownIndividual, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(btnSave, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
									.addComponent(btnLoad, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(btnConnect, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnPauseUnpause, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(btnStartMutating, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(btnGenerateGpool, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(chckbxMicropauses)
							.addGap(18)
							.addComponent(chckbxMacropauses, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)))
					.addGap(40))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblGenes)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(13)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnExecuteStrongest)
										.addComponent(lblStrongestKnownIndividual))
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnExecuteWeakest)
										.addComponent(lblWeakestKnownIndividual)))
								.addComponent(genesPane, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(7)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblMutators)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(13)
									.addComponent(mutatorPane, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)))
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblFitnessFunctions)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(13)
									.addComponent(fitnessFunctionPane, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)))
							.addGap(10)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(12)
									.addComponent(selectorPane, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
								.addComponent(lblSelectors))
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblRecombiners)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(12)
									.addComponent(recombinerPane, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)))
							.addGap(11)
							.addComponent(lblReproducers)
							.addComponent(reproducerPane, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
							.addGap(26))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(chckbxMicropauses)
								.addComponent(chckbxMacropauses))
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(29)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel)
								.addComponent(GeneSizeText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMaxGen)
								.addComponent(maxGensText, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnLoad)))
						.addComponent(txtpnAnim, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(PopSizeText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPoolsize)
								.addComponent(lblFitTarget)
								.addComponent(fitnessTargetText, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnConnect)
								.addComponent(btnGenerateGpool, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnPauseUnpause)
								.addComponent(btnStartMutating))))
					.addGap(5))
		);
		
		mainPanel.setLayout(groupLayout);
		mainPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{genesPanel, mutatorPanel, fitnessPanel, selectorPanel, recombinerPanel, reproducerPanel, PopSizeText, GeneSizeText, btnGenerateGpool, btnStartMutating, btnConnect, btnPauseUnpause, lblGenes, frmGeneticAlgorithmThingamagig.getContentPane(), mutatorPane, genesPane, fitnessFunctionPane, popPane, popPanel, lblPoolsize, lblNewLabel, lblMutators, lblFitnessFunctions, txtpnAnim, lblSelectors, selectorPane, lblRecombiners, recombinerPane, reproducerPane, lblReproducers}));
		
		JPanel currGenPanel = new JPanel();
		tabbedPane.addTab("Current Propulation! Weeee!", null, currGenPanel, null);
		GroupLayout gl_currGenPanel = new GroupLayout(currGenPanel);
		gl_currGenPanel.setHorizontalGroup(
			gl_currGenPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_currGenPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(popPane, GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_currGenPanel.setVerticalGroup(
			gl_currGenPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_currGenPanel.createSequentialGroup()
					.addGap(5)
					.addComponent(popPane, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
					.addContainerGap())
		);
		currGenPanel.setLayout(gl_currGenPanel);

		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		if("initialize".equals(e.getActionCommand())){
			init();
		}
		if("execute".equals(e.getActionCommand())){
			execute();
		}
		if("connect".equals(e.getActionCommand())){
			connect();
		}
		if("pause".equals(e.getActionCommand())){
			pause();
		}
		if("save".equals(e.getActionCommand())){
			save();
		}
		if("load".equals(e.getActionCommand())){
			load();
		}
		if("execStrongest".equals(e.getActionCommand())){
			executeStrongest();
		}
		if("execWeakest".equals(e.getActionCommand())){
			executeWeakest();
		}
	}
	
	private void pause() {
		if(ds.currentGeneticAlgorithm.isPaused())
			ds.currentGeneticAlgorithm.resumeThread();
		else
			ds.currentGeneticAlgorithm.pauseThread();
		
	}

	private void connect() {
		if(DataSource.BOTNAME.equals("Unknown")){
			JOptionPane.showMessageDialog(frmGeneticAlgorithmThingamagig,"Please specify the bot's name in DataSource.BOTNAME");
		} else{
			try {
				if(Connector.isConnected())
					Connector.close();
				Connector.connect(DataSource.BOTNAME);
				BotMonitor.getInstance().initialize(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void init(){
		if(ds.currentGeneticAlgorithm!=null){
			ds.currentGeneticAlgorithm.kill();
			algorithmIsFinished();
		}
		GraphData.getInstance().clear();		
		int popSize= Integer.parseInt(PopSizeText.getText().replaceAll("\\D", ""));
		int indSize= Integer.parseInt(GeneSizeText.getText().replaceAll("\\D", ""));		
		
		if(popSize<1||indSize<1)
			return;
		initialized=true;
		Initializer init = new Initializer();
		ArrayList<AbstractGene> abst = new ArrayList<AbstractGene>();
		for(int i = 0; i < ds.getGenes().size();i++){
			if(ds.getGenes().get(i).isChecked())
				abst.add(ds.getGenes().get(i));
		}
		AbstractGene[] viableGenes = new AbstractGene[abst.size()];
		for(int i=0;i<abst.size();i++){
			viableGenes[i]=abst.get(i);
		}
		
		ds.setPopulation(init.initialize(popSize, indSize, viableGenes));
		ds.setViableGenes(viableGenes);
			
		for(int i =0;i<ds.getPopulation().getPop().size();i++){
			if(textFields.size()>i){
				textFields.get(i).setText(ds.getPopulation().getPop().get(i).toString());
				popPanel.add(textFields.get(i));
			}
			else{
				JTextField text = new JTextField();
				text.setEditable(false);
				text.setText(ds.getPopulation().getPop().get(i).toString());
				popPanel.add(text);
				text.setColumns(2);
				textFields.add(text);
			}
		}
		if(textFields.size()>ds.getPopulation().getPop().size()){
			for(int i=ds.getPopulation().getPop().size();i<textFields.size();i++){
				popPanel.remove(textFields.get(i));
			}
		}
		popPanel.revalidate();
		popPanel.repaint();
	}
	
	private void execute(){
		if(!initialized)
			return;
		if(ds.currentGeneticAlgorithm!=null){
			ds.currentGeneticAlgorithm.kill();
			algorithmIsFinished();
		}		
		int maxGens= Integer.parseInt(maxGensText.getText().replaceAll("\\D", ""));		
		if(maxGens<1)
			maxGens=Integer.MAX_VALUE;
		float fitnessTarget= Float.parseFloat(fitnessTargetText.getText().replaceAll("[.]", "").replaceAll("[,]","."));
		if(fitnessTarget <=0)
			fitnessTarget = Float.POSITIVE_INFINITY;
		
		
		GeneticAlgorithm ga = new GeneticAlgorithm(ds.getPopulation(), ds.currentFitness, ds.currentSelector, ds.currentRecombiner, ds.currentMutator, ds.currentReproducer,maxGens,fitnessTarget);
		ga.start();
		btnPauseUnpause.setEnabled(true);
	}
	
	public void updateTextFields(){
		synchronized (ds.getPopulation()) {
			for(int i=0;i<ds.getPopulation().getPop().size();i++){
				textFields.get(i).setText(ds.getPopulation().getPop().get(i).toString());
			}
		}
	}
	public void algorithmIsFinished(){
		btnPauseUnpause.setEnabled(false);
	}
	
	public static GUI_Swing getInstance(){
		return self;
	}
	
	public void warnOfSensorRange(){
		btnPauseUnpause.setEnabled(true);
		JOptionPane.showMessageDialog(frmGeneticAlgorithmThingamagig,"Bot is about to move out of sensor range, please move it back and unpause");
	}
	
	public void updateWeakestFittestIndividual(Individual weakest, Individual fittest){
		if(weakestIndividual==null||weakestIndividual.fitness<weakest.fitness)
			weakestIndividual=weakest;
		lblWeakestKnownIndividual.setText("Weakest known Individual: "+weakestIndividual.toString());
		if(fittestIndividual==null||fittestIndividual.fitness>fittest.fitness)
			fittestIndividual=fittest;
		lblStrongestKnownIndividual.setText("Strongest known Individual: "+fittestIndividual.toString());
		
		
	}
	
	private void save(){
		if(ds.currentGeneticAlgorithm== null){
			JOptionPane.showMessageDialog(frmGeneticAlgorithmThingamagig,"There's no GA to save -.-");
			return;
		}
		DataSource.getInstance().currentGeneticAlgorithm.saveState(standardFilename);
	}
	private void load(){
		if(!initialized)
			init();
		GeneticAlgorithm ga = GeneticAlgorithm.loadState(standardFilename);
		ds.currentGeneticAlgorithm=ga;
		updateTextFields();
		popPanel.revalidate();
		popPanel.repaint();
	}
	private void executeStrongest(){
		if(ds.currentGeneticAlgorithm==null||fittestIndividual==null){
			JOptionPane.showMessageDialog(frmGeneticAlgorithmThingamagig,"There should actually BE a fittest individual in order to execute it, don't you agree?");
			return;
		}
		if(!(ds.currentGeneticAlgorithm.currentlyPaused||ds.currentGeneticAlgorithm.finished)){
			JOptionPane.showMessageDialog(frmGeneticAlgorithmThingamagig,"Aaaand the Algorithm should be paused or finished, what with the bot only having one set of motors, eh?");
			return;
		}
		fittestIndividual.execute();
	}
	private void executeWeakest(){
		if(ds.currentGeneticAlgorithm==null||weakestIndividual==null){
			JOptionPane.showMessageDialog(frmGeneticAlgorithmThingamagig,"There should actually BE a weakest individual in order to execute it, don't you agree?");
			return;
		}
		if(!(ds.currentGeneticAlgorithm.currentlyPaused||ds.currentGeneticAlgorithm.finished)){
			JOptionPane.showMessageDialog(frmGeneticAlgorithmThingamagig,"Aaaand the Algorithm should be paused or finished, what with the bot only having one set of motors, eh?");
			return;
		}
		weakestIndividual.execute();
	}
}

