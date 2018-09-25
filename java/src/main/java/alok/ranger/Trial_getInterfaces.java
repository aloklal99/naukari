package alok.ranger;

public class Trial_getInterfaces {

	public static void main(String[] args) {
		try {
//			Class<?> classIFirst = Class.forName("alok.madhan.Trial_getInterfaces$IFirst");
//			Class<?> classISecond = Class.forName("alok.madhan.Trial_getInterfaces$ISecond");
			for (String className : new String[] { "CFirst", "CSecond" } ) {
				System.out.println(className);

				System.out.println("calling getInterfaces()");
				Class<?> class1 = Class.forName("alok.madhan.Trial_getInterfaces$" + className);
				Class<?>[] interfaces = class1.getInterfaces();
				for (Class<?> anInterface : interfaces) {
					System.out.println("\t\t" + anInterface.getCanonicalName());
				}
				
				System.out.println("\tcalling instanceof");
				if (IFirst.class.isAssignableFrom(class1)) {
					System.out.println("\t\tImplements IFirst");
				}
				if (ISecond.class.isAssignableFrom(class1)) {
					System.out.println("\t\tImplements ISecond");
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}; 
	}

	static interface IFirst {}
	static interface ISecond {}
	
	static class CFirst implements IFirst, ISecond { }
	static class CSecond extends CFirst {}
}
