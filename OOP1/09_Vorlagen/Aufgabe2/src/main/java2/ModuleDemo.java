import java.util.*;

public class ModuleDemo {
	public static void main(String[] args) {
		long start  =System.currentTimeMillis();
		// Sample code to illustrate how the CatalogueReader could be used:
		try (var reader = new CatalogueReader("LargeCatalogue.txt")) {
			Map<Integer, Module> modules = new HashMap<>();
			String[] names;
			while ((names = reader.readNextLine()) != null) {
				Set<String> reqs = new HashSet<>();
				for (int i = 1; i < names.length; i++) {
					reqs.add(names[i]);
				}
				Module m = new Module(names[0], new HashSet<>(), reqs);
				modules.put(m.hashCode(),m);
			}

			for(int number: modules.keySet()){
				Module currentModule = modules.get(number);
				for(String str:currentModule.getPrereqsstring()){
					currentModule.addreq(modules.get(str.hashCode()));
				}
			}

			
			int semstercounter = 0;
			while(!modules.keySet().isEmpty()){
				semstercounter++;
				ArrayList<Module> finishedmodules = new ArrayList<>();
				System.out.print("Semester: " + semstercounter);

				for(int number: modules.keySet()){
					Module currentModule = modules.get(number);
					if(currentModule.getPrereqs().isEmpty()){
						finishedmodules.add(currentModule);
						System.out.print(" "+currentModule.getName());
					}
				}
				for(Module m: finishedmodules){
					modules.remove(m.hashCode());
				}


				for(int number: modules.keySet()){
					Module currentModule = modules.get(number);
					for(Module finishedmodule: finishedmodules){
						currentModule.removereq(finishedmodule);
						modules.put(currentModule.hashCode(),currentModule);
					}
				}


				if(finishedmodules.isEmpty() && !modules.isEmpty()){
					System.out.println();
					System.out.println("Not possible to finish all modules");
					return;
				}

				System.out.println();

			}

		}
		System.out.println(System.currentTimeMillis() - start + " ms");



	}
}
