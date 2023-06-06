package tcss360.diybuilder;

import tcss360.diybuilder.SystemControl.Controller;
import tcss360.diybuilder.ui.DIYControl;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //read in json file
        Controller controller = new Controller();

        DIYControl jFrame = new DIYControl();
        jFrame.display();
    }
}
