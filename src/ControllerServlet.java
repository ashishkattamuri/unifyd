

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.net.www.content.text.PlainTextInputStream;
/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String serviceURL= "https://www.random.org/integers/?num=4096&min=0&max=255&col=64&base=10&format=plain&rnd=new"; 
		URL myURL = new URL(serviceURL);
		HttpURLConnection myURLConnection = (HttpURLConnection)myURL.openConnection();
		myURLConnection.setRequestMethod("GET");
		myURLConnection.setUseCaches(false);
		myURLConnection.setDoInput(true);
		myURLConnection.setDoOutput(true);
		
		int code1 = myURLConnection.getResponseCode(); 
		//System.out.println("code:"+code1);
		
		//System.out.println(myURLConnection.getContent());
        Object obj= myURLConnection.getContent(); 
		
		PlainTextInputStream pis = (PlainTextInputStream)obj;
         
		int c;
		StringBuilder sb = new StringBuilder();
		int[][] randNumImg = new int[64][64];
		int row= 0;
		while ((c = pis.read()) != -1) { 
	    	
	    	
	    			
	    	if((char)c == '\n' ){
	    		String[] temp = sb.toString().split("\\t");
	    		for(int j=0;j< temp.length;j++){
	    			randNumImg[row][j] = Integer.parseInt(temp[j]);
	    		}
	    		row++;
	    		sb = new StringBuilder();
	    		continue;
	    		
	    	}
	    	
	    	sb.append(""+(char)c);
	    	//System.out.print((char) c); 
	        
	      
	   
	    } 
	    
		int width = 128;
	    int height = 128;
	     //create buffered image object img
	    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	     //file object
	   
	     //create random image pixel by pixel
	    for(int y = 0; y < height; y++){
	       for(int x = 0; x < width; x++){
	    	 int temp  = randNumImg[x/2][y/2];
	         int a = (int)(temp*256); //alpha
	         int r = (int)(temp*256); //red
	         int g = (int)(temp*256); //green
	         int b = (int)(temp*256); //blue
	 
	         int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
	 
	         img.setRGB(x, y, p);
	       }
	     }
//	    try{
//	        f = new File("/Users/ashishkattamuri/Documents/images/output.png");
//	        ImageIO.write(img, "png", f);
//	      }catch(IOException e){
//	        System.out.println("Error: " + e);
//	      }
		
		
	    response.setContentType("image/png");
	    ImageIO.write(img, "png", response.getOutputStream());
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
