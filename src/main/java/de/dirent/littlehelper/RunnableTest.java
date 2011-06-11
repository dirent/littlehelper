package de.dirent.littlehelper;



public class RunnableTest implements Runnable {

	public static void main( String[] args ) throws Exception {

		System.out.print( "Press <SPACE> to terminate ..." );
		RunnableTest runnable = new RunnableTest();
		Thread t = new Thread( runnable );
		
		t.start();
		
		while( System.in.read() != ' ' ) ;
		
		runnable.setSpacePressed();
	}
	
	
	private boolean spacePressed = false;
	
	public void setSpacePressed() {
		
		spacePressed = true;
	}
	
	
	@Override
	public void run() {

		System.out.println( "Thread started" );
		
		while( !spacePressed ) {
			System.out.println( ""+System.currentTimeMillis() );
		}
		
		System.out.println( "Space pressed" );
	}

}
