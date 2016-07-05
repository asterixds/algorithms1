
import java.awt.Font;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KDTreeGraph {

    // delay in miliseconds (controls animation speed)
    private static final int DELAY = 10;
    private static final int N=100;
    
    public static void draw(String key, int x, int y) {
        

               // write status text
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(x+2, y,key );
        StdDraw.circle(x, y, 1);
       
    }
    
    public static void draw2(String key, int x1, int y1, int x2, int y2) {
        

               // write status text
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(x1, y1+1,key );
        StdDraw.line(x1, y1, x2,y2);
       
    }

    public static void main(String[] args) {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setXscale(0, N);
        StdDraw.setYscale(0, N);
        StdDraw.filledSquare(N,N,N);

        /*draw("A", 14, 6);
        draw("B", 2, 23);
        draw("C", 9, 76);
        draw("D",60,94);
        draw("E",18, 77);
        draw("F",4, 52);
        draw("G",83, 26);
        draw("H",87, 69);*/
        
       /* String[] info = {"A (18, 13)  ->  (19, 13)", 
                "B ( 9,  4)  ->  ( 9, 15)",
                "C (12, 12)  ->  (16, 12)",
                "D ( 0,  5)  ->  (14,  5)",
                "E ( 5,  2)  ->  (15,  2)",
                "F (13,  3)  ->  (13,  8)",
                "G ( 1, 16)  ->  ( 1, 17)",
                "H (10, 10)  ->  (17, 10)"};
        for(int i=0; i< info.length;i++) {
        Matcher matcher = Pattern.compile("(\\w+) \\(\\s?(\\d+),\\s+(\\d+)\\)  ->  \\(\\s?(\\d+),\\s+(\\d+)\\)").matcher(info[i]);
            if (matcher.find()) {
                    String key = matcher.group(1);
                    int x1 = Integer.parseInt(matcher.group(2));
                    int y1 = Integer.parseInt(matcher.group(3));
                    int x2 = Integer.parseInt(matcher.group(4));
                    int y2 = Integer.parseInt(matcher.group(5));
                    draw2(key,x1,y1,x2,y2);
            }
        }*/
        
        String[] info = {"A (0.48, 0.61)",
        "B (0.23, 0.79)","C (0.02, 0.58)","D (0.86, 0.45)","E (0.09, 0.71)",
        "F (0.33, 0.99)","G (0.34, 0.18)","H (0.26, 0.81)"}; 
        for(int i=0; i< info.length;i++) {
            Matcher matcher = Pattern.compile("(\\w+) \\((\\d+.\\d+),\\s+(\\d+.\\d+)\\)").matcher(info[i]);
                if (matcher.find()) {
                        String key = matcher.group(1);
                        int x1 = (int)(Double.parseDouble(matcher.group(2))*100);
                        int y1 = (int)(Double.parseDouble(matcher.group(3))*100);
                        draw(key,x1,y1);
                }
            }
        
                        
                        

  /*      
        draw2("A",16,  14,16,  18);
        draw2("B",7,9,7,  18);
                draw2("C",5,  13,15,  13);
                draw2("D",10,  1,10,  11);
                draw2("E", 18,  7,19,  7);
                draw2("F" , 6,  6, 17, 6);
                draw2("G",  2, 3,12, 3);//  [ horizontal ]
                draw2("H" ,8,  5, 11, 5);//  [ vertical   ]
                        
   */                     
                        
        
        StdDraw.show(0);
    }
}