import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

// 存储每个人物在小说文本中的信息，包括姓名和出现次数
class PersonInfo {
    String name;
    int occurrences;

    PersonInfo(String name) {
        this.name = name;
        this.occurrences = 0;
    }

    // 增加出现次数的方法
    void incrementOccurrences() {
        occurrences++;
    }
}

// 小团体面板类
public class SmallGroupPanel {
    // 人物名字数组
    private final String[] nameArray = {
            "刘备", "魏延", "关羽", "张飞", "董卓", "吕布", "孙权", "周瑜", "司马懿", "曹操"
    };
    private CharacterReader getname = new CharacterReader();
    private String U;

    // 初始化，读取小说文本内容
    {
        try {
            U = getname.readNovel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 将文本分割成多个部分，每个部分的长度为 1000 个字符
    private String[] PartOfU = splitText(U, 1000);

    // 分割文本的方法
    private static String[] splitText(String text, int parts) {
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

    // 创建小团体面板的方法
    public JPanel SmallGroup_Panel() {
        // 创建面板
        JPanel smallGroupPanel = new JPanel(new BorderLayout());
        // 创建文本区域
        JTextArea textArea = new JTextArea();
        // 小团体计数器
        int smallGroupCount = 1;

        // 遍历每个文本片段
        for (int i = 0; i < 1000; i++) {
            // 用于存储每个人物在当前文本片段中的信息
            HashMap<String, PersonInfo> personMap = new HashMap<>();

            // 初始化每个人物的信息
            for (String name : nameArray) {
                personMap.put(name, new PersonInfo(name));
            }

            // 获取当前文本片段
            String textSegment = PartOfU[i];

            // 统计每个人物在当前文本片段中的出现次数
            for (String name : nameArray) {
                int count = 0;
                int fromIndex = 0;

                while (fromIndex < textSegment.length()) {
                    int index = textSegment.indexOf(name, fromIndex);
                    if (index == -1) {
                        break;
                    } else {
                        count++;
                        personMap.get(name).incrementOccurrences();
                        fromIndex = index + name.length();
                    }
                }
            }

            // 用于存储当前文本片段中出现次数超过 3 次的人物列表
            ArrayList<String> smallGroupList = new ArrayList<>();

            // 判断每个人物是否出现次数超过 3 次，如果是则添加到列表中
            for (String name : nameArray) {
                int occurrences = personMap.get(name).occurrences;
                if (occurrences >= 3) {
                    smallGroupList.add(name);
                }
            }

            // 如果列表中的人物数量超过 3，则将小团体信息添加到显示文本中
            if (smallGroupList.size() >= 3) {
                textArea.append("小团体" + smallGroupCount + ": " + smallGroupList.toString() + "\n");
                smallGroupCount++;
            }
        }

        // 设置 JTextArea 的属性
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        textArea.setForeground(new Color(40, 40, 40));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textArea.setBackground(new Color(141, 134, 227));  // 设置背景颜色

        // 创建 JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        // 将 JScrollPane 添加到面板
        smallGroupPanel.add(scrollPane, BorderLayout.CENTER);

        // 返回小团体面板
        return smallGroupPanel;
    }
}
