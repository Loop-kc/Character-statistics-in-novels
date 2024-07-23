import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class IntimacyPanel extends JPanel {
    private ArrayList<Person> personList = new ArrayList<>();//动态数组

    public JPanel Intimacy_Panel(String text) throws IOException {
        String[] nameArray = {
                "刘备", "魏延", "关羽", "张飞", "董卓", "吕布", "孙权", "周瑜", "司马懿", "曹操"
        };

        // 实例化一个亲密度类的对象
        IntimacySort intimacySort = new IntimacySort(nameArray, text);
        this.personList = intimacySort.intimacy();//调用求任意两人亲密度的方法返回一个ArrayList<Person>对象

        // 按亲密度将ArrayList中的personList将亲密度多到少排序
        Collections.sort(personList, Comparator.<Person, Integer>comparing(Person::getIntimacy).reversed());

        // 设置列名
        final Object[] columnNames = {"人物 ", "联系程度", "排名"};

        // 定义二维数组存储每行中的内容，初始值默认为null
        Object[][] rowData = new Object[nameArray.length][3];

        JTable jTable = new JTable(rowData, columnNames);//把行列内容加到表格

        // 给表格每行赋值(也可以说更新每行的值)
        for (int i = 0; i < personList.size(); i++) {
            Person person = personList.get(i);
            jTable.setValueAt(person.getName(), i, 0);
            jTable.setValueAt(person.getIntimacy(), i, 1);
            jTable.setValueAt(i + 1, i, 2);
        }

        jTable.setPreferredScrollableViewportSize(new Dimension(800, 700));//设置表格大小
        jTable.setRowHeight(100);//设置每行高
        jTable.setSelectionBackground(Color.PINK);//设置所选择行的背景色
        jTable.setSelectionForeground(Color.BLUE);//设置所选择行的前景色
        jTable.setGridColor(Color.BLACK);//设置网格线的颜色
        jTable.setFont(new Font("Dialog", Font.ITALIC, 18));//设置字体大小和颜色
        jTable.setBackground(new Color(165, 216, 232));

        // JTable最好加在JScrollPane上
        JScrollPane scrollPane = new JScrollPane(jTable);

        // 创建一个面板对象
        JPanel intimacyPanel = new JPanel();
        intimacyPanel.add(scrollPane);//将JScrollPane加到面板上

        // 设置面板的背景颜色
        intimacyPanel.setBackground(new Color(88, 89, 208));
        return intimacyPanel;
    }
}
