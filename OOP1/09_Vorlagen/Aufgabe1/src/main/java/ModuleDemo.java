import java.util.ArrayList;

public class ModuleDemo {
	public static void main(String[] args) {
		// Sample code to illustrate how the CatalogueReader could be used:
		try (var reader = new CatalogueReader("StudyCatalogue.txt")) {
			ArrayList<Module> modules = new ArrayList<Module>();
			String[] names;
			while ((names = reader.readNextLine()) != null) {
				ArrayList<String> reqs = new ArrayList<String>();
				for (int i = 1; i < names.length; i++) {
					reqs.add(names[i]);
				}
				modules.add(new Module(names[0], reqs));
			}
			int semstercounter = 0;
			while(!modules.isEmpty()){
				semstercounter++;
				ArrayList<Module> finishedmodules = new ArrayList<Module>();
				System.out.print("Semester: " + semstercounter);
				var it = modules.iterator();
				while(it.hasNext()){
					Module m = it.next();
					if(m.getPrereqs().isEmpty()){
						System.out.print(" "+m.getName());
						finishedmodules.add(m);
						it.remove();
					}
				}
				if(finishedmodules.isEmpty() && !modules.isEmpty()){
					System.out.println();
					System.out.println("Not possible to finish all modules");
					return;
				}
				for(Module m: finishedmodules){
					for(Module module: modules){
						module.removereq(m.getName());
					}
				}
				System.out.println();

			}

		}
	}
}
