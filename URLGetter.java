import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by bedaa on 03/04/2017.
 */



public class URLGetter {
public static void main(String[] args) throws Exception {
        URLGetter urlgetter = new URLGetter();
        Set<String> links = urlgetter.parseDates();
        links.forEach(System.out::println);

        }

    public List<String> parseLinks() {
     try {
         WebDriver driver;
         // should download geckodriver https://github.com/mozilla/geckodriver/releases and set according your local file
         System.setProperty("webdriver.firefox.marionette", "C:\\Users\\bedaa\\OneDrive\\Documents\\Do you have a chance\\Hockey\\geckodriver.exe");
         driver = new FirefoxDriver();
         String baseUrl = "http://www.flashscore.com/nhl/";

         driver.get(baseUrl);

         return driver.findElement(By.className("hockey"))
                 .findElements(By.tagName("tr"))
                 .stream()
                 .distinct()
                 .filter(we -> !we.getAttribute("id").isEmpty())
                 .map(we -> extractId(we.getAttribute("id")))
                 .collect(Collectors.toList());


     } catch (Exception e) {
         e.printStackTrace();
         return Collections.EMPTY_LIST;
        }
     }

    public Set<String> parseDates() {
        try {
            WebDriver driver;
            // should download geckodriver https://github.com/mozilla/geckodriver/releases and set according your local file
            System.setProperty("webdriver.firefox.marionette", "C:\\Users\\bedaa\\OneDrive\\Documents\\Do you have a chance\\Hockey\\geckodriver.exe");
            driver = new FirefoxDriver();
            String baseUrl = "http://www.flashscore.com/nhl/";

            driver.get(baseUrl);

            return driver.findElement(By.id("fs"))
                    .findElements(By.tagName("tr"))
                    .stream()
                    .distinct()
                    .filter(we -> !we.getAttribute("id").isEmpty())
                    .map(we -> extractId(we.getAttribute("id")))
                    .collect(Collectors.toSet());


        } catch (Exception e) {
            e.printStackTrace();
            return (Set<String>) Collections.EMPTY_LIST;
        }
    }



  //  private String createLink(String id) {
   // return String.format("http://www.flashscore.com/match/%s/#match-summary", extractId(id));
   // }

    private String extractId(String id) {
    if (id.contains("x_4_")) {
        id = id.replace("x_4_" , "");
    } else if (id.contains("g_4_")) {
        id = id.replace("g_4_" , "");
    }
    return id;
    }

}