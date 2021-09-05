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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 
public class mainCrawler {
 
    //WebDriver
    private static WebDriver driver;
    private static ChromeOptions options;
   
    //Driver Id, 경로(chrome driver.exe가 있는 path)
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:\\Users\\dnwls\\OneDrive\\바탕 화면\\셀레니움\\chromedriver_win32/chromedriver.exe";
	
	public static void main(String[] args) throws InterruptedException {
    	crawlerSet();
    	portal_login();
    	//mail_login();
    	//startAnnouncementCrawler();
    	//startMailCrawler();
    	startBbCrawler();
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
    
	public static void portal_login() throws InterruptedException {		
		String main_url = "https://mportal.ajou.ac.kr/main.do";
		driver.get(main_url);
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//*[contains(concat( \" \", @class, \" \" ), concat( \" \", \"nb-h-icon-signIn\", \" \" ))]//a")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("userId")).sendKeys("yourId");
		driver.findElement(By.id("password")).sendKeys("yourPw");
		
		driver.findElement(By.id("loginSubmit")).click();
	}
	
	public static void mail_login() throws InterruptedException {
		driver.findElement(By.xpath("//*[(@id = \"P031\")]//*[contains(concat( \" \", @class, \" \" ), concat( \" \", \"nb-p-iconHone\", \" \" ))]//a")).click();
	}
	
	//공지사항 크롤링
    public static void startAnnouncementCrawler() throws InterruptedException{
          	
		for (int i = 0, cnt=0 ; i < 100; i+=10, cnt=0) {
			
			String url_title = "https://www.ajou.ac.kr/kr/ajou/notice.do?mode=list&&articleLimit=10&article.offset="+i;
			
			// WebDriver을 해당 URL로 이동시킴
			try {
				// get page -> 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다
				driver.get(url_title);
				Thread.sleep(1000);

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
	
    //아주 메일 크롤링
	public static void startMailCrawler() throws InterruptedException{
		/* 구글 로그인 우회
		driver.get("https://stackoverflow.com/"); 
		Thread.sleep(500); driver.findElement(By.xpath("//*[contains(concat( \" \", @class, \" \" ), concat( \" \", \"py8\", \" \" )) and contains(concat( \" \", @class, \" \" ), concat( \" \", \"js-gps-track\", \" \" ))]")).click(); 
		Thread.sleep(500); driver.findElement(By.xpath("//*[contains(concat( \" \", @class, \" \" ), concat( \" \", \"s-btn__google\", \" \" ))]")).click();
		Thread.sleep(500); 
		driver.findElement(By.id("identifierId")).sendKeys("yourId");
		driver.findElement(By.id("password")).sendKeys("yourPw");

		driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/div/button/div[2]")).click();
		Thread.sleep(500);
		
		driver.get("https://mail.google.com/mail/u/0/#inbox");
		*/

		int num=1;
		
		for (int i = 0, cnt=0 ; i < 5; i++, cnt=0) {
			
			String url_title = "https://mail.google.com/mail/u/0/#inbox/p2="+i;
			
			// WebDriver을 해당 URL로 이동시킴
			try {
				// get page -> 브라우저에서 url을 주소창에 넣은 후 request 한 것과 같다
				driver.get(url_title);
				Thread.sleep(1000);

			} catch (Exception e) {
				e.printStackTrace();
			}

			List<WebElement> Elements_Participants = driver.findElements(By.xpath("//*[contains(concat( \" \", @class, \" \" ), concat( \" \", \"yW\", \" \" ))]"));
			List<WebElement> Elements_title = driver.findElements(By.xpath("//*[contains(concat( \" \", @class, \" \" ), concat( \" \", \"bog\", \" \" ))]//span"));
			
			for (WebElement title : Elements_title) {
				String mail_title = title.getText().trim();
				String mail_participants = Elements_Participants.get(cnt++).getText().trim();				
				System.out.println((num++)+" ("+mail_participants+") "+mail_title);
			}
		}
    }
	
	//bb 크롤링
    public static void startBbCrawler() throws InterruptedException{
    	//driver.findElement(By.xpath("//*[contains(concat( \" \", @class, \" \" ), concat( \" \", \"hover\", \" \" ))]")).click();	
    	driver.get("https://eclass2.ajou.ac.kr/ultra/course");
    	Thread.sleep(500);
    	
    	List<WebElement> Elements = driver.findElements(By.className("js-course-title-element ellipsis"));
			
    	int cnt=1;
    	
		for (WebElement e : Elements) {
			String title = e.getText().trim();				
			System.out.println((cnt++)+" "+title);
		}
    }
}