/**
 * Player Class
 * 
 * Specifies an individual player in the game.
 * 
 * Each player has 4 fields representing their skill levels in the 4 game categories.
 * 
 * Each player has 1 array that keeps track of a subjective preference list for teams.
 * 
 * @author Philip Lin
 *
 */
public class Player{
	
	String name;
	
	//Player skill levels contributed to team
	int attack;
	int defense;
	int intelligence;
	int resourceProduction;
	
	//Boolean marker to indicate if a player is free to propose or currently a part of a match
	boolean free;
	
	//Array of team names that represents subjective rank for teams
	String[] preferences;
	
	//Index of the team to propose to when a player is free to propose in the matching algorithm
	int preferencesIndex;

}