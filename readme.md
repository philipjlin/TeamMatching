# Team Matching


## Repository
<https://github.com/philipjlin/TeamMatching>


## Description
Team matching program that uses a many-to-one matching algorithm to create fixed size teams for a theoretical multiplayer game. The matching algorithm takes into account the subjective preferences as well objective ratings of players who participate in the algorithm. In addition, teams can weight certain aspects of a players rating to influence how the algorithm works.

The program takes an input file with preferences and rankings of individual players in order to sort the player base into teams that are matched as evenly as possible.


## Input File
playerteaminfo.txt is the input file used for the program. This file contains information about team preferences, player preferences, and player ratings.


## Class Overview
Main - Sets up and runs the program with the input file.

Domain Objects <br>
    - MatchingGenerator - Contains methods used to generate matchings of teams to players. <br>
    - Player - Represents a player with both subjective preferences and objective ratings. Ratings include stats for attack, defense, intelligent, and resource production. A flag for whether a player has already been matched is also included. <br>
    - Reader - Used to read the input file and extract information and statistics about teams and players. <br>
    - Team - Represents a team with attributes based on the team roster of a fixed size. A team will also have a list of weights for each of the four player ratings (attack, defense, intelligence, resource production) that is used in the matching algorithm. <br>
