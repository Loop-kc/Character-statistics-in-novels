import java.io.IOException;
import java.util.ArrayList;

public class IntimacySort {
    private String[] nameArray;
    private String personName_1;
    private ArrayList<Person> per;
    public IntimacySort(){//实例化

    }
    /**
     * 构造函数传参
     * @param personName_1 文本框中输入的名字
     * @param nameArray 存储人物名字的 数组
     */
    public IntimacySort(String[] nameArray,String personName_1){
        this.personName_1=personName_1;
        this.nameArray=nameArray;
    }
    /**
     * 求任意两个人物的亲密度（即当一个人出现时判断其他人也在指定的区域内出现的次数）
     * @param //personName_1 其中一个人物的名字
     * @throws IOException 抛出可能存在的异常
     */
    public ArrayList<Person> intimacy() throws IOException{
        //创建一个ArrayList来存储数据
        per = new ArrayList<Person>();
        //得到输入的字符串
        CharacterReader getname=new CharacterReader();
        String result =getname.readNovel();
        //定义数组来存储本人物与其他人物的亲密度
        int[]count=new int[nameArray.length];

        for(int i=0;i<nameArray.length;i++){
            int fromIndex=100;//开始索引
            if(personName_1.equals(nameArray[i])){
                count[i]=0;
            }else{
                while(fromIndex+100<result.length()){
                    //找到人物在小说中指定位置出现的索引
                    int index= result.indexOf(personName_1, fromIndex);
                    if(index==-1)break;//找不到就退出
                    else{
                        String str = result.substring(index-100, index+100);
                        if(str.contains(nameArray[i])){
                            count[i]++;//人物出现一次就加1
                        }
                        fromIndex= index+100;//改变索引再继续遍历
                    }

                }
            }

            per.add(new Person(nameArray[i],count[i]));//加入到ArrayList中
        }
        return per;
    }
}

