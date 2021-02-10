/**
 * Player Class
 * 
 * Specifies a team in the game.
 * 
 * Each team has 4 fields representing the total skill levels in the 4 game categories,
 * which are determined by the totals from the individual players.
 * 
 * Each team has a strategy which is represented by weights for the 4 game categories, which
 * are used to determine which players fit the team based on their skill set. A higher weight 
 * for a category would indicate that the category is more important to the team than another
 * category with a lower weight.
 * 
 * Each team has 1 array that keeps track of  an objective preference list for players,
 * These preferences are calculated using the team strategy weights and a player's skill 
 * set, which show how well a player fits the team strategy.
 * 
 * @author Philip Lin
 *
 */
public class Team{
	
	String name;
	
	//Team consists of a fixed number of players, which are represented in an array
	final static int TEAM_SIZE = 3;
	Player[] teamPlayers = new Player[TEAM_SIZE];
	
	//Total team attributes, based on all players in the team
	int teamAttack = 0;
	int teamDefense = 0;
	int teamIntelligence = 0;
	int teamResourceProduction = 0;
	
	//Weights for team strategy, used when selecting team players
	int attackWeight;
	int defenseWeight;
	int intelligenceWeight;
	int resourceProductionWeight;
	
	//Array of player names that represents objective rank of players based on fit
	String[] preferences;
	
	/**
	 * Calculates the team skill levels for the 4 categories based on all players of the team.
	 */
	public void calculateTeamStats(){
		
		for( Player p : teamPlayers ){
			
			teamAttack += p.attack;
			teamDefense += p.defense;
			teamIntelligence += p.intelligence;
			teamResourceProduction += p.resourceProduction;
		}
	}
	
}