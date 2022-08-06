import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class GraphPanel extends JPanel {
	//Extra space before starting graph
	public int xtra;

	//X and Y axis names
	public String xAxis;
	public String yAxis;

	//Reference to car
	public Car car;

	//Points list
	public ArrayList<MyPoint> points;

        public GraphPanel(String xAxis, String yAxis, Car car){
        	//Points list specific to graph
		points = new ArrayList<>();
		this.car = car;
		
		//Axis names
		this.xAxis = xAxis;
		this.yAxis = yAxis;

		//Extra space before graphing for text
        	xtra = 20;
	}

        @Override
        protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
		
		//Draw background
		g2d.setColor(Constants.GREY_MED);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		//Grid and points
                drawGrid(g2d);
                drawPoints(g2d);

		if (yAxis.equals("Position")){
			drawTargetLine(g2d);
		}
        }

        //Draw all points in time.points
        public void drawPoints(Graphics2D g2d){
                g2d.setColor(Constants.POINT);
		int pointSize = 4;
                
		//Draw points
                for (int x = 0; x < points.size(); x++){
                        MyPoint point = points.get(x);
                        drawPoint(g2d, point.x * Constants.SPACE + xtra, getHeight() - point.y * Constants.SPACE - xtra, pointSize);
		}
        }	

	//Draw point on center
        public void drawPoint(Graphics2D g2d, double x, double y, int r) {
                g2d.fillOval((int)(x - r / 2), (int)(y - r / 2), r, r);
        }

        //Draw initial grid base
        public void drawGrid(Graphics2D g2d){
       		//Time text
		g2d.setColor(Constants.BLACK);
            	g2d.drawString(xAxis, xtra, getHeight()-5);

            	//Motor Output text
            	g2d.rotate(Math.toRadians(270));
            	g2d.drawString(yAxis, -getHeight()+xtra, xtra-5);
            	g2d.rotate(-Math.toRadians(270));

            	g2d.setColor(Constants.GREY_LOW);

            	//X-axis (up/down)
            	for (int x = 0; x <= getWidth(); x+=Constants.SPACE){
            	        g2d.drawLine(x+xtra, 0, x+xtra, getHeight()-xtra);
            	}	

		//Y-axis (left/right)
            	for (int x = getHeight()-xtra; x >= 0; x-=Constants.SPACE){
          		g2d.drawLine(0+xtra, x, getWidth() +xtra, x);
		}
        }

	//Draw target line if position vs. time graph
	public void drawTargetLine(Graphics2D g2d){
		int target_line = (int)((((double)getHeight()/Constants.SPACE)-car.target)*Constants.SPACE);

                g2d.setColor(Constants.TARGET);
                g2d.drawLine(0+xtra, target_line-xtra, getWidth() + xtra, target_line-xtra);
	}

	//Reset all points
	public void resetPoints(){
		points.clear();
	}
}