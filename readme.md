# Team Matching


## Repository
<https://github.com/philipjlin/TeamMatching>


## Description
Team matching program that uses a many-to-one matching algorithm to create fixed size teams for a theoretical multiplayer game. The matching algorithm takes into account the subjective preferences as well objective ratings of players who participate in the algorithm. In addition, teams can weight certain aspects of a players rating to influence how the algorithm works.

The program takes an input file with preferences and rankings of individual players in order to sort the player base into teams that are matched as evenly as possible.


## Technologies
Developed in Java.


## High Level Components
    * Input file - playerteaminfo.txt contains info about team preferences, player preferences, and player ratings.
    * Input file reader
    * Matching engine with sorting logic
    * Objects that represent teams and players


## Class Overview
    Main
        - Main - Sets up and runs the program with the input file.

<br> 

    Domain Objects
        - MatchingGenerator - Contains methods used to generate matchings of teams to players.
        - Player - Represents a player with both subjective preferences and objective ratings. Ratings include stats for attack, defense, intelligent, and resource production. A flag for whether a player has already been matched is also included.
        - Reader - Used to read the input file and extract information and statistics about teams and players.
        - Team - Represents a team with attributes based on the team roster of a fixed size. A team will also have a list of weights for each of the four player ratings (attack, defense, intelligence, resource production) that is used in the matching algorithm.
