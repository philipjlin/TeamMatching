import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Reader Class
 * 
 * Takes a formatted input lists of players and teams and reads lines to get information
 * and generates objects to represent every team and player.
 * 
 * Player and team objects are stored in maps.
 * 
 * @author Philip Lin
 *
 */
public class Reader{

	//Maps used to store player and team objects with name keys
	Map<String, Team> teamMap = new HashMap<String, Team>();
	Map<String, Player> playerMap = new HashMap<String, Player>();
	
	/**
	 * Reads a text file and extracts information about all players.
	 * 
	 * Team and player objects created with the information from the files are stored in maps.
	 * 
	 * @param filename name of text file to read
	 */
	public void readFile(String filename){
				
		try{
			
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader); 
			
			String line;
			int lineCount = 0;
			int playerCount = 0;
			int teamCount = 0;
			
			//Read the next line in the triangle file
			//Every line contains information about a new team or player
			while( (line = bufferedReader.readLine()) != null){

				lineCount++;
				
				//Split the line into an array
				String[] stringValues = line.split("\\s+");

				//Create a team object and add information
				if(stringValues[0].equals("Team")){
					
					teamCount++;
					
					teamMap.put(stringValues[1], new Team());
					teamMap.get(stringValues[1]).name = stringValues[1];
					
					//Team strategy
					teamMap.get(stringValues[1]).attackWeight = Integer.parseInt(stringValues[2].split(":")[1]);
					teamMap.get(stringValues[1]).defenseWeight = Integer.parseInt(stringValues[3].split(":")[1]);
					teamMap.get(stringValues[1]).intelligenceWeight = Integer.parseInt(stringValues[4].split(":")[1]);
					teamMap.get(stringValues[1]).resourceProductionWeight = Integer.parseInt(stringValues[5].split(":")[1]);
				}
				
				//Create a player object and add information
				if(stringValues[0].equals("Player")){
					
					playerCount++;
					
					playerMap.put(stringValues[1], new Player());
					playerMap.get(stringValues[1]).name = stringValues[1];
					
					//Player skill set
					playerMap.get(stringValues[1]).attack = Integer.parseInt(stringValues[2].split(":")[1]);
					playerMap.get(stringValues[1]).defense = Integer.parseInt(stringValues[3].split(":")[1]);
					playerMap.get(stringValues[1]).intelligence = Integer.parseInt(stringValues[4].split(":")[1]);
					playerMap.get(stringValues[1]).resourceProduction = Integer.parseInt(stringValues[5].split(":")[1]);
					
					//Subjective player preference list of teams
					playerMap.get(stringValues[1]).preferences = new String[stringValues[6].split(":")[1].split(",").length];
					
					for( int i = 0 ; i < stringValues[6].split(":")[1].split(",").length ; i++ ){
						
						playerMap.get(stringValues[1]).preferences[i] = stringValues[6].split(":")[1].split(",")[i];
					}
					
				}
				
			}//end while
			
			System.out.println(lineCount + " lines read in file \"" + filename + "\"");
			System.out.println(playerCount + " players created.");
			System.out.println(teamCount + " teams created.");
			bufferedReader.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public Map<String, Team> getTeamMap(){
		
		return teamMap;
	}
	
	public Map<String, Player> getPlayerMap(){
		
		return playerMap;
	}
	
}