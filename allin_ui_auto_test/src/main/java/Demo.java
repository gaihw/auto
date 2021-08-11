import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Demo {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver","libs\\chromedriver92.exe");
        //实例化一个Chrome浏览器的实例
        WebDriver driver = new ChromeDriver();
        //设置打开的浏览器窗口最大化
        driver.manage().window().maximize();
        //使用get()打开一个网站
        driver.get("http://dg-test.allintechinc.com/login");
        //getTitle()获取当前页面的title，用System.out.println()打印在控制台
        System.out.println("当前打开页面的标题是： "+ driver.getTitle());
        //关闭浏览器
//        driver.quit();
    }
}
