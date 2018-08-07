package wdk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import wdk.option.DeliverFile2;

@SuppressWarnings("all")
public class SwingLoginExample {
	private static JTable table;
	private static DefaultTableModel model;
	private static Vector names;//表的列名
	private static Vector data = new Vector();//表的数据
	private static Map<String,File> map = new HashMap<String,File>();//存放选择的文件
	private static List<File> list = new ArrayList<File>();//存放选择的文件
	
	public static void main(String[] args) {
		UIManager ui = new UIManager();
		try {//设置感官风格与系统一致
			ui.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		JFrame frame = new JFrame("王得坤版权所有");
		frame.setSize(350, 300);
		frame.setLocation(400, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JScrollPane p = new JScrollPane();
		placeComponents(p);
		frame.add(p);
		// 设置界面可见	JTable
		frame.setVisible(true);
	}

	private static void placeComponents(JScrollPane panel) {
		Element element = new Element();
		element.setName("选择文件");
		element.setOpenFile(true);
		panel.add(getJButton(element));
		
		Element e = new Element();
		e.setName("确定");
		e.setOpenFile(true);
		JButton btn = getJButton(e);
		btn.setBounds(200, 200, 120, 25);
		panel.add(btn);
		
		JTable table = getJTable(element);
		table.setName("ceshi de biaoge");
		panel.add(table);
		panel.setViewportView(table);
	}
	/**
	 * 获取表格
	 * @param element
	 * @return
	 */
	private static JTable getJTable(Element element){
		String[] columns = {"文件名称", "文件大小", "文件类型", "全路径"};  
		 names = new Vector();
		/*for (int i = 0; i < columns.length; i++) {
			names.add(columns[i]);
		}*/
		names.add("文件名称");
		names.add("文件大小");
	    model = new DefaultTableModel(data, names); 
		table =new JTable();
		table.setModel(model);
		return table;
	}
	/**
	 * 打开文件选择窗口
	 * @param btn
	 */
	private static void openFile(JButton btn,Element element){
		btn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
            	if("选择文件".equals(element.getName())){
            		JFileChooser fc = new JFileChooser();
                	//FILES_ONLY	DIRECTORIES_ONLY
                	fc.setFileSelectionMode(JFileChooser.FILES_ONLY);//设置文件选择方式
                	fc.showOpenDialog(null);
                	File f = fc.getSelectedFile();//获取选择的文件
                	String absolutePath = f.getAbsolutePath();
                	String fileName = f.getName();
                	File odlFile = map.put(fileName, f);
                	if(odlFile!=null)return;
                	list.add(f);
                	//把选择的文件信息显示到面板中
                	DecimalFormat df = new DecimalFormat("#.00");
                	Vector row = new Vector();
            		row.add(fileName);
            		row.add(df.format((double)f.length()/1048576)+"MB");
            		data.add(row);
            		model = new DefaultTableModel(data, names); 
            		table.setModel(model);
            	}else if("确定".equals(element.getName())){
            		if(list.size()<=1)return;
                	DeliverFile2.merge(list);
                	JOptionPane.showMessageDialog(null,"主人我完成任务啦！快点赞！","棒棒哒",
                			JOptionPane.INFORMATION_MESSAGE);
                	list.clear();
                	map.clear();
                	//清空列表
                	data.clear();
                	model = new DefaultTableModel(data, names); 
            		table.setModel(model);
            	}
            		
            	
            		
            }           
        });
	}
	/**
	 * 获取按钮
	 * @param element
	 * @return
	 */
	private static JButton getJButton(Element element){
		JButton btn =new JButton(element.getName());
		btn.setBounds(0, 200, 120, 25);
		if(element.isOpenFile())openFile(btn,element);
		btn.repaint();
		return btn;
	}
	/**
	 * 获取标签
	 * @param element
	 * @return
	 */
	private static JLabel getJLabel(Element element){
		JLabel lab =new JLabel(element.getName());
		lab.setBounds(10, 20, 80, 25);
		return lab;
	}
	
}