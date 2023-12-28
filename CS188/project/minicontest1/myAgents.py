# myAgents.py
# ---------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
#
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).

from game import Agent
from searchProblems import PositionSearchProblem

import util
import time
import search

"""
IMPORTANT
`agent` defines which agent you will use. By default, it is set to ClosestDotAgent,
but when you're ready to test your own agent, replace it with MyAgent
"""
def createAgents(num_pacmen, agent='MyAgent'):
    return [eval(agent)(index=i) for i in range(num_pacmen)]

op = []
target = []
def mazeDistance(point1, point2, gameState):
    """
    Returns the maze distance between any two points, using the search functions
    you have already built. The gameState can be any game state -- Pacman's
    position in that state is ignored.
    Example usage: mazeDistance( (2,4), (5,6), gameState)
    This might be a useful helper function for your ApproximateSearchAgent.
    """
    x1, y1 = point1
    x2, y2 = point2
    walls = gameState.getWalls()
    assert not walls[x1][y1], 'point1 is a wall: ' + str(point1)
    assert not walls[x2][y2], 'point2 is a wall: ' + str(point2)
    prob = PositionSearchProblem(gameState, start=point1, goal=point2, warn=False, visualize=False)
    return len(search.bfs(prob))

class MyAgent(Agent):
    """
    Implementation of your agent.
    """

    def findPathToClosestDot(self, gameState):
        """
        Returns a path (a list of actions) to the closest dot, starting from
        gameState.
        """
        # Here are some useful elements of the startState
        startPosition = gameState.getPacmanPosition()
        food = gameState.getFood()
        walls = gameState.getWalls()
        problem = AnyFoodSearchProblem(gameState)

        x, y = startPosition
        path = []

        mind = 99999
        mfx = 99999
        mfy = 99999

        for idx, fx in enumerate(food):
            for idy, fy in enumerate(fx):
                if food[idx][idy]:
                    md = mazeDistance((x, y), (idx, idy), gameState)
                    if md < mind:
                        mfx = idx
                        mfy = idy
                        mind = md

        prob = PositionSearchProblem(gameState, start=(x, y), goal=(mfx, mfy), warn=False, visualize=False)

        return search.bfs(prob)

    def getAction(self, state):
        """
        Returns the next action the agent will take
        """

        "*** YOUR CODE HERE ***"
        global op, target
        food = state.getFood()
        problem = AnyFoodSearchProblem(state, self.index)
        index = self.index;
        target_i, target_j = target[index];
        flag = 0
        if (target_i, target_j) == (-1, -1) or food[target_i][target_j] is False or len(op[index]) <= 1:
            for i in range(food.width):
                for j in range(food.height):
                    if food[i][j]:
                        path = search.bfs(problem)
                        target[index] = (i, j)
                        op[index] = path
                        count = 0
                        for ith in range(len(target)):
                            if target[ith] == [i, j]:
                                count += 1;

                        flag = (count <= 1)
                        if flag:
                            break;
                if flag:
                    break;
        else:
            del(op[index][0])

        return op[index][0]



    def initialize(self):
        """
        Intialize anything you want to here. This function is called
        when the agent is first created. If you don't need to use it, then
        leave it blank
        """
        "*** YOUR CODE HERE"
        global op, target
        op = [[1,] for i in range(4)]
        target = [[-1, -1] for i in range (4)]

"""
Put any other SearchProblems or search methods below. You may also import classes/methods in
search.py and searchProblems.py. (ClosestDotAgent as an example below)
"""

class ClosestDotAgent(Agent):

    def findPathToClosestDot(self, gameState):
        """
        Returns a path (a list of actions) to the closest dot, starting from
        gameState.
        """
        # Here are some useful elements of the startState
        startPosition = gameState.getPacmanPosition(self.index)
        food = gameState.getFood()
        walls = gameState.getWalls()
        problem = AnyFoodSearchProblem(gameState, self.index)


        "*** YOUR CODE HERE ***"

        def findPathToClosestDot(self, gameState):
            """
            Returns a path (a list of actions) to the closest dot, starting from
            gameState.
            """
            # Here are some useful elements of the startState
            startPosition = gameState.getPacmanPosition(self.index)
            food = gameState.getFood()
            walls = gameState.getWalls()
            problem = AnyFoodSearchProblem(gameState, self.index)

            "*** YOUR CODE HERE ***"
            cost = 1000000000

        global opt
        flag = 0

        if len(opt[self.index]) <= 1:

            for i in range(food.width):

                for j in range(food.height):

                    if food[i][j]:
                        path = search.bfs(problem)

                        if len(path) < cost:
                            opt[self.index] = path
                            cost = len(path)

                            if self.index != 0:
                                flag = 1
                                break
                if flag:
                    break
        else:
            del (opt[self.index][0])

        return opt[self.index]

    def getAction(self, state):
        return self.findPathToClosestDot(state)[0]

class AnyFoodSearchProblem(PositionSearchProblem):
    """
    A search problem for finding a path to any food.

    This search problem is just like the PositionSearchProblem, but has a
    different goal test, which you need to fill in below.  The state space and
    successor function do not need to be changed.

    The class definition above, AnyFoodSearchProblem(PositionSearchProblem),
    inherits the methods of the PositionSearchProblem.

    You can use this search problem to help you fill in the findPathToClosestDot
    method.
    """

    def __init__(self, gameState, agentIndex):
        "Stores information from the gameState.  You don't need to change this."
        # Store the food for later reference
        self.food = gameState.getFood()

        # Store info for the PositionSearchProblem (no need to change this)
        self.walls = gameState.getWalls()
        self.startState = gameState.getPacmanPosition(agentIndex)
        self.costFn = lambda x: 1
        self._visited, self._visitedlist, self._expanded = {}, [], 0 # DO NOT CHANGE

    def isGoalState(self, state):
        """
        The state is Pacman's position. Fill this in with a goal test that will
        complete the problem definition.
        """
        x,y = state

        "*** YOUR CODE HERE ***"
        return self.food[x][y];

