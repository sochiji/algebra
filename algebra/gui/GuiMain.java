package gui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import matrix.Matrix;
import number.Fraction;
import storage.MatrixManager;
import util.Container;

public class GuiMain {

	private interface OneMatrixToOneFraction {
		Fraction operate(Matrix mat);
	}

	private interface OneMatrixToOneMatrix {
		Matrix operate(Matrix mat);
	}

	static final String cwd = ".\\AlgebraData";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					GuiMain window = new GuiMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JFrame frame;;
	Container<String[]> messageContainer;
	DefaultListModel<String> listCore = new DefaultListModel<String>();
	MatrixManager manager;
	JList<String> list = new JList<String>(listCore);
	private JMenu menuCalculate = new JMenu("\u8FD0\u7B97");
	private JMenu menuMatrixCalculate = new JMenu("\u77E9\u9635\u8FD0\u7B97");
	private JMenuItem menuItemDetcal = new JMenuItem("\u65B9\u9635\u7684\u884C\u5217\u5F0F");
	private JMenuItem menuItem_12 = new JMenuItem("\u65B9\u9635\u7684\u7279\u5F81\u503C");
	private JMenuItem menuItem_13 = new JMenuItem("\u65B9\u9635\u7684\u7279\u5F81\u5411\u91CF");
	private JMenuItem menuRank = new JMenuItem("\u77E9\u9635\u7684\u79E9");
	private JMenuItem mntmAddition = new JMenuItem("\u77E9\u9635\u52A0\u51CF\u8FD0\u7B97");
	private JMenuItem mntmScalar = new JMenuItem("\u77E9\u9635\u4E0E\u6709\u7406\u6570\u76F8\u4E58");
	private JMenu menuRelation = new JMenu("\u5173\u7CFB\u5224\u65AD");
	private JMenuItem menuItem_9 = new JMenuItem("\u5411\u91CF\u7EC4\u7684\u7EBF\u6027\u76F8\u5173\u6027");
	private JMenuItem menuItem_11 = new JMenuItem("\u5411\u91CF\u7684\u6B63\u4EA4\u6027");
	private JMenuItem menuItem_16 = new JMenuItem("\u77E9\u9635\u7684\u7B49\u4EF7\u6027");
	private JMenuItem menuItem_17 = new JMenuItem("\u77E9\u9635\u7684\u76F8\u4F3C\u6027");
	private JMenu menuMatrixTransform = new JMenu("\u77E9\u9635\u53D8\u6362");
	private JMenuItem menuItemGetTransMatrix = new JMenuItem("\u751F\u6210\u8F6C\u7F6E\u77E9\u9635");
	private JMenuItem menuItemGetRowLadder = new JMenuItem("\u751F\u6210\u884C\u9636\u68AF\u5F62");
	private JMenuItem menuItemGetRowSimplified = new JMenuItem("\u751F\u6210\u884C\u6700\u7B80\u5F62");
	private JMenuItem menuItemGetStandard = new JMenuItem("\u751F\u6210\u6807\u51C6\u5F62");
	private JMenuItem mntmGetInverse = new JMenuItem("\u751F\u6210\u9006\u77E9\u9635");
	private JMenuItem menuItem_4 = new JMenuItem("\u81EA\u5B9A\u4E49\u521D\u7B49\u53D8\u6362");
	private JMenu menuCombination = new JMenu("\u62C6\u5206\u4E0E\u7EC4\u5408");
	private JMenuItem menuItem_5 = new JMenuItem("\u63D0\u53D6\u5411\u91CF\uFF08\u7EC4\uFF09");
	private JMenuItem menuItem_10 = new JMenuItem("\u6700\u5927\u65E0\u5173\u5411\u91CF\u7EC4");
	private JMenuItem menuItem_6 = new JMenuItem("\u5C06\u77E9\u9635\u5206\u5757");
	private JMenuItem menuItem_7 = new JMenuItem("\u77E9\u9635\u7EC4\u5408");
	private JMenu menuEquation = new JMenu("\u65B9\u7A0B\u7EC4\u76F8\u5173");
	private JMenuItem menuItem_8 = new JMenuItem("\u7EBF\u6027\u65B9\u7A0B\u7EC4\u6C42\u89E3");
	private JMenuItem menuItem_15 = new JMenuItem("\u77E9\u9635\u65B9\u7A0B");
	private JMenu menuHelp = new JMenu("\u5E2E\u52A9");
	private JMenuItem menuItemAbout = new JMenuItem("\u5173\u4E8E");
	private JLabel label = new JLabel("\u77E9\u9635\u53D8\u91CF\u5217\u8868");
	private JTextArea textArea = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(textArea);
	private JButton buttonSelectAll = new JButton("\u9009\u62E9\u5168\u90E8");
	private JButton buttonInverseSelection = new JButton("\u53CD\u5411\u9009\u62E9");
	private JButton buttonCancelSelection = new JButton("\u53D6\u6D88\u9009\u62E9");
	private JLabel label_1 = new JLabel("\u77E9\u9635\u53D8\u91CF\u5185\u5BB9\u9884\u89C8");
	private JScrollPane scrollPane_1 = new JScrollPane();
	private final JScrollPane scrollPane_2 = new JScrollPane();

