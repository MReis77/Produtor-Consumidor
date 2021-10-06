package Package;
public class Main {
	public static volatile Object lock = new Object();
	public static volatile int produtos = 0;
	
	public static void main(String[] args) {
		Produtor p1 = new Produtor(1);
		p1.start();
		Consumidor c1 = new Consumidor(1);
		c1.start();
	}
}

class Produtor extends Thread {
	int id = 0;
	Produtor(int novoId) {
		this.id = novoId;
	}
	public void run() {
		for(int i = 0; i < 100; i ++ ) {			
			synchronized( Main.lock )
			{	
				if(Main.produtos < 100) {
					Main.produtos = Main.produtos + 1;
					/*
					 * 
					 * */
					Main.lock.notify();
				} else {
         				 try {
            					System.out.println("#~~ Produtor " + id + "-" + i + ";\t Cansadão, xô ir embora");
						Main.lock.wait();
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
        }
					System.out.println("#<- Produtor " + id + "-" + i +";\t\t estoque = " + Main.produtos);
			}
		}
	}
}
class Consumidor extends Thread {
	int id = 0;
	Consumidor(int novoId) {
		this.id = novoId;
	}
	public void run() {
		for(int i = 0; i < 100; i++) {	
			
			synchronized( Main.lock )
			{	
				
				if (Main.produtos <= 0) {
					System.out.println("#~~ Consumidor " + id + "-" + i + "; Catapimbas, como assim acabou o estoque de bolacha?");
					
					try {
						Main.lock.wait();
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
          				System.out.println("#~~ Consumidor " + id + "-" + i + "; Hello my friends, I return.");
				}
				
				Main.produtos = Main.produtos - 1;
				System.out.println("#-> Consumidor " + id + "-" + i +";\t estoque = " + Main.produtos);
        			Main.lock.notify();
			}
		
		}
	}
}