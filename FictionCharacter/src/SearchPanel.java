import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class SearchPanel extends JFrame {
    public static JPanel Search_Panel(int[][] intimacy) throws IOException {
        // 人物名称数组
        String[] nameArray = {
                "刘备", "魏延", "关羽", "张飞", "董卓", "吕布", "孙权", "周瑜", "司马懿", "曹操"
        };

        // 用于存储每个人物的信息，包括人物名称、出现次数、跨度
        ArrayList<Person> list = new ArrayList<>();
        CharacterReader characterReader = new CharacterReader();

        // 遍历人物数组，获取每个人物的出现次数和跨度，然后添加到列表中
        for (String name : nameArray) {
            int times = characterReader.appearTimes(name);
            int span = characterReader.appearSpan(name);
            list.add(new Person(name, times, span));
        }

        // 定义表格列名
        Object[] columnNames = new Object[nameArray.length + 1];
        columnNames[0] = "亲密度";

        for (int i = 1; i <= nameArray.length; i++) {
            columnNames[i] = nameArray[i - 1];
        }

        // 定义二维数组用于存储表格的行数据
        Object[][] rowData = new Object[list.size()][nameArray.length + 1];

        JTable jTable = new JTable(rowData, columnNames);

        // 为表格赋值
        for (int i = 0; i < nameArray.length; i++) {
            jTable.setValueAt(nameArray[i], i, 0);
            for (int j = 0; j < nameArray.length; j++) {
                jTable.setValueAt(intimacy[i][j], i, j + 1);
            }
        }

        // 设置表格的外观和属性
        jTable.setPreferredScrollableViewportSize(new Dimension(800, 700));
        jTable.setRowHeight(100);
        jTable.setSelectionBackground(Color.pink);
        jTable.setSelectionForeground(Color.BLUE);
        jTable.setGridColor(Color.BLACK);
        jTable.setFont(new Font("Dialog", Font.BOLD, 15));
        jTable.setBackground(new Color(165, 216, 232));

        // 将表格放入带滚动条的面板中
        JScrollPane scrollPane = new JScrollPane(jTable);

        // 创建包含表格的面板
        JPanel searchPanel = new JPanel();
        searchPanel.add(scrollPane);
        searchPanel.setBackground(new Color(88, 89, 208));

        return searchPanel;
    }
}