	private final JButton buttonNewFraction = new JButton("\u65B0\u5EFA\u53D8\u91CF");

	private final JButton buttonDeleteFraction = new JButton("\u5220\u9664\u53D8\u91CF");

	DefaultListModel<String> fractionListCore = new DefaultListModel<String>();
	private final JMenuItem menuItemModifyMatrix = new JMenuItem("\u4FEE\u6539\u77E9\u9635");
	private final JMenuItem menuItemDeleteMatrix = new JMenuItem("\u5220\u9664\u77E9\u9635");

	/**
	 * Create the application.
	 */
	public GuiMain() {
		messageContainer = new Container<String[]>();
		manager = new MatrixManager(cwd, messageContainer);
		initialize();
	}

	private void clearSelection() {
		list.removeSelectionInterval(0, listCore.getSize() - 1);
	}

	private String getLastDefaultName() {
		String[] arr = manager.getNameList();
		boolean[] appeared = new boolean[128];
		Arrays.fill(appeared, false);
		for (String x : arr) {
			if (x.matches("[Nn]ewMatrix\\d+")) {
				int pos = x.lastIndexOf('x');
				appeared[Integer.parseInt(x.substring(pos + 1))] = true;
			}
		}
		int ret = 0;
		for (int i = 1; i <= 127; i++)
			if (!appeared[i]) {
				ret = i;
				break;
			}
		return "NewMatrix" + ret;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("\u6587\u4EF6");
		menuBar.add(fileMenu);

		JMenuItem menuItemReloadAll = new JMenuItem("\u4ECE\u78C1\u76D8\u5237\u65B0\u5217\u8868");
		menuItemReloadAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] failedList = manager.reloadAll();
				updateListCore(listCore, manager.getNameList());
				if (failedList.length == 0)
					JOptionPane.showMessageDialog(frame, "成功刷新列表！");
				else {
					String failedString = "";
					for (String x : failedList)
						failedString += x + "\n";
					JOptionPane.showMessageDialog(frame, "下列文件\n" + failedString + "未能成功读取。");
				}
			}
		});

		JMenuItem menuItemNewMatrix = new JMenuItem("\u65B0\u5EFA\u77E9\u9635");
		menuItemNewMatrix.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new EditMatrix(GuiMain.this);
			}
		});
		fileMenu.add(menuItemNewMatrix);
		menuItemModifyMatrix.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index != -1)
					new EditMatrix(index, GuiMain.this);
				else
					noSelectionCalculatingDialog();
			}
		});
		fileMenu.add(menuItemModifyMatrix);
		menuItemDeleteMatrix.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index == -1)
					noSelectionCalculatingDialog();
				else {
					JOptionPane.showConfirmDialog(frame, "是否确认要删除？", "删除确认", JOptionPane.YES_NO_OPTION);
					manager.delete(index);
					updateListCore();
				}
			}
		});
		fileMenu.add(menuItemDeleteMatrix);
		fileMenu.add(menuItemReloadAll);

		JMenuItem menuItemSaveAllToFile = new JMenuItem("\u4FDD\u5B58\u5217\u8868\u5230\u78C1\u76D8");
		menuItemSaveAllToFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					manager.saveAll();
					JOptionPane.showMessageDialog(frame, "成功保存全部文件！");
				} catch (IOException f) {
					JOptionPane.showMessageDialog(frame, "有部分文件保存失败！");
				}
			}
		});
		fileMenu.add(menuItemSaveAllToFile);

		JMenuItem menuItemOpenFolder = new JMenuItem("\u6253\u5F00\u6570\u636E\u5B58\u50A8\u4F4D\u7F6E");
		menuItemOpenFolder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Runtime newRuntime = Runtime.getRuntime();
				try {
					newRuntime.exec("explorer " + cwd);
				} catch (IOException f) {
				}
			}
		});
		;
		fileMenu.add(menuItemOpenFolder);

		menuBar.add(menuCalculate);

		menuCalculate.add(menuMatrixCalculate);

		menuMatrixCalculate.add(menuItemDetcal);
		menuItemDetcal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index != -1 && !manager.getMatrix(index).isSquare())
					showNotSquareDialog();
				else
					oneMatrixToOneFraction(mat -> mat.detCal());
			}
		});
		menuItem_12.setEnabled(false);

		menuMatrixCalculate.add(menuItem_12);
		menuItem_13.setEnabled(false);

		menuMatrixCalculate.add(menuItem_13);

		menuMatrixCalculate.add(menuRank);
		menuRank.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				oneMatrixToOneFraction(mat -> new Fraction(mat.rank()));
			}
		});
		mntmAddition.setEnabled(false);

		menuMatrixCalculate.add(mntmAddition);

		menuCalculate.add(menuRelation);
		menuItem_9.setEnabled(false);

		menuRelation.add(menuItem_9);
		menuItem_11.setEnabled(false);

		menuRelation.add(menuItem_11);
		menuItem_16.setEnabled(false);

		menuRelation.add(menuItem_16);
		menuItem_17.setEnabled(false);

		menuRelation.add(menuItem_17);

		menuCalculate.add(menuMatrixTransform);
		menuItemGetTransMatrix.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				oneMatrixToOneMatrix((mat) -> mat.trans());
			}
		});
		menuMatrixTransform.add(menuItemGetTransMatrix);
		menuItemGetRowLadder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				oneMatrixToOneMatrix(mat -> mat.getRowLadder());
			}
		});
		menuMatrixTransform.add(menuItemGetRowLadder);
		menuItemGetRowSimplified.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				oneMatrixToOneMatrix(mat -> mat.getRowSimplified());
			}
		});
		menuMatrixTransform.add(menuItemGetRowSimplified);
		menuItemGetStandard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				oneMatrixToOneMatrix(mat -> mat.getStandard());
			}
		});
		menuMatrixTransform.add(menuItemGetStandard);
		mntmGetInverse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index != -1
						&& (!manager.getMatrix(index).isSquare() || manager.getMatrix(index).detCal().getA() == 0))
					JOptionPane.showMessageDialog(frame, "所选矩阵不可逆", "错误", JOptionPane.ERROR_MESSAGE);
				else
					oneMatrixToOneMatrix(mat -> mat.getInverse());
			}
		});
		menuMatrixTransform.add(mntmGetInverse);

		JMenuItem menuItemGetMinor = new JMenuItem("\u751F\u6210\u4F59\u5B50\u9635");
		menuItemGetMinor.setEnabled(false);
		menuMatrixTransform.add(menuItemGetMinor);
		mntmScalar.setEnabled(false);
		menuMatrixTransform.add(mntmScalar);
		menuItem_4.setEnabled(false);

		menuMatrixTransform.add(menuItem_4);

		menuCalculate.add(menuCombination);
		menuItem_5.setEnabled(false);

		menuCombination.add(menuItem_5);
		menuItem_10.setEnabled(false);

		menuCombination.add(menuItem_10);
		menuItem_6.setEnabled(false);

		menuCombination.add(menuItem_6);
		menuItem_7.setEnabled(false);

		menuCombination.add(menuItem_7);

		menuCalculate.add(menuEquation);
		menuItem_8.setEnabled(false);

		menuEquation.add(menuItem_8);
		menuItem_15.setEnabled(false);

		menuEquation.add(menuItem_15);

		menuBar.add(menuHelp);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int index = list.getSelectedIndex();
				updateViewer(index);
			}
		});
		menuItemAbout.setHorizontalAlignment(SwingConstants.CENTER);
		menuItemAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame,
						"Version: Alpha 0.2\nAuthor: Sochiji\nE-mail: song_zs@qq.com\nGitHub: sochiji", "About",
						JOptionPane.PLAIN_MESSAGE);
			}
		});

		JMenuItem menuItemHowToUse = new JMenuItem("\u5982\u4F55\u4F7F\u7528");
		menuItemHowToUse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame,
						"将矩阵的信息写成扩展名为\".matdat\"的文本文件，放在当前目录下的\"AlgebraData\"文件夹中。\n程序启动时会从磁盘中读取一次矩阵列表，也可以手动刷新。\n格式：第一行两个正整数，中间用空格隔开，表示行数和列数。\n接下来按照矩阵排列的格式写有理数，每个有理数之间用空格隔开，分子和分母之间是\"/\"\n",
						"帮助", JOptionPane.PLAIN_MESSAGE);

			}
		});
		menuHelp.add(menuItemHowToUse);
		menuHelp.add(menuItemAbout);
		updateListCore();
		frame.getContentPane().setLayout(null);
		label.setHorizontalAlignment(SwingConstants.CENTER);

		label.setBounds(28, 0, 110, 27);
		label.setFont(new Font("等线", Font.PLAIN, 18));
		frame.getContentPane().add(label);

		textArea.setEditable(false);
		scrollPane.setBounds(205, 30, 372, 370);
		frame.getContentPane().add(scrollPane);

		buttonSelectAll.setBounds(36, 414, 91, 23);
		buttonSelectAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setAllSelected();
			}
		});
		frame.getContentPane().add(buttonSelectAll);

		buttonInverseSelection.setBounds(36, 447, 91, 23);
		buttonInverseSelection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int len = manager.getSize();
				int[] selected = list.getSelectedIndices();
				clearSelection();
				int[] totalList = new int[len];
				for (int i = 0; i <= len - 1; i++)
					totalList[i] = i;
				for (int x : selected)
					totalList[x] = -1;
				for (int x : totalList)
					if (x != -1)
						list.addSelectionInterval(x, x);
			}
		});
		frame.getContentPane().add(buttonInverseSelection);

		buttonCancelSelection.setBounds(36, 480, 91, 23);
		buttonCancelSelection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearSelection();
			}
		});
		frame.getContentPane().add(buttonCancelSelection);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);

		label_1.setFont(new Font("等线", Font.PLAIN, 18));
		label_1.setBounds(314, 0, 149, 27);
		frame.getContentPane().add(label_1);

		scrollPane_1.setBounds(10, 30, 140, 370);
		frame.getContentPane().add(scrollPane_1);
		scrollPane_1.setViewportView(list);
		scrollPane_2.setBounds(634, 30, 140, 370);

		frame.getContentPane().add(scrollPane_2);

		JList<String> list_1 = new JList<String>(fractionListCore);
		scrollPane_2.setViewportView(list_1);

		JLabel label_2 = new JLabel("\u6709\u7406\u6570\u53D8\u91CF\u5217\u8868");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("等线", Font.PLAIN, 18));
		label_2.setBounds(634, 0, 140, 27);
		frame.getContentPane().add(label_2);
		buttonNewFraction.setBounds(656, 414, 93, 23);
		buttonNewFraction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String addition = JOptionPane.showInputDialog(frame, "输入新有理数变量", "新有理数变量",
							JOptionPane.QUESTION_MESSAGE);
					if (addition != null)
						fractionListCore.addElement(addition);
				} catch (NumberFormatException f) {
					JOptionPane.showMessageDialog(frame, "有理数格式无法识别。", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		frame.getContentPane().add(buttonNewFraction);
		buttonDeleteFraction.setBounds(656, 447, 93, 23);
		buttonDeleteFraction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list_1.getSelectedIndex();
				if (index >= 0)
					fractionListCore.remove(index);
			}
		});
		frame.getContentPane().add(buttonDeleteFraction);

	}

	private void noSelectionCalculatingDialog() {
		JOptionPane.showMessageDialog(frame, "没有选中要操作的矩阵", "错误", JOptionPane.ERROR_MESSAGE);
	}

	private void oneMatrixToOneFraction(OneMatrixToOneFraction op) {
		int index = list.getSelectedIndex();
		if (index == -1) {
			noSelectionCalculatingDialog();
			return;
		}
		Matrix mat = manager.getMatrix(index);
		Fraction result = op.operate(mat);
		int choice = JOptionPane.showConfirmDialog(frame, result + "\n将结果加入变量列表？", "计算完毕", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (choice == JOptionPane.YES_OPTION)
			fractionListCore.addElement(result.toString());
	}

	private void oneMatrixToOneMatrix(OneMatrixToOneMatrix op) {
		int index = list.getSelectedIndex();
		if (index >= 0) {
			Matrix result = op.operate(manager.getMatrix(index));
			String name = getUniqueName();
			if (name == null)
				return;
			manager.add(result, name);
			updateListCore(listCore, manager.getNameList());
			try {
				manager.saveToFile(list.getLastVisibleIndex());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, name + ".matdat保存失败。", "错误", JOptionPane.ERROR_MESSAGE);
			}
		} else
			noSelectionCalculatingDialog();
	}

	private void setAllSelected() {
		int size = manager.getSize();
		list.getSelectionModel().setSelectionInterval(0, size - 1);
	}

	private String setNewMatrixNameDialog(Component c) {
		return (String) JOptionPane.showInputDialog(c, "设定新矩阵的名称", "计算完毕", JOptionPane.QUESTION_MESSAGE, null, null,
				getLastDefaultName());
	}

	private void showNotSquareDialog() {
		JOptionPane.showMessageDialog(frame, "所选矩阵不是方阵。", "错误", JOptionPane.ERROR_MESSAGE);

	}

	public void updateListCore() {
		updateListCore(listCore, manager.getNameList());
	}

	public void updateListCore(DefaultListModel<String> listCore, String[] nameList) {
		listCore.clear();
		for (String x : nameList)
			listCore.addElement(x);
	}

	void updateViewer(int index) {
		if (index != -1)
			textArea.setText(manager.getMatrix(index).toString());
		else
			textArea.setText("");
	}

	private boolean checkSameName(String matrixName) {
		boolean ret = false;
		String[] nameList = manager.getNameList();
		for (String x : nameList)
			if (matrixName.equalsIgnoreCase(x))
				ret = true;
		return ret;
	}

	private String getUniqueName() {
		String name;
		boolean isLegal;
		do {
			isLegal = true;
			name = setNewMatrixNameDialog(frame);
			if (name == null)
				return null;
			if (name.equals("")) {
				JOptionPane.showMessageDialog(frame, "矩阵名不能为空。", "错误", JOptionPane.ERROR_MESSAGE);
				isLegal = false;
			}
			if (checkSameName(name)) {
				JOptionPane.showMessageDialog(frame, "不允许名称重复。", "错误", JOptionPane.ERROR_MESSAGE);
				isLegal = false;
			}
		} while (!isLegal);
		return name;
	}
}
