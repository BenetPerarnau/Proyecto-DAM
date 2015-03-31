import Controler.ControladorROOT;
import Model.ConectorBBDD;

import View.Principal;

/**
 * 
 * @author Benet
 *
 */

public class Test {
/**
 * 
 * @param args
 * 
 * Punto de entrada de la aplicación.
 */
	public static void main(String[] args) {
				
		Principal vista=new Principal();
		ControladorROOT control=new ControladorROOT(vista);

	}

}
