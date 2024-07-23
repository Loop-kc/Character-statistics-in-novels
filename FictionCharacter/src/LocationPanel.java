import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class LocationPanel extends JFrame {
    // 人物名字数组
    private final String[] nameArray = {
            "刘备", "魏延", "关羽", "张飞", "董卓", "吕布", "孙权", "周瑜", "司马懿", "曹操"
    };
    CharacterReader getname = new CharacterReader();
    // 存储小说文本内容
    private String U;
    {
        try {
            U = getname.readNovel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // 将文本分割成多个部分，每个部分的长度为 1000 个字符
    String[] PartOfU = splitText(U, 1000);//字符分割

    public static String[] splitText(String text, int parts) {
        int partLength = (int) Math.ceil((double) text.length() / parts);
        String[] result = new String[parts];
        int start = 0;
        for (int i = 0; i < parts; i++) {
            int end = Math.min(start + partLength, text.length());
            result[i] = text.substring(start, end);
            start = end;
        }
        return result;
    }

    // 创建面板，显示每个人物在不同片段中的出现情况
    public JPanel Location_Panel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 创建表格模型
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("");

        // 遍历人物数组，为每个人物添加一行数据
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 使单元格不可编辑
            }
        };

        for (int i = 0; i < nameArray.length; i++) {
            String name = nameArray[i];
            tableModel.addRow(new Object[]{name});
            // 设置行高
            int rowHeight = (i == nameArray.length - 1) ? 85 : 71;
            table.setRowHeight(i, rowHeight);
        }

        table.setShowVerticalLines(false); // 不显示垂直网格线
        table.setIntercellSpacing(new Dimension(0, 0)); // 设置单元格间距为零

        // 添加表格到滚动面板，以支持大量数据
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // 去掉边框
        scrollPane.setPreferredSize(new Dimension(50, getHeight())); // 调整滚动面板宽度
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // 去掉垂直滚轮
        mainPanel.add(scrollPane, BorderLayout.WEST);

        // 添加绘制小说人物出现位置的面板
        JPanel drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int lineHeight = getHeight() / nameArray.length;
                // 遍历每个人物
                for (int i = 0; i < nameArray.length; i++) {
                    String name = nameArray[i];

                    // 遍历每个片段，如果包含人物名字，画一条竖线
                    for (int j = 0; j < 1000; j++) {
                        if (PartOfU[j].contains(name)) {
                            g.setColor(new Color(0, 0, 0));
                            g.fillRect(1 * j, i * lineHeight, 1, lineHeight - 15);
                        }
                    }
                }
            }
        };
        // 添加绘制面板到主面板
        mainPanel.add(drawPanel, BorderLayout.CENTER);
        return mainPanel;
    }
}
