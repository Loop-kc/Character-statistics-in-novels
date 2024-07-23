import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class NovelCharacterStatisticsUI extends JFrame {
    private CardLayout card;
    private JPanel panel;
    private ArrayList<Person> person;
    private String[] nameArray;
    private int[][] intimacy;

    /**
     * 构造方法，用于初始化对象和传递参数
     * @param person ArrayList中的元素对象
     * @param nameArray 用于存储人物名字的数组
     * @param intimacy 用于存储亲密度的数组
     */
    public NovelCharacterStatisticsUI(ArrayList<Person> person, String[] nameArray, int[][] intimacy) {
        this.person = person;
        this.nameArray = nameArray;
        this.intimacy = intimacy;
    }

    /**
     * 主界面的方法
     * @throws IOException
     */
    public void mainUI() throws IOException {
        card = new CardLayout(0, 0);
        panel = new JPanel(card);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(177, 230, 173));

        // 创建按钮和面板的名称映射
        Map<String, String> buttonPanelMapping = new HashMap<>();
        buttonPanelMapping.put("出现次数排序", "出现次数柱状图");
        buttonPanelMapping.put("首尾跨度排序", "篇幅跨度柱状图");
        buttonPanelMapping.put("可视化图", "可视化图");
        buttonPanelMapping.put("联系紧密程度", "联系紧密程度");
        buttonPanelMapping.put("小团体", "小团体");

        // 创建按钮和文本框
        JButton[] buttons = new JButton[buttonPanelMapping.size()];
        for (String buttonText : buttonPanelMapping.keySet()) {
            JButton button = new JButton(buttonText);
            button.addActionListener(createButtonListener(buttonPanelMapping.get(buttonText)));
            buttonPanel.add(button);
        }

        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(100, 30));
        field.setText("刘备");

        JButton searchButton = new JButton("搜索");
        searchButton.addActionListener(e -> {
            try {
                panel.add(new IntimacyPanel().Intimacy_Panel(field.getText()), "亲密度排序");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            card.show(panel, "亲密度排序");
        });

        buttonPanel.add(field);
        buttonPanel.add(searchButton);

        // 添加按钮和面板
        panel.add(new AppearTimesSortingPanel().AppearTimesSorting_Panel(), "出现次数柱状图");
        panel.add(new AppearSpanSortingPanel().AppearSpanSorting_Panel(), "篇幅跨度柱状图");
        panel.add(new LocationPanel().Location_Panel(), "可视化图");
        panel.add(new SearchPanel().Search_Panel(intimacy), "联系紧密程度");
        panel.add(new SmallGroupPanel().SmallGroup_Panel(), "小团体");

        this.setTitle("小说人物分析");
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(panel);
        this.getContentPane().add(buttonPanel, BorderLayout.NORTH);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * 创建按钮监听器
     * @param panelName 要切换到的面板名称
     * @return ActionListener实例
     */
    private ActionListener createButtonListener(String panelName) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(panel, panelName);
            }
        };
    }
}

/*将按钮和相应的面板的名称集中管理，以便于统一管理。
使用Map来存储按钮和面板的关系，避免大量的if-else语句。*/