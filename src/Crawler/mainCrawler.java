//Java에서의 크롤링 순서
// 1. 드라이버 설정(크롬 드라이버 사용 예정)
// 2. 크롬 드라이버를 담은 WebDriver 객체 생성
// 3. WebDriver을 크롬 브라우저라 생각하고, 원하는 URL의 원하는 태그에 접근 및 내용 얻기

// 즉, 접근한 URL에서 원하는 element를 받아올 때 WebElement 객체에 find한 Element값을 담는 것

package Crawler;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class mainCrawler {

    private static WebDriver driver;
    private static ChromeOptions options;

    //Driver Id, 경로(chrome driver.exe가 있는 path)
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:\\Users\\dnwls\\OneDrive\\바탕 화면\\셀레니움\\chromedriver_win32/chromedriver.exe";

	public static void main(String[] args) {
    	crawlerSet();
    	startCrawler();
    }
	
	public static void crawlerSet() {
		//WebDriver 경로 설정
        try {
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}

        //옵션 설정
        options = new ChromeOptions();
        options.addArguments("--start-maximized"); //전체화면으로 실행
        options.addArguments("--disable-popup-blocking"); //팝업무시
        options.addArguments("--disable-default-apps"); //무조건 크롬으로 시작
        //

        //WebDriver 객체 생성 = 하나의 브라우저 창
        driver = new ChromeDriver(options); //크롬 브라우저 실행
	}

    public static void startCrawler() {

		for (int i = 0, cnt=0 ; i < 100; i+=10, cnt=0) {

			String url_title = "https://www.ajou.ac.kr/kr/ajou/notice.do?mode=list&&articleLimit=10&article.offset="+i;

			// WebDriver을 해당 URL로 이동시킴
			try {
				// get page -> 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다
				driver.get(url_title);

			} catch (Exception e) {
				e.printStackTrace();
			}

			List<WebElement> Elements_num = driver.findElements(By.xpath("//*[contains(concat( \" \", @class, \" \" ), concat( \" \", \"b-num-box\", \" \" ))]"));
			List<WebElement> Elements_title = driver.findElements(By.xpath("//*[contains(concat( \" \", @class, \" \" ), concat( \" \", \"b-title-box\", \" \" ))]//a"));

			for (WebElement num : Elements_num) {
				String article_num = num.getText().trim();
				String title = Elements_title.get(cnt++).getText().trim();				
				System.out.println(article_num+" "+title);
			}
		}
    }
}  