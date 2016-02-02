package test;
import io.IOMachine;
import turing.*;

public class testIO {
	public static void main(String[] args) {
		IOMachine machine = new IOMachine();
		machine.parseProgram("test_program.tur");
		try {
			for (Transition now : machine.getInstructions()) {
				System.out.println(now.getDepart()+" "+now.getSymbole()+" "+now.getArrive()+" "+now.getLR()+" "+now.getReec());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
