import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * MatchingGenerator Class
 * 
 * Generates matchings for a set of teams and players.
 * 
 * Players are matched to a certain team with a specified size using a many-to-one algorithm
 * in which the players with subjective preferences for teams propose to teams with an objective
 * ranking of all players.
 * 
 * The subjective preferences of players are given as input.
 * The objective rankings of teams for all players are calculated given a player's skill set and 
 * the strategy of a particular team.
 * 
 * 
 * @author Philip Lin
 *
 */
public class MatchingGenerator{
	
	//Maps used to store player and team objects with name keys
	Map<String, Team> teamMap;
	Map<String, Player> playerMap;
	
	/**
	 * Constructor
	 * 
	 * @param teamMap map of teams
	 * @param playerMap map of players
	 */
	public MatchingGenerator(Map<String, Team> teamMap, Map<String, Player> playerMap){
		
		this.teamMap = teamMap;
		this.playerMap = playerMap;
	}
	
	
	/**
	 * Calculates the preference list of players for all teams, by calculating the 
	 * fit of every player for a team.
	 * 
	 * Fit determined by taking the vector dot product of a teams weighted strategy 
	 * and player skill set for the 4 game categories
	 * 
	 * The players are sorted in order of best fit, and added to
	 * the team's preference list.
	 */
	public void calculateTeamPreferenceLists(){
		
		//Map of how well each player fits a team strategy used to determine rank
		Map<String, Integer> playerFitsMap = new HashMap<String, Integer>();
		
		//Loop through all teams and calculate fit for each player
		for( Team team : teamMap.values() ){
			
			//Loop through all players and calculate fit
			for( Player p : playerMap.values() ){
				
				int fit = (team.attackWeight * p.attack) 
				+ (team.defenseWeight * p.defense)
				+ (team.intelligenceWeight * p.intelligence)
				+ (team.resourceProductionWeight * p.resourceProduction);

				playerFitsMap.put(p.name, fit);
			}
			
			//Put fit of every player into a list to sort by order using Collections
			List<Map.Entry<String, Integer>> sortedList = new ArrayList<Map.Entry<String, Integer>>(playerFitsMap.entrySet());
			
			Collections.sort(sortedList, new Comparator<Map.Entry<String, Integer>>(){
			             				
				public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2){
					
					Map.Entry<String, Integer> e1 = (Map.Entry<String, Integer>) o1;
					Map.Entry<String, Integer> e2 = (Map.Entry<String, Integer>) o2;
					
					return ((Comparable<Integer>) e1.getValue()).compareTo(e2.getValue());
				}
			});
			
			//Convert sorted list of player fits into a sorted list of names
			List<String> sortedNames = new ArrayList<String>();
			
			for( Map.Entry<String, Integer> entry : sortedList ){
				
				sortedNames.add(entry.getKey());
			}
			
			//Transfer sorted list into preferences array field for the team
			team.preferences = new String[playerMap.size()];
			team.preferences = sortedNames.toArray(new String[playerFitsMap.size()]);
			
		}//end for
	
	}
	
	/**
	 * Returns the rank of a specified player on a team's player preference list.
	 * 
	 * @param player player to find rank
	 * @param team team whose player preference list to check
	 * @return int player rank
	 */
	public int getPlayerRank(Player player, Team team){
		
		int playerRank = 0;
		
		for( int i = 0 ; i < team.preferences.length ; i++ ){
			
			if( player.name.equals(team.preferences[i]) ){
				
				playerRank = i;
			}
		}
		
		return playerRank;
	}
	
	/**
	 * Returns a player from a specified rank on a team's player preference list.
	 * 
	 * @param rank input rank
	 * @param team team to check team preference list of
	 * @return Player player who has input rank on the team preference list
	 */
	public Player getPlayerByRank(int rank, Team team){
		
		String playerName = team.preferences[rank];
		Player player = playerMap.get(playerName);
		
		return player;
	}
	
	
	/**
	 * 
	 * Runs a many-to-one, round-by-round matching algorithm to match players to teams, with each team 
	 * being matched with a specified number of players based on both the preferences of the players and
	 * a team ranking of how well the players fit that team.
	 * 
	 * Maps of the teams and player are used to keep track of potential matchings at every round.
	 * Players are matched to or removed from teams after each round based on the matching algorithm,
	 * until the teams are filled and the matchings are stable.
	 * 
	 * The algorithm is player proposing.
	 * 
		 
		 Setup: 
		 Map of players
		 	- Set each player to free (to propose)
		 	- Set the index of current place on team preference list to 0 (team at index 0 is most preferred team)
		 Map of teams
		 				
		 					 
		 Rounds:
		 Players that are free propose to highest team on their preference list that they haven't already proposed to.
		 	- After each player's proposal, increment the position on the player's team preference list 
		 
		 Potential matches calculated
		 	- If there are free spaces in a team, player automatically matches with a team
		 		> Player put on a team along with how highly they are ranked for that team
		 	- Otherwise, check to see if a team prefers a player proposal more than an existing match 
		 		> If so, potential match becomes current match, replacing least preferred existing player out
		 			+ Free replaced player to propose again
		 			+ Lock player that is now matched so they don't propose in further rounds unless replaced
		 	- If no match, player remains free (and proposes next round)
		 	
		 End Condition - algorithm ends when no players are free
		  	- All teams should be filled and this is the matching returned
		 	- The matching should be stable
		 
	 * 
	 * @return Map of matchings
	 */
	public void PPDA(){
		
		//Setup 		
		for( Player player : playerMap.values() ){
			
			player.free = true;
			player.preferencesIndex = 0;
		}
		
		//Rounds: Keep repeating until there are no more free players
		System.out.println("\nRun matching algorithm.");
		int freePlayers = playerMap.size();
		int round = 1;
		
		while( freePlayers > 0 ){
			
			System.out.println("________");
			System.out.println("Round " + round + ".");
			System.out.println(freePlayers + " total players propose.");
			round++;
			
			//Loop through all players to see which are free to propose
			for( Player player : playerMap.values() ){
				
				//Player will propose to a team based on their preferences index if free
				if( player.free == true ){
					
					//1. Use preferences index and preferences list to get the team to propose to, and update the preferences index
					Team teamToProposeTo = teamMap.get(player.preferences[player.preferencesIndex]);
					System.out.println("  Player " + player.name + " proposes to: " + teamToProposeTo.name);
					playerMap.get(player.name).preferencesIndex++;
					
					//2. Set up priority queue that contains up to max team size ranks of current players on the team to propose to
					Queue<Integer> ranksQueue = new PriorityQueue<Integer>();
					int maxQueueSize = Team.TEAM_SIZE;
					
					//3. Populate priority queue with ranks of existing players
					Player[] currentTeamPlayers = teamToProposeTo.teamPlayers;
					
					for( int i = 0 ; i < currentTeamPlayers.length ; i++ ){
						
						if( currentTeamPlayers[i] != null ){
							
							int currentPlayerRank = getPlayerRank(currentTeamPlayers[i], teamToProposeTo);
							
							ranksQueue.add(currentPlayerRank);
						}
					}
					
					//4. Determine the rank on the team preference list of the proposing player
					int proposingPlayerRank = getPlayerRank(player, teamToProposeTo);
					
					
					//5. If there is an empty spot on a team (size of ranks queue is less than team size), fill it automatically.
					if( ranksQueue.size() < maxQueueSize ){
						
						ranksQueue.add(proposingPlayerRank);
						System.out.println("    - Player added: " + player.name);
						
						//The proposing player is no longer free to propose next round
						playerMap.get(player.name).free = false;
					}
						
					//6. If all spots are already filled on a team, add the proposing player's rank to the queue, then remove the lowest rank
					else if( ranksQueue.size() == maxQueueSize){
						
						ranksQueue.add(proposingPlayerRank);
						System.out.println("    - Player added: " + player.name);
						
						//Remove the least priority element from the priority queue
						Queue<Integer> newRanksQueue = new PriorityQueue<Integer>();
						while(ranksQueue.size() > 1){
							
					        newRanksQueue.add(ranksQueue.poll());
					    }
						
						//Get the rank of last element of the priorityQueue - the player with that rank is the rejected player
						int rankRemoved = ranksQueue.peek();
						
						Player rejectedPlayer = getPlayerByRank(rankRemoved, teamToProposeTo);
						System.out.println("    - Player rejected: " + rejectedPlayer.name);
						System.out.println("    - " + rejectedPlayer.name + " is free to propose next round.");
						
						//Free rejected player
						playerMap.get(rejectedPlayer.name).free = true;
						
						//If rejected player isn't the proposing player, lock the proposing player
						if( !rejectedPlayer.equals(player) ){
							
							playerMap.get(player.name).free = false;
						}	
						
						//Update queue of ranks for team
						ranksQueue = newRanksQueue;
					} 
					
					//7. Add all teams from the associated ranks in the ranks queue to the current team players
					ArrayList<Player> teamPlayersList = new ArrayList<Player>();
					for( int rank : ranksQueue){
						
						teamPlayersList.add(getPlayerByRank(rank, teamToProposeTo));
					}
					
					teamToProposeTo.teamPlayers = teamPlayersList.toArray(new Player[teamPlayersList.size()]);
					
					teamMap.put(teamToProposeTo.name, teamToProposeTo);					
					

				}//end if
				
			}//end for
			
			//Go through the player map to see how many of the players are still free to determine if end condition is reached
			freePlayers = 0;
			
			for( Player p : playerMap.values() ){
				
				if( p.free == true )
					freePlayers++;
			}
			
			System.out.println("\nCurrent Team Rosters:");
			printTeamRosters();
			System.out.println("Free Players that Propose Next Round: " + freePlayers + "\n");
			
		}//end while
		
		System.out.println("End algorithm, final matchings found.");
		System.out.println("________");
	}
	
	
	/*
	 * Print Methods
	 */
	public void printFinalMatchings(){
		
		System.out.println("\nFinal Team Rosters");
		
		for( Team team : teamMap.values() ){
			
			System.out.println("Team " + team.name);
			System.out.print("Team Players: ");
			
			for( Player player : team.teamPlayers ){
				
				System.out.print(player.name + " ");
			}
						
			team.calculateTeamStats();
			System.out.println("\nTeam Attack: " + team.teamAttack);
			System.out.println("Team Defense: " + team.teamDefense);
			System.out.println("Team Intelligence: " + team.teamIntelligence);
			System.out.println("Team Resource Production: " + team.teamResourceProduction);
			System.out.println("Total Team Skill: " + (team.teamAttack + team.teamDefense + team.teamIntelligence + team.teamResourceProduction));
			
			System.out.println("");
		}
		
	}
	
	public void printPlayerPreferenceLists(){
		
		for( Player player : playerMap.values() ){
			
			System.out.print("\nPlayer: " + player.name + " |");
			System.out.print(" Preferences: ");
			
			for( String team : player.preferences )
				System.out.print(team + " ");
			
		}
		
	}
	
	public void printTeamPreferenceLists(){
		
		for( Team team : teamMap.values() ){
			
			System.out.print("\nTeam: " + team.name + " |");
			System.out.print(" Preferences: ");
			
			for( String player : team.preferences )
				System.out.print(player + " ");
			
		}
	}
	
	public void printTeamRosters(){
		
		for( Team team : teamMap.values() ){
			
			System.out.print("Team " + team.name + ": ");
			
			for( Player player : team.teamPlayers ){
				
				if( player != null)
					System.out.print(player.name + " ");
			}
			
			System.out.println("");
		}
		
	}
	
}