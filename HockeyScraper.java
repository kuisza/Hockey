import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by bedaa on 03/04/2017.
 */
public class HockeyScraper {

    //URL: http://www.eredmenyek.com/merkozes/nBPiuHF3/#egymas-elleni;overall
    //Selector: td.winLose a
    //_ga=GA1.2.47011772.1485726144
public String srapingthestats(String connect) throws Exception{
    final Document page = Jsoup
            .connect(connect)
            .cookie("_ga","GA1.2.47011772.1485726144")
            .referrer("http://d.flashscore.com/x/feed/proxy-local")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
            .header("X-Fsign", "SW9D1eZo")
            .header("X-GeoIP", "1")
            .header("X-Requested-With", "XMLHttpRequest")
            .get();

    int T1Won = 0;
    int T2Won = 0;
    int T1Lost = 0;
    int T2Lost = 0;
    String Winner = null;

    String firstgame = "Empty";
    String secondgame = "Empty";
    boolean winstreak = true;
    boolean lossstreak = true;
    for (Element game : page.getElementsByClass("head_to_head h2h_home").first().getElementsByTag("tr")) {
        //System.out.println("----------------------------------------------------");
        //System.out.println(game.text());

        Elements list = game.getElementsByClass("form-bg-last");

        if (!list.isEmpty()) {
            if (firstgame.equals("Empty")) {
                if(list.first().attr("title").startsWith("Draw/")) {
                    firstgame = list.first().attr("title").substring(5);
                }
                else {
                    firstgame = list.first().attr("title");
                }

                if (firstgame.equals("Win")){
                    Winner = "T1";
                }
                else if (firstgame.equals("Loss")) {
                    Winner = "T2";
                }
            }
            else if (secondgame.equals("Empty")) {
                secondgame = list.first().attr("title");
                if (secondgame.startsWith("Draw/")) {
                    secondgame = secondgame.substring(5);
                }
                if (secondgame.equals("Win")) {
                    T1Won++;
                }
                else if (secondgame.equals("Loss"))  {
                    T1Lost++;
                }

            }
            else {
                String currentgame = list.first().attr("title");
                if (currentgame.startsWith("Draw/")) {
                    currentgame = currentgame.substring(5);
                }

                if (secondgame.equals("Win")) {
                    if (!currentgame.equals("Win")) {
                        winstreak = false;
                    }
                    if(winstreak) {
                        T1Won++;
                    }
                }
                else if (secondgame.equals("Loss")) {
                    if (!currentgame.equals("Loss")) {
                        lossstreak = false;
                    }
                    if(lossstreak) {
                        T1Lost++;
                    }
                }
            }
        }

    }

    firstgame = "Empty";
    secondgame = "Empty";
    winstreak = true;
    lossstreak = true;
    for (Element game : page.getElementsByClass("head_to_head h2h_away").first().getElementsByTag("tr")) {
        //System.out.println("----------------------------------------------------");
        //System.out.println(game.text());

        Elements list = game.getElementsByClass("form-bg-last");

        if (!list.isEmpty()) {
            if (firstgame.equals("Empty")) {
                if(list.first().attr("title").startsWith("Draw/")) {
                    firstgame = list.first().attr("title").substring(5);
                }
                else {
                    firstgame = list.first().attr("title");
                }
                if (firstgame.equals("Win")){
                    Winner = "T2";
                }
            }
            else if (secondgame.equals("Empty")) {
                secondgame = list.first().attr("title");
                if (secondgame.startsWith("Draw/")) {
                    secondgame = secondgame.substring(5);
                }
                if (secondgame.equals("Win")) {
                    T2Won++;
                }
                else if (secondgame.equals("Loss"))  {
                    T2Lost++;
                }

            }
            else {
                String currentgame = list.first().attr("title");
                if (currentgame.startsWith("Draw/")) {
                    currentgame = currentgame.substring(5);
                }

                if (secondgame.equals("Win")) {
                    if (!currentgame.equals("Win")) {
                        winstreak = false;
                    }
                    if(winstreak) {
                        T2Won++;
                    }
                }
                else if (secondgame.equals("Loss")) {
                    if (!currentgame.equals("Loss")) {
                        lossstreak = false;
                    }
                    if(lossstreak) {
                        T2Lost++;
                    }
                }
            }
        }

    }
   return T1Won + ";" + T2Won + ";" + T1Lost + ";" + T2Lost + ";" + Winner;

}
}
