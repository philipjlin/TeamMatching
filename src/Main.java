/**
 * Main Class
 * 
 * Sets up and runs the program.
 * 
 * @author Philip Lin
 *
 */
public class Main{

	public static void main(String[] args){
		
		Reader reader = new Reader();
		reader.readFile("playerteaminfo.txt");
		
		MatchingGenerator generator = new MatchingGenerator(reader.getTeamMap(), reader.getPlayerMap());
		generator.calculateTeamPreferenceLists();
		
		generator.printPlayerPreferenceLists();
		System.out.println("");
		generator.printTeamPreferenceLists();
		System.out.println("");
		
		generator.PPDA();
		
		generator.printFinalMatchings();
	}
}
