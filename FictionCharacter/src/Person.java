public class Person<T> {
    //定义属性
    private String name;//名字
    private int appearTimes;//出现次数
    private int span;//跨度
    private int intimacy;//联系紧密程度
    public Person(String name,int appearTimes,int span){
        this.name=name;
        this.appearTimes=appearTimes;
        this.span=span;
    }
    public Person(String name,int intimacy){
        this.name=name;
        this.intimacy=intimacy;
    }
  //get和set的方法
    public int getIntimacy() {
        return intimacy;
    }
    public String getName() {
        return name;
    }
    public int getAppearTimes() {
        return appearTimes;
    }
    public int getSpan() {
        return span;
    }

}