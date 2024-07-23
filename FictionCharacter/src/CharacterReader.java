import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CharacterReader {
    private static final String NOVEL_FILE = "text1.txt";
    // 人物别名映射表
    private static final Map<String, String> CHARACTER_ALIASES = createCharacterAliases();

    /**
     * 读取指定文件名的内容，作为字符串返回
     *
     * @return 读到的内容作为字符串返回
     * @throws IOException 抛出可能存在的异常
     */
    public String readNovel() throws IOException {
        try (FileInputStream ins = new FileInputStream(NOVEL_FILE)) {
            byte[] contentByte = new byte[ins.available()];
            ins.read(contentByte);
            String novelContent = new String(contentByte);

            // 将别名转换为原名
            for (Map.Entry<String, String> entry : CHARACTER_ALIASES.entrySet()) {
                novelContent = novelContent.replaceAll(entry.getKey(), entry.getValue());
            }

            return novelContent;
        }
    }

    /**
     * 查找人物出现的次数
     *
     * @param personName 人物名字
     * @return 返回人物出现的次数
     * @throws IOException 抛出可能存在的异常
     */
    public int appearTimes(String personName) throws IOException {
        String result = readNovel();
        int count = 0, fromIndex = 0;

        // 将别名转换为原名
        String originalName = getOriginalName(personName);

        // 循环查找人物出现的次数
        while ((fromIndex = result.indexOf(originalName, fromIndex)) != -1) {
            count++;
            fromIndex += originalName.length();
        }
        return count;
    }

    /**
     * 查找人物第一次和最后一次出现的跨度
     *
     * @param personName 人物名字
     * @return 返回人物出现的首尾跨度
     * @throws IOException 抛出可能存在的异常
     */
    public int appearSpan(String personName) throws IOException {
        String result = readNovel();

        // 将别名转换为原名
        String originalName = getOriginalName(personName);

        int firstIndex = result.indexOf(originalName);
        int lastIndex = result.lastIndexOf(originalName);

        // 计算首尾跨度
        return lastIndex - firstIndex;
    }

    /**
     * 求任意两个人物的亲密度
     *
     * @param personName_1 其中一个人物的名字
     * @param personName_2 另一个人物的名字
     * @return 返回亲密度的整数值
     * @throws IOException 抛出可能存在的异常
     */
    public int intimacy(String personName_1, String personName_2) throws IOException {
        String result = readNovel();
        int count = 0, fromIndex = 100;

        // 将别名转换为原名
        String originalName1 = getOriginalName(personName_1);
        String originalName2 = getOriginalName(personName_2);

        // 遍历小说文本，计算两个人物的亲密度
        while (fromIndex + 100 < result.length()) {
            int index = result.indexOf(originalName1, fromIndex);

            if (index == -1) {
                break;
            } else {
                // 截取指定位置附近的文本
                String str = result.substring(Math.max(0, index - 100), Math.min(result.length(), index + 100));
                if (str.contains(originalName2)) {
                    count++;
                }
                fromIndex = index + 100;
            }
        }
        return count;
    }

    // 创建人物别名映射表
    private static Map<String, String> createCharacterAliases() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("关公", "关羽");
        aliases.put("刘皇叔","刘备");
        aliases.put("张翼德","张飞");
        aliases.put("吕奉先","吕布");
        aliases.put("仲谋","孙权");
        aliases.put("公瑾","周瑜");
        aliases.put("司马仲达","司马懿");
        aliases.put("文长","魏延");
        aliases.put("仲颖","董卓");
        aliases.put("孟德","曹操");
        return aliases;
    }
    // 获取人物的原名
    private String getOriginalName(String alias) {
        return CHARACTER_ALIASES.getOrDefault(alias, alias);
    }
}
