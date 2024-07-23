import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;


public class AppearSpanSortingPanel extends JPanel {

    public static JPanel AppearSpanSorting_Panel() throws IOException {
        String[] nameArray = new String[]{
                "刘备", "魏延", "关羽", "张飞", "董卓", "吕布", "孙权", "周瑜", "司马懿", "曹操"
        };

        ArrayList<Person> list = new ArrayList<>();
        CharacterReader getname = new CharacterReader();
        String U = getname.readNovel();

        for (int i = 0; i < nameArray.length; i++) {
            int times = getname.appearTimes(nameArray[i]);
            int span = getname.appearSpan(nameArray[i]);
            list.add(new Person(nameArray[i], times, span));
        }
        Collections.sort(list, Comparator.comparingInt(Person::getSpan));
        CategoryDataset dataset = createDataset(list);

        JFreeChart chart = ChartFactory.createBarChart(
                "人物篇幅跨度统计图",
                "人物",
                "篇幅跨度",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        Font titleFont = new Font("黑体", Font.BOLD, 20);
        Font labelFont = new Font("黑体", Font.BOLD, 15);
        chart.getTitle().setFont(titleFont);

        CategoryAxis domainAxis1 = new CategoryAxis("人物");
        NumberAxis rangeAxis1 = new NumberAxis("篇幅跨度");
        NumberAxis rangeAxis2 = new NumberAxis("相对百分比");
        rangeAxis2.setNumberFormatOverride(new DecimalFormat("0%"));
        rangeAxis2.setRange(0, 1);

        BarRenderer renderer = new BarRenderer();
        renderer.setDefaultItemLabelsVisible(true);  // 启用数据标签显示
        //  renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator()); //在柱子上显示相应信息

        CategoryDataset dataset1 = createPercentageDataset(list, U);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setDataset(0, dataset1);
        plot.setDomainAxis(0, domainAxis1);

        // 设置自定义颜色或渲染器
        // 1. 使用自定义颜色
        renderer.setSeriesPaint(0,new Color(5, 189, 248));
        //renderer.setSeriesPaint(1,new Color(5, 189, 248));
        // 2. 使用自定义渲染器
        // LayeredBarRenderer customRenderer = new LayeredBarRenderer();
        //  plot.setRenderer(1, customRenderer);

        // 设置横轴标签字体
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(labelFont);
        domainAxis.setLabelFont(labelFont);

        // 设置标签角度，防止中文标签乱码
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.toRadians(70))); // 70度倾斜
        domainAxis.setMaximumCategoryLabelWidthRatio(0.35f);

     /*   // 设置柱状图显示数值
        StandardCategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
        renderer.setDefaultItemLabelGenerator(generator);
        renderer.setDefaultItemLabelsVisible(true);*/

        plot.setRangeAxis(0, rangeAxis1);
        plot.setRangeAxis(1, rangeAxis2);
        plot.setRenderer(0, renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 700));
        JPanel AppearSpanSortingPanel = new JPanel();
        AppearSpanSortingPanel.add(chartPanel);

        return AppearSpanSortingPanel;
    }

    private static CategoryDataset createDataset(List<Person> list) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Person person : list) {
            dataset.addValue(person.getSpan(), "篇幅跨度", person.getName());
        }
        return dataset;
    }

    private static CategoryDataset createPercentageDataset(List<Person> list, String U) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Person person : list) {
            double startPercentage = (U.indexOf(person.getName()) / (double) U.length()) * 100;
            double endPercentage = (U.lastIndexOf(person.getName()) / (double) U.length()) * 100;

            // 使用 DecimalFormat 进行格式化,保留两位小数
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String formattedStartPercentage = decimalFormat.format(startPercentage);
            String formattedEndPercentage = decimalFormat.format(endPercentage);

            dataset.addValue(
                    person.getSpan(),
                    "篇幅跨度",
                    person.getName() + " (" + formattedStartPercentage + "% to " + formattedEndPercentage + "%)"
            );
        }
        return dataset;
    }
}