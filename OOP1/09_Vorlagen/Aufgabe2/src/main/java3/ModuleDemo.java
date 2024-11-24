import java.util.*;

public class ModuleDemo {
	public static void main(String[] args) {
		long start  =System.currentTimeMillis();
		// Sample code to illustrate how the CatalogueReader could be used:
		try (var reader = new CatalogueReader("LargeCatalogue.txt")) {
			Map<Integer, Module> modules = new HashMap<>();
			String[] names;
			//initialize Modules
			while ((names = reader.readNextLine()) != null) {
				Set<String> reqs = new HashSet<>();
				for (int i = 1; i < names.length; i++) {
					reqs.add(names[i]);
				}
				Module m = new Module(names[0],new HashSet<>(), new HashSet<>(), reqs);
				modules.put(m.hashCode(),m);
			}

			//populate Modules
			for(int number: modules.keySet()){
				Module currentModule = modules.get(number);
				for(String str:currentModule.getPrereqsstring()){
					modules.put(str.hashCode(),
							modules.get(str.hashCode()).addFutureReq(currentModule)
					);
					modules.put(currentModule.hashCode(),
							modules.get(currentModule.hashCode()).addreq(modules.get(str.hashCode()))
					);

				}
			}


			int semstercounter = 0;
			//loop until no more modules left
			while(!modules.keySet().isEmpty()){
				semstercounter++;
				ArrayList<Module> finishedModules = new ArrayList<>();
				System.out.print("Semester: " + semstercounter);

				for(int number: modules.keySet()){
					Module currentModule = modules.get(number);
					//Loop through every module and check if it is already ready to be done
					if(currentModule.getPrereqs().isEmpty()){
						//remove in all future reqs the req of this module
						finishedModules.add(currentModule);
						System.out.print(" "+currentModule.getName());

					}
				}
				for(Module m: finishedModules){
					modules.remove(m.hashCode());
					for(Module inRemoveModule: m.getFuturereq()){
						modules.put(inRemoveModule.hashCode(),
								modules.get(inRemoveModule.hashCode()).removereq(m)
						);
					}
				}

				if(finishedModules.isEmpty() && !modules.isEmpty()){
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
