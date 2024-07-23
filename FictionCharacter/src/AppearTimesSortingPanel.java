import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.renderer.category.BarRenderer;

public class AppearTimesSortingPanel extends JPanel{
    //创建一个面板对象
    public static JPanel AppearTimesSorting_Panel() throws IOException {
        String[] nameArray=new String[]{
                "刘备", "魏延", "关羽", "张飞", "董卓", "吕布", "孙权", "周瑜", "司马懿", "曹操"
        };
        //创建一个ArrayList来存储数据
        ArrayList<Person> list = new ArrayList<Person>();
        //实例化对象并调用方法
        CharacterReader getname = new CharacterReader();
        //得到出现的次数与跨度
        for(int i=0;i<nameArray.length;i++){
            int times=getname.appearTimes(nameArray[i]);//出现的次数
            int span=getname.appearSpan(nameArray[i]);//跨度
            list.add(new Person(nameArray[i],times,span));//加入到ArrayList中
        }

        // 对 list 按照出现次数进行排序
        Collections.sort(list, new Comparator<Person>() {
            public int compare(Person one, Person another) {
                return one.getAppearTimes() - another.getAppearTimes();
            }
        });

        CategoryDataset dataset = createDataset(list);


        // 创建 JFreeChart 柱状图
        JFreeChart chart = ChartFactory.createBarChart(
                "人物出现次数统计图",
                "人物",
                "出现次数",
                dataset,
                PlotOrientation.VERTICAL,  // 垂直方向
                true,
                true,
                false);

        // 设置字体
        Font titleFont = new Font("黑体", Font.BOLD, 20);
        Font labelFont = new Font("黑体", Font.BOLD, 18);
        chart.getTitle().setFont(titleFont);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis axis = plot.getDomainAxis();
        axis.setTickLabelFont(labelFont);

        // 设置标签角度，防止中文标签乱码
        axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        axis.setMaximumCategoryLabelWidthRatio(0.6f);

        // 获取渲染器
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // 设置柱状图显示数值
        StandardCategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
        renderer.setDefaultItemLabelGenerator(generator);
        renderer.setDefaultItemLabelsVisible(true);

        // 设置横轴标签字体
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(labelFont);
        domainAxis.setLabelFont(labelFont);

        // 设置标签角度，防止中文标签乱码
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);

        // 设置纵轴标签的字体
        ValueAxis yAxis = plot.getRangeAxis();
        yAxis.setTickLabelFont(labelFont);
        yAxis.setLabelFont(labelFont);


        // 创建 ChartPanel 并将其添加到面板上
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 700));
        JPanel AppearTimesSortingPanel = new JPanel();
        AppearTimesSortingPanel.add(chartPanel);

        return AppearTimesSortingPanel;
    }
    private static CategoryDataset createDataset(List<Person> list) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Person person : list) {
            dataset.addValue(person.getAppearTimes(), "出现次数", person.getName());
        }
        return dataset;
    }
}