import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by bedaa on 03/04/2017.
 */
//It gets the winning and loosing streaks of the two teams, maybe we could get the odds, break out the loop if the streak ends etc.
public class MainScraper {
    public static void main(String[] args) throws Exception{

        Set<String> dates = new TreeSet<String>();
        BufferedReader in = new BufferedReader(new FileReader("Dates.csv"));

        String line;
        while((line = in.readLine()) != null)
        {
            dates.add(line);
        }
        in.close();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        String todaydate = (dateFormat.format(date));


        if (!dates.contains(todaydate)) {
        final Document page = Jsoup
                .connect("http://d.flashscore.com/x/feed/d_hh_8Iup6pKJ_en_1")
                .cookie("_ga","GA1.2.47011772.1485726144")
                .referrer("http://d.flashscore.com/x/feed/proxy-local")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
                .header("X-Fsign", "SW9D1eZo")
                .header("X-GeoIP", "1")
                .header("X-Requested-With", "XMLHttpRequest")
                .get();


      HockeyScraper scraper = new HockeyScraper();
        final PrintWriter out = new PrintWriter("newresults.csv");
       URLGetter urlgetter = new URLGetter();

       for (Object gameid : urlgetter.parseDates()) {
   //    System.out.print(gameid);
     //  System.out.print("----->>>>>    ");
     // System.out.println(scraper.srapingthestats("http://d.flashscore.com/x/feed/d_hh_" + gameid + "_en_1"));
      String stats = scraper.srapingthestats("http://d.flashscore.com/x/feed/d_hh_" + gameid + "_en_1") + "\n";
      out.write( stats );
           try {
               Files.write(Paths.get("HockeyStats.csv"), stats.getBytes(), StandardOpenOption.APPEND);
           }catch (IOException e) {
               //exception handling left as an exercise for the reader
           }
  }

       out.close();


        DateFormat dateFormat2 = new SimpleDateFormat("dd.MM.yyyy");
        Date date2 = new Date();
        System.out.println(dateFormat.format(date2));
        String StringInDate = dateFormat.format(date2) + "\n";
        try {
            Files.write(Paths.get("Dates.csv"), StringInDate.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        } }
        else {
                System.out.println("Today's Matches was already scraped");
        }

        System.exit(0);
    }

}
