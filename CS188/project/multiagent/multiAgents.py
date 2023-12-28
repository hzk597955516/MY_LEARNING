# multiAgents.py
# --------------
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


from util import manhattanDistance
from game import Directions
import random, util

from game import Agent

class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """


    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        "*** YOUR CODE HERE ***"
        def manhadundistance(x_from, x_to, y_from, y_to):
            return abs(x_from - x_to) + abs(y_from - y_to)

        new_x, new_y = newPos;
        g_x, g_y = newGhostStates[0].getPosition()
        ghostposlist = [i.getPosition() for i in newGhostStates]
        disstoghost = [manhadundistance(new_x, x, new_y, y) for x, y in ghostposlist]
        distofood = 99999
        distoghost = sum([(1 / (i + 1)) for i in disstoghost]) / len(disstoghost)
        for i in range(newFood.width):
            for j in range(newFood.height):
                if newFood[i][j]:
                    distofood = min(distofood, manhadundistance(new_x, i, new_y, j))

        if (distoghost < 8):
            return successorGameState.getScore() / 10 + 2 / (distofood + 1) - 10 * distoghost
        else:
            return successorGameState.getScore() / 10 + 2 / (distofood + 1) - distoghost

def scoreEvaluationFunction(currentGameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '1'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action from the current gameState using self.depth
        and self.evaluationFunction.

        Here are some method calls that might be useful when implementing minimax.

        gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

        gameState.generateSuccessor(agentIndex, action):
        Returns the successor game state after an agent takes an action

        gameState.getNumAgents():
        Returns the total number of agents in the game

        gameState.isWin():
        Returns whether or not the game state is a winning state

        gameState.isLose():
        Returns whether or not the game state is a losing state
        """
        "*** YOUR CODE HERE ***"

        def minimaxSearch(State, agentIndex, depth):
            if depth == self.depth or State.isWin() or State.isLose():
                return self.evaluationFunction(State), Directions.STOP
            elif agentIndex == 0:
                return maximizer(State, agentIndex, depth)
            else:
                return minimizer(State, agentIndex, depth)

        def maximizer(State, agentIndex, depth):
            next_agentIndex = agentIndex + 1
            next_depth = depth
            v = -1000000000000000
            action = Directions.STOP
            actions = State.getLegalActions(agentIndex)
            for i in actions:
                new_state = State.generateSuccessor(agentIndex, i)
                new_v = minimaxSearch(new_state, next_agentIndex, next_depth)[0]
                if new_v > v:
                    v = new_v
                    action = i
            return v, action

        def minimizer(State, agentIndex, depth):
            if agentIndex == State.getNumAgents() - 1:
                next_agentIndex = 0
                next_depth = depth + 1
            else:
                next_agentIndex = agentIndex + 1
                next_depth = depth
            v = 10000000000000000
            action = Directions.STOP
            actions = State.getLegalActions(agentIndex)
            for i in actions:
                new_state = State.generateSuccessor(agentIndex, i)
                new_v = minimaxSearch(new_state, next_agentIndex, next_depth)[0]
                if new_v < v:
                    v = new_v
                    action = i
            return v, action

        return minimaxSearch(gameState, agentIndex=0, depth=0)[1]



class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
        Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"

        def minimaxSearch(State, agentIndex, depth, a, b):
            if depth == self.depth or State.isWin() or State.isLose():
                return self.evaluationFunction(State), Directions.STOP
            elif agentIndex == 0:
                return maximizer(State, agentIndex, depth, a, b)
            else:
                return minimizer(State, agentIndex, depth, a, b)

        def maximizer(State, agentIndex, depth, a, b):
            next_agentIndex = agentIndex + 1
            next_depth = depth
            v = -1000000000000000
            action = Directions.STOP
            actions = State.getLegalActions(agentIndex)
            for i in actions:
                new_state = State.generateSuccessor(agentIndex, i)
                new_v = minimaxSearch(new_state, next_agentIndex, next_depth, a, b)[0]
                if new_v > v:
                    v = new_v
                    action = i
                if v > b:
                    return v, action
                a = max(a, v)
            return v, action

        def minimizer(State, agentIndex, depth, a, b):
            if agentIndex == State.getNumAgents() - 1:
                next_agentIndex = 0
                next_depth = depth + 1
            else:
                next_agentIndex = agentIndex + 1
                next_depth = depth
            v = 10000000000000000
            action = Directions.STOP
            actions = State.getLegalActions(agentIndex)
            for i in actions:
                new_state = State.generateSuccessor(agentIndex, i)
                new_v = minimaxSearch(new_state, next_agentIndex, next_depth, a, b)[0]
                if new_v < v:
                    v = new_v
                    action = i
                if v < a:
                    return v, action
                b = min(v, b)
            return v, action

        return minimaxSearch(gameState, agentIndex=0, depth=0, a=-1e9, b=1e9)[1]

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        "*** YOUR CODE HERE ***"
        def minimaxSearch(State, agentIndex, depth):
            if depth == self.depth or State.isWin() or State.isLose():
                return self.evaluationFunction(State), Directions.STOP
            elif agentIndex == 0:
                return maximizer(State, agentIndex, depth)
            else:
                return expimizer(State, agentIndex, depth)

        def maximizer(State, agentIndex, depth):
            next_agentIndex = agentIndex + 1
            next_depth = depth
            v = -1e9
            action = Directions.STOP
            actions = State.getLegalActions(agentIndex)
            for i in actions:
                new_state = State.generateSuccessor(agentIndex, i)
                new_v = minimaxSearch(new_state, next_agentIndex, next_depth)[0]
                if new_v > v:
                    v = new_v
                    action = i
            return v, action

        def expimizer(State, agentIndex, depth):
            if agentIndex == State.getNumAgents() - 1:
                next_agentIndex = 0
                next_depth = depth + 1
            else:
                next_agentIndex = agentIndex + 1
                next_depth = depth

            v = 0
            action = Directions.STOP
            actions = State.getLegalActions(agentIndex)
            for i in actions:
                new_state = State.generateSuccessor(agentIndex, i)
                new_v = minimaxSearch(new_state, next_agentIndex, next_depth)[0]
                v += new_v
            return v / len(actions), action

        return minimaxSearch(gameState, agentIndex=0, depth=0)[1]

def betterEvaluationFunction(currentGameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: <write something here so we know what you did>
    """
    "*** YOUR CODE HERE ***"
    pacman_pos = currentGameState.getPacmanPosition()
    food = currentGameState.getFood()
    foods = food.asList()
    ghost_states = currentGameState.getGhostStates()
    scared_times = [ghost_state.scaredTimer for ghost_state in ghost_states]

    nearest_ghost_dis = 1e9
    for ghost_state in ghost_states:
        ghost_x, ghost_y = ghost_state.getPosition()
        ghost_x = int(ghost_x)
        ghost_y = int(ghost_y)

        if ghost_state.scaredTimer == 0:
            nearest_ghost_dis = min(nearest_ghost_dis, manhattanDistance((ghost_x, ghost_y), pacman_pos))
        else:
            nearest_ghost_dis = - 10

    nearest_food_dis = 1e9
    for food in foods:
        nearest_food_dis = min(nearest_food_dis, manhattanDistance(food, pacman_pos))

    if not foods:
        nearest_food_dis = 0

    return currentGameState.getScore() - 7 / (nearest_ghost_dis + 1) - nearest_food_dis / 3
# Abbreviation
better = betterEvaluationFunction
