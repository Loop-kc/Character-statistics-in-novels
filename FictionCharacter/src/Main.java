import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private ArrayList<Person> personList;  // 存储人物信息的列表
    private String[] nameArray;  // 人物名字数组
    private int[][] intimacyMatrix;  // 亲密度矩阵


    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.init();
        main.output();
    }

    public void init() throws IOException {
        // 定义人物名字数组
        nameArray = new String[]{
                "刘备", "魏延", "关羽", "张飞", "董卓", "吕布", "孙权", "周瑜", "司马懿", "曹操"
        };

        // 初始化存储人物信息的列表
        personList = new ArrayList<>();
        CharacterReader characterReader = new CharacterReader();

        // 读取人物信息并加入列表
        for (String name : nameArray) {
            int times = characterReader.appearTimes(name);
            int span = characterReader.appearSpan(name);
            personList.add(new Person(name, times, span));
        }

        // 初始化亲密度矩阵并计算亲密度
        intimacyMatrix = new int[nameArray.length][nameArray.length];
        calculateIntimacy(characterReader);
    }

    private void calculateIntimacy(CharacterReader characterReader) throws IOException {
        for (int i = 0; i < nameArray.length; ++i) {
            for (int j = i + 1; j < nameArray.length; ++j) {
                int intimacyValue = characterReader.intimacy(nameArray[i], nameArray[j]);
                intimacyMatrix[i][j] = intimacyValue;
                intimacyMatrix[j][i] = intimacyValue;  // 对称赋值
            }
        }
    }

    public void output() throws IOException {
        NovelCharacterStatisticsUI ui = new NovelCharacterStatisticsUI(personList, nameArray, intimacyMatrix);
        ui.mainUI();
    }
}
